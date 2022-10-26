package coms309.assignments;

import coms309.database.dataobjects.Assignment;
import coms309.database.dataobjects.Grade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaAssignment {

    @PostMapping("/assignment/{id}/submit")
    public @ResponseBody Grade gradeAssignment(@RequestBody Assignment a){
        return null;
    }

}
