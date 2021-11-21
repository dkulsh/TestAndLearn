package OfficeUtil;

import com.google.gson.Gson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ReservationStatusCodeCorrector {

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    public static void main(String[] args) throws Exception {

        ReservationStatusCodeCorrector obj = new ReservationStatusCodeCorrector();

        try(BufferedReader b = new BufferedReader(new FileReader(new File("/Users/deep.kulshreshtha/Downloads/SeawareResNumbers.txt")))) {

            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                System.out.println(readLine);

                obj.getDetails(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDetails(String readLine) throws IOException, InterruptedException, JAXBException {

        String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<vx:OTA_ReadRQ xmlns:vx=\"http://www.opentravel.org/OTA/2003/05\" xmlns:ns3=\"http://www.versonix.com/ota\" xmlns:ns4=\"http://schemas.xmlsoap.org/soap/envelope/\" Version=\"1.0\">\n" +
                "   <vx:POS>\n" +
                "      <vx:Source>\n" +
                "         <vx:RequestorID Type=\"5\" ID_Context=\"SEAWARE\" ID=\"5\" />\n" +
                "         <vx:BookingChannel Type=\"1\">\n" +
                "            <vx:CompanyName>OPENTRAVEL</vx:CompanyName>\n" +
                "         </vx:BookingChannel>\n" +
                "      </vx:Source>\n" +
                "   </vx:POS>\n" +
                "   <vx:ReadRequests>\n" +
                "      <vx:ReadRequest>\n" +
                "         <vx:UniqueID Type=\"14\" ID_Context=\"SEAWARE\" ID=\"" + readLine + "\" />\n" +
                "      </vx:ReadRequest>\n" +
                "   </vx:ReadRequests>\n" +
                "</vx:OTA_ReadRQ>";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://prod.virginvoyages.com//ota/rest/OTA_ReadRQ"))
                .method("POST", HttpRequest.BodyPublishers.ofString(input))
                .header("Content-Type", "application/xml")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JAXBContext jaxbContext = JAXBContext.newInstance(OTA_ResRetrieveRS.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbUnmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
//        OTA_ResRetrieveRS resRetrieveRS = (OTA_ResRetrieveRS) jaxbUnmarshaller.unmarshal(new StringReader(response.body()));

        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<vx:OTA_ResRetrieveRS>\n" +
                "  <vx:Success/>\n" +
                "  <vx:ReservationsList>\n" +
                "    <vx:CruiseReservation>\n" +
                "      <vx:ReservationInfo>\n" +
                "        <vx:ReservationID StatusCode=\"49\" ID=\"15080\">\n" +
                "        </vx:ReservationID>\n" +
                "      </vx:ReservationInfo>\n" +
                "      <vx:PaymentsDue>\n" +
                "        <vx:PaymentDue Amount=\"3684\" PaymentNumber=\"70\"/>\n" +
                "        <vx:PaymentDue Amount=\"3684\" PaymentNumber=\"71\"/>\n" +
                "        <vx:PaymentDue Amount=\"3684\" PaymentNumber=\"80\"/>\n" +
                "        <vx:PaymentDue Amount=\"0\" PaymentNumber=\"81\"/>\n" +
                "        <vx:PaymentDue Amount=\"0\" PaymentNumber=\"82\"/>\n" +
                "        <vx:PaymentDue Amount=\"660\" PaymentNumber=\"1\" DueDate=\"2019-07-30T23:59:59\"/>\n" +
                "        <vx:PaymentDue Amount=\"3024\" PaymentNumber=\"50\" DueDate=\"2020-02-01T23:59:59\"/>\n" +
                "      </vx:PaymentsDue>\n" +
                "    </vx:CruiseReservation>\n" +
                "  </vx:ReservationsList>\n" +
                "</vx:OTA_ResRetrieveRS>";

        OTA_ResRetrieveRS resRetrieveRS = (OTA_ResRetrieveRS) jaxbUnmarshaller.unmarshal(new StringReader(str));

        System.out.println("");

////        49 - RS
//        Formula : 80 - 71
//
//                70 - Total due
//                71 - Gross due
//                80 - Total amount

    }
}

@XmlRootElement(name = "vx:OTA_ResRetrieveRS")
class OTA_ResRetrieveRS {

    private ReservationsList reservationsList;

    public ReservationsList getReservationsList() {
        return reservationsList;
    }

//    @XmlElement(name = "vx:ReservationsList")
    public void setReservationsList(ReservationsList reservationsList) {
        this.reservationsList = reservationsList;
    }
}

@XmlRootElement(name = "vx:ReservationsList")
class ReservationsList {

    private CruiseReservation cruiseReservation;

    public CruiseReservation getCruiseReservation() {
        return cruiseReservation;
    }

    @XmlElement(name = "vx:CruiseReservation")
    public void setCruiseReservation(CruiseReservation cruiseReservation) {
        this.cruiseReservation = cruiseReservation;
    }
}

@XmlRootElement(name = "vx:CruiseReservation")
class CruiseReservation{

    private ReservationInfo reservationInfo;

    public ReservationInfo getReservationInfo() {
        return reservationInfo;
    }

    @XmlElement(name = "vx:ReservationInfo")
    public void setReservationInfo(ReservationInfo reservationInfo) {
        this.reservationInfo = reservationInfo;
    }
}

@XmlRootElement(name = "vx:ReservationInfo")
class ReservationInfo {

    ReservationID reservationID;

    public ReservationID getReservationID() {
        return reservationID;
    }

    @XmlElement(name = "vx:ReservationID")
    public void setReservationID(ReservationID reservationID) {
        this.reservationID = reservationID;
    }
}

@XmlRootElement(name = "vx:ReservationID")
class ReservationID {

    private String statusCode;
    private String id;

    public String getStatusCode() {
        return statusCode;
    }

    @XmlAttribute(name = "StatusCode")
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getId() {
        return id;
    }

    @XmlAttribute(name = "ID")
    public void setId(String id) {
        this.id = id;
    }
}