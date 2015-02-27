package com.jayway.pong.model;

public class Winning {

    public String winner;
    public int player1Points;
    public int player2Points;

    public Winning(String winner, int player1Points, int player2Points) {
        this.winner = winner;
        this.player1Points = player1Points;
        this.player2Points = player2Points;
    }

    @Override
    public String toString() {
        return "Winning: " + winner + " player1Points=" + player1Points + " player2Points=" + player2Points;
    }
}
