import greenfoot.*;

/**
 * A Ball is a thing that bounces of walls and paddles (or at least i should).
 * 
 * @author The teachers 
 * @version 1
 */
public class Ball extends Actor
{
    private static final int BALL_SIZE = 25;
    private static final int BOUNCE_DEVIANCE_MAX = 5;
    private static final int STARTING_ANGLE_WIDTH = 90;
    private static final int MAX_ANGLE_WIDTH = 90;
    private static final int DELAY_TIME = 50;
    
    private int speed;
    private boolean hasBouncedHorizontally;
    private boolean hasBouncedVertically;
    private int delay;
    
    private int ballHitPaddle = 0;
    private int level = 0;
    private int newSpeed = 0;
    private int wallHitTimes = 0;
    private int playerPaddleHit = 0;
    private int randomPaddleHit = 0;
    private int enemyPaddleHit = 0;
    private boolean canSpawnBall;
    private boolean canIncreaseLevel;
    private int increaseLevel = 0;
    private int levelReal;
    private int id;
    /**
     * Contructs the ball and sets it in motion!
     */
    public Ball(boolean canSpawnBallParam, boolean canIncreaseLevelParam, int ballID)
    {
        createImage();
        init();
        this.canSpawnBall = canSpawnBallParam;
        this.canIncreaseLevel = canIncreaseLevelParam;
        this.id = ballID;
        setImage("ballmaskgold2.png");
        GreenfootImage image = getImage();
        image.scale(BALL_SIZE,BALL_SIZE);
        levelReal = 0;
    }

    /**
     * Creates and sets an image of a black ball to this actor.
     */
    private void createImage()
    {
        GreenfootImage ballImage = new GreenfootImage(BALL_SIZE,BALL_SIZE);
        ballImage.setColor(Color.WHITE);
        ballImage.fillOval(0, 0, BALL_SIZE, BALL_SIZE);
        setImage(ballImage);
    }

    /**
     * Act - do whatever the Ball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        bounceOffPaddle();
        if (delay > 0)
        {
            delay--;
        }
        else
        {
            move(speed);
            checkBounceOffWalls();
            checkBounceOffCeiling();
            checkRestart();
        }
        if (wallHitTimes == 10){
            setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2);
            init();
            wallHitReset();
        }
        if (playerPaddleHit == 5 || randomPaddleHit == 5 || enemyPaddleHit == 5){
            setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2);
            init();
            playerPaddleHitReset();
            enemyPaddleHitReset();
            randomPaddleHitReset();
        }
    }    

    /**
     * Returns true if the ball is touching one of the side walls.
     */
    private boolean isTouchingSides()
    {
        return (getX() <= BALL_SIZE/2 || getX() >= getWorld().getWidth() - BALL_SIZE/2);
    }

    /**
     * Returns true if the ball is touching the ceiling.
     */
    private boolean isTouchingCeiling()
    {
        return (getY() <= BALL_SIZE/2);
    }

    /**
     * Returns true if the ball is touching the floor.
     */
    private boolean isTouchingFloor()
    { 
        return (getY() >= getWorld().getHeight() - BALL_SIZE/2);
    }

    /**
     * Check to see if the ball should bounce off one of the walls.
     * If touching one of the walls, the ball is bouncing off.
     */
    private void checkBounceOffWalls()
    {
        if (isTouchingFloor())
        {
            if (! hasBouncedHorizontally)
            {
                screamSound(GameManager.getRandomNumber(1,4));
                revertVertically();
                wallHitTimes++;
            }
        }
        else
        {
            hasBouncedHorizontally = false;
        }
    }

    /**
     * Check to see if the ball should bounce off the ceiling.
     * If touching the ceiling the ball is bouncing off.
     */
    private void checkBounceOffCeiling()
    {
        if (isTouchingCeiling())
        {
            if (! hasBouncedVertically)
            {
                screamSound(GameManager.getRandomNumber(1,4));
                revertVertically();
                wallHitTimes++;
            }
        }
        else
        {
            hasBouncedVertically = false;
        }
    }
    
