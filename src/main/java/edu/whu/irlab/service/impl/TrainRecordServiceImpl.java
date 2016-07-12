package edu.whu.irlab.service.impl;

import com.google.common.collect.Maps;
import edu.whu.irlab.entity.TrainRecord;
import edu.whu.irlab.mobile.props.ConfigProps;
import edu.whu.irlab.repository.TrainRecordDao;
import edu.whu.irlab.service.TrainRecordService;
import edu.whu.irlab.util.CDynamicSpecifications;
import edu.whu.irlab.util.CSearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Roger on 2016/7/10.
 */
@Service
public class TrainRecordServiceImpl implements TrainRecordService {

    @Autowired
    private ConfigProps configProps;

    @Autowired
    private TrainRecordDao trainRecordDao;

    @Override
    public TrainRecord findEntity(Integer id) {
        return trainRecordDao.findOne(id);
    }

    @Override
    public TrainRecord save(TrainRecord record) {
        return trainRecordDao.save(record);
    }

    @Override
    public int updateByPrimaryKey(TrainRecord record) {
        return trainRecordDao.updateByPrimaryKey(
                record.getTrainPrecision(),
                record.getState(),
                new Date(),
                record.getModelPath(),
                record.getId());
    }

    @Override
    public List<TrainRecord> selectByModel(String model) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Map<String, CSearchFilter> filters = Maps.newHashMap();
        filters.put("model", new CSearchFilter("model", CSearchFilter.COperator.EQ, model));
        Specification<TrainRecord> spec = CDynamicSpecifications.bySearchFilter(filters.values(), TrainRecord.class);

        return trainRecordDao.findAll(spec, sort);
    }

    @Override
    public List<TrainRecord> selectByModelAndState(String model, Integer state) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Map<String, CSearchFilter> filters = Maps.newHashMap();
        filters.put("model", new CSearchFilter("model", CSearchFilter.COperator.EQ, model));
        filters.put("state", new CSearchFilter("state", CSearchFilter.COperator.EQ, state));
        Specification<TrainRecord> spec = CDynamicSpecifications.bySearchFilter(filters.values(), TrainRecord.class);

        return trainRecordDao.findAll(spec, sort);
    }

    @Override
    public Map<String, TrainRecord> getModelToPredict(String[] modelNames) {
        Map<String, TrainRecord> model = new HashMap<>();

        for (String modelName: modelNames){
            TrainRecord record = getModelToPredict(modelName);
            if (record != null){
                model.put(modelName, record);
            }
        }

        return model;
    }

    private TrainRecord getModelToPredict(String model){
        List<TrainRecord> list = selectByModelAndState(model, 0);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }
}
