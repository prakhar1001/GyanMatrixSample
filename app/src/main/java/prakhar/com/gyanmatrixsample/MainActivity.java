package prakhar.com.gyanmatrixsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import prakhar.com.gyanmatrixsample.Database.DatabaseHandler;
import prakhar.com.gyanmatrixsample.Fragments.PlayersListFragment;
import prakhar.com.gyanmatrixsample.Model.CricketRecordModel;

public class MainActivity extends AppCompatActivity {

    MaterialSearchView mSearchView;
    Toolbar mToolbar;
    PlayersListFragment playersListFragment;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                if (query.length() > 0)
                    searchQueryFromDB(query);
                else
                    Toast.makeText(MainActivity.this, "Empty Search String", Toast.LENGTH_LONG).show();
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                if (newText.length() != 0) {
                    if (newText.length() > 0)
                        searchQueryFromDB(newText);
                    else
                        Toast.makeText(MainActivity.this, "Empty Search String", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        if (savedInstanceState == null)

        {
            playersListFragment = new PlayersListFragment();
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if (playersListFragment != null) {
                ft.replace(R.id.frame_layout, playersListFragment);
                ft.commit();
            }
        } else

        {
            //send a variable to a fragment which can abduct api call
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            playersListFragment = (PlayersListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "playerListFragment");
            if (playersListFragment != null) {
                ft.replace(R.id.frame_layout, playersListFragment);
                ft.commit();
            }
        }
    }


    private void searchQueryFromDB(String query) {
        ArrayList<CricketRecordModel.Record> PlayerQuery = db.getAllContactsWithName(query);
        Log.d("size", String.valueOf(PlayerQuery.size()));
        if (PlayerQuery.size() > 0) {
            playersListFragment.setmRecordList(PlayerQuery);
        }
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

        if (playersListFragment != null)
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
