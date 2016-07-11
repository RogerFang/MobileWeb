package edu.whu.irlab.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Roger on 2016/7/9.
 */
@Entity
@Table(name = "train_record")
public class TrainRecord {

    private Integer id;
    private String model;
    private String trainMonthData;
    private String modelPath;
    private String trainPrecision;
    private Integer state;
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

    @Column(name = "train_month_data")
    public String getTrainMonthData() {
        return trainMonthData;
    }

    public void setTrainMonthData(String trainMonthData) {
        this.trainMonthData = trainMonthData;
    }

    @Column(name = "model_path")
    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    @Column(name = "train_precision")
    public String getTrainPrecision() {
        return trainPrecision;
    }

    public void setTrainPrecision(String trainPrecision) {
        this.trainPrecision = trainPrecision;
    }

    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
