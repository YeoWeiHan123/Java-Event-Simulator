import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    
    @Override
    public int compare(Event x, Event y) {
        double d1 = x.getEventTime();
        double d2 = y.getEventTime();
        int value = Double.compare(d1, d2);
        if (value == 0) {
            int i1 = x.getCustomer().getID();
            int i2 = y.getCustomer().getID();
            return Integer.compare(i1, i2);
        }
        return value;
    }
}
