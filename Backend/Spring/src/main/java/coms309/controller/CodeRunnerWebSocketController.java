package coms309.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.*;

import coms309.api.dataobjects.ApiCodeSubmission;
import coms309.coderunner.AssignmentUnitTestResult;
import coms309.coderunner.CodeRunner;
import coms309.coderunner.CodeRunnerFactory;
import coms309.coderunner.TempFileManager;
import coms309.controller.token.UserTokens;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.AssignmentFile;
import coms309.database.dataobjects.AssignmentUnitTest;
import coms309.database.dataobjects.Grade;
import coms309.database.dataobjects.User;
import coms309.database.services.AssignmentService;
import coms309.database.services.GradeService;
import coms309.database.services.UserService;

@Controller
@ServerEndpoint(value = "/run/assignment/{assignmentId}/{token}")
public class CodeRunnerWebSocketController {

    private static AssignmentService as;

    private static GradeService gs;

    private static UserService us;

    private Long assignmentId;

    private String token;

    @Autowired
    public void setAssignmentService(AssignmentService assignmentService) {
        as = assignmentService;
    }

    @Autowired
    public void setGradeService(GradeService gradeService) {
        gs = gradeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        us = userService;
    }
    
    @OnOpen
    public void onOpen(Session session, @PathParam("assignmentId") long assignmentId, @PathParam("token") String token) {
        this.assignmentId = assignmentId;
        this.token = token;
    }

    @OnClose
    public void onClose(Session session) {

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            sendMessage(session, throwable.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        Gson g = new Gson();
        ApiCodeSubmission codeSubmission = g.fromJson(message, ApiCodeSubmission.class);

        sendMessage(session, "json parsed");

        if (!UserTokens.isStudent(token)) {
            AssignmentUnitTestResult result = new AssignmentUnitTestResult(null, "", "Token is not a student", false);
            sendMessage(session, g.toJson(result));
            return;
        }

        sendMessage(session, "token checked");

        Long studentId = UserTokens.getID(token);

        Optional<User> u = us.findById(studentId);

        Optional<Assignment> a = as.findById(assignmentId);

        sendMessage(session, "database accessed");

        if (!a.isPresent()) {
            AssignmentUnitTestResult result = new AssignmentUnitTestResult(null, "", "Assignment not found", false);
            sendMessage(session, g.toJson(result));
            return;
        }

        AssignmentFile af = a.get().getAssignmentFile();

        // Hibernate.initialize(a.get().getUnitTests());
        Set<AssignmentUnitTest> unitTests = a.get().getUnitTests();

        sendMessage(session, "unit tests retrieved");

        if (af == null) af = new AssignmentFile(); // initialize to empty

        // TODO move this stuff to a service of some kind??
        TempFileManager tempFileManager = new TempFileManager("/home/gitlab-runner/tempfiles/users/", studentId, a.get().getId());
        tempFileManager.createAssignmentFolder();

        try {
            CodeRunnerFactory factory = new CodeRunnerFactory();
            CodeRunner runner = factory.createCodeRunner(af, codeSubmission, tempFileManager, unitTests);

            sendMessage(session, "code runner created");

            File codeFile = new File(tempFileManager.getAssignmentFolderPath() + "/" + codeSubmission.getName());

            FileWriter writer = new FileWriter(codeFile);

            writer.write(codeSubmission.getContents());

            writer.close();

            sendMessage(session, "file written");

            if (runner.isCompiledRunner()) {
                if (!runner.compile()) {
                    AssignmentUnitTestResult result = new AssignmentUnitTestResult(null, "", "Compilation failed", false);
                    sendMessage(session, g.toJson(result));
                    return;
                }
            }

            sendMessage(session, "code compiled");

            double passCount = 0.0d;

            for(int i = 0; i < unitTests.size(); i++) {
                AssignmentUnitTestResult result = runner.runNext();
                if(result.isPassed()) passCount += 1.0d;
                sendMessage(session, g.toJson(result));
            }

            sendMessage(session, "unit tests executed");

            double finalGrade = (passCount / (double) unitTests.size()) * 100.0d;

            Grade grade = gs.findByUserAndAssignment(studentId, assignmentId);

            // if a grade for this assignment doesn't exist, create it
            // otherwise update the grade with the new grade and set the update time to now
            if(grade == null) {
                grade = new Grade(finalGrade, Calendar.getInstance().getTime());
                grade.setAssignment(a.get());
                a.get().getGrades().add(grade);
                grade.setUser(u.get());
                u.get().getGrades().add(grade);

                gs.create(grade);
            } else {
                grade.setGrade(finalGrade);
                grade.setUpdateDate(Calendar.getInstance().getTime());

                gs.update(grade);
            }

            sendMessage(session, "grade submitted");

        } catch (Exception e) {
            e.printStackTrace();
            AssignmentUnitTestResult result = new AssignmentUnitTestResult(null, "", "Exception thrown: " + e.getMessage(), false);
            sendMessage(session, g.toJson(result));
        }

        sendMessage(session, "finished");

    }

    public void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

}
