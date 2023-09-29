class Statistics {
    private final int serve;
    private final int leave;
    private final double waitTime;

    Statistics() {
        this.serve = 0;
        this.leave = 0;
        this.waitTime = 0.0;
    }

    Statistics(int serve, int leave, double waitTime) {
        this.serve = serve;
        this.leave = leave;
        this.waitTime = waitTime;
    }

    Statistics doStats(Event event) {
        String s = event.toString();

        if (s.contains("serves")) {
            return new Statistics(this.serve + 1, this.leave,
                    this.waitTime + event.getEventTime() - event.getCustomer().getArrivalTime());

        } else if (s.contains("leaves")) {

            return new Statistics(this.serve, this.leave + 1, this.waitTime);
        } else {
            return this;
        }
    }

    @Override
    public String toString() {
        return String.format("[%.3f %d %d]", 
                this.waitTime / (double) this.serve, this.serve, this.leave);
    }
}
