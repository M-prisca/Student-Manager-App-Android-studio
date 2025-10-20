package rw.ac.studentmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import rw.ac.studentmanagementapp.R;

public class MainActivity extends AppCompatActivity {

    Button btnAddStudent, btnViewStudents, btnExportCSV, btnPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnViewStudents = findViewById(R.id.btnViewStudents);
        btnExportCSV = findViewById(R.id.btnExportCSV);
        btnPrefs = findViewById(R.id.btnPrefs);

        // Navigate to AddStudentActivity
        btnAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });

        // Navigate to StudentListActivity
        btnViewStudents.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
            startActivity(intent);
        });

        // Navigate to CSV Export
        btnExportCSV.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExportCSVActivity.class);
            startActivity(intent);
        });

        // Navigate to SharedPreferences example
        btnPrefs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SharedPrefsActivity.class);
            startActivity(intent);
        });
    }
}
