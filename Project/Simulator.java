import java.util.function.Supplier;
import java.util.Optional;

class Simulator {
    private final Shop listOfServers;
    private final int qmax;
    private final PQ<Event> pq;


    Simulator(int numOfServers, int qmax, ImList<Pair<Double,Supplier<Double>>> inputTimes,
            Supplier<Double> restTime) {

        ImList<Server> listOfServers = new ImList<Server>();
        this.qmax = qmax;

        ArriveEvent arrive;
        Customer customer;
        Server server;

        PQ<Event> pq = new PQ<>(new EventComparator());

        for (int serverCounter = 1; serverCounter <= numOfServers; serverCounter++) {
            //server = new Server(serverCounter, restTime);
            server = new Server(serverCounter);
            listOfServers = listOfServers.add(server);
        }
        this.listOfServers = new Shop(listOfServers); // transfer group of server to shop

        //no need to create new customer queue
        for (int cusCounter = 1; cusCounter <= inputTimes.size(); cusCounter++) {

            customer = new Customer(cusCounter, inputTimes.get(cusCounter - 1), restTime);
            arrive = new ArriveEvent(customer, qmax);
            pq = pq.add(arrive);
        }
        this.pq = pq;

    }

    String simulate() {
        // Declare first instead of declaring in the for loop
        PQ<Event> pq = this.pq;
        String s = "";
        Shop listOfServers = this.listOfServers;
        Statistics stats = new Statistics();
        Event event;
        Optional<Event> nextEvent;

        while (!pq.isEmpty()) {
            event = pq.poll().first(); //for every event create other queue
            s = s + event.toString();
            if (!event.toString().equals("")) {
                s = s + "\n";
            }

            stats = stats.doStats(event);
            pq = pq.poll().second();

            nextEvent = event.execute(listOfServers).first();
            listOfServers = event.execute(listOfServers).second();

            //pq = pq.add(nextEvent.get());
            //server = event

            if ( nextEvent.isPresent()
                    && nextEvent.get() != event) {
                pq = pq.add(nextEvent.get());
            }
        }
        return s + stats.toString();

    }

}
