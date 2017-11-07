package com.schools.blackjack.controller;

import com.schools.blackjack.model.CardTable;
import com.schools.blackjack.model.Hand;
import com.schools.blackjack.model.Player;
import com.schools.blackjack.service.ShoeService;
import com.schools.blackjack.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {
    @Autowired
    TableService tableService;

    @Autowired
    ShoeService shoeService;

    CardTable cardTable = new CardTable();

    @RequestMapping(path ="/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(path = "/cardTable", method = RequestMethod.GET)
    public String showTable(Model model) {
        model.addAttribute("table", cardTable);
        return "cardTable";
    }

    @RequestMapping(path = "/index", method = RequestMethod.POST)
    public String start(@RequestParam(value="num-players") int num) {
        cardTable = tableService.initializeTable(num);
        cardTable.setShoe(shoeService.loadShoe(cardTable.getShoe()));
        cardTable.setShoe(shoeService.shuffleShoe(cardTable.getShoe()));
        CardTable tempTable = tableService.dealCards(cardTable);
        cardTable = tempTable;
        return "redirect:/cardTable";
    }

    @RequestMapping(path = "/cardTable", method = RequestMethod.POST)
    public String buttonClicked(@RequestParam(value = "actionButton") String action) {
        if (action.equals("hit") || action.equals("double")) {
            Hand hand = cardTable.getPlayers().get(cardTable.getCurrentPlayer()).getHands().get(cardTable.getPlayers().get(cardTable.getCurrentPlayer()).getCurrentHand());
            Hand hitHand = cardTable.hit(hand);
            List<Hand> hands = cardTable.getPlayers().get(cardTable.getCurrentPlayer()).getHands();
            if (action.equals("double")) {
                hitHand.setDoubleDown(true);
                hitHand.setMessage(hitHand.getMessage() + "Double Down");
            }
            hands.remove(cardTable.getPlayers().get(cardTable.getCurrentPlayer()).getHands().get(cardTable.getPlayers().get(cardTable.getCurrentPlayer()).getCurrentHand()));
            hands.add(hitHand);
            cardTable.getPlayers().get(cardTable.getCurrentPlayer()).setHands(hands);
            if (hitHand.isBust() || hitHand.getTotal() == 21 || hitHand.isDoubleDown()) {
                cardTable.stand();
            }
        } else if (action.equals("stand")) {
            cardTable.stand();
        } else if (action.equals("split")) {
            Player current = cardTable.getPlayers().get(cardTable.getCurrentPlayer());
            current.split();
        }
        return "redirect:/cardTable";
    }

    @RequestMapping(path = "/dealCards", method = RequestMethod.POST)
    public String deal() {
        CardTable tempTable = tableService.dealCards(cardTable);
        cardTable = tempTable;
        return "redirect:/cardTable";
    }
}
