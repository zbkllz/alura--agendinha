package laros.com;

import android.app.Application;

import laros.com.dao.StudentDAO;
import laros.com.model.Student;

public class AgendinhaApp extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        createNPCForTesting();
    }

    private void createNPCForTesting() {
        StudentDAO dao = new StudentDAO();
        dao.save(new Student("Fulano", "1122223333", "fuleco@alura.com.br"));
        dao.save(new Student("Cicrana", "1122223333", "cricri@gmail.com"));
        dao.save(new Student("Beltrano", "1122224444", "bebel@gmail.com"));
    }
}
