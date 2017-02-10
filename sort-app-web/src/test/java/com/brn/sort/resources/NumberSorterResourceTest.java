package com.brn.sort.resources;

import com.brn.sort.resources.dto.SortResultDto;
import com.brn.sort.service.SortingService;
import com.brn.sort.service.db.entity.SortResult;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NumberSorterResourceTest {

    private static SortingService sortingService =  mock(SortingService.class);


    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new NumberSorterResource(sortingService))
            .build();

    @After
    public void tearDown(){
        // we have to reset the mock after each test because of the
        Mockito.reset(sortingService);
    }

    @Test
    public void shouldSuccessfullySortNumbers() {
        when(sortingService.sortNumbers(any())).thenReturn(new SortResult("1,2,3,4", "1,2,3,4", 1234, 1));
        Response response = resources.client().target("/sortNumbers").request().buildPost(Entity.text("4,5,6,7,2")).invoke();
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        SortResultDto sortResultDto = response.readEntity(SortResultDto.class);
        assertThat(sortResultDto).isNotNull();
    }

    @Test
    public void shouldReturnErrorCodeWhenEmptyInput() {
        Response response = resources.client().target("/sortNumbers").request().buildPost(Entity.text("")).invoke();
        assertThat(response).isNotNull();
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnErrorCodeWhenBadInput() {
        Response response = resources.client().target("/sortNumbers").request().buildPost(Entity.text("sfsdg")).invoke();
        assertThat(response).isNotNull();
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnSortResult() {
        when(sortingService.findAllSortNumberResults()).thenReturn(Arrays.asList(new SortResult("1,2,3,4", "1,2,3,4", 1234, 1)));
        Response response = resources.client().target("/sortNumbers").request().get();
        assertThat(response).isNotNull();
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        List<SortResultDto> sortResultDto = response.readEntity(new GenericType<List<SortResultDto>>(){});
        assertThat(sortResultDto).isNotNull();
        assertThat(sortResultDto).isNotEmpty();
    }
}