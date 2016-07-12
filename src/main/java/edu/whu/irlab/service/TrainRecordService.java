package edu.whu.irlab.service;

import edu.whu.irlab.entity.TrainRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by Roger on 2016/7/9.
 */
public interface TrainRecordService {

    TrainRecord findEntity(Integer id);

    TrainRecord save(TrainRecord record);

    int updateByPrimaryKey(TrainRecord record);

    List<TrainRecord> selectByModel(String model);

    List<TrainRecord> selectByModelAndState(String model, Integer state);

    Map<String, TrainRecord> getModelToPredict(String[] modelNames);
}
