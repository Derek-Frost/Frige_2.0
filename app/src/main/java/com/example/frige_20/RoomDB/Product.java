package com.example.frige_20.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = ProductName.class,
        parentColumns = "id",
        childColumns = "idProduct"
))
public class Product {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "idProduct")
    int idProduct;
    @ColumnInfo(name = "weight")
    float weight;
    @ColumnInfo(name = "count")
    int count;
    @ColumnInfo(name = "frige")
    int idFrige;
    @ColumnInfo(name = "coordinates")
    String сoord;
    @ColumnInfo(name = "time")
    String time;
    @ColumnInfo(name = "timeEnd")
    String timeEnd;

    public Product(int idProduct, float weight, int count, int idFrige) {
        this.idProduct = idProduct;
        this.weight = weight;
        this.count = count;
        this.idFrige = idFrige;
    }

    public Product(int idProduct, float weight, int count, int idFrige, String time, String timeEnd) {
        this.idProduct = idProduct;
        this.weight = weight;
        this.count = count;
        this.idFrige = idFrige;
        this.time = time;
        this.timeEnd = timeEnd;
    }

    public Product() {
    }


    public int getIdproduct() {
        return idProduct;
    }

    public int getIdFrige() {
        return idFrige;
    }

    public void setIdFrige(int idFrige) {
        this.idFrige = idFrige;
    }

    public float getweight() {
        return weight;
    }

    public int getcount() {
        return count;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getСoord() {
        return сoord;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setСoord(String сoord) {
        this.сoord = сoord;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
