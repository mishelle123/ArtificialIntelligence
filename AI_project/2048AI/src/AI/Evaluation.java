package AI;

import game2048.util2048;

public class Evaluation {

	private double cornerW = 0;
	private double snakeW = 0;

	private double diffW = 0;
	private double scoreW = 0;
	private double emptyW = 0;
	private double maxW = 0;
	
	/**
	 * put the weights for the different heuristics.
	 * @param cornerW
	 * @param snakeW
	 * @param diffW
	 * @param scoreW
	 * @param emptyW
	 * @param maxW
	 */
	public Evaluation(double cornerW, double snakeW, double diffW, double scoreW, double emptyW, double maxW){
		this.cornerW = cornerW;
		this.snakeW = snakeW;
		this.diffW = diffW;
		this.scoreW = scoreW;
		this.emptyW = emptyW;
		this.maxW = maxW;

	}
	/**
	 * 
	 * @param board
	 * @param score
	 * @param boardSize
	 * @return an evaluation of the board .
	 */
	public double eval(int[] board, int[] score, int boardSize){

		if(util2048.isGameOver(board, boardSize)){
			return -100000;
		}
		double corner = (cornerW==0) ? 0 : util2048.getCornerValue(board, boardSize);
		double snake = (snakeW==0) ? 0 : util2048.getSnakeValue(board, boardSize);
		double diff = (diffW==0) ? 0 : util2048.getDiffernceValue(board, boardSize);
		double sc = (scoreW==0) ? 0 : score[0]/(double)4;
		double empty = (emptyW==0) ? 0 : 100 * util2048.getEmptyCells(board).length;
		double max = (maxW==0) ? 0 : util2048.getMax(board)[1];


//				System.out.println("score: " + sc );
//				System.out.println("diff: " + diff );
//				System.out.println("corner: " + corner );
//				System.out.println("empty: " + empty );
//				System.out.println("max: " + max );
//				System.out.println("snake: " + snake );

		double eval = corner*cornerW + snake*snakeW + diff*diffW + sc*scoreW + empty*emptyW + max*maxW ; 
		//		System.out.println("eval: " + eval );

		return eval;

	}
	////////////////////////////////////////////for the evaluation////////////////////////////
	public void printWeights(){
		System.out.print(this.cornerW+"\t");
		System.out.print(this.snakeW+"\t");
		System.out.print(this.diffW+"\t");
		System.out.print(this.scoreW+"\t");
		System.out.print(this.emptyW+"\t");
		System.out.print(this.maxW);
	}
}
