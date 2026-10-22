import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentManager {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student is required.");
        }

        if (findById(student.getStudentId()) != null) {
            throw new IllegalArgumentException(
                "Student ID already exists."
            );
        }

        students.add(student);
    }

    public List<Student> getAllStudents() {
        return Collections.unmodifiableList(
            new ArrayList<>(students)
        );
    }

    public Student findById(String id) {
        if (id == null) {
            return null;
        }

        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(id.trim())) {
                return student;
            }
        }

        return null;
    }

    public List<Student> search(String query) {
        List<Student> results = new ArrayList<>();
        String text = query == null ? "" : query.trim().toLowerCase();

        for (Student student : students) {
            if (student.getStudentId().toLowerCase().contains(text)
                    || student.getFullName().toLowerCase().contains(text)) {
                results.add(student);
            }
        }

        return results;
    }

    public void updateStudent(String originalId, Student updated) {
        Student existing = findById(originalId);

        if (existing == null) {
            throw new IllegalArgumentException(
                "Student record was not found."
            );
        }

        Student duplicate = findById(updated.getStudentId());

        if (duplicate != null && duplicate != existing) {
            throw new IllegalArgumentException(
                "Another student already uses this ID."
            );
        }

        existing.setStudentId(updated.getStudentId());
        existing.setFullName(updated.getFullName());
        existing.setCourse(updated.getCourse());
        existing.setYearLevel(updated.getYearLevel());
        existing.setAverageGrade(updated.getAverageGrade());
    }

    public boolean deleteStudent(String id) {
        Student student = findById(id);
        return student != null && students.remove(student);
    }

    public int getTotalStudents() {
        return students.size();
    }

    public int getPassingCount() {
        int count = 0;

        for (Student student : students) {
            if (student.getAverageGrade() >= 75) {
                count++;
            }
        }

        return count;
    }

    public int getFailingCount() {
        return getTotalStudents() - getPassingCount();
    }
}