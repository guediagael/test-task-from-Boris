package training.android.ui.birthdays.views;

/**
 * Created by guedi on 7/16/2017.
 */


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

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

    private FragmentListener mListener;

    private RecyclerView rvBirthdays;

    private int mSectionNumber;
    private HashMap<String, Long> mBirthdays;




    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, HashMap<String,Long> birthdays) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(ARG_BIRTHDAYS,birthdays);
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
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rvBirthdays = rootView.findViewById(R.id.recycler_birthdays);
        rvBirthdays.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBirthdays.setAdapter(new MyRecyclerAdapter(mBirthdays));
        ((SwipeRefreshLayout)rootView).setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListener.refreshList(mSectionNumber);
            }
        });
        return rootView;
    }

    public void setBirthdays(Map<String,Long> birthdays){
        ((SwipeRefreshLayout)this.getView()).setRefreshing(false);
        mBirthdays.clear();
        mBirthdays.putAll(birthdays);
        rvBirthdays.getAdapter().notifyDataSetChanged();
    }


    public void cancelRefresh(){
        SwipeRefreshLayout layout = ((SwipeRefreshLayout)this.getView());
        if (layout.isRefreshing()){
            layout.setRefreshing(false);
        }
    }

    public interface FragmentListener{
        void refreshList(int tag);
    }
}
