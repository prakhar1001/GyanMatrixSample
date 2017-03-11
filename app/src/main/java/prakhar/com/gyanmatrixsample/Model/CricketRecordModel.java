package prakhar.com.gyanmatrixsample.Model;

/**
 * Created by lendingkart on 3/11/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CricketRecordModel implements Parcelable {

    @SerializedName("records")
    @Expose
    private List<Record> records = null;
    @SerializedName("quote_max")
    @Expose
    private String quoteMax;
    @SerializedName("quote_available")
    @Expose
    private String quoteAvailable;

    protected CricketRecordModel(Parcel in) {
        quoteMax = in.readString();
        quoteAvailable = in.readString();
    }

    public static final Creator<CricketRecordModel> CREATOR = new Creator<CricketRecordModel>() {
        @Override
        public CricketRecordModel createFromParcel(Parcel in) {
            return new CricketRecordModel(in);
        }

        @Override
        public CricketRecordModel[] newArray(int size) {
            return new CricketRecordModel[size];
        }
    };

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public String getQuoteMax() {
        return quoteMax;
    }

    public void setQuoteMax(String quoteMax) {
        this.quoteMax = quoteMax;
    }

    public String getQuoteAvailable() {
        return quoteAvailable;
    }

    public void setQuoteAvailable(String quoteAvailable) {
        this.quoteAvailable = quoteAvailable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quoteMax);
        dest.writeString(quoteAvailable);
    }


    public class Record implements Parcelable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("total_score")
        @Expose
        private String totalScore;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("matches_played")
        @Expose
        private String matchesPlayed;
        @SerializedName("country")
        @Expose
        private String country;

        protected Record(Parcel in) {
            id = in.readString();
            name = in.readString();
            image = in.readString();
            totalScore = in.readString();
            description = in.readString();
            matchesPlayed = in.readString();
            country = in.readString();
        }

        public final Creator<Record> CREATOR = new Creator<Record>() {
            @Override
            public Record createFromParcel(Parcel in) {
                return new Record(in);
            }

            @Override
            public Record[] newArray(int size) {
                return new Record[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(String totalScore) {
            this.totalScore = totalScore;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMatchesPlayed() {
            return matchesPlayed;
        }

        public void setMatchesPlayed(String matchesPlayed) {
            this.matchesPlayed = matchesPlayed;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(image);
            dest.writeString(totalScore);
            dest.writeString(description);
            dest.writeString(matchesPlayed);
            dest.writeString(country);
        }

    }
}