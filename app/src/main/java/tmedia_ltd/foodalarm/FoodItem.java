package tmedia_ltd.foodalarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by will on 9/20/2015.
 */
public class FoodItem {

    private int id;
    private String name;
    private Long expiry;



    private String quantity;
    private String price;

    public FoodItem(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getExpiry() {
        Date d = new Date(expiry);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedExpiryDate = df.format(d);
        return formattedExpiryDate;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public FoodItem(String name, Long expiry, String quantity, String price) {
        super();
        this.name = name;

        this.expiry = expiry;
        this.quantity = quantity;
        this.price = price;
    }

    //getters & setters

    @Override
    public String toString() {
        return "FoodItem [id=" + id + ", name=" + name + ", expiry=" + expiry.toString() + ", quantity=" + quantity + ", price=" + price
                + "]";
    }
}