package coms309.assignments;

import com.sun.xml.bind.v2.TODO;
import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Grade;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

@RestController
public class JavaAssignment {

    /*
    This file is a "God Class" and will be broken up after it is merged to main
     */

    public static String combineText(String boiler, String input) {
        Scanner s = new Scanner(boiler);
        StringBuilder sb = new StringBuilder();
        while (s.hasNextLine()) {
            String t = s.nextLine();
            if (t.equals("// INPUT CODE HERE")) {
                sb.append(input).append("\n");
            } else {
                sb.append(t).append("\n");
            }
        }
        return sb.toString();
    }

    public static boolean generateFile(String text) {
        File f = new File("Assignment.java");
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(text);
            fw.close();
            f.createNewFile();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean compileAndGrade(String output) { // Reading output does not work TODO
        String c = "javac Assignment.Java";
        Process p;
        try {
            p = Runtime.getRuntime().exec(c);
        } catch (IOException e) {
            return false;
        }
        c = "java Assignment";
        try {
            p = Runtime.getRuntime().exec(c);
        } catch (IOException e) {
            return false;
        }
        Scanner s = new Scanner(new InputStreamReader(p.getInputStream()));
        StringBuilder sb = new StringBuilder();
        while (s.hasNextLine()) {
            sb.append(s.nextLine()).append("\n");
        }
        return sb.toString().equals(output);
    }

    @PutMapping("/assignment/java/{id}")
    public @ResponseBody Grade submitAssignment(@PathVariable long id) {
        return null;
    }

}
