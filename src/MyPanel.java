import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Map;
import java.util.*;

class MyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private  Integer PREFERRED_WIDTH = 72;
	private  Integer PREFERRED_HEIGTH = 128;
	private  Integer PADDING = 10;
	
	private Graph m_graph;
	private Integer state;
	private Edge idStart;
	private Edge idEnd;
	private Point pointStart;
	private Point pointEnd;

	private Integer start;
	private Integer end;

	private int[] prevB;
	private Map<Integer, Integer> cameFrom;
	private Map<Integer, Integer> costSoFar;

	Converter converter;

	MyPanel(Integer width, Integer height) {
		
		PREFERRED_WIDTH = width;
		PREFERRED_HEIGTH = height;
		PADDING = PREFERRED_HEIGTH / 10;
		converter = new Converter(Constraints.minLan, Constraints.maxLan, Constraints.maxLon, Constraints.minLon, PREFERRED_WIDTH-PADDING, PREFERRED_HEIGTH-PADDING);
		
		m_graph = new Graph();
		
		long ts = System.currentTimeMillis();
		ReadMapOfCityXML(this.m_graph, "map2.xml");
		long time = System.currentTimeMillis() - ts;
		
		System.out.println("Reading Done in: "+time+" ms");

		state = 0;

		addMouseListener(new MouseAdapter() {

			// evenimentul care se produce la apasarea mousse-ului
			public void mousePressed(MouseEvent e) {
				switch (state) {
				case 0:
					pointStart = e.getPoint();
					break;
				case 1:
					pointEnd = e.getPoint();
					break;
				default:
					break;
				}
			}

			// evenimentul care se produce la eliberarea mousse-ului
			public void mouseReleased(MouseEvent e) {
				if (state == 0) {
					idStart = findNodeAt(pointStart.getX(), pointStart.getY());
					
					System.out.println("First Node: " + idStart.getFinish().getLatitude() + " " 
					+ idStart.getFinish().getLongitude() + " . ID:" + idStart.getFinish().getId());
					
					start = idStart.getStart().getId();
					++state;
				} else if (state == 1) {
					idEnd = findNodeAt(pointEnd.getX(), pointEnd.getY());
					
					System.out.println("Second Node: " + idEnd.getFinish().getLatitude() + " " 
							+ idEnd.getFinish().getLongitude() + " . ID:" + idEnd.getFinish().getId());
					
					end = idEnd.getStart().getId();
					repaint();
					// printPath();
				}

			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);// apelez metoda paintComponent din clasa de baza

		Graphics2D g2 = (Graphics2D) g;
		
		for (Vertex index : m_graph.getNodes()) {
			index.drawNode(g, 2);
		}

		if (state == 1) {

			Scanner in = new Scanner(System.in);
			System.out.println("Ce algoritm doriti sa urmati?");
			System.out.println("0 - Dijkstra");
			System.out.println("1 - BellmanFord");
			Integer type = (in.nextInt());
			long ts = System.currentTimeMillis();
			if (type == 0) {
				dijkstra();

				//////////////////////////////////
				Integer parent = -1;
				Integer curr = end;
				while (cameFrom.containsKey(curr)) {

					parent = cameFrom.get(curr);

					assert curr != null;

					if (parent != null) {
						g2.drawLine(this.m_graph.getNodes().get(curr).getLongitude().intValue(),
								this.m_graph.getNodes().get(curr).getLatitude().intValue(),
								this.m_graph.getNodes().get(parent).getLongitude().intValue(),
								this.m_graph.getNodes().get(parent).getLatitude().intValue());
					}
					g2.setStroke(new BasicStroke(2));
					g2.setColor(Color.RED);
					if (parent == start) {
						break;
					}

					curr = parent;
				}
			} else {
				BellmanFord();

				//////////////////////////////////
				Integer parent = -1;
				Integer curr = end;
				while (curr != -1) {

					parent = prevB[curr];

					if (parent != -1) {
						g2.drawLine(this.m_graph.getNodes().get(curr).getLongitude().intValue(),
								this.m_graph.getNodes().get(curr).getLatitude().intValue(),
								this.m_graph.getNodes().get(parent).getLongitude().intValue(),
								this.m_graph.getNodes().get(parent).getLatitude().intValue());
					}
					g2.setStroke(new BasicStroke(2));
					g2.setColor(Color.RED);
					if (parent == start) {
						break;
					}

					curr = parent;
				}
			}
			long time = System.currentTimeMillis() - ts;
			System.out.println("Finding Done in: "+time+" ms");
			state = 0;
		}
	}

	private void dijkstra() {

		cameFrom = new HashMap<>();
		costSoFar = new HashMap<>();

		PriorityQueue<Pair> frontier = new PriorityQueue<>(new PairComparator());

		frontier.add(new Pair(start, 0));
		cameFrom.put(start, null);
		costSoFar.put(start, 0);

		while (!frontier.isEmpty()) {
			Pair current = frontier.remove();

			if (current.getX() == end) {
				System.out.println("Found! Total cost: " + costSoFar.get(current.getX()));
				// isMarking = true;
				// repaint();
				break;
			}

			for (Edge index : this.m_graph.getMap().get(current.getX())) {

				Pair next = new Pair(index.getFinish().getId(), index.getLenght());

				int newCost = costSoFar.get(current.getX()) + index.getLenght();

				if (!costSoFar.containsKey(next.getX()) || newCost < costSoFar.get(next.getX())) {

					costSoFar.put(next.getX(), newCost);
					next.setY(newCost);
					frontier.add(next);
					cameFrom.put(next.getX(), current.getX());
				}
			}
		}
	}

	void BellmanFord() {
		int V = m_graph.getNodes().size();
		int dist[] = new int[V];

		for (int i = 0; i < V; ++i)
			dist[i] = Integer.MAX_VALUE;
		dist[start] = 0;

		prevB = new int[V];
		for (int i = 1; i < V; ++i) {
			prevB[i] = -1;
		}

		// Relax all edges |V| - 1 times
		for (int i = 1; i < V; ++i) {
			for (int j = 0; j < m_graph.getMap().size(); ++j)
				for (int k = 0; k < m_graph.getMap().get(j).size(); ++k) {

					int u = m_graph.getMap().get(j).get(k).getStart().getId();
					int v = m_graph.getMap().get(j).get(k).getFinish().getId();
					int weight = m_graph.getMap().get(j).get(k).getLenght();
					if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
						prevB[v] = j;
						dist[v] = dist[u] + weight;
						if (v == end) {
							System.out.println("Found! Total cost: " + dist[v]);
							i = V;
						}
					}
				}
		}
		
		/*NEGATIVE CYCLE
		 * for (int j = 0; j < m_graph.getMap().size(); ++j) for(int k = 0; k <
		 * m_graph.getMap().get(j).size(); ++k) {
		 * 
		 * int u = m_graph.getMap().get(j).get(k).getStart().getId(); int v =
		 * m_graph.getMap().get(j).get(k).getFinish().getId(); int weight =
		 * m_graph.getMap().get(j).get(k).getLenght(); if (dist[u] != Integer.MAX_VALUE
		 * && dist[u] + weight < dist[v]) {
		 * System.out.println("Graph contains negative weight cycle"); return; }
		 * 
		 * }
		 */
	}

	void printArr(int dist[], int V) {
		System.out.println("Vertex Distance from Source");
		for (int i = 0; i < V; ++i)
			System.out.println(i + "\t\t" + dist[i]);
	}

	public Edge findNodeAt(double x, double y) {
		Double shortestDistance = Double.MAX_VALUE;
		Edge nearArc = new Edge();

		// Iterate the map
		for (Integer i = 0; i < m_graph.getMap().size(); ++i) {
			Iterator<Edge> itr = m_graph.getMap().get(i).iterator();

			while (itr.hasNext()) {
				Edge currentArc = itr.next();
				Double currentDistance = euclidianDistance(y, x, currentArc);
				if (currentDistance < shortestDistance) {
					shortestDistance = currentDistance;
					nearArc = currentArc;
				}

			}
		}

		return nearArc;

	}

	public Double euclidianDistance(Double x, Double y, Edge point2) {

		return ((x - point2.getFinish().getLatitude()) * (x - point2.getFinish().getLatitude())
				+ (y - point2.getFinish().getLongitude()) * (y - point2.getFinish().getLongitude()));
	}

	public void ReadMapOfCityXML(Graph graph, String fileName) {

		try {

			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// doc.getDocumentElement().normalize();

			Node map = doc.getDocumentElement();
			if (map.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) map;

				graph.setNameOfCity(eElement.getAttribute("description"));
			}

			NodeList nodesList = doc.getElementsByTagName("node");
			int size = nodesList.getLength();
			for (int index = 0; index < size; index++) {
				Node node = nodesList.item(index);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nodesList.item(index);

					Double x = Double.parseDouble(e.getAttribute("longitude")) / 100000;
					Double y = Double.parseDouble(e.getAttribute("latitude")) / 100000;
					Integer id = Integer.parseInt(e.getAttribute("id"));

					y = (double) converter.LatitudeToPx(y) +  PADDING / 2;
					x = (double) converter.LongitudeToPx(x) + PADDING / 2;

					graph.getNodes().add(new Vertex(id, y, x));
				}
			}

			graph.setMap(graph.getNodes().size());

			nodesList = doc.getElementsByTagName("arc");
			size = nodesList.getLength();
			graph.setSizeE(size);
			for (int index = 0; index < size; index++) {

				Node node = nodesList.item(index);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nodesList.item(index);
					Integer from = Integer.parseInt(e.getAttribute("from"));
					Integer to = Integer.parseInt(e.getAttribute("to"));
					Integer lenght = Integer.parseInt(e.getAttribute("length"));

					graph.getMap().get(from)
							.add(new Edge(graph.getNodes().get(from), graph.getNodes().get(to), lenght));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}