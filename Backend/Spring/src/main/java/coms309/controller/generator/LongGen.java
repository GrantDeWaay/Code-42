package coms309.controller.generator;

import java.util.Random;

public class LongGen {

    public static Random r = new Random();

    public static long generateId() {
        return r.nextLong();
    }

}
