package csc1035.project2.Testing;

import csc1035.project2.HibernateUtil;
import csc1035.project2.Student;
import csc1035.project2.Module;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StudentModuleTesting {

    public static void main(String[] args) {

        Session session = null;

        Student stu1 = new Student();
        stu1.setStudentID(123);
        stu1.setFirstName("Test 1");
        stu1.setLastName("Student");

        Student stu2 = new Student();
        stu2.setStudentID(456);
        stu2.setFirstName("Test 2");
        stu2.setLastName("Student");

        Set<Student> stuList = new HashSet<>(Arrays.asList(stu1, stu2));

        Module mod1 = new Module();
        mod1.setModuleID("123");
        mod1.setModuleName("Test Module");
        mod1.setCredits(1);
        mod1.setWeeks(1);

        Set<Module> modList = new HashSet<>(Arrays.asList(mod1));

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.persist(stu1);
            session.persist(stu2);

            session.persist(mod1);

            for (Student stu : stuList) {
                stu.setModules(modList);
                session.persist(stu);
            }

            session.getTransaction().commit();

        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}
