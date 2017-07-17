package training.android.ui.counter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by guedi on 7/17/2017.
 */

public interface CounterApi {

    @GET("counter.php")
    Call<CounterResponse> getCountStatus();

    @GET("counter.php")
    Call<CounterResponse> sendCount(@Query("delta") String delta, @Query("action") String action);
}
