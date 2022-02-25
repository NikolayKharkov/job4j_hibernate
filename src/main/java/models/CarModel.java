package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car_models")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public CarModel(String name) {
        this.name = name;
    }

    public CarModel() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel carModels = (CarModel) o;
        return id == carModels.id && Objects.equals(name, carModels.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CarModels{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
