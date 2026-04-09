# Git Workflow for Teammates

This document explains the basic Git workflow for our current project.

## 1. Current Rule

- Do not work directly on `main`.
- Do not push directly to `ZhuohangTIAN`.
- Each person should use their own personal branch.
- Current issues should be developed based on `ZhuohangTIAN`.
- Pull Requests should currently target `ZhuohangTIAN`, not `main`.

## 2. First Step: Claim an Issue

Before writing code or editing documents, choose one issue on GitHub and tell the team which one you are taking.

Examples:
- `#2 Polish the V1 panel layout`
- `#3 Write a V1 manual test checklist`
- `#4 Stress test the Jobs panel with many job entries`

## 3. Create Your Own Branch

Branch names should use your own name, for example:

- `WangXxx`
- `LiXxx`
- `ChenXxx`

If you are creating your branch for the first time:

```powershell
git fetch origin
git checkout -b YourName origin/ZhuohangTIAN
git push -u origin YourName
```

## 4. Update Your Branch Before Working

If your branch already exists, update it from `ZhuohangTIAN` before starting new work:

```powershell
git fetch origin
git checkout YourName
git merge origin/ZhuohangTIAN
```

## 5. Do Your Work on Your Own Branch

Make your changes only on your own branch.

Then save and commit:

```powershell
git add .
git commit -m "docs: update V1 manual test checklist"
git push origin YourName
```

Use clear commit messages. Examples:

- `docs: add V1 manual test checklist`
- `fix: improve Jobs panel layout`
- `test: stress test Jobs panel with many entries`

## 6. Open a Pull Request

After pushing your branch, open a Pull Request on GitHub:

- Source branch: `YourName`
- Target branch: `ZhuohangTIAN`

In the Pull Request description, write:

- what you changed
- why you changed it
- how you checked it
- which issue it relates to, for example `Related to #3`

## 7. Important Reminder

- Do not merge your own Pull Request unless asked.
- Do not push directly to `main`.
- Do not push directly to `ZhuohangTIAN`.
- If there is a conflict or error, stop and ask before trying random commands.

## 8. Goal

This workflow helps us show:

- each member has their own visible branch
- each member has their own commits
- each task can be linked to an issue and a Pull Request
- our work can be reviewed before being merged
