package SampleTestingMayur;

public class Sbicount {
    public static void main(String[] args) {
        int[] arr = { 1, 43, 2, 23, 43, 1, 23, 1 };

        for (int i = 0; i < arr.length; i++) {

            int count = 0;
            boolean isVisited = false;

            // check if already counted
            for (int k = 0; k < i; k++) {
                if (arr[i] == arr[k]) {
                    isVisited = true;
                    break;
                }
            }

            if (isVisited) {
                continue;
            }

            // count frequency
            for (int j = 0; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    count++;
                }
            }

            System.out.println(arr[i] + " --> " + count);
        }
    }
}
















//package SampleTestingMayur;
//
//public class Sbicount {
//	public static void main(String[] args) {
//		int[] arr = { 1, 43, 2, 23, 43, 1, 23, 1 };
//
//		for (int i = 0; i < arr.length; i++)
//
//		{
//			int count = 0;
//			for (int j = 0; j < arr.length; j++) {
//				if (arr[i] == arr[j]) {
//					count++;
//
//				}
//
//			}
//			System.out.println(arr[i] + "-->" + count);
//		}
//	}
//
//}
