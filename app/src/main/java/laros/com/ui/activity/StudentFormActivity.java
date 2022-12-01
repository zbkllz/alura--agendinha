package laros.com.ui.activity;

import static laros.com.ui.activity.ConstantActivities.STUDENT_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import laros.com.R;
import laros.com.dao.StudentDAO;
import laros.com.model.Student;

public class StudentFormActivity extends AppCompatActivity {


    private static final String TITLE_APPBAR_NU = "New student";
    private static final String TITLE_APPBAR_EDIT = "Edit student";
    private EditText nameField;
    private EditText phoneField;
    private EditText emailField;
    private final StudentDAO dao = new StudentDAO();
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        formInitializing();
        loadStudent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_form_submit_student, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_form_submit_student) {
            submitData();
        }
        return super.onContextItemSelected(item);
    }

    private void loadStudent() {
        Intent data = getIntent();
        if (data.hasExtra(STUDENT_KEY)) {
            setTitle(TITLE_APPBAR_EDIT);
            student = (Student) data.getSerializableExtra(STUDENT_KEY);
            fillingDataFields();
        } else {
            setTitle(TITLE_APPBAR_NU);
            student = new Student();
        }
    }

    private void fillingDataFields() {
        nameField.setText(student.getName());
        phoneField.setText(student.getPhone());
        emailField.setText(student.getEmail());
    }

    private void submitData() {
        fillingStudentForm();
        if (student.hasValidId()) {
            dao.edit(student);
        } else {
            dao.save(student);
        }
        finish();
    }

    private void formInitializing() {
        nameField = findViewById(R.id.et_student_name);
        phoneField = findViewById(R.id.et_student_phone);
        emailField = findViewById(R.id.et_student_email);
    }

    private void fillingStudentForm() {
        String name = nameField.getText().toString();
        String phone = phoneField.getText().toString();
        String email = emailField.getText().toString();

        student.setName(name);
        student.setPhone(phone);
        student.setEmail(email);
    }
}