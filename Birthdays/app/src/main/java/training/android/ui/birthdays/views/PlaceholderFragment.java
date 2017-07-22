package training.android.ui.birthdays.views;

/**
 * Created by guedi on 7/16/2017.
 */


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
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

    private Timer  timer;
    private TimerTask timerTask;
    private boolean mIsFirstLoad =true;
    private LinearLayoutManager layoutManager;
    private MyRecyclerAdapter adapter;

    final  Handler  handler = new Handler ();

    private int mFirtElement;
    private int mLastElement;




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
        layoutManager = new LinearLayoutManager(getActivity());
        rvBirthdays.setLayoutManager(layoutManager);
        adapter = new MyRecyclerAdapter(mBirthdays,mIsCountdown);
        rvBirthdays.setAdapter(adapter);
        ((SwipeRefreshLayout)rootView).setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (timer!=null){
                    timer.cancel();
                    timer =null;
                }
                mListener.refreshList();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeTimers();
    }

    public void setBirthdays(Map<String,Long> birthdays){
        mBirthdays.clear();
        mBirthdays.putAll(birthdays);
        rvBirthdays.getAdapter().notifyDataSetChanged();
        cancelRefresh();
        initializeTimers();


        if (mIsFirstLoad) mIsFirstLoad = false;

    }



    @Override
    public void onPause() {
        super.onPause();
        cancelRefresh();
        if (timer!=null){
            timer.cancel();
            timer =null;
        }

    }

    public void cancelRefresh(){
        SwipeRefreshLayout layout = ((SwipeRefreshLayout)this.getView());
        if (layout != null && layout.isRefreshing()) {
            layout.setRefreshing(false);
        } else
            Log.d(getClass().getSimpleName(),"null view ");
    }


    private void initializeTimers(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                mFirtElement =layoutManager.findFirstVisibleItemPosition();
                mLastElement = layoutManager.findLastCompletelyVisibleItemPosition();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=mFirtElement; i<=mLastElement; i ++){
                            View view = rvBirthdays.getChildAt(i);
                            try{
                                MyViewHolder viewHolder =(MyViewHolder) rvBirthdays.getChildViewHolder(view);
                                viewHolder.count();
                            }catch (NullPointerException e){
                                Log.d(getClass().getSimpleName(),e.getMessage());
                            }
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask,3000,1000);
    }


    public interface FragmentListener{
        void refreshList();
    }





}
