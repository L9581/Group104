# V1 Scope

This document defines the agreed scope for `Version 1` of the `Teaching Assistant Recruitment System`.

The purpose of `V1` is to deliver a `minimal but complete workflow` rather than a feature-rich system.

## Core Scope

`V1` only includes two roles:

- `TA`
- `MO`

An explicit login screen is optional in `V1`.

There will be no password system in this version.

Users only need to enter a name to register or enter the system.

The system must check for duplicate names and prevent repeated registration with the same name.

## MO Scope in V1

The `MO` role can post jobs.

Job posting is intentionally simplified in `V1`.

For this version, the MO only needs to enter:

- `job name`

No additional fields are required in `V1`, such as:

- description
- required skills
- hours
- pay

## TA Scope in V1

The `TA` role can:

- view all available jobs
- click to apply for a job

When a TA applies for a job, the backend data handling should add that TA's name to the applicant list of that job.

## MO Applicant Management in V1

The `MO` role can:

- view all jobs posted by that MO
- view the list of applicant names under each job
- click `Hire` for one applicant

Once an MO hires one TA for a job:

- that job becomes locked
- other TAs can still see the job, but they must not be able to apply for it anymore

## Workflow Summary

The planned `V1` workflow is:

1. A user enters a name and registers or enters the system.
2. An `MO` posts a job using only the job name.
3. A `TA` views all jobs.
4. A `TA` applies for a job.
5. The system records that TA under the selected job.
6. The `MO` reviews applicant names under that job.
7. The `MO` hires one applicant.
8. The job is locked and becomes unavailable for future applications.

## Design Intention

This version is intentionally minimal.

The goal is to prove that the project already has:

- a clear role-based workflow
- a complete recruitment loop
- data persistence
- a runnable prototype that can be demonstrated and extended in later versions
