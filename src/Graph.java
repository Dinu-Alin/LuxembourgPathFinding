import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
		
	private ArrayList<Vertex> nodes;
	private Integer sizeE; 
	ArrayList<LinkedList<Edge>> map;
	private String nameOfCity;

	public ArrayList<Vertex> getNodes() {
		return nodes;
	}

	public void addNode(Vertex node)
	{
		nodes.add(node);
	}
	
	public void setMap(Integer size)
	{
		this.map = new ArrayList<>(size);
		
		for(int i = 0;i < size; ++i) {
			this.map.add(new LinkedList<>());
		}
	}
	
	public ArrayList<LinkedList<Edge>> getMap() {
		return map;
	}
	
	public String getNameOfCity() {
		return nameOfCity;
	}

	public void setNameOfCity(String nameOfCity) {
		this.nameOfCity = nameOfCity;
	}
	
	public Graph()
	{
		nodes = new ArrayList<>();
		setMap(0);
	}

	public Integer getSizeE() {
		return sizeE;
	}

	public void setSizeE(Integer sizeE) {
		this.sizeE = sizeE;
	}
		
}

