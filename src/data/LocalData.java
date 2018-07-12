package data;

import Item.City;
import Item.Flight;
import Item.OrderListItem;
import Item.UserItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;

import java.util.ArrayList;
import java.util.List;

public class LocalData {
    public static ObservableList<OrderListItem> list = FXCollections.observableArrayList();
    public static ObservableList<Flight> flights=FXCollections.observableArrayList();
    public static List<City> cities = new ArrayList<>();
    public static String reTimeStart=null;
    public static String reTimeEnd=null;
    public static List<String> userItems = new ArrayList<>();
    public static List<UserItem> users = new ArrayList<>();
    public static void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerData.getOrderListItem(list,null,null,null);
                ServerData.getFlights(flights);
                ServerData.getUsers(users);
            }
        }).start();


    }

    public static UserItem getuser(String name){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUname().equals(name))
                return users.get(i);
        }
        return null;
    }

    public static int getCityId(String name){
        for(int i=0;i<cities.size();i++)
            if(cities.get(i).name.equals(name))
                return cities.get(i).i;
        return -1;
    }
}
