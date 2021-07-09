package GamePackage;

/**
 * Created by Владимир on 28.11.2019.
 */
public class PathCell implements Comparable<PathCell>
{
    public int x;
    public int y;
    public int cost;

    public PathCell(int x, int y, int cost)
    {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }


    @Override
    public int compareTo(PathCell o)
    {
        return this.cost-o.cost;
    }
}