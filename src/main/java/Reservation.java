import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Reservation implements Serializable
{

    @SerializedName("payments")
    @Expose
    private List<Object> payments = null;
    @SerializedName("sailingDetails")
    @Expose
    private SailingDetails sailingDetails;
    @SerializedName("reservationID")
    @Expose
    private String reservationID;
    @SerializedName("reservationDate")
    @Expose
    private String reservationDate;
    @SerializedName("sailors")
    @Expose
    private List<Sailor> sailors = null;
    @SerializedName("voyagePackage")
    @Expose
    private VoyagePackage voyagePackage;
    @SerializedName("reservationStatus")
    @Expose
    private String reservationStatus;
    private final static long serialVersionUID = 3351539740148254130L;

    public List<Object> getPayments() {
        return payments;
    }

    public void setPayments(List<Object> payments) {
        this.payments = payments;
    }

    public SailingDetails getSailingDetails() {
        return sailingDetails;
    }

    public void setSailingDetails(SailingDetails sailingDetails) {
        this.sailingDetails = sailingDetails;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public List<Sailor> getSailors() {
        return sailors;
    }

    public void setSailors(List<Sailor> sailors) {
        this.sailors = sailors;
    }

    public VoyagePackage getVoyagePackage() {
        return voyagePackage;
    }

    public void setVoyagePackage(VoyagePackage voyagePackage) {
        this.voyagePackage = voyagePackage;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }


    public class ReservationAddon implements Serializable
    {

        @SerializedName("componentType")
        @Expose
        private String componentType;
        @SerializedName("componentSubcode2")
        @Expose
        private String componentSubcode2;
        @SerializedName("componentPackageType")
        @Expose
        private Object componentPackageType;
        @SerializedName("addonCode")
        @Expose
        private String addonCode;
        @SerializedName("componentSubcode1")
        @Expose
        private Object componentSubcode1;
        @SerializedName("componentCode")
        @Expose
        private String componentCode;
        @SerializedName("componentSubcode3")
        @Expose
        private Object componentSubcode3;
        @SerializedName("componentPackageClass")
        @Expose
        private String componentPackageClass;
        private final static long serialVersionUID = 747683804726194547L;

        public String getComponentType() {
            return componentType;
        }

        public void setComponentType(String componentType) {
            this.componentType = componentType;
        }

        public String getComponentSubcode2() {
            return componentSubcode2;
        }

        public void setComponentSubcode2(String componentSubcode2) {
            this.componentSubcode2 = componentSubcode2;
        }

        public Object getComponentPackageType() {
            return componentPackageType;
        }

        public void setComponentPackageType(Object componentPackageType) {
            this.componentPackageType = componentPackageType;
        }

        public String getAddonCode() {
            return addonCode;
        }

        public void setAddonCode(String addonCode) {
            this.addonCode = addonCode;
        }

        public Object getComponentSubcode1() {
            return componentSubcode1;
        }

        public void setComponentSubcode1(Object componentSubcode1) {
            this.componentSubcode1 = componentSubcode1;
        }

        public String getComponentCode() {
            return componentCode;
        }

        public void setComponentCode(String componentCode) {
            this.componentCode = componentCode;
        }

        public Object getComponentSubcode3() {
            return componentSubcode3;
        }

        public void setComponentSubcode3(Object componentSubcode3) {
            this.componentSubcode3 = componentSubcode3;
        }

        public String getComponentPackageClass() {
            return componentPackageClass;
        }

        public void setComponentPackageClass(String componentPackageClass) {
            this.componentPackageClass = componentPackageClass;
        }

    }

    public class SailingDetails implements Serializable
    {

        @SerializedName("startPort")
        @Expose
        private String startPort;
        @SerializedName("endPort")
        @Expose
        private String endPort;
        @SerializedName("sailingEnd")
        @Expose
        private String sailingEnd;
        @SerializedName("shipCode")
        @Expose
        private String shipCode;
        @SerializedName("cabinCategory")
        @Expose
        private String cabinCategory;
        @SerializedName("sailingID")
        @Expose
        private String sailingID;
        @SerializedName("sailingStart")
        @Expose
        private String sailingStart;
        private final static long serialVersionUID = -603088053326336657L;

        public String getStartPort() {
            return startPort;
        }

        public void setStartPort(String startPort) {
            this.startPort = startPort;
        }

        public String getEndPort() {
            return endPort;
        }

        public void setEndPort(String endPort) {
            this.endPort = endPort;
        }

        public String getSailingEnd() {
            return sailingEnd;
        }

        public void setSailingEnd(String sailingEnd) {
            this.sailingEnd = sailingEnd;
        }

        public String getShipCode() {
            return shipCode;
        }

        public void setShipCode(String shipCode) {
            this.shipCode = shipCode;
        }

        public String getCabinCategory() {
            return cabinCategory;
        }

        public void setCabinCategory(String cabinCategory) {
            this.cabinCategory = cabinCategory;
        }

        public String getSailingID() {
            return sailingID;
        }

        public void setSailingID(String sailingID) {
            this.sailingID = sailingID;
        }

        public String getSailingStart() {
            return sailingStart;
        }

        public void setSailingStart(String sailingStart) {
            this.sailingStart = sailingStart;
        }

    }

    public static class Sailor implements Serializable
    {

        @SerializedName("gender")
        @Expose
        private Object gender;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("cabinNumber")
        @Expose
        private Object cabinNumber;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("reservationAddons")
        @Expose
        private List<ReservationAddon> reservationAddons = null;
        @SerializedName("guestSequenceNumber")
        @Expose
        private String guestSequenceNumber;
        @SerializedName("dateOfBirth")
        @Expose
        private Object dateOfBirth;
        @SerializedName("clientAddresses")
        @Expose
        private List<ClientAddress> clientAddresses = null;
        @SerializedName("paymentDueItems")
        @Expose
        private List<PaymentDueItem> paymentDueItems = null;
        @SerializedName("guestID")
        @Expose
        private String guestID;
        @SerializedName("clientEmail")
        @Expose
        private String clientEmail;
        @SerializedName("guestType")
        @Expose
        private String guestType;
        @SerializedName("title")
        @Expose
        private Object title;
        @SerializedName("middleName")
        @Expose
        private Object middleName;
        @SerializedName("invoiceItems")
        @Expose
        private List<InvoiceItem> invoiceItems = null;
        @SerializedName("clientID")
        @Expose
        private String clientID;
        @SerializedName("ageCategory")
        @Expose
        private Object ageCategory;
        private final static long serialVersionUID = -3545241594857946484L;

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Object getCabinNumber() {
            return cabinNumber;
        }

        public void setCabinNumber(Object cabinNumber) {
            this.cabinNumber = cabinNumber;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public List<ReservationAddon> getReservationAddons() {
            return reservationAddons;
        }

        public void setReservationAddons(List<ReservationAddon> reservationAddons) {
            this.reservationAddons = reservationAddons;
        }

        public String getGuestSequenceNumber() {
            return guestSequenceNumber;
        }

        public void setGuestSequenceNumber(String guestSequenceNumber) {
            this.guestSequenceNumber = guestSequenceNumber;
        }

        public Object getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Object dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public List<ClientAddress> getClientAddresses() {
            return clientAddresses;
        }

        public void setClientAddresses(List<ClientAddress> clientAddresses) {
            this.clientAddresses = clientAddresses;
        }

        public List<PaymentDueItem> getPaymentDueItems() {
            return paymentDueItems;
        }

        public void setPaymentDueItems(List<PaymentDueItem> paymentDueItems) {
            this.paymentDueItems = paymentDueItems;
        }

        public String getGuestID() {
            return guestID;
        }

        public void setGuestID(String guestID) {
            this.guestID = guestID;
        }

        public String getClientEmail() {
            return clientEmail;
        }

        public void setClientEmail(String clientEmail) {
            this.clientEmail = clientEmail;
        }

        public String getGuestType() {
            return guestType;
        }

        public void setGuestType(String guestType) {
            this.guestType = guestType;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public Object getMiddleName() {
            return middleName;
        }

        public void setMiddleName(Object middleName) {
            this.middleName = middleName;
        }

        public List<InvoiceItem> getInvoiceItems() {
            return invoiceItems;
        }

        public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
            this.invoiceItems = invoiceItems;
        }

        public String getClientID() {
            return clientID;
        }

        public void setClientID(String clientID) {
            this.clientID = clientID;
        }

        public Object getAgeCategory() {
            return ageCategory;
        }

        public void setAgeCategory(Object ageCategory) {
            this.ageCategory = ageCategory;
        }

    }

    public class VoyagePackage implements Serializable
    {

        @SerializedName("packageType")
        @Expose
        private String packageType;
        @SerializedName("packageCode")
        @Expose
        private String packageCode;
        @SerializedName("packageID")
        @Expose
        private String packageID;
        private final static long serialVersionUID = 988749132443862116L;

        public String getPackageType() {
            return packageType;
        }

        public void setPackageType(String packageType) {
            this.packageType = packageType;
        }

        public String getPackageCode() {
            return packageCode;
        }

        public void setPackageCode(String packageCode) {
            this.packageCode = packageCode;
        }

        public String getPackageID() {
            return packageID;
        }

        public void setPackageID(String packageID) {
            this.packageID = packageID;
        }

    }

    public class ClientAddress implements Serializable
    {

        @SerializedName("addressCountry")
        @Expose
        private String addressCountry;
        @SerializedName("addressLine4")
        @Expose
        private Object addressLine4;
        @SerializedName("addressLine1")
        @Expose
        private String addressLine1;
        @SerializedName("addressCity")
        @Expose
        private String addressCity;
        @SerializedName("addressMailingFlag")
        @Expose
        private String addressMailingFlag;
        @SerializedName("addressSequenceNumber")
        @Expose
        private Object addressSequenceNumber;
        @SerializedName("addressState")
        @Expose
        private String addressState;
        @SerializedName("addressZIP")
        @Expose
        private String addressZIP;
        @SerializedName("addressShippingFlag")
        @Expose
        private String addressShippingFlag;
        @SerializedName("addressType")
        @Expose
        private String addressType;
        @SerializedName("addressLine2")
        @Expose
        private Object addressLine2;
        @SerializedName("addressLine3")
        @Expose
        private Object addressLine3;
        private final static long serialVersionUID = 4265956651733745667L;

        public String getAddressCountry() {
            return addressCountry;
        }

        public void setAddressCountry(String addressCountry) {
            this.addressCountry = addressCountry;
        }

        public Object getAddressLine4() {
            return addressLine4;
        }

        public void setAddressLine4(Object addressLine4) {
            this.addressLine4 = addressLine4;
        }

        public String getAddressLine1() {
            return addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public String getAddressCity() {
            return addressCity;
        }

        public void setAddressCity(String addressCity) {
            this.addressCity = addressCity;
        }

        public String getAddressMailingFlag() {
            return addressMailingFlag;
        }

        public void setAddressMailingFlag(String addressMailingFlag) {
            this.addressMailingFlag = addressMailingFlag;
        }

        public Object getAddressSequenceNumber() {
            return addressSequenceNumber;
        }

        public void setAddressSequenceNumber(Object addressSequenceNumber) {
            this.addressSequenceNumber = addressSequenceNumber;
        }

        public String getAddressState() {
            return addressState;
        }

        public void setAddressState(String addressState) {
            this.addressState = addressState;
        }

        public String getAddressZIP() {
            return addressZIP;
        }

        public void setAddressZIP(String addressZIP) {
            this.addressZIP = addressZIP;
        }

        public String getAddressShippingFlag() {
            return addressShippingFlag;
        }

        public void setAddressShippingFlag(String addressShippingFlag) {
            this.addressShippingFlag = addressShippingFlag;
        }

        public String getAddressType() {
            return addressType;
        }

        public void setAddressType(String addressType) {
            this.addressType = addressType;
        }

        public Object getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(Object addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public Object getAddressLine3() {
            return addressLine3;
        }

        public void setAddressLine3(Object addressLine3) {
            this.addressLine3 = addressLine3;
        }

    }

    public class InvoiceItem implements Serializable
    {

        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("commissionPercent")
        @Expose
        private Object commissionPercent;
        @SerializedName("itemSubtype1")
        @Expose
        private Object itemSubtype1;
        @SerializedName("itemSubtype3")
        @Expose
        private Object itemSubtype3;
        @SerializedName("itemSubtype2")
        @Expose
        private Object itemSubtype2;
        @SerializedName("itemType")
        @Expose
        private String itemType;
        private final static long serialVersionUID = 7237885585868693374L;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Object getCommissionPercent() {
            return commissionPercent;
        }

        public void setCommissionPercent(Object commissionPercent) {
            this.commissionPercent = commissionPercent;
        }

        public Object getItemSubtype1() {
            return itemSubtype1;
        }

        public void setItemSubtype1(Object itemSubtype1) {
            this.itemSubtype1 = itemSubtype1;
        }

        public Object getItemSubtype3() {
            return itemSubtype3;
        }

        public void setItemSubtype3(Object itemSubtype3) {
            this.itemSubtype3 = itemSubtype3;
        }

        public Object getItemSubtype2() {
            return itemSubtype2;
        }

        public void setItemSubtype2(Object itemSubtype2) {
            this.itemSubtype2 = itemSubtype2;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

    }

    public class PaymentDueItem implements Serializable
    {

        @SerializedName("itemType")
        @Expose
        private String itemType;
        @SerializedName("itemDueDate")
        @Expose
        private String itemDueDate;
        @SerializedName("gracePeriod")
        @Expose
        private String gracePeriod;
        @SerializedName("expirationDate")
        @Expose
        private String expirationDate;
        @SerializedName("amount")
        @Expose
        private String amount;
        private final static long serialVersionUID = -7292766495790495782L;

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getItemDueDate() {
            return itemDueDate;
        }

        public void setItemDueDate(String itemDueDate) {
            this.itemDueDate = itemDueDate;
        }

        public String getGracePeriod() {
            return gracePeriod;
        }

        public void setGracePeriod(String gracePeriod) {
            this.gracePeriod = gracePeriod;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

    }


}
