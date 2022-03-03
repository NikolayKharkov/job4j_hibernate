package integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {

    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndFindById() {
        OrdersStore store = new OrdersStore(pool);
        Order saved = Order.of("name1", "description1");
        store.save(saved);
        Order expected = store.findById(1);
        assertEquals(saved.getDescription(), expected.getDescription());
        assertEquals(saved.getName(), expected.getName());
    }

    @Test
    public void whenSaveFewOrdersAndFindByName() {
        OrdersStore store = new OrdersStore(pool);
        Order order1 = Order.of("name1", "description1");
        Order order2 = Order.of("name2", "description2");
        Order order3 = Order.of("name1", "description3");
        store.save(order1);
        store.save(order2);
        store.save(order3);
        List<Order> byName = (List<Order>) store.findByName("name1");
        List<Order> expected = new ArrayList<>(Arrays.asList(order1, order3));
        assertEquals(byName, expected);
    }

    @Test
    public void whenSaveThenUpdate() {
        OrdersStore store = new OrdersStore(pool);
        String nameUpdated = "name_updated";
        String descUpdated = "description_updated";
        store.save(Order.of("name1", "description1"));
        store.updateById(1, nameUpdated, descUpdated);
        Order afterUpdate = store.findById(1);
        assertEquals(nameUpdated, afterUpdate.getName());
        assertEquals(descUpdated, afterUpdate.getDescription());
    }
}