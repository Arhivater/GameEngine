package GamePackage;
import java.awt.Image;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.ArrayList;
import java.util.Queue;
import java.util.TreeMap;
import javax.swing.ImageIcon;

/**
 *
 * @author Дима
 */
public class GamePrototype /*extends Game*/{
    static TreeMap<String, ImageIcon> icons=new TreeMap<>();
    
    public enum Race{
        Human, Orc, Nature
    }
    Race Alignment=Race.Nature;
    String name;
    int width, height;
    int HitPointsMax;
    //int HitPoints;
    int Food;
    int GoldCost, WoodCost, OilCost;
    int TimeTicks;//TODO
    ImageIcon IconLabel, IconPanel,IconMap;
    Image ImageMap;
    ArrayList<Ability> abilities=new ArrayList<>();
    int ticksToMove;
    
    //public MoveInEarth=
    
    
    
    
    private ImageIcon getIcon(String name)
    {
        ImageIcon result=icons.get(name);
        if(result==null)
        {
            result=new ImageIcon(name);
            icons.put(name, result);
        }
        return result;
    }
    public void addAbility(Ability a)
    {
        if(!abilities.contains(a))
            abilities.add(a);
        else throw new IllegalArgumentException("Повторное добавление способности "+a.name+" прототипу "+name);
    }
    
    public GamePrototype(Race a, String n, int hpmax, int gcost, int wcost, int ocost, String Label, String Panel, String Map)
    {
        this (a,n,hpmax,gcost,wcost,ocost,Label,Panel,Map,1,1);
        
        
    }
    public GamePrototype(Race a, String n, int hpmax, int gcost, int wcost, int ocost, String Label, String Panel, String Map, int w, int h)
    {
        Alignment=a;
        name=n;
        HitPointsMax=hpmax;
        //HitPoints=hpmax;
        GoldCost=gcost;
        WoodCost=wcost;
        OilCost=ocost;

        width=w;
        height=h;

        IconLabel=getIcon(Label);
        IconPanel=getIcon(Panel);
        IconMap=getIcon(Map);

        ImageMap = IconMap.getImage().getScaledInstance(30*w, 30*h, WIDTH);
    }
}

/*enum MoveDirection
{

}*/

class GameEntity
{
    GamePrototype origin;
    Map location;
    int HitPoints;
    int x,y;
    int tx, ty;
    Player owner;
    ArrayList<Ability> abilities=new ArrayList<>();
    Queue<Integer> path;
    int cadre=0, movedir=-1;
    GameEntity target;
    Interaction interaction;


    public GameEntity(GamePrototype basedOn, Map l, Player o, int x, int y)
    {
        origin=basedOn;
        location=l;
        this.x=x;
        this.y=y;
        tx=ty=-1;
        for (int i=0;i<basedOn.height;++i)
            for(int j=0;j<basedOn.width;++j)
                l.GroundLevel[x+j][y+i]=this;
        HitPoints=origin.HitPointsMax;
        abilities.addAll(origin.abilities);

        owner=o;
        
        if(owner!=null)
        {
            o.PlayerEntity.add(this);
            if(origin.Food>0)
                owner.FoodLimit+=origin.Food;
            else
                owner.UsedFood-=origin.Food;
        }
    }

    private void put(Map.Coords c)
    {
        x=c.x;
        y=c.y;
        location.GroundLevel[x][y]=this;
        /*path=null;*/
        target=null;
    }

