import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {

    private static final int ROW = 3;
    private static final int COL = 3;
    private static final int MOVES_FOR_WIN = 5;
    private static final int MOVES_FOR_TIE = 7;

    // Panels
    JPanel mainPnl;
    JPanel gamePnl;
    JPanel controlPnl;

    // Components
    TicTacToeTile[][] board;
    JButton quitBtn;

    // State
    private String[][] gameBoard;
    private String player;
    private int moveCnt;
    private boolean playing;

    public TicTacToeFrame() {

        setTitle("Tic Tac Toe Game");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center

        gameBoard = new String[ROW][COL];
        player = "X";
        moveCnt = 0;
        playing = true;
        clearBoard();

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createGamePanel();
        createControlPanel();

        mainPnl.add(gamePnl, BorderLayout.CENTER);
        mainPnl.add(controlPnl, BorderLayout.SOUTH);

        add(mainPnl);
        setVisible(true);
    }

    private void createGamePanel() {
        gamePnl = new JPanel();
        gamePnl.setLayout(new GridLayout(ROW, COL));
        gamePnl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        board = new TicTacToeTile[ROW][COL];

        TileListener listener = new TileListener();

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = new TicTacToeTile(row, col);
                board[row][col].setFont(new Font("Arial", Font.BOLD, 24));
                board[row][col].setText(" ");
                board[row][col].addActionListener(listener);
                gamePnl.add(board[row][col]);
            }
        }
    }

    private void createControlPanel() {

        controlPnl = new JPanel();
        quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        quitBtn.addActionListener((ActionEvent ae) -> {
            int result = JOptionPane.showConfirmDialog(
                    this, "Do you want to quit the game?",
                    "Quit Game", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        controlPnl.add(quitBtn);
    }

    private void clearBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                gameBoard[row][col] = " ";
            }
        }
    }

    private void updateBoardDisplay() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col].setText(gameBoard[row][col]);
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return gameBoard[row][col].equals(" ");
    }

    private boolean isWin(String player) {
        return isColWin(player) || isRowWin(player) || isDiagonalWin(player);
    }

    private boolean isColWin(String player) {
        for (int col = 0; col < COL; col++) {
            if (gameBoard[0][col].equals(player) &&
                    gameBoard[1][col].equals(player) &&
                    gameBoard[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRowWin(String player) {
        for (int row = 0; row < ROW; row++) {
            if (gameBoard[row][0].equals(player) &&
                    gameBoard[row][1].equals(player) &&
                    gameBoard[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonalWin(String player) {
        if (gameBoard[0][0].equals(player) &&
                gameBoard[1][1].equals(player) &&
                gameBoard[2][2].equals(player)) {
            return true;
        }

        if (gameBoard[0][2].equals(player) &&
                gameBoard[1][1].equals(player) &&
                gameBoard[2][0].equals(player)) {
            return true;
        }

        return false;
    }

    private boolean isTie() {
        boolean xFlag = false;
        boolean oFlag = false;

        for (int row = 0; row < ROW; row++) {
            xFlag = oFlag = false;

            if (gameBoard[row][0].equals("X") ||
                    gameBoard[row][1].equals("X") ||
                    gameBoard[row][2].equals("X")) {
                xFlag = true;
            }

            if (gameBoard[row][0].equals("O") ||
                    gameBoard[row][1].equals("O") ||
                    gameBoard[row][2].equals("O")) {
                oFlag = true;
            }

            if (!(xFlag && oFlag)) {
                return false;
            }
        }

        for (int col = 0; col < COL; col++) {
            xFlag = oFlag = false;

            if (gameBoard[0][col].equals("X") ||
                    gameBoard[1][col].equals("X") ||
                    gameBoard[2][col].equals("X")) {
                xFlag = true;
            }

            if (gameBoard[0][col].equals("O") ||
                    gameBoard[1][col].equals("O") ||
                    gameBoard[2][col].equals("O")) {
                oFlag = true;
            }

            if (!(xFlag && oFlag)) {
                return false;
            }
        }

        xFlag = oFlag = false;
        if (gameBoard[0][0].equals("X") ||
                gameBoard[1][1].equals("X") ||
                gameBoard[2][2].equals("X")) {
            xFlag = true;
        }

        if (gameBoard[0][0].equals("O") ||
                gameBoard[1][1].equals("O") ||
                gameBoard[2][2].equals("O")) {
            oFlag = true;
        }

        if (!(xFlag && oFlag)) {
            return false;
        }

        xFlag = oFlag = false;
        if (gameBoard[0][2].equals("X") ||
                gameBoard[1][1].equals("X") ||
                gameBoard[2][0].equals("X")) {
            xFlag = true;
        }

        if (gameBoard[0][2].equals("O") ||
                gameBoard[1][1].equals("O") ||
                gameBoard[2][0].equals("O")) {
            oFlag = true;
        }

        if (!(xFlag && oFlag)) {
            return false;
        }

        return true;
    }

    private void resetGame() {
        player = "X";
        moveCnt = 0;
        playing = true;
        clearBoard();
        updateBoardDisplay();
    }

    private class TileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!playing) {
                return;
            }

            TicTacToeTile tile = (TicTacToeTile) e.getSource();
            int row = tile.getRow();
            int col = tile.getCol();

            if (!isValidMove(row, col)) {
                JOptionPane.showMessageDialog(
                        TicTacToeFrame.this,
                        "That space is already taken. Please select another.",
                        "Invalid Move",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Make the move
            gameBoard[row][col] = player;
            tile.setText(player);
            moveCnt++;

            if (moveCnt >= MOVES_FOR_WIN && isWin(player)) {
                JOptionPane.showMessageDialog(
                        TicTacToeFrame.this,
                        "Player " + player + " wins!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
                playing = false;

                askForNewGame();
                return;
            }

            if (moveCnt >= MOVES_FOR_TIE && isTie()) {
                JOptionPane.showMessageDialog(
                        TicTacToeFrame.this,
                        "It's a Tie!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
                playing = false;

                askForNewGame();
                return;
            }

            player = (player.equals("X")) ? "O" : "X";
        }
    }
    private void askForNewGame() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Do you want to play another game?",
                "Play Again",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            playing = false;
        }
    }
}
