package SampleTestingMayur;

import java.util.Scanner;

public class ReverseStringChatGPT {

	public static void main(String[] args) {
		
		Scanner sc= new Scanner(System.in);
		
		System.out.println("Enter String:");
		String input= sc.nextLine();
		
		String reversed= new StringBuilder(input).reverse().toString();
		
		System.out.println("Reversed String:"+reversed);
	}
}
