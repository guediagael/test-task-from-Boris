package training.android.ui.birthdays.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

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
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private HashMap<String,Long> mFutureBirthdays, mPastBirthdays;
    private PlaceholderFragment[] fragments;

    public SectionsPagerAdapter(FragmentManager fm,Birthdays birthdays) {
        super(fm);

        fragments= new PlaceholderFragment[]{null,null};
        mPastBirthdays = new HashMap<>();
        mFutureBirthdays = new HashMap<>();
        extractBirthdays(birthdays);
    }

    public void dataRefreshed(Birthdays birthdays){
        Map<String,Long> past = new HashMap<>();
        for(PastBirthdays pbd : birthdays.getPast()){
            past.put(pbd.getDescription(),pbd.getTime());
        }
        fragments[0].setBirthdays(past);

        Map<String,Long> future = new HashMap<>();
        for(FutureBirthdays fbd : birthdays.getFuture()){
            future.put(fbd.getDescription(),fbd.getTime());
        }
        fragments[1].setBirthdays(future);



    }

    public void stopLoading(){
        fragments[0].cancelRefresh();
        fragments[1].cancelRefresh();
    }


    @Override
    public Fragment getItem(int position) {
        HashMap<String,Long> birthdays= position==0 ? mPastBirthdays:mFutureBirthdays;
        if (fragments[position]==null){
            fragments[position] = PlaceholderFragment.newInstance(position + 1,birthdays);
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



    private void extractBirthdays(Birthdays birthdays){
        List<FutureBirthdays> futureBirthdaysList = birthdays.getFuture();
        List<PastBirthdays> pastBirthdaysList = birthdays.getPast();
        try {
            for (PastBirthdays pbd : pastBirthdaysList){
                String key = pbd.getDescription();
                Long time = pbd.getTime();
                mPastBirthdays.put(key,time);
            }


            getItem(0);

            for (FutureBirthdays fbd : futureBirthdaysList){
                String key = fbd.getDescription();
                Long time =fbd.getTime();
                mFutureBirthdays.put(key,time);
            }

            getItem(1);
        }catch (NullPointerException e){
            Log.d(getClass().getSimpleName(),e.getMessage());
        }

    }




}
