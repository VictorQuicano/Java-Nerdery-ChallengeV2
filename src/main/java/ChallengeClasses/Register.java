package ChallengeClasses;

import ChallengeClasses.Metrics.MetricAccumulator;
import ChallengeClasses.Metrics.Metrics;
import ChallengeClasses.Metrics.MetricsManager;
import ChallengeClasses.Metrics.PrintableMetrics;

import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.stream.Collectors;

public class Register extends PrintableMetrics {
    private List<Record> recordList;
    private double timeConstruction;
    private double timeGetPerDay;
    private List<DaySummary> daySummary;
    // Locations
    private List<String> uniqueLocations;

    // Builders
    public Register(){}
    public Register(List<Record> records, double time){
        super(records);
        recordList = records;
        timeConstruction = time;
        this.getUniqueLocations();
        this.getMetricsPerDay();
    }
    public Register(String originFile){
        double startTime = System.nanoTime(), endTime;
        WeatherDeserializer deserializer = new WeatherDeserializer();
        try{
            this.recordList = deserializer.parseJsonFile(originFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        endTime = System.nanoTime();
        this.timeConstruction = (endTime - startTime) / 1_000_000;
        this.getMetrics();
        this.getMetricsPerDay();
        this.getUniqueLocations();
    }

    private void getMetrics(){
        manager = new MetricsManager(recordList);
    }
    private void getUniqueLocations(){

        uniqueLocations = recordList.stream()
                .map(Record::getMetadataInfo)
                .map(Metadata::getName)
                .distinct()
                .sorted()
                .toList();
    }
    private void getMetricsPerDay() {
        double start = System.nanoTime(), end, duration;
        daySummary =  this.recordList.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getMetadataInfo().getDateTime().toLocalDate()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new DaySummary(entry.getKey(), entry.getValue()))
                .toList();
        timeGetPerDay = (System.nanoTime() - start)/ 1_000_000;
    }

    public String printUniqueLocations() {
        StringBuilder result = new StringBuilder("Unique Locations:");
        int index = 1;

        for (String loc : uniqueLocations) {
            result.append("\n  ").append(index++).append(") ").append(loc);
        }

        return result.toString();
    }

    public String printResume() {
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("METRICS SUMMARY\n");
        sb.append("-".repeat(60)).append("\n");

        sb.append(manager.toString());
        sb.append("\n").append("=".repeat(60)).append("\n");

        return sb.toString();
    }

    public Register filterByLocationName(String nameLocation){
        double startTime = System.nanoTime(), endTime, duration;
        List<Record> records = recordList.stream()
                .filter(r ->
                        r.getMetadataInfo().getName().equals(nameLocation)
                ).toList();
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000;
        return new Register(records, duration);
    }
    public Register filterByDate(int year, int month, int day, int hour) {
        double startTime = System.nanoTime(), endTime, duration;
        List<Record> records = recordList.stream()
                .filter(r -> year <= 0 || r.getMetadataInfo().getDateTime().getYear() == year)
                .filter(r -> month <= 0 || r.getMetadataInfo().getDateTime().getMonthValue() == month)
                .filter(r -> day <= 0 || r.getMetadataInfo().getDateTime().getDayOfMonth() == day)
                .filter(r -> hour < 0 || r.getMetadataInfo().getDateTime().getHour() == hour)
                .sorted(Comparator.comparing(r -> r.getMetadataInfo().getDateTime()))
                .toList();
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000;
        return new Register(records, duration);
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


    // Getters and Setters
    public List<String> getUniqueLocationsList() {
        return uniqueLocations;
    }
    public List<Record> getRecordList() {
        return recordList;
    }
    public double getTimeConstruction() {
        return timeConstruction;
    }
    public double getTimeGetPerDay() {
        return timeGetPerDay;
    }
    public List<DaySummary> getDaySummary(){return daySummary;}
}
