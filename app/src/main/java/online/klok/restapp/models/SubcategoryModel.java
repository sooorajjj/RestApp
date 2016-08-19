package online.klok.restapp.models;

/**
 * Created by klok on 19/8/16.
 */
public class SubcategoryModel {

    /**
     * id : 1
     * categoryId : 1
     * name : Bear
     * shortName : be
     * created_at : 2016-08-15 21:33:30
     * updated_at : 2016-08-15 21:33:30
     */

    private int id;
    private int categoryId;
    private String name;
    private String shortName;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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
