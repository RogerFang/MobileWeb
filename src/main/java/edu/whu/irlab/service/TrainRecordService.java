package edu.whu.irlab.service;

import edu.whu.irlab.entity.TrainRecord;

/**
 * Created by Roger on 2016/7/9.
 */
public interface TrainRecordService {

    TrainRecord save(TrainRecord record);

    int updateByPrimaryKey(TrainRecord record);

    int updateByPrimaryKey(String trainPrecision, Integer state, Integer id);
}
