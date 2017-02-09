package com.brn.sort.resources.dto;


import com.brn.sort.service.db.entity.SortResult;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SortingResult {
    private String inputNumbers;
    private String sortResult;
    private long sortingDuration;
    private int numberOfPositionChanges;

    public SortingResult() {
        // Jackson deserialization
    }

    public SortingResult(String inputNumbers, String sortResult, long sortingDuration, int numberOfPositionChanges) {
        this.inputNumbers = inputNumbers;
        this.sortResult = sortResult;
        this.sortingDuration = sortingDuration;
        this.numberOfPositionChanges = numberOfPositionChanges;
    }

    @JsonProperty
    public String getSortResult() {
        return sortResult;
    }

    @JsonProperty
    public long getSortingDuration() {
        return sortingDuration;
    }

    @JsonProperty
    public int getNumberOfPositionChanges() {
        return numberOfPositionChanges;
    }

    @JsonProperty
    public String getInputNumbers() {
        return inputNumbers;
    }

    public static SortingResult from(SortResult sortResult) {
        return new SortingResult(sortResult.getSortingInput(), sortResult.getSortingOutput(), sortResult.getSortingDuration(), sortResult.getPositionChanges());
    }
}
