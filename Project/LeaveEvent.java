import java.util.Optional;

class LeaveEvent implements Event {
    private final Customer customer;

    LeaveEvent(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop listOfServers) { 
        return new Pair<Optional<Event>,Shop>(Optional.<Event>of(this), listOfServers);
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public double getEventTime() {
        return this.customer.getArrivalTime();
    }

    @Override
    public String toString() {
        String s = String.format("%.3f ", this.customer.getArrivalTime());
        return String.format(s + customer.toString() + " leaves");
    }
}
