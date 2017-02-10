package com.brn.sort.resources;

import com.brn.sort.App;
import com.brn.sort.SortAppConfiguration;
import com.brn.sort.resources.dto.SortResultDto;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberSorterAcceptanceTest {

    @ClassRule
    public static final DropwizardAppRule<SortAppConfiguration> RULE =
            new DropwizardAppRule<>(App.class, ResourceHelpers.resourceFilePath("sort-app-it.yaml"));

    @Test
    @Ignore //Enable when DB already exists
    public void shouldSaveSortingResult() {
        Client client = JerseyClientBuilder.newClient();

        Response response = client.target(
                String.format("http://localhost:%d/sortNumbers", RULE.getLocalPort()))
                .request()
                .post(Entity.text("15,19,6,2,3"));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        SortResultDto sortResultDto = response.readEntity(SortResultDto.class);
        assertThat(sortResultDto).isNotNull();
        assertThat(sortResultDto.getNumberOfPositionChanges()).isNotNull();
        assertThat(sortResultDto.getSortingDuration()).isGreaterThan(0);
        assertThat(sortResultDto.getSortResult()).isEqualTo("2,3,6,15,19");
        assertThat(sortResultDto.getInputNumbers()).isEqualTo("15,19,6,2,3");
    }

    @Test
    @Ignore //Enable when DB already exists
    public void shouldReturnErrorCodeWhenBadInput() {
        Client client = JerseyClientBuilder.newClient();

        Response response = client.target(
                String.format("http://localhost:%d/sortNumbers", RULE.getLocalPort()))
                .request()
                .post(Entity.text(""));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @Ignore //Enable when DB already exists
    public void shouldFetchAllSortingResults() {
        Client client = JerseyClientBuilder.newClient();
        Response response = client.target(
                String.format("http://localhost:%d/sortNumbers", RULE.getLocalPort()))
                .request()
                .get();

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        List<SortResultDto> sortResultDto = response.readEntity(new GenericType<List<SortResultDto>>(){});
        assertThat(sortResultDto).isNotNull();
        assertThat(sortResultDto).isNotEmpty();
    }
}
