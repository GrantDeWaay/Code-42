package coms309;

import java.util.Calendar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import coms309.database.dataobjects.Course;
import coms309.database.repository.CourseRepository;
import coms309.database.dataobjects.User;
import coms309.database.repository.UserRepository;

@SpringBootApplication
public class Application {
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner testMapping(CourseRepository courseRepository, UserRepository userRepository) {
        return args -> {

            System.out.println("Started database map testing...");

            Course course1 = new Course(Long.valueOf(1), "CPR E 288", "Embedded Systems", "C,ASM", Calendar.getInstance().getTime());
            Course course2 = new Course(Long.valueOf(2), "CPR E 281", "Digital Logic", "Verilog", Calendar.getInstance().getTime());
            Course course3 = new Course(Long.valueOf(3), "COM S 227", "Object Oriented Programming", "Java", Calendar.getInstance().getTime());
            Course course4 = new Course(Long.valueOf(4), "COM S 228", "Data Structures and Algorithms", "Verilog", Calendar.getInstance().getTime());
            Course course5 = new Course(Long.valueOf(5), "CPR E 185", "Introduction to C", "C", Calendar.getInstance().getTime());

            courseRepository.save(course1);
            courseRepository.save(course2);
            courseRepository.save(course3);
            courseRepository.save(course4);
            courseRepository.save(course5);

            System.out.println("Courses saved...");
            System.out.println("List of courses:");
            for(Course c : courseRepository.findAll()) {
                System.out.println(c.getTitle());
            }

            User user1 = new User(Long.valueOf(1), "nastark", "Nathan", "Stark", "", "nastark@iastate.edu", "student");
            User user2 = new User(Long.valueOf(2), "nobrown", "Nolan", "Brown", "", "nobrown@iastate.edu", "student");
            User user3 = new User(Long.valueOf(3), "grdewaay", "Grant", "DeWaay", "", "grdewaay@iastate.edu", "student");
            User user4 = new User(Long.valueOf(4), "anbowen", "Andrew", "Bowen", "", "anbowen@iastate.edu", "student");

            user1.getCourses().add(course1);
            user1.getCourses().add(course2);
            user1.getCourses().add(course3);

            user2.getCourses().add(course1);
            user2.getCourses().add(course3);
            user2.getCourses().add(course5);

            user3.getCourses().add(course1);
            user3.getCourses().add(course2);
            user3.getCourses().add(course4);

            user4.getCourses().add(course3);
            user4.getCourses().add(course4);
            user4.getCourses().add(course5);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);

            System.out.println("Users saved...");
        };
    }

}