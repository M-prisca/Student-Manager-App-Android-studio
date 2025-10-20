package rw.ac.studentmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import rw.ac.studentmanagementapp.R;
import rw.ac.studentmanagementapp.database.DatabaseHelper;

public class AddStudentActivity extends AppCompatActivity {

    EditText etName, etAge;
    Button btnSave;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        btnSave = findViewById(R.id.btnSave);
        db = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String ageStr = etAge.getText().toString();
            if(name.isEmpty() || ageStr.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            int age = Integer.parseInt(ageStr);
            boolean inserted = db.addStudent(name, age);
            if(inserted) {
                Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etAge.setText("");
            } else {
                Toast.makeText(this, "Error adding student", Toast.LENGTH_SHORT).show();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int studentId = extras.getInt("studentId");
            etName.setText(extras.getString("studentName"));
            etAge.setText(String.valueOf(extras.getInt("studentAge")));

            btnSave.setOnClickListener(v -> {
                String name = etName.getText().toString();
                int age = Integer.parseInt(etAge.getText().toString());
                db.updateStudent(studentId, name, age);
                Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

    }
}
