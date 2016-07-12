package edu.whu.irlab.util;

import java.io.File;

/**
 * Created by Roger on 2016/7/12.
 */
public class MonthFileUtil {

    /**
     * 根据文件路径, 返回数据用于预测的月份, 月份加1
     * @param predictMonthData
     * @return
     */
    public static String getPredictMonth(String predictMonthData){
        String dataMonth = new File(predictMonthData).getName().substring(0, 6);
        return CalendarUtil.getNextMonth(dataMonth);
    }

    /**
     * 根据检验月份的数据文件获取月份
     * @param monthData
     * @return
     */
    public static String getMonth(String monthData){
        return new File(monthData).getName().substring(0, 6);
    }
}
