package com.jeanbarcellos.ms.organization.client;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jeanbarcellos.ms.organization.entities.Employee;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface EmployeeClient {

    @GET
    @Path("/organization/{organizationId}")
    public List<Employee> getByOrganization(@PathParam("organizationId") Long organizationId);

}
