package com.example.shopmaster.datahandler;

import java.util.List;

public class ParentItem {

    // Declaration of the variables
    private String ParentItemStore;
    private String ParentItemStoreDist;
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

//    public String getParentItemStoreDist() { return ParentItemStoreDist; }

//    public void setParentItemStoreDist(String parentItemStoreDist) { ParentItemStoreDist = parentItemStoreDist; }

    public List<ChildItem> getChildItemList() {
        return ChildItemList;
    }

    public void setChildItemList(List<ChildItem> childItemList) {
        ChildItemList = childItemList;
    }
}