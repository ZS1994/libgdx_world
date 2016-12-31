package com.mygdx.ai;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mygdx.actor.BaseActor;
import com.mygdx.control.CollisionControl;
import com.mygdx.control.IControl;
import com.mygdx.control.MoveControl;
import com.mygdx.tools.Transform;
import com.mygdx.world.TiledMapSystem;

/**
 * 【寻路算法】
 * @author 张顺
 *2016年10月17日17:51:33
 */
public class Pathfind implements IControl{
	public static final int STEP = TiledMapSystem.MAP_TILE_WIDTH;
	
	private BaseActor actorEnd;//目标
	private BaseActor actor;//自己
	private float x;
	private float y;
	private float xend;
	private float yend;
	private Step stepNow;//临时变量用于移动
	private List<Step> stepOn=new ArrayList<Step>();//开启列表
	private List<Step> stepOff=new ArrayList<Step>();//关闭列表
	//测试用
	String[][] str=new String[100][100];
	//定时器
	float time1=0,time2=0;
	
	

	public Pathfind(BaseActor actorStart,BaseActor actorEnd) {
		this.actor = actorStart;
		this.actorEnd=actorEnd;
		initialize();
	}
	
	@Override
	public void initialize() {
		this.x = actor.getX();
		this.y = actor.getY();
		this.xend = actorEnd.getX();
		this.yend = actorEnd.getY();
	}
	
	/**
	 * 安照路线来控制state，从而控制移动
	 * @param stepMain
	 */
	public void update() {
		if (stepNow==null) {
			if (time1>=50) {
				//重新获取一下起点终点
		        initialize();
				final Step startNode = new Step(Transform.xToxIndex(x), Transform.xToxIndex(y));  
		        final Step endNode = new Step(Transform.xToxIndex(xend), Transform.xToxIndex(yend));
		        if (!startNode.equals(endNode)) {
		        	long l1=new Date().getTime();
		        	//太耗时了，需要异步处理
		        	new Thread(){
		        		public void run() {
		        			Step parent = findPath(startNode, endNode);  
		        			while (parent!=null && parent.stepParent != null) {  
				        		parent.stepParent.stepChild=parent;
				        		parent = parent.stepParent;  
				        	}
				        	stepNow=startNode;
				        	time1=0;
		        		};
		        	}.start();
		        	long l2=new Date().getTime();
		        	System.out.println(l2-l1+"毫秒;time1:"+time1);
				}
			}else {
				time1++;
			}
		}else {
			/*
			float x=Transform.xToxIndex(actor.getX());
	    	float y=Transform.xToxIndex(actor.getY());
	    	if (x==stepNow.x && y>stepNow.y) {
				actor.setState(MoveControl.STATE_DOWN);
			}else if (x==stepNow.x && y<stepNow.y) {
				actor.setState(MoveControl.STATE_UP);
			}else if (x>stepNow.x && y==stepNow.y) {
				actor.setState(MoveControl.STATE_LEFT);
				actor.setStateLast(MoveControl.STATE_LEFT);
			}else if (x<stepNow.x && y==stepNow.y) {
				actor.setState(MoveControl.STATE_RIGHT);
				actor.setStateLast(MoveControl.STATE_RIGHT);
			}else {
				actor.setState(MoveControl.STATE_WAIT);
				stepNow=stepNow.stepChild;
			}
			*/
			float xi=stepNow.x*stepNow.w;
	    	float yi=stepNow.y*stepNow.h;
	    	float x=Transform.xToxIndex(actor.getX());
	    	float y=Transform.xToxIndex(actor.getY());
	    	if (x==stepNow.x && y>stepNow.y) {
				actor.setState(MoveControl.STATE_DOWN);
			}else if (x==stepNow.x && y<stepNow.y) {
				actor.setState(MoveControl.STATE_UP);
			}else if (x>stepNow.x && y==stepNow.y) {
				actor.setState(MoveControl.STATE_LEFT);
				actor.setStateLast(MoveControl.STATE_LEFT);
			}else if (x<stepNow.x && y==stepNow.y) {
				actor.setState(MoveControl.STATE_RIGHT);
				actor.setStateLast(MoveControl.STATE_RIGHT);
			}else {
				if (actor.getState()==MoveControl.STATE_DOWN) {
					if (actor.getY()<=yi) {
						actor.setState(MoveControl.STATE_WAIT);
						stepNow=stepNow.stepChild;
					}
				}else if (actor.getState()==MoveControl.STATE_UP) {
					if (actor.getY()>=yi) {
						actor.setState(MoveControl.STATE_WAIT);
						stepNow=stepNow.stepChild;
					}
				}else if (actor.getState()==MoveControl.STATE_LEFT) {
					if (actor.getX()<=xi) {
						actor.setState(MoveControl.STATE_WAIT);
						stepNow=stepNow.stepChild;
					}
				}else if (actor.getState()==MoveControl.STATE_RIGHT) {
					if (actor.getX()>=xi) {
						actor.setState(MoveControl.STATE_WAIT);
						stepNow=stepNow.stepChild;
					}
				}else {
					actor.setState(MoveControl.STATE_WAIT);
					stepNow=stepNow.stepChild;
				}
			}
		}
	}
	
	
	public Step findMinFNodeInOpneList() {  
        Step tempNode = stepOn.get(0);  
        for (Step node : stepOn) {  
            if (node.F < tempNode.F) {  
                tempNode = node;  
            }  
        }  
        return tempNode;  
    }  
	
