package AI;

import game2048.util2048;

import java.util.List;

public class AlphaBetaAgent implements Agent{

	private int boardSize; 
	private Evaluation e;
	
	
	//////////////////for the evaluation///////////////////////////////
	public int expended = 0;
	
	/**
	 * 
	 * @param boardSize
	 * @param e - the evaluation the agent tries to maximize
	 */
	public AlphaBetaAgent(int boardSize, Evaluation e) {
		this.boardSize = boardSize;
		this.e = e;
	}

	@Override
	public String[] getActions(int[] board, int[] score) {
		
		// TODO Auto-generated method stub
		
		int depth = 2;
//		int numOfEmptyTiles = util2048.getEmptyCells(board).length;
//		if (numOfEmptyTiles <= 4){
//			depth = 4;
//		}
		List<int[][]> successors = util2048.getPlayerSuccessors(board, boardSize, score);
		String[] action = {util2048.UP};
		double v = Double.NEGATIVE_INFINITY;
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;


		for (int[][] s: successors){
			double eval = minPlayer(s[BOARD], s[SCORE], depth, alpha, beta);
			if (eval > v){
				v = eval;
				action[0] = util2048.INT_TO_ACTION.get(s[ACTION][0]);
			}
			if (v >= beta){
				return action;
			}
		}
		return action;
	}

	private double maxPlayer(int[] board, int[] score, int depth, double alpha, double beta){
		expended++;
		if(util2048.isGameOver(board, boardSize) || (depth == 0)){
			return e.eval(board, score, boardSize);
		}

		double v = Double.NEGATIVE_INFINITY;

		List<int[][]> succesors = util2048.getPlayerSuccessors(board, boardSize, score);
		for (int[][] s: succesors){
			v = Math.max(v, minPlayer(s[BOARD], s[SCORE], depth, alpha, beta));
			if (v >= beta){
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;

	}
	private double minPlayer(int[] board, int[] score, int depth, double alpha, double beta){
		expended++;
		if(util2048.isGameOver(board, boardSize)){
			return e.eval(board, score, boardSize);
		}

		double v = Double.POSITIVE_INFINITY;

		List<int[][]> succesors = util2048.getComputerSuccessors(board, score);

		if (succesors.size() == 0){
			return maxPlayer(board, score, depth - 1, alpha, beta);
		}
		for (int[][] s: succesors){
			v = Math.min(v, maxPlayer(s[BOARD], s[SCORE], depth - 1, alpha, beta));
			if (v <= alpha){
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;

	}
	//////////////////////////////////////for the evaluation//////////////////////////////////////

	@Override
	public void printStats() {
		// TODO Auto-generated method stub
		System.out.print(expended+"\t");
		e.printWeights();
	}
}
