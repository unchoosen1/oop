//polymorphism
public class ScholarStudent extends Student {
    private double scholarshipDiscount;

    public ScholarStudent(String studentId, String fullName,
                          String course, int yearLevel,
                          double averageGrade,
                          double scholarshipDiscount) {
        super(studentId, fullName, course, yearLevel, averageGrade);
        setScholarshipDiscount(scholarshipDiscount);
    }

    public double getScholarshipDiscount() {
        return scholarshipDiscount;
    }

    public void setScholarshipDiscount(double discount) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException(
                "Discount must be between 0 and 100."
            );
        }
        scholarshipDiscount = discount;
    }

    @Override
    public String getPersonType() {
        return "Scholar Student (" + scholarshipDiscount + "% discount)";
    }
}