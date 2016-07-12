package edu.whu.irlab.thread;

import com.google.common.base.Splitter;
import edu.whu.irlab.entity.TrainRecord;
import edu.whu.irlab.mobile.Train;
import edu.whu.irlab.service.TrainRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Roger on 2016/7/12.
 */
public class TrainThread implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(TrainThread.class);

    private TrainRecordService trainRecordService;
    private static boolean isStdOut = false;
    private TrainRecord trainRecord;

    public TrainThread(TrainRecordService trainRecordService, TrainRecord trainRecord){
        this.trainRecordService = trainRecordService;
        this.trainRecord = trainRecord;
    }

    @Override
    public void run() {
        List<String> months = Splitter.on(",").trimResults().splitToList(trainRecord.getTrainMonthData());
        Train train = new Train(trainRecord.getModel(), months, trainRecord.getModelPath());
        Map<String, Object> rtnMap = train.doTrain(isStdOut);
        if (!(Boolean) rtnMap.get("isCompleted")){
            logger.info("There was problems in Train, train record id={}", trainRecord.getId());
            // 出现异常
            trainRecord.setState(2);
        }else {
            logger.info("Train is completed, train record id={}", trainRecord.getId());
            // 训练完成, 设置成未在训练状态
            trainRecord.setState(0);
            trainRecord.setTrainPrecision((String) rtnMap.get("precision"));
        }
        trainRecordService.updateByPrimaryKey(trainRecord);
    }
}
