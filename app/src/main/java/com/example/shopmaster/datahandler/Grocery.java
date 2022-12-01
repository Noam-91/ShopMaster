package com.example.shopmaster.datahandler;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Grocery {
    private Integer item_id;
    private String cate;
    private String name;
    private String price;
    private String imgurl;
    private String store;
    private Integer quantity=1;
    private String date="-1";
    public static String [] cateList = {"Meat & Seafood","Bakery & Bread","Produce"};
    public static String [] storeList = {"Target","County Market","Walmart","Costco"};

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
        String[] str = {"Muffins","Cake","Cheesecake","Carrot","Tiramisu","Donut","Cookie","Bread",
                "Garlic","Pie","Sugar","Banana","Nut","Brownies","Mousse","Buns","Rolls",
                "Croissants","Bagels","Pancakes","Pastries","Taco","Shrimp","Bean","Mussels",
                "Turkey","Fillets","Steaks","Salmon","Chicken","Salmon","Burgers","Beef","Bacon",
                "Meatballs","Pork","Ham","Tofu","Sirloin","Duck","Crab","Salad","Oysters","Tomato",
                "Mango","Onions","Potatoes","Corn","Apples","Peppers","Lettuce","Lemon","Avocado",
                "Melon","Blueberries","Eggplant","Broccoli" };
        return Arrays.asList(str);
    }
}