package org.launchcode.javawebdevtechjobspersistent.models;

import com.sun.istack.NotNull;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {

    @NotBlank(message = "You Must Add A Description(something please)")
    @Size(min = 3, max = 250, message = "Description Exceeds Maximum Allowed")
    private String description;

    @ManyToMany(mappedBy = "skills")
//    @NotBlank(message = "Select")
    private List<Job> jobs;

    public Skill() {}

    public Skill(String description) {
        super();
        this.description = description;
    }

    public List<Job> getJobs() { return jobs; }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}