package coms309.database;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashMap;
import java.util.Optional;

public class TestRepository {
    
    private HashMap<Integer, Course> courseDatabase;

    private HashMap<Integer, Student> studentDatabase;

    public TestRepository() {
        courseDatabase = new HashMap<>();

        studentDatabase = new HashMap<>();

        Student student1 = new Student(1, "Bill", 11);
        Student student2 = new Student(2, "Steve", 10);
        Student student3 = new Student(3, "Bill", 12);
        Student student4 = new Student(4, "Steve", 9);
        Student student5 = new Student(5, "Bill", 11);
        Student student6 = new Student(6, "Steve", 10);
        Student student7 = new Student(7, "Bill", 9);
        Student student8 = new Student(8, "Steve", 12);

        studentDatabase.put(student1.getId(), student1);
        studentDatabase.put(student2.getId(), student2);
        studentDatabase.put(student3.getId(), student3);
        studentDatabase.put(student4.getId(), student4);
        studentDatabase.put(student5.getId(), student5);
        studentDatabase.put(student6.getId(), student6);
        studentDatabase.put(student7.getId(), student7);
        studentDatabase.put(student8.getId(), student8);

        Course sampleCourse1 = new Course("E E 201", 201);
        sampleCourse1.addStudent(student1);
        sampleCourse1.addStudent(student2);
        sampleCourse1.addStudent(student3);
        sampleCourse1.addStudent(student4);

        Course sampleCourse2 = new Course("COM S 309", 309);
        sampleCourse2.addStudent(student5);
        sampleCourse2.addStudent(student6);
        sampleCourse2.addStudent(student7);
        sampleCourse2.addStudent(student8);

        Course sampleCourse3 = new Course("CPR E 281", 281);
        sampleCourse3.addStudent(student1);
        sampleCourse3.addStudent(student3);
        sampleCourse3.addStudent(student5);
        sampleCourse3.addStudent(student7);

        courseDatabase.put(sampleCourse1.getCatalogId(), sampleCourse1);
        courseDatabase.put(sampleCourse2.getCatalogId(), sampleCourse2);
        courseDatabase.put(sampleCourse3.getCatalogId(), sampleCourse3);
    }

    public Iterable<Course> findAllCourses() {
        return courseDatabase.values();
    }

    public Optional<Course> findCourseById(Integer id) {
        if(!courseDatabase.containsKey(id)) return Optional.empty();

        return Optional.of(courseDatabase.get(id));
    }

    public boolean addCourse(Course course) {
        if(courseDatabase.containsKey(course.getCatalogId())) return false;

        courseDatabase.put(course.getCatalogId(), course);

        return true;
    }

    public boolean removeCourse(Integer id) {
        if(!courseDatabase.containsKey(id)) return false;

        LinkedList<Student> students = courseDatabase.get(id).getStudents();

        for(Student s : students) {
            s.getEnrollment().remove(id);
        }

        courseDatabase.remove(id);

        return true;
    }

    public boolean updateCourseName(Integer id, String name) {
        if(!courseDatabase.containsKey(id)) return false;

        courseDatabase.get(id).setName(name);

        return true;
    }

    public Iterable<Student> findAllStudents() {
        return studentDatabase.values();
    }

    public Optional<Student> findStudentById(Integer id) {
        if(!studentDatabase.containsKey(id)) return Optional.empty();

        return Optional.of(studentDatabase.get(id));
    }

    public boolean addStudent(Student student) {
        if(studentDatabase.containsKey(student.getId())) return false;

        studentDatabase.put(student.getId(), student);

        return true;
    }

    public boolean removeStudent(Integer id) {
        if(!studentDatabase.containsKey(id)) return false;

        LinkedList<Integer> courseIds = studentDatabase.get(id).getEnrollment();

        for(Integer i : courseIds) {
            LinkedList<Student> students = courseDatabase.get(i).getStudents();
            ListIterator<Student> iter = students.listIterator();
            while(iter.hasNext()) {
                Student s = iter.next();
                if(s.getId() == id) {
                    iter.remove();
                }
            }
        }

        studentDatabase.remove(id);

        return true;
    }

    public boolean updateStudentName(Integer id, String name) {
        if(!studentDatabase.containsKey(id)) return false;

        studentDatabase.get(id).setName(name);

        return true;
    }

    public boolean updateStudentGrade(Integer id, Integer grade) {
        if(!studentDatabase.containsKey(id)) return false;

        studentDatabase.get(id).setGrade(grade);

        return true;
    }

}
