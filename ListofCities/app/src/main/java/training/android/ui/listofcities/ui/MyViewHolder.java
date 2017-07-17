package training.android.ui.listofcities.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import training.android.ui.listofcities.R;

/**
 * Created by guedi on 7/16/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public MyViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text_city);
    }

    public void setData(String cityName){
        textView.setText(cityName);
    }
}
