package com.grantsome.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;
import java.util.UUID;

/**
 * Created by tom on 2017/3/6.
 */

public class CriminalPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private List<Crime> mCrimes;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminal_pager);
        UUID crimeId = (UUID) getIntent().getSerializableExtra("crime_id");
        mViewPager = (ViewPager) findViewById(R.id.activity_criminal_pager_view_pager);
        mCrimes = CrimeTab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                Log.d("CrimePagerActivity","crimeId="+crime.getId());
                Log.d("CrimePagerActivity","crimeTitle="+crime.getTitle());
                return new CriminalFragment().newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int state) {}

            public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected (int position) {
                Crime crime = mCrimes.get(position);
                if (crime.getTitle() != null) {
                    setTitle(crime.getTitle());
                }
            }
        });

        for(int i=0; i < mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }


    public static Intent newIntent(Context context, UUID crimeId){
        Intent intent = new Intent(context,CriminalPagerActivity.class);
        intent.putExtra("crime_id",crimeId);
        return intent;
    }
}
