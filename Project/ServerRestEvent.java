import java.util.Optional;

class ServerRestEvent implements Event{
    private final Server server;
    private final double time;
    private final Customer customer;

    ServerRestEvent(Server server, Customer customer, double time) {
        this.server = server;
        this.time = time;
        this.customer = customer;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop listOfServers) {

        ImList<Server> listServers = new ImList<Server>();

        Server updatedServer = new Server(this.server.getID(), 
                this.time,
                listOfServers.getUpdated(this.server).getQueue(), 
                true); 

        for (Server x : listOfServers.getList()) { 
            if (x.getID() == this.server.getID()) {
                listServers = listServers.add(updatedServer);
            } else {
                listServers = listServers.add(x);
            }
        }
        Shop updateShop = new Shop(listServers);

        return new Pair<Optional<Event>,Shop>(Optional.<Event>of(this), updateShop);
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
        return String.format("");
    }
}
