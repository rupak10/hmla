package com.app.service;

import com.app.dto.AppResponse;
import com.app.dto.DailyGoalDTO;
import com.app.dto.WaterDTO;
import com.app.model.DailyGoal;
import com.app.model.User;
import com.app.model.Water;
import com.app.repository.DailyGoalRepository;
import com.app.repository.WaterRepository;
import com.app.util.CommonUtil;
import com.app.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DailyGoalService {

    @Autowired
    private DailyGoalRepository dailyGoalRepository;

    public List<DailyGoalDTO> getDailyGoalInfoList(User user) {
        try {
            List<DailyGoal> dailyGoals = dailyGoalRepository.findByUser(user);
            if(dailyGoals.isEmpty()) {
                return new ArrayList<>();
            }
            return processDailyGoalInfoList(dailyGoals);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<DailyGoalDTO> processDailyGoalInfoList(List<DailyGoal> dailyGoals) {
        List<DailyGoalDTO> list = new ArrayList<>();
        int counter = 1;
        for(DailyGoal dailyGoal : dailyGoals) {
            DailyGoalDTO dailyGoalDTO = new DailyGoalDTO();
            dailyGoalDTO.setId(dailyGoal.getId());
            dailyGoalDTO.setSl(String.valueOf(counter++));
            dailyGoalDTO.setCalories(String.valueOf(dailyGoal.getCalories()));
            dailyGoalDTO.setExerciseMinutes(String.valueOf(dailyGoal.getExerciseMinutes()));
            dailyGoalDTO.setSleep(String.valueOf(dailyGoal.getSleep()));
            dailyGoalDTO.setWater(String.valueOf(dailyGoal.getSleep()));
            dailyGoalDTO.setSleep(String.valueOf(dailyGoal.getSleep()));

            list.add(dailyGoalDTO);
        }
        return list;
    }

    public AppResponse addDailyGoalInfo(DailyGoalDTO dailyGoalDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isDailyGoalInfoAddRequestValid(dailyGoalDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }

            if(getDailyGoalInfoByDateAndUser(dailyGoalDTO.getDate(), loggedInUser) != null) {
                return new AppResponse(false, "Daily goal data already exists for this date !");
            }

            DailyGoal dailyGoal = new DailyGoal();
            dailyGoal.setCalories(Double.parseDouble(dailyGoalDTO.getCalories()));
            dailyGoal.setExerciseMinutes(Integer.parseInt(dailyGoalDTO.getExerciseMinutes()));
            dailyGoal.setWater(Double.parseDouble(dailyGoalDTO.getWater()));
            dailyGoal.setSleep(Double.parseDouble(dailyGoalDTO.getSleep()));
            dailyGoal.setDate(CommonUtil.getDateFromString(dailyGoalDTO.getDate()));

            dailyGoal.setUser(loggedInUser);
            dailyGoal.setCreatedBy(loggedInUser.getUserId());
            dailyGoal.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            dailyGoalRepository.save(dailyGoal);

            return new AppResponse(true, "Daily Goal Info Added Successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to add daily goal info !");
        }
    }

    public DailyGoalDTO fetchDailyGoalInfoById(Long id) {
        try {
            Optional<DailyGoal> dailyGoalOptional = dailyGoalRepository.findById(id);
            if(dailyGoalOptional.isPresent()){
                return processDailyGoalInfo(dailyGoalOptional.get());
            }
            return new DailyGoalDTO();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new DailyGoalDTO();
        }
    }

    public AppResponse updateDailyGoalInfo(DailyGoalDTO dailyGoalDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isDailyGoalInfoAddRequestValid(dailyGoalDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }
            if(!CommonUtil.isValueNotNullAndEmpty(dailyGoalDTO.getId())) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Optional<DailyGoal> dailyGoalOptional = dailyGoalRepository.findById(dailyGoalDTO.getId());
            if(dailyGoalOptional.isEmpty()) {
                return new AppResponse(false, "Invalid value provided !");
            }

            DailyGoal existingDailyGoalInfo = dailyGoalOptional.get();

            existingDailyGoalInfo.setCalories(Double.parseDouble(dailyGoalDTO.getCalories()));
            existingDailyGoalInfo.setExerciseMinutes(Integer.parseInt(dailyGoalDTO.getExerciseMinutes()));
            existingDailyGoalInfo.setWater(Double.parseDouble(dailyGoalDTO.getWater()));
            existingDailyGoalInfo.setSleep(Double.parseDouble(dailyGoalDTO.getSleep()));
            existingDailyGoalInfo.setDate(CommonUtil.getDateFromString(dailyGoalDTO.getDate()));

            existingDailyGoalInfo.setUpdatedBy(loggedInUser.getUserId());
            existingDailyGoalInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            dailyGoalRepository.save(existingDailyGoalInfo);

            return new AppResponse(true, "Daily goal info updated successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to update water info !");
        }
    }

    public AppResponse deleteDailyGoalInfo(Long id) {
        dailyGoalRepository.deleteById(id);
        return new AppResponse(true, "Daily goal info deleted");
    }

    public DailyGoalDTO getDailyGoalInfoByDate(String searchDate){
        try {
            Optional<DailyGoal> dailyGoalOptional = dailyGoalRepository.findByDate(CommonUtil.getDateFromString(searchDate));
            if(dailyGoalOptional.isEmpty()) {
                return null;
            }
            return processDailyGoalInfo(dailyGoalOptional.get());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DailyGoalDTO getDailyGoalInfoByDateAndUser(String searchDate, User user){
        try {
            Optional<DailyGoal> dailyGoalOptional = dailyGoalRepository.findByDateAndUser(CommonUtil.getDateFromString(searchDate), user);
            if(dailyGoalOptional.isEmpty()) {
                return null;
            }
            return processDailyGoalInfo(dailyGoalOptional.get());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private DailyGoalDTO processDailyGoalInfo(DailyGoal dailyGoal) {
        DailyGoalDTO dailyGoalDTO = new DailyGoalDTO();
        dailyGoalDTO.setId(dailyGoal.getId());
        dailyGoalDTO.setCalories(String.valueOf(dailyGoal.getCalories()));
        dailyGoalDTO.setExerciseMinutes(String.valueOf(dailyGoal.getExerciseMinutes()));
        dailyGoalDTO.setWater(String.valueOf(dailyGoal.getWater()));
        dailyGoalDTO.setSleep(String.valueOf(dailyGoal.getSleep()));
        dailyGoalDTO.setDate(String.valueOf(dailyGoal.getDate()));
        return dailyGoalDTO;
    }
}
