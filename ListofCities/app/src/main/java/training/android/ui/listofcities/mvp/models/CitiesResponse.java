package training.android.ui.listofcities.mvp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by guedi on 7/16/2017.
 */

public class CitiesResponse {

    @SerializedName("error")
    @Expose
    private int error;

    @SerializedName("pages")
    @Expose
    private int pages;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("cities")
    @Expose
    private List<String> cities;


    public CitiesResponse(int error, int pages, int page, List<String> cities) {
        this.error = error;
        this.pages = pages;
        this.page = page;
        this.cities = cities;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }
}
