package vn.qnu.edu.mxh_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.qnu.edu.mxh_demo.models.Post;
import vn.qnu.edu.mxh_demo.models.PostRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    public List<Post> getFeed() {
        return postRepo.findFeed();
    }

    public List<Post> getPostsByUserId(Integer userId) {
        return postRepo.findByUserId(userId);
    }

    public Post getPostById(Integer id) {
        return postRepo.findById(new Post(), id);
    }

    public boolean createPost(String title, String body, Integer userId) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setUser_id(userId);
        post.setStatus("published");
        post.setCreated_at(LocalDateTime.now());
        return postRepo.save(post) > 0;
    }

    public boolean updatePost(Integer id, String title, String body, Integer userId) {
        Post post = getPostById(id);
        if (post != null && post.getUser_id().equals(userId)) {
            post.setTitle(title);
            post.setBody(body);
            return postRepo.update(post) > 0;
        }
        return false;
    }

    public boolean deletePost(Integer id, Integer userId) {
        Post post = getPostById(id);
        if (post != null && post.getUser_id().equals(userId)) {
            return postRepo.delete(new Post(), id) > 0;
        }
        return false;
    }
}