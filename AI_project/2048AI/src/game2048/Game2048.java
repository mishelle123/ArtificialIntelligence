package game2048;

/*
 * Copyright 1998-2014 Konstantin Bulenkov http://bulenkov.com/about
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import javax.swing.*;

import AI.Agent;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Arrays;

/**
 * @author Konstantin Bulenkov
 */
public class Game2048 extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private static final Color BG_COLOR = new Color(0xbbada0);
	private static final String FONT_NAME = "Arial";
	private static final int TILE_SIZE = 100;
	private static final int TILES_MARGIN = 16;
	private int movesCounter = 0;
	private int boardSize;
	private Tile[] myTiles;
	private int[] board;
	boolean myLose = false;
	boolean agentActive = false;
	int[] myScore = {0};
	
	/**
	 * create a new game 
	 * @param boardSize
	 */
	private Game2048(int boardSize) {
		this.boardSize = boardSize;
		setFocusable(true);
		resetGame();

	}



	/**
	 * reset the game.
	 */
	public void resetGame() {
		myScore[0] = 0;
		movesCounter = 0;
		myLose = false;
		myTiles = new Tile[boardSize * boardSize];
		board = new int[boardSize * boardSize];
		Arrays.fill(board, 0);
		for (int i = 0; i < myTiles.length; i++) {
			myTiles[i] = new Tile();
		}
		addTile();
		addTile();
		repaint();
	}

	private void updateBoard(){
		for(int i=0; i < myTiles.length; i++){
			myTiles[i].value = board[i];
		}
	}

	private void doMove(String move){
		//		util2048.printMatrix(board, boardSize, myScore);
		if (!myLose) {
			if (util2048.doMove(board, boardSize, myScore, move)){
				movesCounter++;
				updateBoard();
				addTile();
			}


		}
		if (util2048.isGameOver(board, boardSize)) {
			

			myLose = true;
		}
		//		util2048.printMatrix(board, boardSize, myScore);
		repaint();
	}



	private void addTile() {
		int[] available = util2048.getEmptyCells(board);
		if (available.length != 0) {
			int index = (int) (Math.random() * available.length) % available.length;
			int x = available[index] % boardSize;
			int y = available[index] / boardSize;
			int value = Math.random() < 0.9 ? 2 : 4;
			myTiles[x + y * boardSize].value = value;
			board[x + y * boardSize] = value;
		}
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				drawTile(g, myTiles[x + y * boardSize], x, y);
			}
		}
		if (myLose) {
			g.setColor(new Color(255, 255, 255,100));
			g.fillRect(0, 0, getWidth(), getHeight());

		}
		g.setFont(new Font(FONT_NAME, Font.PLAIN, 7*boardSize));
		g.setColor(new Color(255,255,255));
		g.drawString("Score: " + myScore[0], 40*boardSize, 125 * boardSize);

		g.setFont(new Font(FONT_NAME, Font.PLAIN, 5*boardSize));
		g.setColor(new Color(0, 0, 0, 100));
		g.drawString("Press ESC to restart the game", 5, getHeight() - 5);

	}

	private void drawTile(Graphics g2, Tile tile, int x, int y) {
		Graphics2D g = ((Graphics2D) g2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		int value = tile.value;
		int xOffset = offsetCoors(x);
		int yOffset = offsetCoors(y);
		g.setColor(tile.getBackground());
		g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
		g.setColor(tile.getForeground());
		final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
		final Font font = new Font(FONT_NAME, Font.BOLD, size);
		g.setFont(font);

		String s = String.valueOf(value);
		final FontMetrics fm = getFontMetrics(font);

		final int w = fm.stringWidth(s);
		final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

		if (value != 0)
			g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);



	}

	private static int offsetCoors(int arg) {
		return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
	}



	static class Tile {
		int value;

		public Tile() {
			this(0);
		}

		public Tile(int num) {
			value = num;
		}

		public boolean isEmpty() {
			return value == 0;
		}

		public Color getForeground() {
			return value < 16 ? new Color(0x776e65) :  new Color(0xf9f6f2);
		}

		public Color getBackground() {
			switch (value) {
			case 2:    return new Color(0xeee4da);
			case 4:    return new Color(0xede0c8);
			case 8:    return new Color(0xf2b179);
			case 16:   return new Color(0xf59563);
			case 32:   return new Color(0xf67c5f);
			case 64:   return new Color(0xf65e3b);
			case 128:  return new Color(0xedcf72);
			case 256:  return new Color(0xedcc61);
			case 512:  return new Color(0xedc850);
			case 1024: return new Color(0xedc53f);
			case 2048: return new Color(0xedc22e);
			}
			return new Color(0xcdc1b4);
		}
	}

	/*
	 * a method that set the controls of the game
	 * if agent is null the is controlled by the keyboard
	 * else the game is controlled by the agent.
	 */
	private static void setControler(final Game2048 game, final Agent agent){
		if (agent != null){
			game.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						game.agentActive = false;
						game.resetGame();
						game.agentActive = true;
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								askAgent(game, agent);
							}
						});
					}
				}
			});
			game.agentActive = true;
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					askAgent(game, agent);
				}
			});
		} else {
			game.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						game.resetGame();
					}

					switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						game.doMove(util2048.LEFT);
						break;
					case KeyEvent.VK_RIGHT:
						game.doMove(util2048.RIGHT);
						break;
					case KeyEvent.VK_DOWN:
						game.doMove(util2048.DOWN);
						break;
					case KeyEvent.VK_UP:
						game.doMove(util2048.UP);
						break;
					}


				}
			});
		}
	}
	/*
	 * a method that ask the agent for the next set of moves
	 * and perform those moves.
	 * 
	 */
	private static void askAgent(final Game2048 game2048, final Agent agent){
		int[] score = {game2048.myScore[0]};
		int[] board = util2048.getBoardCopy(game2048.board);
		String[] actions = agent.getActions(util2048.getBoardCopy(board), score);
		for (final String a: actions){
			if (!game2048.myLose && game2048.agentActive){
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {

						// TODO Auto-generated method stub
						game2048.doMove(a);
					}
				});
			}else{
				/////////////////////////////print for evaluation///////////////////////////////
				game2048.printStats();
				agent.printStats();
				System.out.println();
				return;
			}
		}
		
		//check if the game is not over and ask the agent again
		if (!game2048.myLose && game2048.agentActive){

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					askAgent(game2048, agent);
				}
			});

		}else{
			System.out.println("here2");

			return;
		}

	}
	//////////////////////////////for the evaluation///////////////////////////////////////
	public void printStats(){
		System.out.print(movesCounter+"\t");
		System.out.print(myScore[0]+"\t");
		System.out.print(util2048.getMax(board)[1]+"\t");
		
	}
	/**
	 * this method runs a new game of 2048
	 * @param boardsize
	 * @param agent - the agent to ask moves from. if null controlled by the player(keyboard)
	 * @param visable
	 */
	public static void run(int boardsize, Agent agent, boolean visable) {
		JFrame game = new JFrame();
		game.setTitle("2048");
		//create a new game
		final Game2048 g = new Game2048(boardsize);
		game.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		if(visable){
			game.addWindowListener( new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					JFrame frame = (JFrame)e.getSource();

					int result = JOptionPane.showConfirmDialog(
							frame,
							"Are you sure you want to exit the game?",
							"Exit Game",
							JOptionPane.YES_NO_OPTION);

					if (result == JOptionPane.YES_OPTION){
						g.agentActive = false;
						frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					}
				}
			});
		}
		game.setSize(122 * boardsize, 145 * boardsize);
		game.setResizable(false);
		game.add(g);
		game.setLocationRelativeTo(null);
		game.setVisible(visable);
		//set the cotroller
		setControler(g, agent);



	}
}



