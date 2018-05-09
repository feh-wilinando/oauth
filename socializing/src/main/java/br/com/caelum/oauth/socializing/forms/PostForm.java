package br.com.caelum.oauth.socializing.forms;

import br.com.caelum.oauth.socializing.models.Post;

import javax.validation.constraints.NotEmpty;

public class PostForm {

    @NotEmpty
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post toEntity() {
        return new Post(content);
    }
}
