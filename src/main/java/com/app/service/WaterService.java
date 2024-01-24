package com.app.service;

import com.app.dto.AppResponse;
import com.app.dto.WaterDTO;
import com.app.model.User;
import com.app.model.Water;
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
public class WaterService {

    @Autowired
    private WaterRepository waterRepository;

    public List<WaterDTO> getWaterInfoList(User user) {
        try {
            List<Water> waters = waterRepository.findByUser(user);
            if(waters.isEmpty()) {
                return new ArrayList<>();
            }
            return processWaterInfoList(waters);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<WaterDTO> processWaterInfoList(List<Water> waters) {
        List<WaterDTO> list = new ArrayList<>();
        int counter = 1;
        for(Water water : waters) {
            WaterDTO waterDTO = new WaterDTO();
            waterDTO.setId(water.getId());
            waterDTO.setSl(String.valueOf(counter++));
            waterDTO.setAmount(String.valueOf(water.getAmount()));
            waterDTO.setIntakeDate(String.valueOf(water.getIntakeDate()));

            list.add(waterDTO);
        }
        return list;
    }

    public AppResponse addWaterInfo(WaterDTO waterDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isWaterInfoAddRequestValid(waterDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }

            if(getWaterInfoByDateAndUser(waterDTO.getIntakeDate(), loggedInUser) != null) {
                return new AppResponse(false, "Water data already exists for this date !");
            }

            Water water = new Water();
            water.setAmount(Double.parseDouble(waterDTO.getAmount()));
            water.setIntakeDate(CommonUtil.getDateFromString(waterDTO.getIntakeDate()));

            water.setUser(loggedInUser);
            water.setCreatedBy(loggedInUser.getUserId());
            water.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            waterRepository.save(water);

            return new AppResponse(true, "Water Info Added Successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to add water info !");
        }
    }

    public WaterDTO fetchWaterInfoById(Long id) {
        try {
            Optional<Water> waterOptional = waterRepository.findById(id);
            if(waterOptional.isPresent()){
                return processWater(waterOptional.get());
            }
            return new WaterDTO();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new WaterDTO();
        }
    }

    public AppResponse updateWaterInfo(WaterDTO waterDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isWaterInfoAddRequestValid(waterDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }
            if(!CommonUtil.isValueNotNullAndEmpty(waterDTO.getId())) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Optional<Water> waterOptional = waterRepository.findById(waterDTO.getId());
            if(waterOptional.isEmpty()) {
                return new AppResponse(false, "Invalid value provided !");
            }

            Water existingWaterInfo = waterOptional.get();
            existingWaterInfo.setAmount(Double.parseDouble(waterDTO.getAmount()));
            existingWaterInfo.setIntakeDate(CommonUtil.getDateFromString(waterDTO.getIntakeDate()));

            existingWaterInfo.setUpdatedBy(loggedInUser.getUserId());
            existingWaterInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            waterRepository.save(existingWaterInfo);

            return new AppResponse(true, "Water info updated successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to update water info !");
        }
    }

    public AppResponse deleteWaterInfo(Long id) {
        waterRepository.deleteById(id);
        return new AppResponse(true, "Water info deleted");
    }

    public WaterDTO getWaterInfoByDate(String searchDate){
        try {
            Optional<Water> waterOptional = waterRepository.findByIntakeDate(CommonUtil.getDateFromString(searchDate));
            if(waterOptional.isEmpty()) {
                return null;
            }
            return processWater(waterOptional.get());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public WaterDTO getWaterInfoByDateAndUser(String searchDate, User user){
        try {
            Optional<Water> waterOptional = waterRepository.findByIntakeDateAndUser(CommonUtil.getDateFromString(searchDate), user);
            if(waterOptional.isEmpty()) {
                return null;
            }
            return processWater(waterOptional.get());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private WaterDTO processWater(Water water) {
        WaterDTO waterDTO = new WaterDTO();
        waterDTO.setId(water.getId());
        waterDTO.setAmount(String.valueOf(water.getAmount()));
        waterDTO.setIntakeDate(String.valueOf(water.getIntakeDate()));
        return waterDTO;
    }
}
