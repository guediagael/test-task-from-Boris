package training.android.ui.birthdays.views;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
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

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView tvChrono, tvName;

    public MyViewHolder(View itemView) {
        super(itemView);
        tvChrono = itemView.findViewById(R.id.text_chrono);
        tvName = itemView.findViewById(R.id.text_person_name);
    }

    public void setData(String name, final Long time) {
        tvName.setText(name);
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    MyASyncTask aSyncTask = new MyASyncTask(tvChrono, time);
                    aSyncTask.execute();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }



    class MyASyncTask extends AsyncTask<Void, Void, Long> {

        private Long time;
        private TextView tv;

        public MyASyncTask(TextView tv, Long time) {
            this.tv = tv;
            this.time = time;

        }

        @Override
        protected Long doInBackground(Void... longs) {
            long difference;
            if (new Date(time).compareTo(new Date()) < 0) {
                difference = new Date().getTime() - new Date(time).getTime();

            } else {
                difference = new Date(time).getTime() - new Date().getTime();
            }


            return difference;




       }


        @Override
        protected void onPostExecute(Long aResult) {
            compute(aResult);
        }

        private void compute(Long aLong){
            Long days = (long) 00;
            Long hours = (long) 00;
            Long minutes = (long) 00;
            Long seconds = (long) 00;
            List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
            Collections.reverse(units);
            Map<TimeUnit, Long> result = new LinkedHashMap<>();
            for (TimeUnit unit : units) {
                long diff = unit.convert(aLong, TimeUnit.MILLISECONDS);
                long diffInMilliesForUnit = unit.toMillis(diff);
                aLong = aLong - diffInMilliesForUnit;
                result.put(unit, diff);
            }

            for (TimeUnit timeUnit : result.keySet()) {
                if (timeUnit == TimeUnit.DAYS) days = result.get(timeUnit);
                else if (timeUnit == TimeUnit.HOURS) hours = result.get(timeUnit);
                else if (timeUnit == TimeUnit.MINUTES) minutes = result.get(timeUnit);
                else if (timeUnit == TimeUnit.SECONDS) seconds = result.get(timeUnit);
            }

            String d;
            if (days >= 100) d = "+99";
            else d = String.valueOf(days);

            tv.setText(d + ":" + hours + ":" + minutes + ":" + seconds);
        }
    }
}
