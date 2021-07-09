/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GamePackage;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Дима
 */
class Map {

    static int []dx={1,1,0,-1,-1,-1,0,1};
    static int []dy={0,-1,-1,-1,0,1,1,1};

    public int[] MapX =

            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    public int[] MapY =

            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    int pathLength[][];
    int gCx, gCy;

    enum Environment
    {
        GROUND, WATER, BOTH, AIR;
    }

    public static class Coords{
        int x,y;
        public Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }


    public GameEntity[][] GroundLevel = new GameEntity[25][25];

    public Map() {
        pathLength=new int[25][25];
    }

    public void setPath (int x, int y) {
        for(int j=0; j<25;++j)
            for(int i=0;i<25;++i)
                pathLength[i][j]=-1;
        PriorityQueue<PathCell> Q=new PriorityQueue<>();
        Q.add(new PathCell(x,y,0));
        while(!Q.isEmpty())
        {
            PathCell cur=Q.poll();
            if(pathLength[cur.x][cur.y]==-1)
            {
                pathLength[cur.x][cur.y] = cur.cost;
                for(int i=0;i<8;++i)
                {
                    if(cur.x+dx[i]>0 && cur.x+dx[i]<25 && cur.y+dy[i]>0 && cur.y+dy[i]<25)
                        if(GroundLevel[cur.x+dx[i]][cur.y+dy[i]] == null)
                            Q.add(new PathCell(cur.x+dx[i], cur.y+dy[i], cur.cost + (10+(i%2)*4)  ));
                }

                /*if (cur.x > 0)
                {
                    if (cur.y > 0 && GroundLevel[cur.x - 1][cur.y - 1] == null && pathLength[cur.x - 1][cur.y - 1] == -1)
                        Q.add(new PathCell(cur.x - 1, cur.y - 1, cur.cost + 14));
                    if (cur.y < 24 && GroundLevel[cur.x - 1][cur.y + 1] == null && pathLength[cur.x - 1][cur.y + 1] == -1)
                        Q.add(new PathCell(cur.x - 1, cur.y + 1, cur.cost + 14));
                    if (GroundLevel[cur.x - 1][cur.y] == null && pathLength[cur.x - 1][cur.y] == -1)
                        Q.add(new PathCell(cur.x - 1, cur.y, cur.cost + 10));
                }
                if (cur.x < 24)
                {
                    if (cur.y > 0 && GroundLevel[cur.x + 1][cur.y - 1] == null && pathLength[cur.x + 1][cur.y - 1] == -1)
                        Q.add(new PathCell(cur.x + 1, cur.y - 1, cur.cost + 14));
                    if (cur.y < 24 && GroundLevel[cur.x + 1][cur.y + 1] == null && pathLength[cur.x + 1][cur.y + 1] == -1)
                        Q.add(new PathCell(cur.x + 1, cur.y + 1, cur.cost + 14));
                    if (GroundLevel[cur.x + 1][cur.y] == null && pathLength[cur.x + 1][cur.y] == -1)
                        Q.add(new PathCell(cur.x + 1, cur.y, cur.cost + 10));
                }
                if (cur.y > 0 && GroundLevel[cur.x][cur.y - 1] == null && pathLength[cur.x][cur.y - 1] == -1)
                    Q.add(new PathCell(cur.x, cur.y - 1, cur.cost + 10));
                if (cur.y < 24 && GroundLevel[cur.x][cur.y + 1] == null && pathLength[cur.x][cur.y + 1] == -1)
                    Q.add(new PathCell(cur.x, cur.y + 1, cur.cost + 10));*/
            }
        }
    }

