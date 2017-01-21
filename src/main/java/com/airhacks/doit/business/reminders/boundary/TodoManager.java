/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.doit.business.reminders.boundary;

import com.airhacks.doit.business.reminders.entity.ToDo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aim
 */
@Stateless
public class TodoManager {

    @PersistenceContext
    EntityManager em;
            
    public ToDo findById(long id) {
        return this.em.find(ToDo.class, id);
    }

   public void delete(long id) {
        ToDo reference = this.em.getReference(ToDo.class, id);
        this.em.remove(reference);
   }
   
   public List<ToDo> all() {
        return this.em.createNamedQuery(ToDo.FIND_ALL, ToDo.class).
                getResultList();
   }
   
   public ToDo save(ToDo todo) {
       return this.em.merge(todo);
   }
    
}
