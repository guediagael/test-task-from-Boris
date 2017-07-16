package training.android.ui.aboutsomeone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by guedi on 7/16/2017.
 */

public class Person {

    @SerializedName("name")
    @Expose
    private String fullName;

    @SerializedName("birthday")
    @Expose
    private String date;

    @SerializedName("description")
    @Expose
    private String description;

    public Person(String fullName, String date, String description) {
        this.fullName = fullName;
        this.date = date;
        this.description = description;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
