import greenfoot.*;


/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce of it.
 */
public class EnemyPaddle extends Actor
{
    private int width;
    private int height;
    private int dy;

    /**
     * Constructs a new paddle with the given dimensions.
     */
    public EnemyPaddle(int width, int height)
    {
        this.width = width;
        this.height = height;
        dy = 6;
        createImage();
        setImage("blackguitar.png");
        GreenfootImage image = getImage();
        image.scale(40,100);
    }

    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        followBall();
    }    

    /**
     * Will rotate the paddle 180 degrees if the paddle is at worlds edge.
     */
    private void followBall()
    {
       Ball ball = (Ball) getWorld().getObjects(Ball.class).get(0);

        if (ball.getY() > getY()) {
            setLocation(getX(), getY() + 1);   // move down
        } else if (ball.getY() < getY()) {
            setLocation(getX(), getY() - 1);   // move up
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