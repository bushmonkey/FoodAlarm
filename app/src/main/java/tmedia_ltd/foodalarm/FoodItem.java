package tmedia_ltd.foodalarm;

/**
 * Created by will on 9/20/2015.
 */
public class FoodItem {

    private int id;
    private String name;
    private String expiry;



    private String quantity;
    private String price;

    public FoodItem(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public FoodItem(String name, String expiry, String quantity, String price) {
        super();
        this.name = name;

        this.expiry = expiry;
        this.quantity = quantity;
        this.price = price;
    }

    //getters & setters

    @Override
    public String toString() {
        return "FoodItem [id=" + id + ", name=" + name + ", expiry=" + expiry + ", quantity=" + quantity + ", price=" + price
                + "]";
    }
}