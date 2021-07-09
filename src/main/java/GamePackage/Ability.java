/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GamePackage;

/**
 *
 * @author prilepko_d
 */
public class Ability {
    String name;
    public Ability(String x)
    {
    name=x;
    }
    public boolean equals(Object o)
    {
        if(this==o)return true;
        if(this.getClass()!=o.getClass())
            return false;
        return name.equals(((Ability)o).name);
    }

    public static Ability WoodStorage=new Ability("WoodStorage");
    public static Ability GoldStorage=new Ability("GoldStorage");
}

class GoldReserve extends Ability
{
    int amount;
    Player user;
    GameEntity []workers=new GameEntity[5];
    //int []timeLast=new int[5];
    public GoldReserve(int amount)
    {
        super("GoldReserve");
        this.amount=amount;
    }
}

class WoodReserve extends Ability
{
    int amount;
    //int []timeLast=new int[3];
    public WoodReserve(int amount)
    {
        super("WoodReserve");
        this.amount=amount;
    }
}

class GoldPicker extends Ability
{
    int maxamount, amount ,time, maxtime;
    GameEntity assignedMine;
    public static Interaction pick =new Interaction() {
        @Override
        public void perform(GameEntity who) {
            who.path=null;
            GoldPicker picker=(GoldPicker)who.getAbility("GoldPicker");
            picker.assignedMine=who.target;
            GoldReserve r=(GoldReserve)who.target.getAbility("GoldReserve");
            int i;
            for(i=0;i<5;++i)
                if(r.workers[i]==null)
                    break;
            if (i<5)
            {
                r.workers[i]=who;
                who.cadre=15;
                who.location.GroundLevel[who.x][who.y] = null;
            }
            return;
        }

        @Override
        public int getDistance() {
            return 0;
        }
    };
    public static Interaction drop=new Interaction() {
        @Override
        public void perform(GameEntity who) {
            GoldPicker picker=(GoldPicker)who.getAbility("GoldPicker");
            who.owner.Gold+=picker.amount;
            picker.amount=0;
            who.target=picker.assignedMine;
            who.location.setPath(who,picker.assignedMine.x,picker.assignedMine.y);
            who.path=who.location.createPath(picker.assignedMine.x,picker.assignedMine.y);
            who.tx=picker.assignedMine.x; who.ty=picker.assignedMine.y;
            System.out.println("GoldMine X: "+picker.assignedMine.x+ ", Y:"+picker.assignedMine.y);
        }

        @Override
        public int getDistance() {
            return 0;
        }
    };

    public GoldPicker()
    {
        super("GoldPicker");
        maxamount=10;
        amount=0;
        maxtime=15;
    }
}

class WoodPicker extends Ability
{
    int maxamount, amount , maxtime;
    GameEntity assignedTree;
    public static Interaction pick =new Interaction() {
        @Override
        public void perform(GameEntity who) {
            who.path=null;
            WoodPicker picker=(WoodPicker)who.getAbility("WoodPicker");
            picker.assignedTree =who.target;
            WoodReserve r=(WoodReserve)who.target.getAbility("WoodReserve");

            who.cadre=15;
            who.location.GroundLevel[who.x][who.y] = null;
            return;
        }

        @Override
        public int getDistance() {
            return 0;
        }
    };
    public static Interaction drop=new Interaction() {
        @Override
        public void perform(GameEntity who) {
            WoodPicker picker=(WoodPicker)who.getAbility("WoodPicker");
            who.owner.Wood+=picker.amount;
            picker.amount=0;
            who.target=picker.assignedTree;
            who.location.setPath(who,picker.assignedTree.x,picker.assignedTree.y);
            who.path=who.location.createPath(picker.assignedTree.x,picker.assignedTree.y);
            who.tx=picker.assignedTree.x; who.ty=picker.assignedTree.y;
            System.out.println("Tree X: "+picker.assignedTree.x+ ", Y:"+picker.assignedTree.y);
        }

        @Override
        public int getDistance() {
            return 0;
        }
    };

    public WoodPicker()
    {
        super("WoodPicker");
        maxamount=20;
        amount=0;
        maxtime=15;
    }
}