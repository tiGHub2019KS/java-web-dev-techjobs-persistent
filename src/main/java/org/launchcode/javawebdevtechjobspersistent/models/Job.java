package org.launchcode.javawebdevtechjobspersistent.models;

import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import javax.persistence.*;
import javax.persistence.Entity;

import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Job extends AbstractEntity{


    @ManyToOne
    private Employer employer;

    @ManyToMany
//    @NotBlank(message = "Please Select At Least One Skill")
//    private List<Skill> skills = new ArrayList<>();
    private List<Skill> skills;

    public Job() {
    }

       public Job(Employer  anEmployer, List<Skill> someSkills) {
        super();
        this.employer = anEmployer;
        this.skills = someSkills;
    }

    // Getters and setters.

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }


}
