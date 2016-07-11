package edu.whu.irlab.service.impl;

import edu.whu.irlab.entity.TrainRecord;
import edu.whu.irlab.repository.TrainRecordDao;
import edu.whu.irlab.service.TrainRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Roger on 2016/7/10.
 */
@Service
public class TrainRecordServiceImpl implements TrainRecordService {

    @Autowired
    private TrainRecordDao trainRecordDao;

    @Override
    public TrainRecord save(TrainRecord record) {
        return trainRecordDao.save(record);
    }

    @Override
    public int updateByPrimaryKey(TrainRecord record) {
        return trainRecordDao.updateByPrimaryKey(
                record.getTrainPrecision(),
                record.getState(),
                record.getId());
    }

    @Override
    public int updateByPrimaryKey(String trainPrecision, Integer state, Integer id) {
        return trainRecordDao.updateByPrimaryKey(
                trainPrecision,
                state,
                id);
    }
}
