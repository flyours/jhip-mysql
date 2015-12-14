package com.jeff.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.jeff.domain.enumeration.Hobby;

/**
 * A Foo.
 */
@Entity
@Table(name = "foo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "foo")
public class Foo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @NotNull
    @Size(min = 10)
    @Column(name = "addr", nullable = false)
    private String addr;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Min(value = 100)
    @Max(value = 10000)
    @Column(name = "salary", precision=10, scale=2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "hobby")
    private Hobby hobby;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Foo foo = (Foo) o;
        return Objects.equals(id, foo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Foo{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", addr='" + addr + "'" +
            ", grade='" + grade + "'" +
            ", birthday='" + birthday + "'" +
            ", salary='" + salary + "'" +
            ", hobby='" + hobby + "'" +
            '}';
    }
}
