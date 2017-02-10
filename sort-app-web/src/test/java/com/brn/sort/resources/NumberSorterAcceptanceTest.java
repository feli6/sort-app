package com.brn.sort.resources;

import com.brn.sort.App;
import com.brn.sort.SortAppConfiguration;
import com.brn.sort.resources.dto.SortResultDto;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberSorterAcceptanceTest {


    private static final String TMP_FILE = createTempFile();
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("sort-app-it.yaml");

    @ClassRule
    public static final DropwizardAppRule<SortAppConfiguration> RULE = new DropwizardAppRule<>(
            App.class, CONFIG_PATH,
            ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE));

    @BeforeClass
    public static void migrateDb() throws Exception {
        RULE.getApplication().run("db", "migrate", CONFIG_PATH);
    }

    private static String createTempFile() {
        try {
            return File.createTempFile("test-example", null).getAbsolutePath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    @Test
    public void shouldSortValidNumericValues() {
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
    public void shouldProcessInputWithSpaces() {
        Client client = JerseyClientBuilder.newClient();

        Response response = client.target(
                String.format("http://localhost:%d/sortNumbers", RULE.getLocalPort()))
                .request()
                .post(Entity.text("15, 19 ,6 , , 3,,10"));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        SortResultDto sortResultDto = response.readEntity(SortResultDto.class);
        assertThat(sortResultDto).isNotNull();
        assertThat(sortResultDto.getNumberOfPositionChanges()).isNotNull();
        assertThat(sortResultDto.getSortingDuration()).isGreaterThan(0);
        assertThat(sortResultDto.getSortResult()).isEqualTo("3,6,10,15,19");
        assertThat(sortResultDto.getInputNumbers()).isEqualTo("15,19,6,3,10");
    }

    @Test
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
    public void shouldFetchAllSortingResults() {
        Client client = JerseyClientBuilder.newClient();

        //create an entry
        client.target(
                String.format("http://localhost:%d/sortNumbers", RULE.getLocalPort()))
                .request()
                .post(Entity.text("15,19,6,2,3"));


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
