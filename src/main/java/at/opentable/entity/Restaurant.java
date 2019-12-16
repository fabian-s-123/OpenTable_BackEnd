package at.opentable.entity;

import at.opentable.dto.Image;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;



    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zip;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String website;

    @Column(nullable=false)
    private String description;

    @Column(name = "social_media")
    private String socialMedia;

    private String images;

    // Optional implementation
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Opening> opening;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Holiday> holidays;

    private String menu;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Teburu> teburu;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tag> tagList;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Admin> admins;

    public Restaurant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    /**
     * Transforms String to JSON
     *
     * @return image as a JSON
     */
    public List<Image> getImages() {
        List<Image> imageList = new LinkedList<>();
        Gson gson = new Gson();
        Type listOfMyClassObject = new TypeToken<ArrayList<Image>>() {
        }.getType();
        return gson.fromJson (this.images, listOfMyClassObject);
    }

    /**
     *
     * @param images requires link & description of Object Image
     */
    public void setImages(List<Image> images) {
        Gson gson = new Gson();
        this.images = gson.toJson(images);
    }

    public List<Opening> getOpening() {
        return opening;
    }

    public void setOpening(List<Opening> opening) {
        this.opening = opening;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    public List<Teburu> getTeburu() {
        return teburu;
    }

    public void setTeburu(List<Teburu> teburu) {
        this.teburu = teburu;
    }

    public List<Admin> getAdmins() {
        return admins;
    }


    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }
}
