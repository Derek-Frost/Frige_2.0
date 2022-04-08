package com.example.frige_20.RoomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {



    @Query("DELETE FROM ProductName")
    public void nukeTable();

    @Query("DELETE FROM Product")
    public void nukeTableProduct();

    @Query("SELECT * FROM ProductName")
    List<ProductName> getAll();

    @Query("SELECT * FROM Product WHERE frige == 0")
    List<Product> getFrige();

    @Query("SELECT * FROM Product WHERE frige == 1")
    List<Product> getFreeze();

    @Query("SELECT name FROM ProductName WHERE id = :id")
    String getNameProduct(int id);

    @Update
    void update(Product product);

    @Query("SELECT * FROM ProductName WHERE id = :id")
    ProductName getProductNameId(int id);

    @Query("SELECT * FROM ProductName WHERE type = :type")
    List<ProductName> getProductName(String type);

    @Query("SELECT variety FROM ProductName WHERE name = :name1")
    List<String> getVariety(String name1);

    @Query("SELECT id FROM ProductName WHERE name = :name AND type = :type AND variety = :variety")
    int getProductId(String name, String variety, String type);


    @Query("SELECT type FROM ProductName")
    List<String> getType();

    @Query("SELECT * FROM Product")
    List<Product> getAllProducts();

    @Delete
    void deleteProduct(Product employee);

    @Insert
    void insert(ProductName employee);

    @Insert
    void insert(Product employee);

}
