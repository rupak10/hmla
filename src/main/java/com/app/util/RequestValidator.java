package com.app.util;

import com.app.dto.*;

public class RequestValidator {

    public static Boolean isSignUpRequestValid(SignupRequest signupRequest) {
        if(signupRequest == null) {
            return false;
        }
        return CommonUtil.isValueNotNullAndEmpty(signupRequest.getFirstName())
                && CommonUtil.isValueNotNullAndEmpty(signupRequest.getEmail())
                && CommonUtil.isValueNotNullAndEmpty(signupRequest.getPassword());
    }

    public static Boolean isLoginUpRequestValid(LoginRequest loginRequest) {
        if(loginRequest == null) {
            return false;
        }
        return CommonUtil.isValueNotNullAndEmpty(loginRequest.getEmail())
                && CommonUtil.isValueNotNullAndEmpty(loginRequest.getPassword());
    }

    public static boolean isCalorieAddRequestValid(CalorieDTO calorieDTO) {
        try {
            if(calorieDTO == null) {
                return false;
            }
            if(!CommonUtil.isValueNotNullAndEmpty(calorieDTO.getFoodType()) || !CommonUtil.isValueNotNullAndEmpty(calorieDTO.getAmountOfFood())
            || !CommonUtil.isValueNotNullAndEmpty(calorieDTO.getCalories()) || !CommonUtil.isValueNotNullAndEmpty(calorieDTO.getIntakeDate())) {
                return false;
            }

            Double.parseDouble(calorieDTO.getCalories());
            Double.parseDouble(calorieDTO.getAmountOfFood());
            CommonUtil.getDateFromString(calorieDTO.getIntakeDate());

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isExerciseAddRequestValid(ExerciseDTO exerciseDTO) {
        try {
            if(exerciseDTO == null) {
                return false;
            }
            if(!CommonUtil.isValueNotNullAndEmpty(exerciseDTO.getExerciseName()) || !CommonUtil.isValueNotNullAndEmpty(exerciseDTO.getExerciseDate())
                    || !CommonUtil.isValueNotNullAndEmpty(exerciseDTO.getTotalMinutes()) || !CommonUtil.isValueNotNullAndEmpty(exerciseDTO.getCaloriesBurnt())) {
                return false;
            }

            Integer.parseInt(exerciseDTO.getTotalMinutes());
            Integer.parseInt(exerciseDTO.getCaloriesBurnt());
            CommonUtil.getDateFromString(exerciseDTO.getExerciseDate());

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isSleepInfoAddRequestValid(SleepDTO sleepDTO) {
        try {
            if(sleepDTO == null) {
                return false;
            }
            if(!CommonUtil.isValueNotNullAndEmpty(sleepDTO.getTotalSleepTime()) || !CommonUtil.isValueNotNullAndEmpty(sleepDTO.getSleepDate())) {
                return false;
            }

            Double.parseDouble(sleepDTO.getTotalSleepTime());
            CommonUtil.getDateFromString(sleepDTO.getSleepDate());

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isWaterInfoAddRequestValid(WaterDTO waterDTO) {
        try {
            if(waterDTO == null) {
                return false;
            }
            if(!CommonUtil.isValueNotNullAndEmpty(waterDTO.getAmount()) || !CommonUtil.isValueNotNullAndEmpty(waterDTO.getIntakeDate())) {
                return false;
            }

            Double.parseDouble(waterDTO.getAmount());
            CommonUtil.getDateFromString(waterDTO.getIntakeDate());

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isDailyGoalInfoAddRequestValid(DailyGoalDTO dailyGoalDTO) {
        try {
            if(dailyGoalDTO == null) {
                return false;
            }
            if(!CommonUtil.isValueNotNullAndEmpty(dailyGoalDTO.getCalories())
                    || !CommonUtil.isValueNotNullAndEmpty(dailyGoalDTO.getExerciseMinutes())
                    || !CommonUtil.isValueNotNullAndEmpty(dailyGoalDTO.getSleep())
                    || !CommonUtil.isValueNotNullAndEmpty(dailyGoalDTO.getWater())
                    || !CommonUtil.isValueNotNullAndEmpty(dailyGoalDTO.getDate())
            ) {
                return false;
            }

            Double.parseDouble(dailyGoalDTO.getCalories());
            Integer.parseInt(dailyGoalDTO.getExerciseMinutes());
            Double.parseDouble(dailyGoalDTO.getWater());
            Double.parseDouble(dailyGoalDTO.getSleep());
            CommonUtil.getDateFromString(dailyGoalDTO.getDate());

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
