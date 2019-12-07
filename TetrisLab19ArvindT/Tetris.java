import java.awt.Color;
import java.util.ArrayList;
/**
 * Main class to run tetris
 * 
 * @author Arvind Thirumalai
 * @version January 16, 2016
 */
public class Tetris implements ArrowListener
{
    // instance variables - replace the example below with your own
    private MyBoundedGrid<Block> board;
    private BlockDisplay display;
    private Tetrad activeTetrad;
    private int linesCompleted = 0;
    /**
     * Constructor for objects of class Tetris
     */
    public Tetris()
    {
        board = new MyBoundedGrid<Block>(20, 10);
        display = new BlockDisplay(board);
        display.setArrowListener(this);
        activeTetrad = new Tetrad(this.board);
        display.setTitle("Tetris");
        display.showBlocks();
    }

    /**
     * Checks if the up button is pressed, and if it is it performs an action
     */
    public void upPressed()
    {
        boolean rotate = activeTetrad.rotate();
        if(rotate)
            display.showBlocks();
    }

    /**
     * Checks if the down button is pressed, and if it is it performs an action.
     */
    public void downPressed()
    {
        boolean move = activeTetrad.translate(1, 0);
        if(move)
            display.showBlocks();
    }

    /**
     * Checks if the left button is pressed, and if it is it performs an action
     */
    public void leftPressed()
    {
        boolean move = activeTetrad.translate(0, -1);
        if(move)
            display.showBlocks();
    }

    /**
     * Checks if the right button is pressed, and if it is it performs an action
     */
    public void rightPressed()
    {
        boolean move = activeTetrad.translate(0, 1);
        if(move)
            display.showBlocks();
    }

    /**
     * Checks if the spacebar is pressed, and if it is it performs an action
     */
    public void spacePressed()
    {
        boolean move = true;
        while(move)
        {
            move = activeTetrad.translate(1, 0);
        }
        display.showBlocks();
    }

    /**
     * Plays a game of Tetris
     */
    public void play()
    {
        System.out.println("Level 1");
        int level = 1;
        int pauseTime = 1200;
        boolean gameOver = false;
        while(!gameOver)
        {
            try
            {
                Thread.sleep(pauseTime);
                boolean move = activeTetrad.translate(1, 0);
                if(!move)
                {
                    clearCompletedRows();
                    System.out.println("Lines Cleared: " + linesCompleted);
                    if(linesCompleted == level*5)
                    {
                        level++;
                        pauseTime = (pauseTime*85) / 100;
                    }
                    activeTetrad = new Tetrad(this.board);
                    if(!activeTetrad.translate(1, 0))
                    {
                        gameOver = true;
                    }
                }   
                display.showBlocks();
            }
            catch(InterruptedException e)
            {
                //comment
            }
        }
        System.out.println("Game Over");
    }

    /**
     * Checks to see if the row is completed, and filled.
     * @param row is the row to check
     */
    private boolean isCompletedRow(int row)
    {
        for(int c = 0; c < 10; c++)
        {
            Location loc = new Location(row, c);
            Block b1 = board.get(loc);
            if(b1 == null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets all of the blocks in a row of a grid to null.
     * @param row is the row to clear.
     */
    private void clearRow(int row)
    {
        for(int c = 0; c < 10; c++)
        {
            Location arv = new Location(row, c);
            Block b1 = board.get(arv);
            b1.removeSelfFromGrid();
        }
        for(int r = row - 1; r >= 0; r--)
        {
            for(int c = 0; c < 10; c++)
            {
                Location loc = new Location(r, c);
                Block b1 = board.get(loc);
                Location newloc = new Location(r + 1, c);
                if(b1 != null)
                {
                    b1.moveTo(newloc);
                }
            }
        }
    }

    /**
     * Clears all the rows in the grid that are completed.
     */
    private void clearCompletedRows()
    {
        for(int row = 0; row <= 19; row++)
        {
            if(isCompletedRow(row))
            {
                clearRow(row);
                linesCompleted++;
            }
        }
    }

    /**
     * Runs Tetris
     * @param args is the information from the command line
     */
    public static void main(String[] args)
    {
        Tetris arvind = new Tetris();
        arvind.play();
    }

}