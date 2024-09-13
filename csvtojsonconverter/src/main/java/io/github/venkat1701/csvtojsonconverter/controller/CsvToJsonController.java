package io.github.venkat1701.csvtojsonconverter.controller;

import io.github.venkat1701.csvtojsonconverter.service.CsvToJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/csv")
@CrossOrigin("${frontend.url}")
public class CsvToJsonController {


    private CsvToJsonConverter csvToJsonConverter;

    @Autowired
    public CsvToJsonController(CsvToJsonConverter csvToJsonConverter) {
        this.csvToJsonConverter = csvToJsonConverter;
    }

    /**
     * Handles HTTP POST requests to the /convert endpoint.
     * Converts a CSV file to JSON and returns the resulting JSON file as a streaming response.
     *
     * @param file The CSV file to be converted, passed as a MultipartFile object.
     * @return A ResponseEntity object containing a StreamingResponseBody that represents the converted JSON file.
     * @throws Exception Any exceptions that occur during the conversion process.
     */
    @PostMapping("/convert")
    public ResponseEntity<StreamingResponseBody> convertToJson(@RequestParam("file") MultipartFile file) throws Exception {
        Path storageDir = Path.of("localstorage");
        Files.createDirectories(storageDir);
        Path csvFilePath = storageDir.resolve(file.getOriginalFilename());
        Path jsonFilePath = storageDir.resolve("output.json");
        try (var outputStream = Files.newOutputStream(csvFilePath)) {
            outputStream.write(file.getBytes());
        }
        File jsonFile = csvToJsonConverter.convertCsvToJson(csvFilePath.toFile(), jsonFilePath.toString());
        if (!jsonFile.exists()) {
            throw new FileNotFoundException("JSON file not found at path: " + jsonFile.getAbsolutePath());
        }
        StreamingResponseBody responseBody = outputStream -> {
            try (FileInputStream fileInputStream = new FileInputStream(jsonFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.json");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }
}
