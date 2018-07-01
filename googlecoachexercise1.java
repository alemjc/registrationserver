import java.util.Arrays;

public class SumExists {

    public static boolean sumExists(int arr[], int target) {

        int [] tempArray = Arrays.copyOf(arr, arr.length);
        int start = 0;
        int end = arr.length -1;
        Arrays.sort(tempArray);

        while (start < end) {
            int sum = tempArray[start] + tmepArray[end];
            if (target == sum) {
                return true;
            } else if (target > sum) {
                start = start+1;
            } else {
                end = end -1;
            }

        }

        return false;
    }

    public static void main (String args[]) {

        int testExample = new int[]{5, 4, 2, 4};
        int target = 8;

        boolean itExists = sumExists(testExample, target);

        if (itExists) {
            System.out.println(String.format("target %d exists"));
        } else {
            System.out.println("target does not exists");
        }
    }


}