/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GamePackage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author prilepko_d
 */


public class Player{
    int
            Gold,
            Wood,
            Oil,
            FoodLimit = 0,
            UsedFood = 0;

    List<GameEntity> PlayerEntity = new LinkedList<>();
    static Player Neutrals=new Player(0,0,0);

    public Player(int PlayerGold, int PlayerWood, int PlayerOil){
        Gold=PlayerGold;
        Wood=PlayerWood;
        Oil=PlayerOil;
    }
}
