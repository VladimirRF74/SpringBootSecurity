package web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping(value = "/users")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }
//доступ только к своей домашней странице /user, где выводятся его данные.
    @GetMapping("/info")
    public String getInfo(ModelMap modelMap, Principal principal, Authentication authentication) {
        modelMap.addAttribute("userInfo", userService.findUserByLogin(principal.getName()));
        modelMap.addAttribute("roles", authentication.getAuthorities());
        return "users/info";
    }

}
