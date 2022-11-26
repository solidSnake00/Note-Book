package com.example.Note.Book.controller;

import com.example.Note.Book.model.Note;
import com.example.Note.Book.model.User;
import com.example.Note.Book.service.NoteService;
import com.example.Note.Book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final NoteService noteService;

    public UserController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }
    @GetMapping("/")
    public String displayRegisterFormHome(){
        return "redirect:/register";
    }

    @GetMapping("/register")
    public String displayRegisterForm(Model model){
        User user=new User();
        model.addAttribute("user",user);

        return "registration";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "registration";
        }
        Boolean b = userService.createUser(user);
        if (!b){
            return "redirect:/register?unique=1";
        }

        return "redirect:/login-user";
    }

    @GetMapping("/login-user")
    public String displayLoginForm(Model model){
        User user=new User();
        model.addAttribute("user",user);

        return "login";
    }
    @PostMapping("/login-user")
    public String loginAuthentication(@ModelAttribute("user") User user, HttpSession httpSession){
        if(!userService.usernameExist(user.getUsername())){
            return "redirect:/login-user?pas=2";
        }
        User user1=userService.getUserByUsername(user.getUsername());

        if(user.getPassword().equals(user1.getPassword())){
            System.out.println("Password matched");
            httpSession.setAttribute("userSession",user1.getId());


        }
        else{
            System.out.println("Password invalid");
            return "redirect:/login-user?pas=1";
        }


        return "redirect:/home";
    }

    @GetMapping("/home")
    public String displaySession(Model model,HttpSession httpSession){

        User user=userService.getUserById((Long) httpSession.getAttribute("userSession"));
        Note note=new Note();
        model.addAttribute("note",note);
        model.addAttribute("user",user);
        List<Note> noteList=noteService.getAllNotesByUser(user.getId());
        model.addAttribute("notes",noteList);

        return "profile";
    }

    @PostMapping("/home")
    public  String createNote(@ModelAttribute("note") Note note, HttpSession httpSession){
        User user=userService.getUserById((Long) httpSession.getAttribute("userSession"));
        //httpSession.setAttribute("noteIdSession",note);

        noteService.createNote(note, (Long) httpSession.getAttribute("userSession"));
        return "redirect:/home";

    }


    @DeleteMapping("/home")
    public String deleteNote(@RequestParam long id){
        noteService.deleteNote(id);
        return "redirect:/home";

    }
    @GetMapping("/image")
    @ResponseBody
    public String getImage() throws IOException {

        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.setColor(Color.blue);
        g.fillRect(0, 0, 50, 100);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);

        byte[] encoded = Base64.getEncoder().encode(bos.toByteArray());
        return new String(encoded,"UTF8");
    }

    @GetMapping("/html")
    @ResponseBody
    public String getHtml() throws IOException {
        return "<img src=\"data:image/png;base64, " + getImage() + "\" alt=\"blue square\">";
    }

    @PostMapping("/home-update")
    public String buttonUpdateFrom(@RequestParam long id,HttpSession httpSession){
        httpSession.setAttribute("noteIdSession",id);

        return "redirect:/update";
    }

    @GetMapping("/update")
    public String displayUpdateForm(Model model, HttpSession httpSession){
        Note note=noteService.getNoteById((Long) httpSession.getAttribute("noteIdSession"));

        model.addAttribute("note",note);

        return "update-note";
    }

    @PutMapping("/update")
    public String updateNote(@ModelAttribute("note") Note note, HttpSession httpSession){
        Note note1=noteService.getNoteById((Long) httpSession.getAttribute("noteIdSession"));
        note1.setText(note.getText());



        noteService.updateNote(note1);

        return "redirect:/home";
    }

    @GetMapping("/home/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/login-user";
    }
}