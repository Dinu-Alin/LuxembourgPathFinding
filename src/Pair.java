import java.util.Comparator;

class PairComparator implements Comparator<Pair>
{
	public int compare(Pair obj1, Pair obj2) 
    {  
  
        int difference = (obj1.getY() - obj2.getY()); 
  
        return difference;
    }
}

public class Pair {

    private int x;
    private int y;
    
    public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

    public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

	@Override
	public String toString()
	{
		return x+","+y;
		
	}

}