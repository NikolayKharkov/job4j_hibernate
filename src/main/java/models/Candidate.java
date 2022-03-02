package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int experience;

    private int salary;

    @OneToOne(fetch = FetchType.LAZY)
    private BaseVacancy baseVacancy;

    public static Candidate of(String name, int experience, int salary) {
        Candidate result = new Candidate();
        result.name = name;
        result.experience = experience;
        result.salary = salary;
        return result;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("Candidate: id=%s, name=%s, experience=%s, salary=%s", id, name, experience, salary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id && experience == candidate.experience && salary == candidate.salary && Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, experience, salary);
    }

    public BaseVacancy getBaseVacancy() {
        return baseVacancy;
    }

    public void setBaseVacancy(BaseVacancy baseVacancy) {
        this.baseVacancy = baseVacancy;
    }
}
