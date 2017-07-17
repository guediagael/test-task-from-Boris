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
    private ClientApi clientApi;

    private String mName,mBirthday, mDescription;

    private static final String NAME= "name";
    private static final String BIRTHDAYS = "birthday";
    private static final String DESC = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = (TextView)findViewById(R.id.text_full_name);
        tvBirthday = (TextView)findViewById(R.id.text_date);
        tvDesc = (TextView)findViewById(R.id.text_description);

        clientApi = RetrofitService.getClient().create(ClientApi.class);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mName = savedInstanceState.getString(NAME);
        mBirthday = savedInstanceState.getString(BIRTHDAYS);
        mDescription = savedInstanceState.getString(DESC);

        tvName.setText(mName);
        tvBirthday.setText(mBirthday);
        tvDesc.setText(mDescription);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(NAME,tvName.getText().toString());
        outState.putString(BIRTHDAYS, tvBirthday.getText().toString());
        outState.putString(DESC, tvDesc.getText().toString());
        super.onSaveInstanceState(outState);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        clientApi = null;
    }

    public void showError(String errorMessage){
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    public void onClick(View view){

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
                call.cancel();
            }
        });
    }
}
