package edu.whu.irlab;

import edu.whu.irlab.mobile.Train;
import edu.whu.irlab.thread.CalculateThread;
import org.junit.Test;

import java.io.File;
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

    @Test
    public void testCal(){
        // CalculateThread thread = new CalculateThread(new File("E:\\data\\raw\\201411.txt"), new File("E:\\data\\predict_result\\201410_1_cmlp.result.txt"));
        // thread.run();
    }
}
