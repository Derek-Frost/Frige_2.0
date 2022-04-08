package com.example.frige_20.Utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class LocationUtils implements LocationListener{

    private String locationS;


    public LocationUtils() {
    }

    public String getLocationS() {
        return locationS;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationS = location.getLatitude() + "_" + location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}

