package at.opentable.dto;

import at.opentable.entity.Restaurant;

public class RestaurantOpeningsDTO {

    private int id;
    private String name;
    private String city;

    public RestaurantOpeningsDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.city = restaurant.getCity();
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
}
