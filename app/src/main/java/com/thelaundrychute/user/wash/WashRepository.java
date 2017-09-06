package com.thelaundrychute.user.wash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.thelaundrychute.business.user.Wash;
import com.thelaundrychute.user.user.UserHelper;
import com.thelaundrychute.user.common.database.AppBaseHelper;

import java.util.List;

public class WashRepository {
    private static WashRepository sWashRepository;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static WashRepository get(Context context) {
        if (sWashRepository == null) {
            sWashRepository = new WashRepository(context);
        }
        return sWashRepository;
    }

    private WashRepository(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AppBaseHelper(mContext)
                .getWritableDatabase();
    }


    public List<Wash> getWashes() {
        List<Wash> washes = UserHelper.getUser().getWashes();
        return washes;
    }

    public Wash getNextWash(String id) {
        //TODO not done
        return this.getWashes().get(1);
    }

    public Wash getWash(long id) {
        Wash wash = null;
        for(Wash w : getWashes()){
            if(w.getId() == id) {
                wash = w;
                break;
            }
            //something here
        }

        return wash;
    }


}
