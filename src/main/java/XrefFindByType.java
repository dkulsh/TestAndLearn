import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XrefFindByType {

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    private Map<String, String> body = new HashMap();

    public static void main(String[] args) throws Exception {

        XrefFindByType obj = new XrefFindByType();
        obj.body.put("referenceTypeID", "b464da30-1aef-11e9-8cf3-12c2067a6ce2");

        try(BufferedReader b = new BufferedReader(new FileReader(new File("/Users/deep.kulshreshtha/Downloads/ClientId.txt")))) {

            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                System.out.println(readLine);

                obj.sendPost(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPost(String cliendId) throws IOException, InterruptedException {

        body.put("nativeSourceIDValue", cliendId);

        String bodyString = new Gson().toJson(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://prod.virginvoyages.com/svc/xref-api/v1/references/search/findByType"))
                .method("POST", HttpRequest.BodyPublishers.ofString(bodyString))
                .header("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55aWQiOiJiMzVhMzI1YS0wMzZhLTRhZDQtODgzNi02NDY5MjNlNjAxZGUiLCJzY29wZSI6WyJyZWFkIiwidHJ1c3QiLCJ3cml0ZSJdLCJleHAiOjE1ODQxOTMxNzUsInRva2VuVHlwZSI6ImNsaWVudFRva2VuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9UUlVTVEVEX0NMSUVOVCJdLCJqdGkiOiJkOTAxOWJlZS0zNjU2LTQ3MjEtODhmNy1kNjZkMTc0ZWE2YzEiLCJjbGllbnRfaWQiOiJiM2MyYjA3Yy04NjkwLTExZTktYmQ4ZS0wYTFhNDI2MWU5NjIifQ.DvAZCVi6CynYfjV47-JXbBdw4xCLBOjq-gn7arciWHYvQWnji4QlUqHpOi1Wm2mq413tq-jfFOXYEmkYOGIi5eodT460rVzwMFR_ivgkz6fy6Uigs1BplPNAlegRF4UQAtus9jybjRnU-yMmoKYVYeOyEai57-JomGrQJWa369xt1Pux_Fev8_eidBLfiBx7zQklnNYGY4urywOd2rtz-uY2vTCoVW5l0DRbx_7GxiY_TmNaulw1IhIV1aAFODQtLZ78rVPMbWI9RSGNzP1whHoOSpyoewhyNgcBESFESjfsuJwTUQ6eb7jioV4t5516Zn94od0rmWIg9JgQ0GAzFg")
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        IDCR idcr = new Gson().fromJson(response.body(), IDCR.class);
        String vxpGuestIds = idcr.getEmbedded().getReferences().stream().filter(reference -> reference.getReferenceType().equals("VXP - Guest"))
                .map(reference -> reference.getNativeSourceIDValue())
                .collect(Collectors.joining(", "));

        writeToFile(cliendId, vxpGuestIds);

    }

    private void writeToFile(String clientId, String vxpGuestIds){

        try(BufferedWriter b = new BufferedWriter(new FileWriter(new File("/Users/deep.kulshreshtha/Downloads/ClientIdToVXPGuest.txt"), true))) {
            b.write(clientId + ", " + vxpGuestIds);
            b.newLine();
            b.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    class IDCR implements Serializable {

        @SerializedName("_links")
        @Expose
        private Object links;
        @SerializedName("page")
        @Expose
        private Object page;
        @SerializedName("_embedded")
        @Expose
        private Embedded embedded;
        private final static long serialVersionUID = -6034037615911406324L;

        public Object getLinks() {
            return links;
        }

        public void setLinks(Object links) {
            this.links = links;
        }

        public Object getPage() {
            return page;
        }

        public void setPage(Object page) {
            this.page = page;
        }

        public Embedded getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Embedded embedded) {
            this.embedded = embedded;
        }

        public class Embedded implements Serializable
        {

            @SerializedName("references")
            @Expose
            private List<Reference> references = null;
            private final static long serialVersionUID = -3895442285319094528L;

            public List<Reference> getReferences() {
                return references;
            }

            public void setReferences(List<Reference> references) {
                this.references = references;
            }

        }

        public class Reference implements Serializable
        {

            @SerializedName("referenceID")
            @Expose
            private String referenceID;
            @SerializedName("nativeSourceIDValue")
            @Expose
            private String nativeSourceIDValue;
            @SerializedName("masterID")
            @Expose
            private String masterID;
            @SerializedName("referenceTypeID")
            @Expose
            private String referenceTypeID;
            @SerializedName("targetReferenceTypeID")
            @Expose
            private Object targetReferenceTypeID;
            @SerializedName("referenceType")
            @Expose
            private String referenceType;
            @SerializedName("created")
            @Expose
            private String created;
            @SerializedName("modified")
            @Expose
            private String modified;
            private final static long serialVersionUID = -8901887040659461426L;

            public String getReferenceID() {
                return referenceID;
            }

            public void setReferenceID(String referenceID) {
                this.referenceID = referenceID;
            }

            public String getNativeSourceIDValue() {
                return nativeSourceIDValue;
            }

            public void setNativeSourceIDValue(String nativeSourceIDValue) {
                this.nativeSourceIDValue = nativeSourceIDValue;
            }

            public String getMasterID() {
                return masterID;
            }

            public void setMasterID(String masterID) {
                this.masterID = masterID;
            }

            public String getReferenceTypeID() {
                return referenceTypeID;
            }

            public void setReferenceTypeID(String referenceTypeID) {
                this.referenceTypeID = referenceTypeID;
            }

            public Object getTargetReferenceTypeID() {
                return targetReferenceTypeID;
            }

            public void setTargetReferenceTypeID(Object targetReferenceTypeID) {
                this.targetReferenceTypeID = targetReferenceTypeID;
            }

            public String getReferenceType() {
                return referenceType;
            }

            public void setReferenceType(String referenceType) {
                this.referenceType = referenceType;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }

        }

    }

}
