package com.brn.sort.service.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = "com.brn.sort.service.db.entity.SortResult.findAll",
                        query = "SELECT r FROM SortResult r"
                )
        }
)
@Table(name = "sort_result")
public class SortResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "sortingInput", nullable = false)
    private String sortingInput;

    @Column(name = "sortingOutput", nullable = false)
    private String sortingOutput;

    @Column(name = "sortingDuration", nullable = false)
    private long sortingDuration;

    @Column(name = "positionChanges", nullable = false)
    private int positionChanges;

    public SortResult() {
    }

    public SortResult(String sortingInput, String sortingOutput, long sortingDuration, int positionChanges) {
        this.sortingInput = sortingInput;
        this.sortingOutput = sortingOutput;
        this.sortingDuration = sortingDuration;
        this.positionChanges = positionChanges;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSortingInput() {
        return sortingInput;
    }

    public void setSortingInput(String sortingInput) {
        this.sortingInput = sortingInput;
    }

    public String getSortingOutput() {
        return sortingOutput;
    }

    public void setSortingOutput(String sortingOutput) {
        this.sortingOutput = sortingOutput;
    }

    public long getSortingDuration() {
        return sortingDuration;
    }

    public void setSortingDuration(long sortingDuration) {
        this.sortingDuration = sortingDuration;
    }

    public int getPositionChanges() {
        return positionChanges;
    }

    public void setPositionChanges(int positionChanges) {
        this.positionChanges = positionChanges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SortResult that = (SortResult) o;

        if (id != that.id) return false;
        if (sortingDuration != that.sortingDuration) return false;
        if (positionChanges != that.positionChanges) return false;
        if (!sortingInput.equals(that.sortingInput)) return false;
        return sortingOutput.equals(that.sortingOutput);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + sortingInput.hashCode();
        result = 31 * result + sortingOutput.hashCode();
        result = 31 * result + (int) (sortingDuration ^ (sortingDuration >>> 32));
        result = 31 * result + (int) (positionChanges ^ (positionChanges >>> 32));
        return result;
    }
}
