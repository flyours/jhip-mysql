package com.jeff.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.jeff.domain.enumeration.Hobby;

/**
 * A DTO for the Foo entity.
 */
public class FooDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String name;

    @NotNull
    @Size(min = 10)
    private String addr;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private Integer grade;

    private LocalDate birthday;

    @Min(value = 100)
    @Max(value = 10000)
    private BigDecimal salary;

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

        FooDTO fooDTO = (FooDTO) o;

        if ( ! Objects.equals(id, fooDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FooDTO{" +
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
