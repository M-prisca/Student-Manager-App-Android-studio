package rw.ac.studentmanagementapp.activities;

import androidx.appcompat.app.AppCompatActivity;
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

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            // Delete student on long click
            Cursor cursor = db.getAllStudents();
            cursor.moveToPosition(position);
            int studentId = cursor.getInt(0);
            boolean deleted = db.deleteStudent(studentId);
            if(deleted) {
                Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                loadStudents();
            }
            return true;
        });
    }

    private void loadStudents() {
        studentList.clear();
        Cursor cursor = db.getAllStudents();
        if(cursor.moveToFirst()) {
            do {
                String student = cursor.getInt(0) + ". " + cursor.getString(1) + " - " + cursor.getInt(2) + " yrs";
                studentList.add(student);
            } while(cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }
}
