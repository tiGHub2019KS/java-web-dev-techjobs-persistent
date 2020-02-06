package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Job;
import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;



/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title", "My Jobs");
        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    @GetMapping("jobs")
    public String displayAllJobs(Model model) {
        model.addAttribute("title", "All Jobs");
        model.addAttribute("jobs", jobRepository.findAll());
        return "jobs";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {

        model.addAttribute("title", "Add Job");
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute(new Job());

        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId,
                                    @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            model.addAttribute("employers", employerRepository.findAll());
            model.addAttribute("skills", skillRepository.findAll());

            return "add";

        }

//        } else {
//            Optional optEmployer = employerRepository.findById(employerId);
//            if (optEmployer.isPresent()) {
//                Employer employer = (Employer) optEmployer.get();
//                newJob.setEmployer(employer);
//                List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
//                newJob.setSkills(skillObjs);
//                jobRepository.save(newJob);
//            } else {
//                return "add";
//            }
//        }
//        return "redirect:";


//        Employer employer = (Employer) result.get();
//        newJob.setEmployer(employer);
//        model.addAttribute("employer", employer.getName());

        Optional<Employer> employerOptional = employerRepository.findById(employerId);
        Employer employer = employerOptional.get();
        newJob.setEmployer(employer);

        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);

//        Location location = (Location) optLocation.get();
//        newJob.setLocation(location);
//        model.addAttribute("location", location.getName());
//


        jobRepository.save(newJob);

        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        Optional optJob = jobRepository.findById(jobId);

           if (optJob.isPresent()) {
            Job job = (Job) optJob.get();
            model.addAttribute("job", job);
            return "view";
        } else {
               return "redirect:";
        }

    }

    @GetMapping("delete/{jobId}")
    public String displayDeleteJobForm(Model model, @PathVariable int jobId) {

        Optional jobBeingDeleted = jobRepository.findById(jobId);

        if (jobBeingDeleted.isPresent()) {
            Job job = (Job) jobBeingDeleted.get();
            model.addAttribute("job", job);
            return "delete";
        } else {
            return "redirect:";
        }
    }

    @PostMapping("delete")
    public String processDeleteJobForm(@RequestParam(required = false) int[] jobId) {

        if (jobId != null) {
            for (int id : jobId) {
                jobRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("update/{jobId}")
    public String displayUpdateJobForm(Model model, @PathVariable int jobId) {
        model.addAttribute("title", "Update Job");
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());

        Optional jobBeingUpdated = jobRepository.findById(jobId);
          if (jobBeingUpdated.isPresent()) {
              Job job = (Job) jobBeingUpdated.get();
              model.addAttribute("job", job);
              return "update";
          } else {
              return "redirect:";
          }
    }

    @PostMapping("update")
    public String processUpdateJobForm(int jobId, String name, @RequestParam int employerId,
                                       @RequestParam List<Integer> skills) {

        Optional<Employer> result = employerRepository.findById(employerId);
          if (result.isEmpty()) {
              return "update";
          }

          Optional jobBeingUpdated = jobRepository.findById(jobId);

            if (jobBeingUpdated.isPresent()) {
                Job job = (Job) jobBeingUpdated.get();
                Employer employer = (Employer) result.get();
                job.setEmployer(employer);
                List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
                job.setSkills(skillObjs);
                job.setName(name);

                jobRepository.save(job);
                return "redirect:";
            }
            return "redirect:";
    }

}
