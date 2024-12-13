package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;

public class Notice {

    @NotEmpty(message = "Mandatory")
    @Size(min = 3, max = 128, message="Length has to be between 3 and 128")
    private String title;

    @NotEmpty(message = "Mandatory")
    @Email(message = "Has to be in email format")
    private String poster;

    @NotNull(message = "Mandatory")
    @Future(message = "Has to be a date in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;

    @NotEmpty(message = "Must include at least one category")
    private List<String> categories;

    @NotEmpty(message = "Mandatory")
    private String text;

    
    public Notice() {
    }


    public Notice(
            @NotEmpty(message = "Mandatory") @Size(min = 3, max = 128, message = "Length has to be between 3 and 128") String title,
            @NotEmpty(message = "Mandatory") @Email(message = "Has to be in Email format") String poster,
            @NotNull(message = "Mandatory") @Future(message = "Has to be a date in the future") Date postDate,
            @NotEmpty(message = "Must include at least one category") List<String> categories,
            @NotEmpty(message = "Mandatory") String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }


    public String getTitle() {
        return title;
    }



    public void setTitle(String title) {
        this.title = title;
    }



    public String getPoster() {
        return poster;
    }



    public void setPoster(String poster) {
        this.poster = poster;
    }



    public Date getPostDate() {
        return postDate;
    }



    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }



    public List<String> getCategories() {
        return categories;
    }



    public void setCategories(List<String> categories) {
        this.categories = categories;
    }



    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    
    


    
}
