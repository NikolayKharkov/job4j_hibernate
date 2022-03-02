package many;

import models.BaseVacancy;
import models.Candidate;
import models.Vacancy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class VacancyRun {
    public static void main(String[] args) {
        Candidate rsl = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            rsl = session.createQuery(
                    "select distinct c from Candidate c "
                            + "join fetch c.baseVacancy bv "
                            + "join fetch bv.vacancyList v "
                            + "where c.id = :id", Candidate.class
            ).setParameter("id", 3).uniqueResult();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        System.out.println(rsl);

    }
}
