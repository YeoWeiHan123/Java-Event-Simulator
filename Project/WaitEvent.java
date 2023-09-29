import java.util.Optional;


class WaitEvent implements Event {
    private final Customer customer;
    private final Server server;
    private final double time;

    WaitEvent(Customer customer, Server server, double time) {
        this.customer = customer;
        this.server = server;
        this.time = time;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public double getEventTime() {
        return this.time;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop listOfServers) {

        ImList<Server> servers = new ImList<Server>();  // create a list of updated servers
        Server updatedServer = new Server(this.server.getID(), this.server.getNextAvailable(), 
                listOfServers.getUpdated(this.server).getQueue().add(this.customer), 
                this.server.isAvailable());

        for (Server x : listOfServers.getList()) {
            if (x.getID() == this.server.getID()) {
                servers = servers.add(updatedServer);
            } else {
                servers = servers.add(x);
            }
        }

        Shop updateShop = new Shop(servers);
        Event event = new ServeQueueEvent(updatedServer, this.customer,  
                this.server.getNextAvailable()); 
        return new Pair<Optional<Event>,Shop>(Optional.<Event>of(event), updateShop);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s waits at %s", this.time, customer.toString(),
                this.server.getID());

    }
}
