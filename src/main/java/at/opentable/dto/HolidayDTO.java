package at.opentable.dto;

import at.opentable.entity.Holiday;

import java.util.Date;

public class HolidayDTO {

    private Integer id;
    private RestaurantOpeningsDTO restaurantOpeningsDTO;
    private Date startHoliday;
    private Date endHoliday;
    private String comment;

    public HolidayDTO(Holiday holiday) {
        this.id = holiday.getId();
        this.restaurantOpeningsDTO = new RestaurantOpeningsDTO(holiday.getRestaurant());
        this.startHoliday = holiday.getStartHoliday();
        this.endHoliday = holiday.getEndHoliday();
        this.comment = holiday.getComment();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantOpeningsDTO getRestaurantOpeningsDTO() {
        return restaurantOpeningsDTO;
    }

    public void setRestaurantOpeningsDTO(RestaurantOpeningsDTO restaurantOpeningsDTO) {
        this.restaurantOpeningsDTO = restaurantOpeningsDTO;
    }

    public Date getStartHoliday() {
        return startHoliday;
    }

    public void setStartHoliday(Date startHoliday) {
        this.startHoliday = startHoliday;
    }

    public Date getEndHoliday() {
        return endHoliday;
    }

    public void setEndHoliday(Date endHoliday) {
        this.endHoliday = endHoliday;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
