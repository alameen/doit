/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.doit.business.reminders.boundary;

import com.airhacks.doit.business.reminders.entity.ToDo;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author aim
 */
@Stateless
@Path("todos")
public class TodosResource {

    @Inject
    TodoManager manager;

    @GET
    @Path("{id}")
    public ToDo find(@PathParam("id") long id) {
        return manager.findById(id);
    }

    @GET
    public List<ToDo> all() {
        return manager.all();
    }

    @POST
    public Response save(ToDo toDo, @Context UriInfo info) {
        ToDo saved = this.manager.save(toDo);
        long id = saved.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        manager.delete(id);
    }
}
