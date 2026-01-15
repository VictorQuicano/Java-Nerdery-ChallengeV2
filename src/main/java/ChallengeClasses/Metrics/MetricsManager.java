package ChallengeClasses.Metrics;

import java.util.List;
import ChallengeClasses.Record;

public class MetricsManager {
    private Metrics maxMetrics;
    private Metrics minMetrics;
    private Metrics sumMetrics;
    private Metrics avgMetrics;

    private double metricsTime;
    private int count = 0;


    public MetricsManager(List<Record> records) {
        long start = System.nanoTime();

        records.stream()
                .map(Record::getMetrics)
                .forEach(this::addMetric);
        avgMetrics = sumMetrics.divideWith(count);
        metricsTime = (System.nanoTime() - start) / 1_000_000d;
    }

    public void addMetric(Metrics m) {
        if (count == 0) {
            maxMetrics = m;
            minMetrics = m;
            sumMetrics = m;
        } else {
            maxMetrics.maxWith(m);
            minMetrics.minWith(m);
            sumMetrics.add(m);
        }
        count++;
    }

    public Metrics getMax() {
        return maxMetrics;
    }

    public Metrics getMin() {
        return minMetrics;
    }

    public Metrics getAVG() {
        return sumMetrics.divideWith(count);
    }

    private void addMetricRow(StringBuilder sb, String metricName,
                              double min, double max, double avg, String unit) {
        String metricResume = String.format(
                "\n  - %s :" + "\n   AVG: %.2f %s" + "\n   MIN: %.2f %s" + "\n   MAX: %.2f %s",
                metricName,
                avg, unit,
                min, unit,
                max, unit
        );
        sb.append(metricResume);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Add each metric row
        addMetricRow(sb, "Air Temperature",
                minMetrics.getAirTemp(), maxMetrics.getAirTemp(), avgMetrics.getAirTemp(), "°C");

        addMetricRow(sb, "Atmospheric Pressure",
                minMetrics.getAtmosphericPressure(), maxMetrics.getAtmosphericPressure(),
                avgMetrics.getAtmosphericPressure(), "hPa");

        addMetricRow(sb, "Gust Speed",
                minMetrics.getGustSpeed(), maxMetrics.getGustSpeed(),
                avgMetrics.getGustSpeed(), "m/s");

        addMetricRow(sb, "Precipitation",
                minMetrics.getPrecipitation(), maxMetrics.getPrecipitation(),
                avgMetrics.getPrecipitation(), "mm");

        addMetricRow(sb, "Relative Humidity",
                minMetrics.getRelativeHumidity(), maxMetrics.getRelativeHumidity(),
                avgMetrics.getRelativeHumidity(), "%");

        addMetricRow(sb, "Solar Radiation",
                minMetrics.getSolar(), maxMetrics.getSolar(),
                avgMetrics.getSolar(), "W/m²");

        addMetricRow(sb, "Strike Distance",
                minMetrics.getStrikeDistance(), maxMetrics.getStrikeDistance(),
                avgMetrics.getStrikeDistance(), "km");

        addMetricRow(sb, "Strikes",
                minMetrics.getStrikes(), maxMetrics.getStrikes(),
                avgMetrics.getStrikes(), "");

        addMetricRow(sb, "Vapour Pressure",
                minMetrics.getVapourPressure(), maxMetrics.getVapourPressure(),
                avgMetrics.getVapourPressure(), "hPa");

        addMetricRow(sb, "Wind Direction",
                minMetrics.getWindDirection(), maxMetrics.getWindDirection(),
                avgMetrics.getWindDirection(), "°");

        addMetricRow(sb, "Wind Speed",
                minMetrics.getWindSpeed(), maxMetrics.getWindSpeed(),
                avgMetrics.getWindSpeed(), "m/s");

        sb.append("\n").append("=".repeat(60)).append("\n");

        return sb.toString();
    }
    
    public double getMetricsTime(){return metricsTime;}
}
