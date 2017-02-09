package com.brn.sort.resources;

import com.brn.sort.resources.dto.SortingResult;
import com.brn.sort.service.SortingService;
import com.brn.sort.service.db.entity.SortResult;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sortNumbers")
@Produces(MediaType.APPLICATION_JSON)
public class NumberSorterResource {

    private final SortingService sortingService;

    @Inject
    public NumberSorterResource(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    @UnitOfWork
    public Response sort(String csvNumbersList) {
        //TODO: Send error code on exceptions etc.
        int[] unsortedNumbers = extractNumbersFromCSV(csvNumbersList);
        SortResult sortResult = sortingService.sortNumbers(unsortedNumbers);
        SortingResult result = SortingResult.from(sortResult);
        return Response.ok()
                .entity(result)
                .build();
    }

    //TODO: Validation
    private int[] extractNumbersFromCSV(String csvNumbersList) {
        String[] numbersStringArray = csvNumbersList.split(",");
        return Arrays.stream(numbersStringArray).mapToInt(Integer::valueOf).toArray();
    }

    @GET
    @Timed
    @UnitOfWork
    public Response getSortedNumbers() {
        //TODO: Need to enable pagination
        List<SortingResult> results = sortingService.findAllSortNumberResults()
                .stream()
                .map(SortingResult::from)
                .collect(Collectors.toList());
        return Response.ok().entity(results).build();
    }
}
