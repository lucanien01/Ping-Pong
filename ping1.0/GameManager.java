import greenfoot.*;

public class GameManager  
{
    private PingWorld pingWorld;
    public GameManager()
    {
        
    }

    public static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
    } 
}
