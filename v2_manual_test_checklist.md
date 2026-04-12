# V2 Manual Test Checklist

> **Document Purpose :**
> This checklist is designed to validate the new features introduced in the V2 Refinement stage. 
> Unlike V1, which focused on the basic loop, V2 testing focuses on state management, UI robustness, and cross-platform compatibility.

## 1. Test Environment 

- **OS**: Windows 10/11, macOS, Linux
- **Java**: 21
- **Start commands** (Run from repository root):
  - Windows: `.\build_and_run`
  - macOS: `./build_and_run_mac.sh`
  - Linux: `./build_and_run_linux.sh`

> **Note on Cross-Platform Testing**: 
> Testers should ideally verify the UI rendering (specifically button layouts) on both Windows and macOS, as previous rendering bugs (Issue #15) were OS-specific.

## 2. Pre-test Preparation 

### 2.1 Recommended Test Accounts
- **MO**: `MO_V2`
- **TA**: `TA_V2_A`, `TA_V2_B`

### 2.2 Data Initialization
> To ensure test isolation, it is highly recommended to start with a clean data state.
1. Close the application.
2. If `RuntimeDataResetService` is available in your test build, trigger it to clear existing mock data.
3. Otherwise, manually clear the contents (keeping the headers) of the CSV files in `codes/data/`.

---

## 3. Manual Test Cases 

### TC-V2-APP-01: TA applies with a valid note 
- **Goal**: Verify that the newly added application note feature saves text correctly.
- **Precondition**: Logged in as `TA_V2_A`. A job `V2_Test_Job` is `OPEN`.
- **Steps**:
  1. Go to `Discover Jobs`.
  2. Click `Apply Now` for `V2_Test_Job`.
  3. In the dialog, type: "I have 3 years of Python experience." in the text area.
  4. Click `Apply` and confirm.
- **Expected Result**:
  - Success dialog appears with "OK" button (verifying global English locale).
  - The application is saved, and the note is persisted in `applications.csv`.

### TC-V2-APP-02: TA applies with empty note validation 
- **Goal**: Ensure the system handles empty note submissions gracefully and displays the fallback text.
- **Precondition**: Logged in as `TA_V2_B`.
- **Steps**:
  1. Click `Apply Now` for an open job.
  2. Leave the note text area completely empty.
  3. Submit the application.
  4. Switch to MO account and open `Review Application` for `TA_V2_B`.
- **Expected Result**:
  - The application is accepted by the system.
  - The MO review dialog displays the unified fallback text: "No application text provided" instead of a blank space or a dash (`-`).

### TC-V2-TRACK-01: TA checks "My Applications" panel 
- **Goal**: Verify that TAs can track their application lifecycle.
- **Precondition**: `TA_V2_A` has submitted an application for `V2_Test_Job`.
- **Steps**:
  1. Logged in as `TA_V2_A`.
  2. Navigate to `My Applications` panel.
- **Expected Result**:
  - The list displays `V2_Test_Job`.
  - The Status label correctly shows `Pending` in grey/neutral color.
  - The personal note submitted in TC-V2-APP-01 is displayed in the card.

### TC-V2-DECISION-01: MO rejects an applicant
- **Goal**: Verify the MO's ability to explicitly reject a candidate without closing the job.
- **Precondition**: Logged in as `MO_V2`. `TA_V2_B` is in the applicant list for `V2_Test_Job`.
- **Steps**:
  1. Go to `My Posted Jobs` -> Click `View Applicants` for `V2_Test_Job`.
  2. Click `Review` for `TA_V2_B`.
  3. Read the application note, then click `Reject`.
- **Expected Result**:
  - Dialog confirms rejection.
  - `TA_V2_B` status changes to `Rejected`.
  - `V2_Test_Job` remains `OPEN`.

### TC-V2-DECISION-02: MO accepts an applicant and job auto-closes
- **Goal**: Verify the core V2 workflow automation: accepting one candidate closes the job and rejects others.
- **Precondition**: Logged in as `MO_V2`. `TA_V2_A` is in `Pending` status for `V2_Test_Job`.
- **Steps**:
  1. In the `ApplicantsDialog` for `V2_Test_Job`, click `Review` for `TA_V2_A`.
  2. Click `Accept`.
- **Expected Result**:
  - Success message: "Application accepted. This job is now closed."
  - The job status immediately changes to `CLOSED`.
  - Check `applications.csv` or TA view: `TA_V2_A` status is `Accepted`.
  - **Crucial check**: Any other `Pending` applicants for this job are automatically set to `Rejected`.

### TC-V2-LOCALE-01: Global English Locale consistency
- **Goal**: Ensure no system-generated Chinese characters appear on non-English OS environments (Issue #16).
- **Precondition**: Test on a machine with Chinese OS language settings.
- **Steps**:
  1. Trigger any `JOptionPane` dialog (e.g., login with an empty name).
  2. Observe the confirm/cancel buttons.
- **Expected Result**:
  - The buttons display strictly as `OK`, `Yes`, `No`, or `Cancel` (No "确定" or "取消").

---

## 4. Execution Record Template 

| Test Case ID | Result (Pass/Fail) | Tester | Date | Notes / Defect ID |
|---|---|---|---|---|
| TC-V2-APP-01 | | | | |
| TC-V2-APP-02 | | | | |
| TC-V2-TRACK-01 | | | | |
| TC-V2-DECISION-01| | | | |
| TC-V2-DECISION-02| | | | |
| TC-V2-LOCALE-01 | | | | |

## 5. Execution Notes
> - V2 introduces dynamic component sizing (FlowLayout). Testers should resize the window during execution to ensure buttons are not truncated.
> - Ensure you are running Java 21; earlier versions might throw `UnsupportedClassVersionError`.
