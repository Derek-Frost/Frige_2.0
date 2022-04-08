package com.example.frige_20.Utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DataUtils {

    public DataUtils() {
    }

    public String plusDate(Calendar dateNow, String dateProduct){
        List<String> data = Arrays.asList(dateProduct.split("-"));
        dateNow.roll(1, Integer.parseInt(data.get(0)));
        dateNow.roll(2, Integer.parseInt(data.get(1)));
        dateNow.roll(3, Integer.parseInt(data.get(2)));
        return String.valueOf(dateNow.getTime());
    }





}
