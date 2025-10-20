package rw.ac.studentmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import rw.ac.studentmanagementapp.R;
import rw.ac.studentmanagementapp.database.DatabaseHelper;

public class ExportCSVActivity extends AppCompatActivity {

    Button btnExport;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_csv);

        btnExport = findViewById(R.id.btnExportCSV);
        db = new DatabaseHelper(this);

        btnExport.setOnClickListener(v -> {
            try {
                File file = new File(getExternalFilesDir(null), "students.csv");
                FileWriter fw = new FileWriter(file);
                Cursor cursor = db.getAllStudents();
                while(cursor.moveToNext()) {
                    fw.append(cursor.getInt(0) + "," + cursor.getString(1) + "," + cursor.getInt(2) + "\n");
                }
                fw.flush();
                fw.close();
                Toast.makeText(this, "Exported to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error exporting CSV", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
