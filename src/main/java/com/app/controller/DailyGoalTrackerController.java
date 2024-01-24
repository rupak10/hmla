package com.app.controller;

import com.app.dto.AppResponse;
import com.app.dto.DailyGoalDTO;
import com.app.dto.WaterDTO;
import com.app.service.DailyGoalService;
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
@RequestMapping("/dailygoal")
public class DailyGoalTrackerController {
    private final Logger log = LoggerFactory.getLogger(DailyGoalTrackerController.class);

    private final String DAILYGOAL_ADD_PAGE = "dailygoal/dailygoal-add";
    private final String DAILYGOAL_EDIT_PAGE = "dailygoal/dailygoal-edit";
    private final String DAILYGOAL_LIST_PAGE = "dailygoal/dailygoal-list";
    private final String REDIRECT_DAILYGOAL_LIST_PAGE = "redirect:/dailygoal";
    private final String ACTIVE_MENU = "dailygoal";

    @Autowired
    private DailyGoalService dailyGoalService;

    @RequestMapping(method = RequestMethod.GET)
    public String loadDalyGoalListPage(Model model, HttpSession httpSession) {
        log.info("Entering loadDalyGoalListPage() method");
        model.addAttribute("pageTitle", "Daly Goal");

        model.addAttribute("dailyGoalList", dailyGoalService.getDailyGoalInfoList(CommonUtil.getUserFromSession(httpSession)));
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadDalyGoalListPage() method");
        return DAILYGOAL_LIST_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String loadDailyGoalAddPage(Model model, HttpSession httpSession) {
        log.info("Entering loadDailyGoalAddPage() method");

        model.addAttribute("pageTitle", "Daily Goal Add");
        model.addAttribute("dailygoal", new DailyGoalDTO());

        model.addAttribute("am", ACTIVE_MENU);
        log.info("Exiting loadDailyGoalAddPage() method");
        return DAILYGOAL_ADD_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addDailyGoalInfo(Model model, HttpSession httpSession, @ModelAttribute DailyGoalDTO dailyGoalDTO,
                             final RedirectAttributes redirectAttributes) {
        log.info("Entering addDailyGoalInfo() method");
        log.info("dailyGoalDTO request:"+dailyGoalDTO);

        AppResponse appResponse = dailyGoalService.addDailyGoalInfo(dailyGoalDTO, CommonUtil.getUserFromSession(httpSession));
        if(appResponse.getStatus()){
            redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
            log.info("Exiting addDailyGoalInfo() method");
            return REDIRECT_DAILYGOAL_LIST_PAGE;
        }
        else{
            model.addAttribute("msg", appResponse.getMessage());
            model.addAttribute("dailygoal", dailyGoalDTO);
            log.info("Exiting addDailyGoalInfo() method");
            return DAILYGOAL_ADD_PAGE;
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String loadDailyGoalEditPage(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        log.info("Entering loadDailyGoalEditPage() method");
        log.info("Daily Goal Info ID : "+id);

        DailyGoalDTO dailyGoalDTO = dailyGoalService.fetchDailyGoalInfoById(id);

        model.addAttribute("pageTitle", "Daily Goal Edit");
        model.addAttribute("dailygoal", dailyGoalDTO);
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadDailyGoalEditPage() method");
        return DAILYGOAL_EDIT_PAGE;
    }

   @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editDailyGoalInfo(Model model, @ModelAttribute DailyGoalDTO dailyGoalDTO, HttpSession httpSession,
                              final RedirectAttributes redirectAttributes) {
        log.info("Entering editDailyGoalInfo() method");
        AppResponse appResponse = dailyGoalService.updateDailyGoalInfo(dailyGoalDTO, CommonUtil.getUserFromSession(httpSession));
       if(appResponse.getStatus()){
           redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
           log.info("Exiting editDailyGoalInfo() method");
           return REDIRECT_DAILYGOAL_LIST_PAGE;
       }
       else{
           model.addAttribute("msg", appResponse.getMessage());
           model.addAttribute("dailygoal", dailyGoalDTO);
           log.info("Exiting editDailyGoalInfo() method");
           return DAILYGOAL_EDIT_PAGE;
       }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteDailyGoalInfo(@PathVariable("id") Long id, HttpSession httpSession, final RedirectAttributes redirectAttributes) {
        log.info("Entering deleteDailyGoalInfo() method");
        log.info("Daily Goal Info ID : "+id);

        AppResponse appResponse = dailyGoalService.deleteDailyGoalInfo(id);
        redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());

        log.info("Exiting deleteDailyGoalInfo() method");
        return REDIRECT_DAILYGOAL_LIST_PAGE;
    }

}
