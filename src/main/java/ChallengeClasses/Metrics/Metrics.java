package ChallengeClasses.Metrics;

public class Metrics{

    private Double airTemp;
    private Double atmosphericPressure;
    private Double gustSpeed;
    private Double precipitation;
    private Double relativeHumidity;
    private Double solar;
    private Double strikeDistance;
    private Double strikes;
    private Double vapourPressure;
    private Double windDirection;
    private Double windSpeed;

    public Metrics(){};

    public Metrics(Double airTemp, Double atmosphericPressure, Double gustSpeed, Double precipitation, Double relativeHumidity, Double solar, Double strikeDistance, Double strikes, Double vapourPressure, Double windDirection, Double windSpeed) {
        this.airTemp = airTemp;
        this.atmosphericPressure = atmosphericPressure;
        this.gustSpeed = gustSpeed;
        this.precipitation = precipitation;
        this.relativeHumidity = relativeHumidity;
        this.solar = solar;
        this.strikeDistance = strikeDistance;
        this.strikes = strikes;
        this.vapourPressure = vapourPressure;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }

    protected String format(Double value, String unit) {
        if (value == null) {
            return "N/A";
        }
        return String.format("%.2f %s", value, unit).trim();
    }

    @Override
    public String toString() {
        return "Metrics {" +
                "\n  Air Temperature       : " + format(airTemp, "°C") +
                "\n  Atmospheric Pressure  : " + format(atmosphericPressure, "hPa") +
                "\n  Gust Speed            : " + format(gustSpeed, "m/s") +
                "\n  Precipitation         : " + format(precipitation, "mm") +
                "\n  Relative Humidity     : " + format(relativeHumidity, "%") +
                "\n  Solar Radiation       : " + format(solar, "W/m²") +
                "\n  Strike Distance       : " + format(strikeDistance, "km") +
                "\n  Strikes               : " + format(strikes, "") +
                "\n  Vapour Pressure       : " + format(vapourPressure, "hPa") +
                "\n  Wind Direction        : " + format(windDirection, "°") +
                "\n  Wind Speed            : " + format(windSpeed, "m/s") +
                "\n}";
    }
    private Double min(Double a, Double b) {
        if (a == null) return b;
        if (b == null) return a;
        return Math.min(a, b);
    }
    private Double max(Double a, Double b) {
        if (a == null) return b;
        if (b == null) return a;
        return Math.max(a, b);
    }
    private Double add(Double a, Double b) {
        return (a == null ? 0.0 : a) + (b == null ? 0.0 : b);
    }
    private Double divide(Double a, int b) {
        return (a == null ? 0.0 : a ) / b;
    }

    public Metrics minWith(Metrics other) {
        return new Metrics(
                min(this.airTemp, other.airTemp),
                min(this.atmosphericPressure, other.atmosphericPressure),
                min(this.gustSpeed, other.gustSpeed),
                min(this.precipitation, other.precipitation),
                min(this.relativeHumidity, other.relativeHumidity),
                min(this.solar, other.solar),
                min(this.strikeDistance, other.strikeDistance),
                min(this.strikes, other.strikes),
                min(this.vapourPressure, other.vapourPressure),
                min(this.windDirection, other.windDirection),
                min(this.windSpeed, other.windSpeed)
        );
    }
    public Metrics maxWith(Metrics other) {
        return new Metrics(
                max(this.airTemp, other.airTemp),
                max(this.atmosphericPressure, other.atmosphericPressure),
                max(this.gustSpeed, other.gustSpeed),
                max(this.precipitation, other.precipitation),
                max(this.relativeHumidity, other.relativeHumidity),
                max(this.solar, other.solar),
                max(this.strikeDistance, other.strikeDistance),
                max(this.strikes, other.strikes),
                max(this.vapourPressure, other.vapourPressure),
                max(this.windDirection, other.windDirection),
                max(this.windSpeed, other.windSpeed)
        );
    }
    public Metrics add(Metrics other) {
        return new Metrics(
                add(this.airTemp, other.airTemp),
                add(this.atmosphericPressure, other.atmosphericPressure),
                add(this.gustSpeed, other.gustSpeed),
                add(this.precipitation, other.precipitation),
                add(this.relativeHumidity, other.relativeHumidity),
                add(this.solar, other.solar),
                add(this.strikeDistance, other.strikeDistance),
                add(this.strikes, other.strikes),
                add(this.vapourPressure, other.vapourPressure),
                add(this.windDirection, other.windDirection),
                add(this.windSpeed, other.windSpeed)
        );
    }
    public Metrics divideWith(int divisor){
        return new Metrics(
                divide(this.airTemp, divisor),
                divide(this.atmosphericPressure, divisor),
                divide(this.gustSpeed, divisor),
                divide(this.precipitation, divisor),
                divide(this.relativeHumidity, divisor),
                divide(this.solar, divisor),
                divide(this.strikeDistance, divisor),
                divide(this.strikes, divisor),
                divide(this.vapourPressure, divisor),
                divide(this.windDirection, divisor),
                divide(this.windSpeed, divisor)
        );
    }

    public Double getAirTemp() { return airTemp; }
    public Double getAtmosphericPressure() {
        return atmosphericPressure;
    }
    public Double getGustSpeed() {
        return gustSpeed;
    }
    public Double getPrecipitation() { return precipitation;}
    public Double getRelativeHumidity() { return relativeHumidity;}
    public Double getSolar() { return solar;}
    public Double getStrikeDistance() { return strikeDistance;}
    public Double getStrikes() { return strikes; }
    public Double getVapourPressure() { return vapourPressure; }
    public Double getWindDirection() { return windDirection; }
    public Double getWindSpeed() { return windSpeed;     }
}
