package AI;

public interface Agent {
	final int BOARD = 0;
	final int SCORE = 1;
	final int ACTION = 2;
	
	/**
	 * 
	 * @param board
	 * @param score
	 * @return an array of actions('left', 'right', 'up', 'down')
	 */
	public String[] getActions(int[] board, int[] score);
	//////////////////////////////////////for the evaluation//////////////////////////////////////
	public void printStats();
}
