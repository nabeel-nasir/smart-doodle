package com.iott.smartdoodle;

import java.util.List;

public class Utils {

    /**
     * Return a 40x40 array from the 400x400 array
     * @param touchPoints set of x,y coordinates
     * @return scaled down touch points with image move to top left
     */
    public static float[][] getSimplifiedTouchPoints(List<int[]> touchPoints) {
        //reposition the drawing to the top-left corner to have min values of 0
        int xMin = 400, yMin = 400;
        for(int[] point : touchPoints) {
            if(point[0] < xMin)
                xMin = point[0];
            if(point[1] < yMin)
                yMin = point[1];
        }
        float[][] remappedPoints = new float[40][40];
        for(int[] point : touchPoints) {
            int x = point[0];
            int y = point[1];

            x = (x - xMin) / 10;
            y = (y - yMin) / 10;

            //flip (x,y) since the android screen follows 3rd quadrant
            remappedPoints[y][x] = 1;
        }
        return remappedPoints;
    }
}
