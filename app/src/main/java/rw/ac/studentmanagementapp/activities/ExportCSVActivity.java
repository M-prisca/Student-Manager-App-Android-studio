package rw.ac.studentmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;
import rw.ac.studentmanagementapp.R;
import rw.ac.studentmanagementapp.database.DatabaseHelper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportCSVActivity extends AppCompatActivity {

    Button btnExport;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_csv);

        btnExport = findViewById(R.id.btnExport);
        db = new DatabaseHelper(this);

        btnExport.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Export");
            builder.setMessage("Do you want to export all students to CSV?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                boolean success = exportCSV();
                if(success) {
                    Toast.makeText(this, "CSV exported successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error exporting CSV", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        });
    }

    private boolean exportCSV() {
        Cursor cursor = db.getAllStudents();
        if(cursor == null || !cursor.moveToFirst()) {
            return false;
        }

        File csvFile = new File(getExternalFilesDir(null), "students.csv");

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append("ID,Name,Age\n");
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                writer.append(id + "," + name + "," + age + "\n");
            } while(cursor.moveToNext());
            cursor.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
