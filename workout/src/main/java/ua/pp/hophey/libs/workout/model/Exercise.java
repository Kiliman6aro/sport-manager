package ua.pp.hophey.libs.workout.model;

public class Exercise{
    private final String name;              // Название упражнения
    private final int setCount;            // Количество подходов
    private final int repetitions;         // Количество повторений в подходе
    private final int restTime;            // Перерыв между подходами (в секундах)
    private final int durationPerRep;   // Длительность одного повторения (в секундах)

    public Exercise(String name, int setCount, int repetitions, int restTime, int durationPerRep) {
        this.name = name;
        this.setCount = setCount;
        this.repetitions = repetitions;
        this.restTime = restTime;
        this.durationPerRep = durationPerRep;
    }

    public String getName() {
        return name;
    }

    public int getSetCount() {
        return setCount;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public int getRestTime() {
        return restTime;
    }

    public int getDurationPerRep() {
        return durationPerRep;
    }

    @Override
    public String toString() {
        return String.format("%s: %d подходов, %d повторений в подходе, длительность %d сек, отдых %d сек",
                name, setCount, repetitions, durationPerRep, restTime);
    }

    public void run() {
        System.out.printf("Упражнение %s началась. [ %s ]%n", this.getName(), this);
        try {
            for (int i = 0; i < this.setCount; i++) {
                System.out.printf("Начинаю подход %d %n", i + 1);
                for (int j = 0; j < this.repetitions; j++) {
                    System.out.printf("One: [%d] %n", j + 1);
                    Thread.sleep((this.durationPerRep / 2) * 1000L);
                    System.out.println("Two...");
                    Thread.sleep((this.durationPerRep / 2) * 1000L);
                }
                if (i < this.setCount - 1 && restTime > 0) { // Отдых только между подходами
                    System.out.println("Отдых между подходами");
                    Thread.sleep(this.restTime * 1000L);
                }
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
        System.out.printf("Упражнение %s закончилось. [ %s ]%n", this.getName(), this);
    }
}
