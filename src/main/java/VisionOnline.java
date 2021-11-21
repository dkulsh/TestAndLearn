import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import decurtis.dxp.batchjob.parent.common.entity.db.EncodingDataQueue;
import decurtis.dxp.batchjob.parent.common.entity.encodingdata.DoorOperation;
import decurtis.dxp.batchjob.parent.common.entity.encodingdata.VingRequest;
import decurtis.dxp.common.logging.aop.ApplicationLevelException;
import decurtis.dxp.doorlock.common.EnvironmentalPropertyConfiguration;
import decurtis.dxp.doorlock.entity.Door;
import decurtis.dxp.doorlock.entity.EncodeCardResponse;
import decurtis.dxp.doorlock.entity.SessionResponse;
import decurtis.dxp.doorlock.utility.DoorLockUtility;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.io.Serializable;
import java.util.stream.Collectors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public class VisionOnline {

    public static final String DATE_HOUR_MINUTE_TIME_FORMAT = "yyyyMMdd'T'HHmm";
    public static final int LAST_HOUR_OF_DAY = 23;
    public static final int LAST_MIN_OF_HOUR = 59;
    public static final int FIRST_HOUR_OF_DAY = 0;
    public static final int FIRST_MINUTE_OF_DAY = 1;

    public static final String RFID48 = "rfid48";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) { return;
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) { return;
                }
            }
    };

    private static HttpClient httpClient = null;

    public static void main(String[] args) throws IOException, InterruptedException, NoSuchAlgorithmException,
            KeyManagementException, SignatureException, InvalidKeyException, ApplicationLevelException {

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        httpClient = HttpClient.newBuilder().sslContext(sc).build();
//        httpClient = HttpClient.newBuilder().build();

        VisionOnline visionOnline = new VisionOnline();
//        SessionResponse sessionResponse = visionOnline.createSessionByUserNameAndPass();
//        List<Door> doors = visionOnline.getAllDoorDetails(visionOnline.getCurrentDate(), sessionResponse.getId(), sessionResponse.getAccessKey());
//        List<Door> offlineDoors = doors.stream().filter(door -> door.getOnline() == false).collect(Collectors.toList());
//        offlineDoors.forEach(door -> System.out.println(door.doorName));

        String encodeJsonString = visionOnline.getEncodeRequestString();
        SessionResponse sessionResponse = visionOnline.createSessionByUserNameAndPass();
        EncodeCardResponse encodeCardResponse = visionOnline.getEncodedCard(encodeJsonString, sessionResponse.getAccessKey(),
                sessionResponse.getId());

    }

    public SessionResponse createSessionByUserNameAndPass() throws IOException, InterruptedException {
        Map<String, String> sessionRequest = new HashMap();
        sessionRequest.put("username", "CERTVXP");
        sessionRequest.put("password", "CERTVXP2020!");

        try {
            String sessionRequestString = new Gson().toJson(sessionRequest);
            String md5 = this.getMD5(sessionRequestString);
            return createSession(sessionRequestString, md5, getCurrentDate());
        } catch (JsonProcessingException var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public SessionResponse createSession(final String requestBody, final String md5, String currentDate) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://10.2.52.111/api/v1/sessions"))
//                .uri(URI.create("https://10.101.224.226/api/v1/sessions"))
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json;charset=utf-8")
                .header("Content-MD5", md5)
                .header("x-aah-date", currentDate)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        return new Gson().fromJson(response.body(), SessionResponse.class);
    }

    public List<Door> getAllDoorDetails(String currentDate, String sessionId, String accessKey) throws IOException, InterruptedException, NoSuchAlgorithmException, SignatureException, ApplicationLevelException, InvalidKeyException {

        String hmac = this.getAuthSignatureWithResource(HttpMethod.GET.name(), null, null, currentDate, null, accessKey, "/api/v1/doors?details=true");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
//                .uri(URI.create("https://10.2.52.111/api/v1/doors?details=true"))
                .uri(URI.create("https://10.101.224.226/api/v1/doors?details=true"))
                .setHeader("Date", currentDate)
                .setHeader("Authorization", "AWS " + sessionId + ":" + hmac)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        Type listType = new TypeToken<List<Door>>() {}.getType();
        return new Gson().fromJson(response.body(), listType);

    }

    public String getAuthSignature(final String method, final String contentMd5, final String contentType, final String date, final String path, String accessKey) throws ApplicationLevelException, InvalidKeyException, SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder authString = new StringBuilder();
        authString.append(method);
        authString.append("\n");
        if (Objects.nonNull(contentMd5)) {
            authString.append(contentMd5);
        }

        authString.append("\n");
        if (Objects.nonNull(contentType)) {
            authString.append(contentType);
        }

        authString.append("\n");
        if (Objects.nonNull(date)) {
            authString.append(date);
        }

        authString.append("\n");
        if (Objects.nonNull(path)) {
            authString.append(path);
        }

        return this.calculateRFC2104HMAC(authString.toString(), accessKey);
    }

    public String getAuthSignatureWithResource(final String method, final String contentMd5, final String contentType, final String date, final String path, final String accessKey, final String resource) throws ApplicationLevelException, InvalidKeyException, SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder authString = new StringBuilder();
        authString.append(method);
        authString.append("\n");
        if (Objects.nonNull(contentMd5)) {
            authString.append(contentMd5);
        }

        authString.append("\n");
        if (Objects.nonNull(contentType)) {
            authString.append(contentType);
        }

        authString.append("\n");
        if (Objects.nonNull(date)) {
            authString.append(date);
        }

        authString.append("\n");
        if (Objects.nonNull(path)) {
            authString.append(path);
        }

        if (Objects.nonNull(resource)) {
            authString.append(resource);
        }

        return this.calculateRFC2104HMAC(authString.toString(), accessKey);
    }

    public String calculateRFC2104HMAC(String data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm());
        mac.init(signingKey);
        return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    public String getCurrentDate() {
        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
        simpleDateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateformat.format(now);
    }

    public HttpHeaders getHeaders(final String contentmd5, final String xAahDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=utf-8");
        headers.set("Content-MD5", contentmd5);
        headers.set("x-aah-date", xAahDate);
        return headers;
    }

    public String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] enc = md.digest();
            return Base64.getEncoder().encodeToString(enc);
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        }

        return null;
    }

    private String getEncodeRequestString() throws JsonProcessingException {

        Instant startInstant = Instant.now();
        Instant expireInstant = Instant.now().plus(2, ChronoUnit.DAYS);

        String startTimeString = DateTimeFormatter.ofPattern(DATE_HOUR_MINUTE_TIME_FORMAT)
                    .withZone(ZoneId.systemDefault())
                    .format(startInstant);

        String expireTimeString = DateTimeFormatter.ofPattern(DATE_HOUR_MINUTE_TIME_FORMAT)
                .withZone(ZoneId.systemDefault())
                .format(expireInstant);

        VingRequest vingRequest = getVingRequest(startTimeString, expireTimeString, "10358Z",
                "04D126DAEB5A85");

        return objectMapper.writeValueAsString(vingRequest);
    }

