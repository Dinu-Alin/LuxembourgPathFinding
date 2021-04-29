import java.text.DecimalFormat;

public class Converter {
	
	public Double latx; // minimum latitude position 
	public Double laty; // maximum latitude position
	Double latyt; // laty - latx maximum latitude transposed pos.
	
	public Double lonx; // minimum longitude position 
	public Double lony; // maximum longitude position
	public Double lonyt;
	
	public Integer screenWidth;
	public Integer screenHeight;
	
	public Converter(Double latx, Double laty, Double lonx, Double lony, Integer screenWidth, Integer screenHeight) {
		super();
		this.latx = latx;
		this.laty = laty;
		this.lonx = lonx;
		this.lony = lony;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		this.latyt = this.laty - this.latx; //maximum latitude transposed pos.
		this.lonyt = this.lony - this.lonx; //maximum longitude transposed pos.
		}
	
	public Integer LatitudeToPx(Double lat) {
		return (int)(screenWidth / (latyt/ (lat - latx)));
	}
	
	public Integer LongitudeToPx(Double lon) {
		return (int)(screenHeight / (lonyt/ (lon - lonx)));
	}
	
	//TODO
	public Double PxToLatitude(Integer x){
		Double px = 0.00000d;
		
		if(x!=0) {
			px = (latyt/((double)screenWidth/(double)x) + latx);
			DecimalFormat formatter = new DecimalFormat("#0.00000");
			return Double.parseDouble(formatter.format(px));
		}
			//px = Math.round(px*1000000.0)/1000000.0;
		return px;
	}
	public Double PxToLongitude(Integer y) {
		Double px = 0.00000d;
		
		if(y!=0) {
			//px = (-lonyt/(screenHeight/y) + lony); <- I need this but in oposite direcetion
			px = (lonx - ((-lonyt)/((double)(screenHeight)/(double)y)));
			DecimalFormat formatter = new DecimalFormat("#00.00000");
			return Double.parseDouble(formatter.format(px));
			//px = Math.round(px*100000.0)/100000.0;
		}
		return px;
	}
	
}
