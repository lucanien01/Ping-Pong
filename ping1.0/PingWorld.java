import greenfoot.*;


/**
 * The Ping World is where Balls and Paddles meet to play pong.
 * 
 * @author The teachers 
 * @version 1
 */
public class PingWorld extends World
{
    private static final int WORLD_WIDTH = 1050;
    private static final int WORLD_HEIGHT = 600;
    private boolean isGameOver;
    
    private GreenfootSound backgroundSong = new GreenfootSound("backgroundsong2.wav");

    private int playerScore = 0;
    private int enemyScore = 0;

    /**
     * Constructor for objects of class PingWorld.
     */
    public PingWorld(boolean gameStarted)
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        if (gameStarted)
        {
            GreenfootImage background = new GreenfootImage("images.jpg");
            background.scale(getWidth(),getHeight());
            setBackground(background);
            backgroundSong.setVolume(40);
            backgroundSong.play();
            // Create a new world with WORLD_WIDTHxWORLD_HEIGHT cells with a cell size of 1x1 pixels.
            addObject(new Ball(true,true,1), WORLD_WIDTH/2, WORLD_HEIGHT/2);
            addObject(new Paddle(20,100), 30, WORLD_HEIGHT/2);
            addObject(new EnemyPaddle(20, 100), WORLD_WIDTH - 30, WORLD_HEIGHT/2);
            randomPaddles();
        }
        else
        {
            Greenfoot.setWorld(new IntroWorld());
        }
    }
    public void stopped(){
        backgroundSong.pause();
    }
     public void started(){
        backgroundSong.play();
    }
    public void act(){
        showText("Score: " + playerScore, WORLD_WIDTH - 990,  WORLD_HEIGHT - 580);
        showText("Score: " + enemyScore, WORLD_WIDTH - 60,WORLD_HEIGHT - 580);
        winLossCondition();
    }
    
    public void pScore(){
        playerScore++;
    }
    
    public void eScore(){
        enemyScore++;
    }
    
    public void winLossCondition(){
        if(enemyScore == 10){
            Greenfoot.stop();
            showText("You Loose!",WORLD_WIDTH/2,WORLD_HEIGHT/2);
            isGameOver = true;
        }
        
        if(playerScore == 10){
            Greenfoot.stop();
            showText("You Win!",WORLD_WIDTH/2,WORLD_HEIGHT/2);
            isGameOver = true;
        }
    }
    public void randomPaddles(){
        addObject(new RandomPaddle(20, GameManager.getRandomNumber(80,130)), GameManager.getRandomNumber(200, 850), 0);
    }
}
