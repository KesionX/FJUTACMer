package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/30.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter{
    private ArrayList fragmentlist ;
    public MyFragmentAdapter(FragmentManager fm,ArrayList fragmentlist) {
        super(fm);
        this.fragmentlist = fragmentlist;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();
    }
}
