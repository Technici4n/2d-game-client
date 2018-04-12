package Base;

public class Unit implements Comparable<Unit> {

	private static int nID = 0;
	public int x, y;
	private int ID;
	
	
	public Unit(int x, int y){
		this.x = x;
		this.y = y;
		ID = nID;
		nID ++;
	
	}
	
	public Unit(int id){
		ID = id;
	}


	@Override
	public int compareTo(Unit arg0) {
		
		return Integer.compare(ID, arg0.ID);
	}
	
}
