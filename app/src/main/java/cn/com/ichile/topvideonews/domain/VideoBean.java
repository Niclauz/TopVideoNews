package cn.com.ichile.topvideonews.domain;

import java.io.Serializable;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 20:48.
 */
public class VideoBean implements Serializable {
    /**
     *
     */
    private String id;
    private String token;
    private String url;
    private String title;
    private String image;
    private String thumb;
    private String author;

    public VideoBean(String id, String token, String url, String image, String thumb, String title, String author) {
        this.id = id;
        this.token = token;
        this.url = url;
        this.image = image;
        this.thumb = thumb;
        this.title = title;
        this.author = author;
    }

    public VideoBean() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? "error" : id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? "error" : author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "error" : title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? "error" : image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb == null ? "error" : thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? "error" : url;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", thumb='" + thumb + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
