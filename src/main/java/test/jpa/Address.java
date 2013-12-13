package test.jpa;

import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Audited
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @AuditJoinTable(name = "xxxxxxx")
    private Department department;

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


    public void setDepartment(Department d) {
        this.department = d;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @AuditJoinTable(name = "xxxxxxx")
    public Department getDepartment() {
        return department;
    }
}
