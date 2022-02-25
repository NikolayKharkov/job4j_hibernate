package many;

import models.Author;
import models.Book;
import models.CarBrand;
import models.CarModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {

    public static void main(String[] args) {
        HbmRun hbmRun = new HbmRun();
        hbmRun.runManyToMany();
    }

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public void runOneToMany() {
        try {
            Session session = sf.openSession();
            session.beginTransaction();

            CarBrand brand = new CarBrand("BMW");
            brand.addCar(new CarModel("X5"));
            brand.addCar(new CarModel("X3"));
            brand.addCar(new CarModel("M1"));
            brand.addCar(new CarModel("X1"));
            brand.addCar(new CarModel("GT7"));

            session.save(brand);

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