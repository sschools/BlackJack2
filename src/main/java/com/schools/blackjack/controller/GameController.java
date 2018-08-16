package com.schools.blackjack.controller;

import com.schools.blackjack.model.CardTable;
import com.schools.blackjack.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {

    private StatService statService;

    @Autowired
    public GameController(StatService statService) {
        this.statService = statService;
    }

    private CardTable cardTable = new CardTable();

    @GetMapping(path ="/")
    public String index() {
        return "index";
    }

    @GetMapping(path = "/cardTable")
    public String showTable(Model model) {
        model.addAttribute("table", cardTable);
        return "cardTable";
    }

    @GetMapping(path = "/shoeSummary")
    public String shoeRes(Model model) {
        model.addAttribute("table", cardTable);
        return "shoeSummary";
    }

    @PostMapping(path = "/index")
    public String start(@RequestParam(value="num-players") int num) {
        cardTable.initializeTable(num, cardTable);
        return "redirect:/cardTable";
    }

    @PostMapping(path = "/cardTable")
    public String buttonClicked(@RequestParam(value = "actionButton") String action) {
        cardTable.doAction(action);
        return "redirect:/cardTable";
    }

    @PostMapping(path = "/dealCards")
    public String deal() {
        cardTable.dealCards();
        return "redirect:/cardTable";
    }

    @PostMapping(path = "/seeStats")
    public String statPage() {
        return "redirect:/shoeSummary";
    }

    @PostMapping(path = "/shuffleCards")
    public String shuffle() {
        statService.add(cardTable.getShoeStat());
        cardTable.resetShoe(cardTable.getShoe());
        return "redirect:/cardTable";
    }
}
