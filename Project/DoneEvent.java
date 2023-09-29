import java.util.Optional;

class DoneEvent implements Event {
    private final Customer customer;
    private final Server server;
    private final double time;

    DoneEvent(Customer customer, Server server, double time) {
        this.customer = customer;
        this.server = server;
        this.time = time;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop listOfServers) { 

        ImList<Server> servers = new ImList<Server>();  
        // create a list of updated servers

        Server updatedServer = new Server(this.server.getID(), 
                this.time + this.customer.getRestTime(),
                listOfServers.getUpdated(this.server).leaveQueue(), 
                this.server.isAvailable());

        for (Server x : listOfServers.getList()) {
            if (x.getID() == this.server.getID()) {
                servers = servers.add(updatedServer);

            } else {
                servers = servers.add(x);
            }
        }

        Shop updateShop = new Shop(servers);
        Event restEvent = new ServerRestEvent(updatedServer, this.customer, 
                this.time + this.customer.getRestTime()); 
        return new Pair<Optional<Event>,Shop>(Optional.<Event>of(restEvent), updateShop);
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
    public String toString() {
        return String.format("%.3f %s done serving by %d", this.getEventTime(), 
                customer.toString(), this.server.getID());
    }
}

