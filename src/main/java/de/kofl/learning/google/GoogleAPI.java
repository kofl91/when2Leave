package de.kofl.learning.google;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import de.kofl.learning.ResourcesConfiguration;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Date;

public class GoogleAPI {

    private final GeoApiContext context;



    public GoogleAPI() {
        context = new GeoApiContext.Builder()
                .apiKey("AIzaSyABCVGPQhv39-DQdyQ50UCSiz9GUqmFlm0")
                .build();
    }

    public TravelConnection getConnection(String origin, String destination) throws InterruptedException, ApiException, IOException {
        DirectionsResult result = DirectionsApi
                .getDirections(context, origin, destination)
                .mode(TravelMode.TRANSIT)
                .await();
//        logConnection(result);

        DirectionsLeg leg = result.routes[0].legs[0];

        return TravelConnection.fromLeg(leg);

    }

    private void logConnection(DirectionsResult connection) {
        for (DirectionsRoute route : connection.routes) {
            logRoute(route);
        }
    }

    public void logRoute(DirectionsRoute route) {
        for (DirectionsLeg leg : route.legs) {
            System.out.println(leg.startAddress);
            System.out.println(leg.endAddress);
            System.out.println(leg.departureTime);
            System.out.println(leg.arrivalTime);
            System.out.println(leg.duration);
            for (DirectionsStep step : leg.steps) {
                if (step.htmlInstructions != null) {
                    System.out.println(step.htmlInstructions);
                }
                if (step.travelMode != null) {
                    System.out.println(step.travelMode.name());
                }
                if (step.transitDetails != null) {
                    System.out.println(step.transitDetails.line.shortName);
                }
            }
        }
    }
}
