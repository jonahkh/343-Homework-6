import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

/**
 * This class runs the program and contains all tests.
 * 
 * @author Jonah Howard
 * @author Jacob Tillett
 */
public class Driver {
	
	/** Text that displays the current distance from the starting city to the ending city. */
	public static final String DISTANCE = "Distance = ";
	
	/** The JFrame for this interface. */
	private final JFrame myFrame;
	
	/** The main panel housing all components. */
	private final JPanel myPanel;
	
	/** File chooser to select the graph input file. */
	private final JFileChooser myChooser;
	
	/** ComboBox displaying the list of cities to start from. */
	private final JComboBox<Vertex> myStartCities;
	
	/** ComboBox displaying the list of cities to end. */
	private final JComboBox<Vertex> myEndCities;
	
	/** Displays the current distance from the starting city to the ending. */
	private final JLabel myCityDistance;
	
	/** The current algorithm being used. */
	private Algorithm myAlgorithm;
	
	/** The current graph. */
	private SimpleGraph myGraph;
	
	/** The list of all DijkstraHeapNodes nodes. */
	private Set<Vertex> myVertices;
	
	/** The button that switches the algorithm being implemented to the MinHeap. */
	private JRadioButton myHeapButton;
	
	/** The button that switches the algorithm being implemented to the array. */
	private JRadioButton myArrayButton;
	
	/** The path from the starting city to the ending city. */
	private JLabel myPath;
	
	/**
	 * Initialize a new Driver.
	 */
	public Driver() {
		myFrame = new JFrame("Trip Planner");
		myPanel = new JPanel();
		myHeapButton = new JRadioButton();
		myArrayButton = new JRadioButton();
		myChooser = new JFileChooser("./testGraphs");
		myStartCities = new JComboBox<Vertex>();
		myEndCities = new JComboBox<Vertex>();
		myCityDistance = new JLabel("Distance = ");
		myPath = new JLabel();
		myVertices = new HashSet<>();
		setUp();
	}
	
