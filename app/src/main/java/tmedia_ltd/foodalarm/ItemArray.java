package tmedia_ltd.foodalarm;

import java.util.ArrayList;

public class ItemArray {
    public String name;
    public String hometown;

    public ItemArray(String name, String hometown) {
        this.name = name;
        this.hometown = hometown;
    }

    public static ArrayList<ItemArray> getUsers() {
        ArrayList<ItemArray> users = new ArrayList<ItemArray>();
        users.add(new ItemArray("Harry", "San Diego"));
        users.add(new ItemArray("Marla", "San Francisco"));
        users.add(new ItemArray("Sarah", "San Marco"));
        return users;
    }
}