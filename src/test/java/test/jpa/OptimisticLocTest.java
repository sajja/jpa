package test.jpa;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transaction;
import java.util.ArrayList;
import java.util.List;

public class OptimisticLocTest {


    @Test
    public void testXX() {
        EntityManager em1 = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityManager em2 = Persistence.createEntityManagerFactory("test").createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        EntityTransaction tx2 = em2.getTransaction();
        EntityTransaction tx3 = em2.getTransaction();
//        tx3.begin();

//        Professor p1 = new Professor();
//        p1.setName("Charls Xavier");
//        p1.setSalary(0);
//        p1.setVersion(0);
//
//        em2.persist(p1);
//        tx3.commit();


        List x = em2.createQuery("from Professor").getResultList();
        System.out.println(x);

        tx1.begin();
        tx2.begin();
        List l = em1.createQuery("from Professor").getResultList();
        List m = em2.createQuery("from Professor").getResultList();

        Professor prof1 = (Professor) l.get(0);
        prof1.setName("Update11111");
        tx1.commit();

        Professor prof2 = (Professor) m.get(0);
        prof2.setName("Update2");
        tx2.commit();


        List f = em2.createQuery("from Professor").getResultList();
        System.out.println(f);
    }
}