    public void action()
    {
        //System.out.println(path==null);
        if( location.GroundLevel[x][y]!=this) return;

        GoldReserve g=(GoldReserve)getAbility("GoldReserve");
        if(g!=null) {
            for(int i=0; i<g.workers.length; ++i)
            {
                if(g.workers[i]!=null)
                {
                    g.workers[i].cadre--;
                    if(g.workers[i].cadre==0) {
                        GoldPicker picker = (GoldPicker) (g.workers[i].getAbility("GoldPicker"));
                        if (picker.maxamount - picker.amount < g.amount) {
                            g.amount -= picker.maxamount;
                            picker.amount = picker.maxamount;
                        } else {
                            picker.amount = g.amount;
                            g.amount=0;
                        }
                        Map.Coords unload = getFreeSpace(Map.Environment.GROUND);
                        if (unload != null) {
                            System.out.println("Unload");
                            g.workers[i].put(unload);

                            g.workers[i].path=location.setPath(g.workers[i],"GoldStorage");
                            /*for(int Y=0;Y<25;++Y)
                            {
                                for(int X=0;X<25;++X)
                                    System.out.print(location.pathLength[X][Y]+"\t");
                                System.out.println();
                            }*/
                            System.out.println("After unload "+g.workers[i].path);
                            g.workers[i]=null;
                        }
                    }
                }
            }
        }

        if(cadre!=0)
        {
            cadre--;
        }
        else {
            if(target!=null)
            {
                int d=distanceTo(target);
                if(d<=interaction.getDistance())
                {
                    System.out.println("Interact");
                    interaction.perform(this);
                    /*path=null;
                    GoldPicker picker=(GoldPicker)getAbility("GoldPicker");
                    picker.assignedTree=target;
                    GoldReserve r=(GoldReserve)target.getAbility("GoldReserve");
                    int i;
                    for(i=0;i<5;++i)
                        if(r.workers[i]==null)
                            break;
                    if (i<5)
                    {
                        r.workers[i]=this;
                        cadre=15;
                        location.GroundLevel[x][y] = null;
                    }*/
                    return;
                }
            }

            if (path != null && !path.isEmpty()) {
                System.out.println("Move");
                location.setPath(this,tx,ty);
                System.out.println("Path: "+this.path);
                path = location.createPath(tx, ty);
                //System.out.println(x+" "+y+" : "+cx+" "+cy);
            }
            if (path != null && !path.isEmpty()) {
                location.GroundLevel[x][y] = null;
                movedir = path.poll();
                switch (movedir) {
                    case 0:
                        x++;
                        cadre = origin.ticksToMove;
                        break;
                    case 1:
                        x++;
                        y--;
                        cadre = origin.ticksToMove * 7 / 5;
                        break;
                    case 2:
                        y--;
                        cadre = origin.ticksToMove;
                        break;
                    case 3:
                        x--;
                        y--;
                        cadre = origin.ticksToMove * 7 / 5;
                        break;
                    case 4:
                        x--;
                        cadre = origin.ticksToMove;
                        break;
                    case 5:
                        x--;
                        y++;
                        cadre = origin.ticksToMove * 7 / 5;
                        break;
                    case 6:
                        y++;
                        cadre = origin.ticksToMove;
                        break;
                    case 7:
                        x++;
                        y++;
                        cadre = origin.ticksToMove * 7 / 5;
                        break;
                }
                location.GroundLevel[x][y] = this;
                if(path.isEmpty())
                    tx=ty=-1;
            } else {
                movedir = -1;
            }
        }
    }
    int moveOffsetX()
    {
        switch(movedir)
        {
            case 0: return -cadre*30/(origin.ticksToMove+1);
            case 1: return -cadre*150/(7*(origin.ticksToMove+1));
            case 3: return cadre*150/(7*(origin.ticksToMove+1));
            case 4: return cadre*30/(origin.ticksToMove+1);
            case 5: return cadre*150/(7*(origin.ticksToMove+1));
            case 7: return -cadre*150/(7*(origin.ticksToMove+1));
        }
        return 0;
    }
    int moveOffsetY()
    {
        switch(movedir)
        {
            case 1: return cadre*150/(7*(origin.ticksToMove+1));
            case 2: return cadre*30/(origin.ticksToMove+1);
            case 3: return cadre*150/(7*(origin.ticksToMove+1));
            case 5: return -cadre*150/(7*(origin.ticksToMove+1));
            case 6: return -cadre*30/(origin.ticksToMove+1);
            case 7: return -cadre*150/(7*(origin.ticksToMove+1));
        }
        return 0;
    }


    public void addAbility(Ability a)
    {
        if(!abilities.contains(a))
            abilities.add(a);
        else throw new IllegalArgumentException("Повторное добавление способности "+a.name+" объекту "+origin.name);
    }

