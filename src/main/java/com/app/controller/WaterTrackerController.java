package com.app.controller;

import com.app.dto.AppResponse;
import com.app.dto.SleepDTO;
import com.app.dto.WaterDTO;
import com.app.service.SleepService;
import com.app.service.WaterService;
import com.app.util.CommonUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/water")
public class WaterTrackerController {
    private final Logger log = LoggerFactory.getLogger(WaterTrackerController.class);

    private final String WATER_ADD_PAGE = "water/water-add";
    private final String WATER_EDIT_PAGE = "water/water-edit";
    private final String WATER_LIST_PAGE = "water/water-list";
    private final String REDIRECT_WATER_LIST_PAGE = "redirect:/water";
    private final String ACTIVE_MENU = "water";

    @Autowired
    private WaterService waterService;

    @RequestMapping(method = RequestMethod.GET)
    public String loadWaterListPage(Model model, HttpSession httpSession) {
        log.info("Entering loadWaterListPage() method");
        model.addAttribute("pageTitle", "Water Info");

        model.addAttribute("waterList", waterService.getWaterInfoList(CommonUtil.getUserFromSession(httpSession)));
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadWaterListPage() method");
        return WATER_LIST_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String loadWaterAddPage(Model model, HttpSession httpSession) {
        log.info("Entering loadWaterAddPage() method");

        model.addAttribute("pageTitle", "Water Add");
        model.addAttribute("water", new WaterDTO());

        model.addAttribute("am", ACTIVE_MENU);
        log.info("Exiting loadWaterAddPage() method");
        return WATER_ADD_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addWaterInfo(Model model, HttpSession httpSession, @ModelAttribute WaterDTO waterDTO,
                             final RedirectAttributes redirectAttributes) {
        log.info("Entering addWaterInfo() method");
        log.info("waterDTO request:"+waterDTO);

        AppResponse appResponse = waterService.addWaterInfo(waterDTO, CommonUtil.getUserFromSession(httpSession));
        if(appResponse.getStatus()){
            redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
            log.info("Exiting addExercise() method");
            return REDIRECT_WATER_LIST_PAGE;
        }
        else{
            model.addAttribute("msg", appResponse.getMessage());
            model.addAttribute("water", waterDTO);
            log.info("Exiting addWaterInfo() method");
            return WATER_ADD_PAGE;
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String loadWaterEditPage(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        log.info("Entering loadWaterEditPage() method");
        log.info("Water Info ID : "+id);

        WaterDTO waterDTO = waterService.fetchWaterInfoById(id);

        model.addAttribute("pageTitle", "Water Edit");
        model.addAttribute("water", waterDTO);
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadWaterEditPage() method");
        return WATER_EDIT_PAGE;
    }

   @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editWaterInfo(Model model, @ModelAttribute WaterDTO waterDTO, HttpSession httpSession,
                              final RedirectAttributes redirectAttributes) {
        log.info("Entering editWaterInfo() method");
        AppResponse appResponse = waterService.updateWaterInfo(waterDTO, CommonUtil.getUserFromSession(httpSession));
       if(appResponse.getStatus()){
           redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
           log.info("Exiting editSleepInfo() method");
           return REDIRECT_WATER_LIST_PAGE;
       }
       else{
           model.addAttribute("msg", appResponse.getMessage());
           model.addAttribute("water", waterDTO);
           log.info("Exiting editWaterInfo() method");
           return WATER_EDIT_PAGE;
       }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteWaterInfo(@PathVariable("id") Long id, HttpSession httpSession, final RedirectAttributes redirectAttributes) {
        log.info("Entering deleteWaterInfo() method");
        log.info("Water Info ID : "+id);

        AppResponse appResponse = waterService.deleteWaterInfo(id);
        redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());

        log.info("Exiting deleteSleepInfo() method");
        return REDIRECT_WATER_LIST_PAGE;
    }

}
