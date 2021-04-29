import java.awt.*;

public class Vertex {
	
	private Integer id;
	private Double longitude;
	private Double latitude;
	
	public Vertex() {
		this.id = -1;
		this.longitude = -1d;
		this.latitude = -1d;
	}
	
	public Vertex(Integer id, Double longitude, Double latitude) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public void drawNode(Graphics g, int node_diam)
	{		
		g.setColor(Color.BLACK);
		
		Double x = longitude - (node_diam/2);
		Double y = latitude - (node_diam/2);
		
        g.fillOval(x.intValue(), y.intValue(), node_diam, node_diam);
	}

}