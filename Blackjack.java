import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Card {
    private final String suit;
    private final String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String toString() {
        return rank + " of " + suit;
    }

    public int getValue() {
        switch (rank) {
            case "Ace":
                return 11;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            default:
                return 10;
        }
    }
}

class Deck {
    private final ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(0);
    }
}

class Hand {
    private final ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int calculateScore() {
        int score = 0;
        int numAces = 0;

        for (Card card : cards) {
            score += card.getValue();
            if (card.getValue() == 11) {
                numAces++;
            }
        }

        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }

    public void display() {
        for (Card card : cards) {
            System.out.println(card);
        }
    }
}

public class Blackjack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        // Initial dealing
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        // Player's turn
        System.out.println("Your cards:");
        playerHand.display();
        System.out.println("Your score: " + playerHand.calculateScore());

        while (playerHand.calculateScore() < 21) {
            System.out.print("Do you want to hit or stand? (h/s): ");
            char choice = scanner.next().charAt(0);

            if (choice == 'h') {
                playerHand.addCard(deck.drawCard());
                System.out.println("Your cards:");
                playerHand.display();
                System.out.println("Your score: " + playerHand.calculateScore());
            } else if (choice == 's') {
                break;
            }
        }

        // Dealer's turn
        System.out.println("Dealer's cards:");
        dealerHand.display();
        System.out.println("Dealer's score: " + dealerHand.calculateScore());

        while (dealerHand.calculateScore() < 17) {
            dealerHand.addCard(deck.drawCard());
            System.out.println("Dealer draws another card...");
            System.out.println("Dealer's cards:");
            dealerHand.display();
            System.out.println("Dealer's score: " + dealerHand.calculateScore());
        }

        // Determine the winner
        int playerScore = playerHand.calculateScore();
        int dealerScore = dealerHand.calculateScore();

        System.out.println("Final Results:");
        System.out.println("Your score: " + playerScore);
        System.out.println("Dealer's score: " + dealerScore);

        if (playerScore > 21) {
            System.out.println("You bust. Dealer wins!");
        } else if (dealerScore > 21 || playerScore > dealerScore) {
            System.out.println("You win!");
        } else if (playerScore < dealerScore) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
        }

        scanner.close();
    }
}
