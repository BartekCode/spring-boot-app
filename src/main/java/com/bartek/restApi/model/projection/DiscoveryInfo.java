package com.bartek.restApi.model.projection;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class DiscoveryInfo {

     String title;
     String url;
     String description;
     LocalDateTime dateAdded;
     String author;
     List<CommentInfo> comments;

}
