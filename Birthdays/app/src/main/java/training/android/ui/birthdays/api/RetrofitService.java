package training.android.ui.birthdays.api;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by guedi on 7/16/2017.
 */

public class RetrofitService {
    private static final String BASE_URL = "http://murzinma.ru/api/";

    private static Retrofit retrofit =null;

    public static Retrofit getInstance(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation().create()))
                    .build();
        }
        return retrofit;
    }
}
