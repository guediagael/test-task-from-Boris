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

    public MyRecyclerAdapter(Map<String, Long> birthdays) {
        this.birthdays = birthdays;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        List<String> names = new ArrayList<>();
        names.addAll(birthdays.keySet());
        String personName = names.get(position);
        final long birthday = birthdays.get(personName);
        holder.setData(personName, birthday);


//            new Thread(new Runnable() {
//                while(true){}
//                @Override
//                public void run() {
//                    try {
//                        new MyASyncTask(holder.getTvChrono(),birthday).execute();
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//
//
//                }
//            });




    }




    @Override
    public int getItemCount() {
        return birthdays.size();
    }



//class MyASyncTask extends AsyncTask<Void,Void,Long>{
//
//    private Long time;
//    private TextView tv;
//
//    public MyASyncTask(TextView tv,Long time) {
//        this.tv = tv;
//        this.time = time;
//    }
//
//    @Override
//    protected Long doInBackground(Void... longs) {
//        long difference;
//        if (new Date(time).compareTo(new Date())<0){
//            difference = new Date().getTime() - new Date(time).getTime() ;
//
//        }else {
//            difference = new Date(time).getTime() - new Date().getTime();
//        }
//
//        return difference;
//    }
//
//
//    @Override
//    protected void onPostExecute(Long aLong) {
//        super.onPostExecute(aLong);
//
//        Long days = (long)00 ;
//        Long hours = (long)00;
//        Long minutes = (long)00;
//        Long seconds = (long)00;
//        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
//        Collections.reverse(units);
//        Map<TimeUnit,Long> result = new LinkedHashMap<>();
//        for (TimeUnit unit : units){
//            long diff = unit.convert(aLong,TimeUnit.MILLISECONDS);
//            long diffInMilliesForUnit = unit.toMillis(diff);
//            aLong = aLong - diffInMilliesForUnit;
//            result.put(unit,diff);
//        }
//
//        for (TimeUnit timeUnit : result.keySet()){
//            if (timeUnit == TimeUnit.DAYS) days = result.get(timeUnit);
//            else if (timeUnit== TimeUnit.HOURS) hours = result.get(timeUnit);
//            else if (timeUnit == TimeUnit.MINUTES) minutes = result.get(timeUnit);
//            else if (timeUnit == TimeUnit.SECONDS) seconds  =result.get(timeUnit);
//        }
//
//        String d;
//        if (days>=100) d = "+99";
//        else d = String.valueOf(days);
//
//        tv.setText(d+":"+hours+":"+minutes+":"+seconds);
//    }

}
