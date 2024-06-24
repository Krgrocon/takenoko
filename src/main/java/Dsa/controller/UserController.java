package Dsa.controller;


import Dsa.entity.UserData;
import Dsa.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {


    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/index")
    public String index() {
        return "index";}


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model , HttpSession session){

        UserData user = userService.authenticate(email,password);
//        if(user == null){
//            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 틀렸습니다.");
//            return "redirect:/";
//        } 아직 예외처리안함
        session.setAttribute("loggedInUser", user);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loggedInUser");
        return "redirect:/";
    }

//    @PostMapping("/users/new")
//    public String create(UserData form, Model model) {
//
//        UserData userdata = new UserData();
//        userdata.setNickname(form.getNickname());
//        userdata.setEmail(form.getEmail());
//        userdata.setPassword(form.getPassword());
//        userdata.setAddress(form.getAddress());
//        userdata.setPhone(form.getPhone());
//
//        boolean isUsermail = userService.usernameVerification(form.getEmail());
//        if(isUsermail) {
//            model.addAttribute("errorMessage", "이미 가입된 아이디 입니다.");
//            return "redirect:/login";
//        }
//        try {
//            userService.save(userdata);
//        } catch (IllegalStateException e) {
//            model.addAttribute("errorMessage", "회원가입에 실패했습니다.");
//            return "redirect:/login";
//        }
//        return "redirect:/";
//    }

    @PostMapping("/users/new")
    public ResponseEntity<Map<String, Object>> create(@RequestBody UserData form) {
        Map<String, Object> response = new HashMap<>();
        UserData userdata = new UserData();
        userdata.setNickname(form.getNickname());
        userdata.setEmail(form.getEmail());
        userdata.setPassword(form.getPassword());
        userdata.setAddress(form.getAddress());
        userdata.setPhone(form.getPhone());

        boolean isUsermail = userService.usernameVerification(form.getEmail());
        if(isUsermail) {
            response.put("success", false);
            response.put("errorMessage", "이미 가입된 아이디 입니다.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            userService.save(userdata);
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalStateException e) {
            response.put("success", false);
            response.put("errorMessage", "회원가입에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
