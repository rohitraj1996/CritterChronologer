package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.enums.PetType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private PetType type;
    private String name;

    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;

    @ManyToMany()
    @JoinTable(
            name = "pet_schedules",
            joinColumns = {@JoinColumn(name = "pet_id")},
            inverseJoinColumns = {@JoinColumn(name = "schedule_id")}
    )
    private Set<Schedule> schedules;

    private LocalDate birthDate;
    private String notes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
