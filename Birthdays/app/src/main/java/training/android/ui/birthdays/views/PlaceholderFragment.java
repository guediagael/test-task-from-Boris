package training.android.ui.birthdays.views;

/**
 * Created by guedi on 7/16/2017.
 */


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import training.android.ui.birthdays.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_BIRTHDAYS = "birthdaysCollection";
    private static final String ARG_LIST_TYPE  = "futureOrPast";

    private FragmentListener mListener;

    private RecyclerView rvBirthdays;

    private int mSectionNumber;
    private HashMap<String, Long> mBirthdays;
    private boolean mIsCountdown;




    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, HashMap<String,Long> birthdays, boolean isCountdown) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(ARG_BIRTHDAYS,birthdays);
        args.putBoolean(ARG_LIST_TYPE,isCountdown);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener)
            mListener = (FragmentListener)context;
        else throw new ClassCastException(context.toString()+" "+
                "should implement"+ FragmentListener.class.getSimpleName());

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            mBirthdays =(HashMap<String, Long>) getArguments().getSerializable(ARG_BIRTHDAYS);
            mIsCountdown = getArguments().getBoolean(ARG_LIST_TYPE);
        }
        setRetainInstance(true);

//        mAsyncTask= new MyASyncTask();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rvBirthdays = rootView.findViewById(R.id.recycler_birthdays);
        rvBirthdays.setLayoutManager(new LinearLayoutManager(getActivity()));

//        HashMap<String,String> map = new HashMap<>();
//        for (String s : mBirthdays.keySet()){
//            map.put(s,"");
//        }
//        ArrayList<String> strings = new ArrayList<>();
//        strings.addAll(map.keySet());
        rvBirthdays.setAdapter(new MyRecyclerAdapter(mBirthdays,mIsCountdown));
        ((SwipeRefreshLayout)rootView).setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListener.refreshList(mSectionNumber);
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SwipeRefreshLayout)view).setRefreshing(false);
    }

    public void setBirthdays(Map<String,Long> birthdays){
        if (this.getView() != null) {
            ((SwipeRefreshLayout)this.getView()).setRefreshing(false);

            mBirthdays.clear();
            mBirthdays.putAll(birthdays);
            rvBirthdays.getAdapter().notifyDataSetChanged();
//            mTimeDifferencies = new Long[birthdays.size()];
//            Iterator<Long> iterator = birthdays.values().iterator();
//            int i = 0;
//            while (iterator.hasNext()){
//
//                mTimeDifferencies[i] = iterator.next();
//                i++;
//            }
//            mAsyncTask.execute(mTimeDifferencies);
        }else
            Log.d(getClass().getSimpleName(),"null view ");

    }


    public void cancelRefresh(){
        SwipeRefreshLayout layout = ((SwipeRefreshLayout)this.getView());
        if (layout != null && layout.isRefreshing()) {
            layout.setRefreshing(false);
        }
    }


//    private void timeUpdated(String[] times){
//        ((MyRecyclerAdapter)rvBirthdays.getAdapter()).timeUpdated(times);
//        mAsyncTask.execute(mTimeDifferencies);
//    }

    public interface FragmentListener{
        void refreshList(int tag);
    }





//    class MyASyncTask extends AsyncTask<Long, Void, String[]> {
//
//
//        @Override
//        protected String[] doInBackground(Long... longs) {
//            String[] times = new String[longs.length];
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            for (int i=0; i<longs.length;i++){
//                long difference;
//                if (new Date(longs[i]).compareTo(new Date()) < 0) {
//                    difference = new Date().getTime() - new Date(longs[i]).getTime();
//
//                } else {
//                    difference = new Date(longs[i]).getTime() - new Date().getTime();
//                }
//                times[i] = compute(difference);
//            }
//
//            return times;
//
//
//
//
//        }
//
//
//        @Override
//        protected void onPostExecute(String[] aResult) {
//            timeUpdated(aResult);
//        }
//
//        private String compute(Long aLong){
//            Long days = (long) 00;
//            Long hours = (long) 00;
//            Long minutes = (long) 00;
//            Long seconds = (long) 00;
//            List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
//            Collections.reverse(units);
//            Map<TimeUnit, Long> result = new LinkedHashMap<>();
//            for (TimeUnit unit : units) {
//                long diff = unit.convert(aLong, TimeUnit.MILLISECONDS);
//                long diffInMilliesForUnit = unit.toMillis(diff);
//                aLong = aLong - diffInMilliesForUnit;
//                result.put(unit, diff);
//            }
//
//            for (TimeUnit timeUnit : result.keySet()) {
//                if (timeUnit == TimeUnit.DAYS) days = result.get(timeUnit);
//                else if (timeUnit == TimeUnit.HOURS) hours = result.get(timeUnit);
//                else if (timeUnit == TimeUnit.MINUTES) minutes = result.get(timeUnit);
//                else if (timeUnit == TimeUnit.SECONDS) seconds = result.get(timeUnit);
//            }
//
//            String d;
//            if (days >= 100) d = "+99";
//            else d = String.valueOf(days);
//
//            return (d + ":" + hours + ":" + minutes + ":" + seconds);
//        }
//    }


}
