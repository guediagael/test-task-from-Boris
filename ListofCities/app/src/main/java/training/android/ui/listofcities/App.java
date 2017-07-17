package training.android.ui.listofcities;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import training.android.ui.listofcities.Entities.DaoMaster;
import training.android.ui.listofcities.Entities.DaoSession;

/**
 * Created by guedi on 7/16/2017.
 */

public class App extends Application {
    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"cities-db",null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }


    public DaoSession getDaoSession(){
        return daoSession;
    }




}
