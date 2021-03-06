package com.zhen.MySillyDesktopCatGame.Model;

import com.zhen.MySillyDesktopCatGame.Controller.Command.Command;
import com.zhen.MySillyDesktopCatGame.Type.GameStateType;
import com.zhen.MySillyDesktopCatGame.Type.SpellType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameState{

    private boolean buttonsDisabled;
    private GameStateType gameStateType;
    private List<Cat> catList;
    private MinigameCat minigameCat;
    private Set<Rat> ratSet = new HashSet<>();
    private int currentPoints = 100;
    private Map<SpellType, Spell> spellTypeToSpellMap = new HashMap<>();
    private List<Observer> observerList = new ArrayList<>();
    private Command[] spellCommands;

    public GameState(){
    }

    public GameState(GameStateType gameStateType) {
        this.gameStateType = gameStateType;
        this.catList = new ArrayList<>();

        minigameCat = new MinigameCat(50,250);

        Cat cat = new Cat(150,150);
        catList.add(cat);

        Spell fireball = new FireballSpell(10,15,150);
        Spell freeze = new FreezeSpell(0,20,500);
        Spell lightning = new LightningSpell(5,10,300);

        spellTypeToSpellMap.put(SpellType.FIREBALL, fireball);
        spellTypeToSpellMap.put(SpellType.LIGHTNING, lightning);
        spellTypeToSpellMap.put(SpellType.FREEZE, freeze);
    }

    public GameStateType getGameStateType() {
        return gameStateType;
    }

    public void setGameStateType(GameStateType gameStateType) {
        this.gameStateType = gameStateType;
    }

    public List<Cat> getCatList() {
        return catList;
    }

    public void setCatList(List<Cat> catList) {
        this.catList = catList;
    }

    public boolean isButtonsDisabled() {
        return buttonsDisabled;
    }

    public void setButtonsDisabled(boolean buttonsDisabled) {
        this.buttonsDisabled = buttonsDisabled;
    }

    public Set<Rat> getRatSet() {
        return ratSet;
    }

    public void setRatSet(Set<Rat> ratSet) {
        this.ratSet = ratSet;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public Map<SpellType, Spell> getSpellTypeToSpellMap() {
        return spellTypeToSpellMap;
    }

    public Command[] getSpellCommands() {
        return spellCommands;
    }

    public void setSpellCommands(Command[] spellCommands) {
        this.spellCommands = spellCommands;
    }

    public MinigameCat getMinigameCat() {
        return minigameCat;
    }

    public void setMinigameCat(MinigameCat minigameCat) {
        this.minigameCat = minigameCat;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "buttonsDisabled=" + buttonsDisabled +
                ", gameStateType=" + gameStateType +
                ", catList=" + catList +
                ", minigameCat=" + minigameCat +
                ", ratSet=" + ratSet +
                ", currentPoints=" + currentPoints +
                ", spellTypeToSpellMap=" + spellTypeToSpellMap +
                ", spellCommands=" + Arrays.toString(spellCommands) +
                '}';
    }
}
