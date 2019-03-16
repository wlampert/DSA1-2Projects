import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.Comparator;
import java.util.Arrays;

public class ClosestPair {

    public double getDistance(Point p1, Point p2) {
        return  Math.sqrt(Math.pow( p1.getX()-p2.getX(), 2.0) + Math.pow(p1.getY()-p2.getY(), 2));
    }

    private static void merge(Point[] list, int left, int midIndex, int right) {
        int s1 = midIndex - left +1;
        int s2 = right - midIndex;
        Point[] leftList = new Point[s1];
        Point[] rightList =  new Point[s2];
        for (int i=0; i<s1; ++i)
            leftList[i] = list[left + i];
        for (int j=0; j<s2; ++j)
            rightList[j] = list[midIndex + 1+ j];
        int i = 0, j = 0;
        int k = left;
        while (i < s1 && j < s2)
        {
            if ((leftList[i].getY() - rightList[j].getY()) <= 0)
            {
                list[k] = leftList[i];
                i++;
            }
            else
            {
                list[k] = rightList[j];
                j++;
            }
            k++;
        }

        while (i < s1)
        {
            list[k] = leftList[i];
            i++;
            k++;
        }

        while (j < s2)
        {
            list[k] = rightList[j];
            j++;
            k++;
        }
    }


    public double closestPairDistance(List<String> fileData) {

        //First, convert the input into point objects and store them in a vector
        Point[] allPoints = new Point[fileData.size()-1];
        int index = 0;
        for(String string: fileData) {
            String[] tempArray = string.split("\\s+");
            if(tempArray.length==2) {
                int x = Integer.parseInt(tempArray[0]);
                int y = Integer.parseInt(tempArray[1]);
                Point temp = new Point(x, y);
                allPoints[index] = (temp);
                index++;
            }
        }

        //Then sort them by x-value
        Arrays.sort(allPoints, new Comparator<Point>() {
            public int compare(Point p1, Point p2) {
                return (int) (p1.getX() - p2.getX());
            }
        });

        //Then call the helper function that will do all of the work
        return closestPairDistance2(allPoints);
    }


    private double closestPairDistance2(Point[] allPoints) {

        /**
         First Number is the number of coordinates

         Sort array by x coordinates - done

        Divide at median x coordinate P[n/2] - done

        Keep doing this until there is only two points, then you know those are the two closest points in the group,
         put them in y order and then merge together with the other half so its sorted by y - done

        Dl and Dr are the minimum distances found in the left and right subhalves - done

        Take the smaller of the two distances - done

        Find all the coordinates that are within d of P[n/2] and add to a new array. x coordinate must be within d - done

        Sort this new array by y coordinates - done

        Compare the points in the array to the next 15 points above it

         if(only two points)
            return distance
         **/

        double minDistance = 0;


        //Base Case: when you only have two or three points
        if(allPoints.length == 2) {
            Arrays.sort(allPoints, new Comparator<Point>() {
                public int compare(Point p1, Point p2) {
                    return (int) (p1.getY() - p2.getY());
                }
            });
            return getDistance(allPoints[0], allPoints[1]);
        }
        else if(allPoints.length == 3) {
            minDistance = getDistance(allPoints[0], allPoints[1]);
            if(getDistance(allPoints[0], allPoints[2]) < minDistance)
                minDistance = getDistance(allPoints[0], allPoints[2]);
            if(getDistance(allPoints[1], allPoints[2]) < minDistance)
                minDistance = getDistance(allPoints[1], allPoints[2]);
            Arrays.sort(allPoints, new Comparator<Point>() {
                public int compare(Point p1, Point p2) {
                    return (int) (p1.getY() - p2.getY());
                }
            });
            return minDistance;

        }

        else {
            //Now, divide the list into two at the medium x coordinate, and recursively call to get the min distance
            //for the two halves. If the list size is odd, want to include the medium coordinate in both sublists.
            //If the list size is even, then want to split it evenly
            int medCoordIndex = allPoints.length / 2;
            double medXCoordinate = 0;
            double leftMin;
            double rightMin;
            if(allPoints.length % 2 != 0) {
                medXCoordinate = allPoints[medCoordIndex].getX();
                leftMin = closestPairDistance2(Arrays.copyOfRange(allPoints, 0, medCoordIndex+1));
                rightMin = closestPairDistance2(Arrays.copyOfRange(allPoints, medCoordIndex, allPoints.length));
                merge(allPoints, 0, medCoordIndex, allPoints.length-1);
            }
            else {

                medXCoordinate = (allPoints[medCoordIndex].getX() + allPoints[medCoordIndex-1].getX())/2;
                leftMin = closestPairDistance2(Arrays.copyOfRange(allPoints, 0, medCoordIndex));
                rightMin = closestPairDistance2(Arrays.copyOfRange(allPoints, medCoordIndex, allPoints.length));
                merge(allPoints, 0, medCoordIndex, allPoints.length-1);
            }

            //Now we have the minimum value for the right and left subhalves, so we can get the delta value
            double delta = Math.min(leftMin, rightMin);
            minDistance = delta;

            //Now we create the strip of coordinates that are within the delta of the medium x coordinate
            Vector<Point> strip = new Vector<>();
            for(Point p: allPoints) {
                if(Math.abs(p.getX()- medXCoordinate) < delta) {
                    strip.add(p);
                }
            }


            for(int i = strip.size()-1; i > 0 ; i--) {
                int count = 1;
                Point p = strip.elementAt(i);
                while(count <= 15 & i - count >= 0) {
                    double x = getDistance(p, strip.get(i-count));
                    if(x < minDistance)
                        minDistance = x;
                    count++;
                }

            }


        }

        return minDistance;
    }

}
