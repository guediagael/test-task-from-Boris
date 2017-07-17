package training.android.ui.listofcities.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import training.android.ui.listofcities.mvp.models.CitiesResponse;

/**
 * Created by guedi on 7/16/2017.
 */

public interface ApiClient {

    @GET("cities.php")
    Call<CitiesResponse> getCities(@Query("page") int page);

}
