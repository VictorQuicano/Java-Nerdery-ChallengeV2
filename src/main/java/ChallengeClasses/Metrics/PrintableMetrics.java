package ChallengeClasses.Metrics;

public interface PrintableMetrics
{
    default String format(Double value, String unit) {
        if (value == null) {
            return "N/A";
        }
        return String.format("%.2f %s", value, unit).trim();
    }
}
