public class Student {
    private String id;
    private String name;
    private Module[] modules; // Array to store modules
    private static final int NUM_MODULES = 3; // Number of modules per student

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.modules = new Module[NUM_MODULES]; // Initialize the array with fixed size
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Module[] getModules() {
        return modules;
    }

    public void setModule(int index, Module module) {
        if (index >= 0 && index < modules.length) {
            modules[index] = module;
        } else {
            System.out.println("Invalid index for module set.");
        }
    }

    public double getAverageMarks() {
        int totalMarks = 0;
        int count = 0;
        for (Module module : modules) {
            if (module != null) {
                totalMarks += module.getMarks();
                count++;
            }
        }
        if (count > 0) {
            return totalMarks / (double) count;
        } else {
            return 0.0; // Return 0 if no modules are set
        }
    }

    public int getTotalMarks() {
        int totalMarks = 0;
        for (Module module : modules) {
            if (module != null) {
                totalMarks += module.getMarks();
            }
        }
        return totalMarks;
    }

    public String getGrade() {
        double average = getAverageMarks();
        if (average >= 80) {
            return "Distinction";
        } else if (average >= 70) {
            return "Merit";
        } else if (average >= 40) {
            return "Pass";
        } else {
            return "Fail";
        }
    }

    public String getDetailedReport() {
        StringBuilder report = new StringBuilder();
        report.append("Student ID: ").append(id)
                .append("\nStudent Name: ").append(name);
        for (int i = 0; i < modules.length; i++) {
            if (modules[i] != null) {
                report.append("\nModule ").append(i + 1).append(" marks: ").append(modules[i].getMarks());
            } else {
                report.append("\nModule ").append(i + 1).append(" marks: Not set");
            }
        }
        report.append("\nTotal: ").append(getTotalMarks())
                .append("\nAverage: ").append(getAverageMarks())
                .append("\nGrade: ").append(getGrade())
                .append("\n");
        return report.toString();
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name + ", Average Marks: " + getAverageMarks() + ", Grade: " + getGrade();
    }
}
