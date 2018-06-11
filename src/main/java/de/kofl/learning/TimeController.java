package de.kofl.learning;

import de.kofl.learning.google.ConnectionPool;
import de.kofl.learning.google.TravelConnection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeController {

    private ConnectionPool pool;

    public TimeController() {
        pool = new ConnectionPool("Kielerstrasse 442", "Hamburg Hauptbahnhof");
    }

    @RequestMapping("/time")
    public TravelConnection getTime() {
        pool.setUpdateInterval(3);
        return pool.getTravelConnections().get(0);
    }
}
