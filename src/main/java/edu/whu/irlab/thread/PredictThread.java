package edu.whu.irlab.thread;

import com.google.common.base.Splitter;
import edu.whu.irlab.entity.PredictRecord;
import edu.whu.irlab.mobile.Predict;
import edu.whu.irlab.service.PredictRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Roger on 2016/7/12.
 */
public class PredictThread implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(PredictThread.class);

    private PredictRecordService predictRecordService;
    private static boolean isStdOut = false;
    private PredictRecord predictRecord;

    public PredictThread(PredictRecordService predictRecordService, PredictRecord predictRecord){
        this.predictRecordService = predictRecordService;
        this.predictRecord = predictRecord;
    }

    @Override
    public void run() {
        List<String> months = Splitter.on(",").trimResults().splitToList(predictRecord.getPredictMonthData());
        Predict predict = new Predict(predictRecord.getModel(), months, predictRecord.getTrainRecord().getModelPath());
        String resultPath = predict.doPredict(isStdOut);
        if (!resultPath.equals("")){
            logger.info("Predict is completed, predict record id={}", predictRecord.getId());
            predictRecord.setState(0);
            predictRecord.setResultPath(resultPath);
        }else {
            // 出现异常
            logger.info("There was problems in Predict, predict record id={}", predictRecord.getId());
            predictRecord.setState(2);
        }
        predictRecordService.updateByPrimaryKey(predictRecord);
    }
}
