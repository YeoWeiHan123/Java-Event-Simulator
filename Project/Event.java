import java.util.Optional;

interface Event {

    Pair<Optional<Event>, Shop> execute(Shop manyServers);

    public double getEventTime();

    public Customer getCustomer();
}
