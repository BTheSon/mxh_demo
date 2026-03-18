package vn.qnu.edu.mxh_demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.qnu.edu.mxh_demo.models.User;
import vn.qnu.edu.mxh_demo.services.PostService;

@Controller
public class HomeController {
    @Autowired private PostService postService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        var listFeed = postService.getPostsByUserId((Integer) user.getId());

        model.addAttribute("user", user);
        model.addAttribute("my_feed", listFeed);

        return "index";
    }
}
