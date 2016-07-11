package edu.whu.irlab.controller;

import edu.whu.irlab.service.TrainRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Roger on 2016/7/9.
 */
@Controller
@RequestMapping("/train")
public class TrainController {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private TrainRecordService trainRecordService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> train(){
        return null;
    }
}
