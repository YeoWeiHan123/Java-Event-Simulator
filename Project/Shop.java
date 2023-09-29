class Shop {
    private final ImList<Server> listOfServers;

    Shop(ImList<Server> listOfServers) {
        this.listOfServers = listOfServers;
    }

    ImList<Server> getServerList() {
        return this.listOfServers;
    }

    Server getUpdated(Server server) {
        Server updatedServer = server;
        for (Server x : this.listOfServers) {
            if (x.getID() == server.getID()) {
                updatedServer = x;
            }
        }
        return updatedServer;
    }

    ImList<Server> getList() {
        return this.listOfServers;
    }

    @Override
    public String toString() {
        return this.listOfServers.toString();
    }

}
