# V1 Current Progress

This document records the current implementation progress of `V1`.

## Implemented Features

- name-based login / registration
- role selection between `TA` and `MO`
- duplicate-name checking
- `MO` can post a job with a minimal job name field
- `TA` can view open jobs
- `TA` can apply for a job
- `MO` can view jobs posted by that MO
- `MO` can open an applicant list for a posted job
- `MO` can hire one applicant
- once hired, the job becomes `CLOSED`
- closed jobs no longer appear in the `TA` jobs list

## Current Code Structure

- `app`: bootstrap, main window, shared context
- `model`: core data objects and enums
- `service`: business logic and workflow rules
- `storage`: CSV repositories and file helpers
- `ui`: Swing panels and dialogs

## Current Runtime Behaviour

- application data is stored in CSV files under `codes/data/`
- compiled output is stored under `codes/build/`
- Windows users can start the app from PowerShell with:

```powershell
.\build_and_run
```

## Notes

- the current version is intended to satisfy the minimal `V1` recruitment loop
- `V2` should extend this structure rather than replace it
