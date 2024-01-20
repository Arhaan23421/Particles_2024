import java.awt.Color;

public class Cloud extends Particle
{
    private static boolean floatingRight;

    public Cloud()
    {
        super(ParticlesProgram.CLOUD, new Color(211,211,211));
        floatingRight = true;
    }
    public static void reversed()
    {
        if(floatingRight)
        {
            floatingRight = false;
        }
        else{
            floatingRight = true;
        }
    }
    public static boolean isFloatingRight()
    {
        return floatingRight;
    }
    public static boolean isFloatingLeft()
    {
        return !floatingRight;
    }
    


}
