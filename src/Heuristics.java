public interface Heuristics {

	/**
	 * inform the heuristics about the environment, needs to be called before the first call to eval()
	 * @param env the environment
	 */
	void init(Environment env);

	/**
	 * return an estimate of the remaining cost of reaching a goal state from state s
	 * @param s the state
	 * @return the calculated value
	 */
	int eval(State s);

}