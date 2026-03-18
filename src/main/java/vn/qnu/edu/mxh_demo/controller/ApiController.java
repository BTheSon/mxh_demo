package vn.qnu.edu.mxh_demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.qnu.edu.mxh_demo.models.User;
import vn.qnu.edu.mxh_demo.services.FollowService;
import vn.qnu.edu.mxh_demo.services.PostService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired private PostService postService;
    @Autowired private FollowService followService;

    // Post APIs
    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody Map<String, String> body, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body("Unauthorized");
        
        String title = body.get("title");
        String content = body.get("body");
        
        if (postService.createPost(title, content, (Integer) user.getId())) {
            return ResponseEntity.ok(Map.of("message", "Post created"));
        }
        return ResponseEntity.badRequest().body("Failed to create post");
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Integer id, @RequestBody Map<String, String> body, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body("Unauthorized");

        String title = body.get("title");
        String content = body.get("body");

        if (postService.updatePost(id, title, content, (Integer) user.getId())) {
            return ResponseEntity.ok(Map.of("message", "Post updated"));
        }
        return ResponseEntity.badRequest().body("Failed to update post");
    }

    // Follow APIs
    @GetMapping("/followings")
    public ResponseEntity<?> searchUsers(@RequestParam String search, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body("Unauthorized");
        
        var results = followService.searchUsers((Integer) user.getId(), search);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<?> followUser(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body("Unauthorized");

        if (followService.follow((Integer) user.getId(), id)) {
            return ResponseEntity.ok(Map.of("message", "Followed"));
        }
        return ResponseEntity.badRequest().body("Failed to follow");
    }

    @DeleteMapping("/follow/{id}")
    public ResponseEntity<?> unfollowUser(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body("Unauthorized");

        if (followService.unfollow((Integer) user.getId(), id)) {
            return ResponseEntity.ok(Map.of("message", "Unfollowed"));
        }
        return ResponseEntity.badRequest().body("Failed to unfollow");
    }
}
