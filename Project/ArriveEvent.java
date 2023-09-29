import java.util.Optional;

class ArriveEvent implements Event {
    private final Customer customer;
    private final double arrivalTime;
    private final int qmax;

    ArriveEvent(Customer customer, int qmax) {
        this.customer = customer;
        this.arrivalTime = customer.getArrivalTime();
        this.qmax = qmax;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public double getEventTime() {
        return this.arrivalTime;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop servers) {

        Event event = new LeaveEvent(this.customer);
        boolean eventDecided = false;

        //loop through server(s) in shop
        for (Server x : servers.getServerList()) {

            //ACCEPT CUSTOMER
            if (x.getNextAvailable() <= this.arrivalTime
                    && x.isAvailable()
                    && !eventDecided
                    && x.getQueueSize() == 0) { //server can serve

                event = new ServeEvent(x, this.customer, this.arrivalTime);
                eventDecided = true;
                    } 
        }

        for (Server x : servers.getServerList()) {
            if (x.isAvailable() == false
                    && !eventDecided
                    && x.getQueueSize() < this.qmax) { //Wait event

                event = new WaitEvent(this.customer, x, this.arrivalTime);
                eventDecided = true;

                    } //ELSE DEFAULT EVENT LEAVE

        }
        return new Pair<Optional<Event>,Shop>(Optional.<Event>of(event), servers);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s arrives", this.arrivalTime, this.customer.toString());

    }
}
