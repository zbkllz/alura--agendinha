package laros.com.dao;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import laros.com.model.Student;

public class StudentDAO {

    private final static List<Student> students = new ArrayList<>();
    private static int idCounter = 1;

    public void save(Student student) {
        student.setId(idCounter);
        students.add(student);
        refreshId();
    }

    private void refreshId() {
        idCounter++;
    }

    public void edit(Student student) {
        Student studentFound = searchStudentPerId(student);
        if (studentFound != null) {
                int studentPosition = students.indexOf(studentFound);
                students.set(studentPosition, student);
            }
    }

    @Nullable
    private Student searchStudentPerId(Student student) {
        for (Student a : students) {
            if (a.getId() == student.getId()) {
                return a;
            }
        }
        return null;
    }

    public List<Student> all() {
        return new ArrayList<>(students);
    }

    public void remove(Student student) {
        Student returnedStudent = searchStudentPerId(student);
        if (returnedStudent != null){
            students.remove(returnedStudent);
        }
    }
}
