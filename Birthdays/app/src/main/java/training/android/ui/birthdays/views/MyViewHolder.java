package training.android.ui.birthdays.views;

import android.icu.util.TimeUnit;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;


import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Period;
import org.joda.time.YearMonth;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import training.android.ui.birthdays.R;

/**
 * Created by guedi on 7/16/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView  tvName;
    private Chronometer tvChrono;
    private long birthday;
    private DateTime given;
    private boolean mIsFuture;
    private Period p ;

    public MyViewHolder(View itemView,boolean isCountdown) {
        super(itemView);
        tvChrono = itemView.findViewById(R.id.text_chrono);
        tvName = itemView.findViewById(R.id.text_person_name);
        mIsFuture = isCountdown;
    }

    public void setData(final String name, final long time ) {
        birthday = time * 1000;
        given = new DateTime(birthday);
        tvName.setText(name);
        count();
    }



    public void count(){
        if (mIsFuture) p =  new Period(new DateTime(),new DateTime(birthday));
        else p =  new Period(given, new DateTime());
        String days = String.valueOf(p.getDays()+(p.getMonths()*30));
        String hours;
        String minutes;
        String seconds;

        if (p.getYears()>0 || p.getMonths() >3)  days = "+99";
        hours = String.valueOf(p.getHours());
        minutes =  String.valueOf(p.getMinutes());
        seconds =  String.valueOf(p.getSeconds());
        tvChrono.setText(days+":"+hours+":"+minutes+":"+seconds);

    }





}
