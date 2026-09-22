import greenfoot.*;


/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce of it.
 */
public class Paddle extends Actor
{
    private int width;
    private int height;
    private int dy;

    /**
     * Constructs a new paddle with the given dimensions.
     */
    public Paddle(int width, int height)
    {
        this.width = width;
        this.height = height;
        dy = 1;
        createImage();
        setImage("whiteguitar.png");
        GreenfootImage image = getImage();
        image.scale(40,100);
    }

    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        Movement();
        setLocation(getX(), getY() + dy);
    }    

    /**
     * Will rotate the paddle 180 degrees if the paddle is at worlds edge.
     */
    private void Movement()
    {
        //Player Movement
        if(Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up"))
        {
            dy = -3;
        }
        else if(Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down")){
            dy = +3;
        } else {
            dy = 0;
        }
    }

    /**
     * Creates and sets an image for the paddle, the image will have the same dimensions as the paddles width and height.
     */
    private void createImage()
    {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(Color.WHITE);
        image.fill();
        setImage(image);
    }

}
