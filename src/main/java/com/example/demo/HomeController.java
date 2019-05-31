package com.example.demo;


import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

  @Autowired
  MessagesRepository messagesRepository;

  @Autowired
  CloudinaryConfig cloudc;


  @Autowired
  UserService userService;

  @GetMapping("/register")
  public String showRegistrationPage(Model model) {
    model.addAttribute("user", new User());
    return "registration";
  }

  @PostMapping("/register")
  public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

    if (result.hasErrors()) {
      return "registration";
    } else {
      userService.saveUser(user);
      model.addAttribute("message", "User Account Created");
    }
    return "redirect:/";
  }

  @RequestMapping("/login")
  public String login() {
    return "login";
  }


  @RequestMapping("/")
  public String listMessages(Model model){
    model.addAttribute("messages", messagesRepository.findAll());
    if(userService.getUser() != null) {
      model.addAttribute("user_id", userService.getUser().getId());
    }
    return "list";
  }
  @GetMapping("/add")
  public String messageform(Model model){
    model.addAttribute("message", new Message());
    return"messageform";
  }

//  @PostMapping("/process")
//
//  public String processForm(@Valid Course course, BindingResult result, Model model) {
//    if (result.hasErrors()) {
//      model.addAttribute("instructors", instructorRepository.findAll());
//      return "courseform";
//  public String processForm(@Valid Course course, BindingResult result){
//    if(result.hasErrors()){

//    }

//    message.setUser(userService.getUser());
//    messagesRepository.save(message);
//    return "redirect:/";
//  }
//
  @PostMapping("/processform")
  public String processmessageform(@ModelAttribute Message message, @Valid @RequestParam("file")MultipartFile file, BindingResult result){
//    if(result.hasErrors()){
//      return "messageform";
//    }
//
//    if (file.isEmpty()){
//      return "redirect:/add";
//    }
//    try{
//      Map uploadResult = cloudc.upload(file.getBytes(),
//              ObjectUtils.asMap("resourcetype", "auto"));
//      message.setPic(uploadResult.get("url").toString());
//      messagesRepository.save(message);
//    }catch(IOException e){
//      e.printStackTrace();
//      return "redirect:/add";
//    }
    message.setUser(userService.getUser());
    messagesRepository.save(message);
    return "redirect:/";
  }
  @RequestMapping("/view/{id}")
  public String showMessage(@PathVariable("id") long id, Model model){
    model.addAttribute("message", messagesRepository.findById(id).get());
    return "show";
  }
  @RequestMapping("/update/{id}")
  public String updateMessage(@PathVariable("id") long id, Model model){
    model.addAttribute("message", messagesRepository.findById(id).get());
    return "messageform";
  }
  @RequestMapping("/delete/{id}")
  public String delMessage(@PathVariable("id") long id){
    messagesRepository.deleteById(id);
    return "redirect:/";
  }




}