//	private void processJoinerCards(EncodingDataQueue encodingDataRecord, String joinerCardId) {
//
//		String folioId = encodingDataRecord.getFolioid();
//		int retryCount = encodingDataRecord.getRetrycount();
//
//		try {
//
//			String joinerJsonString = getJoinerRequestString(encodingDataRecord, joinerCardId);
//
//			SessionResponse sessionResponse = doorLockUtility.createSessionByUserNameAndPass(username, password);
//
//			EncodeCardResponse joinerResponse = doorLockUtility.sendJoinerRequest(joinerJsonString,
//					sessionResponse.getAccessKey(), sessionResponse.getId());
//
//			if (joinerResponse == null) {
//				updateUnSuccessfulRecordsInEncodingDataQueue(retryCount, folioId,
//						encodingDataRecord.getNextprocessingtime());
//			} else {
//				updateSuccessRecordInEncodingDataQueue(retryCount, folioId);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	private String getJoinerRequestString(EncodingDataQueue encodingDataRecord, String joinerCardId) throws JsonProcessingException {
//		String startTimeString = DateTimeFormatter
//				.ofPattern(DATE_HOUR_MINUTE_TIME_FORMAT)
//				.format(Instant.now());
//
//		Date debarkDate = encodingDataRecord.getDebarkDate();
//		Instant expireInstant = Instant.ofEpochMilli(debarkDate.getTime());
//		LocalDateTime expireTime = LocalDateTime
//				.ofInstant(expireInstant, ZoneId.systemDefault())
//				.withHour(LAST_HOUR_OF_DAY)
//				.withMinute(LAST_MIN_OF_HOUR);
//
//		String expireTimeString = DateTimeFormatter
//				.ofPattern(DATE_HOUR_MINUTE_TIME_FORMAT)
//				.format(expireTime);
//
//		JoinerRequest joinerRequest = getJoinerRequest(startTimeString, expireTimeString, List.of(joinerCardId),
//				encodingDataRecord.getStateroom(), encodingDataRecord.getRfId());
//		return objectMapper.writeValueAsString(joinerRequest);
//	}

    public VingRequest getVingRequest(String startTime, String expireTime, String door, String rfid) {

        DoorOperation doorOperation = new DoorOperation("guest", List.of(door));
        return new VingRequest(startTime, expireTime, RFID48, List.of(doorOperation), List.of(rfid));
    }

    public EncodeCardResponse getEncodedCard(final String vingRequest, final String accessKey, final String sessionId) {

        String currentDate = this.getCurrentDate();

        try {
            String md5 = this.getMD5(vingRequest);
            MultiValueMap<String, String> params = new LinkedMultiValueMap();
            params.add("autoJoin", Boolean.TRUE.toString());
            String partialUrl = this.generateURL("", "/api/v1/cards", params);
            String hmac = this.getAuthSignature(HttpMethod.POST.name(), md5, "application/json;charset=utf-8", currentDate, partialUrl, accessKey);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.generateURL("https://10.101.224.226","/api/v1/cards", params)))
                    .method("POST", HttpRequest.BodyPublishers.ofString(vingRequest))
                    .header("Content-MD5", md5)
                    .header("Content-type", "application/json;charset=utf-8")
                    .header("Date", currentDate)
                    .header("Authorization", "AWS " + sessionId + ":" + hmac)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            EncodeCardResponse encodeCardResponse = new Gson().fromJson(response.body(), EncodeCardResponse.class);
            return encodeCardResponse;

        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return null;
    }

    private String generateURL(String uri, final String urlPath, MultiValueMap<String, String> params) {
        UriComponentsBuilder uriComponentBuilder = UriComponentsBuilder.fromUriString(uri).path(urlPath);
        if (Objects.nonNull(params)) {
            uriComponentBuilder.queryParams(params);
        }

        return uriComponentBuilder.toUriString();
    }

