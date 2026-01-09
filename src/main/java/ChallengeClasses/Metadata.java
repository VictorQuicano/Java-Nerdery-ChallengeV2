package ChallengeClasses;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Metadata{
    private String _id;
    private String _dev_id;
    private String name;
    private String location;
    private OffsetDateTime dateTime;
    private DayOfWeek dayOfWeek;

    public Metadata(){}

    public Metadata(String _id, String _dev_id, String name, String location, String dateTime, String dayOfWeek){
        this._id = _id;
        this._dev_id = _dev_id;
        this.name = name;
        this.location = location;
        this.dateTime = OffsetDateTime.parse(dateTime);
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
    }

    @Override
    public String toString() {
        return String.format(
                "%s — %s",
                name != null ? "Location: "+name : "Unknown location",
                dateTime != null
                        ? dateTime.format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy HH:mm (XXX)"))
                        : "Unknown date"
        );
    }


    public Integer getDay(){
        return dateTime.getDayOfMonth();
    }

    public Integer getMonth(){
        return dateTime.getMonthValue();
    }

    public Integer getYear(){
        return dateTime.getYear();
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_dev_id() {
        return _dev_id;
    }

    public void set_dev_id(String _dev_id) {
        this._dev_id = _dev_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}