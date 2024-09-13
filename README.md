

# CSV to JSON Converter

This project is a simple CSV to JSON converter that takes a CSV file as input and produces a JSON file as output.

## Use Cases

This project can be used in a variety of scenarios where huge amount data needs to be converted from CSV to JSON format, such as:

* Data cleaning and conversion for large files.

## How to Use

To use this project, follow these steps:

1. Clone the repository to your local machine.
2. Build the project using your preferred build tool (e.g. Maven, Gradle).
3. Run the `CsvToJsonConverter` class, passing the input CSV file and output JSON file path as arguments.
4. The converted JSON file will be written to the specified output path.

## Setup the Frontend
1. Go to the `csvtojson` directory.
2. Install the dependencies using `npm install`.
3. Run `npm run build` to build the frontend.
4. Run `npm start` and use the application.
5. Enjoy your file.

## Example Usage

Here is an example of how to use the `CsvToJsonConverter` class:
```java
CsvToJsonConverter converter = new CsvToJsonConverter();
File inputFile = new File("input.csv");
String outputFilePath = "output.json";
File outputFile = converter.convertCsvToJson(inputFile, outputFilePath);
```
## Output

The converted JSON file will be written to the specified output path. The output file will contain the data from the input CSV file in JSON format.

## API Documentation

The `CsvToJsonConverter` class has a single method, `convertCsvToJson`, which takes two arguments:

* `inputFile`: The CSV file to be converted.
* `outputFilePath`: The path where the resulting JSON file will be written.

The method returns a `File` object representing the converted JSON file.

## Endpoints

The project also includes a REST endpoint for converting CSV to JSON. The endpoint is located at `/convert` and accepts a CSV file as input. The converted JSON file is returned as the response.

To use the endpoint, send a POST request to `/convert` with the CSV file attached as a multipart file.

## Response

The response will be a JSON file containing the converted data.

I hope this helps! Let me know if you have any questions or need further clarification.