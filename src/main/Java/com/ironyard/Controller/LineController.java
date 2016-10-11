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

    @RequestMapping(value = "/line/update", method = RequestMethod.PUT)
    public  LineItems update (@RequestBody LineItems aLineItem) {
        LineItems updated = null;
        try {
            lineService.update(aLineItem);
            updated = lineService.getItemById(aLineItem.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


