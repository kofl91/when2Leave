package de.kofl.learning.google;

import de.kofl.learning.google.GoogleAPI;
import de.kofl.learning.google.TravelConnection;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Data
public class ConnectionPool {

    private List<TravelConnection> travelConnections = new ArrayList<>();
    private GoogleAPI googleAPI;
    private final String origin;
    private final String destination;
    protected Runnable updateConnections = new Runnable() {
        public void run() {
            updateConnection();
        }
    };
    private ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

    public ConnectionPool(String origin, String destination) {
        googleAPI = new GoogleAPI();
        this.origin = origin;
        this.destination = destination;
    }

    public void updateConnection() {
        try {
            TravelConnection connection = googleAPI.getConnection(origin, destination);
            addConnection(connection);
            discardOldConnections(new Date());
        } catch (Exception e) {
            System.err.println("An error occured while trying to get new connections: " + e.getMessage());
        }
    }


    void discardOldConnections(Date discardDate) {
        travelConnections = travelConnections.stream().filter(connection -> {
            return connection.getDeparture().compareTo(discardDate) > 0;
        }).collect(Collectors.toList());
    }

    void addConnection(TravelConnection connection) {
        if (!travelConnections.contains(connection)) {
            travelConnections.add(connection);
        }
    }

    public void setUpdateInterval(int minuteInterval) {
        exec.scheduleAtFixedRate(updateConnections, 0, minuteInterval, TimeUnit.MINUTES);
    }

    public void stopUpdate() {
        exec.shutdown();
        System.out.println("das");
    }
}
