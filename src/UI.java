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

}