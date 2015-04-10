package AI;
import java.util.List;

import game2048.*;
public class ExpectimaxAgent implements Agent{
	
	private int boardSize; 
	private Evaluation e;
	//////////////////for the evaluation///////////////////////////////
	public int expended = 0;
	/**
	 * 
	 * @param boardSize
	 * @param e - the evaluation the agent tries to maximize
	 */
	public ExpectimaxAgent(int boardSize, Evaluation e){
		this.boardSize = boardSize;
		this.e = e;
	}
	@Override
	public String[] getActions(int[] board, int[] score) {
		// TODO Auto-generated method stub
		
		int depth = 2;
//		int numOfEmptyTiles = util2048.getEmptyCells(board).length;
//		if (numOfEmptyTiles <= 6){
//			depth = 3;
//		}
		
		List<int[][]> succesors = util2048.getPlayerSuccessors(board, boardSize, score);
		String[] action = {util2048.UP};
		double v = Double.NEGATIVE_INFINITY;
		for (int[][] s: succesors){
			double eval = expectationPlayer(s[BOARD], s[SCORE], depth);
			if (eval > v){
				v = eval;
				action[0] = util2048.INT_TO_ACTION.get(s[ACTION][0]);
			}
		}
		return action;
	}
	
	private double maxPlayer(int[] board, int[] score, int depth){
		expended++;
		if(util2048.isGameOver(board, boardSize) || (depth == 0)){
			return e.eval(board, score, boardSize);
		}
		List<int[][]> succesors = util2048.getPlayerSuccessors(board, boardSize, score);
		
		double v = Double.NEGATIVE_INFINITY;
		for (int[][] s: succesors){
			double eval = expectationPlayer(s[BOARD], s[SCORE], depth);
			if (eval > v){
				v = eval;
			} 
		}
		return v;
		
	}
	private double expectationPlayer(int[] board, int[] score, int depth){
		expended++;
		if(util2048.isGameOver(board, boardSize)){
			return e.eval(board, score, boardSize);
		}
		List<int[][]> succesors = util2048.getComputerSuccessors(board, score);

		float expectation = 0;
		for (int[][] s: succesors){
			double eval = maxPlayer(s[BOARD], s[SCORE], depth - 1);
			if (s[ACTION][0] == 2){
				expectation += 0.9 * eval;

			}else {
				expectation += 0.1 * eval;

			}
		}
		return (expectation / (double)util2048.getEmptyCells(board).length) ;
		
	}
	//////////////////////////////////////for the evaluation//////////////////////////////////////

	@Override
	public void printStats() {
		// TODO Auto-generated method stub
		System.out.print(expended+"\t");
		e.printWeights();
	}
	
	
	
	

}
