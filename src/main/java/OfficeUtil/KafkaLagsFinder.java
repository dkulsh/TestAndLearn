package OfficeUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class KafkaLagsFinder {

    Map<String, Map<String, String>> servers = Map.of(
//            "Dev", Map.of("Shore", "10.9.100.157:9092,10.9.100.142:9092,10.9.100.37:9092",
//                    "Ship", "10.9.100.157:9092,10.9.100.142:9092,10.9.100.37:9092"),
//            "Int", Map.of("Shore", "10.9.100.157:9092,10.9.100.142:9092,10.9.100.37:9092",
//                    "Ship", "10.2.52.146:9092,10.2.52.162:9092,10.2.52.148:9092"),
//            "Cert", Map.of("Shore", "10.9.100.157:9092,10.9.100.142:9092,10.9.100.37:9092",
//                    "Ship", "10.2.52.146:9092,10.2.52.162:9092,10.2.52.148:9092"),
            "Prod", Map.of(
//                    "Shore", "10.15.12.38:9092,10.15.13.172:9092,10.15.2.58:9092",
                    "Ship", "10.101.220.213:9092,10.101.220.214:9092,10.101.220.216:9092")
    );

    Map<String, List<String>> groups = Map.of(
//            "Shore", List.of("linksocialnetworktoseaware", "seaware_wearable_event-group"),
            "Ship", List.of("dxp-cabin-doorlock", "reviewask-events-group", "okta-teammember")
    );

    public static void main(String[] args) {

        KafkaLagsFinder finder = new KafkaLagsFinder();
        finder.findLags();
    }

    private void findLags() {

            servers.entrySet().stream().flatMap(e -> e.getValue().entrySet().stream()).forEach(s -> {

                groups.get(s.getKey()).stream().forEach(group -> {
                    String command = buildCommand(s.getValue(), group);

                        try {
                            Process process = Runtime.getRuntime().exec(command);
                            printResults(process);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                });
            });
    }

    private String buildCommand(String server, String group) {

        return "kafka-consumer-groups --bootstrap-server " + server + " --describe --group " + group + " --timeout 100000";
    }

    private synchronized void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        Skip header
        reader.readLine();

        String line = "";
        while ((line = reader.readLine()) != null) {

//            System.out.println(line);

            List<String> lagResult = Arrays.asList(line.split(" ")).stream().filter(s -> s.length() != 0).collect(toList());
            String lags = lagResult.get(5);
            String topic = lagResult.get(1);
            String partition = lagResult.get(2);

//            String lags = line.substring(105, 121).trim();
////            if (! "0".equals(lags)) {
//                String topic = line.substring(27, 62).trim();
//                String partition = line.substring(62, 73).trim();
                System.out.println("Topic : " + topic + " : Partition " + partition + " has " + lags + " lags");
//            }

        }
    }

}
