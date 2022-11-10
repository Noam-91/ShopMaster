package com.example.shopmaster.datahandler;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PlanCalculator {
    private final List<String> shopList;
    private final String primaryFactor;
    private final Integer numOfStops;
    private final Context context;
    String[] allStores = {"Target","Walmart","Costco","County Market"};

    public PlanCalculator(Context context,List<String> shopList,String primaryFactor,Integer numOfStops){
        this.shopList=shopList;
        this.primaryFactor=primaryFactor;
        this.numOfStops=numOfStops;
        this.context = context;

    }

    /**
     * Mock-up Function.
     * Ideally: First find alternatives in all stores.
     * Then calculate by time or money along with number of stops.
     * @return A list of grocery
     */
    public List<Grocery> calculate()
    {
        if (shopList.size()==0){
            throw new ArrayIndexOutOfBoundsException("Shopping list is empty.");
        }
        if (!(primaryFactor.equals("time")||primaryFactor.equals("money"))){
            throw new IllegalArgumentException("The primary factor should be either time or money.");
        }
        if (numOfStops<1 || numOfStops>4){
            throw new IllegalArgumentException("The number of stops should be between 1 and 4.");
        }

        List<Grocery> resultList = new ArrayList<>();
        if (primaryFactor.equals("time")){
            String[] stores = {"Target", "County Market","",""};
            if(numOfStops==1){stores = new String[]{"Target","","",""};}

            DBServer db = new DBServer(context);
            for (String item:shopList){
                List<Grocery> itemList = db.findItemByNameAndStores(item,stores);
                if (!itemList.isEmpty()){
                    resultList.add(itemList.get(0));
                }
                else{
                    throw new RuntimeException("The item does not exist in Target/County Market");
                }
            }
        }
        // User choose Money.
        else {
            Set<String> stores = new HashSet<>(numOfStops);
            DBServer db = new DBServer(context);
            for (String item:shopList) {
                List<Grocery> itemList = db.findItemByName(item);
                if (!itemList.isEmpty()) {
                    int i = 0;
                    while (i < itemList.size()) {
                        Grocery foundItem = itemList.get(i);
                        if (stores.contains(foundItem.getStore())) {
                            resultList.add(foundItem);
                            break;
                        } else if (stores.size() < numOfStops) {
                            stores.add(foundItem.getStore());
                            resultList.add(foundItem);
                            break;
                        } else {
                            i++;
                        }
                    }
                } else {
                    throw new RuntimeException("The item does not exist in Database");
                }
            }
        }

        return resultList;
    }

    @Deprecated
    private List<List<String>> getSubsets(){
        Arrays.sort(allStores);
        List<List<String>> ans = new ArrayList<>();
        for (int i=0; i<allStores.length; i++)
        {
            List<String> subset = new ArrayList<>();
            for(int j=i;j<allStores.length;j++)
            {
                subset.add(allStores[j]);
                ans.add(subset);
            }
        }
        return ans;
    }


    @Deprecated
    private double getTotalDistance(List<String> stores,String start,String end)
    {
        MapService mapService = new MapService();
        if (stores.size()==1){
            String stop = stores.get(0);
            return mapService.getDistance(start,stop)+mapService.getDistance(stop,end);
        }
        if (stores.size()==2){
            String stop1 = stores.get(0);
            String stop2 = stores.get(1);

            double dist1 = mapService.getDistance(start,stop1);
            double dist2 = mapService.getDistance(stop1,stop2);
            double dist3 = mapService.getDistance(stop2,end);

            double dist4 = mapService.getDistance(start,stop2);
            double dist5 = mapService.getDistance(stop2,stop1);
            double dist6 = mapService.getDistance(stop1,end);

            return Math.min(dist1 + dist2 + dist3, dist4 + dist5 + dist6);
        }

        double totalDistance = 0.0;
        double minDistance = 99;
        for (int i=0;i<stores.size();i++){
            for(int j=0;j<stores.size();j++){
                totalDistance = 0.0;
                if (i==j){continue;}
                totalDistance+=mapService.getDistance(start,stores.get(i));
                totalDistance+=mapService.getDistance(stores.get(j),end);
                if(totalDistance>minDistance){continue;}
                List<String> storeSubset = new ArrayList<>(stores);
                storeSubset.remove(stores.get(i));
                storeSubset.remove(stores.get(j));
                totalDistance+=getTotalDistance(storeSubset,stores.get(i),stores.get(j));
                if (totalDistance<minDistance){
                    minDistance = totalDistance;
                }
            }
        }
        return totalDistance;
    }
}
