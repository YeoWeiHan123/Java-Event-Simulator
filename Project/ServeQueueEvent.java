//a mix of serveEvent.java and arriveEvent
//
//This is to keep track of incoming customer
//into individual server's queue 
//
import java.util.Optional;

class ServeQueueEvent implements Event {
    private final Server server;
    private final Customer customer;
    private final double time;

    ServeQueueEvent(Server server, Customer customer, double time) {
        this.server = server;
        this.customer = customer;
        this.time = time;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop currentListOfServers) {
        ImList<Server> newListOfServers = new ImList<Server>(); // create new list of server
        Event serveQueueEvent;

        if (this.server.isAvailable()) { 
            // if true serve and remove from customer from wait queue

            Server updatedServer = new Server(this.server.getID(), 
                    this.time, 
                    currentListOfServers.getUpdated(this.server).leaveQueue(), 
                    //remove customer from wait to serve
                    true);

            for (Server x : currentListOfServers.getList()) {  //updates list of servers
                if (x.getID() == this.server.getID()) {
                    newListOfServers = newListOfServers.add(updatedServer);
                } else {
                    newListOfServers = newListOfServers.add(x);
                } 
            } 

            Shop updateShop = new Shop(newListOfServers);
            // returns updated list of Servers to shop

            serveQueueEvent = new ServeEvent(updatedServer, this.customer, this.time);
            return new Pair<Optional<Event>,Shop>(Optional.<Event>of(serveQueueEvent), updateShop);

        } else {

            serveQueueEvent = new ServeQueueEvent(currentListOfServers.getUpdated(this.server),
                    this.customer, 
                    currentListOfServers.getUpdated(this.server).getNextAvailable());

            return new Pair<Optional<Event>,Shop>(Optional.<Event>of(serveQueueEvent), currentListOfServers); 
        }
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
