package ChallengeClasses.Metrics;

import ChallengeClasses.Record;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public abstract class PrintableMetrics {
    protected MetricsManager manager;

    protected PrintableMetrics(){};

    protected PrintableMetrics(List<Record> records){
        manager = new MetricsManager(records);
    }

    protected static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("EEEE yyyy-MM-dd", Locale.ENGLISH);

    public Metrics getMinMetrics() {
        return manager.getMin();
    }
    public Metrics getMaxMetrics() {
        return manager.getMax();
    }
    public Metrics getAvgMetrics() { return manager.getAVG(); }
    public double getTimeGetMetric() {
        return manager.getMetricsTime();
    }

}

