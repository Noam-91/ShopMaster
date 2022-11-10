package com.example.shopmaster.datahandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapService {
    Map<String, Map<String, Double>> distMap;
    Map<String, String> locMap;
    Map<String, Double[]> locations;

    public MapService()
    {
        Map<String, Double> county = new HashMap<>();
        county.put("Target",0.4);
        county.put("Walmart",3.6);
        county.put("Costco",2.8);
        county.put("User",0.7);
        distMap.put("County Market",county);
        Map<String, Double> target = new HashMap<>();
        target.put("Costco",3.2);
        target.put("Walmart",4.0);
        target.put("County Market", 0.4);
        target.put("User", 0.6);
        distMap.put("Target",target);
        Map<String, Double> walmart = new HashMap<>();
        walmart.put("Costco",1.2);
        walmart.put("Target",4.0);
        walmart.put("County Market", 3.6);
        walmart.put("User", 4.1);
        distMap.put("Walmart",walmart);
        Map<String, Double> costco = new HashMap<>();
        costco.put("County Market",2.8);
        costco.put("Target",3.2);
        costco.put("Walmart", 1.2);
        costco.put("User", 3.3);
        distMap.put("Costco",costco);
        Map<String, Double> user = new HashMap<>();
        user.put("County Market",0.7);
        user.put("Target",0.6);
        user.put("Walmart", 4.1);
        user.put("Costco",3.3);
        distMap.put("User",user);


        Double[] costco_latlng =  {40.1431723,-88.2469611};
        Double[] walmart_latlng = {40.1432246,-88.2535272};
        Double[] county_latlng = {40.1272303,-88.2527639};
        Double[] target_latlng = {440.1272933,-88.2527639};
        locations.put("Costco",costco_latlng);
        locations.put("Walmart",walmart_latlng);
        locations.put("County Market",county_latlng);
        locations.put("Target",target_latlng);


    }

    public Map<String, Map<String, Double>> getDistMap() {
        return distMap;
    }
    public double getDistance(String node1, String node2){
        Map<String, Double> store = distMap.get(node1);
        return store.get(node2);
    }
    public Double[] getLatLng(String store){
        return locations.get(store);
    }
}
