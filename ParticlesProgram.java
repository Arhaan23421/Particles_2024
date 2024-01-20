import java.awt.*;
import java.util.*;
import acm.program.*;

public class ParticlesProgram extends Program
{
    //add constants for particle types here
    public static final int EMPTY = 0;
    public static final int METAL = 1;
    public static final int SAND = 2; 
    public static final int WATER = 3; 
    public static final int ICE = 4; 
    public static final int DIRT = 5; 
    public static final int MUD = 1000;
    public static final int SNOW = 6;
    public static final int LAVA = 7;
    public static final int CLOUD = 8;
    private static int count = 0;
    private static int speed = 15;


    
    //do not add any more private instance variables
    private Particle[][] grid;
    private ParticlesDisplay display;

    public void init()
    {
        initVariables(120, 80);
    }    

    public void initVariables(int numRows, int numCols)
    {
        String[] names;
        names = new String[9];
        names[EMPTY] = "Empty";
        names[METAL] = "Metal";
        names[SAND] = "Sand";
        names[WATER] = "Water";
        names[ICE] = "Ice";
        names[DIRT] = "Dirt";
        names[SNOW] = "Snow";
        names[LAVA] = "Lava";
        names[CLOUD] = "Cloud";

        display = new ParticlesDisplay("Particles Game", 
            numRows, numCols, names);
        // initialize the grid here (task 0.1)
        grid = new Particle[numRows][numCols];
        for(int r=0;r<grid.length;r++)
        {
            for(int c=0;c<grid[0].length;c++)
            {
                grid[r][c] = new Empty();
            }
        }
    }

    //called when the user clicks on a location using the given particleType
    private void locationClicked(int row, int col, int particleType)
    {
        // finish this cascading if (task 0.2)
        if (particleType == EMPTY)
        {
            grid[row][col] = new Empty();
        }
        else if(particleType== METAL)
        {
            grid[row][col] = new Metal();
        }
        else if(particleType== SAND)
        {
            grid[row][col] = new Sand(); 
        }
        else if(particleType== WATER)
        {
            grid[row][col] = new Water(); 
        }
        else if(particleType== ICE)
        {
            grid[row][col] = new Ice(); 
        }
        else if(particleType== DIRT)
        {
            grid[row][col] = new Dirt(); 
        }
        else if(particleType== MUD)
        {
            grid[row][col] = new Mud(); 
        }
        else if(particleType==SNOW)
        {
            grid[row][col] = new Snow(); 
        }
        else if(particleType==LAVA)
        {
            grid[row][col] = new Lava(); 
        }
        else if(particleType==CLOUD)
        {
            if(row-2 >=0)
            {
                grid[row][col] = new Cloud();
            }
            if(row+2 >=grid.length-1)
            {
                grid[row][col] = new Cloud();
            }
            if(col-2 >=0)
            {
                grid[row][col] = new Cloud();
            }
            if(col+2 >=grid[0].length-1)
            {
                grid[row][col] = new Cloud();
            } 
        }

    }

