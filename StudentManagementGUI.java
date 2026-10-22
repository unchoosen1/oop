import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentManagementGUI extends JFrame {

    private final StudentManager manager = new StudentManager();

    private final JTextField idField = new JTextField(12);
    private final JTextField nameField = new JTextField(15);
    private final JTextField gradeField = new JTextField(6);
    private final JTextField searchField = new JTextField(12);

    private final JComboBox<String> courseBox =
        new JComboBox<>(new String[]{
            "BSIT", "BSCS", "BSHM", "BSTM",
            "BEED", "BSED", "Other"
        });

    private final JComboBox<Integer> yearBox =
        new JComboBox<>(new Integer[]{1, 2, 3, 4});

    private final DefaultTableModel model =
        new DefaultTableModel(
            new String[]{
                "Student ID", "Full Name", "Course",
                "Year", "Average", "Status", "Type"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

    private final JTable table = new JTable(model);
    private final JLabel summary =
        new JLabel("Total: 0 | Passed: 0 | Failed: 0");

    private String selectedOriginalId = null;

    public StudentManagementGUI() {
        setTitle("Student Management System");
        setSize(1000, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel(
            "STUDENT MANAGEMENT SYSTEM",
            SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 5));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setBorder(
            BorderFactory.createEmptyBorder(0, 12, 12, 12)
        );

        center.add(createForm(), BorderLayout.NORTH);

        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedStudent();
            }
        });

        center.add(new JScrollPane(table), BorderLayout.CENTER);
        center.add(createBottomPanel(), BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);
        refreshTable(manager.getAllStudents());
    }

    private JPanel createForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(
            BorderFactory.createTitledBorder("Student Information")
        );

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 6, 5, 6);
        c.anchor = GridBagConstraints.WEST;

        addField(panel, c, 0, 0, "Student ID:", idField);
        addField(panel, c, 2, 0, "Full Name:", nameField);
        addField(panel, c, 0, 1, "Course:", courseBox);
        addField(panel, c, 2, 1, "Year Level:", yearBox);
        addField(panel, c, 0, 2, "Average Grade:", gradeField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton add = new JButton("Add Student");
        JButton update = new JButton("Update Selected");
        JButton delete = new JButton("Delete Selected");
        JButton clear = new JButton("Clear Form");

        add.addActionListener(e -> addStudent());
        update.addActionListener(e -> updateStudent());
        delete.addActionListener(e -> deleteStudent());
        clear.addActionListener(e -> clearForm());

        buttons.add(add);
        buttons.add(update);
        buttons.add(delete);
        buttons.add(clear);

        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttons, c);

        return panel;
    }

    private void addField(JPanel panel, GridBagConstraints c,
                          int x, int y, String label, Component field) {
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.gridx = x;
        c.gridy = y;
        panel.add(new JLabel(label), c);

        c.gridx = x + 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, c);
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton search = new JButton("Search");
        JButton showAll = new JButton("Show All");
        JButton average = new JButton("Calculate Class Average");

        search.addActionListener(e -> searchStudents());
        showAll.addActionListener(
            e -> refreshTable(manager.getAllStudents())
        );
        average.addActionListener(e -> calculateClassAverage());

        searchPanel.add(new JLabel("Search ID or Name:"));
        searchPanel.add(searchField);
        searchPanel.add(search);
        searchPanel.add(showAll);
        searchPanel.add(average);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(summary, BorderLayout.SOUTH);

        return panel;
    }

    private Student readForm() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || gradeText.isEmpty()) {
            throw new IllegalArgumentException(
                "Please fill in Student ID, Full Name, and Average Grade."
            );
        }

        double grade;

        try {
            grade = Double.parseDouble(gradeText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Average grade must be a valid number."
            );
        }

        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException(
                "Average grade must be between 0 and 100."
            );
        }

        return new Student(
            id,
            name,
            (String) courseBox.getSelectedItem(),
            (Integer) yearBox.getSelectedItem(),
            grade
        );
    }

    private void addStudent() {
        try {
            Student student = readForm();
            manager.addStudent(student);

            refreshTable(manager.getAllStudents());
            clearForm();

            message("Student added successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalArgumentException e) {
            message(e.getMessage(), "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        if (selectedOriginalId == null) {
            message("Select a student from the table first.",
                    "Notice", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Student updated = readForm();

            manager.updateStudent(selectedOriginalId, updated);
            refreshTable(manager.getAllStudents());
            clearForm();

            message("Student updated successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalArgumentException e) {
            message(e.getMessage(), "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        if (selectedOriginalId == null) {
            message("Select a student from the table first.",
                    "Notice", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete student "
                + selectedOriginalId + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            manager.deleteStudent(selectedOriginalId);
            refreshTable(manager.getAllStudents());
            clearForm();

            message("Student deleted successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void searchStudents() {
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            message("Enter a student ID or name to search.",
                    "Search", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Student> results = manager.search(query);
        refreshTable(results);

        if (results.isEmpty()) {
            message("No matching student found.",
                    "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void calculateClassAverage() {
        List<Student> students = manager.getAllStudents();

        if (students.isEmpty()) {
            message("Add students before calculating the average.",
                    "No Records", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double total = 0;

        for (Student student : students) {
            total += student.getAverageGrade();
        }

        double average = total / students.size();

        message(
            String.format(
                "Class Average: %.2f%nTotal Students: %d",
                average, students.size()
            ),
            "Class Average",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void loadSelectedStudent() {
        int row = table.getSelectedRow();

        if (row < 0) {
            return;
        }

        selectedOriginalId = model.getValueAt(row, 0).toString();
        Student student = manager.findById(selectedOriginalId);

        if (student == null) {
            return;
        }

        idField.setText(student.getStudentId());
        nameField.setText(student.getFullName());
        courseBox.setSelectedItem(student.getCourse());
        yearBox.setSelectedItem(student.getYearLevel());
        gradeField.setText(
            String.valueOf(student.getAverageGrade())
        );
    }

    private void refreshTable(List<Student> students) {
        model.setRowCount(0);

        for (Student student : students) {
            // Polymorphism: calls the object's overridden method.
            model.addRow(new Object[]{
                student.getStudentId(),
                student.getFullName(),
                student.getCourse(),
                student.getYearLevel(),
                String.format("%.2f", student.getAverageGrade()),
                student.getStanding(),
                student.getPersonType()
            });
        }

        summary.setText(
            "Total: " + manager.getTotalStudents()
            + " | Passed: " + manager.getPassingCount()
            + " | Failed: " + manager.getFailingCount()
            + " (passing grade: 75)"
        );
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        courseBox.setSelectedIndex(0);
        yearBox.setSelectedIndex(0);
        gradeField.setText("");

        table.clearSelection();
        selectedOriginalId = null;
        idField.requestFocus();
    }

    private void message(String text, String title, int type) {
        JOptionPane.showMessageDialog(this, text, title, type);
    }
}