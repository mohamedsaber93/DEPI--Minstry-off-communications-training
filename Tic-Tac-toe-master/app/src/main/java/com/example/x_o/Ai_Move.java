package com.example.x_o;

import java.util.ArrayList;
import java.util.Random;

public class Ai_Move {
    private char[] board;
    private char ai;
    private char player;

    public Ai_Move(char[] board, char ai, char player) {
        this.board = board;
        this.ai = ai;
        this.player = player;
    }

    public int getAIMove(String difficulty,char bo[]) {
        board = bo;
        switch (difficulty) {
            case "1":
                return easyAIMove();
            case "2":
                return mediumAIMove();
            case "3":
                return hardAIMove(); // Assuming you have implemented hard level with Minimax
            default:
                return 0;
        }
    }

    private int easyAIMove() {
        ArrayList<Integer> availableMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                availableMoves.add(i);
            }
        }
        Random rand = new Random();
        return availableMoves.get(rand.nextInt(availableMoves.size()));
    }

    private int mediumAIMove() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = ai;
                if (checkWin(ai)) {
                    board[i] = ' ';
                    return i;
                }
                board[i] = ' ';
            }
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = player;
                if (checkWin(player)) {
                    board[i] = ' ';
                    return i;
                }
                board[i] = ' ';
            }
        }
        return easyAIMove();
    }

    public int hardAIMove() {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = ai;
                int score = minimax(0, false, ai, player);
                board[i] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        return move;
    }
    private int minimax(int depth, boolean isMaximizing, char ai, char player) {
        if (checkWin(ai)) return 10 - depth;
        if (checkWin(player)) return depth - 10;
        if (isBoardFull(board)) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == ' ') {
                    board[i] = ai;
                    int score = minimax(depth + 1, false, ai, player);
                    board[i] = ' ';
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == ' ') {
                    board[i] = player;
                    int score = minimax(depth + 1, true, ai, player);
                    board[i] = ' ';
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    public boolean isBoardFull(char[] bod) {
        for (char c : bod) {
            if (c == ' ') return false;
        }
        return true;
    }
    public boolean checkWin(char currentPlayer) {
        int[][] winPatterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}              // Diagonals
        };

        for (int[] pattern : winPatterns) {
            if (board[pattern[0]] == currentPlayer && board[pattern[1]] == currentPlayer && board[pattern[2]] == currentPlayer) {
                return true;
            }
        }
        return false;
    }
}