	/*
	public ArrayList<Step> findNeighborNodes(Step currentNode) {  
		ArrayList<Step> arrayList = new ArrayList<Step>();  
        float tw=TiledMapSystem.MAP_TILE_WIDTH;
        float th=TiledMapSystem.MAP_TILE_HEIGHT;
		// 只考虑上下左右，不考虑斜对角  
        int topX = (int) currentNode.x;  
        int topY = (int) (currentNode.y + 1);  
        if (CollisionControl.canReach(topX,topY,actor) && !exists(stepOff, topX, topY)) {  
            arrayList.add(new Step(topX, topY));  
        }  
        int bottomX = (int) currentNode.x;  
        int bottomY = (int) (currentNode.y - 1);  
        if (CollisionControl.canReach(bottomX, bottomY, actor) && !exists(stepOff, bottomX, bottomY)) {  
            arrayList.add(new Step(bottomX, bottomY));  
        }  
        int leftX = (int) (currentNode.x - 1);  
        int leftY = (int) currentNode.y;  
        if (CollisionControl.canReach(leftX, leftY, actor) && !exists(stepOff, leftX, leftY)) {  
            arrayList.add(new Step(leftX, leftY));  
        }  
        int rightX = (int) (currentNode.x + 1);  
        int rightY = (int) currentNode.y;  
        if (CollisionControl.canReach(rightX, rightY, actor) && !exists(stepOff, rightX, rightY)) {  
            arrayList.add(new Step(rightX, rightY));  
        }  
		return arrayList;
	}
	*/
	
