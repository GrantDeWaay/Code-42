package coms309.controller.generator;

import java.util.Random;

public class LongGen {

    public static Random r;

    public static long generateId() {
        return r.nextLong(1024);
    }

}
