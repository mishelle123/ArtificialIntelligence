
import game2048.Game2048;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import AI.Agent;
import AI.AlphaBetaAgent;
import AI.Evaluation;
import AI.ExpectimaxAgent;


public class Application  {
	private static final int SLIDER_WIDTH = 110; 
	private static final int SLIDER_START = 35; 
	private static final int SLIDER_LABEL_HIGHT = 160; 
	private static final int SLIDER_STRING_HIGHT = 130;
	private static final int SLIDER_STRING_START = 10;

	private static final int SLIDER_HIGHT = 180; 


	private static List<JSlider> addEvaluationElements(JFrame manager){
		ArrayList<JSlider> l = new ArrayList<>();
		final JLabel EvalLabel = new JLabel("Choose weights for the Evaluation function:");
		manager.add(EvalLabel);
		Dimension size = EvalLabel.getPreferredSize();
		EvalLabel.setBounds(20, 100, size.width, size.height);

		final JSlider cornerW = new JSlider(SwingConstants.VERTICAL, 0, 50, 0);
		l.add(cornerW);
		final JLabel cornerL = new JLabel(String.valueOf(cornerW.getValue())); 
		
		final JSlider snakeW = new JSlider(SwingConstants.VERTICAL, 0, 50, 0);
		l.add(snakeW);
		final JLabel snakeL = new JLabel(String.valueOf(snakeW.getValue()));
		
		final JSlider diffW = new JSlider(SwingConstants.VERTICAL, 0, 50, 0);
		l.add(diffW);
		final JLabel diffL = new JLabel(String.valueOf(diffW.getValue()));
		
		final JSlider scoreW = new JSlider(SwingConstants.VERTICAL, 0, 50, 0);
		l.add(scoreW);
		final JLabel scoreL = new JLabel(String.valueOf(scoreW.getValue()));
		
		final JSlider emptyW = new JSlider(SwingConstants.VERTICAL, 0, 50, 0);
		l.add(emptyW);
		final JLabel emptyL = new JLabel(String.valueOf(emptyW.getValue()));  
		
		final JSlider maxW = new JSlider(SwingConstants.VERTICAL, 0, 50, 0);
		l.add(maxW);
		final JLabel maxL = new JLabel(String.valueOf(maxW.getValue())); 
		
		
		
		cornerW.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				cornerL.setText(String.valueOf(cornerW.getValue()));
			}
		});
		snakeW.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				snakeL.setText(String.valueOf(snakeW.getValue()));
			}
		});
		diffW.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				diffL.setText(String.valueOf(diffW.getValue()));
			}
		});
		scoreW.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				scoreL.setText(String.valueOf(scoreW.getValue()));
			}
		});
		emptyW.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				emptyL.setText(String.valueOf(emptyW.getValue()));
			}
		});
		maxW.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				maxL.setText(String.valueOf(maxW.getValue()));
			}
		});
		
		JLabel cornerString = new JLabel("Corner:");
		manager.add(cornerString);
		size = cornerString.getPreferredSize();
		cornerString.setBounds(SLIDER_STRING_START, SLIDER_STRING_HIGHT, size.width, size.height);
		manager.add(cornerL);
		size = cornerL.getPreferredSize();
		cornerL.setBounds(SLIDER_START, SLIDER_LABEL_HIGHT, size.width + 20, size.height);
		manager.add(cornerW);
		size = cornerW.getPreferredSize();
		cornerW.setBounds(SLIDER_START, SLIDER_HIGHT, size.width, size.height);
		
		JLabel snakeString = new JLabel("Snake:");
		manager.add(snakeString);
		size = snakeString.getPreferredSize();
		snakeString.setBounds(SLIDER_STRING_START+SLIDER_WIDTH, SLIDER_STRING_HIGHT, size.width, size.height);
		manager.add(snakeL);
		size = snakeL.getPreferredSize();
		snakeL.setBounds(SLIDER_START+SLIDER_WIDTH, SLIDER_LABEL_HIGHT, size.width + 20, size.height);
		manager.add(snakeW);
		size = snakeW.getPreferredSize();
		snakeW.setBounds(SLIDER_START+SLIDER_WIDTH, SLIDER_HIGHT, size.width, size.height);
		
		JLabel diffString = new JLabel("Difference:");
		manager.add(diffString);
		size = diffString.getPreferredSize();
		diffString.setBounds(SLIDER_STRING_START+2*SLIDER_WIDTH, SLIDER_STRING_HIGHT, size.width, size.height);
		manager.add(diffL);
		size = diffL.getPreferredSize();
		diffL.setBounds(SLIDER_START+2*SLIDER_WIDTH, SLIDER_LABEL_HIGHT, size.width + 20, size.height);
		manager.add(diffW);
		size = diffW.getPreferredSize();
		diffW.setBounds(SLIDER_START+2*SLIDER_WIDTH, SLIDER_HIGHT, size.width, size.height);
		
		JLabel scoreString = new JLabel("Score:");
		manager.add(scoreString);
		size = scoreString.getPreferredSize();
		scoreString.setBounds(SLIDER_STRING_START+3*SLIDER_WIDTH, SLIDER_STRING_HIGHT, size.width, size.height);
		manager.add(scoreL);
		size = scoreL.getPreferredSize();
		scoreL.setBounds(SLIDER_START+3*SLIDER_WIDTH, SLIDER_LABEL_HIGHT, size.width + 20, size.height);
		manager.add(scoreW);
		size = scoreW.getPreferredSize();
		scoreW.setBounds(SLIDER_START+3*SLIDER_WIDTH, SLIDER_HIGHT, size.width, size.height);
		
		JLabel emptyString = new JLabel("Empty space:");
		manager.add(emptyString);
		size = emptyString.getPreferredSize();
		emptyString.setBounds(SLIDER_STRING_START+4*SLIDER_WIDTH, SLIDER_STRING_HIGHT, size.width, size.height);
		manager.add(emptyL);
		size = emptyL.getPreferredSize();
		emptyL.setBounds(SLIDER_START+4*SLIDER_WIDTH, SLIDER_LABEL_HIGHT, size.width + 20, size.height);
		manager.add(emptyW);
		size = emptyW.getPreferredSize();
		emptyW.setBounds(SLIDER_START+4*SLIDER_WIDTH, SLIDER_HIGHT, size.width, size.height);
		
		JLabel maxString = new JLabel("Max tile:");
		manager.add(maxString);
		size = maxString.getPreferredSize();
		maxString.setBounds(SLIDER_STRING_START+5*SLIDER_WIDTH, SLIDER_STRING_HIGHT, size.width, size.height);
		manager.add(maxL);
		size = maxL.getPreferredSize();
		maxL.setBounds(SLIDER_START+5*SLIDER_WIDTH, SLIDER_LABEL_HIGHT, size.width + 20, size.height);
		manager.add(maxW);
		size = maxW.getPreferredSize();
		maxW.setBounds(SLIDER_START+5*SLIDER_WIDTH, SLIDER_HIGHT, size.width, size.height);
		
		
		return l;
	}

	private static void addElements(JFrame manager){

		JLabel cooseBoardSize = new JLabel("Choose board size:");
		Dimension size = cooseBoardSize.getPreferredSize();
		manager.add(cooseBoardSize);

		cooseBoardSize.setBounds(20, 5, size.width, size.height);
		Integer[] boardSizes = new Integer[]{2,3,4,5};
		final JComboBox<Integer> boardSize = new JComboBox<Integer>(boardSizes); 
		boardSize.setSelectedItem(4);
		size = boardSize.getPreferredSize();
		manager.add(boardSize);
		boardSize.setBounds(170, 5, size.width, size.height);

		final List<JSlider> evalWeights = addEvaluationElements(manager);


		JLabel chooseAgent = new JLabel("Choose Agent:");
		manager.add(chooseAgent);
		size = chooseAgent.getPreferredSize();
		chooseAgent.setBounds(20, 400, size.width, size.height);


		String[] agents = new String[]{"Expectimax agent", "AlphaBeta agent", "Play yourself agent"};
		final JComboBox<String> agent = new JComboBox<>(agents);
		agent.setSelectedItem("Expectimax agent");
		manager.add(agent);
		size = agent.getPreferredSize();
		agent.setBounds(150, 400, size.width, size.height);



		JButton runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int bSize = (int) boardSize.getSelectedItem();
				Evaluation eval = new Evaluation(evalWeights.get(0).getValue(),//corner Weight
												evalWeights.get(1).getValue(), //snake Weight
												evalWeights.get(2).getValue(), //diff Weight
												evalWeights.get(3).getValue(), //score Weight
												evalWeights.get(4).getValue(), //empty Weight
												evalWeights.get(5).getValue());//max
				Agent a = null;
				String agentCoosen = (String) agent.getSelectedItem();
				switch (agentCoosen) {
				case "Expectimax agent":
					a = new ExpectimaxAgent(bSize, eval);
					break;
				case "AlphaBeta agent":
					a = new AlphaBetaAgent(bSize, eval);
					break;
				case "Play yourself agent":
					a = null;
					break;
				default:
					break;
				}

				
				Game2048.run(bSize, a, true);
								

			}
		});
		
		manager.add(runButton);
		size = runButton.getPreferredSize();
		runButton.setBounds(220, 500, 200, 100);

	}

	public static void main(String[] args){


		JFrame manager = new JFrame();
		manager.setTitle("2048 Solver");

		manager.setSize(650, 650);
		manager.setResizable(false);
		manager.setLayout(null);
		addElements(manager);
		manager.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		manager.setLocationRelativeTo(null);
		manager.setVisible(true);

	}



}
