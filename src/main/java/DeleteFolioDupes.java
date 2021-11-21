import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;

public class DeleteFolioDupes {

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    public static void main(String[] args) throws Exception {

        DeleteFolioDupes obj = new DeleteFolioDupes();

        try(BufferedReader b = new BufferedReader(new FileReader(new File("/Users/deep.kulshreshtha/Downloads/DupAccessCards.txt")))) {

            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                System.out.println(readLine);

                obj.getDetails(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDetails(String reservationGuestId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://application.scl.virginvoyages.com/svc/dxpcore/reservationguests/" + reservationGuestId))
                .setHeader("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55aWQiOiJiMzVhMzI1YS0wMzZhLTRhZDQtODgzNi02NDY5MjNlNjAxZGUiLCJzY29wZSI6WyJyZWFkIiwidHJ1c3QiLCJ3cml0ZSJdLCJleHAiOjE1ODM0NzM5MzksInRva2VuVHlwZSI6ImNsaWVudFRva2VuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiI0ZGVmNzhlMy0wZmNkLTQ4NTUtOGU3NC05YjBjYzNjNTA1YmIiLCJjbGllbnRfaWQiOiJiM2MyYjA3Yy04NjkwLTExZTktYmQ4ZS0wYTFhNDI2MWU5NjIifQ.fXFZc4n46kGAvW3UrqQubwe4bGGupM-iNSP5-4lLZYRqoacu4sh3pJXeHbJPNdbmnKGrbFhM4awN8iWIgQ14x-awMO9wJjAyos-gMkHTSCVNk51FBFm16S1IDTqtEV5IQg3xbpzwh3SbckDoW7q2GrqvDs_v1TxuPBrpsV1MAhVb_lYZ3nwZ47rxvb4u6VJ5kwtCRgpfTps8MW9tKefwPuiBydgMjB_xKGscwYjt4k3F7wPXJ87EsuQHJHnq9tHgIhfBRnLrrr1Yftyd_YG2wTVyvY2iLijRmE_9cY7ZXyWmxJAgPFNGo1PbrSpFeG_Y7MG7V7mEGtkkEed8XsQ8Tw")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Folio folio = new Gson().fromJson(response.body(), Folio.class);

        sendPatch(folio, reservationGuestId);

    }

    private void sendPatch(Folio folio, String reservationGuestId) throws IOException, InterruptedException {

        DeleteFolioDupes.Folio.Folio_ folio_ = folio.getFolios().stream().max(Comparator.comparing(f -> f.getAccessCardNumber())).get();

        folio.getFolios().removeIf(ff -> ff.getAccessCardNumber().equals(folio_.getAccessCardNumber()));
        folio.getFolios().forEach(ff -> ff.setIsDeleted(Boolean.TRUE));

        String body = new Gson().toJson(folio);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://application.scl.virginvoyages.com/svc/dxpcore/reservationguests/" + reservationGuestId))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
                .header("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55aWQiOiJiMzVhMzI1YS0wMzZhLTRhZDQtODgzNi02NDY5MjNlNjAxZGUiLCJzY29wZSI6WyJyZWFkIiwidHJ1c3QiLCJ3cml0ZSJdLCJleHAiOjE1ODM0NzM5MzksInRva2VuVHlwZSI6ImNsaWVudFRva2VuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiI0ZGVmNzhlMy0wZmNkLTQ4NTUtOGU3NC05YjBjYzNjNTA1YmIiLCJjbGllbnRfaWQiOiJiM2MyYjA3Yy04NjkwLTExZTktYmQ4ZS0wYTFhNDI2MWU5NjIifQ.fXFZc4n46kGAvW3UrqQubwe4bGGupM-iNSP5-4lLZYRqoacu4sh3pJXeHbJPNdbmnKGrbFhM4awN8iWIgQ14x-awMO9wJjAyos-gMkHTSCVNk51FBFm16S1IDTqtEV5IQg3xbpzwh3SbckDoW7q2GrqvDs_v1TxuPBrpsV1MAhVb_lYZ3nwZ47rxvb4u6VJ5kwtCRgpfTps8MW9tKefwPuiBydgMjB_xKGscwYjt4k3F7wPXJ87EsuQHJHnq9tHgIhfBRnLrrr1Yftyd_YG2wTVyvY2iLijRmE_9cY7ZXyWmxJAgPFNGo1PbrSpFeG_Y7MG7V7mEGtkkEed8XsQ8Tw")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

//        System.out.println(response.statusCode());

//        System.out.println(response.body());
    }

    public class Folio implements Serializable
    {

        @SerializedName("folios")
        @Expose
        private List<Folio_> folios = null;
        private final static long serialVersionUID = 887562378295861712L;

        public List<Folio_> getFolios() {
            return folios;
        }

        public void setFolios(List<Folio_> folios) {
            this.folios = folios;
        }

        public class Folio_ implements Serializable
        {

            @SerializedName("folioId")
            @Expose
            private String folioId;
            @SerializedName("reservationGuestId")
            @Expose
            private String reservationGuestId;
            @SerializedName("accessCardNumber")
            @Expose
            private String accessCardNumber;
            @SerializedName("isDeleted")
            @Expose
            private Boolean isDeleted;
            private final static long serialVersionUID = 4019114557775995547L;

            public String getFolioId() {
                return folioId;
            }

            public void setFolioId(String folioId) {
                this.folioId = folioId;
            }

            public String getReservationGuestId() {
                return reservationGuestId;
            }

            public void setReservationGuestId(String reservationGuestId) {
                this.reservationGuestId = reservationGuestId;
            }

            public String getAccessCardNumber() {
                return accessCardNumber;
            }

            public void setAccessCardNumber(String accessCardNumber) {
                this.accessCardNumber = accessCardNumber;
            }

            public Boolean getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(Boolean isDeleted) {
                this.isDeleted = isDeleted;
            }

        }

    }

}
