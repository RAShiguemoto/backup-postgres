package com.ras.backuppostgres.controller;

import com.ras.backuppostgres.model.Registro;
import com.ras.backuppostgres.repository.RegistroRepository;
import com.ras.backuppostgres.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/registros")
public class RegistroController {
    @Autowired
    RegistroRepository registroRepository;
    @Autowired
    RegistroService registroService;

    @GetMapping("")
    public ModelAndView list() {
        List<Registro> registros = registroRepository.findAll();

        ModelAndView mv = new ModelAndView("registros/list");
        mv.addObject("registros", registros);
        return mv;
    }

    @GetMapping("/new")
    public String nnew() {
        try {
            registroService.startBackup();
            return "redirect:/registros";

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
