import greenfoot.*;


/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce of it.
 * 
 * @author The teachers 
 * @version 1
 */
public class RandomPaddle extends Actor
{
    private int width;
    private int height;
    private int dx;

    public RandomPaddle(int width, int height)
    {
        this.width = width;
        this.height = height;
        dx = 2;
        createImage();
        setImage("wall.png");
        GreenfootImage image = getImage();
        image.scale(40,height);
    }

    public void act() 
    {
        setLocation(getX(), getY() + dx);
        detectEdge();
    }    

    private void createImage()
    {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(Color.RED);
        image.fill();
        setImage(image);
    }
    
    public void detectEdge(){
        World w = getWorld();
        if(this.isAtEdge()){
            w.removeObject(this);
            w.addObject(new RandomPaddle(20, GameManager.getRandomNumber(80,130)), GameManager.getRandomNumber(200, 850), 0);


        }
    }
}
