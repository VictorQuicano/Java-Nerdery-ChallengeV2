package ChallengeClasses.Metrics;

public class MetricAVG {
    private Double sum = 0.0;
    private Integer count = 0;

    public MetricAVG() {};

    public MetricAVG(Double init) {
        if (init != null) {
            sum = init;
            count = 1;
        }
    }

    public Double getAvg() {
        if (count == 0){
            return 0.0;
        }
        return sum/count;
    }
    public void addMetric(Double newMetric){

        sum += (newMetric != null) ? newMetric : 0.0;
        count ++;
    }
}
