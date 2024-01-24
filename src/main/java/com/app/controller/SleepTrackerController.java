package com.app.controller;

import com.app.dto.AppResponse;
import com.app.dto.SleepDTO;
import com.app.service.SleepService;
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
@RequestMapping("/sleep")
public class SleepTrackerController {
    private final Logger log = LoggerFactory.getLogger(SleepTrackerController.class);

    private final String SLEEP_ADD_PAGE = "sleep/sleep-add";
    private final String SLEEP_EDIT_PAGE = "sleep/sleep-edit";
    private final String SLEEP_LIST_PAGE = "sleep/sleep-list";
    private final String REDIRECT_SLEEP_LIST_PAGE = "redirect:/sleep";
    private final String ACTIVE_MENU = "sleep";

    @Autowired
    private SleepService sleepService;

    @RequestMapping(method = RequestMethod.GET)
    public String loadSleepListPage(Model model, HttpSession httpSession) {
        log.info("Entering loadSleepListPage() method");
        model.addAttribute("pageTitle", "Sleep Info");

        model.addAttribute("sleepList", sleepService.getSleepInfoList(CommonUtil.getUserFromSession(httpSession)));
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadSleepListPage() method");
        return SLEEP_LIST_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String loadSleepAddPage(Model model, HttpSession httpSession) {
        log.info("Entering loadSleepAddPage() method");

        model.addAttribute("pageTitle", "Sleep Add");
        model.addAttribute("sleep", new SleepDTO());

        model.addAttribute("am", ACTIVE_MENU);
        log.info("Exiting loadSleepAddPage() method");
        return SLEEP_ADD_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addSleepInfo(Model model, HttpSession httpSession, @ModelAttribute SleepDTO sleepDTO,
                             final RedirectAttributes redirectAttributes) {
        log.info("Entering addSleepInfo() method");
        log.info("sleepDTO request:"+sleepDTO);

        AppResponse appResponse = sleepService.addSleep(sleepDTO, CommonUtil.getUserFromSession(httpSession));
        if(appResponse.getStatus()){
            redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
            log.info("Exiting addExercise() method");
            return REDIRECT_SLEEP_LIST_PAGE;
        }
        else{
            model.addAttribute("msg", appResponse.getMessage());
            model.addAttribute("sleep", sleepDTO);
            log.info("Exiting addSleepInfo() method");
            return SLEEP_ADD_PAGE;
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String loadSleepEditPage(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        log.info("Entering loadSleepEditPage() method");
        log.info("Sleep Info ID : "+id);

        SleepDTO sleepDTO = sleepService.fetchSleepInfoById(id);

        model.addAttribute("pageTitle", "Sleep Edit");
        model.addAttribute("sleep", sleepDTO);
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadSleepEditPage() method");
        return SLEEP_EDIT_PAGE;
    }

   @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editSleepInfo(Model model, @ModelAttribute SleepDTO sleepDTO, HttpSession httpSession,
                              final RedirectAttributes redirectAttributes) {
        log.info("Entering editSleepInfo() method");
        AppResponse appResponse = sleepService.updateSleepInfo(sleepDTO, CommonUtil.getUserFromSession(httpSession));
       if(appResponse.getStatus()){
           redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
           log.info("Exiting editSleepInfo() method");
           return REDIRECT_SLEEP_LIST_PAGE;
       }
       else{
           model.addAttribute("msg", appResponse.getMessage());
           model.addAttribute("sleep", sleepDTO);
           log.info("Exiting editSleepInfo() method");
           return SLEEP_EDIT_PAGE;
       }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteSleepInfo(@PathVariable("id") Long id, HttpSession httpSession, final RedirectAttributes redirectAttributes) {
        log.info("Entering deleteSleepInfo() method");
        log.info("Sleep Info ID : "+id);

        AppResponse appResponse = sleepService.deleteSleepInfo(id);
        redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());

        log.info("Exiting deleteSleepInfo() method");
        return REDIRECT_SLEEP_LIST_PAGE;
    }

}
