package br.com.caelum.oauth.socializing.dtos;

import br.com.caelum.oauth.socializing.models.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Movie {

    @JsonProperty("movie")
    private String title;

    private Integer rating;

    private String comment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post toPost(){
        return  new Post(title +"\n" + "rating: " + rating + "\n----" + comment );
    }

    public String toJson(){
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot be cast to json");
        }
    }

}
