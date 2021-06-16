package com.zhen.MySillyDesktopCatGame.View;

import com.zhen.MySillyDesktopCatGame.Controller.CatController;
import com.zhen.MySillyDesktopCatGame.Controller.ViewController;
import com.zhen.MySillyDesktopCatGame.Type.CatStateType;
import com.zhen.MySillyDesktopCatGame.Type.GameStateType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends JPanel implements ActionListener {

    private JButton menuButton;
    private JButton feedButton;
    private JButton playButton;
    private JLabel catLabel;
    private ViewController viewController;
    private CatController catController;

    private BufferedImage catIdleSpriteSheet;
    private BufferedImage catEatSpriteSheet;

    private Image[] catIdleSprite;
    private Image[] catEatSprite;

    private int CAT_DISPLAY_IMAGE_WIDTH = 256;
    private int CAT_DISPLAY_IMAGE_HEIGHT = 256;
    private int CAT_PIXEL_WIDTH = 32;
    private int CAT_PIXEL_HEIGHT = 32;
    private int CAT_IDLE_FRAMES = 4;
    private boolean facingRight = true;

    Timer timer = new Timer(1000,this);
    int time = 0;

    //this class manages the display of Game elements within the frame. it constructs the buttons/graphics.
    //Action listeners are placed on the buttons so view controller and/or cat controller can be notifed when
    //certain buttons are pressed. The animation of the cat changes on a timer. Every one second the timer will call
    //the action listener and check the CatStateType again. If it remains the same then it moves onto the next frame
    //of the animation, else it resets the frame count back to 0 and play the request animation.
    public Game(ViewController viewController, CatController catController) {
        this.viewController = viewController;
        this.catController = catController;

        menuButton = new JButton("Menu");
        menuButton.setBounds(20,20, 100,20);
        menuButton.addActionListener(this);

        feedButton = new JButton("Feed");
        feedButton.setBounds(20,60, 100,20);
        feedButton.addActionListener(this);

        playButton = new JButton("Play");
        playButton.setBounds(20,100, 100,20);
        playButton.addActionListener(this);


        //draw cat lazy loads the sprite base on CatStateType
        drawCat();
        catLabel = new JLabel();
        //display cat decides what gets shown in the label base on CatStateType
        displayCat(catLabel);
        catLabel.setBounds(150,150,CAT_DISPLAY_IMAGE_WIDTH,CAT_DISPLAY_IMAGE_HEIGHT);

        timer.start();

    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.lightGray);
        this.add(menuButton);
        this.add(feedButton);
        this.add(playButton);
        this.add(catLabel);
        this.setLayout(null);

        Graphics2D g2D = (Graphics2D) g;
    }

    public void displayCat(JLabel label)
    {
        if(catController.getCat().getCatStateType().equals(CatStateType.IDLE))
        {
            label.setIcon(new ImageIcon(catIdleSprite[1]));
        }
    }

    //lazy load the cat sprite and save all the animation to catIdleSprite list
    //There are currently 4 Sprite images each being 32 x 32 pixels
    //used a for loop to get the sub images inside the sprite sheet
    //tempImage uses scaleUpImage method and scales up the 32 x 32 pixel art to appear larger on screen

    public void initCatIdle()
    {
        catIdleSprite = new Image[CAT_IDLE_FRAMES];
        if(catIdleSpriteSheet == null)
        {
            try {
                catIdleSpriteSheet = ImageIO.read(getClass().getClassLoader().getResource("CatIdle.png"));
                for(int i = 0; i < CAT_IDLE_FRAMES; i++)
                {
                    Image tempImage = catIdleSpriteSheet.getSubimage(i*32,0,CAT_PIXEL_WIDTH,CAT_PIXEL_HEIGHT);
                    catIdleSprite[i] = scaleUpImage(CAT_DISPLAY_IMAGE_WIDTH, CAT_DISPLAY_IMAGE_HEIGHT, tempImage);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //lazy load the cat sprite and save all the animation to catEatSprite

    public void initCatEat()
    {

    }
    public void drawCat()
    {
        System.out.println("running draw cat");
        if(catController.getCat().getCatStateType().equals(CatStateType.IDLE))
        {
            if(catIdleSprite == null)
            {
                System.out.println("draw cat idle");
                initCatIdle();
            }
        }
        else if(catController.getCat().getCatStateType().equals(CatStateType.EATING))
        {
            if(catEatSprite == null)
            {
                initCatEat();
            }
        }
    }

    public Image scaleUpImage(int scaleWidth, int scaleHeight, Image image)
    {
        return image.getScaledInstance(scaleWidth,scaleHeight,Image.SCALE_SMOOTH);

    }

    //Actions performed whenever someone clicks on a button
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuButton)
        {
            viewController.setGameState(GameStateType.MENU);
        }
        if(e.getSource() == feedButton)
        {
            catController.feedCat();
            catController.setCatState(CatStateType.EATING);
            drawCat();
            System.out.println(catController.getCat().getFullness());
        }
        if(e.getSource() == timer)
        {
            time++;
            if(catController.getCat().getCatStateType().equals(CatStateType.IDLE))
            {
                if((int)(Math.random()*10)+1 == 1 )
                {
                    if(facingRight == true)
                    {
                        facingRight = false;
                    }
                    else if(facingRight == false)
                    {
                        facingRight = true;
                    }
                }
                if(time % 2 == 0 && facingRight == true)
                {
                    catLabel.setIcon(new ImageIcon(catIdleSprite[0]));
                }
                else if(time % 2 != 0 && facingRight == true)
                {
                    catLabel.setIcon(new ImageIcon(catIdleSprite[1]));
                }
                else if(time % 2 == 0 && facingRight == false)
                {
                    catLabel.setIcon(new ImageIcon(catIdleSprite[2]));
                }
                else if(time % 2 != 0 && facingRight == false)
                {
                    catLabel.setIcon(new ImageIcon(catIdleSprite[3]));
                }
            }



        }
    }
}
