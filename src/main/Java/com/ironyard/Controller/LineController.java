package com.ironyard.Controller;

import com.ironyard.Services.LineService;
import com.ironyard.data.LineItems;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Tom on 10/11/16.
 */
@RestController
public class LineController {

    LineService lineService = new LineService();

    @RequestMapping(value = "/line", method = RequestMethod.POST)
    public LineItems save (@RequestBody LineItems aLineItem){

        LineItems saved = null;
        try {
            lineService.save(aLineItem);
            saved = lineService.getItemById(aLineItem.getId());
        }

        catch (SQLException e){
            e.printStackTrace();
        }
        return saved;
    }

    /**
     * breaking down th pieces to this method
     * @param aLineItem
     * @return
     */
    /*
        Annotation              Path                        method
             |                   |                             |
             V                   V                             V
     */
    @RequestMapping(value = "/line/update", method = RequestMethod.PUT)
    /*
                    Method 'update'

             POJO    method    (Annotation to request body)
               |      name          |         POJO      reference
               v        V           |          |            |
                                    v          V            V
     */
    public  LineItems update (@RequestBody LineItems aLineItem) {
       /*
        Java object
         or 'POJO'      reference to POJO
              |         /
              |        /        set to null
              V       /   gets    /
       *///          V    V      V
        LineItems updated = null;

       /*
            try / catch method used to catch exceptions

             Call service class
                  |        It calls the update method
                  |              /
                  |             /       the reference to th LineItems POJO
        *///      V            V         V
        try { lineService.update(aLineItem);
        /*
           reference            Class reference            uses the POJO
              |    gets        /     calls get Id service  |   ------- calls Id Getter
              |     |         /         \           ______/  /
         */// V     V        V           V         V        V
            updated = lineService.getItemById(aLineItem.getId());
        }
        /*
               Catch SQL Exception    Reference
             /   |            _______/
        *///V    V           V
        catch (SQLException e){
        /*          Reference    calls printStackTrace method
              ____/     ________/
        */// V         V
             e.printStackTrace();
        }

         /*
             Returns the now populated updated
          */
        return updated;
    }
   @RequestMapping (value = "/line/{id}", method = RequestMethod.GET)
        public LineItems display (@PathVariable Integer id){
            LineItems found= null;
            try {
                found = lineService.getItemById(id);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
          return found;
   }
   @RequestMapping(value = "/list", method = RequestMethod.GET)
        public List<LineItems> list (){
       List<LineItems> all = null;

       try {
            all = lineService.getAllLineItems();
       }
       catch (SQLException e){
           e.printStackTrace();
       }
       return all;
   }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public LineItems deleteLine (@PathVariable Integer id){
           LineItems deleteOne = null;
        try {
            deleteOne= lineService.getItemById(id);
            lineService.delete(id);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return deleteOne;
    }

}


