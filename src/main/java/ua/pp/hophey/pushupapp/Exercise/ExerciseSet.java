package ua.pp.hophey.pushupapp.Exercise;


public class ExerciseSet {
    private final int totalRepetitions;      // Общее количество повторений
    private final long pauseDurationMillis;  // Длительность паузы в миллисекундах
    private final ExerciseHandler exerciseHandler;   // Обработчик событий упражениня в подходе
    private final SetHandler setHandler;    // Обработчик событий подхода

    private int currentRepetition = 0;       // Счётчик завершённых повторений
    private boolean isExerciseStarted = false; // Флаг этапа в цикле
    private volatile boolean running = false;  // Флаг для остановки потока
    private Thread exerciseThread;           // Поток для выполнения сета

    public ExerciseSet(int totalRepetitions, double pauseDurationSeconds, ExerciseHandler exerciseHandler, SetHandler setHandler) {
        this.totalRepetitions = totalRepetitions;
        this.pauseDurationMillis = (long) (pauseDurationSeconds * 1000); // Переводим секунды в миллисекунды
        this.exerciseHandler = exerciseHandler;
        this.setHandler = setHandler;
    }

    public void start() {
        if (totalRepetitions <= 0 || pauseDurationMillis <= 0) {
            throw new IllegalArgumentException("Количество повторений и пауза должны быть больше 0");
        }
        setHandler.onSetStart(totalRepetitions);
        currentRepetition = 0;
        isExerciseStarted = false;
        running = true;

        exerciseThread = new Thread(() -> {
            executeFirstStep(); // Первый шаг без задержки
            while (running && currentRepetition < totalRepetitions) {
                try {
                    Thread.sleep(pauseDurationMillis); // Пауза между шагами
                    executeNextStep();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        });
        exerciseThread.start();
    }

    public void stop() {
        running = false;
        if (exerciseThread != null) {
            exerciseHandler.onExerciseEnd(currentRepetition + 1, totalRepetitions);
            setHandler.onSetComplete(totalRepetitions);
        }

    }

    public int getTotalRepetitions(){
       return totalRepetitions;
    }

    private void executeFirstStep() {
        if (running) {
            exerciseHandler.onExerciseStart(currentRepetition + 1, totalRepetitions);
            isExerciseStarted = true;
        }
    }

    private void executeNextStep() {
        if (!running) return;

        if (isExerciseStarted) {
            currentRepetition++;
            isExerciseStarted = false;
            if (currentRepetition == totalRepetitions) {
                running = false;
                setHandler.onSetComplete(totalRepetitions);
            }else{
                exerciseHandler.onExerciseEnd(currentRepetition + 1, totalRepetitions);
            }
        } else if (currentRepetition < totalRepetitions) {
            exerciseHandler.onExerciseStart(currentRepetition + 1, totalRepetitions);
            isExerciseStarted = true;
        }
    }
}