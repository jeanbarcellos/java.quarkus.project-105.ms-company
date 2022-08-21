package com.jeanbarcellos.ms.client;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.jeanbarcellos.ms.entities.Department;

@Path("/departments")
@RegisterRestClient(configKey = "department-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface DepartmentClient {

    @GET
    @Path("/organization/{organizationId}")
    List<Department> getByOrganization(@PathParam("organizationId") Long organizationId);

    @GET
    @Path("/organization/{organizationId}/with-employees")
    List<Department> getByOrganizationWithEmployees(@PathParam("organizationId") Long organizationId);

}