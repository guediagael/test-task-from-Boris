package training.android.ui.birthdays.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import training.android.ui.birthdays.models.Birthdays;
import training.android.ui.birthdays.models.FutureBirthdays;
import training.android.ui.birthdays.models.PastBirthdays;

/**
 * Created by guedi on 7/16/2017.
 */



/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


    private PlaceholderFragment[] fragments;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments= new PlaceholderFragment[]{null,null};
    }

    public void dataRefreshed(Birthdays birthdays){
        HashMap<String,Long> past = new HashMap<>();
        for(PastBirthdays pbd : birthdays.getPast()){
            past.put(pbd.getDescription(),pbd.getTime());
        }

        fragments[0].setBirthdays(past);


        HashMap<String,Long> future = new HashMap<>();
        for(FutureBirthdays fbd : birthdays.getFuture()){
            future.put(fbd.getDescription(),fbd.getTime());
        }


        fragments[1].setBirthdays(future);

    }

    public void stopLoading(){
        try{
            fragments[0].cancelRefresh();
            fragments[1].cancelRefresh();
        }catch (NullPointerException e){
            Log.d(getClass().getSimpleName(),e.getMessage());
        }

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PlaceholderFragment fragment =(PlaceholderFragment) super.instantiateItem(container, position);
        fragments[position] = fragment;
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragments[position]=null;
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position]==null){
            if (position==0){
                fragments[position] = PlaceholderFragment.newInstance(new HashMap<String, Long>(),false);
            }else
                fragments[position] = PlaceholderFragment.newInstance(new HashMap<String, Long>(),true);

            return  fragments[position];
        }else
            return fragments[position];

    }



    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Past Birthdays";
            case 1:
                return "Future Birthdays";

        }
        return null;
    }


}
