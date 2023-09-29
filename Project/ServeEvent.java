import java.util.Optional;

class ServeEvent implements Event {
    private final Server server;
    private final Customer customer;
    private final double time;

    ServeEvent(Server server, Customer customer, double time) {
        this.server = server;
        this.customer = customer;
        this.time = time;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop listOfServers) {

        //create list of server
        ImList<Server> listServers = new ImList<Server>();

        // update Server
        Server updatedServer = new Server(this.server.getID(), 
                this.time + this.customer.getServiceTime(),
                listOfServers.getUpdated(this.server).getQueue(), 
                false); 

        for (Server x : listOfServers.getList()) {  //updates list of servers
            if (x.getID() == this.server.getID()) {
                listServers = listServers.add(updatedServer);
            } else {
                listServers = listServers.add(x);
            }
        }

        Shop updateShop = new Shop(listServers);

        Event doneEvent = new DoneEvent(this.customer, updatedServer, 
                this.customer.getServiceTime() + this.time);
        return new Pair<Optional<Event>,Shop>(Optional.<Event>of(doneEvent), updateShop);
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
        return String.format("%.3f %s%s", this.time, 
                this.customer.toString(), this.server.toString());
    }
}