	public ArrayList<Step> findNeighborNodes(Step currentNode) {  
		ArrayList<Step> arrayList = new ArrayList<Step>();  
        // 只考虑上下左右，不考虑斜对角  
        int topX = (int) currentNode.x;  
        int topY = (int) (currentNode.y + 1);  
        if (canReach(topX, topY) && !exists(stepOff, topX, topY) && canReach(topX+1, topY+1)  && canReach(topX, topY+1)) {  
            arrayList.add(new Step(topX, topY));  
        }  
        int bottomX = (int) currentNode.x;  
        int bottomY = (int) (currentNode.y - 1);  
        if (canReach(bottomX, bottomY) && !exists(stepOff, bottomX, bottomY)) {  
            arrayList.add(new Step(bottomX, bottomY));  
        }  
        int leftX = (int) (currentNode.x - 1);  
        int leftY = (int) currentNode.y;  
        if (canReach(leftX, leftY) && !exists(stepOff, leftX, leftY)) {  
            arrayList.add(new Step(leftX, leftY));  
        }  
        int rightX = (int) (currentNode.x + 1);  
        int rightY = (int) currentNode.y;  
        if (canReach(rightX, rightY) && !exists(stepOff, rightX, rightY) && canReach(rightX+1, rightY+1) && canReach(rightX+1, rightY)) {  
            arrayList.add(new Step(rightX, rightY));  
        }  
		return arrayList;
	}
	
	
	public boolean canReach(int x, int y) {  
        if (x >= 0 && y >= 0) {  
            return TiledMapSystem.passEnble(x,y);  
        }  
        return false;
    }  
	
	
	
	public static Step find(List<Step> nodes, Step point) {  
        for (Step n : nodes)  
            if ((n.x == point.x) && (n.y == point.y)) {  
                return n;  
            }  
        return null;  
    } 
	
	public static boolean exists(List<Step> nodes, Step node) {  
        for (Step n : nodes) {  
            if ((n.x == node.x) && (n.y == node.y)) {  
                return true;  
            }  
        }  
        return false;  
    }  
  
    public static boolean exists(List<Step> nodes, int x, int y) {  
        for (Step n : nodes) {  
            if ((n.x == x) && (n.y == y)) {  
                return true;  
            }  
        }  
        return false;  
    }  
    
    public Step findPath(Step startNode, Step endNode) { 
    	stepOn.clear();
    	stepOff.clear();
        // 把起点加入 open list  
        stepOn.add(startNode);  
        while (stepOn.size() > 0) {  
            // 遍历 open list ，查找 F值最小的节点，把它作为当前要处理的节点  
        	Step currentNode = findMinFNodeInOpneList();  
            // 从open list中移除  
        	stepOn.remove(currentNode);  
            // 把这个节点移到 close list  
        	stepOff.add(currentNode);  
            ArrayList<Step> neighborNodes = findNeighborNodes(currentNode);  
            for (Step node : neighborNodes) {  
                if (exists(stepOn, node)) {  
                    foundPoint(currentNode, node);  
                } else {  
                    notFoundPoint(currentNode, endNode, node);  
                }  
            }  
            if (find(stepOn, endNode) != null) {  
                return find(stepOn, endNode);  
            }  
        }  
        return find(stepOn, endNode);  
    }  
  
    private void foundPoint(Step tempStart, Step node) {  
        int G = calcG(tempStart, node);  
        if (G < node.G) {  
            node.stepParent = tempStart;  
            node.G = G;  
            node.calcF();  
        }  
    }  
  
    private void notFoundPoint(Step tempStart, Step end, Step node) {  
        node.stepParent = tempStart;  
        node.G = calcG(tempStart, node);  
        node.H = calcH(end, node);  
        node.calcF();  
        stepOn.add(node);  
    }  
  
    private int calcG(Step start, Step node) {  
        int G = STEP;  
        int parentG = (int) (node.stepParent != null ? node.stepParent.G : 0);  
        return G + parentG;  
    }  
  
    private int calcH(Step end, Step node) {  
        int step = (int) (Math.abs(node.x - end.x) + Math.abs(node.y - end.y));  
        return step * STEP;  
    }  
  
    //------------------------------------------
    public List<Step> getStepOn() {
		return stepOn;
	}
	public void setStepOn(List<Step> stepOn) {
		this.stepOn = stepOn;
	}
	public List<Step> getStepOff() {
		return stepOff;
	}
	public void setStepOff(List<Step> stepOff) {
		this.stepOff = stepOff;
	}
	public BaseActor getActor() {
		return actor;
	}
	public void setActor(BaseActor actor) {
		this.actor = actor;
	}
    
}
