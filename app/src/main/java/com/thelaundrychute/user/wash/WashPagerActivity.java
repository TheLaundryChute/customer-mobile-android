/*
package com.thelaundrychute.user.wash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.thelaundrychute.business.user.Wash;
import com.thelaundrychute.user.test.R;
import com.thelaundrychute.user.common.TranslationService;
import com.thelaundrychute.user.user.UserHelper;


import java.util.List;


public class WashPagerActivity extends AppCompatActivity {
    private static final String EXTRA_WASH_ID =
            "com.thelaundrychute.consumerapp.wash_id";
    private static final String EXTRA_BAG_ID = "com.thelaundrychute.consumerapp.bag_id";
    private static final String RELOAD_ADAPTER = "com.thelaundrychute.consumerapp.reload";

    private ViewPager mViewPager;
    private List<Wash> mWashes;
    private TranslationService translationService;

    public static Intent newIntent(Context packageContext, long washId) {
        Intent intent = new Intent(packageContext, WashPagerActivity.class);
        intent.putExtra(EXTRA_WASH_ID, washId);
        return intent;
    }

    public static Intent newIntent(Context packageContext, String bagId) {
        Intent intent = new Intent(packageContext, WashPagerActivity.class);
        intent.putExtra(EXTRA_BAG_ID, bagId);
        return intent;
    }

    public static Intent newIntent(Context packageContext, Boolean pickup) {
        Intent intent = new Intent(packageContext, WashPagerActivity.class);
        intent.putExtra(RELOAD_ADAPTER, pickup);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        mWashes = UserHelper.getActiveUserWashes();

        if (UserHelper.getActiveUserWashes().size() >= 1) {
            setIntent(intent);//must store the new intent unless getIntent() will return the old one
        } else {
            if (intent.getExtras().getBoolean(RELOAD_ADAPTER)) {
                if(mWashes == null || mWashes.size() == 0){
                    Wash wash = UserHelper.getNonWash();
                    mWashes.add(wash);
                    mViewPager.setCurrentItem(0);
                    this.mViewPager.setAdapter(this.mViewPager.getAdapter());
                    this.mViewPager.getAdapter().notifyDataSetChanged();
                    return;
                }
            }
        }





        if(getIntent().getSerializableExtra(EXTRA_WASH_ID) != null) {
            long washId = (long) getIntent()
                    .getSerializableExtra(EXTRA_WASH_ID);
            setCurrentItem(washId, null, true);

        }

        if(getIntent().getSerializableExtra(EXTRA_BAG_ID) != null) {
            String bagId = (String) getIntent().getSerializableExtra(EXTRA_BAG_ID);
            setCurrentItem(null, bagId, true);

        }

        if (getIntent().getSerializableExtra(RELOAD_ADAPTER) != null) {
            //Used to reload the page after finishCycle
            this.mViewPager.setAdapter(this.mViewPager.getAdapter());

            if (getIntent().getExtras().getBoolean(RELOAD_ADAPTER)) {
                if(mWashes == null || mWashes.size() == 0){
                    Wash wash = UserHelper.getNonWash();
                    mWashes.add(wash);
                    mViewPager.setCurrentItem(0);
                }
            }

            return;
        }

        this.mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translationService = TranslationService.getCurrent();

        setContentView(R.layout.activity_wash_pager);
        setTitle(translationService.get("Common.MyBags", "My Bags"));

        Long washId = null;
        String bagId = null;

        if(getIntent().getSerializableExtra(EXTRA_WASH_ID) != null) {
            washId = (long) getIntent()
                    .getSerializableExtra(EXTRA_WASH_ID);
        }
        if(getIntent().getSerializableExtra(EXTRA_BAG_ID) != null) {
            bagId = (String) getIntent().getSerializableExtra(EXTRA_BAG_ID);
        }

        mViewPager = (ViewPager) findViewById(R.id.activity_wash_pager_view_pager);

        mWashes = UserHelper.getActiveUserWashes();
        //If we don't have any wash, user needs to buy some
        if(mWashes == null || mWashes.size() == 0){
            Wash wash = UserHelper.getNonWash();
            mWashes.add(wash);
            mViewPager.setCurrentItem(0);
        }

        setAdapter();
        setCurrentItem(washId, bagId, false);
    }

    private void setAdapter() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Wash wash = mWashes.get(position);
                return WashFragment.newInstance(wash.getId());
            }

            @Override
            public int getCount() {
                return mWashes != null ? mWashes.size() : 0;
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Wash wash = mWashes.get(position);
                if (wash.getBag().getId() != null) {
                    //setTitle(wash.getTitle());
                    //Just show 'My Bags' instead

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setCurrentItem(Long washId, String bagId, boolean invalidate) {

        if (mWashes != null && mWashes.size() > 0) {

            if (washId != null) {
                for (int i = 0; i < mWashes.size(); i++) {
                    if (mWashes.get(i).getId() == washId){
                        if (invalidate) {
                            setAdapter();
                        }
                        mViewPager.setCurrentItem(i);
                        break;
                    }
                }
            } else if (bagId != null) {

                for (int i = 0; i < mWashes.size(); i++) {
                    if (mWashes.get(i).getBag().getId().contentEquals(bagId)){
                        if (invalidate) {
                            setAdapter();
                        }
                        mViewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        }
    }
}
*/
