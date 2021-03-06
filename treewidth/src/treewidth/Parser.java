package treewidth;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	
	BufferedReader reader;
	
	public Parser() {}
	
	public Graph parseGraph(String path) throws IOException {
		Graph graph = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String[] splitLine;
			
			String line = reader.readLine();
			
			while(line != null) {
				splitLine = line.split(" ");
				
				//Skip comments
				if(splitLine[0].equals("c")) {
					//Do nothing
				}
				
				else if(splitLine[0].equals("p")) {
					splitLine = line.split(" ");
					int nbrOfVertices = Integer.parseInt(splitLine[2]);
					int nbrOfEdges = Integer.parseInt(splitLine[3]);
					graph = new Graph(nbrOfVertices);
				}
				else {
					int node1index = Integer.parseInt(splitLine[0]);
					int node2index = Integer.parseInt(splitLine[1]);
					Node node1 = graph.getNode(node1index);
					Node node2 = graph.getNode(node2index);
					node1.addNeighbor(node2);
					node2.addNeighbor(node1);		
				}
				
				line = reader.readLine();
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file");
			e.printStackTrace();
		}
		//System.out.println("Finished parsing graph");
		return graph;
	}
	
	public TreeDecompositionGraph parseTree(String path, Graph graph) throws IOException {
		TreeDecompositionGraph tree = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String[] splitLine;
			int nbrOfBags;
			int width;
			int nbrOfVertices;
			
			String line = reader.readLine();	
			while(line != null) {
				//System.out.println(line);
				splitLine = line.split(" ");
				
				//skip comments
				if(splitLine[0].equals("c")) {
					//Do nothing
				}
				
				else if(splitLine[0].equals("s")) {
					splitLine = line.split(" ");
					nbrOfBags = Integer.parseInt(splitLine[2]);
					width = Integer.parseInt(splitLine[3]) - 1;
					nbrOfVertices = Integer.parseInt(splitLine[4]);
					tree = new TreeDecompositionGraph(nbrOfBags, width);
				}
				
				//Add nodes to bag
				else if(splitLine[0].equals("b")) {
					Bag bag = tree.getBag(Integer.parseInt(splitLine[1]));
					//Add all nodes on the line
					for(int i = 2; i < splitLine.length; i++) {
						int nodeIndex = Integer.parseInt(splitLine[i]);
						bag.addNode(graph.getNode(nodeIndex));
					}
				}
				else {
					Bag bag1 = tree.getBag(Integer.parseInt(splitLine[0]));
					Bag bag2 = tree.getBag(Integer.parseInt(splitLine[1]));
					bag1.addChild(bag2);
					bag2.addChild(bag1);
				}
				
				line = reader.readLine();
				continue;
				
				
			}
			
			tree.calculateAllIS();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file");
			e.printStackTrace();
		}
		//System.out.println("Finished parsing tree");
		return tree;
	}
	

}
