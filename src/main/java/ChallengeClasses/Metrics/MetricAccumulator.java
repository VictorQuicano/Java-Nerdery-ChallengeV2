package ChallengeClasses.Metrics;

public class MetricAccumulator {
    private MetricAVG airTemp;
    private MetricAVG atmosphericPressure;
    private MetricAVG gustSpeed;
    private MetricAVG precipitation;
    private MetricAVG relativeHumidity;
    private MetricAVG solar;
    private MetricAVG strikeDistance;
    private MetricAVG strikes;
    private MetricAVG vapourPressure;
    private MetricAVG windDirection;
    private MetricAVG windSpeed;

    public MetricAccumulator(){
        airTemp = new MetricAVG();
        atmosphericPressure = new MetricAVG();
        gustSpeed = new MetricAVG();
        precipitation = new MetricAVG();
        relativeHumidity = new MetricAVG();
        solar = new MetricAVG();
        strikeDistance = new MetricAVG();
        strikes = new MetricAVG();
        vapourPressure = new MetricAVG();
        windDirection = new MetricAVG();
        windSpeed = new MetricAVG();
    };
    public void addMetric(Metrics metric) {
        airTemp.addMetric(metric.getAirTemp());
        atmosphericPressure.addMetric(metric.getAtmosphericPressure());
        gustSpeed.addMetric(metric.getGustSpeed());
        precipitation.addMetric(metric.getPrecipitation());
        relativeHumidity.addMetric(metric.getRelativeHumidity());
        solar.addMetric(metric.getSolar());
        strikeDistance.addMetric(metric.getStrikeDistance());
        strikes.addMetric(metric.getStrikes());
        vapourPressure.addMetric(metric.getVapourPressure());
        windDirection.addMetric(metric.getWindDirection());
        windSpeed.addMetric(metric.getWindSpeed());
    }

    public Metrics getAvg(){
        return new Metrics(
            airTemp.getAvg(),
            atmosphericPressure.getAvg(),
            gustSpeed.getAvg(),
            precipitation.getAvg(),
            relativeHumidity.getAvg(),
            solar.getAvg(),
            strikeDistance.getAvg(),
            strikes.getAvg(),
            vapourPressure.getAvg(),
            windDirection.getAvg(),
            windSpeed.getAvg()
        );
    }
}
