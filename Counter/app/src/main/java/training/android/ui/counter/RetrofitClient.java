package training.android.ui.counter;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by guedi on 7/17/2017.
 */

public class RetrofitClient {

    private final static String BASE_URL = "http://murzinma.ru/api/";

    private static Retrofit retrofit = null;

    public static Retrofit getInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                    ))
                    .build();
        }
        return retrofit;
    }
}
