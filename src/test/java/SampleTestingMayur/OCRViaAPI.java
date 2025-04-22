package SampleTestingMayur;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType; // ✅ Correct import for APPLICATION_PDF
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OCRViaAPI {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Mayur More\\Downloads\\IMG_1060.pdf";
        String empEmailid = "";
        String empMobileNo = "";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("http://localhost:8000/ocr");

            File file = new File(filePath);
            FileBody fileBody = new FileBody(file, ContentType.create("application/pdf"));

            System.out.println(ContentType.class.getPackage().getImplementationVersion());

            HttpEntity multipart = MultipartEntityBuilder.create()
                    .addPart("file", fileBody)
                    .build();

            post.setEntity(multipart);

            try (CloseableHttpResponse response = client.execute(post)) {
                InputStream responseStream = response.getEntity().getContent();
                String resultJson;
                try (Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8.name())) {
                    resultJson = scanner.useDelimiter("\\A").next();
                }

                // Print the JSON response to check the structure
                System.out.println("JSON Response: " + resultJson);

                JSONObject jsonObject = new JSONObject(resultJson);

                // Check for the "text" key in the response
                if (jsonObject.has("text")) {
                    String extractedText = jsonObject.getString("text");
                    System.out.println("OCR Extracted Text:\n" + extractedText);

                    // Extract email and phone numbers
                    Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
                    Matcher emailMatcher = emailPattern.matcher(extractedText);
                    if (emailMatcher.find()) {
                        empEmailid = emailMatcher.group();
                    }

                    Pattern mobilePattern = Pattern.compile("\\+?\\d{1,4}[\\s-]?\\(?\\d{2,4}\\)?[\\s-]?\\d{3,4}[\\s-]?\\d{3,4}");
                    Matcher mobileMatcher = mobilePattern.matcher(extractedText);
                    if (mobileMatcher.find()) {
                        empMobileNo = mobileMatcher.group();
                    }

                    System.out.println("Extracted Email ID: " + empEmailid);
                    System.out.println("Extracted Mobile No: " + empMobileNo);
                } else {
                    System.out.println("Error: 'text' key not found in the response.");
                    // Check for possible error or message in the response
                    if (jsonObject.has("error")) {
                        String errorMessage = jsonObject.getString("error");
                        System.out.println("Error from OCR API: " + errorMessage);
                    } else if (jsonObject.has("message")) {
                        String errorMessage = jsonObject.getString("message");
                        System.out.println("Message from OCR API: " + errorMessage);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
