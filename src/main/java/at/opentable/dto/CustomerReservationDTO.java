package at.opentable.dto;

import java.sql.Timestamp;

public class CustomerReservationDTO {

    private int restaurantId;
    private int customerId;
    private Timestamp startDateTime;
    private int groupSize;

    public CustomerReservationDTO(int restaurantId, int customerId, Timestamp startDateTime, int groupSize) {
        this.restaurantId = restaurantId;
        this.customerId = customerId;
        this.startDateTime = startDateTime;
        this.groupSize = groupSize;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }
}
