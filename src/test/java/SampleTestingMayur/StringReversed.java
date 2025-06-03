package SampleTestingMayur;

public class StringReversed {
	
	public static void main(String[] args) {
		
		String Original= "MayurMore";
		
		String Reversed= "";
		
		for(int i=Original.length()-1;i>=0; i--)
		{
			Reversed +=Original.charAt(i);
		}
		System.out.println("Orginial String:"+ Original);
		
		System.out.println("Reversed String:"+ Reversed);
	}

}
