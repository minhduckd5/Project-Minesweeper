package src;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class UI extends JPanel{
    private final int NUM_IMAGES = 13; // 13 images for game
    private int CELL_SIZE = 15; //Size of cell

    //
    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    //Picture number
    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11; 
    private final int DRAW_WRONG_MARK = 12;

    //Variables
    public static int N_MINES = 40; //Number of mines
    public static int N_ROWS = 16;//Number of rows
    public static int N_COLS = 16;//Number of columns

    private int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
    private int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    private int allCells;
    private final JLabel status;
    private boolean firstclick; //flag for first click
    private GameEndListener gameEndListener;

    //getter N_ROWS and N_COLS and N_MINES from InputPanel
    public int getN_ROWS(){
        return N_ROWS;
    }
    public int getN_COLS(){
        return N_COLS;
    }
    public int getN_MINES(){
        return N_MINES;
    }


    public UI(GameEndListener gameEndListener,JLabel status) {
        this.status = status;
        this.gameEndListener = gameEndListener;
        initBoard();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Calculate the new cell size based on the new width and height
                CELL_SIZE = Math.min(getWidth() / N_COLS, getHeight() / N_ROWS);
                // Recalculate the board width and height
                BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
                BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;
                // Redraw the board
                repaint();
            }
        });
        
    }

