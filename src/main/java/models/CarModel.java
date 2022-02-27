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
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private CarBrand carBrand;

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public CarModel(String name, CarBrand carBrand) {
        this.name = name;
        this.carBrand = carBrand;
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
        CarModel carModel = (CarModel) o;
        return id == carModel.id && Objects.equals(name, carModel.name) && Objects.equals(carBrand, carModel.carBrand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, carBrand);
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



    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    @Override
    public String toString() {
        return "CarModel{"
                + "name='" + name + '\''
                + ", carBrand=" + carBrand.getName()
                + '}';
    }
}
