package test.jpa;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.EntityManagerImpl;
import org.hibernate.stat.Statistics;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

/**
 * Created with IntelliJ IDEA.
 * User: sajith
 * Date: 12/31/13
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class CacheTest {

    @BeforeMethod
    public void init() {

    }

    @Test
    public void testSecondLevelCacheMissThenPut() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        Session session = getHibernateSession(em);
        Statistics stats = session.getSessionFactory().getStatistics();
        Department department = em.find(Department.class, 1);
        Assert.assertEquals(department.getName(), "Science");

        Assert.assertEquals(stats.getSecondLevelCacheHitCount(), 0);
        Assert.assertEquals(stats.getSecondLevelCacheMissCount(), 1);
        Assert.assertEquals(stats.getSecondLevelCachePutCount(), 1);
    }

    @Test
    public void testSecondLevelCacheHit() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        Session session = getHibernateSession(em);
        Statistics stats = session.getSessionFactory().getStatistics();
        Department department = em.find(Department.class, 1);
        Assert.assertEquals(department.getName(), "Science");
        em.find(Department.class, 1).getName();

        Assert.assertEquals(stats.getSecondLevelCacheHitCount(), 0);
        Assert.assertEquals(stats.getSecondLevelCacheMissCount(), 1);
        Assert.assertEquals(stats.getSecondLevelCachePutCount(), 1);

        session.evict(department);//remove from session.
        department = em.find(Department.class, 1);

        Assert.assertEquals(stats.getSecondLevelCacheHitCount(), 1);
        Assert.assertEquals(stats.getSecondLevelCacheMissCount(), 1);
        Assert.assertEquals(stats.getSecondLevelCachePutCount(), 1);


    }

    @Test
    public void testCacheHitWhenAccessedSameObjectInTwoSessions() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        SessionFactory factory = getHibernateSessionFactory(em);
        Statistics stats = factory.getStatistics();

        Department department = (Department) factory.openSession().load(Department.class, 1);

        Assert.assertEquals(department.getName(), "Science");
        Assert.assertEquals(stats.getSecondLevelCacheHitCount(), 0);
        Assert.assertEquals(stats.getSecondLevelCacheMissCount(), 1);
        Assert.assertEquals(stats.getSecondLevelCachePutCount(), 1);

        department = (Department) factory.openSession().load(Department.class, 1);

        Assert.assertEquals(department.getName(), "Science");
        Assert.assertEquals(stats.getSecondLevelCacheHitCount(), 1);
        Assert.assertEquals(stats.getSecondLevelCacheMissCount(), 1);
        Assert.assertEquals(stats.getSecondLevelCachePutCount(), 1);
    }

    @Test
    public void testQueryCacheInTwoSessions() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        SessionFactory factory = getHibernateSessionFactory(em);
        Statistics stats = factory.getStatistics();
        Session s1 = factory.openSession();

        Query query = s1.createQuery("from Department where id = 1");
        query.setCacheable(true);
        Department department = (Department) query.list().get(0);

        Assert.assertEquals(department.getName(), "Science");
        Assert.assertEquals(stats.getSecondLevelCacheHitCount(), 0);
        Assert.assertEquals(stats.getSecondLevelCacheMissCount(), 0);
        Assert.assertEquals(stats.getSecondLevelCachePutCount(), 1);
        Assert.assertEquals(stats.getQueryCacheHitCount(), 0);
        Assert.assertEquals(stats.getQueryCacheMissCount(), 1);
        Assert.assertEquals(stats.getQueryCachePutCount(), 1);

        s1.close();

        Session s2 = factory.openSession();
        query = s2.createQuery("from Department where id = 1");
        query.setCacheable(true);

        department = (Department) query.list().get(0);
//
        Assert.assertEquals(department.getName(), "Science");
        Assert.assertEquals(stats.getSecondLevelCacheHitCount(), 1);
        Assert.assertEquals(stats.getSecondLevelCacheMissCount(), 0);
        Assert.assertEquals(stats.getSecondLevelCachePutCount(), 1);
        Assert.assertEquals(stats.getQueryCacheHitCount(), 1);
        Assert.assertEquals(stats.getQueryCacheMissCount(), 1);
        Assert.assertEquals(stats.getQueryCachePutCount(), 1);
    }

    @Test(expectedExceptions = RollbackException.class)
    public void testReadOnlyCacheShouldNotBeWritable() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

        Department department = em.find(Department.class, 1);
        Assert.assertEquals(department.getName(), "Science");
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        department.setAge(10);
        em.persist(department);
        tx.commit();
    }

    @Test
    public void nonCachedObjectShouldNotMakeACashHitOrPut() {
        EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();
        Session session = getHibernateSession(em);
        Statistics stats = session.getSessionFactory().getStatistics();
        long cacheHitCount = stats.getSecondLevelCacheHitCount();
        long cacheMissCount = stats.getSecondLevelCacheMissCount();
        long cachePutCount = stats.getSecondLevelCachePutCount();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Department science = new Department();
        science.setName("Science");
        em.persist(science);
        tx.commit();

        Assert.assertEquals(cacheHitCount, 0);
        Assert.assertEquals(cacheMissCount, 0);
        Assert.assertEquals(cachePutCount, 0);

        em.close();
        em = Persistence.createEntityManagerFactory("test").createEntityManager();
        tx = em.getTransaction();
        tx.begin();
        Department department = em.find(Department.class, science.getId());
        tx.commit();


        cacheHitCount = stats.getSecondLevelCacheHitCount();
        cacheMissCount = stats.getSecondLevelCacheMissCount();
        cachePutCount = stats.getSecondLevelCachePutCount();

        Assert.assertNotNull(department);
        Assert.assertEquals(cacheHitCount, 0);
        Assert.assertEquals(cacheMissCount, 0);
        Assert.assertEquals(cachePutCount, 0);

    }

    private Session getHibernateSession(EntityManager entityManager) {
        Session session;
        if (entityManager.getDelegate() instanceof EntityManagerImpl) {
            EntityManagerImpl entityManagerImpl = (EntityManagerImpl) entityManager.getDelegate();
            session = entityManagerImpl.getSession();
        } else {
            session = (Session) entityManager.getDelegate();
        }
        return session;
    }


    private SessionFactory getHibernateSessionFactory(EntityManager entityManager) {
        return ((EntityManagerImpl) entityManager).getSession().getSessionFactory();
    }
}
