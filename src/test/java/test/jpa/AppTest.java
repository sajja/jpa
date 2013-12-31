package test.jpa;


import org.testng.annotations.Test;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void auditNon_InverseTest() throws NamingException {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Department science = new Department();
        science.setName("Science");

        ArrayList<Professor> employees = new ArrayList<Professor>();

        Professor xavior = new Professor();
        xavior.setName("Xavior");
        employees.add(xavior);

        Professor magnito = new Professor();
        xavior.setName("Magnito");
        employees.add(magnito);

        science.setEmployees(employees);
        em.persist(science);

        Department investigation = new Department();
        investigation.setName("Investigation");

        Address sherlock = new Address();
        sherlock.setName("52, Baker st.");

        Address jamesbond = new Address();
        jamesbond.setName("Classified");

        jamesbond.setDepartment(investigation);
        sherlock.setDepartment(investigation);

        em.persist(sherlock);
        em.persist(jamesbond);


        tx.commit();
        tx = em.getTransaction();
        tx.begin();

        science = em.find(Department.class, science.getId());
        science.getEmployees().remove(0);
        em.persist(science);
        tx.commit();
    }


    @Test
    public void auditInverseTest() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Department cia = new Department();
        cia.setName("Cia");

        Address oo7 = new Address();
        oo7.setName("Classified");
        oo7.setDepartment(cia);


        Address oo8 = new Address();
        oo8.setName("Compromized");
        oo8.setDepartment(cia);

        em.persist(oo7);
        em.persist(oo8);

        oo7.setName("007 - modified");
        em.persist(oo7);

        tx.commit();

        tx = em.getTransaction();
        tx.begin();
        oo8 = em.find(Address.class, oo8.getId());
        oo8.setDepartment(null);
        em.persist(oo8);

        tx.commit();
    }

}
