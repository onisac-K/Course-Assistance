package com.coursespick.entity;

import java.util.LinkedList;
import java.util.List;

public class ClothSet {

    private int srcId;
    private List<Cloth> clothSetList;
    private String title;
//    public ClothSet()
//    {
//        clothSetList = new LinkedList<>();
//
//    }
    public ClothSet(String title) {
        this.title = title;
        clothSetList = new LinkedList<>();
    }

    public ClothSet(List<Cloth> tClothSetList)
    {
        clothSetList = tClothSetList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setClothSetList(List<Cloth> clothSetList) {
        this.clothSetList = clothSetList;
    }

    public String getTitle() {
        return title;
    }

    public List<Cloth> getClothSetList() {
        return clothSetList;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public int getSrcId() {
        return srcId;
    }

}