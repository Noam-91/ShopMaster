package com.example.shopmaster.datahandler;

import java.util.ArrayList;
import java.util.List;

public class PlanCalculator {
    private List<Grocery> shopList;
    private String primaryFactor;
    private Integer numOfStops;
    public PlanCalculator(List<Grocery> shopList,String primaryFactor,Integer numOfStops){
        this.shopList=shopList;
        this.primaryFactor=primaryFactor;
        this.numOfStops=numOfStops;
    }

    public List<Grocery> calculate()
    {
        if (shopList.size()==0){
            throw new ArrayIndexOutOfBoundsException("Exception message");
        }
        List<Grocery> resultList = new ArrayList<>();
        if (primaryFactor.equals("time")){

        }
        if (primaryFactor.equals("time")){

        }

        return resultList;
    }


}
