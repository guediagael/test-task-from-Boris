package training.android.ui.counter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private int mInitialValue =0;
    private int mFinalValue=0;
    private TextView textView;
    private  CounterApi mApi;
    private  MyAsyncTask mAsyncTask ;
    private volatile int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        mApi = RetrofitClient.getInstance().create(CounterApi.class);
        getCount();

    }

    public void increment(View view){
        mFinalValue=mFinalValue+1;
        if (mAsyncTask==null){
            mAsyncTask = new MyAsyncTask();
            mAsyncTask.execute();
        }else if (mAsyncTask.getStatus().equals(AsyncTask.Status.FINISHED)||
                mAsyncTask.getStatus().equals(AsyncTask.Status.RUNNING)){
            mAsyncTask = new MyAsyncTask();
            mAsyncTask.execute();


        }else  counter=0;
    }


    public void decrement(View view){
        mFinalValue=mInitialValue-1;
        if (mAsyncTask==null){
            mAsyncTask = new MyAsyncTask();
            mAsyncTask.execute();
        }else if (mAsyncTask.getStatus().equals(AsyncTask.Status.FINISHED)){
            mAsyncTask = new MyAsyncTask();
            mAsyncTask.execute();
        }else  counter=0;
    }


    private void getCount(){

        Call<CounterResponse> call = mApi.getCountStatus();
        call.enqueue(new Callback<CounterResponse>() {
            @Override
            public void onResponse(Call<CounterResponse> call, Response<CounterResponse> response) {
                if (response.body()!=null){
                    CounterResponse respBody = response.body();
                    if (respBody.getError()==0){
                        mInitialValue =respBody.getValue();
                        mFinalValue =mInitialValue;
                        textView.setText(String.valueOf(respBody.getValue()));
                    }else
                        Toast.makeText(MainActivity.this,"Ощибка сервера :"+ response.code()+" " +
                                response.message(), Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(MainActivity.this,"Ощибка сервера :"+ response.code()+" " +
                            response.message(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CounterResponse> call, Throwable t) {
                if (t.getMessage().equals("timeout")) getCount();
                else
                    Toast.makeText(MainActivity.this,"Ощибка :"+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendCount(){
        Toast.makeText(MainActivity.this,"Прошли 10 сек без нажатия", Toast.LENGTH_LONG).show();
//        mFinalValue = Integer.valueOf(textView.getText().toString());
        Call<CounterResponse> call =null;
        int delta =mFinalValue-mInitialValue;
        if (delta<0){

            call = mApi.sendCount(String.valueOf(-delta),"decrement");
        }else if (delta>0){

            call = mApi.sendCount(String.valueOf(delta), "increment");
        }

        if (call!=null){
            call.enqueue(new Callback<CounterResponse>() {
                @Override
                public void onResponse(Call<CounterResponse> call, Response<CounterResponse> response) {
                    Log.d(getClass().getSimpleName(),response.toString());
                    if (response.body()!=null){
                        CounterResponse respBody = response.body();
                        if (respBody.getError()==0){
                            mInitialValue =respBody.getValue();
                            mFinalValue=mInitialValue;
                            textView.setText(String.valueOf(respBody.getValue()));
                        }else
                            Toast.makeText(MainActivity.this,"Ощибка сервера :"+ response.code()+" " +
                                    response.message(), Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(MainActivity.this,"Ощибка сервера :"+ response.code()+" " +
                                response.message(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<CounterResponse> call, Throwable t) {
                    if (t.getMessage().equals("timeout")) getCount();
                    else
                        Toast.makeText(MainActivity.this,"Ощибка :"+ t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    class MyAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void[] objects) {
            while (counter<10 ){

                counter++;
                try {
                    Thread.sleep(200);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            counter=0;
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            sendCount();
        }
    }
}