//auto resize initboard()
public void initBoard() {
    setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT)); //Set size of board
    img = new Image[NUM_IMAGES];                                //Create array of images
    for (int i = 0; i < NUM_IMAGES; i++) {
        String path = "resources/" + i + ".png";                //Path to images
        try {
            img[i] = ImageIO.read(new File(path));              //Load images
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

addMouseListener(new MinesAdapter());                       //Add mouse listener
newGame();                                                  //Start new game
}
public void newGame(){
    SoundPlayer.playSound("start.wav");

    inGame = true;         
    firstclick = true;                                     //Game is running
    minesLeft = N_MINES;                                        //Number of mines left <= number of mines at start

    allCells = N_ROWS * N_COLS;                                 //Number of cells
    field = new int[allCells];                                  //Create array of cells

    for (int i = 0; i < allCells; i++) {
        field[i] = COVER_FOR_CELL;                              //Cover all cells (10.png)
    }
    status.setText(Integer.toString(minesLeft));
}
    private void placeMines(int emptyCell){
        var random = new Random();
        int cell;
        int i = 0;
    while (i < N_MINES) {

        int position = random.nextInt(allCells);

            if (position != emptyCell && field[position] != COVERED_MINE_CELL) {

                int current_col = position % N_COLS;
                field[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - N_COLS;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + N_COLS - 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - N_COLS;
                if (cell >= 0) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + N_COLS;
                if (cell < allCells) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (N_COLS - 1)) {
                    cell = position - N_COLS + 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + N_COLS + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

private void find_empty_cells(int j){
    int current_col = j % N_COLS;
    int cell;

    if (current_col > 0) {
        cell = j - N_COLS - 1;
        if (cell >= 0) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j - 1;
        if (cell >= 0) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + N_COLS - 1;
        if (cell < allCells) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }
    }

    cell = j - N_COLS;
    if (cell >= 0) {
        if (field[cell] > MINE_CELL) {
            field[cell] -= COVER_FOR_CELL;
            if (field[cell] == EMPTY_CELL) {
                find_empty_cells(cell);
            }
        }
    }

    cell = j + N_COLS;
    if (cell < allCells) {
        if (field[cell] > MINE_CELL) {
            field[cell] -= COVER_FOR_CELL;
            if (field[cell] == EMPTY_CELL) {
                find_empty_cells(cell);
            }
        }
    }

    if (current_col < (N_COLS - 1)) {
        cell = j - N_COLS + 1;
        if (cell >= 0) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + N_COLS + 1;
        if (cell < allCells) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + 1;
        if (cell < allCells) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }   
            }
        }
    }
}
@Override
public void paintComponent(Graphics g){
    super.paintComponent(g);

    // Calculate the top left corner (x,y) to start drawing so the board will be centered
    int boardTopLeftX = (getWidth() - BOARD_WIDTH) / 2;
    int boardTopLeftY = (getHeight() - BOARD_HEIGHT) / 2;

    int uncover = 0;

    for (int i = 0; i < N_ROWS; i++) {
        for (int j = 0; j < N_COLS; j++) {
            int cell = field[(i * N_COLS) + j];
            if(inGame && cell == MINE_CELL){
                inGame = false;
                showGameOverDialog(false); // Game lost
            }
            if(!inGame){
                if(cell == COVERED_MINE_CELL){
                    cell = DRAW_MINE;
                }else if(cell == MARKED_MINE_CELL){
                    cell = DRAW_MARK;
                }else if(cell > COVERED_MINE_CELL){
                    cell = DRAW_WRONG_MARK;
                }else if(cell > MINE_CELL){
                    cell = DRAW_COVER;
                }
            }else{
                if(cell > COVERED_MINE_CELL){
                    cell = DRAW_MARK;
                }else if(cell > MINE_CELL){
                    cell = DRAW_COVER;
                    uncover++;
                }
            }
             // Scale the image to fit the cell size
             Image scaledImg = img[cell].getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH);
            //  g.drawImage(scaledImg, (j * CELL_SIZE), (i * CELL_SIZE), this);
            // Draw the image at the adjusted coordinates
            g.drawImage(scaledImg, boardTopLeftX + (j * CELL_SIZE), boardTopLeftY + (i * CELL_SIZE), this);
         }
    }
    if (uncover == 0 && inGame) {
        inGame = false;
        SoundPlayer.playSound("win.wav");
        status.setText("Game won");
        showGameOverDialog(true); // Game won
    } else if (!inGame) {
        SoundPlayer.playSound("lose_minesweeper.wav");
        status.setText("Game lost");
        showGameOverDialog(false); // Game lost
    }
}
private void showGameOverDialog(boolean won) {
    gameEndListener.endGame(won); // Notify the game end listener
    String message = won ? "Congratulations! You won! Do you want to play again?" : "You lost! Do you want to play again?";
    int option = JOptionPane.showConfirmDialog(this, message, "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (option == JOptionPane.YES_OPTION) {
        ((Minesweeper) SwingUtilities.getWindowAncestor(this)).startNewGame();
    } else {
        ((Minesweeper) SwingUtilities.getWindowAncestor(this)).dispose();
    }
}
private class MinesAdapter extends MouseAdapter{
    @Override
    public void mousePressed(MouseEvent e){
        // Adjust the mouse click coordinates
        int x = e.getX() - (getWidth() - N_COLS * CELL_SIZE) / 2;
        int y = e.getY() - (getHeight() - N_ROWS * CELL_SIZE) / 2;

        int cCol = x / CELL_SIZE;
        int cRow = y / CELL_SIZE;

        boolean doRepaint = false;

        if(!inGame){
            newGame();
            repaint();
        }
        if(firstclick){
            placeMines((cRow * N_COLS)+cCol);
            firstclick = false;
        }
        if((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)){
            if(e.getButton() == MouseEvent.BUTTON3){
                if(field[(cRow * N_COLS) + cCol] > MINE_CELL){
                    doRepaint = true;

                    if(field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL){
                        if(minesLeft > 0){
                            field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
                            minesLeft--;
                            status.setText(Integer.toString(minesLeft));
                        }else{
                            status.setText("No marks left");
                        }
                    }else{
                        field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
                        minesLeft++;
                        status.setText(Integer.toString(minesLeft));
                    }
                }
            }else{
                SoundPlayer.playSound("click.wav");
                if(field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL){
                    return;
                }
                if((field[(cRow * N_COLS) + cCol] > MINE_CELL) && (field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)){
                    field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
                    doRepaint = true;

                    if(field[(cRow * N_COLS) + cCol] == MINE_CELL){
                        inGame = false;
                    }
                    if(field[(cRow * N_COLS) + cCol] == EMPTY_CELL){
                        find_empty_cells((cRow * N_COLS) + cCol);
                    }
                }
            }
            if(doRepaint){
                repaint();
            }
        }
    }
}
}
