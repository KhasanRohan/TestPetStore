package Pojo;

import org.junit.jupiter.api.Tag;

import java.util.ArrayList;

public class CreateNewPet {
    private Integer id;
    private String name;
    private String status;
//    private ArrayList<String> photoUrls;
//    private ArrayList<Tag> tags;

    public CreateNewPet(Integer id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
//        this.photoUrls = photoUrls;
//        this.tags = tags;
    }

    public CreateNewPet(){

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

//    public ArrayList<String> getPhotoUrls() {
//        return photoUrls;
//    }

//    public ArrayList<Tag> getTags() {
//        return tags;
//    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public void setPhotoUrls(ArrayList<String> photoUrls) {
//        this.photoUrls = photoUrls;
//    }
//
//    public void setTags(ArrayList<Tag> tags) {
//        this.tags = tags;
//    }
}

