package many;

import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;


public class HbmRun {

    public static void main(String[] args) {
        HbmRun hbmRun = new HbmRun();
        hbmRun.getCandidateByNameRun("kate");
    }

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();


    public void saveCandidateRun() {
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate candidate1 = Candidate.of("Mikhail", 2, 55_000);
            Candidate candidate2 = Candidate.of("Kate", 3, 75_000);
            Candidate candidate3 = Candidate.of("Bob", 1, 30_000);
            session.save(candidate1);
            session.save(candidate2);
            session.save(candidate3);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void getAllCandidatesRun() {
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            for (Object candidate : session.createQuery("from Candidate").list()) {
                System.out.println(candidate);
            }
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void getCandidateByIdRun(int id) {
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Query query = session.createQuery("from Candidate c where c.id = :id");
            query.setParameter("id", id);
            System.out.println(query.uniqueResult());

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void getCandidateByNameRun(String name) {
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Query query = session.createQuery("from Candidate c where lower(c.name) = lower(:name)");
            query.setParameter("name", name);

            for (Object candidate : query.list()) {
                System.out.println(candidate);
            }

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    public void deleteCandidateByIdRun(int id) {
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            session.createQuery("delete Candidate c where c.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void updateCandidateNameById(int id, int experience) {
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            session.createQuery("update Candidate c set c.experience = :experience where c.id = :id")
                    .setParameter("experience", experience)
                    .setParameter("id", id)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void runOneToMany() {
        List<CarBrand> list = new ArrayList<>();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            list = session.createQuery("from CarBrand").list();
            for (CarBrand carBrand : list) {
                for (CarModel carModel : carBrand.getCars()) {
                    System.out.println(carModel);
                }
            }
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void runManyToMany() {
        try {
            Session session = sf.openSession();
            session.beginTransaction();

            Author author1 = new Author("Author_1");
            Author author2 = new Author("Author_2");
            Author author3 = new Author("Author_3");
            Book book1 = new Book("Book_1");
            Book book2 = new Book("Book_2");
            Book book3 = new Book("Book_3");
            Book book4 = new Book("Book_4");
            author1.addBook(book1);
            author1.addBook(book2);
            author2.addBook(book1);
            author2.addBook(book4);
            author3.addBook(book3);
            session.persist(author1);
            session.persist(author2);
            session.persist(author3);
            Author author1FromDb = session.find(Author.class, 1);
            session.remove(author1FromDb);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}