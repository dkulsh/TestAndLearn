import java.time.Instant;
import java.time.ZoneId;

public class LocalDateTime {

    public static void main(String[] args) {

        final Instant startTimeInstant = Instant.now();
        final java.time.LocalDateTime startTime = java.time.LocalDateTime.ofInstant(startTimeInstant, ZoneId.systemDefault())
                .withHour(0)
                .withMinute(1);

        System.out.println(startTime);
    }
}
