package ua.pp.hophey.pushupapp.Exercise;


public class ExerciseSet {
    private final int totalRepetitions;      // Общее количество повторений
    private final long pauseDurationMillis;  // Длительность паузы в миллисекундах
    private final ExerciseHandler handler;   // Обработчик событий

    private int currentRepetition = 0;       // Счётчик завершённых повторений
    private boolean isExerciseStarted = false; // Флаг этапа в цикле
    private volatile boolean running = false;  // Флаг для остановки потока
    private Thread exerciseThread;           // Поток для выполнения сета

    public ExerciseSet(int totalRepetitions, double pauseDurationSeconds, ExerciseHandler handler) {
        this.totalRepetitions = totalRepetitions;
        this.pauseDurationMillis = (long) (pauseDurationSeconds * 1000); // Переводим секунды в миллисекунды
        this.handler = handler;
    }

    public void start() {
        if (totalRepetitions <= 0 || pauseDurationMillis <= 0) {
            throw new IllegalArgumentException("Количество повторений и пауза должны быть больше 0");
        }

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
            handler.onExerciseEnd(currentRepetition + 1, totalRepetitions);
            handler.onSetComplete(totalRepetitions);
        }

    }

    public int getTotalRepetitions(){
       return totalRepetitions;
    }

    private void executeFirstStep() {
        if (running) {
            handler.onExerciseStart(currentRepetition + 1, totalRepetitions);
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
                handler.onSetComplete(totalRepetitions);
            }else{
                handler.onExerciseEnd(currentRepetition + 1, totalRepetitions);
            }
        } else if (currentRepetition < totalRepetitions) {
            handler.onExerciseStart(currentRepetition + 1, totalRepetitions);
            isExerciseStarted = true;
        }
    }
}