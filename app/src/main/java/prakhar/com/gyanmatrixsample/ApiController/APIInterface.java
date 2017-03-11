package prakhar.com.gyanmatrixsample.ApiController;

import java.util.Map;

import prakhar.com.gyanmatrixsample.Model.CricketRecordModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by lendingkart on 3/12/2017.
 */

public interface APIInterface {

//    http://hackerearth.0x10.info/api/gyanmatrix?type=json&query=list_player
    String ENDPOINT = "http://hackerearth.0x10.info/";

    @GET("api/gyanmatrix")
    Call<CricketRecordModel> GET_CRICKET_RECORDS(@QueryMap Map<String, String> parameterMap);

}
