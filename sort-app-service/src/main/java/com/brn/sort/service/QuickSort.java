package com.brn.sort.service;

/**
 * Implements quicksort for sorting input numbers.
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
        int pivot = input[low + (high - low) / 2];

        while (i <= j) {
            while (input[i] < pivot) {
                i++;
            }
            while (input[j] > pivot) {
                j--;
            }

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
