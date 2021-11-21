import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SailorManifestFromCouch {

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    private static String embarkDate = "2020-03-11";
    private static String debarkDate = "2020-03-15";
    private static String now = "2020-03-13";
    private static String outFileName = "March11_March15_Manifest.csv";

    public static void main(String[] args) throws Exception {

        SailorManifestFromCouch manifestFromCouch = new SailorManifestFromCouch();
        String token = manifestFromCouch.getToken();
        List<String> reservationGuests = manifestFromCouch.getReservationGuests();

        List<CSVWriterBean> list = new LinkedList<>();
        for (String guest : reservationGuests){
            CSVWriterBean bean = manifestFromCouch.getDetails(guest, token);
            list.add(bean);
        }

//        try(BufferedReader b = new BufferedReader(new FileReader(new File("/Users/deep.kulshreshtha/Downloads/Feb26ToMarch07_ResGuestIds.txt")));) {
//
//            String readLine = "";
//            while ((readLine = b.readLine()) != null) {
////                System.out.println(readLine);
//
//                CSVWriterBean bean = manifestFromCouch.getDetails(readLine, token);
//                list.add(bean);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        manifestFromCouch.writeCsvFromBean(list);
    }

    private String getToken() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://application.scl.virginvoyages.com/svc/identityaccessmanagement-service/oauth/token?grant_type=client_credentials"))
                .method("POST", HttpRequest.BodyPublishers.ofString(""))
                .header("Authorization", "Basic YjNjMmIwN2MtODY5MC0xMWU5LWJkOGUtMGExYTQyNjFlOTYyOnI0SlpGUUxGMldKb01tcE8=")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        TokenResponse tokenResponse = new Gson().fromJson(response.body(), TokenResponse.class);
        return tokenResponse.getTokenType() + " " + tokenResponse.getAccessToken();
    }

    private List<String> getReservationGuests() throws IOException, InterruptedException {

//        String sql = "select pStatus.ReservationGuestID " +
//                "from prodappdocstore pStatus " +
//                "where pStatus.type = 'GuestStatus' " +
//                "and pStatus.EmbarkDate >= '" + embarkDate + "' " +
//                "and pStatus.DebarkDate <= '" + debarkDate + "' " +
////                "and pStatus.IsOnBoarded = true " +
//                "and 1584106609000 between pStatus.EmbarkDateEpoch and pStatus.DebarkDateEpoch " +
//                "and pStatus.CheckinStatus = true " +
//                "and meta(pStatus).id not like \"_sync%\"";

        String sql = "select a.ReservationGuestID " +
                "from prodappdocstore a " +
                "where a.type =\"GuestStatus\" " +
                "and (1584057600000 between a.EmbarkDateEpoch and a.DebarkDateEpoch) " +
                "and ((a.EmbarkDateEpoch between 1583884800000 and 1584230400000) " +
                "and (a.DebarkDateEpoch between 1583884800000 and 1584230400000)) " +
                "and a.CheckinStatus = true " +
                "and a.ReservationStatusCode != \"CN\" " +
                "and meta(a).id not like \"_sync%\" ;";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://10.101.220.211:8093/query/service"))
                .method("POST", HttpRequest.BodyPublishers.ofString(sql))
                .header("Authorization", "Basic c2VsZWN0dXNlcjpDYnNzZWxlY3QqMTAx")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        Type listType = new TypeToken<List<CouchResponse>>() {}.getType();
//        List<CouchResponse> reservationGuests = new Gson().fromJson(response.body(), CouchResponse.class);
        CouchResponse couchResponse = new Gson().fromJson(response.body(), CouchResponse.class);

        return couchResponse.getResults().stream().map(result -> result.getReservationGuestID()).collect(Collectors.toList());
    }

    private CSVWriterBean getDetails(String reservationGuestId, String token) throws Exception  {

        HttpRequest personalInfoRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://application.scl.virginvoyages.com/syncgatewayfeedersadmin/prodappdocstore/GuestPersonalInformation::" + reservationGuestId))
                .setHeader("Authorization", token)
                .build();

        HttpResponse<String> personalInfoResponse = httpClient.send(personalInfoRequest, HttpResponse.BodyHandlers.ofString());

        GuestPersonalInformation personalInfo = new Gson().fromJson(personalInfoResponse.body(), GuestPersonalInformation.class);


        HttpRequest identificationRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://application.scl.virginvoyages.com/syncgatewayfeedersadmin/prodappdocstore/GuestIdentification::" + reservationGuestId))
                .setHeader("Authorization", token)
                .build();

        HttpResponse<String> identificationResponse = httpClient.send(identificationRequest, HttpResponse.BodyHandlers.ofString());

        GuestIdentification guestIdentification = new Gson().fromJson(identificationResponse.body(), GuestIdentification.class);

        if (guestIdentification.getVisaDetail().size() > 1) {
            System.out.println(personalInfo.getFirstName() + " " + personalInfo.getLastName() + " has multiple Visas");
        }

        List<GuestIdentification.Identification> identificationList = guestIdentification.getIdentifications().stream()
                .filter(id -> id.getDocumentTypeCode().equals("ARC")).collect(Collectors.toList());
        if (identificationList.size() > 1) {
            System.out.println(personalInfo.getFirstName() + " " + personalInfo.getLastName() + " has multiple ARCs");
        }

//        System.out.print(personalInfo.getFirstName() + ",");
//        System.out.print(personalInfo.getLastName() + ",");
//        System.out.print(personalInfo.getReservationGuestID() + ",");
//        System.out.print(personalInfo.getCitizenshipCountryCode() + ",");

        String visa = null;
        String arc = null;
        String esta = null;

        if (guestIdentification.getVisaDetail().size() > 0) {
            visa = guestIdentification.getVisaDetail().get(0).getVisaTypeCode();
        }

        if (guestIdentification.getIdentifications().stream().anyMatch(id -> id.getDocumentTypeCode().equals("ARC"))) {
            arc = "ARC";
        }

        if (guestIdentification.getIsOptedForESTA()){
            esta = "Esta";
        }

        String passportCountry = guestIdentification.getIdentifications()
                .stream()
                .filter(id -> id.getDocumentTypeCode().equals("P"))
                .map(id -> id.getIssueCountryCode())
                .collect(Collectors.joining(", "));

        return new CSVWriterBean(personalInfo.getReservationGuestID(), personalInfo.getFirstName(), personalInfo.getLastName(),
                personalInfo.getCitizenshipCountryCode(), visa, arc, esta, passportCountry);

        // Anything filled in -> ARC
        // All blank -> Esta

    }

    public void writeCsvFromBean(List<CSVWriterBean> list) throws Exception {
        Writer writer  = new FileWriter("/Users/deep.kulshreshtha/Downloads/" + outFileName);

        StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();

        sbc.write(list);
        writer.close();
    }

    public class GuestIdentification implements Serializable
    {

        @SerializedName("AdditionalDocumentTypeCode")
        @Expose
        private String additionalDocumentTypeCode;
        @SerializedName("AdditionalDocumentTypeID")
        @Expose
        private String additionalDocumentTypeID;
        @SerializedName("Identifications")
        @Expose
        private List<Identification> identifications = null;
        @SerializedName("IsOptedForESTA")
        @Expose
        private Boolean isOptedForESTA;
        @SerializedName("ReservationGuestID")
        @Expose
        private String reservationGuestID;
        @SerializedName("SelectedIdentificationID")
        @Expose
        private String selectedIdentificationID;
        @SerializedName("SelectedIdentificationTypeCode")
        @Expose
        private String selectedIdentificationTypeCode;
        @SerializedName("VisaDetail")
        @Expose
        private List<VisaDetail> visaDetail = null;
        private final static long serialVersionUID = 4376000829414369443L;

        public String getAdditionalDocumentTypeCode() {
            return additionalDocumentTypeCode;
        }

        public void setAdditionalDocumentTypeCode(String additionalDocumentTypeCode) {
            this.additionalDocumentTypeCode = additionalDocumentTypeCode;
        }

        public String getAdditionalDocumentTypeID() {
            return additionalDocumentTypeID;
        }

        public void setAdditionalDocumentTypeID(String additionalDocumentTypeID) {
            this.additionalDocumentTypeID = additionalDocumentTypeID;
        }

        public List<Identification> getIdentifications() {
            return identifications;
        }

        public void setIdentifications(List<Identification> identifications) {
            this.identifications = identifications;
        }

        public Boolean getIsOptedForESTA() {
            return isOptedForESTA;
        }

        public void setIsOptedForESTA(Boolean isOptedForESTA) {
            this.isOptedForESTA = isOptedForESTA;
        }

        public String getReservationGuestID() {
            return reservationGuestID;
        }

        public void setReservationGuestID(String reservationGuestID) {
            this.reservationGuestID = reservationGuestID;
        }

        public String getSelectedIdentificationID() {
            return selectedIdentificationID;
        }

        public void setSelectedIdentificationID(String selectedIdentificationID) {
            this.selectedIdentificationID = selectedIdentificationID;
        }

        public String getSelectedIdentificationTypeCode() {
            return selectedIdentificationTypeCode;
        }

        public void setSelectedIdentificationTypeCode(String selectedIdentificationTypeCode) {
            this.selectedIdentificationTypeCode = selectedIdentificationTypeCode;
        }

        public List<VisaDetail> getVisaDetail() {
            return visaDetail;
        }

        public void setVisaDetail(List<VisaDetail> visaDetail) {
            this.visaDetail = visaDetail;
        }

        public class Identification implements Serializable
        {

            @SerializedName("DocumentTypeCode")
            @Expose
            private String documentTypeCode;
            @SerializedName("GuestDocumentStatusReasons")
            @Expose
            private List<Object> guestDocumentStatusReasons = null;
            @SerializedName("GuestID")
            @Expose
            private String guestID;
            @SerializedName("IdentificationID")
            @Expose
            private String identificationID;
            @SerializedName("Number")
            @Expose
            private String number;
            @SerializedName("VerificationStatus")
            @Expose
            private String verificationStatus;
            @SerializedName("BirthCountryCode")
            @Expose
            private String birthCountryCode;
            @SerializedName("ExpiryDate")
            @Expose
            private String expiryDate;
            @SerializedName("FirstName")
            @Expose
            private String firstName;
            @SerializedName("GenderCode")
            @Expose
            private String genderCode;
            @SerializedName("IssueCountryCode")
            @Expose
            private String issueCountryCode;
            @SerializedName("LastName")
            @Expose
            private String lastName;
            @SerializedName("ScannedCopyMediaItemID")
            @Expose
            private String scannedCopyMediaItemID;

            @SerializedName("Nationality")
            @Expose
            private String nationality;
            private final static long serialVersionUID = 8950744335829368052L;

            public String getDocumentTypeCode() {
                return documentTypeCode;
            }

            public void setDocumentTypeCode(String documentTypeCode) {
                this.documentTypeCode = documentTypeCode;
            }

            public List<Object> getGuestDocumentStatusReasons() {
                return guestDocumentStatusReasons;
            }

            public void setGuestDocumentStatusReasons(List<Object> guestDocumentStatusReasons) {
                this.guestDocumentStatusReasons = guestDocumentStatusReasons;
            }

            public String getGuestID() {
                return guestID;
            }

            public void setGuestID(String guestID) {
                this.guestID = guestID;
            }

            public String getIdentificationID() {
                return identificationID;
            }

            public void setIdentificationID(String identificationID) {
                this.identificationID = identificationID;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getVerificationStatus() {
                return verificationStatus;
            }

            public void setVerificationStatus(String verificationStatus) {
                this.verificationStatus = verificationStatus;
            }

            public String getBirthCountryCode() {
                return birthCountryCode;
            }

            public void setBirthCountryCode(String birthCountryCode) {
                this.birthCountryCode = birthCountryCode;
            }

            public String getExpiryDate() {
                return expiryDate;
            }

            public void setExpiryDate(String expiryDate) {
                this.expiryDate = expiryDate;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getGenderCode() {
                return genderCode;
            }

            public void setGenderCode(String genderCode) {
                this.genderCode = genderCode;
            }

            public String getIssueCountryCode() {
                return issueCountryCode;
            }

            public void setIssueCountryCode(String issueCountryCode) {
                this.issueCountryCode = issueCountryCode;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getScannedCopyMediaItemID() {
                return scannedCopyMediaItemID;
            }

            public void setScannedCopyMediaItemID(String scannedCopyMediaItemID) {
                this.scannedCopyMediaItemID = scannedCopyMediaItemID;
            }

            public String getNationality() {
                return nationality;
            }

            public void setNationality(String nationality) {
                this.nationality = nationality;
            }

        }

        public class VisaDetail implements Serializable
        {

            @SerializedName("BirthCountryCode")
            @Expose
            private String birthCountryCode;
            @SerializedName("ExpiryDate")
            @Expose
            private String expiryDate;
            @SerializedName("FirstName")
            @Expose
            private String firstName;
            @SerializedName("GuestDocumentStatusReasons")
            @Expose
            private List<Object> guestDocumentStatusReasons = null;
            @SerializedName("IsApproved")
            @Expose
            private Boolean isApproved;
            @SerializedName("IssueCountryCode")
            @Expose
            private String issueCountryCode;
            @SerializedName("LastName")
            @Expose
            private String lastName;
            @SerializedName("MultiMediaItemID")
            @Expose
            private String multiMediaItemID;
            @SerializedName("Number")
            @Expose
            private String number;
            @SerializedName("VerificationStatus")
            @Expose
            private String verificationStatus;
            @SerializedName("VisaID")
            @Expose
            private String visaID;
            @SerializedName("VisaTypeCode")
            @Expose
            private String visaTypeCode;
            private final static long serialVersionUID = -6828106924830839097L;

            public String getBirthCountryCode() {
                return birthCountryCode;
            }

            public void setBirthCountryCode(String birthCountryCode) {
                this.birthCountryCode = birthCountryCode;
            }

            public String getExpiryDate() {
                return expiryDate;
            }

            public void setExpiryDate(String expiryDate) {
                this.expiryDate = expiryDate;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public List<Object> getGuestDocumentStatusReasons() {
                return guestDocumentStatusReasons;
            }

            public void setGuestDocumentStatusReasons(List<Object> guestDocumentStatusReasons) {
                this.guestDocumentStatusReasons = guestDocumentStatusReasons;
            }

            public Boolean getIsApproved() {
                return isApproved;
            }

            public void setIsApproved(Boolean isApproved) {
                this.isApproved = isApproved;
            }

            public String getIssueCountryCode() {
                return issueCountryCode;
            }

            public void setIssueCountryCode(String issueCountryCode) {
                this.issueCountryCode = issueCountryCode;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getMultiMediaItemID() {
                return multiMediaItemID;
            }

            public void setMultiMediaItemID(String multiMediaItemID) {
                this.multiMediaItemID = multiMediaItemID;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getVerificationStatus() {
                return verificationStatus;
            }

            public void setVerificationStatus(String verificationStatus) {
                this.verificationStatus = verificationStatus;
            }

            public String getVisaID() {
                return visaID;
            }

            public void setVisaID(String visaID) {
                this.visaID = visaID;
            }

            public String getVisaTypeCode() {
                return visaTypeCode;
            }

            public void setVisaTypeCode(String visaTypeCode) {
                this.visaTypeCode = visaTypeCode;
            }

        }
    }

    public class GuestPersonalInformation implements Serializable
    {

        @SerializedName("AccessCardNumber")
        @Expose
        private String accessCardNumber;
        @SerializedName("AccessCardNumbers")
        @Expose
        private List<AccessCardNumber> accessCardNumbers = null;
        @SerializedName("Addresses")
        @Expose
        private List<Object> addresses = null;
        @SerializedName("BirthDate")
        @Expose
        private String birthDate;
        @SerializedName("Celeberations")
        @Expose
        private List<Object> celeberations = null;
        @SerializedName("CitizenshipCountryCode")
        @Expose
        private String citizenshipCountryCode;
        @SerializedName("DebarkDate")
        @Expose
        private String debarkDate;
        @SerializedName("DebarkPortCode")
        @Expose
        private String debarkPortCode;
        @SerializedName("DebarkPortName")
        @Expose
        private String debarkPortName;
        @SerializedName("EmbarkDate")
        @Expose
        private String embarkDate;
        @SerializedName("EmbarkPortCode")
        @Expose
        private String embarkPortCode;
        @SerializedName("EmbarkPortName")
        @Expose
        private String embarkPortName;
        @SerializedName("EmergencyContacts")
        @Expose
        private List<EmergencyContact> emergencyContacts = null;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("FullName")
        @Expose
        private String fullName;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("GuestID")
        @Expose
        private String guestID;
        @SerializedName("GuestPreferences")
        @Expose
        private List<Object> guestPreferences = null;
        @SerializedName("GuestSecurityMediaItemID")
        @Expose
        private String guestSecurityMediaItemID;
        @SerializedName("IsDocumentPhotoCopied")
        @Expose
        private Boolean isDocumentPhotoCopied;
        @SerializedName("IsPrimary")
        @Expose
        private Boolean isPrimary;
        @SerializedName("IsSameAddressForParty")
        @Expose
        private Boolean isSameAddressForParty;
        @SerializedName("IsSameEmergencyContactForParty")
        @Expose
        private Boolean isSameEmergencyContactForParty;
        @SerializedName("IsVIP")
        @Expose
        private Boolean isVIP;
        @SerializedName("IsVip")
        @Expose
        private Boolean isVip;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("PMSID")
        @Expose
        private String pMSID;
        @SerializedName("PhoneNumbers")
        @Expose
        private List<Object> phoneNumbers = null;
        @SerializedName("ReservationGuestID")
        @Expose
        private String reservationGuestID;
        @SerializedName("ReservationID")
        @Expose
        private String reservationID;
        @SerializedName("ReservationNumber")
        @Expose
        private String reservationNumber;
        @SerializedName("ReservationParty")
        @Expose
        private List<Object> reservationParty = null;
        @SerializedName("ShipCode")
        @Expose
        private String shipCode;
        @SerializedName("ShipName")
        @Expose
        private String shipName;
        @SerializedName("SpecialNeeds")
        @Expose
        private List<Object> specialNeeds = null;
        @SerializedName("Stateroom")
        @Expose
        private String stateroom;
        @SerializedName("StateroomParty")
        @Expose
        private List<Object> stateroomParty = null;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("TravelWiths")
        @Expose
        private List<Object> travelWiths = null;
        @SerializedName("VerificationStatus")
        @Expose
        private String verificationStatus;
        @SerializedName("VerifiedBy")
        @Expose
        private String verifiedBy;
        @SerializedName("VoyageNumber")
        @Expose
        private String voyageNumber;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("_rev")
        @Expose
        private String rev;
        @SerializedName("channels")
        @Expose
        private List<String> channels = null;
        @SerializedName("lastModifiedBy")
        @Expose
        private String lastModifiedBy;
        @SerializedName("lastModifiedDate")
        @Expose
        private String lastModifiedDate;
        @SerializedName("sourceId")
        @Expose
        private String sourceId;
        @SerializedName("type")
        @Expose
        private String type;
        private final static long serialVersionUID = -7376034331814909962L;

        public String getAccessCardNumber() {
            return accessCardNumber;
        }

        public void setAccessCardNumber(String accessCardNumber) {
            this.accessCardNumber = accessCardNumber;
        }

        public List<AccessCardNumber> getAccessCardNumbers() {
            return accessCardNumbers;
        }

        public void setAccessCardNumbers(List<AccessCardNumber> accessCardNumbers) {
            this.accessCardNumbers = accessCardNumbers;
        }

        public List<Object> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<Object> addresses) {
            this.addresses = addresses;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public List<Object> getCeleberations() {
            return celeberations;
        }

        public void setCeleberations(List<Object> celeberations) {
            this.celeberations = celeberations;
        }

        public String getCitizenshipCountryCode() {
            return citizenshipCountryCode;
        }

        public void setCitizenshipCountryCode(String citizenshipCountryCode) {
            this.citizenshipCountryCode = citizenshipCountryCode;
        }

        public String getDebarkDate() {
            return debarkDate;
        }

        public void setDebarkDate(String debarkDate) {
            this.debarkDate = debarkDate;
        }

        public String getDebarkPortCode() {
            return debarkPortCode;
        }

        public void setDebarkPortCode(String debarkPortCode) {
            this.debarkPortCode = debarkPortCode;
        }

        public String getDebarkPortName() {
            return debarkPortName;
        }

        public void setDebarkPortName(String debarkPortName) {
            this.debarkPortName = debarkPortName;
        }

        public String getEmbarkDate() {
            return embarkDate;
        }

        public void setEmbarkDate(String embarkDate) {
            this.embarkDate = embarkDate;
        }

        public String getEmbarkPortCode() {
            return embarkPortCode;
        }

        public void setEmbarkPortCode(String embarkPortCode) {
            this.embarkPortCode = embarkPortCode;
        }

        public String getEmbarkPortName() {
            return embarkPortName;
        }

        public void setEmbarkPortName(String embarkPortName) {
            this.embarkPortName = embarkPortName;
        }

        public List<EmergencyContact> getEmergencyContacts() {
            return emergencyContacts;
        }

        public void setEmergencyContacts(List<EmergencyContact> emergencyContacts) {
            this.emergencyContacts = emergencyContacts;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getGuestID() {
            return guestID;
        }

        public void setGuestID(String guestID) {
            this.guestID = guestID;
        }

        public List<Object> getGuestPreferences() {
            return guestPreferences;
        }

        public void setGuestPreferences(List<Object> guestPreferences) {
            this.guestPreferences = guestPreferences;
        }

        public String getGuestSecurityMediaItemID() {
            return guestSecurityMediaItemID;
        }

        public void setGuestSecurityMediaItemID(String guestSecurityMediaItemID) {
            this.guestSecurityMediaItemID = guestSecurityMediaItemID;
        }

        public Boolean getIsDocumentPhotoCopied() {
            return isDocumentPhotoCopied;
        }

        public void setIsDocumentPhotoCopied(Boolean isDocumentPhotoCopied) {
            this.isDocumentPhotoCopied = isDocumentPhotoCopied;
        }

        public Boolean getIsPrimary() {
            return isPrimary;
        }

        public void setIsPrimary(Boolean isPrimary) {
            this.isPrimary = isPrimary;
        }

        public Boolean getIsSameAddressForParty() {
            return isSameAddressForParty;
        }

        public void setIsSameAddressForParty(Boolean isSameAddressForParty) {
            this.isSameAddressForParty = isSameAddressForParty;
        }

        public Boolean getIsSameEmergencyContactForParty() {
            return isSameEmergencyContactForParty;
        }

        public void setIsSameEmergencyContactForParty(Boolean isSameEmergencyContactForParty) {
            this.isSameEmergencyContactForParty = isSameEmergencyContactForParty;
        }

        public Boolean getIsVIP() {
            return isVIP;
        }

        public void setIsVIP(Boolean isVIP) {
            this.isVIP = isVIP;
        }

        public Boolean getIsVip() {
            return isVip;
        }

        public void setIsVip(Boolean isVip) {
            this.isVip = isVip;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPMSID() {
            return pMSID;
        }

        public void setPMSID(String pMSID) {
            this.pMSID = pMSID;
        }

        public List<Object> getPhoneNumbers() {
            return phoneNumbers;
        }

        public void setPhoneNumbers(List<Object> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
        }

        public String getReservationGuestID() {
            return reservationGuestID;
        }

        public void setReservationGuestID(String reservationGuestID) {
            this.reservationGuestID = reservationGuestID;
        }

        public String getReservationID() {
            return reservationID;
        }

        public void setReservationID(String reservationID) {
            this.reservationID = reservationID;
        }

        public String getReservationNumber() {
            return reservationNumber;
        }

        public void setReservationNumber(String reservationNumber) {
            this.reservationNumber = reservationNumber;
        }

        public List<Object> getReservationParty() {
            return reservationParty;
        }

        public void setReservationParty(List<Object> reservationParty) {
            this.reservationParty = reservationParty;
        }

        public String getShipCode() {
            return shipCode;
        }

        public void setShipCode(String shipCode) {
            this.shipCode = shipCode;
        }

        public String getShipName() {
            return shipName;
        }

        public void setShipName(String shipName) {
            this.shipName = shipName;
        }

        public List<Object> getSpecialNeeds() {
            return specialNeeds;
        }

        public void setSpecialNeeds(List<Object> specialNeeds) {
            this.specialNeeds = specialNeeds;
        }

        public String getStateroom() {
            return stateroom;
        }

        public void setStateroom(String stateroom) {
            this.stateroom = stateroom;
        }

        public List<Object> getStateroomParty() {
            return stateroomParty;
        }

        public void setStateroomParty(List<Object> stateroomParty) {
            this.stateroomParty = stateroomParty;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Object> getTravelWiths() {
            return travelWiths;
        }

        public void setTravelWiths(List<Object> travelWiths) {
            this.travelWiths = travelWiths;
        }

        public String getVerificationStatus() {
            return verificationStatus;
        }

        public void setVerificationStatus(String verificationStatus) {
            this.verificationStatus = verificationStatus;
        }

        public String getVerifiedBy() {
            return verifiedBy;
        }

        public void setVerifiedBy(String verifiedBy) {
            this.verifiedBy = verifiedBy;
        }

        public String getVoyageNumber() {
            return voyageNumber;
        }

        public void setVoyageNumber(String voyageNumber) {
            this.voyageNumber = voyageNumber;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRev() {
            return rev;
        }

        public void setRev(String rev) {
            this.rev = rev;
        }

        public List<String> getChannels() {
            return channels;
        }

        public void setChannels(List<String> channels) {
            this.channels = channels;
        }

        public String getLastModifiedBy() {
            return lastModifiedBy;
        }

        public void setLastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
        }

        public String getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(String lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public class AccessCardNumber implements Serializable
        {

            @SerializedName("AccessCardNumber")
            @Expose
            private String accessCardNumber;
            @SerializedName("CreditBalance")
            @Expose
            private Integer creditBalance;
            @SerializedName("DebitBalance")
            @Expose
            private Integer debitBalance;
            @SerializedName("FolioID")
            @Expose
            private String folioID;
            @SerializedName("IsAtRisk")
            @Expose
            private Boolean isAtRisk;
            @SerializedName("IsGangwayAllowed")
            @Expose
            private Boolean isGangwayAllowed;
            private final static long serialVersionUID = -2964380754868453782L;

            public String getAccessCardNumber() {
                return accessCardNumber;
            }

            public void setAccessCardNumber(String accessCardNumber) {
                this.accessCardNumber = accessCardNumber;
            }

            public Integer getCreditBalance() {
                return creditBalance;
            }

            public void setCreditBalance(Integer creditBalance) {
                this.creditBalance = creditBalance;
            }

            public Integer getDebitBalance() {
                return debitBalance;
            }

            public void setDebitBalance(Integer debitBalance) {
                this.debitBalance = debitBalance;
            }

            public String getFolioID() {
                return folioID;
            }

            public void setFolioID(String folioID) {
                this.folioID = folioID;
            }

            public Boolean getIsAtRisk() {
                return isAtRisk;
            }

            public void setIsAtRisk(Boolean isAtRisk) {
                this.isAtRisk = isAtRisk;
            }

            public Boolean getIsGangwayAllowed() {
                return isGangwayAllowed;
            }

            public void setIsGangwayAllowed(Boolean isGangwayAllowed) {
                this.isGangwayAllowed = isGangwayAllowed;
            }

        }

        public class EmergencyContact implements Serializable
        {

            @SerializedName("CountryTelephoneCode")
            @Expose
            private String countryTelephoneCode;
            @SerializedName("EmergencyContactID")
            @Expose
            private String emergencyContactID;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("PhoneNumber")
            @Expose
            private String phoneNumber;
            @SerializedName("Relationship")
            @Expose
            private String relationship;
            private final static long serialVersionUID = 2362635006919854048L;

            public String getCountryTelephoneCode() {
                return countryTelephoneCode;
            }

            public void setCountryTelephoneCode(String countryTelephoneCode) {
                this.countryTelephoneCode = countryTelephoneCode;
            }

            public String getEmergencyContactID() {
                return emergencyContactID;
            }

            public void setEmergencyContactID(String emergencyContactID) {
                this.emergencyContactID = emergencyContactID;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getRelationship() {
                return relationship;
            }

            public void setRelationship(String relationship) {
                this.relationship = relationship;
            }

        }
    }

    public class TokenResponse implements Serializable
    {

        @SerializedName("access_token")
        @Expose
        private String accessToken;
        @SerializedName("token_type")
        @Expose
        private String tokenType;

        private final static long serialVersionUID = -3002913840410160830L;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

    }

    public class CouchResponse implements Serializable
    {

        @SerializedName("results")
        @Expose
        private List<Result> results = null;
        private final static long serialVersionUID = -3831235171523543894L;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public class Result implements Serializable
        {

            @SerializedName("ReservationGuestID")
            @Expose
            private String reservationGuestID;
            private final static long serialVersionUID = 9104260363072304652L;

            public String getReservationGuestID() {
                return reservationGuestID;
            }

            public void setReservationGuestID(String reservationGuestID) {
                this.reservationGuestID = reservationGuestID;
            }

        }
    }

    public class CSVWriterBean {

        public CSVWriterBean(String reservationGuestId, String firstName, String lastName, String citizenship, String visa, String arc, String esta, String passportIssueCountry) {
            this.reservationGuestId = reservationGuestId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.citizenship = citizenship;
            this.visa = visa;
            this.arc = arc;
            this.esta = esta;
            this.passportIssueCountry = passportIssueCountry;
        }

        @CsvBindByPosition(position = 0)
        private String reservationGuestId;

        @CsvBindByPosition(position = 1)
        private String firstName;

        @CsvBindByPosition(position = 2)
        private String lastName;

        @CsvBindByPosition(position = 3)
        private String citizenship;

        @CsvBindByPosition(position = 4)
        private String visa;

        @CsvBindByPosition(position = 5)
        private String arc;

        @CsvBindByPosition(position = 6)
        private String esta;

        @CsvBindByPosition(position = 7)
        private String passportIssueCountry;

        public String getReservationGuestId() {
            return reservationGuestId;
        }

        public void setReservationGuestId(String reservationGuestId) {
            this.reservationGuestId = reservationGuestId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getCitizenship() {
            return citizenship;
        }

        public void setCitizenship(String citizenship) {
            this.citizenship = citizenship;
        }

        public String getVisa() {
            return visa;
        }

        public void setVisa(String visa) {
            this.visa = visa;
        }

        public String getArc() {
            return arc;
        }

        public void setArc(String arc) {
            this.arc = arc;
        }

        public String getEsta() {
            return esta;
        }

        public void setEsta(String esta) {
            this.esta = esta;
        }

        public String getPassportIssueCountry() {
            return passportIssueCountry;
        }

        public void setPassportIssueCountry(String passportIssueCountry) {
            this.passportIssueCountry = passportIssueCountry;
        }


    }

}
