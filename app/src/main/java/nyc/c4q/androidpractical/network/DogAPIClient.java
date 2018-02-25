package nyc.c4q.androidpractical.network;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.androidpractical.controller.ImageAdapter;
import nyc.c4q.androidpractical.model.Image;
import nyc.c4q.androidpractical.model.ListOfImages;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shant on 2/25/2018.
 */

public class DogAPIClient implements Callback<ListOfImages> {

    RecyclerView recyclerView;
    String breed;
    List<Image> images = new ArrayList<>();

    public DogAPIClient(RecyclerView recyclerView,String breed){
        this.recyclerView = recyclerView;
        this.breed = breed;
    }

    public void start(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogAPI  dogAPI = retrofit.create(DogAPI.class);
        Call<ListOfImages> call = dogAPI.getPics(breed);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<ListOfImages> call, Response<ListOfImages> response) {
        ListOfImages result = response.body();
        List<String> imageList = result.getMessage();

        for(String s:imageList){
            Image img = new Image();
            img.setUrl(s);
            images.add(img);
        }

        ImageAdapter imageAdapter = new ImageAdapter(images);
        recyclerView.setAdapter(imageAdapter);

    }

    @Override
    public void onFailure(Call<ListOfImages> call, Throwable t) {
        t.printStackTrace();
    }

    public List<Image> getImages() {
        return images;
    }
}
