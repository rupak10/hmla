package com.app.service;

import com.app.dto.AppResponse;
import com.app.dto.ExerciseDTO;
import com.app.dto.SleepDTO;
import com.app.model.Exercise;
import com.app.model.Sleep;
import com.app.model.User;
import com.app.repository.ExerciseRepository;
import com.app.repository.SleepRepository;
import com.app.util.CommonUtil;
import com.app.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SleepService {

    @Autowired
    private SleepRepository sleepRepository;

    public List<SleepDTO> getSleepInfoList(User user) {
        try {
            List<Sleep> sleeps = sleepRepository.findByUser(user);
            if(sleeps.isEmpty()) {
                return new ArrayList<>();
            }
            return processSleepList(sleeps);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<SleepDTO> processSleepList(List<Sleep> sleeps) {
        List<SleepDTO> list = new ArrayList<>();
        int counter = 1;
        for(Sleep sleep : sleeps) {
            SleepDTO sleepDTO = new SleepDTO();
            sleepDTO.setId(sleep.getId());
            sleepDTO.setSl(String.valueOf(counter++));
            sleepDTO.setTotalSleepTime(String.valueOf(sleep.getTotalSleepTime()));
            sleepDTO.setSleepDate(String.valueOf(sleep.getSleepDate()));

            list.add(sleepDTO);
        }
        return list;
    }

    public AppResponse addSleep(SleepDTO sleepDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isSleepInfoAddRequestValid(sleepDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }

            if(getSleepInfoByDateAndUser(sleepDTO.getSleepDate(), loggedInUser) != null) {
                return new AppResponse(false, "Sleep data already exists for this date !");
            }

            Sleep sleep = new Sleep();
            sleep.setTotalSleepTime(Double.parseDouble(sleepDTO.getTotalSleepTime()));
            sleep.setSleepDate(CommonUtil.getDateFromString(sleepDTO.getSleepDate()));
            sleep.setUser(loggedInUser);
            sleep.setCreatedBy(loggedInUser.getUserId());
            sleep.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            sleepRepository.save(sleep);

            return new AppResponse(true, "Sleep Info Added Successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to add sleep info !");
        }
    }

    public SleepDTO fetchSleepInfoById(Long id) {
        try {
            Optional<Sleep> sleep = sleepRepository.findById(id);
            if(sleep.isPresent()){
                return processSleep(sleep.get());
            }
            return new SleepDTO();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new SleepDTO();
        }
    }

    public AppResponse updateSleepInfo(SleepDTO sleepDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isSleepInfoAddRequestValid(sleepDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }
            if(!CommonUtil.isValueNotNullAndEmpty(sleepDTO.getId())) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Optional<Sleep> sleepOptional = sleepRepository.findById(sleepDTO.getId());
            if(sleepOptional.isEmpty()) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Sleep existingSleepInfo = sleepOptional.get();
            existingSleepInfo.setTotalSleepTime(Double.parseDouble(sleepDTO.getTotalSleepTime()));
            existingSleepInfo.setSleepDate(CommonUtil.getDateFromString(sleepDTO.getSleepDate()));

            existingSleepInfo.setUpdatedBy(loggedInUser.getUserId());
            existingSleepInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            sleepRepository.save(existingSleepInfo);

            return new AppResponse(true, "Sleep info updated successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to update sleep info !");
        }
    }

    public AppResponse deleteSleepInfo(Long id) {
        sleepRepository.deleteById(id);
        return new AppResponse(true, "Sleep info deleted");
    }

    public SleepDTO getSleepInfoByDate(String searchDate){
        try {
            Optional<Sleep> sleepOptional = sleepRepository.findBySleepDate(CommonUtil.getDateFromString(searchDate));
            if(sleepOptional.isEmpty()) {
                return null;
            }
            return processSleep(sleepOptional.get());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SleepDTO getSleepInfoByDateAndUser(String searchDate, User user){
        try {
            Optional<Sleep> sleepOptional = sleepRepository.findBySleepDateAndUser(CommonUtil.getDateFromString(searchDate), user);
            if(sleepOptional.isEmpty()) {
                return null;
            }
            return processSleep(sleepOptional.get());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private SleepDTO processSleep(Sleep sleep) {
        SleepDTO sleepDTO = new SleepDTO();
        sleepDTO.setId(sleep.getId());
        sleepDTO.setTotalSleepTime(String.valueOf(sleep.getTotalSleepTime()));
        sleepDTO.setSleepDate(String.valueOf(sleep.getSleepDate()));
        return sleepDTO;
    }
}
