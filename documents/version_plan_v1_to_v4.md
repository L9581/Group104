# Version Plan V1-V4

This document defines a practical version plan for the `Teaching Assistant Recruitment System`.

The team will treat `V1` as a `minimal but complete workflow prototype`, then use `V2-V4` to refine, extend, and stabilise the system.

## Planning Principles

- Each version must be runnable.
- Each version must add clear user value compared with the previous version.
- `V1` should already form a basic functional loop.
- Later versions should improve and extend the workflow rather than restart it.
- Each version should focus on a small number of user stories.
- The system scope should remain small enough to finish within the coursework timeline.
- Optional AI features are not part of the core mid-term scope.

## System Scope for the Planned Versions

The planned system focuses on one clear workflow:

1. `MO` posts TA jobs.
2. `TA` views available jobs.
3. `TA` applies for a job.
4. `MO` reviews applicants and updates status.
5. `Admin` checks overall workload or recruitment summary.

## V1 - Minimal Functional Closed Loop

### Goal

Deliver the first runnable version as a `small but complete recruitment workflow`.

### Main Users

- `TA`
- `MO`

### Target Stories

- 4. As a `TA`, I want to view available jobs.
- 6. As a `TA`, I want to view job details.
- 7. As a `TA`, I want to apply for a job.
- 14. As a `MO`, I want to create a job post.
- 15. As a `MO`, I want to define basic job information.
- 32. As a `system user`, I want data stored in text files.
- 33. As a `system user`, I want basic input validation.
- 34. As a `system user`, I want existing data to be loaded.

### Required Features

- basic role selection or role switching
- basic main window or navigation skeleton
- job post form
- fields for title, module/department, description, skills, workload, and pay
- job list page
- job detail view
- application form
- save posted jobs into a text file or CSV
- save applications into a text file or CSV
- load saved job data when the program starts
- error message when required fields are empty
- success message after posting

### Deliverable Standard

- the program can be opened and used
- at least one job can be created and saved successfully
- a TA can view jobs and submit an application
- saved data can be seen in local text files
- the version is stable enough for a short demonstration

### Notes

This version should already look like a small recruitment system rather than an isolated single feature.

## V2 - Recruitment Management Refinement

### Goal

Strengthen the recruitment workflow by allowing MOs to review applicants and manage application progress.

### Main Users

- `MO`
- `TA`

### Target Stories

- 8. As a `TA`, I want to add a short application note.
- 9. As a `TA`, I want to view my submitted applications.
- 10. As a `TA`, I want to check my application status.
- 20. As a `MO`, I want to view all applicants for a specific job.
- 21. As a `MO`, I want to view an applicant's profile or application data.
- 23. As a `MO`, I want to update application status.

### Required Features

- applicant list view for each job
- display of core applicant information
- status update actions such as `Submitted`, `Shortlisted`, `Rejected`, and `Accepted`
- simple TA-side application record view
- optional application note support
- file persistence for updated status

### Deliverable Standard

- the MO can review applicants for a posted job
- the MO can update application status
- application status is preserved after restart
- the TA side can display application records or current status in a simple way

### Notes

This version makes the system manageable rather than only submittable.

## V3 - Admin Visibility and Data Quality

### Goal

Add administrative visibility and improve consistency of system behaviour.

### Main Users

- `Admin`
- all users indirectly

### Target Stories

- 26. As an `Admin`, I want to view all TAs' total workload.
- 27. As an `Admin`, I want to view a summary of jobs and applications.
- 28. As an `Admin`, I want to identify unfilled jobs.
- 35. As a `system user`, I want feedback messages for important actions.

### Required Features

- summary page for admin
- simple workload statistics, for example accepted jobs or application counts per TA
- identification of jobs that are still open or not yet filled
- improved validation and confirmation messages
- better file consistency checks

### Deliverable Standard

- admin can see meaningful system summary information
- major operations give understandable feedback
- the system data remains readable and consistent after normal use

### Notes

This version improves oversight and reliability.

## V4 - Usability Enhancement and Final Polish

### Goal

Improve usability, add secondary features, and prepare the system for final presentation quality.

### Main Users

- all users

### Target Stories

- 2. As a `TA`, I want to edit my profile.
- 5. As a `TA`, I want to filter jobs.
- 12. As a `TA`, I want to see my current workload.
- 17. As a `MO`, I want to view the jobs I posted.
- 18. As a `MO`, I want to edit an open job.
- 36. As a `system user`, I want the interface to be simple and clear.

### Required Features

- filter or search support for jobs
- profile editing or better personal information handling
- better navigation and clearer interaction flow
- optional job management improvements for MO
- UI polish and bug fixing
- preparation for documentation, testing, and final demonstration

### Deliverable Standard

- the system is easier to use than earlier versions
- secondary features are integrated without breaking the main workflow
- the version is suitable for final assessment preparation

### Notes

This version should focus on enhancement and presentation quality rather than rebuilding the core workflow.

## Suggested Mapping to Coursework Stages

### Before Mid-term

- target `V1` as the required minimum
- implement as much of `V2` as possible
- if time allows, begin the easiest parts of `V3`

### After Mid-term

- strengthen `V2` and `V3`
- complete `V4`
- add testing, documentation, and final demo preparation

## Open Questions for Team Discussion

- Do we need a separate TA profile file in V1, or can we keep application data simple first
- Should workload in V3 mean `accepted jobs only` or `all applications`
- Do we need job deadlines before V2, or can this be deferred
- Should application status be editable only by MO
- What is the minimum acceptable UI quality for mid-term demonstration
