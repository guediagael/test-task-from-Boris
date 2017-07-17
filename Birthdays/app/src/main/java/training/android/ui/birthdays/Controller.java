package training.android.ui.birthdays;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import training.android.ui.birthdays.api.BirthdaysApi;
import training.android.ui.birthdays.api.RetrofitService;
import training.android.ui.birthdays.models.Birthdays;
import training.android.ui.birthdays.views.MainView;

/**
 * Created by guedi on 7/16/2017.
 */

public class Controller {
    private BirthdaysApi mApi;
    private MainView mView;


    public Controller(MainView view) {
        mApi = RetrofitService.getInstance().create(BirthdaysApi.class);
        mView = view;
    }

    public void loadData(){
        Call<Birthdays> call = mApi.getBirthdays();
        call.enqueue(new Callback<Birthdays>() {
            @Override
            public void onResponse(Call<Birthdays> call, Response<Birthdays> response) {
                Log.d(getClass().getSimpleName(),response.toString());
                if (response.body()!=null){
                    Birthdays respBody = response.body();
                    mView.birthdaysLoaded(respBody);
                }else mView.onError("Возникла ощибка");
            }

            @Override
            public void onFailure(Call<Birthdays> call, Throwable t) {
                if (t.getMessage().equals("timeout")) loadData();
                else {
                    mView.onError(t.getMessage());
                    call.cancel();
                }
            }
        });
    }
}
