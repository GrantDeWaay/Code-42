package coms309.controller.transcript;


import java.util.List;

public class Transcript {

    String studentName;

    List<CourseAssignments> courseList;

    public Transcript() {

    }

    public Transcript(String studentName, List<CourseAssignments> courseList) {
        this.studentName = studentName;
        this.courseList = courseList;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<CourseAssignments> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseAssignments> courseList) {
        this.courseList = courseList;
    }

    public static class AssignmentGrade {

        String assignmentName;

        double assignmentGrade;

        public AssignmentGrade(String assignmentName, double assignmentGrade) {
            this.assignmentName = assignmentName;
            this.assignmentGrade = assignmentGrade;
        }

        public String getAssignmentName() {
            return assignmentName;
        }

        public void setAssignmentName(String assignmentName) {
            this.assignmentName = assignmentName;
        }

        public double getAssignmentGrade() {
            return assignmentGrade;
        }

        public void setAssignmentGrade(double assignmentGrade) {
            this.assignmentGrade = assignmentGrade;
        }
    }

    public static class CourseAssignments {

        String courseName;

        List<AssignmentGrade> assignmentList;

        public CourseAssignments(String courseName, List<AssignmentGrade> assignmentList) {
            this.courseName = courseName;
            this.assignmentList = assignmentList;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public List<AssignmentGrade> getAssignmentList() {
            return assignmentList;
        }

        public void setAssignmentList(List<AssignmentGrade> assignmentList) {
            this.assignmentList = assignmentList;
        }
    }

}
