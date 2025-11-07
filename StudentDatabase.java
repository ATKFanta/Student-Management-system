package studentmgmt;

import java.util.*;

public class StudentDatabase {
    private final Map<String, Student> byId = new LinkedHashMap<>();

    public boolean hasStudent(String id) {
        return byId.containsKey(sanitizeId(id));
    }

    public Collection<Student> allStudents() {
        return Collections.unmodifiableCollection(byId.values());
    }

    public Student getById(String id) {
        return byId.get(sanitizeId(id));
    }

    public Student addStudent(String id, String name) {
        String sid = sanitizeId(id);
        if (byId.containsKey(sid)) {
            throw new IllegalArgumentException("Student with ID already exists: " + sid);
        }
        Student s = new Student(sid, name);
        byId.put(sid, s);
        return s;
    }

    public boolean deleteStudent(String id) {
        return byId.remove(sanitizeId(id)) != null;
    }

    public List<Student> searchByName(String namePart) {
        String q = namePart == null ? "" : namePart.trim().toLowerCase();
        if (q.isEmpty()) return List.of();
        List<Student> out = new ArrayList<>();
        for (Student s : byId.values()) {
            if (s.getName().toLowerCase().contains(q)) {
                out.add(s);
            }
        }
        return out;
    }

    private static String sanitizeId(String id) {
        if (id == null) throw new IllegalArgumentException("id required");
        String t = id.trim().toUpperCase();
        if (t.isEmpty()) throw new IllegalArgumentException("id required");
        return t;
    }
}