package training.android.ui.aboutsomeone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tvName, tvBirthday, tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = (TextView)findViewById(R.id.text_full_name);
        tvBirthday = (TextView)findViewById(R.id.text_date);
        tvDesc = (TextView)findViewById(R.id.text_description);
    }

    public void showError(String errorMessage){
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    public void onClick(View view){
        ClientApi clientApi = RetrofitService.getClient().create(ClientApi.class);
        Call<Person> call = clientApi.getPerson();
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.body()!=null){
                    Person respbody = response.body();
                    tvName.setText(respbody.getFullName());
                    tvBirthday.setText(respbody.getDate());
                    tvDesc.setText(respbody.getDescription());
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }
}
