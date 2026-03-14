package vn.qnu.edu.mxh_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.qnu.edu.mxh_demo.models.Follow;
import vn.qnu.edu.mxh_demo.models.FollowRepo;
import vn.qnu.edu.mxh_demo.models.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FollowService {

    @Autowired
    private FollowRepo followRepo;

    public List<User> getFollowings(Integer followerId) {
        return followRepo.findFollowingUsers(followerId);
    }

    public List<User> searchUsers(Integer currentUserId, String query) {
        return followRepo.searchUsersToFollow(currentUserId, query);
    }

    public boolean follow(Integer followerId, Integer followedId) {
        if (followerId.equals(followedId)) return false;
        if (followRepo.isFollowing(followerId, followedId)) return false;

        Follow follow = new Follow();
        follow.setFollowing_user_id(followerId);
        follow.setFollowed_user_id(followedId);
        follow.setCreated_at(LocalDateTime.now());
        return followRepo.save(follow) > 0;
    }

    public boolean unfollow(Integer followerId, Integer followedId) {
        return followRepo.deleteFollow(followerId, followedId) > 0;
    }
}