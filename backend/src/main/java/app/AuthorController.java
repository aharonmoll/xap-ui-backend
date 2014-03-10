package app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: guym
 * Date: 12/11/13
 * Time: 4:37 PM
 */
@Controller
@RequestMapping("/authors")
public class AuthorController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public  String index() {
        return "this is authors";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/{id}")
    @ResponseBody
    public String list(){
        return "this is authors list";
    }
}
