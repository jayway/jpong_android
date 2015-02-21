package com.jayway.pong;

public class MatchResult {

    public String namePlayer1;

    public String namePlayer2;

    public int scorePlayer1;

    public int scorePlayer2;

    public MatchResult(String namePlayer1, String namePlayer2, int scorePlayer1, int scorePlayer2) {
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
    }
}
