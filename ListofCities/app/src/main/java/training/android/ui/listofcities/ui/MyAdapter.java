package training.android.ui.listofcities.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import training.android.ui.listofcities.R;

/**
 * Created by guedi on 7/16/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<String> cities;

    public MyAdapter(List<String> cities) {
        this.cities = cities;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_view_holder,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String city = cities.get(position);
        holder.setData(city);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
