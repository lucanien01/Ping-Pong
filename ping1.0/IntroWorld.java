import greenfoot.*;

public class IntroWorld extends World
{
    private static final int WORLD_WIDTH = 1050;
    private static final int WORLD_HEIGHT = 600;
    
    private boolean started = false;
    private GreenfootSound introSong = new GreenfootSound("DUBIST.wav");
    private int blinkTimer = 0;
   
    public IntroWorld()
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        GreenfootImage background = getBackground();
        background.setColor(Color.DARK_GRAY);
        background.fill();
        
        Font titlefont = new Font("Arial", true, false, 120);
        background.setFont(titlefont);
        background.setColor(Color.WHITE);
        background.drawString("PONG", WORLD_WIDTH / 2 - 175, WORLD_HEIGHT / 2);
        
        Font lowertitlefont = new Font("Arial", true, false, 30); 
        background.setFont(lowertitlefont);
        background.setColor(Color.WHITE);
        background.drawString("Hit ", WORLD_WIDTH / 2 - 175, WORLD_HEIGHT / 2+60);
        background.drawString(" to start game", WORLD_WIDTH / 2 - 20, WORLD_HEIGHT / 2+60);
        background.drawString("ENTER", WORLD_WIDTH / 2 - 125, WORLD_HEIGHT / 2+60);
    }
    
    public void act()
    {
        if(!started) {
            introSong.playLoop();
            started = true;
        }
        lowerTitleBlinking();
        TitleColorChange();
        String key = Greenfoot.getKey();
        if (key != null && key.equals("enter"))
        {
            introSong.stop();
            Greenfoot.setWorld(new PingWorld(true));
        }
    }
    
    public void lowerTitleBlinking() {
        blinkTimer++;
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fillRect(WORLD_WIDTH / 2 - 125, WORLD_HEIGHT / 2+30,110,40);
        //background.fillRect(WORLD_WIDTH / 2 - 200, WORLD_HEIGHT / 2+30,400,90);
        
        if (blinkTimer % 80 < 40) 
        {
            Font lowertitlefont = new Font("Arial", true, false, 30); 
            background.setFont(lowertitlefont);
            background.setColor(Color.WHITE);
            background.drawString("ENTER", WORLD_WIDTH / 2 - 125, WORLD_HEIGHT / 2+60);
        }
    
    }
    
    public void TitleColorChange() {
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fillRect(WORLD_WIDTH / 2 - 200, WORLD_HEIGHT / 2-120,600,150);
        
        int r = (blinkTimer * 3) % 255; //rød
        int g = (blinkTimer * 5) % 255; //grøn
        int b = (blinkTimer * 7) % 255; //blå
        background.setColor(new Color(r,g,b));
        
        Font titlefont = new Font("Arial", true, false, 120);
        background.setFont(titlefont);
        background.drawString("PONG", WORLD_WIDTH / 2 - 175, WORLD_HEIGHT / 2);
        
    }
}    
