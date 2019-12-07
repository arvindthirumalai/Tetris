import java.awt.Color;
import java.util.ArrayList;
/**
 * Tetrad is the class that keeps track of a tetrad with 4 blocks.
 * 
 * @author Arvind Thirumalai
 * @version March 3, 2017
 */
public class Tetrad
{
    private Block[] fourBlock = new Block[4];
    private MyBoundedGrid<Block> grid;
    private boolean isSquare = false;
    /**
     * Constructor for objects of class Tetrad
     * @param grid is the grid that the tetrad is created on
     */
    public Tetrad(MyBoundedGrid<Block> grid)
    {
        this.grid = grid;
        for(int i = 0; i < fourBlock.length; i++)
        {
            Block b1 = new Block();           
            fourBlock[i] = b1;
        }
        Location[] locs = new Location[4];
        int rand = (int) (Math.random()*7);
        int numRows = grid.getNumRows();
        int numCols = grid.getNumCols();
        if(rand == 0)
        {
            locs[0] = new Location(0, numCols/2);
            locs[1] = new Location(2, numCols/2);
            locs[2] = new Location(1, numCols/2 - 1);
            locs[3] = new Location(1, numCols/2);   
            for(int i = 0; i < fourBlock.length; i++)
            {
                fourBlock[i].setColor(Color.GRAY);
            }
        }
        else if(rand == 1)
        {
            locs[0] = new Location(0, numCols/2);
            locs[1] = new Location(0, numCols/2 - 1);
            locs[2] = new Location(0, numCols/2 + 1);
            locs[3] = new Location(0, numCols/2 + 2);
            for(int i = 0; i < fourBlock.length; i++)
            {
                fourBlock[i].setColor(Color.RED);
            }
        }
        else if(rand == 2)
        {
            locs[0] = new Location(0, numCols/2);
            locs[1] = new Location(1, numCols/2);
            locs[2] = new Location(1, numCols/2 + 1);
            locs[3] = new Location(2, numCols/2 + 1);
            for(int i = 0; i < fourBlock.length; i++)
            {
                fourBlock[i].setColor(Color.BLUE);
            }
        }
        else if(rand == 3)
        {
            locs[0] = new Location(0, numCols/2);
            locs[1] = new Location(0, numCols/2 + 1);
            locs[2] = new Location(1, numCols/2);
            locs[3] = new Location(1, numCols/2 + 1);
            for(int i = 0; i < fourBlock.length; i++)
            {
                fourBlock[i].setColor(Color.CYAN);
                isSquare = true;
            }
        }
        else if(rand == 4)
        {
            locs[0] = new Location(0, numCols/2);
            locs[1] = new Location(1, numCols/2);
            locs[2] = new Location(2, numCols/2);
            locs[3] = new Location(2, numCols/2 + 1);
            for(int i = 0; i < fourBlock.length; i++)
            {
                fourBlock[i].setColor(Color.YELLOW);
            }
        }
        else if(rand == 5)
        {
            locs[0] = new Location(0, numCols/2);
            locs[1] = new Location(1, numCols/2);
            locs[2] = new Location(2, numCols/2);
            locs[3] = new Location(2, numCols/2 - 1);
            for(int i = 0; i < fourBlock.length; i++)
            {
                fourBlock[i].setColor(Color.MAGENTA);
            }
        }
        else 
        {
            locs[0] = new Location(0, numCols/2);
            locs[1] = new Location(1, numCols/2);
            locs[2] = new Location(1, numCols/2 - 1);
            locs[3] = new Location(2, numCols/2 - 1);
            for(int i = 0; i < fourBlock.length; i++)
            {
                fourBlock[i].setColor(Color.GREEN);
            }
        }
        addToLocations(grid, locs);
    }

    /**
     * Adds the blocks to certain locations in a specific grid
     * 
     * @param  gr   the grid to add the blocks to
     * @param  locs   array of locations to add the blocks to
     */
    private void addToLocations(MyBoundedGrid<Block> gr, Location[] locs)
    {
        this.grid = gr;
        for(int i = 0; i < fourBlock.length; i++)
        {
            fourBlock[i].putSelfInGrid(grid, locs[i]);
        }
    }

    /**
     * Removes the four Blocks of the tetrad from the grid
     * @return an array of Locations of where blocks were removed.
     */
    public Location[] removeBlocks()
    {
        ArrayList<Location> occloc = grid.getOccupiedLocations();
        if(occloc.size() == 0)
        {
            return null;
        }
        Location[] locs = new Location[4];
        int counter = 0;
        for(int index = 0; index < occloc.size(); index++)
        {
            Location loc = occloc.get(index);
            Block b1 = grid.get(loc);
            if(b1 == fourBlock[0] || b1 == fourBlock[1] || b1 == fourBlock[2] || b1 == fourBlock[3])
            {
                b1.removeSelfFromGrid();
                locs[counter] = loc;
                counter++;      
            }
        }
        return locs;
    }

    /**
     * Checks if the locations in the locations array is empty in the grid.
     * 
     * @param gr is the grid in which to check if the locations are empty in.
     * @param locs is an array of Locations.
     * @return true if the Location are empty, otherwise
     *         false.
     */
    private boolean areEmpty(MyBoundedGrid<Block> gr, Location[] locs)
    {
        for(int i = 0; i < locs.length; i++)
        {
            Location loc = locs[i];
            if(!gr.isValid(loc))
            {
                return false;
            }
            Block b = gr.get(loc);
            if(b != null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Translates the tetrad a certain number of rows, and a certain number of columns
     * @param deltaRow is the number of Rows to move the tetrad
     * @param deltaCol is the number of columns to move the tetrad
     * @return true if the translation was successful; otherwise,
     *         false.
     */
    public boolean translate(int deltaRow, int deltaCol)
    {
        Location[] prevLocs = removeBlocks();
        Location[] newLocs = new Location[4];        
        for(int i = 0; i < 4; i++)
        {
            newLocs[i] = new Location(prevLocs[i].getRow() + deltaRow, 
                                      prevLocs[i].getCol() + deltaCol);
        }
        if(!areEmpty(grid, newLocs))
        {
            addToLocations(grid, prevLocs);
            return false;
        }
        addToLocations(grid, newLocs);
        return true;
    }

    /**
     * Rotates the tetrad clockwise 90 degrees around a pivot point.
     * 
     * @return true if the rotation was successful; otherwise,
     *         false.
     */
    public boolean rotate()
    {
        if(isSquare)
        {
            return true;
        }
        Location[] prevLocs = removeBlocks();
        Location[] newLocs = new Location[4];
        for(int i = 0; i < 4; i++)
        {
            int prevRow = prevLocs[i].getRow();
            int prevCol = prevLocs[i].getCol();
            int pivotRow = prevLocs[0].getRow();
            int pivotCol = prevLocs[0].getCol();
            newLocs[i] = new Location(pivotRow - pivotCol + prevCol, pivotRow + pivotCol - prevRow);
        }
        if(!areEmpty(grid, newLocs))
        {
            addToLocations(grid, prevLocs);
            return false;
        }
        addToLocations(grid, newLocs);
        return true;
    }
}