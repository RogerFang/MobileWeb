package edu.whu.irlab.controller;

import edu.whu.irlab.mobile.props.ConfigProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Roger on 2016/5/18.
 */
@Controller
@RequestMapping("/system")
public class SystemController {
    private static Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private ConfigProps configProps;


    @RequestMapping(method = RequestMethod.GET)
    public String system(Model model){
        model.addAttribute("configProps", configProps);
        return "system";
    }
}
