package nyc.c4q.androidpractical;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shant on 2/25/2018.
 */

public class BreedsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SHARED_PREFS_KEY = "loginCredentials";
    private SharedPreferences sharedPreferences;


    private ImageView terrier;
    private ImageView poodle;
    private ImageView retriever;
    private ImageView spaniel;
    private TextView welcome;

    private CardView terrierCard;
    private CardView spanielCard;
    private CardView retrieverCard;
    private CardView poodleCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds);

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText(getWelcomeMessage());

        terrier = (ImageView) findViewById(R.id.terrier_image);
        poodle = (ImageView) findViewById(R.id.poodle_image);
        retriever = (ImageView) findViewById(R.id.retriever_image);
        spaniel = (ImageView) findViewById(R.id.spaniel_image);

        terrierCard = (CardView) findViewById(R.id.terrier_cardview);
        terrierCard.setOnClickListener(this);

        spanielCard = (CardView) findViewById(R.id.spaniel_cardview);
        spanielCard.setOnClickListener(this);

        poodleCard = (CardView) findViewById(R.id.poodle_cardview);
        poodleCard.setOnClickListener(this);

        retrieverCard = (CardView) findViewById(R.id.retriever_cardview);
        retrieverCard.setOnClickListener(this);

        GetImages getImages = new GetImages();
        getImages.execute();

    }

    public String getWelcomeMessage(){
        String username = sharedPreferences.getString("username",null);
        StringBuilder welcome = new StringBuilder();
        welcome.append(getResources().getString(R.string.breed_question));
        welcome.append(username);
        welcome.append("?");
        return welcome.toString();
    }

    public String getRequestString(String breed) {
        StringBuilder request = new StringBuilder();
        request.append("https://dog.ceo/api/breed/");
        request.append(breed);
        request.append("/images/random");
        return request.toString();
    }


    private static String downloadData(String urlString) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            is = conn.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return new String(total);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @Override
    public void onClick(View view) {
        String breedToPass = "";

        switch (view.getId()) {
            case R.id.poodle_cardview:
                breedToPass = getResources().getString(R.string.poodle).toLowerCase();
                Log.d("onclick", "clicking");
                break;
            case R.id.spaniel_cardview:
                breedToPass = getResources().getString(R.string.spaniel).toLowerCase();
                break;
            case R.id.retriever_cardview:
                breedToPass = getResources().getString(R.string.retriever).toLowerCase();
                break;
            case R.id.terrier_cardview:
                breedToPass = getResources().getString(R.string.terrier).toLowerCase();
                break;

        }

        Intent goToDogsActivity = new Intent(this, DogsActivity.class);
        goToDogsActivity.putExtra("breed", breedToPass);
        startActivity(goToDogsActivity);
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

    private class GetImages extends AsyncTask<Void, Void, Void> {
        JSONObject terrierJson;
        JSONObject spanielJson;
        JSONObject poodleJson;
        JSONObject retrieverJson;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String terrierResult = downloadData(getRequestString("terrier"));
                terrierJson = new JSONObject(terrierResult);

                String poodleResult = downloadData(getRequestString("poodle"));
                poodleJson = new JSONObject(poodleResult);

                String retriverResult = downloadData(getRequestString("retriever"));
                retrieverJson = new JSONObject(retriverResult);

                String spanielResult = downloadData(getRequestString("spaniel"));
                spanielJson = new JSONObject(spanielResult);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            try {
                Picasso.with(getApplication().getApplicationContext()).load(terrierJson.getString("message")).into(terrier);
                Picasso.with(getApplication().getApplicationContext()).load(spanielJson.getString("message")).into(spaniel);
                Picasso.with(getApplication().getApplicationContext()).load(poodleJson.getString("message")).into(poodle);
                Picasso.with(getApplication().getApplicationContext()).load(retrieverJson.getString("message")).into(retriever);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
