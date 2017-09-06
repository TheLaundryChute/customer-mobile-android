package com.thelaundrychute.user.location;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.thelaundrychute.business.location.Location;
import com.thelaundrychute.user.common.database.AppBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class LocationRepository {
    private static LocationRepository sLocationRepository;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static LocationRepository get(Context context) {
        if (sLocationRepository == null) {
            sLocationRepository = new LocationRepository(context);
        }
        return sLocationRepository;
    }

    private LocationRepository(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AppBaseHelper(mContext)
                .getWritableDatabase();
    }



    public List<Location> getLocations() {
        List<Location> locations = new ArrayList();

        Location location =new Location();
        location.setName("Lassimer Hall");
        locations.add(location);

        return locations;
    }


    public Location getLocation(String id) {

        Location location= null;
        for(Location l : getLocations()){
            if(l.getId() == id) {
                location = l;
                break;
            }
            //something here
        }
        return location;
    }


}
