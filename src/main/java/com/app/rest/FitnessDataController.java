package com.app.rest;

import com.app.controller.HomeController;
import com.app.dto.SearchDTO;
import com.app.model.User;
import com.app.service.*;
import com.app.util.CommonUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class FitnessDataController {
    private final Logger log = LoggerFactory.getLogger(FitnessDataController.class);

    @Autowired
    private CalorieService calorieService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private SleepService sleepService;

    @Autowired
    private WaterService waterService;

    @Autowired
    private DailyGoalService dailyGoalService;

    @PostMapping("/data")
    public ResponseEntity<?> fetchFitnessData(@RequestBody SearchDTO searchDTO, HttpSession httpSession) {
        log.info("Entering fetchFitnessData() method");
        log.info("searchDTO : "+searchDTO);
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        try {
            log.info("Exiting fetchReservationsByFilter() method");

            User loggedInUser = CommonUtil.getUserFromSession(httpSession);

            data.put("calorie", calorieService.getCalorieInfoByDateAndUser(searchDTO.getSearchDate(), loggedInUser));
            data.put("exercise", exerciseService.getExerciseInfoByDateAndUser(searchDTO.getSearchDate(), loggedInUser));
            data.put("sleep", sleepService.getSleepInfoByDateAndUser(searchDTO.getSearchDate(), loggedInUser));
            data.put("water", waterService.getWaterInfoByDateAndUser(searchDTO.getSearchDate(), loggedInUser));
            data.put("dailyGoal", dailyGoalService.getDailyGoalInfoByDateAndUser(searchDTO.getSearchDate(), loggedInUser));

            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exiting fetchFitnessData() method");
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
