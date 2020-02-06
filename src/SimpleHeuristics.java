
public class SimpleHeuristics implements Heuristics {
	/**
	 * reference to the environment to be able to figure out positions of obstacles
	 */
	private Environment env;

	public void init(Environment env) {
		this.env = env;
	}

	@Override
	public int eval(State s) {
		StateStatus status = env.is_winning_state(s);
		int view;
		if(s.isW_turn())
			view = 1;
		else
			view = -1;

		switch (status){
			case WHITE_WINS:
				return 100 * view;
			case BLACK_WINS:
				return -100 * view;
			case DRAW:
				return 0;
			case PLAY:
				int distance_black = Integer.MAX_VALUE, distance_white = Integer.MAX_VALUE;

				for (Coordinate c: s.get_W_pawns()){
					if(Environment.max_Y - c.getY() < distance_white){ // white wins
						distance_white = Environment.max_Y - c.getY();
					}
				}
				for (Coordinate c : s.get_B_pawns()) {
					if (c.getY() < distance_black) { // black win wins
						distance_black = c.getY();
					}
				}
				return (distance_black - distance_white) * view;
		}
		System.out.println("Error  in evaluate function with state: " + s);
		return -404; // Error should not occur
	}

}