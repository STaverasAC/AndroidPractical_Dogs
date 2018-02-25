package nyc.c4q.androidpractical;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.androidpractical.controller.ImageAdapter;
import nyc.c4q.androidpractical.model.Image;
import nyc.c4q.androidpractical.network.DogAPIClient;

/**
 * Created by Shant on 2/25/2018.
 */

public class DogsActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_KEY = "loginCredentials";
    SharedPreferences sharedPreferences;

    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    String breed;
    List<Image> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs);

        ActionBar ab = getSupportActionBar();

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();
        breed = bundle.getString("breed");

        ab.setTitle(breed);

        recyclerView = (RecyclerView) findViewById(R.id.dogs_recview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        DogAPIClient dogAPIClient = new DogAPIClient(recyclerView, breed);
        dogAPIClient.start();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent goToLogin = new Intent(this, LoginActivity.class);
                startActivity(goToLogin);

                break;
        }
        return true;
    }


}
