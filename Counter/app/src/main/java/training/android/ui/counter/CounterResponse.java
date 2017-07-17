package training.android.ui.counter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by guedi on 7/17/2017.
 */

public class CounterResponse {

    @SerializedName("value")
    @Expose
    private int value;

    @SerializedName("error")
    @Expose
    private int error;

    public CounterResponse() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
