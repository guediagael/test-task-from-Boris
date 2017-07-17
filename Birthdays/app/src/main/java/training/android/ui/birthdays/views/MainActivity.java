package training.android.ui.birthdays.views;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import training.android.ui.birthdays.Controller;
import training.android.ui.birthdays.R;
import training.android.ui.birthdays.models.Birthdays;
import training.android.ui.birthdays.models.FutureBirthdays;
import training.android.ui.birthdays.models.PastBirthdays;

public class MainActivity extends AppCompatActivity implements MainView,
       PlaceholderFragment.FragmentListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private Controller mController;

    private Birthdays mBirthdays = new Birthdays();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),mBirthdays);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mController = new Controller(this);
        mController.loadData();

    }



    @Override
    public void birthdaysLoaded(Birthdays birthdays) {
        mSectionsPagerAdapter.dataRefreshed(birthdays);
    }


    @Override
    public void onError(String error) {

        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
        mSectionsPagerAdapter.stopLoading();

    }

    @Override
    public void refreshList(int tag) {
        mController.loadData();
    }

}
