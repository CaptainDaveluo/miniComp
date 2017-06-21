package com.debla.minicomp.Structs;

/**
 * Created by Dave-PC on 2017/2/13.
 */

public class ItemView {
    private int Color;              //颜色
    private int item_img;           //图片
    private String item_title;      //名称
    private int Id;                 //Id

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public int getItem_img() {

        return item_img;
    }

    public void setItem_img(int item_img) {
        this.item_img = item_img;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
