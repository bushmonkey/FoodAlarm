package tmedia_ltd.foodalarm;

/**
 * Created by will on 9/16/2015.
 */
public class FoodTable_SQLlite {
    private long id;
    private String FoodDetail;

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFoodDetail() {
        return FoodDetail;
    }

    public void setFoodDetail(String foodDetail) {
        this.FoodDetail = foodDetail;
    }

    public String toString() {
        return FoodDetail;
    }
}
