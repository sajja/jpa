package test.jpa;


import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Audited
@AuditTable(value = "DDD")
public class Department {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Audited
    private int id;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    private String name;

    @OneToMany(targetEntity = Professor.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "dept_id")
    @AuditJoinTable(name = "yyy")
    private java.util.List<Professor> employees;

    @OneToMany(targetEntity = Address.class, mappedBy = "department")
    @AuditJoinTable(name = "xxxxxxx")
    private java.util.List<Address> addresses;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String deptName) {
        this.name = deptName;
    }

    public List<Professor> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Professor> employees) {
        this.employees = employees;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @AuditJoinTable(name = "xxxxxxx")
    public List<Address> getAddresses() {
        return addresses;
    }
}