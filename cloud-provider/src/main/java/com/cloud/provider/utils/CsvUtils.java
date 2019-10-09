package com.cloud.provider.utils;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Csv读写帮助类
 */
public class CsvUtils {

    /***
     * 用于生成csv文件
     * @param csvFilePath 生成文件的保存路径 例如 "D://test.csv"
     * @param csvHeaders 文件的表头 例如 { "编号", "姓名", "年龄" }
     * @param csvContent 文件的内容 例如 [{ 0, username1, 10 },{ 1, username2, 20 }]
     */
    public static void csvWrite(String csvFilePath, String[] csvHeaders, List<String[]> csvContent) {
        try {
            CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("UTF-8"));
            csvWriter.writeRecord(csvHeaders);
            int len = csvContent.size();
            for (int i = 0; i < len; i++) {
                csvWriter.writeRecord(csvContent.get(i));
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 用于读取csv文件，并将其转换为 List<Map<String,Object>>
     * @param csvFilePath 文件的路径 例如 "D://test.csv"
     * @param clz 需要转换的 bean.class
     * @return 返回 bean List 抛出异常时返回null
     */
    public static List<Map<String, Object>> csvRead(String csvFilePath, Class<?> clz) {
        ArrayList<String[]> csvFileList = new ArrayList<String[]>();
        try {
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
            //跳过表头
            //reader.readHeaders();
            //逐行读入除表头的数据
            while (reader.readRecord()) {
                csvFileList.add(reader.getValues());
            }
            reader.close();
            //将得到的数据转储到List<Map<String, Object>>
            ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            String[] headers = csvFileList.get(0);
            for (int i = 1; i < csvFileList.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                int j = 0;
                for (String  context: csvFileList.get(i)) {
                    String value = context;
                    String key = headers[j];
                    map.put(key, value);
                    j += 1;
                    if (j >= headers.length) {
                        break;
                    }
                }
                result.add(map);
                if (i > headers.length) {
                    break;
                }
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
