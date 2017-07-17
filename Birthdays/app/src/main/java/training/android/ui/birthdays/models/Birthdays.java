package training.android.ui.birthdays.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by guedi on 7/16/2017.
 */

public class Birthdays {

    @SerializedName("past")
    @Expose
    private List<PastBirthdays> past = null;
    @SerializedName("future")
    @Expose
    private List<FutureBirthdays> future = null;

//    public Birthdays(List<PastBirthdays> past, List<FutureBirthdays> future) {
//        this.past = past;
//        this.future = future;
//    }

    public List<PastBirthdays> getPast() {
        return past;
    }

    public void setPast(List<PastBirthdays> past) {
        this.past = past;
    }

    public List<FutureBirthdays> getFuture() {
        return future;
    }

    public void setFuture(List<FutureBirthdays> future) {
        this.future = future;
    }
}
