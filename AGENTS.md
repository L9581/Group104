# Repository Guidelines

## Project Structure & Module Organization

The repository is intentionally small.

- `codes/src/`: Java source files for the TA Recruitment System.
- `documents/`: planning and project documents such as version scope, user stories, and workflow notes.
- `README.md`: team conventions and current collaboration workflow.

Keep implementation files under `codes/src/`. Keep planning, scope, and report materials under `documents/`. Do not store generated binaries or local data dumps in the repository root.

## Build, Test, and Development Commands

There is no fixed build script at the moment. Use simple Java CLI commands while the project is being rebuilt.

- `javac codes/src/*.java`
  Compiles all Java source files.
- `java -cp codes/src Main`
  Runs the application if the entry class is `Main`.

If the entry class changes, update both the command and `README.md`. Add new helper scripts only when the team agrees on a stable workflow.

## Coding Style & Naming Conventions

- Use 4 spaces for indentation.
- Prefer simple Java and standard library features over frameworks.
- Use `PascalCase` for classes, `camelCase` for methods and variables, and lowercase file names only for data files such as `jobs.csv`.
- Keep classes focused: UI, storage, and domain logic should not be mixed more than necessary.
- Write clear, short comments only where the code is not obvious.

## Testing Guidelines

Automated tests are not set up yet. When adding tests, place them in a future `codes/test/` directory and keep naming consistent, for example `JobStoreTest.java`.

Until a formal test suite exists:

- test the full user flow manually
- verify persistence by restarting the app
- record important test cases in `documents/`

## Commit & Pull Request Guidelines

Follow the existing Conventional Commit style:

- `feat`: new feature
- `fix`: bug fix
- `docs`: documentation changes
- `refactor`: code restructuring
- `test`: testing work
- `chore`: maintenance

Each contributor should work on a personal branch and merge in small steps. Pull requests should explain:

- what changed
- why it changed
- how it was checked

For UI changes, include screenshots when useful. Keep `main` runnable and suitable for demonstration.
