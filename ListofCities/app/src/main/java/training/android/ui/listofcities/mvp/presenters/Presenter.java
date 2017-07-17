package training.android.ui.listofcities.mvp.presenters;

import training.android.ui.listofcities.api.ApiClient;

/**
 * Created by guedi on 7/16/2017.
 */

public interface Presenter {


    void loadList();

    void updateList();

    void loadFromDb();

}
