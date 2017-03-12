package prakhar.com.gyanmatrixsample.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import prakhar.com.gyanmatrixsample.Adapter.PlayersRecordListAdapter;
import prakhar.com.gyanmatrixsample.ApiController.APIInterface;
import prakhar.com.gyanmatrixsample.ApiController.RetroClient;
import prakhar.com.gyanmatrixsample.Model.CricketRecordModel;
import prakhar.com.gyanmatrixsample.R;
import prakhar.com.gyanmatrixsample.Utils.AppStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lendingkart on 3/11/2017.
 */

public class PlayersListFragment extends Fragment {

    ListView mPlayerListView;
    private ProgressDialog mProgressDialog;
    Map<String, String> mQueryMap;


    ArrayList<CricketRecordModel.Record> mRecordList = null;
    PlayersRecordListAdapter mPlayersRecordListAdapter;


    public void setmRecordList(ArrayList<CricketRecordModel.Record> mRecordList) {
        this.mRecordList = mRecordList;
        mPlayersRecordListAdapter.addData(this.mRecordList);
    }

    public ArrayList<CricketRecordModel.Record> getmRecordList() {
        return mRecordList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueryMap = new HashMap<>();
        mQueryMap.put("type", "json");
        mQueryMap.put("query", "list_player");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playerslistfragment, container, false);


        mProgressDialog = displayProgressDialog(getActivity());
        mPlayerListView = (ListView) view.findViewById(R.id.players_listview);
        mPlayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mRecordList.get(position) != null) {
                    Bundle arg = new Bundle();
                    arg.putParcelable("RecordParcelableObject", mRecordList.get(position));
                    PlayersDetailsFrag playerDetailsFrag = new PlayersDetailsFrag();
                    playerDetailsFrag.setArguments(arg);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    if (playerDetailsFrag != null) {
                        ft.replace(R.id.frame_layout, playerDetailsFrag);
                        ft.addToBackStack("playerDetailsFrag");
                        ft.commit();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Cricket Record Found", Toast.LENGTH_LONG).show();
                }
            }
        });

        setHasOptionsMenu(true);

        mPlayersRecordListAdapter = new PlayersRecordListAdapter(getActivity(), new ArrayList<CricketRecordModel.Record>());
        mPlayerListView.setAdapter(mPlayersRecordListAdapter);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mRecordList == null) {
            callCricketRecordAPI();
        }
    }

    private void callCricketRecordAPI() {
        showProgress();
        if (AppStatus.getInstance(getActivity()).isOnline()) {

            Call<CricketRecordModel> CricketRecordCall = RetroClient.getInstance().getRetrofit().create(APIInterface.class).GET_CRICKET_RECORDS(mQueryMap);
            CricketRecordCall.enqueue(new Callback<CricketRecordModel>() {
                @Override
                public void onResponse(Call<CricketRecordModel> call, Response<CricketRecordModel> response) {
                    dismissProgress();
                    mRecordList = new ArrayList<CricketRecordModel.Record>();
                    mRecordList = (ArrayList<CricketRecordModel.Record>) response.body().getRecords();
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Cricket Records (" + mRecordList.size() + ")");
                    if (mRecordList.size() > 0) {
                        mPlayersRecordListAdapter.addData(mRecordList);
                        saveArrayListToSQLiteDB(mRecordList);
                    } else
                        Toast.makeText(getActivity(), "No Cricket Record Found", Toast.LENGTH_LONG).show();
                }


                @Override
                public void onFailure(Call<CricketRecordModel> call, Throwable t) {
                    dismissProgress();
                    if (!t.getMessage().equals(null))
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("failure", t.getMessage());
                }
            });
        } else {
            dismissProgress();
            NoInternetFragment noInternetFragment = new NoInternetFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            if (noInternetFragment != null) {
                ft.replace(R.id.frame_layout, noInternetFragment);
                ft.commit();
            }
        }
    }

    //TODO
    private void saveArrayListToSQLiteDB(ArrayList<CricketRecordModel.Record> mRecordList) {


    }

    //sort
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.sort_by_matches_played:
                if (getmRecordList().size() == 0) {
                    Toast.makeText(getActivity(), "No elements to sort", Toast.LENGTH_LONG).show();
                }
                if (getmRecordList().size() > 1) {
                    Collections.sort(getmRecordList(), new Comparator<CricketRecordModel.Record>() {
                        @Override
                        public int compare(CricketRecordModel.Record o1, CricketRecordModel.Record o2) {
                            return Integer.parseInt(o1.getMatchesPlayed()) - Integer.parseInt(o2.getMatchesPlayed());
                        }
                    });
                    mPlayersRecordListAdapter.addData(getmRecordList());
                    Toast.makeText(getActivity(), "Sorting Order : Ascending", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Sorting wont make any difference", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.sort_by_total_score:

                if (getmRecordList().size() == 0) {
                    Toast.makeText(getActivity(), "No elements to sort", Toast.LENGTH_LONG).show();
                }
                if (getmRecordList().size() > 1) {
                    Collections.sort(getmRecordList(), new Comparator<CricketRecordModel.Record>() {
                        @Override
                        public int compare(CricketRecordModel.Record o1, CricketRecordModel.Record o2) {
                            return Integer.parseInt(o1.getTotalScore()) - Integer.parseInt(o2.getTotalScore());
                        }
                    });
                    mPlayersRecordListAdapter.addData(getmRecordList());
                    Toast.makeText(getActivity(), "Sorting Order : Ascending", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Sorting wont make any difference", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getmRecordList() != null)
            mPlayersRecordListAdapter.addData(getmRecordList());
    }

    private void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    private void showProgress() {
        if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }


    public static ProgressDialog displayProgressDialog(Context context) {
        ProgressDialog progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.setContentView(R.layout.tresbudialog);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        assert progressDialog.getWindow() != null;
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();
        return progressDialog;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