    private Map.Coords getFreeSpace(Map.Environment e)
    {
        if(x>0)
            for (int i=0; i<origin.height; i++){
                if(y+i<25 && location.GroundLevel[x-1][y+i]==null)
                    return new Map.Coords(x-1, y+i);
            }
        if(y+origin.height<25)
            for (int i=0; i<origin.width; i++){
                if(x+i<25 && location.GroundLevel[x+i][y+origin.height]==null)
                    return new Map.Coords(x+i, y+origin.height);
            }
        if(x+origin.width<25)
            for (int i=origin.height-1; i>=-1; i--){
                if(y+i>=0 && location.GroundLevel[x+origin.width][y+i]==null)
                    return new Map.Coords(x+origin.width, y+i);
            }
        if(y>0)
            for (int i=origin.width-1; i>=-1; i--){
                if(x+i>=0 && location.GroundLevel[x+i][y-1]==null)
                    return new Map.Coords(x+i, y-1);
            }
        return null;
    }

    
    @Override
    public String toString()
    {
        return "HP: "+HitPoints+", Origin: "+origin;
    }
    public boolean inflictDamage(int amount)
    {
        HitPoints-=amount;
        if(HitPoints<=0)
        {
            /*if(x>0 && location.GroundLevel[y-1][x]==null)
            {
                new GameEntity(origin, location, x-1,y);
            }     */
            for (int i=0;i<origin.height;++i)
                for(int j=0;j<origin.width;++j)
                    location.GroundLevel[x+j][y+i]=null;

            if(owner!=null) {
                owner.PlayerEntity.remove(this);
                if (origin.Food > 0)
                    owner.FoodLimit -= origin.Food;
                else
                    owner.UsedFood += origin.Food;
            }
            return false;
        }
        return true;
    }
    public Ability getAbility(String name)
    {
        for(int i=0; i<abilities.size();++i)
            if (abilities.get(i).name.equals(name) )
                return abilities.get(i);
        for(int i=0; i<origin.abilities.size();++i)
            if (origin.abilities.get(i).name.equals(name) )
                return origin.abilities.get(i);
            return null;
    }

    public Interaction canInteract(GameEntity other) {

        Ability a=null, b=null;
        a=getAbility("GoldPicker");
        b=getAbility("WoodPicker");
        if(a!=null)
        {
            GoldPicker p=(GoldPicker)a;
            if(p.amount<p.maxamount) {
                if (other.getAbility("GoldReserve") != null)
                    return GoldPicker.pick;

            }
            else
            {
                if(other.owner==owner && other.getAbility("GoldStorage")!=null)
                {
                    return GoldPicker.drop;
                }
            }

        }
        if(b!=null)
        {
            WoodPicker p=(WoodPicker)b;
            if(p.amount<p.maxamount) {
                if (other.getAbility("WoodReserve") != null)
                    return WoodPicker.pick;
            }
            else
            {
                if(other.owner==owner && other.getAbility("WoodStorage")!=null)
                {
                    return WoodPicker.drop;
                }
            }

        }
        return null;
    }


    public int distanceTo(GameEntity other)
    {
        int DX, DY;
        if(x>other.x)
            DX=x-(other.x+other.origin.width);
        else
            if(x<other.x)
                DX=other.x-(x+origin.width);
            else DX=0;
            if(DX<0)DX=0;
        if(y>other.y)
            DY=y-(other.y+other.origin.height);
        else
        if(y<other.y)
            DY=other.y-(y+origin.height);
        else DY=0;
            if(DY<0)DY=0;
        return (int)Math.sqrt(DX*DX+DY*DY);
    }

}

class GoldMine extends GamePrototype{
    public static GoldMine Instance=new GoldMine();
    public static GameEntity createGoldMine(Map l, int x, int y, int amount)
    {
        GameEntity result=new GameEntity(GoldMine.Instance, l, Player.Neutrals,x, y);
        result.addAbility(new GoldReserve(amount));
        return result;
    }
    private GoldMine()
    {
        super(GamePrototype.Race.Nature, "Gold Mine", 2000, 25000, 0, 0, "GoldMineLabel.png", "GoldMine.png", "WC2Gold.gif",2,2);
    }
}

