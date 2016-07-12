package edu.whu.irlab.controller;

import edu.whu.irlab.entity.PredictRecord;
import edu.whu.irlab.mobile.props.ConfigProps;
import edu.whu.irlab.service.DataService;
import edu.whu.irlab.service.PredictRecordService;
import edu.whu.irlab.service.TrainRecordService;
import edu.whu.irlab.thread.CalculateThread;
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

import java.util.List;

/**
 * Created by Roger on 2016/7/12.
 */
@Controller
@RequestMapping("/display")
public class DisplayController {

    private static Logger logger = LoggerFactory.getLogger(DisplayController.class);

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
    public String display(Model model){
        String[] modelNames = {
                // configProps.getProp("MODEL_LINEAR"),
                // configProps.getProp("MODEL_RMLP"),
                configProps.getProp("MODEL_CMLP"),
                configProps.getProp("MODEL_CNN"),
                configProps.getProp("MODEL_RNN")
        };
        model.addAttribute("model", trainRecordService.getModelToPredict(modelNames));
        model.addAttribute("data", dataService.getData());
        return "display";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public List<PredictRecord> search(Integer trainRecordId, String checkMonthData){
        List<PredictRecord> predictRecordList = predictRecordService.selectDisplayRecord(trainRecordService.findEntity(trainRecordId), MonthFileUtil.getMonth(checkMonthData));
        // 进行预测正确率计算
        for (PredictRecord record: predictRecordList){
            if (record.getCalState() == null || record.getCalState() == 2 || record.getCalState() == -1){
                // 进行计算
                record.setCalState(1);
                predictRecordService.updateCalStateByPrimaryKey(1, record.getId());
                threadPoolTaskExecutor.execute(new CalculateThread(checkMonthData, record, predictRecordService));
            }
        }
        return predictRecordList;
    }
}
