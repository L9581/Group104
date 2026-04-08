# Group104

Software Engineering group project for the `Teaching Assistant Recruitment System`.

## Team Members

- Bowen Lai / 231223069 / 2023215121
- Zehao Yang / 231223106 / 2023215121
- Zhuohang Tian / 231223128 / 2023215121
- Weiye Chen / 231223117 / 2023215121
- Qian Li / 231223092 / 2023215121
- Chenyu Kang / 231223122 / 2023215121

## Working Method

This project will be developed using a lightweight Agile workflow suitable for a small student team and the course requirements.

### Core Principle

Each iteration should produce a `working prototype` or `working software increment`.

We do not wait until the whole system is complete before integrating work. Instead:

1. A small but runnable prototype is defined first.
2. The prototype is reviewed to identify missing requirements, bugs, and usability issues.
3. The issues found are converted into concrete development tasks.
4. Tasks are assigned to team members and implemented in branches.
5. Finished tasks are reviewed and merged back into `main` in small steps.

### Team Coordination

The project direction is defined first by the core coordinators of the current iteration. Their responsibilities include:

- defining the iteration goal
- preparing the current prototype or workflow draft
- identifying missing requirements and defects
- splitting work into concrete tasks
- reviewing whether work is ready to merge

Other team members take clearly scoped tasks based on the current prototype. This is intended to reduce confusion and reduce communication cost.

### Iteration Pattern

For each iteration, the team follows this cycle:

1. Define a small iteration goal.
2. Produce or update a runnable prototype.
3. Review the prototype and record:
   - missing requirements
   - bugs
   - data fields that need to be added
   - UI or workflow problems
4. Break the findings into small implementation tasks.
5. Assign tasks to branches.
6. Merge completed work into `main` after checking that it does not break the current runnable version.

### Branch and Merge Rule

- `main` must remain the latest stable and demonstrable version.
- Each member should work on their own branch.
- Work should be merged in `small steps`, not in one large final merge.
- A change should be merged only when:
  - it has a clear purpose
  - it does not break existing runnable behaviour
  - it is understandable to the rest of the team

### Task Assignment Rule

Tasks should be assigned as `clear deliverables`, not vague ideas.

Good task examples:

- implement job post form
- implement CSV storage for jobs
- implement application submission page
- write user stories document
- prepare demo script

Bad task examples:

- improve the system
- handle frontend
- fix something in applications

## Repository Structure

```text
Group104
|-- documents
|-- codes
|   `-- src
`-- README.md
```

## Git Convention

Commit messages should follow simple Conventional Commit style:

- `feat`: new feature
- `fix`: bug fix
- `docs`: documentation update
- `refactor`: code restructuring
- `test`: test-related work
- `chore`: other maintenance work
