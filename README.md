# Student-Management-system
Student Management Project with ability to add/view/rename/delete students and add/change/remove classes with simple GPA. Console based

Small, no-deps Java console app that manages students in memory using a map keyed by student ID.  
Supports:
- Add / view / rename / delete students
- Add, change, and remove classes (per student)
- Simple GPA calculation from letter grades (A, A-, B+, ..., F). Non-GPA grades like P/NP/I/W are ignored in GPA.

> Note: This version is reconstructed from the original description (no persistence).

## Build & Run

```bash
cd src
javac studentmgmt/*.java
java studentmgmt.Main
```

- Requires Java 17+ (earlier may work too). No external libraries.
- Data is in-memory only for simplicity.

## Example

```
=== Student Management (Console) ===
1) Add student
2) View student (by ID)
3) Edit a student's classes/grades
4) Delete student
5) List all students
6) Rename student
0) Exit
```
