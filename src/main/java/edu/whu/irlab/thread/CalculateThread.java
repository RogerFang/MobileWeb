package edu.whu.irlab.thread;

import edu.whu.irlab.entity.PredictRecord;
import edu.whu.irlab.mobile.service.ExSortService;
import edu.whu.irlab.service.PredictRecordService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Roger on 2016/7/12.
 */
public class CalculateThread implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(CalculateThread.class);

    private static ExSortService exSortService = ExSortService.getInstance();
    private PredictRecordService predictRecordService;
    private String checkFilePath;
    private PredictRecord predictRecord;

    public CalculateThread(String checkFilePath, PredictRecord predictRecord, PredictRecordService predictRecordService){
        this.checkFilePath = checkFilePath;
        this.predictRecord = predictRecord;
        this.predictRecordService = predictRecordService;
    }

    @Override
    public void run() {
        List<File> fileList = new ArrayList<>();
        try {
            fileList.add(exSortService.sort(new File(checkFilePath)));
            fileList.add(new File(predictRecord.getResultPath()));
            String predictPrecision = calculate(fileList);
            predictRecord.setPredictPrecision(predictPrecision);
            predictRecordService.updatePrecisionByPrimaryKey(predictPrecision, predictRecord.getId());
        } catch (IOException e) {
            predictRecordService.updateCalStateByPrimaryKey(2, predictRecord.getId());
            e.printStackTrace();
        }
    }

    /**
     * 根据已排序的大数据文件列表, 生成交集数据
     * @param sortedFileList
     */
    private String calculate(List<File> sortedFileList) throws IOException {
        logger.info("Start generating intersection from the sorted file list");
        int interCount = 0;
        int equalCount = 0;

        int fileListSize = sortedFileList.size();

        List<BufferedReader> brList = new ArrayList<>();
        for (File sortedFile: sortedFileList){
            brList.add(new BufferedReader(new FileReader(sortedFile)));
        }

        // 是否有文件已经读完
        boolean notComplete = true;

        List<String> lines = new ArrayList<>();
        List<String> mobiles = new ArrayList<>();
        // 初始化
        for (int i=0; i<fileListSize; i++){
            String line = brList.get(i).readLine();
            if (line != null){
                if (StringUtils.isNotEmpty(line)){
                    lines.add(line);
                    mobiles.add(line.substring(0, 11));
                }
            }else {
                // 有任何文件已经读完后就表示求交集完成
                notComplete = false;
            }
        }

        while (notComplete){
            // 对lines, mobiles求交集
            List<Integer> smallerList = getMaxMobile(mobiles);
            // mobiles不等, 较小的索引对应的文件继续往下读
            if (smallerList.size() > 0){
                for (Integer index: smallerList){
                    String line = brList.get(index).readLine();

                    if (line != null){
                        if (StringUtils.isNotEmpty(line)){
                            lines.set(index, line);
                            mobiles.set(index, line.substring(0, 11));
                        }
                    }else {
                        // 有任何文件已经读完后就表示求交集完成
                        notComplete = false;
                    }
                }
            }else {
                // smallerList.size() == 0时, 表示mobiles都相等是交集, 写入交集文件
                boolean isEqual = checkEquals(lines);
                interCount++;
                if (isEqual){
                    equalCount++;
                }

                // 写入交集文件后重新读取数据更新lines, mobiles
                for (int i=0; i<brList.size(); i++){
                    String line = brList.get(i).readLine();

                    if (line != null){
                        if (StringUtils.isNotEmpty(line)){
                            lines.set(i, line);
                            mobiles.set(i, line.substring(0, 11));
                        }
                    }else {
                        // 有任何文件已经读完后就表示求交集完成
                        notComplete = false;
                    }
                }
            }
        }

        for (BufferedReader br: brList){
            if (br != null){
                br.close();
            }
        }

        sortedFileList.get(0).delete();

        logger.info("Calculate predict precision, equalCount={}, interCount={}", equalCount, interCount);
        return String.format("%.2f", (double)equalCount/interCount);
    }

    /**
     * 将传入的交集lines转换为一条特征记录
     *
     * @param lines
     * @return
     */
    private boolean checkEquals(List<String> lines){
        String[] checkProps = lines.get(0).split(",");
        String[] resultProps = lines.get(1).split(",");

        String check;
        if (checkProps[2].equals("在网-开通")){
            check = "1";
        }else {
            check = "0";
        }

        if(check.equals(resultProps[1])){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 返回较小的index索引值
     * @param mobiles
     * @return
     */
    private List<Integer> getMaxMobile(List<String> mobiles){
        // 降序
        Collections.sort(mobiles, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        List<Integer> smallerIds = new ArrayList<>();
        String max = mobiles.get(0);
        for (int i=0; i<mobiles.size(); i++){
            if (mobiles.get(i).compareTo(max) < 0){
                smallerIds.add(i);
            }
        }
        return smallerIds;
    }
}
