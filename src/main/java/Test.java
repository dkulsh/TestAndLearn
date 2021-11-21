import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import entity.SimplePositionBean;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    private static SSHClient client;
    private static SFTPClient sftpClient;

    public static void main(String[] args) throws FileNotFoundException {

//        Type REVIEW_TYPE = new TypeToken<List<Reservation>>() {}.getType();
//        Gson gson = new Gson();
//        JsonReader reader1 = new JsonReader(new FileReader("/Users/deep.kulshreshtha/Downloads/SwResProd/NullClientId_Reservations_Filtered.json"));
//        List<Reservation> data1 = gson.fromJson(reader1, REVIEW_TYPE);

        System.out.println("0/60 0 0 ? * * *");
    }

//    public static void main(String[] args) throws IOException {
//
//        Type REVIEW_TYPE = new TypeToken<List<Reservation>>() {}.getType();
//        Gson gson = new Gson();
//        JsonReader reader1 = new JsonReader(new FileReader("/Users/deep.kulshreshtha/Downloads/SwResProd/NullClientId_Reservations.json"));
//        List<Reservation> data1 = gson.fromJson(reader1, REVIEW_TYPE); // contains the whole reviews list
//
////        Map<String, List<String>> map = new HashMap<>();
//
////        for (Reservation reservation : data){
////
////            if (reservation.getSailors().size() <= 1) { continue; }
////
////            Map<String, List<Reservation.Sailor>> sailors = reservation
////                    .getSailors()
////                    .stream()
////                    .filter(sailor -> sailor.getClientID() != null)
////                    .collect(Collectors.groupingBy(Reservation.Sailor::getClientID));
////
////            sailors
////                    .entrySet()
////                    .stream()
////                    .filter(entry -> entry.getValue().size() > 1)
////                    .forEach(entry -> {
////                        if (map.containsKey(entry.getKey())) {
////                            map.get(entry.getKey()).add(reservation.getReservationID());
////                        } else {
////                            List<String> list = new ArrayList<>();
////                            list.add(reservation.getReservationID());
////                            map.put(entry.getKey(), list);
////                        }
////                    } );
////        }
//
//        List<Reservation> list1 = data1.stream()
//                .map(reservation -> {
//                    List<Reservation.Sailor> sailors = reservation.getSailors().stream().
//                            filter(sailor -> sailor.getClientID() != null).collect(Collectors.toList());
//
//                    reservation.setSailors(sailors);
//                    return reservation;
//                } )
//                .collect(Collectors.toList());
//
////        for (Reservation reservation : list1){
////
////            Map<String, List<Reservation.Sailor>> sailors = reservation
////                    .getSailors()
////                    .stream()
////                    .collect(Collectors.groupingBy(Reservation.Sailor::getClientID));
////
////            if (sailors
////                    .entrySet()
////                    .stream()
////                    .anyMatch(entry -> entry.getValue().size() > 1)) {
////                filteredList.add(reservation);
////            }
////        }
//
//        try (Writer writer = new FileWriter("/Users/deep.kulshreshtha/Downloads/SwResProd/NullClientId_Reservations_Filtered.json")) {
//            new GsonBuilder().setPrettyPrinting().create().toJson(list1, writer);
//        }
//
//        System.out.println();
//
//    }

//    public static void main(String[] args) {
//
//        String REMOTE_DIRECTORY = "/dev/";
//
//        try {
//
//            if (client == null || !client.isConnected()) {
//                client = new SSHClient();
//                client.addHostKeyVerifier(new PromiscuousVerifier());
//                client.connect("ftp.itsc.rrd.com");
//                client.authPassword("virgint", "MW5wc6CP");
//
//                sftpClient = client.newSFTPClient();
//            }
//
//            sftpClient.put("/Users/deep.kulshreshtha/Downloads/VVWOF2953920191210090418.csv",
//                    REMOTE_DIRECTORY + "VVWOF2953920191210090418.csv");
//
//            System.out.println(" placed");
//        } catch(Exception e){
//
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) throws IOException {
//
//
////        READING CSV
//
//        Class clazz = SimplePositionBean.class;
//
//        ColumnPositionMappingStrategy<SimplePositionBean> ms = new ColumnPositionMappingStrategy<>();
//        ms.setType(clazz);
//
//        Path path = Paths.get("/Users/deep.kulshreshtha/Downloads/twoColumn.csv");
//        Reader reader = Files.newBufferedReader(path);
//
//        List<SimplePositionBean> beanList = new CsvToBeanBuilder(reader)
//                .withMappingStrategy(ms)
//                .build()
//                .parse();
//
//        System.out.println(beanList);
//
//        reader.close();
//    }

//    private void writeCSV() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//
//        Writer writer = new FileWriter("/Users/deep.kulshreshtha/Downloads/twoColumn.csv");
//
//        StatefulBeanToCsv<SimplePositionBean> sbc = new StatefulBeanToCsvBuilder<SimplePositionBean>(writer)
//                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
//                .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
//                .withApplyQuotesToAll(false)
//                .build();
//
////        List<SimplePositionBean> list = new ArrayList<>();
////        list.add(new SimplePositionBean("1,1", "1"));
////        list.add(new SimplePositionBean("2", "2"));
////        list.add(new SimplePositionBean("3", "3"));
////        list.add(new SimplePositionBean("4", "4"));
////
////        sbc.write(list);
//        writer.close();
//    }
}