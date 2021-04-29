import java.util.Comparator;


class EdgeComparator implements Comparator<Edge> { 
	    public int compare(Edge edge1, Edge edge2) { 
	    	return edge1.getLenght().compareTo(edge2.getLenght()); 
	    } 
	}

public class Edge{
	
		private Vertex start;
		private Vertex end;
		private Integer length;
		
		public Edge() {
			this.start = new Vertex();
			this.end = new Vertex();
			this.length = Integer.MAX_VALUE; //Simulate Infinity 
		}
		
		public Edge(Vertex start, Vertex finish, Integer lenght) {
			this.start = start;
			this.end = finish;
			this.length = lenght;
		}
		
		public Vertex getStart() {
			return start;
		}

		public void setStart(Vertex start) {
			this.start = start;
		}
		
		public Vertex getFinish() {
			return end;
		}
		public void setFinish(Vertex end) {
			this.end = end;
		}
		public Integer getLenght() {
			return length;
		}
		public void setLenght(Integer length) {
			this.length = length;
		}
		
		@Override
		public String toString()
		{
			return start+","+end+":"+length;
			
		}

		
		/*
		public void drawArc(Graphics g,int diam)
		{
			if (start != null)
			{
	            g.setColor(Color.BLACK);
	            drawArrowLine(g,start.x,start.y,end.x,end.y,40,10,diam/2);
	        }
		}
		
		public void drawArcN(Graphics g,int diam)
		{
			if (start != null)
			{
	            g.setColor(Color.BLACK);
	            g.drawLine(start.x, start.y, end.x, end.y);
	        }
		}
		
		private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h,int diam) {
		   
			int dx = x2 - x1, dy = y2 - y1;
		    double D = Math.sqrt(dx*dx + dy*dy);
		    double xm = D-d, xn = xm, xp, ym = h, yn = -h, yp, x;
		    double sin = dy / D, cos = dx / D, tetha;
		    
		    double angle1 = Math.atan2(y1 - y2, x1 - x2);
		    double angle2 = Math.atan2(0 , diam);
		    tetha = (angle1-angle2);

		    xp=x2+diam*Math.cos(tetha);
		    yp=y2+diam*Math.sin(tetha);
		    		
		    x = xm*cos - ym*sin + x1;
		    ym = xm*sin + ym*cos + y1;
		    xm = x;

		    x = xn*cos - yn*sin + x1;
		    yn = xn*sin + yn*cos + y1;
		    xn = x;
		    
		    int[] xpoints = {(int)xp, (int) xm, (int) xn};
		    int[] ypoints = {(int)yp, (int) ym, (int) yn};

		    
		    g.drawLine(x1, y1, x2, y2);
		    g.fillPolygon(xpoints, ypoints, 3);
		}
		*/
}
