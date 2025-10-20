package rw.ac.studentmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import rw.ac.studentmanagementapp.R;

public class SharedPrefsActivity extends AppCompatActivity {

    EditText etUsername;
    Button btnSave, btnLoad;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);

        etUsername = findViewById(R.id.etUsername);
        btnSave = findViewById(R.id.btnSavePrefs);
        btnLoad = findViewById(R.id.btnLoadPrefs);

        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        btnSave.setOnClickListener(v -> {
            String name = etUsername.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", name);
            editor.apply();
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        });

        btnLoad.setOnClickListener(v -> {
            String name = prefs.getString("username", "Guest");
            etUsername.setText(name);
            Toast.makeText(this, "Loaded!", Toast.LENGTH_SHORT).show();
        });
    }
}
