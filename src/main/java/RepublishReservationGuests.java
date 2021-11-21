import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RepublishReservationGuests {

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    public static void main(String[] args) throws Exception {

        RepublishReservationGuests obj = new RepublishReservationGuests();

        try(BufferedReader b = new BufferedReader(new FileReader(new File("/Users/deep.kulshreshtha/Downloads/RepublishResGuestEvents.txt")))) {

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
                .setHeader("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55aWQiOiJiMzVhMzI1YS0wMzZhLTRhZDQtODgzNi02NDY5MjNlNjAxZGUiLCJzY29wZSI6WyJyZWFkIiwidHJ1c3QiLCJ3cml0ZSJdLCJleHAiOjE1ODQ3MjI3NTMsInRva2VuVHlwZSI6ImNsaWVudFRva2VuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiJmNzA1MTM5MS00Mjk3LTQzOTgtYWI0Zi1mY2YwMDEyZDhkZTUiLCJjbGllbnRfaWQiOiJiM2MyYjA3Yy04NjkwLTExZTktYmQ4ZS0wYTFhNDI2MWU5NjIifQ.d0nv2OPUqNP8UKqU0lwvdw7tD77v_Dcwh2mX8UAM3knEtnQGE_L2_qpPbedI2kdsr3iE3fYm5OxzyMck_xf90ApCsO09IiDoftQ-HNr5l3ql9ALDVI2saSOONIcF39YfTCzQuibS_jPZ_kW0K2QiqReQ7doVT9Qr5h3dDsyVLsjveoefGOeVJkHqG2olNCH3HdnObK7RE5HPvAAJWoClc4jkUXMDU4xjZ3rFOClqZJUrTE-MUQXYdzY6T6XdyMijgEkWX4p3rAsASU8nRe-HoV-5CO1mVLTSdlyNlqMD4wX8AfqkNFxOQi2Ax5KdSsf_usf7Yt_FohI_uwWXv3hmFg")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ResGuest resGuest = new Gson().fromJson(response.body(), ResGuest.class);

        sendPatch(resGuest, reservationGuestId);

    }

    private void sendPatch(ResGuest resGuest, String reservationGuestId) throws IOException, InterruptedException {

        String body = new Gson().toJson(resGuest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://application.scl.virginvoyages.com/svc/dxpcore/reservationguests/" + reservationGuestId))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
                .header("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55aWQiOiJiMzVhMzI1YS0wMzZhLTRhZDQtODgzNi02NDY5MjNlNjAxZGUiLCJzY29wZSI6WyJyZWFkIiwidHJ1c3QiLCJ3cml0ZSJdLCJleHAiOjE1ODQ3MjI3NTMsInRva2VuVHlwZSI6ImNsaWVudFRva2VuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiJmNzA1MTM5MS00Mjk3LTQzOTgtYWI0Zi1mY2YwMDEyZDhkZTUiLCJjbGllbnRfaWQiOiJiM2MyYjA3Yy04NjkwLTExZTktYmQ4ZS0wYTFhNDI2MWU5NjIifQ.d0nv2OPUqNP8UKqU0lwvdw7tD77v_Dcwh2mX8UAM3knEtnQGE_L2_qpPbedI2kdsr3iE3fYm5OxzyMck_xf90ApCsO09IiDoftQ-HNr5l3ql9ALDVI2saSOONIcF39YfTCzQuibS_jPZ_kW0K2QiqReQ7doVT9Qr5h3dDsyVLsjveoefGOeVJkHqG2olNCH3HdnObK7RE5HPvAAJWoClc4jkUXMDU4xjZ3rFOClqZJUrTE-MUQXYdzY6T6XdyMijgEkWX4p3rAsASU8nRe-HoV-5CO1mVLTSdlyNlqMD4wX8AfqkNFxOQi2Ax5KdSsf_usf7Yt_FohI_uwWXv3hmFg")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public class ResGuest implements Serializable
    {

        @SerializedName("isVIP")
        @Expose
        private Boolean isVIP;
        private final static long serialVersionUID = -3461397820390485943L;

        public Boolean getIsVIP() {
            return isVIP;
        }

        public void setIsVIP(Boolean isVIP) {
            this.isVIP = isVIP;
        }

    }

}
