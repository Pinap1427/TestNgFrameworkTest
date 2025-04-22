package SampleTestingMayur;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.testng.internal.protocols.Input;

import java.util.Arrays;

public class ArrayInt {
    public static String compressString(String str) {
        StringBuilder result = new StringBuilder();
        int count = 1;

        for (int i = 0; i < str.length(); i++) {
        	System.out.println(str.length());
            // Check if next character is the same
            if (i < str.length() - 1 && str.charAt(i) == str.charAt(i + 1)) {
                count++;
            } else {
                result.append(str.charAt(i)).append(count);
                count = 1; // Reset count for the next character
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String str = "aaaabbbccdddeeffggggggggggghhhhhhhhhhhhiiiiiiiiiiiiiijjjjjjjjjjkkkkkkkkklllllllllllllllllllllllllllmmmmmmmmmmnoopqqrrrrrrssssssssttuuuuvvvvwwwxxxxyyyyyzzz";
//        String str="aabbbbccd";
        String compressed = compressString(str);
        System.out.println(compressed);
    }
}
