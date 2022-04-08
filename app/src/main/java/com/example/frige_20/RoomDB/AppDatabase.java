package com.example.frige_20.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, ProductName.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao getProductDao();

}
