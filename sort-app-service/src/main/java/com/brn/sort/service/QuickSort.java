package com.brn.sort.service;

/**
 * Implements quicksort for sorting inout numbers.
 */
public class QuickSort implements Sorter {

    public SorterResult sort(int[] input) {
        validate(input);

        final long startTime = System.nanoTime();
        final int positionChanges = quicksort(0, input.length - 1, input, 0);
        final long completionTime = System.nanoTime();
        final long sortingDurationInNanoSeconds = completionTime - startTime;

        return new SorterResult(input, positionChanges, sortingDurationInNanoSeconds);
    }

    private void validate(int[] input) {
        if (input == null || input.length == 0) {
            throw new IllegalArgumentException("Input array shouldn't be empty or null");
        }
    }


    private int quicksort(int low, int high, int[] input, int positionChanges) {
        int i = low, j = high;
        // Get the pivot element from the middle of the list
        int pivot = input[low + (high - low) / 2];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list
            while (input[i] < pivot) {
                i++;
            }
            // If the current value from the right list is larger then the pivot
            // element then get the next element from the right list
            while (input[j] > pivot) {
                j--;
            }

            // If we have found a values in the left list which is larger then
            // the pivot element and if we have found a value in the right list
            // which is smaller then the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(i, j, input);
                i++;
                j--;
                positionChanges = positionChanges + 2;
            }
        }
        // Recursion
        if (low < j) {
            positionChanges =  quicksort(low, j, input, positionChanges);
        }

        if (i < high) {
            positionChanges = quicksort(i, high, input, positionChanges);
        }
        return positionChanges;
    }

    private void exchange(int i, int j, int[] input) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }
}
