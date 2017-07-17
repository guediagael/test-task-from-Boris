package training.android.ui.listofcities.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import training.android.ui.listofcities.R;
import training.android.ui.listofcities.mvp.presenters.CitiesPresenter;
import training.android.ui.listofcities.mvp.views.CitiesView;
import training.android.ui.listofcities.mvp.presenters.Presenter;

public class MainActivity extends AppCompatActivity implements CitiesView {

    private Presenter mPresenter;
    private RecyclerView rvCities;
    private List<String> mCities;
    private SharedPreferences mSharedPreferences;
    private final String mHasLoadedEverythingFromServer = "HasLoadedEverythingFromServer";
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mSharedPreferences = getPreferences(MODE_PRIVATE);
        mCities = new ArrayList<>();
        mPresenter = new CitiesPresenter(this,this);
        rvCities = (RecyclerView) findViewById(R.id.recycler_cities);
        rvCities.setLayoutManager(new LinearLayoutManager(this));
        rvCities.setAdapter(new MyAdapter(mCities));
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rvCities.addItemDecoration(decoration);
        rvCities.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) rvCities.getLayoutManager();
                if (layoutManager.findLastCompletelyVisibleItemPosition()==mCities.size()-1) {
                    if (isConnected() && !isEverythingLoaded()){
                        mPresenter.updateList();
                        progressBar.setVisibility(View.VISIBLE);

                    }else {
                        if (!isConnected())onError("Нет доступ к интернету");
                        else {
                            onError("Все элементы загружены");
                            if (mCities.isEmpty()) mPresenter.loadFromDb();

                        }
                    }

                }
            }
        });


        if (isConnected()){
            progressBar.setVisibility(View.VISIBLE);
            mPresenter.loadList();

        }else {
            progressBar.setVisibility(View.GONE);
            onError("Нет доступ к интернету");
        }


    }

    @Override
    public void listLoaded(List<String> cities) {
        progressBar.setVisibility(View.GONE);
        mCities.addAll(cities);
        rvCities.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void listUpdated(List<String> cities, boolean isLast) {
        progressBar.setVisibility(View.GONE);
        int position=mCities.size();
        Log.d(getClass().getSimpleName(),"old size: "+mCities.size());
        mCities.addAll(cities);
        Log.d(getClass().getSimpleName(),"new size: "+mCities.size());
        rvCities.getAdapter().notifyItemInserted(position);
        if (isLast){
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(mHasLoadedEverythingFromServer,true);
            editor.commit();
        }
    }

    @Override
    public void onError(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork !=null && activeNetwork.isConnectedOrConnecting();
    }


    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }


    private boolean isEverythingLoaded(){

        return  mSharedPreferences
                .getBoolean(mHasLoadedEverythingFromServer,false);
    }


}
