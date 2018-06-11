package de.kofl.learning.google;

import com.google.maps.model.DirectionsLeg;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class TravelConnection {
    List<String> lines;
    Date departure;
    Date arrival;
    List<String> instructions;

    public static TravelConnection fromLeg(DirectionsLeg leg) {
        TravelConnectionBuilder builder = builder();
        builder.arrival(leg.arrivalTime.toDate());
        builder.departure(leg.departureTime.toDate());
        builder.instructions(Arrays.stream(leg.steps).map(s -> s.htmlInstructions).collect(Collectors.toList()));
        builder.lines(Arrays.stream(leg.steps).map(s -> {
            String stepDescription = "";
            if ((s.travelMode != null) && !"TRANSIT".equals(s.travelMode.name())) {
                stepDescription += s.travelMode.name();
            }
            if (s.transitDetails != null) {
                stepDescription += s.transitDetails.line.shortName;
            }
            return stepDescription;
        }).collect(Collectors.toList()));

        return builder.build();
    }
}
