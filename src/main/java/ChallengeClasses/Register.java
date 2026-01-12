package ChallengeClasses;

import ChallengeClasses.Metrics.MetricAVG;
import ChallengeClasses.Metrics.MetricAccumulator;
import ChallengeClasses.Metrics.Metrics;
import ChallengeClasses.Metrics.PrintableMetrics;

import java.io.IOException;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Register implements PrintableMetrics {
    private List<Record> recordList;

    // Metrics
    private Metrics minMetrics;
    private Metrics maxMetrics;
    private Metrics avgMetrics;

    // Locations
    private List<String> uniqueLocations;

    // Builders
    public Register(){}
    public Register(List<Record> records){
        recordList = records;
        this.getUniqueLocations();
        this.getMetrics();
    }
    public Register(String originFile){
        WeatherDeserializer deserializer = new WeatherDeserializer();
        try{
            this.recordList = deserializer.parseJsonFile(originFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.getMetrics();
        this.getUniqueLocations();
    }
    private void getUniqueLocations(){

        uniqueLocations = recordList.stream()
                .map(Record::getMetadataInfo)
                .map(Metadata::getName)
                .distinct()
                .sorted()
                .toList();
    }
    private void getMetrics(){

        minMetrics = recordList.stream()
                .map(Record::getMetrics)
                .reduce(Metrics::minWith)
                .orElse(null);
        maxMetrics = recordList.stream()
                .map(Record::getMetrics)
                .reduce(Metrics::maxWith)
                .orElse(null);
        avgMetrics = recordList.stream()
                .map(Record::getMetrics)
                .collect(MetricAccumulator::new, MetricAccumulator::addMetric,
                        (a, b) -> { throw new UnsupportedOperationException(); })
                .getAvg();
    }


    public String printUniqueLocations(){
        StringBuilder initString = new StringBuilder("Unique Locations:");
        int index = 1;
        uniqueLocations.forEach((loc) -> {
            String formatedLocation = "\n  " + index+ ") " + loc;
            initString.append(formatedLocation);
        });
        return initString.toString();
    }
    public String printResume() {
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("METRICS SUMMARY\n");
        sb.append("-".repeat(60)).append("\n");

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

    public Register filterByDate(int year, int month, int day, int hour) {
        List<Record> records = recordList.stream()
                .filter(r -> year <= 0 || r.getMetadataInfo().getDateTime().getYear() == year)
                .filter(r -> month <= 0 || r.getMetadataInfo().getDateTime().getMonthValue() == month)
                .filter(r -> day <= 0 || r.getMetadataInfo().getDateTime().getDayOfMonth() == day)
                .filter(r -> hour < 0 || r.getMetadataInfo().getDateTime().getHour() == hour)
                .sorted(Comparator.comparing(r -> r.getMetadataInfo().getDateTime()))
                .toList();

        return new Register(records);
    }
    public OffsetDateTime minDate(){
        return recordList.stream()
                .min(Comparator.comparing(r -> r.getMetadataInfo().getDateTime()))
                .map(r -> r.getMetadataInfo().getDateTime())
                .orElse(null);
    }
    public OffsetDateTime maxDate(){
        return recordList.stream()
                .max(Comparator.comparing(r -> r.getMetadataInfo().getDateTime()))
                .map(r -> r.getMetadataInfo().getDateTime())
                .orElse(null);
    }


    public String printResumeRegister() {
        StringBuilder resume = new StringBuilder();
        resume.append(" # Unique Stations: ".concat(String.valueOf(uniqueLocations.size())).concat("\n"));
        resume.append(String.format(" # Registers: %,d%n", recordList.size()));

        String minDate = minDate().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
        String maxDate = maxDate().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
        resume.append(String.format(" From %s to %s", minDate, maxDate ).concat("\n"));
        return resume.toString();
    }

    public void printCompleteRegister() {
        StringBuilder completeRegister = new StringBuilder();
        for(Record record: recordList) {
            completeRegister.append(record.toString());
            completeRegister.append("\n");
        }
        System.out.println(completeRegister);
    }

    public Register filterByLocationName(String nameLocation){
        List<Record> records = recordList.stream()
                .filter(r ->
                    r.getMetadataInfo().getName().equals(nameLocation)
                ).toList();
        return new Register(records);
    }
    // Getters and Setters
    public List<String> getUniqueLocationsList() {
        return uniqueLocations;
    }
    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public Metrics getMinMetrics() {
        return minMetrics;
    }

    public void setMinMetrics(Metrics minMetrics)
    {
        this.minMetrics = minMetrics;
    }

    public Metrics getMaxMetrics() {
        return maxMetrics;
    }

    public void setMaxMetrics(Metrics maxMetrics) {
        this.maxMetrics = maxMetrics;
    }

    public Metrics getAvgMetrics() {
        return avgMetrics;
    }

    public void setAvgMetrics(Metrics avgMetrics) {
        this.avgMetrics = avgMetrics;
    }

}
