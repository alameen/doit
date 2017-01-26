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
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author aim
 */
@Stateless
@Path("todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
        System.out.println("created: " + uri);
        return Response.created(uri).build();
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        manager.delete(id);
    }
    
    @PUT
    @Path("{id}")
    public ToDo update(@PathParam("id") long id, ToDo todo) {
        todo.setId(id);
        return manager.save(todo);
    }
    
    @PUT
    @Path("{id}/status")
    public Response update(@PathParam("id") long id, JsonObject statusUpdate) {
        if (!statusUpdate.containsKey("done")) {
            Response.status(Response.Status.BAD_REQUEST)
                    .header("Reason", "JSON should contain field done")
                    .build();
        }
        
        boolean done = statusUpdate.getBoolean("done");
        ToDo toDo = manager.updateStatus(id, done);
        
        if (toDo == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Reason", "Todo with id " + id + " does not exist")
                    .build();
        }
        
        return Response.ok(toDo).build();
    }    
}
