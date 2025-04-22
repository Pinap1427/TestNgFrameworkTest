package SampleTestingMayur;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.*;

public class EmailExtractor {

	public static void main(String[] args) {
		String filePath = "C:\\Users\\Mayur More\\Downloads\\Naukri_BhushanE.Chaudhari[14y_0m].docx";
		try (FileInputStream fis = new FileInputStream(filePath); XWPFDocument document = new XWPFDocument(fis)) {

			// Combine all paragraphs into one text block
			StringBuilder text = new StringBuilder();
			for (XWPFParagraph para : document.getParagraphs()) {
				text.append(para.getText()).append("\n");
			}

			// Regex pattern for email extraction
			String emailPattern = "(?i)(?:email\\s*id[:\\s]*)([\\w.-]+@[\\w.-]+\\.\\w+)";
			Pattern pattern = Pattern.compile(emailPattern);
			Matcher matcher = pattern.matcher(text.toString());

			if (matcher.find()) {
				System.out.println("Email found: " + matcher.group(1));
			} else {
				System.out.println("Email not found.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
