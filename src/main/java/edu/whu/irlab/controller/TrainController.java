package edu.whu.irlab.controller;

import com.google.common.base.Joiner;
import edu.whu.irlab.entity.TrainRecord;
import edu.whu.irlab.mobile.props.ConfigProps;
import edu.whu.irlab.service.DataService;
import edu.whu.irlab.service.TrainRecordService;
import edu.whu.irlab.thread.TrainThread;
import edu.whu.irlab.util.CalendarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.*;

/**
 * Created by Roger on 2016/7/9.
 */
@Controller
@RequestMapping("/train")
public class TrainController {

    private static Logger logger = LoggerFactory.getLogger(TrainController.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private TrainRecordService trainRecordService;

    @Autowired
    private DataService dataService;

    @Autowired
    private ConfigProps configProps;

    @RequestMapping(method = RequestMethod.GET)
    public String train(){
        return "train";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> train(String model,
                                     String[] months){
        Map<String, String> map = new HashMap<>();
        if (months == null){
            map.put("msg", "请选择相应的数据!");
            return map;
        }

        TrainRecord trainRecord = new TrainRecord();
        trainRecord.setModel(model);
        trainRecord.setTrainMonthData(Joiner.on(",").skipNulls().join(months));

        if (model.equals(configProps.getProp("MODEL_RNN"))){
            int rnnsize = Integer.parseInt(configProps.getProp("numSteps")) + 1;
            if (months==null || months.length != rnnsize){
                map.put("msg", "数据只能输入连续"+rnnsize+"个月份!");
                return map;
            }
        }else {
            if (months== null || months.length != 2){
                map.put("msg", "数据只能输入连续2个月份");
                return map;
            }
        }

        if (!checkDataContinuity(trainRecord)){
            map.put("msg", "数据月份不连续!");
            return map;
        }


        trainRecord.setModelPath("");
        trainRecord.setTrainPrecision("");
        // 1:正在训练
        trainRecord.setState(1);
        trainRecord.setUpdateTime(new Date());
        trainRecord = trainRecordService.save(trainRecord);
        String modelPath = configProps.getProp("MODEL_SAVE_DIR") + File.separator + trainRecord.getModel() + "_" + trainRecord.getId() + ".model";
        trainRecord.setModelPath(modelPath);
        logger.info("Start Train Thread, model:{},months:{},modelPath:{}", trainRecord.getModel(), trainRecord.getTrainMonthData(), trainRecord.getModelPath());
        threadPoolTaskExecutor.execute(new TrainThread(trainRecordService, trainRecord));

        return map;
    }

    @RequestMapping(value = "/getTrainRecord", method = RequestMethod.POST)
    @ResponseBody
    public List<TrainRecord> getTrainRecord(String model){
        return trainRecordService.selectByModel(model);
    }

    @RequestMapping(value = "/getTrainData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getTrainData(String model){
        List<TrainRecord> finishedList = trainRecordService.selectByModelAndState(model, 0);
        List<TrainRecord> activeList = trainRecordService.selectByModelAndState(model, 1);
        finishedList.addAll(activeList);
        Set<String> dealedDatanameSet = new HashSet<>();
        for (TrainRecord record: finishedList){
            List<String> trainDataName = record.getTrainMonthDataName();
            dealedDatanameSet.addAll(trainDataName.subList(0, trainDataName.size()-1));
        }

        Map<String, String> data = new TreeMap<>();
        for (Map.Entry<String, String> entry: dataService.getData().entrySet()){
            if (!dealedDatanameSet.contains(entry.getKey())){
                data.put(entry.getKey(), entry.getValue());
            }
        }
        return data;
    }

    private boolean checkDataContinuity(TrainRecord trainRecord){
        List<String> trainDataName = trainRecord.getTrainMonthDataName();
        boolean isContinuity = true;
        for (int i=0; i<trainDataName.size()-1; i++){
            if (!CalendarUtil.getNextMonth(trainDataName.get(i).substring(0,6)).equals(trainDataName.get(i+1).substring(0,6))){
                isContinuity = false;
            }
        }
        return  isContinuity;
    }
}
