package com.app.service;

import com.app.dto.AppResponse;
import com.app.dto.CalorieDTO;
import com.app.model.Calorie;
import com.app.model.User;
import com.app.repository.CalorieRepository;
import com.app.util.CommonUtil;
import com.app.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalorieService {

    @Autowired
    private CalorieRepository calorieRepository;

    public List<CalorieDTO> getCalorieInfoList(User user) {
        try {
            List<Calorie> calories = calorieRepository.findByUser(user);
            if(calories.isEmpty()) {
                return new ArrayList<>();
            }
            return processCalorieList(calories);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<CalorieDTO> processCalorieList(List<Calorie> calories) {
        List<CalorieDTO> list = new ArrayList<>();
        int counter = 1;
        for(Calorie calorie : calories) {
            CalorieDTO calorieDTO = new CalorieDTO();
            calorieDTO.setId(calorie.getId());
            calorieDTO.setSl(String.valueOf(counter++));
            calorieDTO.setFoodType(calorie.getFoodType());
            calorieDTO.setAmountOfFood(String.valueOf(calorie.getAmountOfFood()));
            calorieDTO.setCalories(String.valueOf(calorie.getCalories()));
            calorieDTO.setIntakeDate(String.valueOf(calorie.getIntakeDate()));

            list.add(calorieDTO);
        }
        return list;
    }

    public AppResponse addCalorie(CalorieDTO calorieDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isCalorieAddRequestValid(calorieDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }

            if(getCalorieInfoByDateAndUser(calorieDTO.getIntakeDate(), loggedInUser) != null) {
                return new AppResponse(false, "Calorie data already exists for this date !");
            }

            Calorie calorie = new Calorie();
            calorie.setFoodType(calorieDTO.getFoodType());
            calorie.setAmountOfFood(Double.parseDouble(calorieDTO.getAmountOfFood()));
            calorie.setCalories(Double.parseDouble(calorieDTO.getCalories()));
            calorie.setIntakeDate(CommonUtil.getDateFromString(calorieDTO.getIntakeDate()));

            calorie.setUser(loggedInUser);

            calorie.setCreatedBy(loggedInUser.getUserId());
            calorie.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            calorieRepository.save(calorie);

            return new AppResponse(true, "Calorie Added Successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to add calorie !");
        }
    }

    public CalorieDTO fetchCalorieById(Long id) {
        try {
            Optional<Calorie> calorie = calorieRepository.findById(id);
            if(calorie.isPresent()){
                return processCalorie(calorie.get());
            }
            return new CalorieDTO();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new CalorieDTO();
        }
    }

    public AppResponse updateCalorieInfo(CalorieDTO calorieDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isCalorieAddRequestValid(calorieDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }
            if(!CommonUtil.isValueNotNullAndEmpty(calorieDTO.getId())) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Optional<Calorie> calorieOptional = calorieRepository.findById(calorieDTO.getId());
            if(calorieOptional.isEmpty()) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Calorie existingCalorieInfo = calorieOptional.get();
            existingCalorieInfo.setFoodType(calorieDTO.getFoodType());
            existingCalorieInfo.setAmountOfFood(Double.parseDouble(calorieDTO.getAmountOfFood()));
            existingCalorieInfo.setCalories(Double.parseDouble(calorieDTO.getCalories()));
            existingCalorieInfo.setIntakeDate(CommonUtil.getDateFromString(calorieDTO.getIntakeDate()));

            existingCalorieInfo.setUpdatedBy(loggedInUser.getUserId());
            existingCalorieInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            calorieRepository.save(existingCalorieInfo);

            return new AppResponse(true, "Calorie info updated successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to update calorie info !");
        }
    }

    public AppResponse deleteCalorieInfo(Long id) {
        calorieRepository.deleteById(id);
        return new AppResponse(true, "Calorie info deleted");
    }

    public CalorieDTO getCalorieInfoByDate(String searchDate){
        try {
            Optional<Calorie> calorieOptional = calorieRepository.findByIntakeDate(CommonUtil.getDateFromString(searchDate));
            if(calorieOptional.isEmpty()) {
                return null;
            }
            return processCalorie(calorieOptional.get());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CalorieDTO getCalorieInfoByDateAndUser(String searchDate, User user){
        try {
            Optional<Calorie> calorieOptional = calorieRepository.findByIntakeDateAndUser(CommonUtil.getDateFromString(searchDate), user);
            if(calorieOptional.isEmpty()) {
                return null;
            }
            return processCalorie(calorieOptional.get());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private CalorieDTO processCalorie(Calorie calorie) {
        CalorieDTO calorieDTO = new CalorieDTO();
        calorieDTO.setId(calorie.getId());
        calorieDTO.setFoodType(calorie.getFoodType());
        calorieDTO.setCalories(String.valueOf(calorie.getCalories()));
        calorieDTO.setAmountOfFood(String.valueOf(calorie.getAmountOfFood()));
        calorieDTO.setIntakeDate(String.valueOf(calorie.getIntakeDate()));

        return calorieDTO;
    }

}
