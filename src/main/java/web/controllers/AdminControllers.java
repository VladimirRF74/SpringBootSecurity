package web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.dao.RoleDao;
import web.models.User;
import web.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminControllers {

    private final UserService userService;
    private final RoleDao roleDao;


    public AdminControllers(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }
    @GetMapping(value = "/admin")
    public String admin(ModelMap modelMap, Authentication authentication) {
        modelMap.addAttribute("userList", userService.getAllUsers());
        //modelMap.addAttribute("roles", authentication.getAuthorities());
        return "admin/admin";
    }

    @GetMapping(value = "/add")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/add";
    }

    @PostMapping(value = "/add")
    public String create(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/admin";
    }

    @GetMapping(value = "/modify/{id}")
    public String modifyUser(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("message", userService.findUserById(id));
        return "admin/modify";
    }

    @PostMapping(value = "/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/admin";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.findUserById(id));
        return "admin/edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/admin";
    }
}