//    class Door implements Serializable {
//
//        @SerializedName("allowOnOff")
//        @Expose
//        private Boolean allowOnOff;
//        @SerializedName("autoLock")
//        @Expose
//        private Boolean autoLock;
//        @SerializedName("doorCategory")
//        @Expose
//        private String doorCategory;
//        @SerializedName("doorGroup")
//        @Expose
//        private String doorGroup;
//        @SerializedName("doorGroupID")
//        @Expose
//        private Integer doorGroupID;
//        @SerializedName("doorID")
//        @Expose
//        private Integer doorID;
//        @SerializedName("doorName")
//        @Expose
//        private String doorName;
//        @SerializedName("doorType")
//        @Expose
//        private Integer doorType;
//        @SerializedName("escapeReturnMode")
//        @Expose
//        private Integer escapeReturnMode;
//        @SerializedName("exitButton")
//        @Expose
//        private Integer exitButton;
//        @SerializedName("exitButtonOpenTime")
//        @Expose
//        private Integer exitButtonOpenTime;
//        @SerializedName("externalRelayMode")
//        @Expose
//        private Boolean externalRelayMode;
//        @SerializedName("localName")
//        @Expose
//        private String localName;
//        @SerializedName("online")
//        @Expose
//        private Boolean online;
//        @SerializedName("openTime")
//        @Expose
//        private Integer openTime;
//        @SerializedName("roomIntervalStart")
//        @Expose
//        private Integer roomIntervalStart;
//        @SerializedName("roomIntervalStop")
//        @Expose
//        private Integer roomIntervalStop;
//        private final static long serialVersionUID = -6561371916239328110L;
//
//        public Boolean getAllowOnOff() {
//            return allowOnOff;
//        }
//
//        public void setAllowOnOff(Boolean allowOnOff) {
//            this.allowOnOff = allowOnOff;
//        }
//
//        public Boolean getAutoLock() {
//            return autoLock;
//        }
//
//        public void setAutoLock(Boolean autoLock) {
//            this.autoLock = autoLock;
//        }
//
//        public String getDoorCategory() {
//            return doorCategory;
//        }
//
//        public void setDoorCategory(String doorCategory) {
//            this.doorCategory = doorCategory;
//        }
//
//        public String getDoorGroup() {
//            return doorGroup;
//        }
//
//        public void setDoorGroup(String doorGroup) {
//            this.doorGroup = doorGroup;
//        }
//
//        public Integer getDoorGroupID() {
//            return doorGroupID;
//        }
//
//        public void setDoorGroupID(Integer doorGroupID) {
//            this.doorGroupID = doorGroupID;
//        }
//
//        public Integer getDoorID() {
//            return doorID;
//        }
//
//        public void setDoorID(Integer doorID) {
//            this.doorID = doorID;
//        }
//
//        public String getDoorName() {
//            return doorName;
//        }
//
//        public void setDoorName(String doorName) {
//            this.doorName = doorName;
//        }
//
//        public Integer getDoorType() {
//            return doorType;
//        }
//
//        public void setDoorType(Integer doorType) {
//            this.doorType = doorType;
//        }
//
//        public Integer getEscapeReturnMode() {
//            return escapeReturnMode;
//        }
//
//        public void setEscapeReturnMode(Integer escapeReturnMode) {
//            this.escapeReturnMode = escapeReturnMode;
//        }
//
//        public Integer getExitButton() {
//            return exitButton;
//        }
//
//        public void setExitButton(Integer exitButton) {
//            this.exitButton = exitButton;
//        }
//
//        public Integer getExitButtonOpenTime() {
//            return exitButtonOpenTime;
//        }
//
//        public void setExitButtonOpenTime(Integer exitButtonOpenTime) {
//            this.exitButtonOpenTime = exitButtonOpenTime;
//        }
//
//        public Boolean getExternalRelayMode() {
//            return externalRelayMode;
//        }
//
//        public void setExternalRelayMode(Boolean externalRelayMode) {
//            this.externalRelayMode = externalRelayMode;
//        }
//
//        public String getLocalName() {
//            return localName;
//        }
//
//        public void setLocalName(String localName) {
//            this.localName = localName;
//        }
//
//        public Boolean getOnline() {
//            return online;
//        }
//
//        public void setOnline(Boolean online) {
//            this.online = online;
//        }
//
//        public Integer getOpenTime() {
//            return openTime;
//        }
//
//        public void setOpenTime(Integer openTime) {
//            this.openTime = openTime;
//        }
//
//        public Integer getRoomIntervalStart() {
//            return roomIntervalStart;
//        }
//
//        public void setRoomIntervalStart(Integer roomIntervalStart) {
//            this.roomIntervalStart = roomIntervalStart;
//        }
//
//        public Integer getRoomIntervalStop() {
//            return roomIntervalStop;
//        }
//
//        public void setRoomIntervalStop(Integer roomIntervalStop) {
//            this.roomIntervalStop = roomIntervalStop;
//        }
//
//    }
}
