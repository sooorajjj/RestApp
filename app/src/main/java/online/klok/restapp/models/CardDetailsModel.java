package online.klok.restapp.models;

/**
 * Created by klok on 20/8/16.
 */
public class CardDetailsModel {

    /**
     * id : 1
     * cardId : 1
     * cardHolderName : name1
     * contactNo : 9638527410
     * address : address1
     * created_at : 2016-08-18 05:42:12
     * updated_at : 2016-08-18 05:42:12
     */

    private int id;
    private String cardId;
    private String cardHolderName;
    private String contactNo;
    private String address;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
