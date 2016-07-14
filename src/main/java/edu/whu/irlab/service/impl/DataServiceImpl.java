package edu.whu.irlab.service.impl;

import edu.whu.irlab.mobile.props.ConfigProps;
import edu.whu.irlab.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Roger on 2016/5/17.
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private ConfigProps configProps;

    /*@Override
    public Map<String, String> getData2Train() {
        return getData(configProps.getProp("DATA_TO_TRAIN_DIR"));
    }

    @Override
    public Map<String, String> getData2Predict() {
        return getData(configProps.getProp("DATA_TO_PREDICT_DIR"));
    }*/

    @Override
    public Map<String, String> getData() {
        return getData(configProps.getProp("DATA_RAW_DIR"));
    }

    /**
     * @param dirPath 存放文件的目录
     * @return key=文件名, value=文件绝对路径
     */
    private Map<String, String> getData(String dirPath){
        File dir = new File(dirPath);
        if (dir.exists()){
            if (dir.isDirectory()){
                File[] files = dir.listFiles();
                if (files.length > 0) {
                    Map<String, String> dataFiles = new TreeMap<>();
                    for (File file: dir.listFiles()){
                        // System.out.println(file.getName());
                        dataFiles.put(file.getName(), file.getAbsolutePath());
                    }
                    return dataFiles;
                }
            }
        }else {
            dir.mkdirs();
        }
        return null;
    }
}
