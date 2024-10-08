package com.jeanbarcellos.project105.department.resources;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import com.jeanbarcellos.project105.department.entities.Department;
import com.jeanbarcellos.project105.department.services.DepartmentService;

@Path("/departments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    @Inject
    DepartmentService service;

    @GET
    public Response getAll() {
        return Response.ok(this.service.getAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(this.service.getById(id)).build();
    }

    @GET
    @Path("/organization/{organizationId}")
    public Response getyByOrganization(@PathParam("organizationId") Long organizationId) {
        return Response.ok(this.service.getByOrganization(organizationId)).build();
    }

    @Path("/organization/{organizationId}/with-employees")
    @GET
    public Response getByOrganizationWithEmployees(@PathParam("organizationId") Long organizationId) {
        return Response.ok(this.service.getByOrganizationWithEmployees(organizationId)).build();
    }

    @POST
    @Path("/")
    @Transactional
    public Response insert(@RequestBody Department department) {
        return Response.ok(this.service.insert(department)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @RequestBody Department department) {
        return Response.ok(this.service.update(department.setId(id))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        this.service.delete(id);
        return Response.noContent().build();
    }

}
