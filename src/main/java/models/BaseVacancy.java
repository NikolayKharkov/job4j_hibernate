package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "base_vacancy")
public class BaseVacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nameVacancyBase;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancyList = new ArrayList<>();

    public static BaseVacancy of(String nameVacancyBase) {
        BaseVacancy result = new BaseVacancy();
        result.nameVacancyBase = nameVacancyBase;
        return result;
    }

    public void addVacancy(Vacancy vacancy) {
        this.vacancyList.add(vacancy);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCandidateName() {
        return nameVacancyBase;
    }

    public void setCandidateName(String nameVacancyBase) {
        this.nameVacancyBase = nameVacancyBase;
    }

    public List<Vacancy> getVacancyList() {
        return vacancyList;
    }

    public void setVacancyList(List<Vacancy> vacancyList) {
        this.vacancyList = vacancyList;
    }

    @Override
    public String toString() {
        return "BaseVacancy{"
                + "id=" + id
                + ", candidateName='" + nameVacancyBase + '\''
                + ", vacancyList=" + vacancyList
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
        BaseVacancy that = (BaseVacancy) o;
        return id == that.id
                && Objects.equals(nameVacancyBase, that.nameVacancyBase)
                && Objects.equals(vacancyList, that.vacancyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameVacancyBase, vacancyList);
    }
}
