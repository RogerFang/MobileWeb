package edu.whu.irlab.controller;

import edu.whu.irlab.mobile.props.ConfigProps;
import edu.whu.irlab.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Roger on 2016/5/18.
 */
@Controller
@RequestMapping("/system")
public class SystemController {
    private static Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private ConfigProps configProps;

    @Autowired
    private DataService dataService;

    @RequestMapping(method = RequestMethod.GET)
    public String system(Model model){
        List<String> data2TrainPath = dataService.getData2Train();
        String data2PredictPath = dataService.getData2Predict();
        // String predictMonth = dataService.getPredictMonth();
        // List<TrainRecord> trainRecords = trainRecordService.selectAll();
        // List<TrainRecord> trainRecordsFinished = trainRecordService.selectByState(0);
        // SystemProps systemProps = systemPropsService.getSystemProps();

        model.addAttribute("data2TrainPath", data2TrainPath);
        model.addAttribute("data2PredictPath", data2PredictPath);
        // model.addAttribute("predictMonth", predictMonth);
        // model.addAttribute("trainRecords", trainRecords);
        // model.addAttribute("trainRecordsFinished", trainRecordsFinished);
        model.addAttribute("configProps", configProps);

        return "system";
    }
}
