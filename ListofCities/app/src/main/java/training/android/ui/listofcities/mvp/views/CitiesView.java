package training.android.ui.listofcities.mvp.views;

import java.util.List;

/**
 * Created by guedi on 7/16/2017.
 */

public interface CitiesView {

    void listLoaded(List<String> cities);
    void listUpdated(List<String> cities, boolean isLast);
    void onError(String message);
}
