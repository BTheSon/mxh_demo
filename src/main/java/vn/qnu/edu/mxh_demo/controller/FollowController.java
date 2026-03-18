package vn.qnu.edu.mxh_demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.qnu.edu.mxh_demo.models.User;
import vn.qnu.edu.mxh_demo.services.FollowService;

@Controller
public class FollowController {

    @Autowired private FollowService followService;

    @GetMapping("/followings")
    public String getFollowings(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        var listFollowings = followService.getFollowings((Integer) user.getId());
        model.addAttribute("followings", listFollowings);
        model.addAttribute("user", user);
        
        return "followings";
    }
}
