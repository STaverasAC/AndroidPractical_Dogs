package nyc.c4q.androidpractical;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_KEY = "loginCredentials";
    private SharedPreferences sharedPreferences;

    private EditText username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username_edittext);
        password = (EditText) findViewById(R.id.password_edittext);
        login = (Button) findViewById(R.id.login_button);

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        if (isInSharedPreferences()) {
            Intent goToBreedsActivity = new Intent(this, BreedsActivity.class);
            startActivity(goToBreedsActivity);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = username.getText().toString();
                String passwordInput = password.getText().toString();

                if (!isFieldBlank(usernameInput) &&
                        !isFieldBlank(passwordInput) &&
                        isStringContained(usernameInput, passwordInput)) {
                    password.setText(R.string.string_contained);
                }

                if (isFieldBlank(usernameInput)) {
                    username.setHint(R.string.username_empty);
                }

                if (isFieldBlank(passwordInput)) {
                    password.setHint(R.string.password_empty);
                }

                if (!isStringContained(usernameInput, passwordInput) &&
                        !isFieldBlank(usernameInput) &&
                        !isFieldBlank(passwordInput)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", usernameInput);
                    editor.putString("password", passwordInput);
                    editor.commit();

                    Intent goToBreedsActivity = new Intent(getApplicationContext(), BreedsActivity.class);
                    startActivity(goToBreedsActivity);

                }

            }
        });

    }

    public boolean isStringContained(String a, String b) {
        return b.contains(a);
    }

    public boolean isFieldBlank(String s) {
        return TextUtils.isEmpty(s);
    }

    public boolean isInSharedPreferences() {
        return sharedPreferences.getString("username", null) != null && sharedPreferences.getString("password", null) != null;
    }


}
