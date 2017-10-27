package com.schools.blackjack.controller;

import com.schools.blackjack.model.CardTable;
import com.schools.blackjack.service.ShoeService;
import com.schools.blackjack.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {
    @Autowired
    TableService tableService;

    @Autowired
    ShoeService shoeService;

    @RequestMapping(path ="/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(path = "/cardTable", method = RequestMethod.GET)
    public String table() {
        return "cardTable";
    }

    @RequestMapping(path = "/index", method = RequestMethod.POST)
    public String start(@RequestParam(value="num-players") int num) {
        CardTable cardTable = tableService.initializeTable(num);
        cardTable.setShoe(shoeService.loadShoe(cardTable.getShoe()));
        cardTable.setShoe(shoeService.shuffleShoe(cardTable.getShoe()));
        return "redirect:/cardTable";
    }
}
