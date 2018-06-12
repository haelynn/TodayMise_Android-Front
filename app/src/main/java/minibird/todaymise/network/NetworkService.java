package minibird.todaymise.network;

import minibird.todaymise.model.Main1Result;
import minibird.todaymise.model.Main2Result;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {

    @GET("/main1")
    Call<Main1Result> getMain1Result(@Query("date") String sDate,
                                     @Query("location") String sLocation);

    // main2
    @GET("/main2")
    Call<Main2Result> getMain2Result(@Query("XVal") String XValue,
                                     @Query("YVal") String YValue);

}
