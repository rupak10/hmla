package com.app.controller;

import com.app.dto.AppResponse;
import com.app.dto.CalorieDTO;
import com.app.dto.ExerciseDTO;
import com.app.service.CalorieService;
import com.app.service.ExerciseService;
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
@RequestMapping("/exercise")
public class ExerciseTrackerController {
    private final Logger log = LoggerFactory.getLogger(ExerciseTrackerController.class);

    private final String EXERCISE_ADD_PAGE = "exercise/exercise-add";
    private final String EXERCISE_EDIT_PAGE = "exercise/exercise-edit";
    private final String EXERCISE_LIST_PAGE = "exercise/exercise-list";
    private final String REDIRECT_EXERCISE_LIST_PAGE = "redirect:/exercise";
    private final String ACTIVE_MENU = "exercise";

    @Autowired
    private ExerciseService exerciseService;

    @RequestMapping(method = RequestMethod.GET)
    public String loadExerciseListPage(Model model, HttpSession httpSession) {
        log.info("Entering loadExerciseListPage() method");
        model.addAttribute("pageTitle", "Calorie");

        model.addAttribute("exerciseList", exerciseService.getExerciseInfoList(CommonUtil.getUserFromSession(httpSession)));
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadExerciseListPage() method");
        return EXERCISE_LIST_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String loadExerciseAddPage(Model model, HttpSession httpSession) {
        log.info("Entering loadExerciseAddPage() method");

        model.addAttribute("pageTitle", "Exercise Add");
        model.addAttribute("exercise", new ExerciseDTO());

        model.addAttribute("am", ACTIVE_MENU);
        log.info("Exiting loadExerciseAddPage() method");
        return EXERCISE_ADD_PAGE;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addExercise(Model model, HttpSession httpSession, @ModelAttribute ExerciseDTO exerciseDTO,
                             final RedirectAttributes redirectAttributes) {
        log.info("Entering addExercise() method");
        log.info("exerciseDTO request:"+exerciseDTO);

        AppResponse appResponse = exerciseService.addExercise(exerciseDTO, CommonUtil.getUserFromSession(httpSession));
        if(appResponse.getStatus()){
            redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
            log.info("Exiting addExercise() method");
            return REDIRECT_EXERCISE_LIST_PAGE;
        }
        else{
            model.addAttribute("msg", appResponse.getMessage());
            model.addAttribute("exercise", exerciseDTO);
            log.info("Exiting addExercise() method");
            return EXERCISE_ADD_PAGE;
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String loadExerciseEditPage(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        log.info("Entering loadExerciseEditPage() method");
        log.info("Exercise ID : "+id);

        ExerciseDTO exerciseDTO = exerciseService.fetchExerciseById(id);

        model.addAttribute("pageTitle", "Exercise Edit");
        model.addAttribute("exercise", exerciseDTO);
        model.addAttribute("am", ACTIVE_MENU);

        log.info("Exiting loadExerciseEditPage() method");
        return EXERCISE_EDIT_PAGE;
    }

   @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editExercise(Model model, @ModelAttribute ExerciseDTO exerciseDTO, HttpSession httpSession,
                              final RedirectAttributes redirectAttributes) {
        log.info("Entering editExercise() method");

        AppResponse appResponse = exerciseService.updateExerciseInfo(exerciseDTO, CommonUtil.getUserFromSession(httpSession));
       if(appResponse.getStatus()){
           redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());
           log.info("Exiting editExercise() method");
           return REDIRECT_EXERCISE_LIST_PAGE;
       }
       else{
           model.addAttribute("msg", appResponse.getMessage());
           model.addAttribute("exercise", exerciseDTO);
           log.info("Exiting editExercise() method");
           return EXERCISE_EDIT_PAGE;
       }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteExerciseInfo(@PathVariable("id") Long id, HttpSession httpSession, final RedirectAttributes redirectAttributes) {
        log.info("Entering deleteExerciseInfo() method");
        log.info("Exercise ID : "+id);

        AppResponse appResponse = exerciseService.deleteExerciseInfo(id);
        redirectAttributes.addFlashAttribute("activity_msg", appResponse.getMessage());

        log.info("Exiting deleteExerciseInfo() method");
        return REDIRECT_EXERCISE_LIST_PAGE;
    }

}
