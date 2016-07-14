package edu.whu.irlab.controller;

import edu.whu.irlab.entity.PredictRecord;
import edu.whu.irlab.entity.TrainRecord;
import edu.whu.irlab.mobile.props.ConfigProps;
import edu.whu.irlab.service.DataService;
import edu.whu.irlab.service.PredictRecordService;
import edu.whu.irlab.service.TrainRecordService;
import edu.whu.irlab.thread.PredictThread;
import edu.whu.irlab.util.MonthFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Roger on 2016/7/12.
 */
@Controller
@RequestMapping("/predict")
public class PredictController {

    private static Logger logger = LoggerFactory.getLogger(PredictController.class);

    @Autowired
    private ConfigProps configProps;

    @Autowired
    private TrainRecordService trainRecordService;

    @Autowired
    private PredictRecordService predictRecordService;

    @Autowired
    private DataService dataService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @RequestMapping(method = RequestMethod.GET)
    public String predict(Model model){
        String[] modelNames = {
                configProps.getProp("MODEL_LINEAR"),
                configProps.getProp("MODEL_RMLP"),
                configProps.getProp("MODEL_CMLP"),
                configProps.getProp("MODEL_CNN"),
                configProps.getProp("MODEL_RNN")
        };

        model.addAttribute("model", trainRecordService.getModelToPredict(modelNames));
        return "predict";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> predict(Integer trainRecordId,
                                       String predictMonthData){
        Map<String, String> map = new HashMap<>();
        if (predictMonthData == null){
            map.put("msg", "请选择相应的数据!");
            return map;
        }

        TrainRecord trainRecord = trainRecordService.findEntity(trainRecordId);
        String model = trainRecord.getModel();
        PredictRecord predictRecord = new PredictRecord();
        predictRecord.setModel(model);
        predictRecord.setPredictMonthData(predictMonthData);
        predictRecord.setPredictMonth(MonthFileUtil.getPredictMonth(predictMonthData));
        predictRecord.setTrainRecord(trainRecord);
        predictRecord.setPredictPrecision("");
        predictRecord.setResultPath("");
        predictRecord.setState(1);
        predictRecord.setUpdateTime(new Date());

        predictRecordService.save(predictRecord);
        logger.info("Start Predict Thread, model:{},months:{},modelPath:{}", predictRecord.getModel(), predictRecord.getPredictMonthData(), trainRecord.getModelPath());
        threadPoolTaskExecutor.execute(new PredictThread(predictRecordService, predictRecord));

        map.put("msg", "正在预测!");
        return map;
    }

    @RequestMapping(value = "/getPredictData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getPredictData(Integer trainRecordId){
        TrainRecord trainRecord = trainRecordService.findEntity(trainRecordId);
        String model = trainRecord.getModel();
        List<PredictRecord> finishedList = predictRecordService.selectByModelAndState(model, 0);
        List<PredictRecord> activeList = predictRecordService.selectByModelAndState(model, 1);
        finishedList.addAll(activeList);
        Set<String> dealedDatanameSet = new HashSet<>();
        for (PredictRecord record: finishedList){
            List<String> predictDataName = record.getPredictMonthDataName();
            dealedDatanameSet.addAll(predictDataName);
        }

        Map<String, String> data = new TreeMap<>();
        for (Map.Entry<String, String> entry: dataService.getData().entrySet()){
            if (!dealedDatanameSet.contains(entry.getKey())){
                data.put(entry.getKey(), entry.getValue());
            }
        }
        return data;
    }

    @RequestMapping(value = "/getPredictRecord", method = RequestMethod.POST)
    @ResponseBody
    public List<PredictRecord> getPredictRecord(Integer trainRecordId){
        TrainRecord trainRecord = trainRecordService.findEntity(trainRecordId);
        String model = trainRecord.getModel();
        return predictRecordService.selectByModel(model);
    }


}
