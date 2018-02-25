package nyc.c4q.androidpractical.network;


/**
 * Created by Shant on 2/25/2018.
 */


import nyc.c4q.androidpractical.model.ListOfImages;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DogAPI {

    @GET("api/breed/{breedname}/images")
    Call<ListOfImages> getPics(@Path("breedname") String breedname);


}
