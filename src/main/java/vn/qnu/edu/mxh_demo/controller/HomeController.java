package vn.qnu.edu.mxh_demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.qnu.edu.mxh_demo.models.User;
import vn.qnu.edu.mxh_demo.services.PostService;

@Controller
public class HomeController {
    @Autowired private PostService postService;

    @GetMapping("/")
    public String index(
            @RequestParam(required = false) Integer userId,
            HttpSession session, 
            Model model
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        var listFeed = (userId != null) ? postService.getPostsByUserId(userId) : postService.getFeed((Integer) user.getId());

        model.addAttribute("user", user);
        model.addAttribute("my_feed", listFeed);
        model.addAttribute("filterUserId", userId);

        return "index";
    }
}
