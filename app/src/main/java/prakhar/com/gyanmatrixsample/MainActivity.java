package prakhar.com.gyanmatrixsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import prakhar.com.gyanmatrixsample.Fragments.PlayersListFragment;
import prakhar.com.gyanmatrixsample.Model.CricketRecordModel;

public class MainActivity extends AppCompatActivity {

    MaterialSearchView mSearchView;
    Toolbar mToolbar;
    PlayersListFragment playersListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                searchQueryFromDB(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        if (savedInstanceState == null) {
            playersListFragment = new PlayersListFragment();
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if (playersListFragment != null) {
                ft.replace(R.id.frame_layout, playersListFragment);
                ft.commit();
            }
        } else {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            playersListFragment = (PlayersListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "playerListFragment");
            if (playersListFragment != null) {
                ft.replace(R.id.frame_layout, playersListFragment);
                ft.commit();
            }
        }
    }


    private void searchQueryFromDB(String query) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

        return super.onCreateOptionsMenu(menu);
    }



    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("TAG, onSavedInstanceState");

        if (getSupportFragmentManager().getFragments().contains(playersListFragment))
            getSupportFragmentManager().putFragment(outState, "playerListFragment", playersListFragment);

        outState.putParcelableArrayList("ParceableRecordList", playersListFragment.getmRecordList());
    }

    protected void onRestoreInstanceState(Bundle savedState) {
        System.out.println("TAG, onRestoreInstanceState");
        ArrayList<CricketRecordModel.Record> mRepoArrayList = savedState.getParcelableArrayList("ParceableRecordList");
        if (mRepoArrayList != null && playersListFragment != null) {
            playersListFragment.setmRecordList(mRepoArrayList);
        }
    }


}
