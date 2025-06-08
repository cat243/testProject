package com.yupi.project.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvToJsonConverter {

    /**
     * 将CSV文件转换为JSON字符串
     * @param csvFilePath CSV文件路径
     * @param hasHeader 是否包含表头
     * @return JSON字符串
     * @throws IOException 文件读取异常
     */
    public static String convertCsvToJson(String csvFilePath, boolean hasHeader) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
             CSVReader csvReader = new CSVReader(reader)) {
            
            List<String[]> csvData = csvReader.readAll();
            if (csvData.isEmpty()) {
                return "[]";
            }

            String[] headers = hasHeader ? csvData.get(0) : generateHeaders(csvData.get(0).length);
            int startRow = hasHeader ? 1 : 0;

            List<Map<String, String>> jsonData = new ArrayList<>();
            for (int i = startRow; i < csvData.size(); i++) {
                Map<String, String> rowMap = new LinkedHashMap<>();
                String[] row = csvData.get(i);
                
                for (int j = 0; j < headers.length; j++) {
                    // 处理列数不一致的情况
                    String value = j < row.length ? row[j] : "";
                    rowMap.put(headers[j], value);
                }
                jsonData.add(rowMap);
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(jsonData);
        } catch (CsvException e) {
            throw new IOException("CSV parsing error", e);
        }
    }

    /**
     * 将CSV文件转换为JSON并保存到文件
     * @param csvFilePath 输入CSV文件路径
     * @param jsonFilePath 输出JSON文件路径
     * @param hasHeader 是否包含表头
     * @throws IOException 文件读写异常
     */
    public static void convertCsvToJsonFile(String csvFilePath, String jsonFilePath, boolean hasHeader) 
            throws IOException {
        String json = convertCsvToJson(csvFilePath, hasHeader);
        Files.write(Paths.get(jsonFilePath), json.getBytes());
    }

    /**
     * 生成默认表头(当CSV没有表头时使用)
     * @param columnCount 列数
     * @return 生成的表头数组
     */
    private static String[] generateHeaders(int columnCount) {
        String[] headers = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            headers[i] = "column_" + (i + 1);
        }
        return headers;
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        try {
            // 示例用法
            String csvPath = "input.csv";
            String jsonPath = "output.json";
            
            // 转换并打印到控制台
            String json = convertCsvToJson(csvPath, true);
            System.out.println("CSV to JSON Result:\n" + json);
            
            // 转换并保存到文件
            convertCsvToJsonFile(csvPath, jsonPath, true);
            System.out.println("JSON file saved to: " + jsonPath);
            
        } catch (IOException e) {
            System.err.println("Error converting CSV to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}