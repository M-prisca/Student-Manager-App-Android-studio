package rw.ac.studentmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import rw.ac.studentmanagementapp.R;
import rw.ac.studentmanagementapp.database.DatabaseHelper;
import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper db;
    ArrayList<String> studentList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        listView = findViewById(R.id.listView);
        db = new DatabaseHelper(this);
        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        listView.setAdapter(adapter);

        loadStudents();

        // Tap to edit
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor = db.getAllStudents();
            if(cursor != null && cursor.moveToPosition(position)) {
                int studentId = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);

                Intent intent = new Intent(this, AddStudentActivity.class);
                intent.putExtra("studentId", studentId);
                intent.putExtra("studentName", name);
                intent.putExtra("studentAge", age);
                startActivity(intent);
                cursor.close();
            }
        });

        // Long press to delete
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Cursor cursor = db.getAllStudents();
            if(cursor != null && cursor.moveToPosition(position)) {
                int studentId = cursor.getInt(0);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you want to delete this student?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    db.deleteStudent(studentId);
                    loadStudents();
                    Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("No", null);
                builder.show();

                cursor.close();
            }
            return true; // long press handled
        });
    }

    private void loadStudents() {
        studentList.clear();
        Cursor cursor = db.getAllStudents();
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    String student = cursor.getInt(0) + ". " + cursor.getString(1) + " - " + cursor.getInt(2) + " yrs";
                    studentList.add(student);
                } while(cursor.moveToNext());
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }
}
