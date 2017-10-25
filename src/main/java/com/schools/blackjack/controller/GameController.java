package com.schools.blackjack.controller;

import com.schools.blackjack.model.Table;
import com.schools.blackjack.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {
    @Autowired
    TableService tableService;

    @RequestMapping(path ="/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(path = "/index", method = RequestMethod.POST)
    public String start(@RequestParam(value="num-players") int num) {
        Table table = tableService.initializeTable(num);

        return "redirect:table";
    }
}