    private void bounceOffPaddle()
    {
        if (checkPlayerPaddlePlacement() == 1){
           if (this.getRotation() > 90 && this.getRotation() < 270){
                placementPlayerBounce("Top");
            }
            else{ 
                return;
            }
        }
        if (checkPlayerPaddlePlacement() == 2){
            if(this.getRotation() > 90 && this.getRotation() < 270){
                placementPlayerBounce("Mid");
            }
            else{ 
                return;
            }
        }
        if (checkPlayerPaddlePlacement() == 3){
            if(this.getRotation() > 90 && this.getRotation() < 270){
               placementPlayerBounce("Bot");
            }
            else {
            return;
            }
        }
        if (checkEnemyPaddlePlacement() == 1){
            if (this.getRotation() > 270 || this.getRotation() < 90){
               placementEnemyBounce("Top");
            }
            else{ 
                return;
            }
        }
        if (checkEnemyPaddlePlacement() == 2){
            if(this.getRotation() > 270 || this.getRotation() < 90){
               placementEnemyBounce("Mid");
            }
            else{ 
                return;
            }
        }
        if (checkEnemyPaddlePlacement()== 3){
            if(this.getRotation() > 270 || this.getRotation() < 90){
                placementEnemyBounce("Bot");
            }
            else {
            return;
            }
        }
        if (intersects((Actor)getWorld().getObjects(RandomPaddle.class).get(0))){
            //RandomPaddle rp = (RandomPaddle)getWorld().getObjects(RandomPaddle.class).get(0);
            if(this.getRotation() > 270 || this.getRotation() < 90)
            {
                kickSound(GameManager.getRandomNumber(1,4));
                revertHorizontallyMid();
                wallHitReset();
                randomPaddleHit++;
                playerPaddleHitReset();
                enemyPaddleHitReset();
            }
            else 
            {
                return;
            }
        }
    }
    
