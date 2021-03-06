package com.zhen.MySillyDesktopCatGame.View;

import com.zhen.MySillyDesktopCatGame.Controller.MainController;
import com.zhen.MySillyDesktopCatGame.Model.GameState;
import com.zhen.MySillyDesktopCatGame.Model.GameWindow;
import com.zhen.MySillyDesktopCatGame.Type.GameStateType;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class MainWindowView extends JFrame implements View{

    private MenuView menuView;
    private SillyCatGameView sillyCatGameView;
    private MinigameView minigameView;
    private JPanel panelController = new JPanel();
    private CardLayout layout = new CardLayout();
    private MainController mainController;
    private MiniGameShopView miniGameShopView;
    private View currentView;

    public MainWindowView(MainController mainController){
        this.mainController = mainController;

        this.setSize(GameWindow.GAME_WINDOW_WIDTH, GameWindow.GAME_WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelController.setLayout(layout);
        this.add(panelController);
        switchScreenTo(GameStateType.MENU);

        mainController.subscribe(this);
        this.setVisible(true);
    }

    @Override
    public void tick()
    {
        this.currentView.tick();
    }

    public void switchScreenTo(GameStateType gameStateType)
    {
        switch(gameStateType)
        {
            case SILLY_CAT_GAME:
                sillyCatGameView = SillyCatGameView.getInstance(mainController);
                if(sillyCatGameView.getParent() != panelController)
                {
                    panelController.add(sillyCatGameView, "game");
                }
                currentView = sillyCatGameView;
                layout.show(panelController,"game");
                break;

            case MENU:
                menuView = MenuView.getInstance(mainController);
                if(menuView.getParent() != panelController)
                {
                    panelController.add(menuView, "menu");
                }
                currentView = menuView;
                layout.show(panelController,"menu");
                break;

            case MINIGAME_1:
                minigameView = MinigameView.getInstance(mainController);
                if(minigameView.getParent() != panelController)
                {
                    panelController.add(minigameView, "miniGame");
                }
                currentView = minigameView;
                layout.show(panelController,"miniGame");
                break;

            case SHOP:
                miniGameShopView = MiniGameShopView.getInstance(mainController);
                if(miniGameShopView.getParent() != panelController)
                {
                    panelController.add(miniGameShopView, "miniGameShop");
                }
                currentView = miniGameShopView;
                layout.show(panelController,"miniGameShop");
                break;

            default:
                break;
        }
    }

    @Override
    public void updateView(GameState gameState) {
        switchScreenTo(gameState.getGameStateType());
    }
}
