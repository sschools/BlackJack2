package com.schools.blackjack.controller;

import com.schools.blackjack.model.CardTable;
import com.schools.blackjack.model.Hand;
import com.schools.blackjack.model.Player;
import com.schools.blackjack.service.StatService;
import com.schools.blackjack.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GameController {

    private TableService tableService;
    private StatService statService;

    @Autowired
    public GameController(TableService tableService, StatService statService) {
        this.tableService = tableService;
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
        cardTable = tableService.initializeTable(num);
        cardTable.setShoe((cardTable.getShoe().loadShoe()));
        cardTable.setShoe((cardTable.getShoe().shuffleShoe()));
        cardTable.setStats();
        CardTable tempTable = tableService.dealCards(cardTable);
        cardTable = tempTable;
        return "redirect:/cardTable";
    }

    @PostMapping(path = "/cardTable")
    public String buttonClicked(@RequestParam(value = "actionButton") String action) {
        if (action.equals("hit") || action.equals("double")) {
            int currentPlayerNum = cardTable.getCurrentPlayer();
            int currentHandNum = cardTable.getPlayers().get(currentPlayerNum).getCurrentHand();
            Hand hand = cardTable.getPlayers().get(currentPlayerNum).getHands().get(currentHandNum);
            Hand hitHand = cardTable.hit(hand);
            List<Hand> hands = cardTable.getPlayers().get(currentPlayerNum).getHands();
            if (action.equals("double")) {
                hitHand.setDoubleDown(true);
                hitHand.setMessage(hitHand.getMessage() + "Double Down");
            }
            hands.remove(cardTable.getPlayers().get(currentPlayerNum).getHands().get(currentHandNum));
            hands.add( currentHandNum, hitHand);
            cardTable.getPlayers().get(currentPlayerNum).setHands(hands);
            cardTable.getPlayers().get(currentPlayerNum).setButtons();
            if (hitHand.isBust() || hitHand.getTotal() == 21 || hitHand.isDoubleDown()) {
                cardTable.stand();
            }
        } else if (action.equals("stand")) {
            cardTable.stand();
        } else if (action.equals("split")) {
            Player current = cardTable.getPlayers().get(cardTable.getCurrentPlayer());
            current.setSplitHands(true);
            cardTable.getShoeStat().setNumHands(cardTable.getShoeStat().getNumHands() + 1);
            current.split();
        }
        return "redirect:/cardTable";
    }

    @PostMapping(path = "/dealCards")
    public String deal() {
        CardTable tempTable = tableService.dealCards(cardTable);
        cardTable = tempTable;
        return "redirect:/cardTable";
    }

    @PostMapping(path = "/seeStats")
    public String statPage() {
        return "redirect:/shoeSummary";
    }

    @PostMapping(path = "/shuffleCards")
    public String shuffle() {
        statService.add(cardTable.getShoeStat());
        cardTable.setShoe((cardTable.getShoe().shuffleShoe()));
        cardTable.setStats();
        CardTable tempTable = tableService.dealCards(cardTable);
        cardTable = tempTable;
        cardTable.setMessage("");
        cardTable.setEndShoe(false);
        return "redirect:/cardTable";
    }
}
