package com.brn.sort.service;


import com.brn.sort.service.entity.SortResult;
import com.brn.sort.service.dao.SortResultDao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortingService {

    private final Sorter sorter;
    private final SortResultDao dao;

    @javax.inject.Inject
    public SortingService(Sorter sorter, SortResultDao dao) {
        this.sorter = sorter;
        this.dao = dao;
    }

    public SortResult sortNumbers(int[] inputNumbers) {
        Sorter.SorterResult sorterResult = sorter.sort(Arrays.copyOf(inputNumbers, inputNumbers.length));
        return dao.create(toSortResult(inputNumbers, sorterResult));
    }

    public List<SortResult> findAllSortResults() {
        return dao.findAll();
    }

    /**
     * Converts to entity SortResult
     *
     * @param numbers
     * @param sorterResult
     * @return
     */
    private SortResult toSortResult(int[] numbers, Sorter.SorterResult sorterResult) {
        return new SortResult(toCsv(numbers), toCsv(sorterResult.getSortedNumbers()), sorterResult.getSortingDuration(), sorterResult.getPositionChanges());
    }

    private static String toCsv(int[] array) {
        return Arrays.stream(array)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
