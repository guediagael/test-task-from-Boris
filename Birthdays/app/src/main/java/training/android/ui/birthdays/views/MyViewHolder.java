package training.android.ui.birthdays.views;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;


import java.util.Date;

import training.android.ui.birthdays.R;

/**
 * Created by guedi on 7/16/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView  tvName;
    private Chronometer tvChrono;
    private TextView tvCountdown;

    public MyViewHolder(View itemView,boolean isCountdown) {
        super(itemView);
        tvChrono = itemView.findViewById(R.id.text_chrono);
        tvName = itemView.findViewById(R.id.text_person_name);
        tvCountdown = itemView.findViewById(R.id.text_countdown);
    }

    public void setData(String name,long time ) {
        long hour = new Date().getTime() - time;
        tvName.setText(name);
        tvChrono.setBase(hour);
        tvChrono.start();


        new CountDownTimer(hour, 1000) {
            @Override
            public void onTick(long l) {
                tvCountdown.setText(String.valueOf(l));
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }





}
