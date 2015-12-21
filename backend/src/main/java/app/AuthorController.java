package app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: Aharon
 * Date: 12/11/13
 * Time: 4:37 PM
 */
@Controller
public class AuthorController {

    @RequestMapping(value = "/authors",method = RequestMethod.GET)
    @ResponseBody
    public  String index() {
        return "this is authors";
    }

    @RequestMapping( value = "/list/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String list(){
        return "this is authors list";
    }
}
