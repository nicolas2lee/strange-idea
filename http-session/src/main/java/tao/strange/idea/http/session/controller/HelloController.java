package tao.strange.idea.http.session.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class HelloController {

    @GetMapping("/test")
    public String test(HttpServletRequest request){
        final HttpSession session = request.getSession();
        final String sessionId = session.getId();
        Integer count = (Integer) session.getAttribute("count");
        if (count == null) count = 0;
        else count++;
        session.setAttribute("count", count);
        final String message = String.format("The session id is: %s, with count: %d", sessionId, count);
        System.out.println(message);
        return message;
    }
}
