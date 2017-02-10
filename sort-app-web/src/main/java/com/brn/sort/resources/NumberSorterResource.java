package com.brn.sort.resources;

import com.brn.sort.resources.dto.SortResultDto;
import com.brn.sort.service.SortingService;
import com.brn.sort.service.db.entity.SortResult;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Strings;
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
        try {
            int[] unsortedNumbers = extractNumbersFromCSV(csvNumbersList);
            SortResult sortResult = sortingService.sortNumbers(unsortedNumbers);
            SortResultDto result = SortResultDto.from(sortResult);
            return Response.ok()
                    .entity(result)
                    .build();
        } catch (Exception e) { //look at using dropwizard exception mappers or catching specific exceptions
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }


    private int[] extractNumbersFromCSV(String csvNumbersList) {
        validate(csvNumbersList);
        final String[] numbersStringArray = csvNumbersList.split(",");
        return Arrays.stream(numbersStringArray)
                .mapToInt(Integer::valueOf)
                .toArray();
    }

    //TODO: More Validation cases need to be implemented
    private void validate(String csvNumbersList) {
        if (Strings.isNullOrEmpty(csvNumbersList)) {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    @GET
    @Timed
    @UnitOfWork
    public Response getSortedNumbers() {
        //TODO: Need to enable pagination
        final List<SortResultDto> results = sortingService.findAllSortNumberResults()
                .stream()
                .map(SortResultDto::from)
                .collect(Collectors.toList());
        return Response.ok().entity(results).build();
    }
}
