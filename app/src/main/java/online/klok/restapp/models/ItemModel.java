package online.klok.restapp.models;

/**
 * Created by klok on 20/8/16.
 */
public class ItemModel {

    /**
     * id : 1
     * itemId : 1
     * itemName : King Fisher
     * description : qwertyuiopasdfghhjkl
     * salesRate : 90.00
     * otherLang : French
     * categoryId : 1
     * subCategoryId : 1
     * created_at : 2016-08-18 05:42:12
     * updated_at : 2016-08-18 05:42:12
     */

    private int id;
    private String itemId;
    private String itemName;
    private String description;
    private String salesRate;
    private String otherLang;
    private int categoryId;
    private int subCategoryId;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalesRate() {
        return salesRate;
    }

    public void setSalesRate(String salesRate) {
        this.salesRate = salesRate;
    }

    public String getOtherLang() {
        return otherLang;
    }

    public void setOtherLang(String otherLang) {
        this.otherLang = otherLang;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
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
