package training.android.ui.birthdays.api;

import retrofit2.Call;
import retrofit2.http.GET;
import training.android.ui.birthdays.models.Birthdays;

/**
 * Created by guedi on 7/16/2017.
 */

public interface BirthdaysApi {

    @GET("dates.php")
    Call<Birthdays> getBirthdays();
}
