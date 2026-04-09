# V1 Manual Test Checklist

This checklist validates the current V1 workflow and is aligned with the actual UI labels and behavior in the codebase.

## 1. Test Environment

- OS: Windows 10/11
- Java: 21
- Start command (run from repository root):

```powershell
.\build_and_run
```

## 2. Pre-test Preparation

### 2.1 Recommended Test Accounts

- MO: `MO_A`
- TA: `TA_A`, `TA_B`

### 2.2 Data Setup

1. Close the application.
2. Ensure `codes/data/` exists.
3. It is recommended to back up old CSV files and run a clean test round.
4. Current data files:
   - `codes/data/users.csv`
   - `codes/data/jobs.csv`
   - `codes/data/applications.csv`

### 2.3 UI Label Baseline

- Login page title: `Login / Register`
- Login button: `Enter`
- Top navigation buttons: `Jobs` / `Post New Job` / `My Posted Jobs` / `Logout`
- Post page title: `Post New Job`
- Post button: `Post`
- Job list apply button: `Apply`
- Posted job action button: `Option`
- Applicant dialog action button: `Hire`

## 3. Manual Test Cases

### TC-LOGIN-01 New MO user enters the system

- Goal: A new MO user can enter through `Enter`.
- Precondition: `MO_A` does not exist in `users.csv`.
- Steps:
  1. Start the app and stay on `Login / Register`.
  2. Input `MO_A` in `Name`.
  3. Select `MO` in `Role`.
  4. Click `Enter`.
- Expected Result:
  - User enters the main system view successfully.
  - Top navigation shows `Jobs`, `Post New Job`, `My Posted Jobs`, `Logout`.
  - `users.csv` contains one new record: `MO_A,MO`.

### TC-LOGIN-02 New TA user enters the system

- Goal: A new TA user can enter successfully.
- Precondition: `TA_A` does not exist in `users.csv`.
- Steps:
  1. Click `Logout` to return to login page.
  2. Input `TA_A` in `Name`, select `TA` in `Role`.
  3. Click `Enter`.
- Expected Result:
  - User enters the main system view successfully.
  - Top navigation shows `Jobs` and `Logout` only (TA has no post-job entry).
  - `users.csv` contains one new record: `TA_A,TA`.

### TC-LOGIN-03 Empty name validation

- Goal: Empty input is rejected.
- Precondition: On login page.
- Steps:
  1. Leave `Name` empty (or input only spaces).
  2. Select any role and click `Enter`.
- Expected Result:
  - Error dialog appears with `Name cannot be empty.`.
  - User cannot enter the system and no new user record is added.

### TC-LOGIN-04 Same name with different role is blocked

- Goal: Same name cannot be registered/entered under another role.
- Precondition: `TA_A` already exists with role `TA`.
- Steps:
  1. On login page, input `TA_A`.
  2. Change `Role` to `MO`.
  3. Click `Enter`.
- Expected Result:
  - Error dialog appears with `This name is already registered with another role.`.
  - Access is denied.

### TC-JOB-01 MO posts a job successfully

- Goal: MO can post a job with only the required field.
- Precondition: Logged in as `MO_A`.
- Steps:
  1. Click top nav `Post New Job`.
  2. Input `Job_V1_Test_1` in `Job Name`.
  3. Click `Post`.
- Expected Result:
  - Dialog shows `Job posted successfully.`.
  - View navigates to `My Posted Jobs`.
  - List shows `Job_V1_Test_1 | Status: OPEN`.
  - `jobs.csv` contains the new job record.

### TC-JOB-02 Empty job name validation

- Goal: Empty job name is rejected.
- Precondition: Logged in as `MO_A`, on `Post New Job` page.
- Steps:
  1. Leave `Job Name` empty (or spaces only).
  2. Click `Post`.
- Expected Result:
  - Error dialog appears with `Job name cannot be empty.`.
  - No new job record is added.

### TC-APPLY-01 TA applies to an open job

- Goal: TA can apply to an open job.
- Precondition:
  - `Job_V1_Test_1` is `OPEN`.
  - Logged in as `TA_A`.
- Steps:
  1. In `Jobs`, find `Job_V1_Test_1`.
  2. Click `Apply` on that row.
  3. In `Confirm Application`, click `Yes`.
