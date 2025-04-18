package com.yupi.project.Utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 编写csv转换为List的工具类
 */
public class CSVParser {

    // 定义一个类来存储每一行的数据
    public static class Data {
        private String column1;
        private String column2;
        private String column3;

        public Data(String column1, String column2, String column3) {
            this.column1 = column1;
            this.column2 = column2;
            this.column3 = column3;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "column1='" + column1 + '\'' +
                    ", column2='" + column2 + '\'' +
                    ", column3='" + column3 + '\'' +
                    '}';
        }
    }

    public static List<Data> parseCSV(String filePath) {
        List<Data> dataList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            // 跳过标题行（如果有的话）
            reader.readNext();  // Uncomment if there's a header row
            while ((line = reader.readNext()) != null) {
                // 假设 CSV 有 3 列数据
                String column1 = line[0];
                String column2 = line[1];
                String column3 = line[2];
                // 将数据存入 List
                dataList.add(new Data(column1, column2, column3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return dataList;
    }

}
