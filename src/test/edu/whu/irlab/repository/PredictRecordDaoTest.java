package edu.whu.irlab.repository;

import edu.whu.irlab.entity.PredictRecord;
import edu.whu.irlab.entity.TrainRecord;
import edu.whu.irlab.service.PredictRecordService;
import edu.whu.irlab.service.TrainRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Roger on 2016/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:applicationContext-jpa.xml"})
public class PredictRecordDaoTest {
    @Autowired
    private TrainRecordService trainRecordService;

    @Autowired
    private PredictRecordService predictRecordService;

    @Autowired
    private PredictRecordDao predictRecordDao;

    @Test
    public void findByTrainRecordAndPredictMonthDataAndState() throws Exception {
        TrainRecord trainRecord = trainRecordService.findEntity(1);
        List<PredictRecord> list = predictRecordService.selectDisplayRecord(trainRecord, "E:\\data\\train\\201409.txt");
        System.out.println(list.size());
    }

}