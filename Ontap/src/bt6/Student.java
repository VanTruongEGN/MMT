package bt6;

public class Student {
    public int id;
    public String name;
    public int bYear;
    public double grade;

    public Student(int id, String name, int bYear) {
        this.id = id;
        this.name = name;
        this.bYear = bYear;
    }
    
    public Student(int id, String name, int bYear, double grade) {
        this.id = id;
        this.name = name;
        this.bYear = bYear;
        this.grade = grade;
    }
    public Student() {};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getbYear() {
        return bYear;
    }

    public void setbYear(int bYear) {
        this.bYear = bYear;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + bYear + "\t" + grade;
    }
}
