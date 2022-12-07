package laros.com.ui.activity;

import static laros.com.ui.activity.ConstantActivities.STUDENT_KEY;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import laros.com.R;
import laros.com.dao.StudentDAO;
import laros.com.model.Student;
import laros.com.ui.adapter.StudentsListAdapter;

public class StudentListActivity extends AppCompatActivity {

    private static final String TITLE_APPBAR = "Students List";
    private final StudentDAO dao = new StudentDAO();
    private StudentsListAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        setTitle(TITLE_APPBAR);
        fabConfigEnrollStudent();
        listConfig();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_students_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_students_list_menu_delete) {
            approveDeleting(item);
        }
        return super.onContextItemSelected(item);
    }

    private void approveDeleting(final MenuItem item) {
        new AlertDialog
                .Builder(this)
                .setTitle("Deleting...")
                .setMessage("Are you sure?")
                .setPositiveButton("DuH!", (dialog, which) -> {
                    AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Student selectedStudent = adapter.getItem(menuInfo.position);
                    remove(selectedStudent);
                })
                .setNegativeButton("NOPE", null)
                .show()
        ;
    }

    private void fabConfigEnrollStudent() {
        FloatingActionButton btnAddStudent = findViewById(R.id.fab_add_new_student);
        btnAddStudent.setOnClickListener(v -> openFormInsertMode());
    }

    private void openFormInsertMode() {
        startActivity(new Intent(this,
                StudentFormActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshStudents();

    }

    private void refreshStudents() {
        adapter.refresh(dao.all());
    }

    private void listConfig() {
        ListView studentsList = findViewById(R.id.lv_students_list);
        adapterConfig(studentsList);
        configClickListenerPerItem(studentsList);
        registerForContextMenu(studentsList);
    }

    private void remove(Student student) {
        adapter.remove(student);
        dao.remove(student);
    }

    private void configClickListenerPerItem(ListView studentsList) {
        studentsList.setOnItemClickListener((adapterView, view, position, id) -> {
            Student selectedStudent = (Student) adapterView.getItemAtPosition(position);
            openFormEditMode(selectedStudent);
        });
    }

    private void openFormEditMode(Student student) {
        Intent goToStudentFormActivity = new Intent(StudentListActivity.this,
                StudentFormActivity.class);
        goToStudentFormActivity.putExtra(STUDENT_KEY, student);
        startActivity(goToStudentFormActivity);
    }

    private void adapterConfig(ListView studentsList) {
        adapter = new StudentsListAdapter(this);
        studentsList.setAdapter(adapter);
    }
}
