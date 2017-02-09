package com.brn.sort.service;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuickSortTest {

    @Test
    public void shouldSortNumbersInAscendingOrder() {
        QuickSort sorter = new QuickSort();
        Sorter.SorterResult result = sorter.sort(new int[]{5, 4, 3, 2, 1});
        assertThat(result.getSortedNumbers()).isNotNull();
        assertThat(result.getSortedNumbers()).containsExactly(1, 2, 3, 4, 5);
        assertThat(result.getPositionChanges()).isEqualTo(8);
        assertThat(result.getSortingDuration()).isGreaterThan(0);
    }

    @Test
    public void shouldSortNumbersInAscendingOrder2() {
        QuickSort sorter = new QuickSort();
        Sorter.SorterResult result = sorter.sort(new int[]{2,3,6,19,15});
        assertThat(result.getSortedNumbers()).isNotNull();
        assertThat(result.getSortedNumbers()).containsExactly(2,3,6,15,19);
        assertThat(result.getSortingDuration()).isGreaterThan(0);
    }

    //TODO: more tests to follow



}