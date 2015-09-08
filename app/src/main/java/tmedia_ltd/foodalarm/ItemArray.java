package tmedia_ltd.foodalarm;

import java.util.ArrayList;

public class ItemArray {
    public String name;
    public String hometown;

    public ItemArray(String name, String hometown) {
        this.name = name;
        this.hometown = hometown;
    }

    static ArrayList<ItemArray> users = new ArrayList<ItemArray>();

    public static ArrayList<ItemArray> getUsers() {
        return users;
    }

   // public static ArrayList<ItemArray> AddItem(ArrayList<ItemArray> NewItem)
   public static ArrayList<ItemArray> AddItem(String NewItem)
    {
        users.add(new ItemArray(NewItem, "San Diego"));
        return users;
    }

    public static ArrayList<ItemArray> RemoveItem(int DeletedItem)
    {
        users.remove(DeletedItem);
        //users.add(new ItemArray(NewItem, "San Diego"));
        return users;
    }
}