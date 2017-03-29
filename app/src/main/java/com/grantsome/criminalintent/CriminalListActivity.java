package com.grantsome.criminalintent;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;


/**
 * Created by tom on 2017/3/5.
 */

public class CriminalListActivity extends SingleFragmentActivity{


    @Override
    public Fragment onCreateFragment() {
        return new CriminalListFragment();
    }

}