- Expected Result:
  - Dialog shows `Application submitted.`.
  - `applications.csv` contains a new `TA_A` application with status `PENDING`.

### TC-APPLY-02 Duplicate apply by same TA is blocked

- Goal: Same user can only apply once to the same job.
- Precondition: `TA_A` has already applied to `Job_V1_Test_1`.
- Steps:
  1. Still as `TA_A`, click `Apply` on `Job_V1_Test_1` again.
  2. In confirmation dialog, click `Yes`.
- Expected Result:
  - Error dialog appears with `You have already applied for this job.`.
  - No duplicate application record is added.

### TC-APPLY-03 Second TA can apply before hire

- Goal: Multiple applicants are allowed before hiring.
- Precondition:
  - `Job_V1_Test_1` is still `OPEN`.
  - `TA_A` has already applied.
- Steps:
  1. `Logout` and login as `TA_B` (create account first if needed).
  2. In `Jobs`, click `Apply` for `Job_V1_Test_1` and confirm `Yes`.
- Expected Result:
  - Apply succeeds.
  - `applications.csv` for this job includes at least `TA_A` and `TA_B` as `PENDING`.

### TC-HIRE-01 MO reviews applicants and hires one

- Goal: MO can hire one applicant and close the job.
- Precondition:
  - `Job_V1_Test_1` posted by `MO_A` has at least two applicants.
  - Logged in as `MO_A`.
- Steps:
  1. Click `My Posted Jobs`.
  2. On row `Job_V1_Test_1 | Status: OPEN`, click `Option`.
  3. In dialog `Applicants - Job_V1_Test_1`, click `Hire` on `TA_A`.
  4. In `Confirm Hire`, click `Yes`.
- Expected Result:
  - Dialog shows `Applicant hired. The job is now closed.`.
  - Dialog closes and view returns to `My Posted Jobs`.
  - Job status changes to `CLOSED`.
  - In `applications.csv`: hired applicant becomes `HIRED`; others become `REJECTED`.

### TC-HIRE-02 Closed job is no longer applicable

- Goal: No more application after job is closed.
- Precondition: `Job_V1_Test_1` is closed in TC-HIRE-01.
- Steps:
  1. `Logout` and login as any TA.
  2. Open `Jobs` page.
- Expected Result:
  - Closed job does not appear in `Jobs` list (current implementation shows only `OPEN` jobs).
  - Therefore there is no way to apply to that closed job.

### TC-PERSIST-01 Persistence after restart

- Goal: Data remains consistent after app restart.
- Precondition: Core flow above has been completed and data exists.
- Steps:
  1. Close the app.
  2. Run `.\build_and_run` again.
  3. Login as `MO_A` and check `My Posted Jobs` status of `Job_V1_Test_1`.
  4. Open `Option` and verify applicant statuses.
  5. Login as `TA_A` or `TA_B` and verify closed job is not shown in `Jobs`.
- Expected Result:
  - Users, jobs, applications, and hire statuses are consistent with pre-restart state.
  - No data rollback, data loss, or duplicate-write issue.

## 4. Execution Record Template

| Test Case ID | Result (Pass/Fail) | Tester | Date | Notes / Defect ID |
|---|---|---|---|---|
| TC-LOGIN-01 |  |  |  |  |
| TC-LOGIN-02 |  |  |  |  |
| TC-LOGIN-03 |  |  |  |  |
| TC-LOGIN-04 |  |  |  |  |
| TC-JOB-01 |  |  |  |  |
| TC-JOB-02 |  |  |  |  |
| TC-APPLY-01 |  |  |  |  |
| TC-APPLY-02 |  |  |  |  |
| TC-APPLY-03 |  |  |  |  |
| TC-HIRE-01 |  |  |  |  |
| TC-HIRE-02 |  |  |  |  |
| TC-PERSIST-01 |  |  |  |  |

## 5. Execution Notes

- This checklist is based on current V1 implementation behavior.
- If message text differs slightly, judge by functional behavior and log wording differences in Notes.
- Recommended attachments when reporting results:
  - One completed execution table
  - At least 2 key screenshots (before hire, after hire)
  - Reproduction steps for each defect found
