package laros.com.ui.activity;

import static laros.com.ui.activity.ConstantActivities.STUDENT_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import laros.com.R;
import laros.com.model.Student;
import laros.com.ui.StudentsListView;

public class StudentListActivity extends AppCompatActivity {

    private static final String TITLE_APPBAR = "Students List";
    private final StudentsListView studentsListView = new StudentsListView(this);

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
            studentsListView.approveDeleting(item);
        }
        return super.onContextItemSelected(item);
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
        studentsListView.refreshStudents();

    }

    private void listConfig() {
        ListView studentsList = findViewById(R.id.lv_students_list);
        studentsListView.adapterConfig(studentsList);
        configClickListenerPerItem(studentsList);
        registerForContextMenu(studentsList);
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
}
