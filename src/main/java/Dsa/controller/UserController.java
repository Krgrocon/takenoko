package Dsa.controller;


import Dsa.entity.UserData;
import Dsa.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {


    private final UserService userService;

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


    @PostMapping("/users/new")
    public String create(UserData form, Model model) {
        UserData userdata = new UserData();
        userdata.setNickname(form.getNickname());
        userdata.setEmail(form.getEmail());
        userdata.setPassword(form.getPassword());
        userdata.setAddress(form.getAddress());
        userdata.setPhone(form.getPhone());

//        boolean isPassword= userService.PasswordVerification(userData.getPassword());
//        boolean isUsername= userService.usernameVerification(userData.getUsername());

//        if(!isUsername) {
//            model.addAttribute("errorMessage", "아이디를 형식에 맞춰 입력해주세요");
//            return "members/createMemberForm";
//        }
//        if(!isPassword){
//            model.addAttribute("errorMessage", "패스워드를 형식에 맞춰 입력해주세요");
//            return "members/createMemberForm";
//        }


        try {
            userService.save(userdata);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "이미 가입된 아이디 입니다.");
            return "users/new"; // 회원 가입 폼으로 다시 이동
        }
        return "redirect:/";
    }


}
