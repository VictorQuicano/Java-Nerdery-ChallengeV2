package ChallengeClasses;

import ChallengeClasses.Metrics.Metrics;

public class Record {
    private Metadata metadataInfo;
    private Metrics metrics;

    @Override
    public String toString() {
        StringBuilder builderString = new StringBuilder();
        String divisor = "-".repeat(40);

        builderString.append(divisor).append("\n");
        builderString.append(metadataInfo).append("\n");
        builderString.append(divisor).append("\n");
        builderString.append(metrics);

        return builderString.toString();
    }


    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public Metadata getMetadataInfo() {
        return metadataInfo;
    }

    public void setMetadataInfo(Metadata metadataInfo) {
        this.metadataInfo = metadataInfo;
    }
}
