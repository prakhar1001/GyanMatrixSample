package prakhar.com.gyanmatrixsample.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import prakhar.com.gyanmatrixsample.CustomView.CustomFontTextView;
import prakhar.com.gyanmatrixsample.Model.CricketRecordModel;
import prakhar.com.gyanmatrixsample.R;

/**
 * Created by lendingkart on 3/12/2017.
 */

public class PlayersDetailsFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_player_details, container, false);


        Bundle args = getArguments();
        CricketRecordModel.Record myClass = (CricketRecordModel.Record) args
                .getParcelable("RecordParcelableObject");
        CustomFontTextView Name, TotalScore, MatchesPlayed, Country, Description;
        ImageView imageView;


        imageView = (ImageView) view.findViewById(R.id.image);
        Name = (CustomFontTextView) view.findViewById(R.id.Name);
        TotalScore = (CustomFontTextView) view.findViewById(R.id.total_score);
        MatchesPlayed = (CustomFontTextView) view.findViewById(R.id.matches_played);
        Country = (CustomFontTextView) view.findViewById(R.id.country);
        Description = (CustomFontTextView) view.findViewById(R.id.description);


        Picasso.with(getActivity())
                .load(myClass.getImage())
                .into(imageView);
        Name.setText("Name :\n" + myClass.getName());
        TotalScore.setText("Total Score :\n" + myClass.getTotalScore());
        MatchesPlayed.setText("Matches Played :\n" + myClass.getMatchesPlayed());
        Country.setText("Country :\n" + myClass.getCountry());
        Description.setText("Description :\n" + myClass.getDescription());

        return view;
    }
}
