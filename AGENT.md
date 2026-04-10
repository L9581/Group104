# Group104 Agent Guide

This file summarizes the repository conventions that should be followed when contributing to `Group104`.

## Project Context

- Project: `Group104 - TA Recruitment System (V2)`
- Tech stack:
  - Java `21`
  - Java Swing
  - CSV-based persistence

## Commit Message Rules

Use **Conventional Commits** for every commit message.

### Allowed commit types

- `feat`: Feature. New functions.
- `fix`: Fix the bugs.
- `docs`: Docs changes.
- `refactor`: Refactor the codes.
- `test`: Test codes.
- `chore`: Others.

### Commit message format

Use this format:

```text
<type>: <short summary>
```

### Examples

```text
feat: add application note submission flow
fix: correct macOS button rendering issue
docs: update README build instructions
refactor: simplify job card creation logic
test: add manual test checklist for MO workflow
chore: update .gitignore for IDEA IDE
```

## Branch and Merge Notes

- Keep `main` as the latest stable and demonstrable version.
- Work on personal branches.
- Merge in small steps instead of one large final merge.
- Only merge changes that:
  - have a clear purpose
  - do not break existing runnable behaviour
  - are understandable to the rest of the team

## Working Principle

- Each iteration should produce a working prototype or working software increment.
- Define a small runnable goal first.
- Review the prototype, turn findings into concrete tasks, and merge finished work back to `main` in small steps.
