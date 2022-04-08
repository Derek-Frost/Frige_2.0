package com.example.frige_20.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class ProductName {
    @PrimaryKey
    @ColumnInfo(name = "id")
    long id;

    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "type")
    String type;
    @ColumnInfo(name = "variety")
    String variety;
    @ColumnInfo(name = "time")
    String time;
    @ColumnInfo(name = "canFreeze")
    boolean canFreeze;

    public ProductName(long id, String name, String type, String variety, String time, boolean canFreeze) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.variety = variety;
        this.time = time;
        this.canFreeze = canFreeze;
    }

    public ProductName() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCanFreeze(boolean canFreeze) {
        this.canFreeze = canFreeze;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getVariety() {
        return variety;
    }

    public String getTime() {
        return time;
    }

    public boolean isCanFreeze() {
        return canFreeze;
    }
}
