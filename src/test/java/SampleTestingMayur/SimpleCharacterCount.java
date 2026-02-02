package SampleTestingMayur;

public class SimpleCharacterCount {
    public static void main(String[] args) {
        String input = "Abhishek Gowda";
        input = input.toLowerCase().replaceAll("\\s+", ""); // remove spaces and convert to lowercase

        int[] count = new int[26]; // For 26 letters a-z

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                count[ch - 'a']++; // Increment the count at position (ch - 'a')
            }
        }

        // Print characters that occurred
        System.out.println("Character frequencies:");
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                System.out.println((char)(i + 'a') + " : " + count[i]);
            }
        }
    }
}
