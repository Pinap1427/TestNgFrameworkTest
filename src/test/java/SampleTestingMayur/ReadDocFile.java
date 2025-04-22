package SampleTestingMayur;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadDocFile {

    public static void main(String[] args) throws TikaException {
        String filePath = "C:\\Users\\Mayur More\\Downloads\\Mayur Moredocfile.doc"; // Update the file path
        String empEmailid = "";
        String empMobileNo = "";

        try {
            Tika tika = new Tika();
            String text = tika.parseToString(new File(filePath));

            System.out.println("DOC Content:\n" + text);

            // Extract email
            Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
            Matcher emailMatcher = emailPattern.matcher(text);
            if (emailMatcher.find()) {
                empEmailid = emailMatcher.group();
            }

            // Extract global mobile numbers (with or without country code)
            Pattern mobilePattern = Pattern.compile("\\+?\\d{1,4}[-\\s]?\\(?\\d{1,4}\\)?[-\\s]?\\d{1,4}[-\\s]?\\d{1,4}");
            Matcher mobileMatcher = mobilePattern.matcher(text);
            if (mobileMatcher.find()) {
                empMobileNo = mobileMatcher.group();
            }

            System.out.println("Extracted Email ID: " + empEmailid);
            System.out.println("Extracted Mobile No: " + empMobileNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
