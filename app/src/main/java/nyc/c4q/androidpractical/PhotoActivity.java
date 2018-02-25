package nyc.c4q.androidpractical;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import retrofit2.Callback;

/**
 * Created by Shant on 2/25/2018.
 */

public class PhotoActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_KEY = "loginCredentials";
    SharedPreferences sharedPreferences;

    ImageView bigPicture;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        bigPicture = (ImageView) findViewById(R.id.big_pic);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        Log.d("url", url);

        setImg(url);

    }

    public void setImg(String s){
        Picasso.with(this).load(s).fit().into(bigPicture);
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
