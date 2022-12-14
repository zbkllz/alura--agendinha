package laros.com.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import laros.com.dao.StudentDAO;
import laros.com.model.Student;
import laros.com.ui.adapter.StudentsListAdapter;

public class StudentsListView {

    private final StudentDAO dao;
    private final StudentsListAdapter adapter;
    private final Context context;

    public StudentsListView(Context context) {
        this.context = context;
        this.adapter = new StudentsListAdapter(this.context);
        this.dao = new StudentDAO();
    }

    public void approveDeleting(final MenuItem item) {
        new AlertDialog
                .Builder(context)
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

    public void refreshStudents() {
        adapter.refresh(dao.all());
    }

    private void remove(Student student) {
        adapter.remove(student);
        dao.remove(student);
    }

    public void adapterConfig(ListView studentsList) {
        studentsList.setAdapter(adapter);
    }
}
