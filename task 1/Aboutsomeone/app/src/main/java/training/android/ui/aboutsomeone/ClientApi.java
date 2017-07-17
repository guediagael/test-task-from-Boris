package training.android.ui.aboutsomeone;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by guedi on 7/16/2017.
 */

public interface ClientApi{

    @GET("people.php")
    Call<Person> getPerson();
}
