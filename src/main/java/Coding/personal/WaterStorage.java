package Coding.personal;

public class WaterStorage {

    public static void main(String[] args) {
        calculate();
    }

    static void calculate(){

//        Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
//        Output: 6

        int[] heights = new int[] { 4, 2, 0, 3, 2, 5 };
//        int[] heights = new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
//        Output: 6

        int volume = 0;

        for (int i = 0; i < heights.length; i++) {

            if (heights[i] == 0) { continue; }

//            find next element either equal / more / less
            int subLength = 1;
            while ((i + subLength) < heights.length
                    && heights[i + subLength] < heights[i]){
                subLength++;
            }

            if (i + subLength >= heights.length) {
                continue;
            }

            int unitsPerIndex = Math.min(heights[i], heights[i + subLength]);

            for (int j = i + 1; j < (i + subLength); j++) {
                int unitAtIndex = unitsPerIndex - heights[j];
                volume = volume + unitAtIndex;
            }

            i = i + subLength - 1;
        }

        System.out.println("Total volume = " + volume);
    }
}