	/**
	 * Set up the file chooser listener.
	 */
	private void setUpFileChooserListener() {
		myChooser.showOpenDialog(myFrame);
		try {
			
			myGraph = new SimpleGraph();
	        GraphInput.LoadSimpleGraph(myGraph, myChooser.getSelectedFile().toString());
	        myStartCities.removeAllItems();
	        myEndCities.removeAllItems();
	        if (myHeapButton.isSelected()) {
		        myAlgorithm = new MinHeapImplementation(myGraph);
		        myAlgorithm.runAlgorithm(myGraph.aVertex());
		        myVertices = ((MinHeapImplementation) myAlgorithm).getVertices();
	        } else {
	        	//TODO fill out 
		        myAlgorithm = new ArrayImplementation(myGraph);
		        myAlgorithm.runAlgorithm(myGraph.aVertex());
		        myVertices = ((ArrayImplementation) myAlgorithm).getVertices();
	        	System.out.println("Not yet implemented!!");
	        }
	        
	        for (Vertex v : myVertices) {
	        	myStartCities.addItem(v);
	        	myEndCities.addItem(v);	        	
	        }
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}
	
	/**
	 * Set up the components of the user interface. 
	 */
	private void setUp() {
		final JPanel panel = new JPanel();
		final JPanel startCity = new JPanel();
		final JPanel endCity = new JPanel();
		final JLabel startLabel = new JLabel("Starting City");
		final JLabel endLabel = new JLabel("Ending City");
		final JPanel northPanel = new JPanel();
		final JLabel heapMode = new JLabel("Heap implementation");
		final JLabel arrayMode = new JLabel("Array implementation");
		final JButton chooseFile = new JButton("Choose File");
		
		northPanel.setBackground(Color.WHITE);
		northPanel.add(chooseFile);
		northPanel.add(heapMode);
		northPanel.add(myHeapButton);
		northPanel.add(arrayMode);
		northPanel.add(myArrayButton);
		startCity.setBackground(Color.WHITE);
		endCity.setBackground(Color.WHITE);
		panel.setBackground(Color.WHITE);
		myPanel.setBackground(Color.WHITE);
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		chooseFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				setUpFileChooserListener();
			}
		});
		JScrollPane pane = new JScrollPane(myPath);
		myPath.setBackground(Color.WHITE);
		pane.setBackground(Color.WHITE);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBackground(Color.WHITE);
		pane.setMaximumSize(new Dimension(100, 200));
		pane.setPreferredSize(new Dimension(100, 200));
		startCity.add(startLabel);
		startCity.add(myStartCities);
		endCity.add(endLabel);
		endCity.add(myEndCities);
		panel.add(myPanel);
		myPanel.add(northPanel);
		myPanel.add(startCity);
		myPanel.add(endCity);
		myPanel.add(myCityDistance);
		myPanel.add(new JLabel("Path: "));
		myPanel.add(pane);
		myFrame.add(panel);
		addButtonListeners();
		formatFrame();
	}
	
	/**
	 * Add the listeners for the buttons. 
	 */
	private void addButtonListeners() {
		// Default: use heap implementation
		myHeapButton.doClick();
		myHeapButton.setBackground(Color.WHITE);
		myArrayButton.setBackground(Color.WHITE);
		final ButtonGroup group = new ButtonGroup();
		group.add(myHeapButton);
		group.add(myArrayButton);

		myHeapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				myAlgorithm = new MinHeapImplementation(myGraph);
		        myAlgorithm.runAlgorithm((Vertex) myStartCities.getSelectedItem());
			}
		});
		
		myArrayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				myAlgorithm = new ArrayImplementation(myGraph);
				myAlgorithm.runAlgorithm((Vertex) myStartCities.getSelectedItem());
			}
		});
	}
	
	/**
	 * Format the JFrame.
	 */
	private void formatFrame() {
		final Toolkit kit = Toolkit.getDefaultToolkit();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setBackground(Color.WHITE);
		myFrame.setSize(new Dimension(600, 600));
		myFrame.setResizable(false);
		myFrame.setLocation((int) (kit.getScreenSize().getWidth() / 2 - myFrame.getWidth() / 2),
				(int) (kit.getScreenSize().getHeight() / 2 - myFrame.getHeight() / 2));
		myStartCities.setBackground(Color.WHITE);
		myStartCities.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg) {
				if (myHeapButton.isSelected()) {
					Vertex start = (Vertex) myStartCities.getSelectedItem();
					Vertex last = (Vertex) myEndCities.getSelectedItem();
					if (last != null) {
						myCityDistance.setText("Distance = " 
								+ ((MinHeapImplementation) myAlgorithm)
								.getCorrespondingNode(last).getDistance());
						myPath.setText(myAlgorithm.getPath(start, last));
					} 
					myAlgorithm.runAlgorithm(start);
				} else {
					//TODO fill this part out
					Vertex start = (Vertex) myStartCities.getSelectedItem();
					Vertex last = (Vertex) myEndCities.getSelectedItem();
					if (last != null) {
						myCityDistance.setText("Distance = " 
								+ ((ArrayImplementation) myAlgorithm)
								.getCorrespondingNode(last).getDistance());
						myPath.setText(myAlgorithm.getPath(start, last));
					} 
					myAlgorithm.runAlgorithm(start);
				}
			}});
		myEndCities.setBackground(Color.WHITE);
		myEndCities.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg) {
				//TODO if you aren't using dijkstraheapnodes, modify
				if(myHeapButton.isSelected()) {
					DijkstraHeapNode node = ((MinHeapImplementation) myAlgorithm)
							.getCorrespondingNode((Vertex) myEndCities.getSelectedItem());
					myCityDistance.setText(DISTANCE + node.getDistance());
					myPath.setText(myAlgorithm.getPath(
							(Vertex) myStartCities.getSelectedItem(), 
							(Vertex) myEndCities.getSelectedItem()));
				} else {
					DijkstraHeapNode node = ((ArrayImplementation) myAlgorithm)
							.getCorrespondingNode((Vertex) myEndCities.getSelectedItem());
					myCityDistance.setText(DISTANCE + node.getDistance());
					myPath.setText(myAlgorithm.getPath(
							(Vertex) myStartCities.getSelectedItem(), 
							(Vertex) myEndCities.getSelectedItem()));
				}
			}
			
		});
		myFrame.setVisible(true);
	}
	
	/**
	 * Main method. Runs the program and all tests.
	 * 
	 * @param args command line arguments to be ignored
	 */
	public static void main(String... args) {
		BinaryHeap b = new BinaryHeap();
		Driver d = new Driver();

	}
}	
