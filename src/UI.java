package src;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class UI extends JPanel{
    private static UI Instance; // Static reference to the active UI instance

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

    public static int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;
    private Stack<int[]> moveStack; //for undo

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
        Instance = this;
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
        
          // Add key listener for undo functionality
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_Z) {
              undoMove();
          }
      }
  });
}

private void undoMove() {
    if (!moveStack.isEmpty()) {
        // Pop all moves related to the last action
        while (!moveStack.isEmpty()) {
            int[] lastMove = moveStack.pop();
            int cellIndex = lastMove[0];
            int cellValue = lastMove[1];
            minesLeft = lastMove[2];
            field[cellIndex] = cellValue;

            // Check if the next move in the stack is from a different action
            if (moveStack.isEmpty() || moveStack.peek()[2] != minesLeft) {
                break;
            }
        }
        status.setText(Integer.toString(minesLeft));
        repaint();
    }
}

//auto resize initboard()
public void initBoard() {
    moveStack = new Stack<>();
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

private void calculateBoardSize(int width, int height) {
    CELL_SIZE = Math.min((width) / N_COLS, (height) / N_ROWS);
    BOARD_WIDTH = N_COLS * CELL_SIZE ;
    BOARD_HEIGHT = N_ROWS * CELL_SIZE;
    setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
  }
// private void placeMines(int emptyCell){
    //     var random = new Random();
    //     int cell;
    //     int i = 0;
    // while (i < N_MINES) {

    //     int position = random.nextInt(allCells);

    //         if (position != emptyCell && field[position] != COVERED_MINE_CELL) {

    //             int current_col = position % N_COLS;
    //             field[position] = COVERED_MINE_CELL;
    //             i++;

    //             if (current_col > 0) {
    //                 cell = position - 1 - N_COLS;
    //                 if (cell >= 0) {
    //                     if (field[cell] != COVERED_MINE_CELL) {
    //                         field[cell] += 1;
    //                     }
    //                 }
    //                 cell = position - 1;
    //                 if (cell >= 0) {
    //                     if (field[cell] != COVERED_MINE_CELL) {
    //                         field[cell] += 1;
    //                     }
    //                 }

    //                 cell = position + N_COLS - 1;
    //                 if (cell < allCells) {
    //                     if (field[cell] != COVERED_MINE_CELL) {
    //                         field[cell] += 1;
    //                     }
    //                 }
    //             }

    //             cell = position - N_COLS;
    //             if (cell >= 0) {
    //                 if (field[cell] != COVERED_MINE_CELL) {
    //                     field[cell] += 1;
    //                 }
    //             }

    //             cell = position + N_COLS;
    //             if (cell < allCells) {
    //                 if (field[cell] != COVERED_MINE_CELL) {
    //                     field[cell] += 1;
    //                 }
    //             }

    //             if (current_col < (N_COLS - 1)) {
    //                 cell = position - N_COLS + 1;
    //                 if (cell >= 0) {
    //                     if (field[cell] != COVERED_MINE_CELL) {
    //                         field[cell] += 1;
    //                     }
    //                 }
    //                 cell = position + N_COLS + 1;
    //                 if (cell < allCells) {
    //                     if (field[cell] != COVERED_MINE_CELL) {
    //                         field[cell] += 1;
    //                     }
    //                 }
    //                 cell = position + 1;
    //                 if (cell < allCells) {
    //                     if (field[cell] != COVERED_MINE_CELL) {
    //                         field[cell] += 1;
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }
    private void placeMines(int emptyCell) {
        Random random = new Random();
        int minesPlaced = 0;

        // Create a set to keep track of mine positions
        Set<Integer> minePositions = new HashSet<>();

        // Add the emptyCell to the set to ensure no mine is placed there
        minePositions.add(emptyCell);

        while (minesPlaced < N_MINES) {
            int position = random.nextInt(allCells);

            // If the position is not already a mine or the emptyCell, place a mine there
            if (!minePositions.contains(position)) {
                minePositions.add(position);
                field[position] = COVERED_MINE_CELL;
                minesPlaced++;

                // Update adjacent cells' mine counts
                int current_col = position % N_COLS;
                int[] directions = {-N_COLS - 1, -N_COLS, -N_COLS + 1, -1, 1, N_COLS - 1, N_COLS, N_COLS + 1};

                for (int dir : directions) {
                    int cell = position + dir;
                    if (cell >= 0 && cell < allCells) {
                        if (current_col == 0 && (dir == -N_COLS - 1 || dir == -1 || dir == N_COLS - 1)) {
                            continue;
                        }
                        if (current_col == N_COLS - 1 && (dir == -N_COLS + 1 || dir == 1 || dir == N_COLS + 1)) {
                            continue;
                        }
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }
    private void find_empty_cells(int j) {
    Queue<Integer> queue = new LinkedList<>();
    queue.add(j);

    while (!queue.isEmpty()) {
        int cellPos = queue.poll();
        int current_col = cellPos % N_COLS;
        int cell;

        // Check all 8 directions
        int[] directions = {-N_COLS - 1, -N_COLS, -N_COLS + 1, -1, 1, N_COLS - 1, N_COLS, N_COLS + 1};

        for (int dir : directions) {
            cell = cellPos + dir;

            // Ensure cell is within bounds
            if (cell >= 0 && cell < allCells) {
                // Handle edge cases where cells are on different rows but considered adjacent
                if ((dir == -N_COLS - 1 || dir == -1 || dir == N_COLS - 1) && current_col == 0) continue;
                if ((dir == -N_COLS + 1 || dir == 1 || dir == N_COLS + 1) && current_col == (N_COLS - 1)) continue;

                if (field[cell] > MINE_CELL) {

                    // Push the state of each affected cell onto the stack
                    moveStack.push(new int[]{cell, field[cell], minesLeft});

                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        queue.add(cell);
                    }
                }
            }
        }
    }
}


// private void find_empty_cells(int j){
//     int current_col = j % N_COLS;
//     int cell;

//     if (current_col > 0) {
//         cell = j - N_COLS - 1;
//         if (cell >= 0) {
//             if (field[cell] > MINE_CELL) {
//                 field[cell] -= COVER_FOR_CELL;
//                 if (field[cell] == EMPTY_CELL) {
//                     find_empty_cells(cell);
//                 }
//             }
//         }

//         cell = j - 1;
//         if (cell >= 0) {
//             if (field[cell] > MINE_CELL) {
//                 field[cell] -= COVER_FOR_CELL;
//                 if (field[cell] == EMPTY_CELL) {
//                     find_empty_cells(cell);
//                 }
//             }
//         }

//         cell = j + N_COLS - 1;
//         if (cell < allCells) {
//             if (field[cell] > MINE_CELL) {
//                 field[cell] -= COVER_FOR_CELL;
//                 if (field[cell] == EMPTY_CELL) {
//                     find_empty_cells(cell);
//                 }
//             }
//         }
//     }

//     cell = j - N_COLS;
//     if (cell >= 0) {
//         if (field[cell] > MINE_CELL) {
//             field[cell] -= COVER_FOR_CELL;
//             if (field[cell] == EMPTY_CELL) {
//                 find_empty_cells(cell);
//             }
//         }
//     }

//     cell = j + N_COLS;
//     if (cell < allCells) {
//         if (field[cell] > MINE_CELL) {
//             field[cell] -= COVER_FOR_CELL;
//             if (field[cell] == EMPTY_CELL) {
//                 find_empty_cells(cell);
//             }
//         }
//     }

//     if (current_col < (N_COLS - 1)) {
//         cell = j - N_COLS + 1;
//         if (cell >= 0) {
//             if (field[cell] > MINE_CELL) {
//                 field[cell] -= COVER_FOR_CELL;
//                 if (field[cell] == EMPTY_CELL) {
//                     find_empty_cells(cell);
//                 }
//             }
//         }

//         cell = j + N_COLS + 1;
//         if (cell < allCells) {
//             if (field[cell] > MINE_CELL) {
//                 field[cell] -= COVER_FOR_CELL;
//                 if (field[cell] == EMPTY_CELL) {
//                     find_empty_cells(cell);
//                 }
//             }
//         }

//         cell = j + 1;
//         if (cell < allCells) {
//             if (field[cell] > MINE_CELL) {
//                 field[cell] -= COVER_FOR_CELL;
//                 if (field[cell] == EMPTY_CELL) {
//                     find_empty_cells(cell);
//                 }   
//             }
//         }
//     }
// }
@Override
public void paintComponent(Graphics g){
    super.paintComponent(g);

    // Check if the component has a valid size
    if (getWidth() > 0 && getHeight() > 0) {
        calculateBoardSize(getWidth(), getHeight());
    }

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
    Timer timer = new Timer(100, e -> {
    int option = JOptionPane.showConfirmDialog(this, message, "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (option == JOptionPane.YES_OPTION) {
        ((Minesweeper) SwingUtilities.getWindowAncestor(this)).startNewGame();
    } else {
        ((Minesweeper) SwingUtilities.getWindowAncestor(this)).dispose();
    }
});
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
}
private class MinesAdapter extends MouseAdapter{
    @Override
    public void mousePressed(MouseEvent e){
        // Adjust the mouse click coordinates
        int x = e.getX() - (getWidth() - N_COLS * CELL_SIZE) / 2 ;
        int y = e.getY() - (getHeight() - N_ROWS * CELL_SIZE) / 2;

        int cCol = x / CELL_SIZE;
        int cRow = y / CELL_SIZE;

        boolean doRepaint = false;
        if (cCol < 0 || cRow < 0 || cCol >= N_COLS || cRow >= N_ROWS) {
            return; // Ignore clicks outside the board
        }

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

                    // Push current state to stack
                    moveStack.push(new int[]{cRow * N_COLS + cCol, field[cRow * N_COLS + cCol], minesLeft});

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
                    
                     // Push current state to stack
                    moveStack.push(new int[]{cRow * N_COLS + cCol, field[cRow * N_COLS + cCol], minesLeft});

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

    // Getters for the current game state
    public int[] getField() {
        return field;
    }

    public boolean isInGame() {
        return inGame;
    }

    public int getMinesLeft() {
        return minesLeft;
    }

    public boolean isFirstClick() {
        return firstclick;
    }

    public int getCoveredMineCell() {
        return COVERED_MINE_CELL;
    }
    public int getCellSize(){
        return CELL_SIZE;
    }
    public int getMineCell(){
        return MINE_CELL;
    }

    public static UI getInstance() {
        if(Instance == null){
            throw new IllegalStateException("UI is not initialized");
        }
        return Instance;
    }
    public int getBoardTopLeftX() {
        return  (getWidth() - BOARD_WIDTH) / 2;
    }
    public int getBoardTopLeftY() {
        return (getHeight() - BOARD_HEIGHT) / 2;
    }

    public int getFlaggedCell() {
        return COVERED_MINE_CELL + MARK_FOR_CELL;
    }
    public int getCoveredEmptyCell() {
        return COVER_FOR_CELL;
    }


    // Setters for the current game state
    public void setField(int[] field) {
        this.field = field;
    }
    
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
    
    public void setMinesLeft(int minesLeft) {
        this.minesLeft = minesLeft;
    }
    
    public void setFirstClick(boolean firstClick) {
        this.firstclick = firstClick;
    }
}
