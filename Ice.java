import java.awt.Color;

public class Ice extends Particle
{
    private int age;
    
    public Ice()
    {
        super(ParticlesProgram.ICE, Color.white);
        age = 0;
        
    }
    public void incrementAge()
    {
        age++;
    }
    public boolean melted()
    {
        return age > 1000;
    }
    


}
