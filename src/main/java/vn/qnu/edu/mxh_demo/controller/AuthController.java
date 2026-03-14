package vn.qnu.edu.mxh_demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.qnu.edu.mxh_demo.services.UserService;

@Controller
public class AuthController {

    @Autowired private UserService userService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        var user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        model.addAttribute("err", "Sai tên đăng nhập hoặc mật khẩu");
        return "login";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        if (username.isBlank() || password.isBlank()) {
            model.addAttribute("err", "Vui lòng nhập đầy đủ thông tin");
            return "register";
        }
        if (userService.register(username, password)) {
            return "redirect:/login";
        }
        model.addAttribute("err", "Tên đăng nhập đã tồn tại");
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
