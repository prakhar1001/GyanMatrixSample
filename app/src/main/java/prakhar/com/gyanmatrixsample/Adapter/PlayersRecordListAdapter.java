package prakhar.com.gyanmatrixsample.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import prakhar.com.gyanmatrixsample.CustomView.CustomFontTextView;
import prakhar.com.gyanmatrixsample.Model.CricketRecordModel;
import prakhar.com.gyanmatrixsample.R;

/**
 * Created by lendingkart on 3/12/2017.
 */

public class PlayersRecordListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<CricketRecordModel.Record> mCricketRecordModelList;
    RecordViewHolder viewHolder;

    public PlayersRecordListAdapter(Context context, ArrayList<CricketRecordModel.Record> cricketRecordModels) {
        mContext = context;
        mCricketRecordModelList = cricketRecordModels;
    }

    public void addData(ArrayList<CricketRecordModel.Record> recordList) {
        mCricketRecordModelList = recordList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mCricketRecordModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCricketRecordModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.record_item_row, parent, false);
            viewHolder = new RecordViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RecordViewHolder) convertView.getTag();
        }

        viewHolder.PlayerName.setText(mCricketRecordModelList.get(position).getName());
        return convertView;
    }

    private class RecordViewHolder {

        CustomFontTextView PlayerName;

        RecordViewHolder(View view) {
            PlayerName = (CustomFontTextView) view.findViewById(R.id.recordtitle);
        }
    }
}
