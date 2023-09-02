package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {

    public static SessionFactory getSessionFactory() {

        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        try {
            return configuration.buildSessionFactory();
        } catch (Exception e) {
                System.out.println("Problem creating session");
                e.printStackTrace();
        }
        return null;
    }
}