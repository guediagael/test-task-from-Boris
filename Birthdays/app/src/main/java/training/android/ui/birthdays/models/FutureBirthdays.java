package training.android.ui.birthdays.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by guedi on 7/16/2017.
 */

public class FutureBirthdays implements Serializable{

    @SerializedName("time")
    @Expose
    private Long time;
    @SerializedName("description")
    @Expose
    private String description;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
