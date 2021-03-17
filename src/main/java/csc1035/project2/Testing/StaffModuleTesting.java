package csc1035.project2.Testing;

import csc1035.project2.HibernateUtil;
import csc1035.project2.Staff;
import csc1035.project2.Module;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StaffModuleTesting {

    public static void main(String[] args) {

        Session session = null;

        Staff staff1 = new Staff();
        staff1.setStaffID("123");
        staff1.setFirstName("Test 1");
        staff1.setLastName("Staff");

        Staff staff2 = new Staff();
        staff2.setStaffID("456");
        staff2.setFirstName("Test 2");
        staff2.setLastName("Staff");

        Set<Staff> staffList = new HashSet<>(Arrays.asList(staff1, staff2));

        Module mod2 = new Module();
        mod2.setModuleID("456");
        mod2.setModuleName("Test Module 2");
        mod2.setCredits(2);
        mod2.setWeeks(2);

        Set<Module> modList = new HashSet<>(Arrays.asList(mod2));

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.persist(staff1);
            session.persist(staff2);

            session.persist(mod2);

            for (Staff staff : staffList) {
                staff.setModules(modList);
                session.persist(staff);
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