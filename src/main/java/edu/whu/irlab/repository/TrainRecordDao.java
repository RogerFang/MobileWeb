package edu.whu.irlab.repository;

import edu.whu.irlab.entity.TrainRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Roger on 2016/7/9.
 */
public interface TrainRecordDao extends PagingAndSortingRepository<TrainRecord, Integer>, JpaSpecificationExecutor<TrainRecord> {

    @Modifying
    @Query("update TrainRecord tr set tr.trainPrecision = ?1, tr.state = ?2 where tr.id = ?3")
    int updateByPrimaryKey(String trainPrecision, Integer state, Integer id);
}