    /**
     * Check to see if the ball should be restarted.
     * If touching the floor the ball is restarted in initial position and speed.
     */
    private void checkRestart()
    {
        PingWorld w = (PingWorld)getWorld();
        if (isTouchingSides())
        {
            if(getX() <= 0){
                w.eScore();
                init();
                setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2);
            } else if (getX() >= getWorld().getWidth() - 1){
                w.pScore();
                init();
                setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2);
            }
        }
    }

    /**
     * Bounces the ball back from a vertical surface.
     */
    private void revertHorizontallyMid()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX)- BOUNCE_DEVIANCE_MAX / 2;
        
        setRotation((180 - getRotation() + randomness + 360) % 360);
        hasBouncedHorizontally = true;
    }
    private void revertHorizontallyTop()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX)- BOUNCE_DEVIANCE_MAX / 2;
        
        setRotation((180 - getRotation() + randomness + 360 - 32) % 360);
        hasBouncedHorizontally = true;
    }
    private void revertHorizontallyBottom()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX)- BOUNCE_DEVIANCE_MAX / 2;
        
        setRotation((180 - getRotation() + randomness + 360 + 32) % 360);
        hasBouncedHorizontally = true;
    }
    /**
     * Bounces the bal back from a horizontal surface.
     */
    private void revertVertically()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX)- BOUNCE_DEVIANCE_MAX / 2;
        
        setRotation((360 - getRotation()+ randomness + 360) % 360);
        hasBouncedVertically = true;
    }
    
    /**
     * Starts the rotation
     */
    public void startRotation(){
        int angleFixer = GameManager.getRandomNumber(135,225);
        
        if (angleFixer >= 185 || angleFixer <= 175){
            setRotation(angleFixer);
        }
        else{
            setRotation(215);
        }
    }
        
    /**
     * Initialize the ball settings.
     */
    private void init()
    {
        speed = 3 + newSpeed;
        delay = DELAY_TIME;
        hasBouncedHorizontally = false;
        hasBouncedVertically = false;
        startRotation();
    }
    
    /**
     * Changes speed when ball has hit paddle a fixed number of times
     */
    public void increaseSpeed(){
        switch(ballHitPaddle)
        {
            case 10://10 Gange
                for( int i = 0 ; i < 1 ; i++){
                    if(canSpawnBall){
                    spawnExtraBall();
                }
            }
                newSpeed = 1;
                speed = 3 + newSpeed;
                level = 1;
                break;

            case 20: //20 Gange
                newSpeed = 2;
                speed = 3 + newSpeed;
                level = 2;
                    break;

            case 30://30 Gange
                newSpeed = 3;
                speed = 3 + newSpeed;
                level = 3;
                    break;
        }
        if(increaseLevel <= 11){
            levelReal = 1;
        }
        if(increaseLevel >= 10  && increaseLevel <= 21){
            levelReal = 2;
        }
        if(increaseLevel >= 20){
            levelReal = 3;
        }
        if(this.id == 1){
        getWorld().showText("Current Level: " + levelReal + "/3", getWorld().getWidth()/2,getWorld().getHeight()-20);
        }
    }
    public void spawnExtraBall(){
        getWorld().addObject(new Ball(false,false,2), getWorld().getWidth()/2, getWorld().getHeight()/2);
    }
    private int checkPlayerPaddlePlacement(){
        Paddle p = (Paddle)getWorld().getObjects(Paddle.class).get(0);
        int playerPlacement = 0;
        if (intersects((Actor)getWorld().getObjects(Paddle.class).get(0))){
            if ( this.getY() >= p.getY() + 16){
                playerPlacement = 3;
            }
            else if (this.getY() <= p.getY() + 16 && this.getY() >= p.getY() -16) {
                playerPlacement = 2;
            }
            else if (this.getY() <= p.getY() - 16){
                playerPlacement = 1;
            }
        }
        return playerPlacement;
        } 
    private int checkEnemyPaddlePlacement(){
        EnemyPaddle ep = (EnemyPaddle)getWorld().getObjects(EnemyPaddle.class).get(0);
        int enemyPlacement = 0;
        if (intersects((Actor)getWorld().getObjects(EnemyPaddle.class).get(0))){
            if ( this.getY() <= ep.getY() + 16 && this.getY() >= ep.getY() -16){
                    enemyPlacement = 2;
            }
            else if(this.getY() <= ep.getY() - 16) {
                    enemyPlacement = 1;
            }    
            else if( this.getY() >= ep.getY() + 16){
                    enemyPlacement = 3;
            }  
        }
        return enemyPlacement;
    }
    private void wallHitReset(){
        wallHitTimes = 0;
    }
    private void playerPaddleHitReset(){
        playerPaddleHit = 0;
    }
    private void randomPaddleHitReset(){
        randomPaddleHit = 0;
    }
    private void enemyPaddleHitReset(){
        enemyPaddleHit = 0;
    }
    private void hitSound(int playlist){
        GreenfootSound hitSound1 = new GreenfootSound("hit1.wav");
        GreenfootSound hitSound2 = new GreenfootSound("hit2.wav");
        GreenfootSound hitSound3 = new GreenfootSound("hit3.wav");
        if(playlist == 1){
            hitSound1.setVolume(100);
            hitSound1.play();
        }
        else if (playlist == 2){
            hitSound2.setVolume(100);
            hitSound2.play();
        }
        else if (playlist == 3){
            hitSound3.setVolume(100);
            hitSound3.play();
        }
    }
    private void kickSound(int kicklist){
        GreenfootSound kickSound1 = new GreenfootSound("kick1.wav");
        GreenfootSound kickSound2 = new GreenfootSound("kick2.wav");
        GreenfootSound kickSound3 = new GreenfootSound("kick3.wav");
        if(kicklist == 1){
            kickSound1.setVolume(100);
            kickSound1.play();
        }
        else if (kicklist == 2){
            try{
                kickSound2.setVolume(100);
                kickSound2.play();   
            }
            catch (Exception e){
                kickSound3.setVolume(100);
                kickSound3.play();
            }
        }
            
        else if (kicklist == 3){
            kickSound3.setVolume(100);
            kickSound3.play();
        }
    }
    private void screamSound(int screamlist){
        GreenfootSound screamSound1 = new GreenfootSound("scream1.wav");
        GreenfootSound screamSound2 = new GreenfootSound("scream2.wav");
        GreenfootSound screamSound3 = new GreenfootSound("scream3.wav");
        if(screamlist == 1){
            screamSound1.setVolume(100);
            screamSound1.play();
        }
        else if(screamlist == 2){
            screamSound2.setVolume(100);
            screamSound2.play();
        }
        else if(screamlist == 3){
            screamSound3.setVolume(100);
            screamSound3.play();
        }
    }
    private static void infoGetWorld(){
        return;
    }
    private void placementPlayerBounce(String placement){
        hitSound(GameManager.getRandomNumber(1,4));
        if(placement == "Top"){
                   revertHorizontallyTop(); 
                }
        if(placement == "Mid"){
                    revertHorizontallyMid();
                }
        if(placement == "Bot"){
                    revertHorizontallyBottom();
                }
        ballHitPaddle++;
        wallHitReset();
        playerPaddleHit++;
        enemyPaddleHitReset();
        randomPaddleHitReset();
        increaseSpeed();
        if(canIncreaseLevel){
            increaseLevel++;
        }
    }
    private void placementEnemyBounce(String placement){
        hitSound(GameManager.getRandomNumber(1,4));
        if(placement == "Top"){
                   revertHorizontallyTop(); 
                }
        if(placement == "Mid"){
                    revertHorizontallyMid();
                }
        if(placement == "Bot"){
                    revertHorizontallyBottom();
                }
        ballHitPaddle++;
        wallHitReset();
        enemyPaddleHit++;
        playerPaddleHitReset();
        randomPaddleHitReset();
        increaseSpeed();
        if(canIncreaseLevel){
            increaseLevel++;
        }
    }
}