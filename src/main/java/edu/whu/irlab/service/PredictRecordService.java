package edu.whu.irlab.service;

import edu.whu.irlab.entity.PredictRecord;
import edu.whu.irlab.entity.TrainRecord;

import java.util.List;

/**
 * Created by Roger on 2016/7/12.
 */
public interface PredictRecordService {

    PredictRecord save(PredictRecord record);

    List<PredictRecord> selectByModelAndState(String model, Integer state);

    List<PredictRecord> selectByModel(String model);

    int updateByPrimaryKey(PredictRecord record);

    int updatePrecisionByPrimaryKey(String predictPrecision, Integer id);

    List<PredictRecord> selectDisplayRecord(TrainRecord trainRecord, String predictMonth);

    int updateCalStateByPrimaryKey(Integer calState, Integer id);
}
