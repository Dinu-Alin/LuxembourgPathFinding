import javax.swing.*;
import java.awt.*;

public class Map {
	
	private static final Integer PREFERRED_WIDTH = 400+14;
	private static final Integer PREFERRED_HEIGTH = 1536/2+37;
	
	private static void initUI() {
		JFrame f = new JFrame("Map");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel myPanel = new MyPanel(PREFERRED_WIDTH,PREFERRED_HEIGTH);
		f.add(myPanel, BorderLayout.CENTER);
		f.pack();
		//f.setResizable(false);
		f.setSize(PREFERRED_WIDTH,PREFERRED_HEIGTH);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public static void main(String[] args) {
		
		//Graph g = new Graph("map2.xml");
		
		SwingUtilities.invokeLater(new Runnable()
				{
		            public void run() 
		            {
		            	initUI();
		            }
		        });
		        
			}
		
}

