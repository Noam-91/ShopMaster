package com.example.shopmaster.datahandler;

import java.util.Comparator;
import java.util.List;

public class ParentItem implements Comparable<ParentItem> {

    // Declaration of the variables
    private String ParentItemStore;
    private int ParentItemExtraTime;
    private List<ChildItem> ChildItemList;

    public ParentItem(String parentItemStore, List<ChildItem> childItemList) {
        ParentItemStore = parentItemStore;
        ChildItemList = childItemList;
    }

    public String getParentItemStore() {
        return ParentItemStore;
    }

    public void setParentItemStore(String parentItemStore) {
        ParentItemStore = parentItemStore;
    }

    public int getParentItemExtraTime() {
        return ParentItemExtraTime;
    }

    public void setParentItemExtraTime(int parentItemExtraTime) {
        ParentItemExtraTime = parentItemExtraTime;
    }

    public List<ChildItem> getChildItemList() {
        return ChildItemList;
    }

    public void setChildItemList(List<ChildItem> childItemList) {
        ChildItemList = childItemList;
    }

    @Override
    public int compareTo(ParentItem otherStoreEntry) {
        return this.getParentItemExtraTime()-otherStoreEntry.getParentItemExtraTime();
    }
}