class Tree extends GamePrototype{
    public static Tree Instance=new Tree();
    public static GameEntity createTree(Map l, int x, int y, int amount)
    {
        GameEntity result=new GameEntity(Tree.Instance, l, Player.Neutrals,x, y);
        result.addAbility(new WoodReserve(amount));
        return result;
    }
    private Tree()
    {

        super(GamePrototype.Race.Nature, "Tree", 200, 0, 0, 0, "Tree.png", "Tree.png", "Tree.png",1,2);
    }

}

class OrcsFortress extends GamePrototype{
    public  OrcsFortress()
    {
        super(GamePrototype.Race.Orc, "Orcs Fortress", 2500, 200, 0, 0, "OrcFortressLabel.jpg", "OrcFortressPanel.png", "OrcTownhall.gif",4,3);
        addAbility(Ability.GoldStorage);
    }
        String
            Role = "buildings";
        int 
            HitPointsConst = 2500,
            HitPoints = 2500,
            UsedFood = 0,
            InitialFoodLimit = 10,
            InitialGold = 200,
            InitialWood = 0;
        ImageIcon 
            IconHirePeon = new ImageIcon("PeonPanel.png");
            
}

class OrcPeon extends GamePrototype{
    public static OrcPeon Instance=new OrcPeon();
    public  OrcPeon()
    {
        super(GamePrototype.Race.Orc, "Peon", 250, 75, 0, 0, "PeonLabel.png", "PeonPanel.png", "PeonMap.png");
        ticksToMove=10;
    }
    public static GameEntity createOrcPeon(Map l, Player p, int x, int y)
    {
        GameEntity result=new GameEntity(Instance, l, p, x,y);
        result.addAbility(new GoldPicker());
        result.addAbility(new WoodPicker());
        return result;
    }
    
        String 
            name = "Peon",
            race = "Orc",
            Role = "Builder";
        int 
            HitPointsConst = 250,
            Gold = 75,
            Wood = 0,    
            UsedFood = 1,
            Armor = 0;
        float 
            HitPoints = 250f,
            HitPointRegenerationRate = 0.25f;
        ImageIcon 
            IconLabel = new ImageIcon("PeonLabel.gif"),
            IconPanel = new ImageIcon("PeonPanel.png"),
            IconMap = new ImageIcon("Peon.png"),
        // abilities
            IconBuildStructure = new ImageIcon("PeonBuildStructure.png"),
            IconGather = new ImageIcon("PeonGather.png"),
            IconRepairBuildings = new ImageIcon("PeonRepairBuildings.png");
}

class OrcGrunt extends GamePrototype{
    public static OrcGrunt Instance=new OrcGrunt();
    public  OrcGrunt()
    {
        super(GamePrototype.Race.Orc, "Grunt", 600, 200, 0, 0, "GruntLabel.png", "GruntPanel.png", "GruntMap.png");
        ticksToMove=10;
    }
    public static GameEntity createOrcGrunt(Map l, Player p, int x, int y)
    {
        GameEntity result=new GameEntity(Instance, l, p, x,y);
        return result;
    }
        String 
            name = "Grunt",
            race = "Orc",
            Role = "Warrior";
        int 
            HitPointsConst = 700,
            Gold = 200,
            Wood = 0,    
            UsedFood = 3,
            Armor = 1;
        float 
            HitPoints = 700f,
            HitPointRegenerationRate = 0.25f;
        ImageIcon 
            IconLabel = new ImageIcon("GruntLabel.png"),
            IconPanel = new ImageIcon("GruntPanel.png"),
            IconMap = new ImageIcon("GruntMap.png"),
            
        // abilities
            IconAttack = new ImageIcon("MeleeAxeWeaponPanel.png"),
            IconPassiveArmor = new ImageIcon("ArmorPanel.png");
          //  IconRepairBuildings = new ImageIcon("PeonRepairBuildings.png");
}