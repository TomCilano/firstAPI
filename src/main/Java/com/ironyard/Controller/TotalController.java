package com.ironyard.Controller;

import com.ironyard.Services.LineService;
import com.ironyard.data.Total;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Tom on 10/11/16.
 */
@RestController
public class TotalController {

    public LineService lineService = new LineService();

    @RequestMapping(value = "/total", method = RequestMethod.GET)
    public  List<Total> totalList(){
        List<Total> list = null;
      try {
          list = lineService.getTotals();
      }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }


}
