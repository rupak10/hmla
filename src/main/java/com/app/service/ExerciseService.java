package com.app.service;

import com.app.dto.AppResponse;
import com.app.dto.CalorieDTO;
import com.app.dto.ExerciseDTO;
import com.app.model.Calorie;
import com.app.model.Exercise;
import com.app.model.User;
import com.app.repository.CalorieRepository;
import com.app.repository.ExerciseRepository;
import com.app.util.CommonUtil;
import com.app.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<ExerciseDTO> getExerciseInfoList(User user) {
        try {
            List<Exercise> exercises = exerciseRepository.findByUser(user);
            if(exercises.isEmpty()) {
                return new ArrayList<>();
            }
            return processExerciseList(exercises);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<ExerciseDTO> processExerciseList(List<Exercise> exercises) {
        List<ExerciseDTO> list = new ArrayList<>();
        int counter = 1;
        for(Exercise exercise : exercises) {
            ExerciseDTO exerciseDTO = new ExerciseDTO();
            exerciseDTO.setId(exercise.getId());
            exerciseDTO.setSl(String.valueOf(counter++));
            exerciseDTO.setExerciseName(exercise.getExerciseName());
            exerciseDTO.setTotalMinutes(String.valueOf(exercise.getTotalMinutes()));
            exerciseDTO.setCaloriesBurnt(String.valueOf(exercise.getCaloriesBurnt()));
            exerciseDTO.setExerciseDate(String.valueOf(exercise.getExerciseDate()));

            list.add(exerciseDTO);
        }
        return list;
    }

    public AppResponse addExercise(ExerciseDTO exerciseDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isExerciseAddRequestValid(exerciseDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }

            if(getExerciseInfoByDateAndUser(exerciseDTO.getExerciseDate(), loggedInUser) != null) {
                return new AppResponse(false, "Exercise data already exists for this date !");
            }

            Exercise exercise = new Exercise();

            exercise.setExerciseName(exerciseDTO.getExerciseName());
            exercise.setTotalMinutes(Integer.parseInt(exerciseDTO.getTotalMinutes()));
            exercise.setCaloriesBurnt(Integer.parseInt(exerciseDTO.getCaloriesBurnt()));
            exercise.setExerciseDate(CommonUtil.getDateFromString(exerciseDTO.getExerciseDate()));

            exercise.setUser(loggedInUser);

            exercise.setCreatedBy(loggedInUser.getUserId());
            exercise.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            exerciseRepository.save(exercise);

            return new AppResponse(true, "Exercise Info Added Successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to add exercise info !");
        }
    }

    public ExerciseDTO fetchExerciseById(Long id) {
        try {
            Optional<Exercise> exercise = exerciseRepository.findById(id);
            if(exercise.isPresent()){
                return processExercise(exercise.get());
            }
            return new ExerciseDTO();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ExerciseDTO();
        }
    }

    public AppResponse updateExerciseInfo(ExerciseDTO exerciseDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isExerciseAddRequestValid(exerciseDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }
            if(!CommonUtil.isValueNotNullAndEmpty(exerciseDTO.getId())) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Optional<Exercise> exerciseOptional = exerciseRepository.findById(exerciseDTO.getId());
            if(exerciseOptional.isEmpty()) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Exercise existingExerciseInfo = exerciseOptional.get();
            existingExerciseInfo.setExerciseName(exerciseDTO.getExerciseName());
            existingExerciseInfo.setTotalMinutes(Integer.parseInt(exerciseDTO.getTotalMinutes()));
            existingExerciseInfo.setCaloriesBurnt(Integer.parseInt(exerciseDTO.getCaloriesBurnt()));
            existingExerciseInfo.setExerciseDate(CommonUtil.getDateFromString(exerciseDTO.getExerciseDate()));

            existingExerciseInfo.setUpdatedBy(loggedInUser.getUserId());
            existingExerciseInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            exerciseRepository.save(existingExerciseInfo);

            return new AppResponse(true, "Exercise info updated successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to update exercise info !");
        }
    }

    public AppResponse deleteExerciseInfo(Long id) {
        exerciseRepository.deleteById(id);
        return new AppResponse(true, "Exercise info deleted");
    }

    public ExerciseDTO getExerciseInfoByDate(String searchDate){
        try {
            Optional<Exercise> exerciseOptional = exerciseRepository.findByExerciseDate(CommonUtil.getDateFromString(searchDate));
            if(exerciseOptional.isEmpty()) {
                return null;
            }
            return processExercise(exerciseOptional.get());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ExerciseDTO getExerciseInfoByDateAndUser(String searchDate, User user){
        try {
            Optional<Exercise> exerciseOptional = exerciseRepository.findByExerciseDateAndUser(CommonUtil.getDateFromString(searchDate), user);
            if(exerciseOptional.isEmpty()) {
                return null;
            }
            return processExercise(exerciseOptional.get());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ExerciseDTO processExercise(Exercise exercise) {
        ExerciseDTO exerciseDTO = new ExerciseDTO();
        exerciseDTO.setId(exercise.getId());
        exerciseDTO.setExerciseName(exercise.getExerciseName());
        exerciseDTO.setTotalMinutes(String.valueOf(exercise.getTotalMinutes()));
        exerciseDTO.setCaloriesBurnt(String.valueOf(exercise.getCaloriesBurnt()));
        exerciseDTO.setExerciseDate(String.valueOf(exercise.getExerciseDate()));

        return exerciseDTO;
    }
}