    public Queue<Integer> setPath(GameEntity who, String abilityName)
    {
        int x=who.x;
        int y=who.y;

        gCx=x; gCy=y;
        for(int j=0; j<25;++j)
            for(int i=0;i<25;++i)
                pathLength[i][j]=-1;
        PriorityQueue<PathCell> Q=new PriorityQueue<>();
        Q.add(new PathCell(x,y,0));

        while(!Q.isEmpty()/*&& pathLength[cx][cy]==-*/)
        {
            PathCell cur=Q.poll();
            if(pathLength[cur.x][cur.y]==-1)
            {
                pathLength[cur.x][cur.y] = cur.cost;
                if(GroundLevel[cur.x][cur.y]!=null && GroundLevel[cur.x][cur.y]!=who) {
                    who.target=GroundLevel[cur.x][cur.y];
                    who.tx=cur.x;
                    who.ty=cur.y;
                    return createPath(cur.x, cur.y);
                }
                for(int i=0;i<8;++i)
                {
                    if(cur.x+dx[i]>0 && cur.x+dx[i]<25 && cur.y+dy[i]>0 && cur.y+dy[i]<25)
                        if(GroundLevel[cur.x+dx[i]][cur.y+dy[i]] ==null
                        ||who.owner==GroundLevel[cur.x+dx[i]][cur.y+dy[i]].owner && GroundLevel[cur.x+dx[i]][cur.y+dy[i]].getAbility(abilityName)!=null)
                            Q.add(new PathCell(cur.x+dx[i], cur.y+dy[i], cur.cost + (10+(i%2)*4)  ));
                }
            }
        }
        return null;
    }

    public void setPath (GameEntity who, int cx, int cy) {
        int x=who.x;
        int y=who.y;

        GameEntity where =GroundLevel[cx][cy];
        if(where!=null) {
            Interaction i=who.canInteract(where);
            if(i!=null) {
                who.target = where;
                who.interaction=i;
            }
        }
        else who.target=null;

        int D=(x-cx)*(x-cx)+(y-cy)*(y-cy), D1;
        gCx=x; gCy=y;
        for(int j=0; j<25;++j)
            for(int i=0;i<25;++i)
                pathLength[i][j]=-1;
        PriorityQueue<PathCell> Q=new PriorityQueue<>();
        Q.add(new PathCell(x,y,0));

        while(!Q.isEmpty()/*&& pathLength[cx][cy]==-*/)
        {
            PathCell cur=Q.poll();
            if(pathLength[cur.x][cur.y]==-1)
            {
                pathLength[cur.x][cur.y] = cur.cost;
                D1=(cur.x-cx)*(cur.x-cx)+(cur.y-cy)*(cur.y-cy);
                if(D1<D)
                {
                    gCx=cur.x;
                    gCy=cur.y;
                    D=D1;
                }
                for(int i=0;i<8;++i)
                {
                    if(cur.x+dx[i]>0 && cur.x+dx[i]<25 && cur.y+dy[i]>0 && cur.y+dy[i]<25)
                        if(GroundLevel[cur.x+dx[i]][cur.y+dy[i]] ==null   || GroundLevel[cur.x+dx[i]][cur.y+dy[i]] == who.target)
                            Q.add(new PathCell(cur.x+dx[i], cur.y+dy[i], cur.cost + (10+(i%2)*4)  ));
                }
            }
        }
    }


    public Queue<Integer> createPath(int x, int y)
    {
        LinkedList<Integer> Q=new LinkedList<>();
        if(pathLength[x][y]==-1)
        {
            x=gCx;
            y=gCy;
        }
        while(pathLength[x][y]>0)
        {
            if(x>0 && y>0 && pathLength[x-1][y-1]==pathLength[x][y]-14)
            {   Q.offerFirst(7); x--; y--;}
            else if(x>0 && y<24 && pathLength[x-1][y+1]==pathLength[x][y]-14)
            {   Q.offerFirst(1); x--; y++;}
            else if(x>0 && pathLength[x-1][y]==pathLength[x][y]-10)
            {    Q.offerFirst(0); x--;}
            else if(x<24 && y>0 && pathLength[x+1][y-1]==pathLength[x][y]-14)
            {   Q.offerFirst(5); x++; y--;}
            else if(x<24 && y<24 && pathLength[x+1][y+1]==pathLength[x][y]-14)
            {   Q.offerFirst(3); x++; y++;}
            else if(x<24 && pathLength[x+1][y]==pathLength[x][y]-10)
            {    Q.offerFirst(4); x++;}
            else if(y>0 && pathLength[x][y-1]==pathLength[x][y]-10)
            {    Q.offerFirst(6);y--;}
            else if(y<24 && pathLength[x][y+1]==pathLength[x][y]-10)
            {    Q.offerFirst(2);y++;}
        }
        System.out.println(Q);
        return Q;
    }
}