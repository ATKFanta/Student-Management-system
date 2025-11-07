package studentmgmt;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private final String id;
    private String name;
    // Use LinkedHashMap to keep insertion order for nicer printing
    private final Map<String, String> courses = new LinkedHashMap<>();

    public Student(String id, String name) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        this.id = id.trim();
        this.name = name.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        this.name = name.trim();
    }

    public Map<String, String> getCourses() { return courses; }

    /** Add or update a course with a (letter) grade, e.g., "A-", "B", "C+". */
    public void addOrUpdateCourse(String courseCode, String grade) {
        String c = sanitizeKey(courseCode);
        String g = sanitizeGrade(grade);
        courses.put(c, g);
    }

    /** Remove a course by code. Returns true if it existed. */
    public boolean removeCourse(String courseCode) {
        String c = sanitizeKey(courseCode);
        return courses.remove(c) != null;
    }

    /** Compute GPA on a standard 4.0 scale with +/- handling. Returns null if no courses. */
    public Double computeGPA() {
        if (courses.isEmpty()) return null;
        double sum = 0.0;
        int count = 0;
        for (String g : courses.values()) {
            Double pts = letterToPoints(g);
            if (pts != null) {
                sum += pts;
                count++;
            }
        }
        if (count == 0) return null;
        return sum / count;
    }

    private static String sanitizeKey(String s) {
        if (s == null) throw new IllegalArgumentException("value required");
        String t = s.trim();
        if (t.isEmpty()) throw new IllegalArgumentException("value required");
        return t.toUpperCase();
    }

    private static String sanitizeGrade(String grade) {
        if (grade == null) throw new IllegalArgumentException("grade required");
        String g = grade.trim().toUpperCase();
        if (g.isEmpty()) throw new IllegalArgumentException("grade required");
        // allow I, W, P/NP too; they won't impact GPA
        return g;
    }

    /** Typical US 4.0 scale with +/-; unsupported letters return null (excluded from GPA). */
    public static Double letterToPoints(String g) {
        if (g == null) return null;
        switch (g.toUpperCase()) {
            case "A+": return 4.0; // often capped at 4.0
            case "A":  return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B":  return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C":  return 2.0;
            case "C-": return 1.7;
            case "D+": return 1.3;
            case "D":  return 1.0;
            case "D-": return 0.7;
            case "F":  return 0.0;
            default:   return null; // P/NP, I, W, etc.
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" — ").append(name);
        Double gpa = computeGPA();
        if (gpa != null) {
            sb.append(String.format(" (GPA: %.2f)", gpa));
        }
        if (courses.isEmpty()) {
            sb.append("\n  No courses yet.");
        } else {
            sb.append("\n  Courses:");
            for (Map.Entry<String, String> e : courses.entrySet()) {
                sb.append("\n    ").append(e.getKey()).append(" : ").append(e.getValue());
            }
        }
        return sb.toString();
    }
}