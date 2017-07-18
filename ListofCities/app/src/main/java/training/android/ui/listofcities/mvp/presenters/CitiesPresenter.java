package training.android.ui.listofcities.mvp.presenters;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import training.android.ui.listofcities.App;
import training.android.ui.listofcities.Entities.City;
import training.android.ui.listofcities.Entities.CityDao;
import training.android.ui.listofcities.Entities.DaoSession;
import training.android.ui.listofcities.api.ApiClient;
import training.android.ui.listofcities.api.RetrofitService;
import training.android.ui.listofcities.mvp.models.CitiesResponse;
import training.android.ui.listofcities.mvp.views.CitiesView;
import training.android.ui.listofcities.ui.MainActivity;

/**
 * Created by guedi on 7/16/2017.
 */

public class CitiesPresenter implements Presenter {

    private CitiesView mView;
    private ApiClient mApi;
    private volatile int page;
    private volatile short numberOfTry = 0;
    private CityDao cityDao;

    public CitiesPresenter(CitiesView view, Context context) {
        this.mView = view;
        mApi = RetrofitService.getInstance().create(ApiClient.class);
        DaoSession daoSession =((App) ((MainActivity) context).getApplication()).getDaoSession();
        cityDao = daoSession.getCityDao();
        page = getLatestSavedPage();

    }

    @Override
    public void loadList() {
        Call<CitiesResponse> call = mApi.getCities(0);
        call.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(Call<CitiesResponse> call, Response<CitiesResponse> response) {
                Log.d(getClass().getSimpleName(),response.toString());
                if (response.body()!=null){
                    CitiesResponse respBody = response.body();
                    if (respBody.getError()==0){
                        for (String city : respBody.getCities()){
                            City c = new City();
                            c.setName(city);
                            try{
                                cityDao.insert(c);
                            }catch (SQLiteConstraintException e){
                                Log.d(getClass().getSimpleName(),e.getMessage());
                            }

                        }
                        mView.listLoaded(respBody.getCities());
                    }else  mView.onError("Возникла ощибка : " + response.code());
                }else mView.onError("Сервер не доступен : "+ response.code());
            }

            @Override
            public void onFailure(Call<CitiesResponse> call, Throwable t) {
                Log.d(getClass().getSimpleName(),t.getMessage());
                if (t.getMessage().equals("timeout")){
                    if (numberOfTry<3) {
                        numberOfTry++;
                        loadList();
                    }
                    else {
                        mView.onError("Проверьте свой интернет пожалуйта");
                        call.cancel();
                    }
                }
                mView.onError(t.getMessage());
                call.cancel();
            }
        });
    }

    @Override
    public void updateList() {
        page++;
        Call<CitiesResponse> call = mApi.getCities(page);
        call.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(Call<CitiesResponse> call, Response<CitiesResponse> response) {
                Log.d(getClass().getSimpleName(),response.toString());
                if (response.body()!=null){
                    CitiesResponse respBody = response.body();
                    if (respBody.getError()==0){
                        List<String> cites = new ArrayList<>();
                        cites.addAll(respBody.getCities());
                        for (String city : cites){
                            City c = new City();
                            c.setName(city);
                            c.setPageNumber(respBody.getPage());
                            try {
                                cityDao.insert(c);
                            }catch (SQLiteConstraintException e){
                                Log.d(getClass().getSimpleName(),e.getMessage());
                            }
                        }
                       mView.listUpdated(cites);

                    }else {
                        page--;
                        mView.onError("Вы скачали все элеметы");
                    }
                }else{
                    page--;
                    mView.onError("Пройзошла ощибка");
                }
            }

            @Override
            public void onFailure(Call<CitiesResponse> call, Throwable t) {
                page--;
                if (t.getMessage().equals("timeout")){
                    if (numberOfTry<3) {
                        numberOfTry++;
                        loadList();
                    }
                    else {
                        mView.onError("Проверьте свой интернет пожалуйта");
                        call.cancel();
                    }
                }
                mView.onError(t.getMessage());
                call.cancel();

            }
        });
    }

    @Override
    public void loadFromDb() {
        List<String> cities = new ArrayList<>();
        for (City city :cityDao.loadAll()) cities.add(city.getName());
        mView.listLoaded(cities);
    }

    private int getLatestSavedPage(){
        QueryBuilder<City> qb = cityDao.queryBuilder();
        qb.orderDesc(CityDao.Properties.PageNumber).limit(1);
        try{
            return  qb.list().get(0).getPageNumber();
        }catch (IndexOutOfBoundsException e){
            Log.d(getClass().getSimpleName(),e.getMessage());
        }
        return 0;
    }
}
