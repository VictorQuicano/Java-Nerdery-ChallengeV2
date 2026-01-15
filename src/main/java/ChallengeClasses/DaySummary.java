package ChallengeClasses;

import ChallengeClasses.Metrics.MetricsManager;
import ChallengeClasses.Metrics.PrintableMetrics;

import java.time.LocalDate;
import java.util.List;

public class DaySummary extends PrintableMetrics {
    private LocalDate date;

    public DaySummary(LocalDate date, List<Record> records)
    {
        this.date = date;
        calculateMetrics(records);
    }

    private void calculateMetrics(List<Record> records){
        MetricsManager manager = new MetricsManager(records);
    }

    @Override
    public String toString(){
        return date.format(FORMATTER).concat("\n") +
                "-".repeat(60) + "\n" +
                manager.toString();
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getMetricsTime(){
        return this.manager.getMetricsTime();
    }
}

