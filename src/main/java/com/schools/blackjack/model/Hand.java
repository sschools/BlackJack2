package com.schools.blackjack.model;

import java.util.List;

public class Hand {
    private int total;
    private List<Card> cards;
    private boolean ace;
    private boolean bust;
    private double win;
    private String message;
    private boolean doubleDown;
    private boolean active;
    private boolean soft;

    public Hand() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = 0;
        this.setSoft(false);
        for (Card card : this.getCards()) {
            this.total += card.getValue();
        }
        if (this.ace && this.total < 12) {
            this.total += 10;
            this.setSoft(true);
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public boolean isAce() {
        return ace;
    }

    public double getWin() {
        return win;
    }

    public void setWin(double win) {
        this.win = win;
    }

    public void setAce(boolean ace) {
        this.ace = ace;
    }

    public boolean isBust() {
        return bust;
    }

    public void setBust(boolean bust) {
        this.bust = bust;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDoubleDown() {
        return doubleDown;
    }

    public void setDoubleDown(boolean doubleDown) {
        this.doubleDown = doubleDown;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSoft() {
        return soft;
    }

    public void setSoft(boolean soft) {
        this.soft = soft;
    }

    public boolean blackJack() {
        boolean temp;
        int first = 0;
        int second = 0;
        first = this.cards.get(0).getValue();
        second = this.cards.get(1).getValue();
        temp = (first == 1 && second == 10) || (second == 1 && first == 10);
        return temp;
    }

    public void hit(Shoe shoe) {
        Card newCard = shoe.getNextCard();
        if (newCard.getValue() == 1) {
            this.setAce(true);
        }
        this.getCards().add(newCard);
        this.setTotal();
        if (this.getTotal() > 21) {
            this.setBust(true);
        }
        shoe.setIndex(shoe.getIndex() + 1);
    }

    public String decisionWith2Cards(int dealerUpCard) {
        String action = "";
        if (this.getCards().get(0).getValue() == this.getCards().get(1).getValue()) {
            if (this.decideSplit(dealerUpCard)) {
                action = "split";
            } else {
                switch (this.getCards().get(0).getValue()) {
                    case 10:
                    case 9:
                        action = "stand";
                        break;
                    case 5:
                        if (dealerUpCard == 10 || dealerUpCard == 1) {
                            action = "stand";
                        } else {
                            action = "double";
                        }
                        break;
                    default:
                        action = "hit";
                        break;
                }
            }
        } else if (this.isAce()) {
            action = decideWithAce(dealerUpCard);
        } else {
            action = decideWithPlainCards(dealerUpCard);
        }
        return action;
    }

    public boolean decideSplit(int dealerUpCard) {
        boolean test = false;
        switch (this.getCards().get(0).getValue()) {
            case 1:
            case 8:
                test = true;
                break;
            case 10:
            case 5:
                test = false;
                break;
            case 9:
                test = !(dealerUpCard == 1 || dealerUpCard == 7 || dealerUpCard == 10);
                break;
            case 7:
            case 3:
            case 2:
                test = (dealerUpCard > 1 && dealerUpCard < 8);
                break;
            case 6:
                test = (dealerUpCard > 1 && dealerUpCard < 7);
            case 4:
                test = (dealerUpCard > 4 && dealerUpCard < 7);
                break;
        }
        return test;
    }

    public String decideWithAce(int dealerUpCard) {
        String action = "";
        switch (this.getTotal()) {
            case 21:
            case 20:
            case 19:
                action = "stand";
                break;
            case 18:
                if (dealerUpCard > 2 && dealerUpCard < 7) {
                    action = "double";
                } else if (dealerUpCard > 1 && dealerUpCard < 8) {
                    action = "stand";
                } else {
                    action = "hit";
                }
                break;
            case 17:
                if (dealerUpCard > 2 && dealerUpCard < 7) {
                    action = "double";
                } else {
                    action = "hit";
                }
                break;
            case 16:
            case 15:
                if (dealerUpCard > 3 && dealerUpCard < 7) {
                    action = "double";
                } else {
                    action = "hit";
                }
                break;
            case 14:
            case 13:
                if (dealerUpCard > 4 && dealerUpCard < 7) {
                    action = "double";
                } else {
                    action = "hit";
                }
                break;
        }
        return action;
    }

    public String decideWithPlainCards(int dealerUpCard) {
        String action = "";
        switch (this.getTotal()) {
            case 20:
            case 19:
            case 18:
            case 17:
                action = "stand";
                break;
            case 16:
            case 15:
            case 14:
            case 13:
                if (dealerUpCard > 1 && dealerUpCard < 7) {
                    action = "stand";
                } else {
                    action = "hit";
                }
                break;
            case 12:
                if (dealerUpCard > 3 && dealerUpCard < 7) {
                    action = "stand";
                } else {
                    action = "hit";
                }
                break;
            case 11:
                if (dealerUpCard > 1) {
                    action = "double";
                } else {
                    action = "hit";
                }
                break;
            case 10:
                if (dealerUpCard > 1 && dealerUpCard < 10) {
                    action = "double";
                } else {
                    action = "hit";
                }
                break;
            case 9:
                if (dealerUpCard > 2 && dealerUpCard < 7) {
                    action = "double";
                } else {
                    action = "hit";
                }
                break;
            default:
                action = "hit";
                break;
        }
        return action;
    }

    public String decisionWithMultipleCards(int dealerUpCard) {
        String action = "";
        if (this.getTotal() >= 21) {
            action = "stand";
        } else if (!this.isSoft()) {
            switch (this.getTotal()) {
                case 20:
                case 19:
                case 18:
                case 17:
                    action = "stand";
                    break;
                case 16:
                case 15:
                case 14:
                case 13:
                    if (dealerUpCard > 1 && dealerUpCard < 7) {
                        action = "stand";
                    } else {
                        action = "hit";
                    }
                    break;
                case 12:
                    if (dealerUpCard > 3 && dealerUpCard < 7) {
                        action = "stand";
                    } else {
                        action = "hit";
                    }
                    break;
                default:
                    action = "hit";
                    break;
            }
        } else {
            switch (this.getTotal()) {
                case 11:
                case 10:
                case 9:
                    action = "stand";
                    break;
                case 8:
                    if (dealerUpCard == 2 || dealerUpCard == 7 || dealerUpCard == 8) {
                        action = "stand";
                    } else {
                        action = "hit";
                    }
                    break;
                default:
                    action = "hit";
                    break;
            }
        }

        return action;
    }
}
