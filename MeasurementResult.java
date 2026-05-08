public class MeasurementResult {
    public final long timeNano;
    public final double timeMicro;
    public final double timeMilli;
    public final int iterations;

    public MeasurementResult(long timeNano, int iterations) {
        this.timeNano = timeNano;
        this.timeMicro = timeNano / 1000.0;
        this.timeMilli = timeNano / 1_000_000.0;
        this.iterations = iterations;
    }

    @Override
    public String toString() {
        return String.format("время=%.3f мкс (%.3f мс), итераций=%d",
                timeMicro, timeMilli, iterations);
    }
}
