package training.android.ui.birthdays.views;

import java.util.List;

import training.android.ui.birthdays.models.Birthdays;
import training.android.ui.birthdays.models.FutureBirthdays;
import training.android.ui.birthdays.models.PastBirthdays;

/**
 * Created by guedi on 7/16/2017.
 */

public interface MainView {

    void birthdaysLoaded(Birthdays pastBirthdays);
    void onError(String error);
}
