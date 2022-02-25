package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "car_brands")
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarModel> cars = new ArrayList<>();

    public CarBrand(String name) {
        this.name = name;
    }

    public CarBrand() {

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

    public void addCar(CarModel carModel) {
        this.cars.add(carModel);
    }

    @Override
    public String toString() {
        return "CarBrand{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarBrand carBrand = (CarBrand) o;
        return id == carBrand.id && Objects.equals(name, carBrand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
