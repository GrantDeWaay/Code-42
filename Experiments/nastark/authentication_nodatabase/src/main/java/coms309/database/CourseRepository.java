package coms309.database;

import java.util.HashMap;
import java.util.Optional;

public class CourseRepository {
    
    private HashMap<Integer, Course> database;

    public CourseRepository() {
        database = new HashMap<>();

        Course sampleCourse1 = new Course("E E 201", 201, "B+");
        Course sampleCourse2 = new Course("COM S 309", 309, "A-");
        Course sampleCourse3 = new Course("CPR E 281", 281, "A");

        database.put(sampleCourse1.getCatalogId(), sampleCourse1);
        database.put(sampleCourse2.getCatalogId(), sampleCourse2);
        database.put(sampleCourse3.getCatalogId(), sampleCourse3);
    }

    public Iterable<Course> findAll() {
        return database.values();
    }

    public Optional<Course> findById(Integer id) {
        if(!database.containsKey(id)) return Optional.empty();

        return Optional.of(database.get(id));
    }

    public boolean add(Course course) {
        if(database.containsKey(course.getCatalogId())) return false;

        database.put(course.getCatalogId(), course);

        return true;
    }

    public boolean remove(Integer id) {
        return database.remove(id) != null;
    }

    public boolean updateName(Integer id, String name) {
        if(!database.containsKey(id)) return false;

        database.get(id).setName(name);

        return true;
    }

    public boolean updateGrade(Integer id, String grade) {
        if(!database.containsKey(id)) return false;

        database.get(id).setName(grade);

        return true;
    }

}
