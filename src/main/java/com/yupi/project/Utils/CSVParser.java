package com.yupi.project.Utils;

import java.io.*;
import java.util.*;

public class CSVParser {

    private String filepath;

    public CSVParser(String filepath) {
        this.filepath = filepath;
    }

    /**
     * 读取 CSV 文件并将其内容转换为 List<Map<String, String>>。
     * 每行数据存储为一个 Map，键为表头，值为数据内容。
     *
     * @return List<Map<String, String>> 数据
     */
    public List<Map<String, String>> readCSVToList() {
        List<Map<String, String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            String[] headers = null;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (isHeader) {
                    headers = values;
                    isHeader = false;
                } else {
                    Map<String, String> rowData = new HashMap<>();
                    for (int i = 0; i < values.length; i++) {
                        rowData.put(headers[i], values[i]);
                    }
                    data.add(rowData);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到: " + filepath);
        } catch (IOException e) {
            System.out.println("读取文件时发生错误: " + e.getMessage());
        }
        return data;
    }

    /**
     * 将数据写入 CSV 文件。
     *
     * @param data List<Map<String, String>> 数据
     * @param outputFilePath 输出文件路径
     */
    public void writeListToCSV(List<Map<String, String>> data, String outputFilePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            if (data == null || data.isEmpty()) {
                System.out.println("数据为空，无法写入 CSV 文件");
                return;
            }

            // 写入表头
            Set<String> headers = data.get(0).keySet();
            bw.write(String.join(",", headers));
            bw.newLine();

            // 写入数据
            for (Map<String, String> row : data) {
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    values.add(row.get(header));
                }
                bw.write(String.join(",", values));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("写入文件时发生错误: " + e.getMessage());
        }
    }

    /**
     * 读取 CSV 文件并转换为 List<String[]>，每一行数据是一个数组。
     *
     * @return List<String[]> 数据
     */
    public List<String[]> readCSVToArrayList() {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到: " + filepath);
        } catch (IOException e) {
            System.out.println("读取文件时发生错误: " + e.getMessage());
        }
        return data;
    }

    /**
     * 将 List<String[]> 写入 CSV 文件。
     *
     * @param data List<String[]> 数据
     * @param outputFilePath 输出文件路径
     */
    public void writeArrayListToCSV(List<String[]> data, String outputFilePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("写入文件时发生错误: " + e.getMessage());
        }
    }

    // 示例使用
    public static void main(String[] args) {
        String filepath = "example.csv";  // 需要替换为实际文件路径
        CSVParser parser = new CSVParser(filepath);

        // 读取 CSV 文件为 List<Map<String, String>> 格式
        List<Map<String, String>> dataDict = parser.readCSVToList();
        System.out.println("读取的 CSV 数据（字典格式）:");
        for (Map<String, String> row : dataDict) {
            System.out.println(row);
        }

        // 写入 CSV 文件
        String outputFilePathDict = "output_dict.csv";
        parser.writeListToCSV(dataDict, outputFilePathDict);

        // 读取 CSV 文件为 List<String[]> 格式
        List<String[]> dataArray = parser.readCSVToArrayList();
        System.out.println("读取的 CSV 数据（数组格式）:");
        for (String[] row : dataArray) {
            System.out.println(Arrays.toString(row));
        }

        // 写入 CSV 文件
        String outputFilePathArray = "output_array.csv";
        parser.writeArrayListToCSV(dataArray, outputFilePathArray);
    }
}
