package edu.whu.irlab.repository;

import edu.whu.irlab.entity.TrainRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Roger on 2016/7/9.
 */
public interface TrainRecordDao extends PagingAndSortingRepository<TrainRecord, Integer>, JpaSpecificationExecutor<TrainRecord> {

    @Transactional
    @Modifying
    @Query("update TrainRecord tr set tr.trainPrecision = ?1, tr.state = ?2, tr.updateTime =?3, tr.modelPath = ?4 where tr.id = ?5")
    int updateByPrimaryKey(String trainPrecision, Integer state, Date updateTime, String modelPath, Integer id);
}
