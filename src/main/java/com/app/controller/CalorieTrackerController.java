package com.app.controller;

import com.app.dto.AppResponse;
import com.app.dto.CalorieDTO;
import com.app.service.CalorieService;
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
@RequestMapping("/calories")
public class CalorieTrackerController {
    private final Logger log = LoggerFactory.getLogger(CalorieTrackerController.class);

    private final String CALORIE_ADD_PAGE = "calorie/calorie-add";
    private final String CALORIE_EDIT_PAGE = "calorie/calorie-edit";
    private final String CALORIE_LIST_PAGE = "calorie/calorie-list";
    private final String REDIRECT_CALORIE_LIST_PAGE = "redirect:/calories";

    private final String ACTIVE_MENU = "calorie";

    @Autowired
    private CalorieService calorieService;

    @RequestMapping(method = RequestMethod.GET)
    public String loadCalorieListPage(Model model, HttpSession httpSession) {
        log.info("Entering loadCalorieListPage() method");
        model.addAttribute("pageTitle", "Calorie");

        model.addAttribute("calorieList", calorieService.getCalorieInfoList(CommonUtil.getUserFromSession(httpSession)));
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadCalorieListPage() method");
        return CALORIE_LIST_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String loadCalorieAddPage(Model model, HttpSession httpSession) {
        log.info("Entering loadCalorieAddPage() method");

        model.addAttribute("pageTitle", "Add Calorie");
        model.addAttribute("calorie", new CalorieDTO());

        model.addAttribute("am", ACTIVE_MENU);
        log.info("Exiting loadCalorieAddPage() method");
        return CALORIE_ADD_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCalorie(Model model, HttpSession httpSession, @ModelAttribute CalorieDTO calorieDTO,
                             final RedirectAttributes redirectAttributes) {
        log.info("Entering addCalorie() method");
        log.info("calorieDTO request:"+calorieDTO);

        AppResponse appResponse = calorieService.addCalorie(calorieDTO, CommonUtil.getUserFromSession(httpSession));
        if(appResponse.getStatus()){
            redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
            log.info("Exiting addCalorie() method");
            return REDIRECT_CALORIE_LIST_PAGE;
        }
        else{
            model.addAttribute("msg", appResponse.getMessage());
            model.addAttribute("calorie", calorieDTO);
            log.info("Exiting addCalorie() method");
            return CALORIE_ADD_PAGE;
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String loadCalorieEditPage(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        log.info("Entering loadCalorieEditPage() method");
        log.info("Calorie ID : "+id);

        CalorieDTO calorieDTO = calorieService.fetchCalorieById(id);

        model.addAttribute("pageTitle", "Edit Calorie");
        model.addAttribute("calorie", calorieDTO);
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadCalorieEditPage() method");
        return CALORIE_EDIT_PAGE;
    }

   @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editCalorie(Model model, @ModelAttribute CalorieDTO calorieDTO, HttpSession httpSession,
                              final RedirectAttributes redirectAttributes) {
        log.info("Entering editCalorie() method");

        AppResponse appResponse = calorieService.updateCalorieInfo(calorieDTO, CommonUtil.getUserFromSession(httpSession));
       if(appResponse.getStatus()){
           redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
           log.info("Exiting editCalorie() method");
           return REDIRECT_CALORIE_LIST_PAGE;
       }
       else{
           model.addAttribute("msg", appResponse.getMessage());
           model.addAttribute("calorie", calorieDTO);
           log.info("Exiting editCalorie() method");
           return CALORIE_EDIT_PAGE;
       }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteCalorieInfo(@PathVariable("id") Long id, HttpSession httpSession, final RedirectAttributes redirectAttributes) {
        log.info("Entering deleteCalorieInfo() method");
        log.info("Calorie ID : "+id);

        AppResponse appResponse = calorieService.deleteCalorieInfo(id);
        redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());

        log.info("Exiting deleteCalorieInfo() method");
        return REDIRECT_CALORIE_LIST_PAGE;
    }

}
