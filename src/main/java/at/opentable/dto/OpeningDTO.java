package at.opentable.dto;

import at.opentable.entity.Opening;

import java.sql.Time;

public class OpeningDTO {

    private int id;
    private RestaurantOpeningsDTO restaurantOpeningsDTO;
    private Opening.Weekday weekday;
    private Time starOpening;
    private Time endOpening;

    public OpeningDTO(Opening opening) {
        this.id = opening.getId();
        this.restaurantOpeningsDTO = new RestaurantOpeningsDTO(opening.getRestaurant());
        this.weekday = opening.getWeekday();
        this.starOpening = opening.getStartOpening();
        this.endOpening = opening.getEndOpening();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RestaurantOpeningsDTO getRestaurantOpeningsDTO() {
        return restaurantOpeningsDTO;
    }

    public void setRestaurantOpeningsDTO(RestaurantOpeningsDTO restaurantOpeningsDTO) {
        this.restaurantOpeningsDTO = restaurantOpeningsDTO;
    }

    public Opening.Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Opening.Weekday weekday) {
        this.weekday = weekday;
    }

    public Time getStarOpening() {
        return starOpening;
    }

    public void setStarOpening(Time starOpening) {
        this.starOpening = starOpening;
    }

    public Time getEndOpening() {
        return endOpening;
    }

    public void setEndOpening(Time endOpening) {
        this.endOpening = endOpening;
    }
}
