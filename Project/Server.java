class Server {
    private final double availableTime;
    private final boolean available;
    private final int serverID;
    private final ImList<Customer> queue;

    Server(int serverID) {
        this.serverID = serverID;
        this.available = true;
        this.availableTime = 0.0;
        this.queue = new ImList<Customer>();
    }

    Server(int serverID, double availableTime,
            ImList<Customer> queue, boolean available) {
        this.serverID = serverID;
        this.availableTime = availableTime;
        this.available = available;
        this.queue = queue;
    }

    ImList<Customer> leaveQueue() {
        if (this.queue.size() == 0) {
            return this.queue;
        }
        return this.queue.remove(0);
    }

    ImList<Customer> getQueue() {
        return this.queue;   
    }

    int getID() {
        return this.serverID;
    }

    int getQueueSize() {
        return this.queue.size();
    }

    double getNextAvailable() {
        return this.availableTime;
    }

    boolean isAvailable() {
        return this.available;
    }

    @Override 
    public String toString() {
        return String.format(" serves by " + this.serverID);
    }
}
