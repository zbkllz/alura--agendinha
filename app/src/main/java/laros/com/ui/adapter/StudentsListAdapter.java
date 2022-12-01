package laros.com.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import laros.com.R;
import laros.com.model.Student;

public class StudentsListAdapter extends BaseAdapter {

    private final List<Student> students = new ArrayList<>();
    private final Context context;

    public StudentsListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Student getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View builtView = buildView(viewGroup);
        Student returnedStudent = students.get(position);
        relate(builtView, returnedStudent);
        return builtView;
    }

    private void relate(View builtView, Student returnedStudent) {
        TextView name = builtView.findViewById(R.id.tv_student_name);
        name.setText(returnedStudent.getName());
        TextView phone = builtView.findViewById(R.id.tv_student_phone);
        phone.setText(returnedStudent.getPhone());
    }

    private View buildView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.student_item, viewGroup, false);
    }

    public void refresh(List<Student> students){
        this.students.clear();
        this.students.addAll(students);
        notifyDataSetChanged();
    }


    public void remove(Student student) {
        students.remove(student);
        notifyDataSetChanged();
    }
}
