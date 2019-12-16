package at.opentable.controller;

import at.opentable.dto.HolidayDTO;
import at.opentable.entity.Holiday;
import at.opentable.entity.Restaurant;
import at.opentable.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class HolidayController {

    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private RestaurantController restaurantController;

    public Iterable<HolidayDTO> findByRestaurant(int id) {
        Iterable<Holiday> holidays = holidayRepository.findByRestaurant(id);
        ArrayList<HolidayDTO> holidayDTOS = new ArrayList<>();
        for (Holiday holiday : holidays) {
            holidayDTOS.add(new HolidayDTO(holiday));
        }
        return holidayDTOS;
    }

    public boolean createHoliday(Holiday holiday) {
        Holiday objEntity = this.holidayRepository.save(holiday);
        Optional<Restaurant> updateRestaurant = this.restaurantController.getRestaurant(holiday.getRestaurant().getId());
        if(updateRestaurant.isPresent()){
            Restaurant restaurant = updateRestaurant.get();
            restaurant.getHolidays().add(objEntity);
            this.restaurantController.updateRestaurant(restaurant);
            return true;
        }
        return false;

    }

    public Optional<Holiday> getHoliday(int id) {
        return this.holidayRepository.findById(id);
    }

    public Iterable<HolidayDTO> findAll() {
        Iterable<Holiday> holidays = this.holidayRepository.findAll();
        ArrayList<HolidayDTO> holidayDTOS = new ArrayList<>();
        for (Holiday holiday : holidays) {
            holidayDTOS.add(new HolidayDTO(holiday));
        }
        return holidayDTOS;
    }

    public Optional<Holiday> updateHoliday(Holiday holiday) {
        Optional<Holiday> optionalHoliday = getHoliday(holiday.getId());
        if (optionalHoliday.isPresent()) {
            this.holidayRepository.saveAndFlush(holiday);
            return this.getHoliday(holiday.getId());
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteHoliday(int id) {
        Holiday holiday = this.getHoliday(id).get();
        Optional<Restaurant> optionalRestaurant = this.restaurantController.getRestaurant(holiday.getRestaurant().getId());
        if (this.getHoliday(id).isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.getHolidays().remove(holiday);
            this.restaurantController.updateRestaurant(restaurant);
            this.holidayRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
