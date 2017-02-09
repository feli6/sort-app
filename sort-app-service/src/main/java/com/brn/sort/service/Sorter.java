package com.brn.sort.service;

public interface Sorter {

    /**
     * Sorts input numbers in ascending order.
     *
     * @param input numbers to sort
     * @return SorterResult result
     */
    SorterResult sort(int[] input);

    class SorterResult {
        private final int[] sortedNumbers;
        private final int positionChanges;
        private final long sortingDuration;

        /***
         * Creates sorting result
         * @param sortedNumbers Sorted numbers in ascending order
         * @param positionChanges number of position changes required during sorting
         * @param sortingDuration sorting duration in nano seconds
         */
        public SorterResult(int[] sortedNumbers, int positionChanges, long sortingDuration) {
            this.sortedNumbers = sortedNumbers;
            this.positionChanges = positionChanges;
            this.sortingDuration = sortingDuration;
        }

        public int[] getSortedNumbers() {
            return sortedNumbers;
        }

        public int getPositionChanges() {
            return positionChanges;
        }

        /**
         * SortResult duration is ascending order
         * @return
         */
        public long getSortingDuration() {
            return sortingDuration;
        }
    }
}
