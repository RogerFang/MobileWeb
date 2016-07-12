package edu.whu.irlab.repository;

import edu.whu.irlab.entity.PredictRecord;
import edu.whu.irlab.entity.TrainRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Roger on 2016/7/12.
 */
public interface PredictRecordDao extends PagingAndSortingRepository<PredictRecord, Integer>, JpaSpecificationExecutor<PredictRecord> {

    @Transactional
    @Modifying
    @Query("update PredictRecord pr set pr.resultPath = ?1, pr.state = ?2, pr.updateTime = ?3 where pr.id = ?4")
    int updateByPrimaryKey(String resultPath, Integer state, Date updateTime, Integer id);

    @Transactional
    @Modifying
    @Query("update PredictRecord pr set pr.predictPrecision = ?1, pr.calState =?2, pr.updateTime = ?3 where pr.id = ?4")
    int updatePrecisionByPrimaryKey(String predictPrecision, Integer calState, Date updateTime, Integer id);

    @Transactional
    @Modifying
    @Query("update PredictRecord pr set pr.calState =?1, pr.updateTime = ?2 where pr.id = ?3")
    int updateCalStateByPrimaryKey(Integer calState, Date updateTime, Integer id);

    List<PredictRecord> findByTrainRecordAndPredictMonthAndState(TrainRecord trainRecord, String predictMonth, Integer state);
}
