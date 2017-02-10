package com.brn.sort.resources.dto;


import com.brn.sort.service.db.entity.SortResult;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer object for the web layer. Mainly used for serialization and deserialization from/to json.
 */
public class SortResultDto {
    private String inputNumbers;
    private String sortResult;
    private long sortingDuration;
    private int numberOfPositionChanges;

    public SortResultDto() {
        // Jackson deserialization
    }

    public SortResultDto(String inputNumbers, String sortResult, long sortingDuration, int numberOfPositionChanges) {
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

    public static SortResultDto from(SortResult sortResult) {
        return new SortResultDto(sortResult.getSortingInput(), sortResult.getSortingOutput(), sortResult.getSortingDuration(), sortResult.getPositionChanges());
    }
}
