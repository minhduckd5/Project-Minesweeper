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
}