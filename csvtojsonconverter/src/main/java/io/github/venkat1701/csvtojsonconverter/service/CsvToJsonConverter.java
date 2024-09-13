package io.github.venkat1701.csvtojsonconverter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvToJsonConverter {

    /**
     * Converts a CSV file to a JSON file.
     *
     * @param inputFile The CSV file to be converted.
     * @param outputFilePath The path where the resulting JSON file will be written.
     * @return The File object representing the converted JSON file.
     * @throws Exception Any exceptions that occur during the conversion process.
     */
    public File convertCsvToJson(File inputFile, String outputFilePath) throws Exception {
        File output = new File(outputFilePath);
        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();
        List<Object> readData = csvMapper.readerFor(Map.class).with(csvSchema).readValues(inputFile).readAll();
        for (Object mapObj : readData) {
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) mapObj;
            List<String> deleteList = new ArrayList<>();
            LinkedHashMap<String, String> insertMap = new LinkedHashMap<>();
            for (Object entObj : map.entrySet()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) entObj;
                String oldKey = entry.getKey();
                String newKey = oldKey.replaceAll("[\n\s]+", " ");
                String value = entry.getValue();
                deleteList.add(oldKey);
                insertMap.put(newKey, value);
            }
            for (String oldKey : deleteList) {
                map.remove(oldKey);
            }
            map.putAll(insertMap);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(output, readData);
        return output;
    }
}
