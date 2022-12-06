package coms309.controller.transcript;


import java.util.List;

/**
 * A transcript for a user that contains the user's courses, grades, and assignments.
 */
public class Transcript {

    /**
     * Students name.
     */
    private String studentName;

    /**
     * List of courses and their assignments.
     */
    private List<CourseAssignments> courseList;

    /**
     * Constructor.
     *
     * @param studentName student's name
     * @param courseList  list of courses and their assignments
     */
    public Transcript(String studentName, List<CourseAssignments> courseList) {
        this.studentName = studentName;
        this.courseList = courseList;
    }

    /**
     * Get student's name.
     *
     * @return student's name
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Set student's name.
     *
     * @param studentName students name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Get list of courses and their assignments.
     *
     * @return list of courses and their assignments
     */
    public List<CourseAssignments> getCourseList() {
        return courseList;
    }

    /**
     * Set list of courses and their assignments.
     *
     * @param courseList list of courses and their assignments
     */
    public void setCourseList(List<CourseAssignments> courseList) {
        this.courseList = courseList;
    }

    /**
     * Inner class that houses a users grade for an assignment.
     */
    public static class AssignmentGrade {

        /**
         * Name of assignment.
         */
        private String assignmentName;

        /**
         * User's grade for the assignment.
         */
        private double assignmentGrade;

        /**
         * Constructor.
         *
         * @param assignmentName  name of assignment
         * @param assignmentGrade user's grade for the assignment
         */
        public AssignmentGrade(String assignmentName, double assignmentGrade) {
            this.assignmentName = assignmentName;
            this.assignmentGrade = assignmentGrade;
        }

        /**
         * Get assignment name.
         *
         * @return assignment name
         */
        public String getAssignmentName() {
            return assignmentName;
        }

        /**
         * Set assignment name.
         *
         * @param assignmentName assignment name
         */
        public void setAssignmentName(String assignmentName) {
            this.assignmentName = assignmentName;
        }

        /**
         * Get assignment grade.
         *
         * @return assignment grade
         */
        public double getAssignmentGrade() {
            return assignmentGrade;
        }

        /**
         * Set assignment grade.
         *
         * @param assignmentGrade assignment grade
         */
        public void setAssignmentGrade(double assignmentGrade) {
            this.assignmentGrade = assignmentGrade;
        }
    }

    /**
     * Inner class that houses a course and all of its assignments.
     */
    public static class CourseAssignments {

        /**
         * Name of course.
         */
        private String courseName;

        /**
         * List of assignments and the students grades.
         */
        private List<AssignmentGrade> assignmentList;

        /**
         * Constructor.
         *
         * @param courseName     name of course
         * @param assignmentList
         */
        public CourseAssignments(String courseName, List<AssignmentGrade> assignmentList) {
            this.courseName = courseName;
            this.assignmentList = assignmentList;
        }

        /**
         * Get course name.
         *
         * @return course name
         */
        public String getCourseName() {
            return courseName;
        }

        /**
         * Set course name.
         *
         * @param courseName course name
         */
        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        /**
         * Get list of assignments and the students grades.
         *
         * @return
         */
        public List<AssignmentGrade> getAssignmentList() {
            return assignmentList;
        }

        /**
         * Set list of assignments and the students grades.
         *
         * @param assignmentList list of assignments and the students grades
         */
        public void setAssignmentList(List<AssignmentGrade> assignmentList) {
            this.assignmentList = assignmentList;
        }
    }

}
