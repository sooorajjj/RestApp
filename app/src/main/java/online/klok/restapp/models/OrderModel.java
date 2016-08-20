package online.klok.restapp.models;

/**
 * Created by klok on 20/8/16.
 */
public class OrderModel {

    /**
     * id : 1
     * orderId : 1
     * orderTotal : 3
     * cardNo : 1
     * totalItemsCount : 3
     * primaryItem : 1
     * extraItem : 1
     * modifierItem : 1
     * locationId : 1
     * time : 10:10:10 am
     * date : 14/8/16
     * created_at : 2016-08-18 05:42:12
     * updated_at : 2016-08-18 05:42:12
     */

    private int id;
    private String orderId;
    private String orderTotal;
    private String cardNo;
    private String totalItemsCount;
    private int primaryItem;
    private int extraItem;
    private int modifierItem;
    private int locationId;
    private String time;
    private String date;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTotalItemsCount() {
        return totalItemsCount;
    }

    public void setTotalItemsCount(String totalItemsCount) {
        this.totalItemsCount = totalItemsCount;
    }

    public int getPrimaryItem() {
        return primaryItem;
    }

    public void setPrimaryItem(int primaryItem) {
        this.primaryItem = primaryItem;
    }

    public int getExtraItem() {
        return extraItem;
    }

    public void setExtraItem(int extraItem) {
        this.extraItem = extraItem;
    }

    public int getModifierItem() {
        return modifierItem;
    }

    public void setModifierItem(int modifierItem) {
        this.modifierItem = modifierItem;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
