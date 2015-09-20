package tmedia_ltd.foodalarm;

import java.util.ArrayList;

public class ItemArray {
    public String name;
    public String expiry;
    public String quantity;
    public String price;
    public int id;

    public ItemArray(String name, String expiry) {
        this.name = name;
        this.expiry= expiry;
        this.quantity = quantity;
        this.price = price;
    }

    static ArrayList<ItemArray> users = new ArrayList<ItemArray>();

    public static ArrayList<ItemArray> getUsers() {
        return users;
    }

  // public static ArrayList<ItemArray> AddItem(ArrayList<ItemArray> NewItem)
   public static ArrayList<ItemArray> AddItem(String NewItem)
    {
       users.add(new ItemArray(NewItem, "San Diego"));
        //users.add(new ItemArray(NewItem));
        return users;
    }

    public static ArrayList<ItemArray> RemoveItem(int DeletedItem)
    {
        users.remove(DeletedItem);
        //users.add(new ItemArray(NewItem, "San Diego"));
        return users;
    }
}