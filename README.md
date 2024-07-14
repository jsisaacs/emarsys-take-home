# Due Date Calculator

## Description

This project implements a `DueDateCalculator` that calculates the due date for tasks based on a submit date and a specified turnaround time in working hours. The calculator takes into account the following constraints:
- **Working Hours:** 9 AM to 5 PM
- **Working Days:** Monday to Friday
- **Timezone:** Assumes input date-times are in the UTC timezone

## Approach

### Continuous Integration
- Configured continuous integration using GitHub Actions to ensure that all tests are run automatically on every push and pull request.
- Ensured that the build passes and all tests are green before merging any changes to the main branch.

### Branching Strategy
- Followed a feature-branch workflow where each new feature or bug fix is developed in a separate branch.
- Created a branch `feature/due-date-calculator` for implementing the due date calculator feature.
- Created a branch `ci-setup` for implementing the continuous integration pipeline.
- Merged changes to the main branch only successful CI checks.

### Test-Driven Development (TDD)
- Developed the `DueDateCalculator` implementation using TDD principles.
- Wrote unit tests to cover various scenarios and edge cases before writing the implementation code.
- Ensured that all tests passed before refactoring or adding new functionality.

### Clean Code Principles
- Followed Clean Code principles as advocated by Uncle Bob to ensure the codebase is readable, maintainable, and extensible.
- Used meaningful names for classes, methods, and variables.
- Kept methods small and focused on a single responsibility.
- Added comments only where necessary to explain the "why" behind complex logic.
