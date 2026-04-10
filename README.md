# Group104 - TA Recruitment System (V2)
This is the group mini-project for the Software Engineering class. **It is strongly recommended to read ALL of this README.md file before contributing.**

## Team Members
- Bowen Lai / 231223069 / 2023215121
- Zehao Yang / 231223106 / 2023215121
- Zhuohang Tian / 231223128 / 2023215121
- Weiye Chen / 231223117 / 2023215121
- Qian Li / 231223092 / 2023215121
- Chenyu Kang / 231223122 / 2023215121

## Current Project Progress (V2 Refinement)
The system has moved beyond the V1 prototype and currently implements the **V2 recruitment management workflow**. Key features include:
* **Polished UI**: Modernized Java Swing interface with card-based layouts and enhanced navigation.
* **Application Notes**: TAs can now add a short note when applying for a job.
* **Application Tracking**: TAs have a dedicated "My Applications" panel to track the status (Pending, Accepted, Rejected) of their submissions.
* **MO Decision Management**: MOs can review specific applicant notes and perform explicit **Accept** or **Reject** actions.
* **Job Status Automation**: Jobs automatically transition to `CLOSED` status once an applicant is accepted.

## File structure

```
----Group104
   |----documents: Planning docs, user stories, and Git workflow guides
   |----codes
       |----src: Java source codes
           |----app: Bootstrap, main window, and shared context
           |----model: Domain objects and enums
           |----service: Business logic and recruitment rules
           |----storage: CSV repositories and file handling
           |----ui: Swing panels and interactive dialogs
       |----data: Runtime CSV storage files (tracked by .gitkeep)
```

## Build and Development Environment
- **Java Version**: 21
- **UI Framework**: Java Swing
- **Storage**: CSV file persistence

## Git

Commit msg use Conventional Commits:

feat: Feature. New functions.  
fix: Fix the bugs.  
docs: Docs changes.  
refactor: Refactor the codes.  
test: Test codes.  
chore: Others.  

---

## Current Working Method

This section records the currently proposed collaboration workflow for the project.

### Core Principle

Each iteration should produce a `working prototype` or `working software increment`.

The team will not wait until the whole system is complete before integrating work. Instead:

1. Define a small but runnable prototype first.
2. Review the prototype to identify missing requirements, bugs, and usability issues.
3. Convert the findings into concrete development tasks.
4. Assign tasks to team members and implement them in personal branches.
5. Merge finished work back into `main` in small steps.

### Team Coordination

The current project direction for each iteration is defined first by the core coordinators of that iteration. Their responsibilities include:

- defining the iteration goal
- preparing the current prototype or workflow draft
- identifying missing requirements and defects
- splitting work into concrete tasks
- reviewing whether work is ready to merge

Other team members should work on clearly scoped tasks based on the current prototype. This is intended to reduce confusion and communication cost.

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

### Current Technical Choice

- Java: `21`
- UI: `Java Swing`
- Storage: `CSV` files for the current version

### Build and Run

- Windows users should open `PowerShell` in the repository root and run:

```powershell
.\build_and_run
```

- The launcher delegates to `build_and_run_win.bat`
- Linux users should run:

```bash
./build_and_run_linux.sh
```

- macOS users should run:

```bash
./build_and_run_mac.sh
```

- The script requires `Java 21`

- Mac users should open `Terminal` in the repository root and run:
```Terminal
chmod +x build_and_run_mac.sh
./build_and_run_mac.sh
```
  
### Current Project Progress (V2 Refinement)
-The system has moved beyond the V1 prototype and currently implements the V2 recruitment management workflow. Key features include:

-Polished UI: Modernized Java Swing interface with card-based layouts and enhanced navigation.

-Application Notes: TAs can now add a short note when applying for a job.

-Application Tracking: TAs have a dedicated "My Applications" panel to track the status (Pending, Accepted, Rejected) of their submissions.

-MO Decision Management: MOs can review specific applicant notes and perform explicit Accept or Reject actions.

-Job Status Automation: Jobs automatically transition to CLOSED status once an applicant is accepted.
