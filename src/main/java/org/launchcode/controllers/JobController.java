package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job someJob = jobData.findById(id);
        model.addAttribute("jobObject", someJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job" ;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@Valid JobForm newJobForm, Errors errors, Model model){
        // use model binding? @ModelAttribute (@20:00 in 3.8 video Models (Part2))
        // (@ModelAttribute @Valid JobForm newJobForm, Errors errors, Model model)

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        // boolean: are there errors?
        // render the new job form
        if (errors.hasErrors()){
            //model.addAttribute("name", "name");
            //model.addAttribute(new JobForm());
            return "new-job";
        }


            Job newJob = new Job();

            Employer jobsEmployer = jobData.getEmployers().findById(newJobForm.getEmployerId());
            Location jobsLocation = jobData.getLocations().findById(newJobForm.getLocationId());
            PositionType jobsPosition = jobData.getPositionTypes().findById(newJobForm.getPositionTypeId());
            CoreCompetency jobsCoreCompetency = jobData.getCoreCompetencies().findById(newJobForm.getCoreCompetencyId());

            newJob.setName(newJobForm.getName());
            newJob.setEmployer(jobsEmployer);
            newJob.setLocation(jobsLocation);
            newJob.setPositionType(jobsPosition);
            newJob.setCoreCompetency(jobsCoreCompetency);

            // save the new object to the array of objects
            jobData.add(newJob);

            //String redirect = "redirect:/job?id=" + newJob.getId();
            //pass newJob object to the template
            model.addAttribute("jobObject", newJob);
            //return redirect;
            return "redirect:/job?id=" + newJob.getId();

    }
}
