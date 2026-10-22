// inheritance
public class Student extends Person {
    private String studentId;
    private String course;
    private int yearLevel;
    private double averageGrade;

    public Student(String studentId, String fullName, String course,
                   int yearLevel, double averageGrade) {
        super(fullName);
        setStudentId(studentId);
        setCourse(course);
        setYearLevel(yearLevel);
        setAverageGrade(averageGrade);
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourse() {
        return course;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setStudentId(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID is required.");
        }
        this.studentId = studentId.trim();
    }

    public void setCourse(String course) {
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Course is required.");
        }
        this.course = course.trim();
    }

    public void setYearLevel(int yearLevel) {
        if (yearLevel < 1 || yearLevel > 4) {
            throw new IllegalArgumentException(
                "Year level must be from 1 to 4."
            );
        }
        this.yearLevel = yearLevel;
    }

    public void setAverageGrade(double averageGrade) {
        if (averageGrade < 0 || averageGrade > 100) {
            throw new IllegalArgumentException(
                "Grade must be between 0 and 100."
            );
        }
        this.averageGrade = averageGrade;
    }

    @Override
    public String getPersonType() {
        return "Regular Student";
    }

    public String getStanding() {
        return averageGrade >= 75 ? "Passed" : "Failed";
    }
}