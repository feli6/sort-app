package com.brn.sort.resources;

import com.brn.sort.resources.dto.SortingResult;
import com.brn.sort.service.SortingService;
import com.brn.sort.service.db.entity.SortResult;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NumberSorterResourceTest {

    private static SortingService sortingService =  mock(SortingService.class);


    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new NumberSorterResource(sortingService))
            .build();


    @Before
    public void setup() {
        when(sortingService.sortNumbers(any())).thenReturn(new SortResult("1,2,3,4", "1,2,3,4", 1234, 1));
    }

    @After
    public void tearDown(){
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        Mockito.reset(sortingService);
    }

    @Test
    public void shouldSuccessfullySortNumbers() {
        Response response = resources.client().target("/sortNumbers").request().buildPost(Entity.text("4,5,6,7,2")).invoke();
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        SortingResult sortingResult = response.readEntity(SortingResult.class);
        assertThat(sortingResult).isNotNull();
    }
}