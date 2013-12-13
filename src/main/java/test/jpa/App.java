package test.jpa;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NamingException {
        EntityManager entityManager = Persistence.createEntityManagerFactory("tutorialPU").createEntityManager();
    }
}
