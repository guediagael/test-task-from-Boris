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

    private HashMap<String, Long> mBirthdays;
    private boolean mIsCountdown;




    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(HashMap<String,Long> birthdays, boolean isCountdown) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
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
//            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            mBirthdays =(HashMap<String, Long>) getArguments().getSerializable(ARG_BIRTHDAYS);
            mIsCountdown = getArguments().getBoolean(ARG_LIST_TYPE);
        }
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rvBirthdays = rootView.findViewById(R.id.recycler_birthdays);
        rvBirthdays.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBirthdays.setAdapter(new MyRecyclerAdapter(mBirthdays,mIsCountdown));
        ((SwipeRefreshLayout)rootView).setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListener.refreshList();
            }
        });
        return rootView;
    }


    public void setBirthdays(Map<String,Long> birthdays){
        cancelRefresh();
        mBirthdays.clear();
        mBirthdays.putAll(birthdays);
        rvBirthdays.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onPause() {
        super.onPause();
        cancelRefresh();

    }

    public void cancelRefresh(){
        SwipeRefreshLayout layout = ((SwipeRefreshLayout)this.getView());
        if (layout != null && layout.isRefreshing()) {
            layout.setRefreshing(false);
        } else
            Log.d(getClass().getSimpleName(),"null view ");
    }


    public interface FragmentListener{
        void refreshList();
    }





}
