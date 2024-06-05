package com.example.btth4_firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail;
    private List<Student> studentList;
    private ArrayAdapter<Student> studentArrayAdapter;
    private FirebaseDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        ListView listViewStudents = findViewById(R.id.listViewStudents);
        studentList = new ArrayList<Student>();
        studentArrayAdapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, studentList);
        listViewStudents.setAdapter(studentArrayAdapter);
        databaseHelper = new FirebaseDatabaseHelper();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateStudent();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deleteStudent();
            }
        });

        loadStudents();
    }

    // Helper method
    private void addStudent() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String id = databaseHelper.getReference().push().getKey();
        Student student = new Student(id, name, email);
        Toast.makeText(this, student.getEmail(), Toast.LENGTH_SHORT).show();
        databaseHelper.addStudent(student);
    }

    private void updateStudent(String id) {
        String email = editTextEmail.getText().toString();
        String name = editTextName.getText().toString();
        Student student = new Student(id, name, email);
        databaseHelper.updateStudent(id, student);
    }

    private void deleteStudent(String id) {
        databaseHelper.deleteStudent(id);
    }

    private void loadStudents() {
        databaseHelper.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student = postSnapshot.getValue(Student.class);
                    assert student != null;
                    Toast.makeText(MainActivity.this, student.getName(), Toast.LENGTH_SHORT).show();
                    studentList.add(student);
                }
                // Update ListView with the new data
                studentArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Khong load duoc data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}