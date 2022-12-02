package com.example.shopmaster.datahandler;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Grocery {
    private Integer item_id;
    private String cate;
    private String name;
    private String price;
    private String imgurl;
    private String store;
    private Integer quantity=1;
    private String date="-1";
    private static Map<String[], Integer> travelTimeDict = new HashMap<>();
    public static String [] cateList = {"Meat & Seafood","Bakery & Bread","Produce"};
    public static String [] storeList = {"Target","County Market","Walmart","Costco"};

    public Grocery(){
        String[] countyTarget = {"County Market", "Target"};
        String[] countyWalmart = {"County Market", "Walmart"};
        String[] countyCostco = {"County Market", "Costco"};
        String[] targetWalmart = {"County Market", "Walmart"};
        String[] targetCostco = {"County Market", "Costco"};
        String[] walmartCostco = {"Walmart", "Costco"};

        travelTimeDict.put(countyTarget,3);
        travelTimeDict.put(countyWalmart,9);
        travelTimeDict.put(countyCostco,8);
        travelTimeDict.put(walmartCostco,4);
        travelTimeDict.put(targetCostco,10);
        travelTimeDict.put(targetWalmart,12);
    }

    public Integer getId() {
        return item_id;
    }
    public void setId(Integer item_id) {
        this.item_id = item_id;
    }

    public String getCate() {
        return cate;
    }
    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgurl;
    }
    public void setImgUrl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getStore() {
        return store;
    }
    public void setStore(String store) {
        this.store = store;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getHistDate() {
        return date;
    }
    public void setHistDate(String date) {
        this.date = date;
    }

    public static String [] getCateList(){return cateList;}
    public static String [] getStoreList(){return storeList;}

    @NonNull
    public String toString() {
        return "Grocery Item--->"+"Id: "+item_id+",  name: "+name+", category: "+cate
                +", Store: "+store+", Quantity: "+quantity+", History Date: "+date;
    }

    public static class SortbyStoreCate implements Comparator<Grocery>
    {

        @Override
        public int compare(Grocery g1, Grocery g2) {
            int c = g1.getStore().compareTo(g2.getStore());
            if(c==0){
                c = g1.getCate().compareTo(g2.getCate());
            }
            return c;
        }
    }

    public static List<String> getAllKeywords(){
        String[] str = {"muffins", "cake", "cheesecake", "carrot", "tiramisu", "donut", "cookie",
                "bread", "garlic", "pie", "sugar", "banana", "nut", "brownies", "mousse", "buns",
                "rolls", "croissants", "bagels", "pancakes", "pastries", "taco", "shrimp", "bean",
                "mussels", "turkey", "fillets", "steaks", "salmon", "chicken", "salmon", "burgers",
                "beef", "bacon", "meatballs", "pork", "ham", "tofu", "sirloin", "duck", "crab",
                "salad", "oysters", "tomato", "mango", "onions", "potatoes", "corn", "apples",
                "peppers", "lettuce", "lemon", "avocado", "melon", "blueberries", "eggplant",
                "broccoli"};
        return Arrays.asList(str);
    }

    public static Integer getTravalTime(String store1, String store2) throws IOException {
        if(store1.equals(store2)){return 0;}
        int travelTime = 0;
        if(!Arrays.asList(storeList).contains(store1)||!Arrays.asList(storeList).contains(store2))
        {
            throw new IOException("Input stores do not exist.");
        }else{
            for (String[] storePair : travelTimeDict.keySet()){
                if (Arrays.asList(storePair).contains(store1)
                        &&Arrays.asList(storePair).contains(store2)){
                    travelTime = travelTimeDict.get(storePair);
                    break;
                }
            }
        }
        return travelTime;

    }
}