import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private static final int SIZE = 5;
    private static final int BUTTON_SIZE = 80;

    private char[][] board = new char[SIZE][SIZE];
    private boolean xTurn = true;

    private GridPane grid;
    private Label resultLabel;
    

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeBoard();

        grid = createGridPane();
        addButtons(grid);

        resultLabel = new Label();
        resultLabel.setMinHeight(50);

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> resetGame());

        HBox buttonBox = new HBox(resetButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(grid, resultLabel, buttonBox);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, SIZE * BUTTON_SIZE, SIZE * BUTTON_SIZE + 100);
        primaryStage.setTitle("Tic Tac Toe (5x5)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
    }

    private void addButtons(GridPane grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button button = createButton(row, col);
                grid.add(button, col, row);
            }
        }
    }

    private Button createButton(int row, int col) {
        Button button = new Button();
        button.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
        button.setOnAction(e -> handleButtonClick(row, col, button));
        return button;
    }

    private void handleButtonClick(int row, int col, Button button) {
        if (board[row][col] == 0) {
            if (xTurn) {
                button.setText("X");
                board[row][col] = 'X';
            } else {
                button.setText("O");
                board[row][col] = 'O';
            }
            xTurn = !xTurn;

            if (checkForWin(row, col)) {
                displayWinner();
                disableButtons();
            } else if (isBoardFull()) {
                displayDraw();
                disableButtons();
            }
        }
    }

    private boolean checkForWin(int row, int col) {
        return checkRow(row) || checkColumn(col) || checkDiagonals(row, col);
    }

    private boolean checkRow(int row) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] != board[row][0]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(int col) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] != board[0][col]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonals(int row, int col) {
        if (row == col) {
            for (int i = 0; i < SIZE; i++) {
                if (board[i][i] != board[0][0]) {
                    return false;
                }
            }
            return true;
        } else if (row + col == SIZE - 1) {
            for (int i = 0; i < SIZE; i++) {
                if (board[i][SIZE - 1 - i] != board[0][SIZE - 1]) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void displayWinner() {
        resultLabel.setText("Player " + (xTurn ? "O" : "X") + " wins!");
    }

    private void displayDraw() {
        resultLabel.setText("It's a draw!");
    }

    private void disableButtons() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ((Button) grid.getChildren().get(row * SIZE + col)).setDisable(true);
            }
        }
    }

    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = 0;
            }
        }
    }

    private void resetGame() {
        initializeBoard();
        xTurn = true;
        resultLabel.setText("");
        enableButtons();
    }

    private void enableButtons() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ((Button) grid.getChildren().get(row * SIZE + col)).setDisable(false);
                ((Button) grid.getChildren().get(row * SIZE + col)).setText("");
            }
        }
    }
}
