import java.util.function.Supplier;

class Customer {
    private final int id;
    private final double arrivalTime;
    private final Lazy<Double> serviceTime;
    private final Lazy<Double> restTime;

    Customer(int id, Pair<Double,Supplier<Double>> pairofTimes, 
             Supplier<Double> restTime) {
        this.id = id;
        this.arrivalTime = pairofTimes.first();
        this.serviceTime = Lazy.<Double>of(pairofTimes.second());
        this.restTime = Lazy.<Double>of(restTime);
    }

    int getID() {
        return this.id;
    }

    double getServiceTime() {
        return this.serviceTime.get();
    }

    double getArrivalTime() {
        return this.arrivalTime;
    }

    double getRestTime() {
        return this.restTime.get();
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
