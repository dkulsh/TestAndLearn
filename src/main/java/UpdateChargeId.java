import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UpdateChargeId {

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    public static void main(String[] args) throws Exception {

        UpdateChargeId obj = new UpdateChargeId();

        try(BufferedReader b = new BufferedReader(new FileReader(new File("/Users/deep.kulshreshtha/Downloads/ResGuests_Rafi.txt")))) {

            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                System.out.println(readLine);

                obj.sendPost(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendGet(String reservationGuestId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://prod.virginvoyages.com/svc/dxpcore/reservationguests/" + reservationGuestId))
                .setHeader("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55aWQiOiJiMzVhMzI1YS0wMzZhLTRhZDQtODgzNi02NDY5MjNlNjAxZGUiLCJzY29wZSI6WyJyZWFkIiwidHJ1c3QiLCJ3cml0ZSJdLCJleHAiOjE1ODM0NzM5MzksInRva2VuVHlwZSI6ImNsaWVudFRva2VuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiI0ZGVmNzhlMy0wZmNkLTQ4NTUtOGU3NC05YjBjYzNjNTA1YmIiLCJjbGllbnRfaWQiOiJiM2MyYjA3Yy04NjkwLTExZTktYmQ4ZS0wYTFhNDI2MWU5NjIifQ.fXFZc4n46kGAvW3UrqQubwe4bGGupM-iNSP5-4lLZYRqoacu4sh3pJXeHbJPNdbmnKGrbFhM4awN8iWIgQ14x-awMO9wJjAyos-gMkHTSCVNk51FBFm16S1IDTqtEV5IQg3xbpzwh3SbckDoW7q2GrqvDs_v1TxuPBrpsV1MAhVb_lYZ3nwZ47rxvb4u6VJ5kwtCRgpfTps8MW9tKefwPuiBydgMjB_xKGscwYjt4k3F7wPXJ87EsuQHJHnq9tHgIhfBRnLrrr1Yftyd_YG2wTVyvY2iLijRmE_9cY7ZXyWmxJAgPFNGo1PbrSpFeG_Y7MG7V7mEGtkkEed8XsQ8Tw")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

    private void sendPost(String reservationGuestId) throws IOException, InterruptedException {

//        Folio folio = new Folio(accesscardNumber);
//        Folios folios = new Folios(List.of(folio));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://prod.virginvoyages.com/svc/dxpcore/reservationguests/" + reservationGuestId + "/resetaccesscardnumber"))
                .method("POST", HttpRequest.BodyPublishers.ofString("{}"))
                .header("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55aWQiOiJiMzVhMzI1YS0wMzZhLTRhZDQtODgzNi02NDY5MjNlNjAxZGUiLCJzY29wZSI6WyJyZWFkIiwidHJ1c3QiLCJ3cml0ZSJdLCJleHAiOjE1ODM0NzM5MzksInRva2VuVHlwZSI6ImNsaWVudFRva2VuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiI0ZGVmNzhlMy0wZmNkLTQ4NTUtOGU3NC05YjBjYzNjNTA1YmIiLCJjbGllbnRfaWQiOiJiM2MyYjA3Yy04NjkwLTExZTktYmQ4ZS0wYTFhNDI2MWU5NjIifQ.fXFZc4n46kGAvW3UrqQubwe4bGGupM-iNSP5-4lLZYRqoacu4sh3pJXeHbJPNdbmnKGrbFhM4awN8iWIgQ14x-awMO9wJjAyos-gMkHTSCVNk51FBFm16S1IDTqtEV5IQg3xbpzwh3SbckDoW7q2GrqvDs_v1TxuPBrpsV1MAhVb_lYZ3nwZ47rxvb4u6VJ5kwtCRgpfTps8MW9tKefwPuiBydgMjB_xKGscwYjt4k3F7wPXJ87EsuQHJHnq9tHgIhfBRnLrrr1Yftyd_YG2wTVyvY2iLijRmE_9cY7ZXyWmxJAgPFNGo1PbrSpFeG_Y7MG7V7mEGtkkEed8XsQ8Tw")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

    class Folios implements Serializable {

        public Folios(List<Folio> folios) {
            this.folios = folios;
        }

        @SerializedName("folios")
        @Expose
        private List<Folio> folios = null;
        private final static long serialVersionUID = 8147575777085303759L;

        public List<Folio> getFolios() {
            return folios;
        }

        public void setFolios(List<Folio> folios) {
            this.folios = folios;
        }
    }

    class Folio implements Serializable {
        public Folio(String accessCardNumber) {
            this.accessCardNumber = accessCardNumber;
        }

        @SerializedName("accessCardNumber")
        @Expose
        private String accessCardNumber;
        private final static long serialVersionUID = 1943608003088589805L;

        public String getAccessCardNumber() {
            return accessCardNumber;
        }

        public void setAccessCardNumber(String accessCardNumber) {
            this.accessCardNumber = accessCardNumber;
        }
    }
}
