package training.android.ui.birthdays.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import training.android.ui.birthdays.R;

/**
 * Created by guedi on 7/16/2017.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Map<String,Long> birthdays;
    private List<String> names;
    private boolean mIsCountdown;

    public MyRecyclerAdapter(Map<String,Long>  birthdays,boolean isCountdown) {
        this.birthdays = birthdays;
        names = new ArrayList<>();
        mIsCountdown = isCountdown;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder,parent,false);

        return new MyViewHolder(itemView,mIsCountdown);
    }




    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        names.clear();
        names.addAll(birthdays.keySet());
        String personName = names.get(position);
        final Long birthday = birthdays.get(personName);

        holder.setData(personName,birthday);
    }





    @Override
    public int getItemCount() {
        return birthdays.size();
    }







}
