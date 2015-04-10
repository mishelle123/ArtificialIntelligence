import game2048.Game2048;
import AI.Agent;
import AI.AlphaBetaAgent;
import AI.Evaluation;
import AI.ExpectimaxAgent;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int j=0;j<100;j++){
			Evaluation eval = new Evaluation(0,30,50,0,0,0);//max
			Agent a = new ExpectimaxAgent(4, eval);
			Game2048.run(4, a, false);
		}
//		for(int j=0;j<100;j++){
//			Evaluation eval = new Evaluation(0,30,0,20,0,0);//max
//			Agent a = new ExpectimaxAgent(4, eval);
//			Game2048.run(4, a, false);
//		}
//		for(int j=0;j<100;j++){
//			Evaluation eval = new Evaluation(0,30,0,30,0,0);//max
//			Agent a = new ExpectimaxAgent(4, eval);
//			Game2048.run(4, a, false);
//		}
//		for(int j=0;j<100;j++){
//			Evaluation eval = new Evaluation(0,30,0,40,0,0);//max
//			Agent a = new ExpectimaxAgent(4, eval);
//			Game2048.run(4, a, false);
//		}
//		for(int j=0;j<100;j++){
//			Evaluation eval = new Evaluation(0,30,0,50,0,0);//max
//			Agent a = new ExpectimaxAgent(4, eval);
//			Game2048.run(4, a, false);
//		}


	}

}
