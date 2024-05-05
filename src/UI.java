package src;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class UI extends JPanel{
    private final int NUM_IMAGES = 13; // 13 images for game
    private final int CELL_SIZE = 60; //Size of cell

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
    private int N_MINES = 40; //Number of mines
    private int N_ROWS = 16;//Number of rows
    private int N_COLS = 16;//Number of columns

    private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
    private final int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    private int allCells;
    private final JLabel status;

    public Board(JLabel status) {
        this.status = status;
        initBoard();
    }
private void initBoard(){
    setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    img = new Image[NUM_IMAGES];
    for (int i = 0; i < NUM_IMAGES; i++) {
        var path = "src/resources/" + i + ".png";
        img[i] = (new ImageIcon(path)).getImage();
    }
    addMouseListener(new MinesAdapter());
    newGame();
}
private void newGame(){
    int cell;

    var random = new Random();
    inGame = true;
    minesLeft = N_MINES;
    allcells = N_ROWS * N_COLS;
    field = new int[allcells];

    for (int i = 0; i < allcells; i++) {
        field[i] = COVER_FOR_CELL;
    }
    status.setText(Integer.toString(minesLeft));

    int i = 0;

    while(i<N_MINES){
        int position = (int) (allcells * random.nextDouble());

        if((position < allcells) && (field[position] != COVERED_MINE_CELL)){
            int current_col = position % N_COLS;
            field[position] = COVERED_MINE_CELL;
            i++;

            if(current_col > 0){
                cell = position - 1 - N_COLS;
                if(cell >= 0 && field[cell] != COVERED_MINE_CELL){
                    field[cell] += 1;
                }
                cell += N_COLS;
                if(cell < allcells && field[cell] != COVERED_MINE_CELL){
                    field[cell] += 1;
                }
            }
            cell = position - N_COLS;
            if(cell >= 0 && field[cell] != COVERED_MINE_CELL){
                field[cell] += 1;
            }
            cell = position + N_COLS;
            if(cell < allcells && field[cell] != COVERED_MINE_CELL){
                field[cell] += 1;
            }
            if(current_col < (N_COLS - 1)){
                cell = position - N_COLS + 1;
                if(cell >= 0 && field[cell] != COVERED_MINE_CELL){
                    field[cell] += 1;
                }
                cell += N_COLS;
                if(cell < allcells && field[cell] != COVERED_MINE_CELL){
                    field[cell] += 1;
                }
            }
        }
    }
}
@Override
public void paintComponent(Graphics g){
    int uncover = 0;

    for (int i = 0; i < N_ROWS; i++) {
        for (int j = 0; j < N_COLS; j++) {
            int cell = field[(i * N_COLS) + j];
            if(inGame && cell == MINE_CELL){
                inGame = false;
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
            g.drawImage(img[cell], (j * CELL_SIZE), (i * CELL_SIZE), this);
        }
    }
    if(uncover == 0 && inGame){
        inGame = false;
        status.setText("Game won");
    }else if(!inGame){
        status.setText("Game lost");
    }

}
private class MinesAdapter extends MouseAdapter{
    @Override
    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        int cCol = x / CELL_SIZE;
        int cRow = y / CELL_SIZE;

        boolean doRepaint = false;

        if(!inGame){
            newGame();
            repaint();
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