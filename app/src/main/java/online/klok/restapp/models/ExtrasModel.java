package online.klok.restapp.models;

/**
 * Created by klok on 20/8/16.
 */
public class ExtrasModel {

    /**
     * id : 1
     * extrasId : 1
     * item : 1
     * extrasName : FrenchFrice
     * shortName : ff
     * description : decribeeeee0
     * salesRate : 10
     * created_at : 2016-08-18 05:42:12
     * updated_at : 2016-08-18 05:42:12
     */

    private int id;
    private String extrasId;
    private int item;
    private String extrasName;
    private String shortName;
    private String description;
    private int salesRate;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExtrasId() {
        return extrasId;
    }

    public void setExtrasId(String extrasId) {
        this.extrasId = extrasId;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getExtrasName() {
        return extrasName;
    }

    public void setExtrasName(String extrasName) {
        this.extrasName = extrasName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSalesRate() {
        return salesRate;
    }

    public void setSalesRate(int salesRate) {
        this.salesRate = salesRate;
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
