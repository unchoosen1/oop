//abstract class
public abstract class Person {
    private String fullName;

    public Person(String fullName) {
        setFullName(fullName);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required.");
        }
        this.fullName = fullName.trim();
    }

    public abstract String getPersonType();
}