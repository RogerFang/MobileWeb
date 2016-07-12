package edu.whu.irlab.entity;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Roger on 2016/7/12.
 */
@Entity
@Table(name = "predict_record")
public class PredictRecord {

    private Integer id;
    private String model;
    private String predictMonth;
    private String predictMonthData;
    private String predictPrecision;
    private String resultPath;
    private TrainRecord trainRecord;
    private Integer state;
    private Integer calState;
    private Date updateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "predict_month")
    public String getPredictMonth() {
        return predictMonth;
    }

    public void setPredictMonth(String predictMonth) {
        this.predictMonth = predictMonth;
    }

    @Column(name = "predict_month_data")
    public String getPredictMonthData() {
        return predictMonthData;
    }

    public void setPredictMonthData(String predictMonthData) {
        this.predictMonthData = predictMonthData;
    }

    @Column(name = "predict_precision")
    public String getPredictPrecision() {
        return predictPrecision;
    }

    public void setPredictPrecision(String predictPrecision) {
        this.predictPrecision = predictPrecision;
    }

    @Column(name = "result_path")
    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    @ManyToOne
    @JoinColumn(name = "train_record")
    public TrainRecord getTrainRecord() {
        return trainRecord;
    }

    public void setTrainRecord(TrainRecord trainRecord) {
        this.trainRecord = trainRecord;
    }

    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Column(name = "cal_state")
    public Integer getCalState() {
        return calState;
    }

    public void setCalState(Integer calState) {
        this.calState = calState;
    }

    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Transient
    public String getFormatUpdateTime(){
        return DateFormatUtils.format(updateTime, "yyyy-MM-dd HH:mm:ss");
    }

    @Transient
    public List<String> getPredictMonthDataName() {
        List<String> filenameList = new ArrayList<>();
        for (String filepath: Splitter.on(",").trimResults().splitToList(predictMonthData)){
            filenameList.add(new File(filepath).getName());
        }
        return filenameList;
    }
}
