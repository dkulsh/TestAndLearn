import com.google.gson.Gson;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.jni.Local;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TimeConversion {

    public static void main(String[] args) throws ParseException {

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2021-08-09", formatter);
        LocalDateTime dateTime = date.atStartOfDay();

        System.out.println(dateTime);

        System.out.println(outputFormatter.format(dateTime));
        System.out.println(outputFormatter.format(dateTime.withHour(23).withMinute(59)));

//        String timeString = "2021-04-13T07:16:53";
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        Date parsedUtcDate = format.parse(timeString);
//        System.out.println(DateUtils.addMinutes(parsedUtcDate, -300));
//
//        LocalDateTime localDateTime = LocalDateTime.parse(timeString).plusMinutes(-300);
//        System.out.println(localDateTime);


//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy hh:mm a");
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);

        System.out.println(LocalDate.now().minusDays(45));

//        System.out.println(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

//        System.out.println(dateTimeFormatter.format(new Timestamp(new Date().getTime()).toLocalDateTime()));

    }

//    public static void main(String[] args) throws ParseException {
//
//        System.out.println(LocalTime.of(16, 0));
//
//        String timeString = "2020-11-11T07:38:46";
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        Date parsedUtcDate = format.parse(timeString);
//        System.out.println(DateUtils.addMinutes(parsedUtcDate, -300));
//
//        LocalDateTime localDateTime = LocalDateTime.parse(timeString).plusMinutes(-300);
//        System.out.println(localDateTime);
//
//        System.out.println(localDateTime.toLocalTime().isAfter(LocalTime.of(16, 0)));
//
////        Date.from(LocalDateTime.parse(timeString).atOffset(ZoneOffset.ofTotalSeconds(60 * -300)));
//
////        System.out.println(Date.from(LocalDateTime.parse(timeString).atOffset(ZoneOffset.ofTotalSeconds(60 * -300)).toInstant()));
//
//    }
}
