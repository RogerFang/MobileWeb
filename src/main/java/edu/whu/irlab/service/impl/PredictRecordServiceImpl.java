package edu.whu.irlab.service.impl;

import com.google.common.collect.Maps;
import edu.whu.irlab.entity.PredictRecord;
import edu.whu.irlab.entity.TrainRecord;
import edu.whu.irlab.repository.PredictRecordDao;
import edu.whu.irlab.service.PredictRecordService;
import edu.whu.irlab.util.CDynamicSpecifications;
import edu.whu.irlab.util.CSearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Roger on 2016/7/12.
 */
@Service
public class PredictRecordServiceImpl implements PredictRecordService {

    @Autowired
    private PredictRecordDao predictRecordDao;

    @Override
    public PredictRecord save(PredictRecord record) {
        return predictRecordDao.save(record);
    }

    @Override
    public List<PredictRecord> selectByModelAndState(String model, Integer state) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Map<String, CSearchFilter> filters = Maps.newHashMap();
        filters.put("model", new CSearchFilter("model", CSearchFilter.COperator.EQ, model));
        filters.put("state", new CSearchFilter("state", CSearchFilter.COperator.EQ, state));
        Specification<PredictRecord> spec = CDynamicSpecifications.bySearchFilter(filters.values(), PredictRecord.class);
        return predictRecordDao.findAll(spec, sort);
    }

    @Override
    public List<PredictRecord> selectByModel(String model) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Map<String, CSearchFilter> filters = Maps.newHashMap();
        filters.put("model", new CSearchFilter("model", CSearchFilter.COperator.EQ, model));
        Specification<PredictRecord> spec = CDynamicSpecifications.bySearchFilter(filters.values(), PredictRecord.class);
        return predictRecordDao.findAll(spec, sort);
    }

    @Override
    public int updateByPrimaryKey(PredictRecord record) {
        return predictRecordDao.updateByPrimaryKey(
                record.getResultPath(),
                record.getState(),
                new Date(),
                record.getId());
    }

    @Override
    public int updatePrecisionByPrimaryKey(String predictPrecision, Integer id) {
        return predictRecordDao.updatePrecisionByPrimaryKey(
                predictPrecision,
                0,
                new Date(),
                id);
    }

    @Override
    public List<PredictRecord> selectDisplayRecord(TrainRecord trainRecord, String predictMonth) {
        return predictRecordDao.findByTrainRecordAndPredictMonthAndState(trainRecord, predictMonth, 0);
    }

    @Override
    public int updateCalStateByPrimaryKey(Integer calState, Integer id) {
        return predictRecordDao.updateCalStateByPrimaryKey(
                calState,
                new Date(),
                id);
    }
}
