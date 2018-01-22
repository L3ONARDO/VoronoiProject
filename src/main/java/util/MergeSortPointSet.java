package util;

import model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Sorts a {@link Set<Point>} {@code points} on x-coordinates.
 * Based on http://www.vogella.com/tutorials/JavaAlgorithmsMergesort/article.html
 */
public class MergeSortPointSet {
    private Point[] points;
    private Point[] helper;

    private int number;

    public List<Point> sort(List<Point> points, boolean sortY) {
        List<Point> result = new ArrayList<Point>();
        this.points = new Point[points.size()];
        int i = 0;
        for (Point p : points) {
            this.points[i] = p;
            i++;
        }
        number = points.size();
        this.helper = new Point[number];
        mergesort(0, number - 1, sortY);
        for (Point p : this.points) {
            result.add(p);
        }
        return result;
    }

    public List<Point> sort(Set<Point> points, boolean sortY) {
        List<Point> result = new ArrayList<Point>();
        this.points = new Point[points.size()];
        int i = 0;
        for (Point p : points) {
            this.points[i] = p;
            i++;
        }
        number = points.size();
        this.helper = new Point[number];
        mergesort(0, number - 1, sortY);
        for (Point p : this.points) {
            result.add(p);
        }
        return result;
    }

    private void mergesort(int low, int high, boolean sortY) {
        // check if low is smaller than high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
            // Sort the left side of the array
            mergesort(low, middle, sortY);
            // Sort the right side of the array
            mergesort(middle + 1, high, sortY);
            // Combine them both
            merge(low, middle, high, sortY);
        }
    }

    private void merge(int low, int middle, int high, boolean sortY) {
        // Copy both parts into the helper array
        for (int i = low; i <= high; i++) {
            helper[i] = points[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high) {
            if (sortY) {
                if (helper[i].getY() <= helper[j].getY()) {
                    points[k] = helper[i];
                    i++;
                } else {
                    points[k] = helper[j];
                    j++;
                }
            } else {
                if (helper[i].getX() <= helper[j].getX()) {
                    points[k] = helper[i];
                    i++;
                } else {
                    points[k] = helper[j];
                    j++;
                }
            }
            k++;
        }
        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
            points[k] = helper[i];
            k++;
            i++;
        }
        // Since we are sorting in-place any leftover elements from the right side
        // are already at the right position.
    }
}
