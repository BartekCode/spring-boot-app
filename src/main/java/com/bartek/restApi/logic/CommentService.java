package com.bartek.restApi.logic;

import com.bartek.restApi.model.Comment;
import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.model.User;
import com.bartek.restApi.repository.CommentRepository;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentService {

    private final DiscoveryService discoveryService;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final DiscoveryRepository discoveryRepository;

    public CommentService(DiscoveryService discoveryService, UserService userService, CommentRepository commentRepository, DiscoveryRepository discoveryRepository) {
        this.discoveryService = discoveryService;
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.discoveryRepository = discoveryRepository;
    }

    public Comment creatComment(Comment toSave, int discoverId) {
//        public Comment creatComment(Comment toSave, int userId, int discoverId){ //wywalamy userId bo nie ma lgowania do principala
//        User user = userService.findById(userId);
        if (discoveryRepository.existsById(discoverId)) {
            throw new NoSuchElementException("No discovery found");
        } else {
            Discovery discovery = discoveryService.findById(discoverId);
            System.out.println("kupa");
            toSave.setDateAdded(LocalDateTime.now());
            System.out.println("Kupa 2");
            toSave.setDescription(toSave.getDescription());
            System.out.println("Kuoa 3");
            toSave.setDiscovery(discovery);
//     toSave.setUser(user);
            Comment save = commentRepository.save(toSave);
            return save;
        }
    }


    public List<Comment> getAllDiscoverComments(int discoveryId){
        if (discoveryRepository.existsById(discoveryId)){
            throw  new NoSuchElementException("No discovery found");
        }
        if (discoveryService.findById(discoveryId).getComments().isEmpty()){
            throw  new NoSuchElementException("No comments found");
        }
        Discovery discovery = discoveryService.findById(discoveryId);
        return new ArrayList<>(discovery.getComments());
    }

}
