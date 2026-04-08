# User Stories Pool

This document records the full user story pool for the `Teaching Assistant Recruitment System` based on the course handout. It is intentionally broader than the mid-term scope so that the team can prioritise stories into iterations.

## Actors

- Teaching Assistant (`TA` / applicant)
- Module Organiser (`MO`)
- Admin
- System-level user needs

## TA / Applicant Stories

1. As a `TA`, I want to create my profile so that I can save my personal details, contact information, major, and skills.
2. As a `TA`, I want to edit my profile so that my application information stays up to date.
3. As a `TA`, I want to upload or record my CV information so that MOs can review my background.
4. As a `TA`, I want to view available jobs so that I can know what opportunities are open.
5. As a `TA`, I want to filter jobs by module, department, or job type so that I can find suitable positions faster.
6. As a `TA`, I want to view job details so that I can understand responsibilities, required skills, workload, and pay.
7. As a `TA`, I want to apply for a job so that I can express interest in that role.
8. As a `TA`, I want to add a short application note so that I can explain my strengths or availability.
9. As a `TA`, I want to view my submitted applications so that I can track what I have already applied for.
10. As a `TA`, I want to check my application status so that I know whether I was shortlisted, rejected, or accepted.
11. As a `TA`, I want to withdraw an application before the deadline so that I can change my decision.
12. As a `TA`, I want to see my current workload so that I do not over-commit.
13. As a `TA`, I want the system to tell me when required profile information is missing so that I can complete my application materials.

## MO Stories

14. As a `MO`, I want to create a job post so that I can publish a TA vacancy.
15. As a `MO`, I want to define the title, module, department, description, required skills, workload, and pay for a job so that the post is complete and clear.
16. As a `MO`, I want to set an application deadline so that I can control the recruitment period.
17. As a `MO`, I want to view the jobs I posted so that I can manage my vacancies.
18. As a `MO`, I want to edit an open job so that I can correct mistakes or update requirements.
19. As a `MO`, I want to close a job post so that it stops receiving new applications.
20. As a `MO`, I want to view all applicants for a specific job so that I can review candidates.
21. As a `MO`, I want to view an applicant's profile and skills so that I can judge their suitability.
22. As a `MO`, I want to view an applicant's CV or application note so that I can make a more informed decision.
23. As a `MO`, I want to update an application status so that the recruitment process is trackable.
24. As a `MO`, I want to select the final TA for a job so that the role can be filled.
25. As a `MO`, I want to avoid selecting overworked TAs so that job allocation is more balanced.

## Admin Stories

26. As an `Admin`, I want to view all TAs' total workload so that I can monitor whether anyone is overloaded.
27. As an `Admin`, I want to view a summary of all jobs and applications so that I can monitor overall recruitment progress.
28. As an `Admin`, I want to identify unfilled jobs so that I can follow up with the responsible MO.
29. As an `Admin`, I want to check how many jobs a TA has applied for or accepted so that I can support coordination.
30. As an `Admin`, I want to correct wrong records when necessary so that the system data remains accurate.
31. As an `Admin`, I want to export job or application data to text files or CSV so that I can archive and analyse the results.

## System Stories

32. As a `system user`, I want all data to be stored in text files or CSV so that the system complies with the coursework restriction.
33. As a `system user`, I want the system to validate inputs and show error messages so that invalid data is reduced.
34. As a `system user`, I want the system to load existing data on startup so that previous records are preserved.
35. As a `system user`, I want success messages for important actions so that I know whether operations completed.
36. As a `system user`, I want the interface to be simple and clear so that different users can complete tasks quickly.

## Optional AI Enhancement Stories

37. As a `TA`, I want to see how well my skills match a job so that I can judge whether to apply.
38. As a `TA`, I want the system to identify missing skills so that I know my gaps.
39. As a `MO`, I want applicants to be ranked by skill match so that screening becomes more efficient.
40. As an `Admin`, I want the system to flag workload imbalance so that job assignment is fairer.

## Suggested Priority View

### Core Stories

- 1. Create TA profile
- 4. View available jobs
- 6. View job details
- 7. Apply for a job
- 14. Create a job post
- 20. View applicants for a job
- 23. Update application status
- 26. View workload or summary information
- 32. Store data in text files
- 33. Validate inputs
- 34. Load persisted data

### Important but Not Essential for Mid-term

- 2. Edit profile
- 8. Add application note
- 9. View submitted applications
- 10. Check application status
- 17. View posted jobs
- 18. Edit job
- 19. Close job
- 21. View applicant details
- 24. Select final TA
- 27. View recruitment summary
- 28. Identify unfilled jobs
- 29. Check TA application counts
- 35. Show confirmation messages
- 36. Keep interface simple and usable

### Optional or Final-stage Stories

- 3. Upload CV
- 5. Filter jobs
- 11. Withdraw application
- 12. View own workload
- 13. Detect missing profile information
- 22. View CV or note
- 25. Avoid overloaded TA selection
- 30. Correct records
- 31. Export data
- 37-40. AI-assisted features
