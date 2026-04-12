# V2 Scope

This document defines the agreed scope for `Version 2` of the `Teaching Assistant Recruitment System`.

The purpose of `V2` is to extend the minimal closed loop from `V1` into a clearer and more usable workflow, while still keeping the system simple.

## Core Direction

`V2` does not introduce new roles or complex authentication.

The system still only includes:

- `TA`
- `MO`

Passwords are still not part of the scope.

The main improvement in `V2` is the addition of clearer panels and a simple application form workflow.

## TA Scope in V2

The `TA` side should contain two main panels:

- `All Jobs`
- `My Applications`

### All Jobs

The TA can:

- view all jobs
- select a job
- open a simple application form for that job

The application form should remain minimal.

The TA only needs to:

- enter text content
- click `Apply`

No complex form structure is required in `V2`.

### My Applications

The TA can view the jobs they have already applied for.

This panel is intended to give the TA a clear record of their own submitted applications.

## MO Scope in V2

The `MO` side should contain three main panels:

- `All Jobs`
- `My Posted Jobs`
- `Post New Job`

### All Jobs

The MO can view all jobs in the system.

### My Posted Jobs

The MO can view the jobs posted by that MO.

This panel should display a list of the MO's posted jobs.

For each posted job, the MO can view all applicant names under that job.

Each applicant should have an option to open a simple decision view.

In that view, the MO can:

- read the text submitted by that applicant
- choose `Reject`
- choose `Accept`
- choose `Exit`

`Exit` means leaving the view without making any decision.

Job status in `V2` should be simplified to:

- `Open`
- `Closed`

The logic should remain simple:

- once the MO clicks `Accept` for one applicant, that job becomes `Closed`

### Post New Job

The MO can create a new job post.

The UI can remain simple and consistent with the other panels.

## Design Choice

`Apply for Jobs` is not defined as a separate panel in `V2`.

Instead, applying for a job is treated as an action inside the `All Jobs` workflow.

This is intended to reduce UI complexity, reduce state management problems, and make the code easier to implement correctly.

## Workflow Summary

The planned `V2` workflow is:

1. A `TA` opens `All Jobs`.
2. The `TA` selects a job.
3. The system opens a simple application form.
4. The `TA` enters text and clicks `Apply`.
5. The system stores the application.
6. The `TA` can later open `My Applications` to view submitted applications.
7. The `MO` can navigate between `All Jobs`, `My Posted Jobs`, and `Post New Job`.
8. In `My Posted Jobs`, the `MO` can open an applicant entry, read the submitted text, and choose `Accept`, `Reject`, or `Exit`.
9. Once one applicant is accepted, the job becomes `Closed`.

## Status Behaviour

### Job Status

Jobs only use two statuses in `V2`:

- `Open`
- `Closed`

If a job is `Closed`, it should not appear in the `All Jobs` panel for the TA side.

This is a deliberate simplification to reduce UI and logic complexity.

### Application Result

If a TA opens `My Applications`, they should be able to see whether their application has been accepted.

## Design Intention

This version is still intentionally lightweight.

The goal is to improve usability and structure without introducing too many new rules or data fields.

`V2` should feel like a cleaner and more manageable extension of `V1`, not a redesign of the system.
