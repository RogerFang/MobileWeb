package edu.whu.irlab;

import edu.whu.irlab.mobile.Train;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roger on 2016/7/1.
 */
public class MainTest {

    @Test
    public void testMobile(){
        List<String> files = new ArrayList<>();
        files.add("201408.txt");
        files.add("201409.txt");

        Train train = new Train("cmlp", files, "/path/to/model");
        train.doTrain(true);
    }
}