    //called repeatedly.
    //causes one random particle to maybe do something.
    public void step()
    {
        int row = (int)(Math.random()*grid.length);
        int col = (int)(Math.random()*grid[0].length);
        Particle particle = grid[row][col];
        if(particle.getType() == EMPTY)
        {
            return;
        }
        else if( particle.getType() == METAL)
        {
            return;
        }
        else if(particle.getType() == SAND)
        {
            tryToMoveDown(row,col);
        }
        else if(particle.getType() == WATER)
        {
            waterMovement(row,col);
            if(row<grid.length-1 && grid[row+1][col].getType()==DIRT)
            {
                grid[row+1][col] = new Mud();
            }
        }
        else if(particle.getType() == ICE)
        {
            Ice ice = (Ice)particle;
            ice.incrementAge();
            if(ice.melted())
            {
                grid[row][col] = new Water();
            }
        }
        else if(particle.getType() == DIRT)
        {
            return;
        }
        else if(particle.getType() == MUD)
        {
            if(Math.random()<0.05)
            {
                waterMovement(row,col);
            }
        }
        else if(particle.getType()== SNOW)
        {
            if(speedOfFall())
            {
                tryToMoveDown(row,col);
            }
            if(row<grid.length-1 && grid[row+1][col].getType()==WATER)
            {
                grid[row+1][col] = new Ice();
            }
        }
        else if(particle.getType() == LAVA)
        {
            if(row<grid.length-1 && grid[row+1][col].getType()==WATER)
            {
                grid[row+1][col] = new Metal();
            }
            if(row<grid.length-1 && grid[row+1][col].getType()==METAL)
            {
                grid[row+1][col] = new Empty();
            }
            if(row<grid.length-1 && grid[row+1][col].getType()==ICE)
            {
                grid[row+1][col] = new Water();
            }
            if(row<grid.length-1 && grid[row+1][col].getType()==SNOW)
            {
                grid[row+1][col] = new Water();
            }
            tryToMoveDownLava(row,col);
        }
        else if(particle.getType()==CLOUD)
        {
            if(grid[row+1][col].getType() == EMPTY)
            {
                boolean isCloud = true;
                for  ( int r=0;r<3;r++)
                {
                    for(int c=0;c<3;c++)
                    {
                        if(col+c<grid[0].length && row-r >=0 &&grid[row-r][col+c].getType()== EMPTY && 
                        grid[row+10][col].getType() != EMPTY)
                        {
                            isCloud = false;
                        }
                    }
                }
                if(isCloud==true)
                {
                    int rand = (int)(Math.random()*10000);
                    if(rand==0)
                    {
                        grid[row+1][col] = new Snow();
                    }
                    if(rand ==1)
                    {
                        grid[row+1][col] = new Water();
                    }
                }
                Cloud cloud = (Cloud)particle;
                count++;
                if(count%speed ==0)
                {
                    
                if(col == grid[0].length-1 && cloud.isFloatingRight())
                {
                    cloud.reversed();
                }
                else if(col == 0 && cloud.isFloatingLeft())
                {
                    cloud.reversed();
                }
                
                else if(cloud.isFloatingRight())
                {
                    tryToMoveRight(row,col);
                }
                else
                {
                    tryToMoveLeft(row,col);
                }
            }

                
        
            }

        }

    }

    public boolean speedOfFall()
    {
        int rand = (int)(Math.random()*15);
        return(rand==0);
    }

    public void waterMovement(int row,int col)
    {
        int num = (int)(Math.random()*3);
        if(num==0)
        {
            tryToMoveDown(row,col);
        }
        else if(num==1)
        {
            tryToMoveLeft(row,col);
        }
        else if(num==2)
        {
            tryToMoveRight(row,col);
        }

    }

    public void tryToMoveLeft(int row, int col)
    {
        if(col>0&& grid[row][col-1].getType()==EMPTY)
        {
            grid[row][col-1] = grid[row][col];
            grid[row][col] = new Empty();

        }
    }

    public void tryToMoveRight(int row, int col)
    {
        if(col<grid[0].length-1 && grid[row][col+1].getType()==EMPTY)
        {
            grid[row][col+1] = grid[row][col];
            grid[row][col] = new Empty();

        }
    }

    public void tryToMoveDown(int row, int col)
    {
        if(row<grid.length-1 && (grid[row+1][col].getType()==EMPTY ||grid[row+1][col].getType()==WATER) )
        {
            Particle save = grid[row+1][col];
            grid[row+1][col] = grid[row][col];
            grid[row][col] = save;

        }
    }
    public void tryToMoveDownLava(int row, int col)
    {
        if(row<grid.length-1)
        {
            int rand =(int)(Math.random()*15);
            if(rand==1)
            {
                Particle save = grid[row+1][col];
            grid[row+1][col] = grid[row][col];
            grid[row][col] = save;
            }

        }
    }
    //copies each element of grid into the display (don't modify this)
    public void updateDisplay()
    {
        for (int r=0; r<grid.length; r++)
            for (int c=0; c<grid[0].length; c++)
                display.setColor(r, c, grid[r][c].getColor());
    }

    // repeatedly calls step and updates the display
    // (don't modify this)
    public void run()
    {
        while (true)
        {
            for (int i = 0; i < display.getSpeed(); i++)
                step();
            updateDisplay();
            display.repaint();
            display.pause(1);  //wait for redrawing and for mouse
            int[] mouseLoc = display.getMouseLocation();
            if (mouseLoc != null)  //test if mouse clicked
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
        }
    }
}
