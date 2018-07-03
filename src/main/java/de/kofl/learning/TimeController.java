package de.kofl.learning;

import de.kofl.learning.google.ConnectionPool;
import de.kofl.learning.google.TravelConnection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@RestController
public class TimeController {

    private ConnectionPool pool;

    public TimeController() {
        pool = new ConnectionPool("Kielerstrasse 442", "Hamburg Hauptbahnhof");
        pool.setUpdateInterval(2);
        pool.updateConnection();
    }

    @RequestMapping("/time")
    public TravelConnection getTime() {
        if (pool.getTravelConnections().size() > 0)
            return pool.getTravelConnections().get(0);
        return TravelConnection.builder()
                .departure(new Date())
                .arrival(new Date())
                .instructions(Collections.singletonList("Stay home"))
                .lines(Collections.singletonList("NOTHING"))
                .build();
    }
}
