import java.util.List;

public class AlphaBeta {

    private Environment env;
    private Heuristics heuristics;
    Action best_action_at_all  = null;

    private final float time_threshold = -25f; // in ms
    private double start;
    private double time_to_play;
    private int state_expansions;
    private int depth;
    private double start_time; // just for this iteration

    public AlphaBeta(Environment e, Heuristics h){
        env = e;
        heuristics = h;
    }

    public Action alphaBetaRoot(int depth, State s, float alpha, float beta,double start, double play_clock) throws OutOfTimeException{
        time_to_play = play_clock;
        start_time = System.currentTimeMillis();
        this.depth = depth;
        this.start = start;
        state_expansions = 0;
        float bestValue = Float.NEGATIVE_INFINITY;
        Action bestAction  = null;

        List<Action> actions = env.get_legal_actions(s);
        for(Action a : actions){
            s.doAction(a, true); // do action
            float value = -alphaBeta(depth-1,s, -beta, -alpha);
            s.doAction(a,false); // undo action
            state_expansions++;
            if(value > bestValue){
                bestValue = value;
                bestAction = a;
            }
        }
        best_action_at_all = bestAction;
        return bestAction;
    }

    public float alphaBeta (int depth, State s, float alpha, float beta)throws OutOfTimeException{
        if(System.currentTimeMillis() - start > time_to_play + time_threshold){
            throw new OutOfTimeException();
        }
        if(env.is_terminal_state(s) || depth <= 0){
            return heuristics.eval(s);
        }
        float bestValue = Float.NEGATIVE_INFINITY;

        List<Action> actions = env.get_legal_actions(s);
        for(Action a : actions){
            s.doAction(a, true); // do action
            float value = -alphaBeta(depth-1, s, -beta, -alpha);
            s.doAction(a,false); // undo action
            state_expansions++;
            bestValue = Math.max(value, bestValue);
            if(bestValue > alpha){
                alpha = bestValue;
                if(alpha >=beta){
                    break;
                }

            }
        }
        return bestValue;
    }

    public void print_result_of_iteration(){
        System.out.println("depth limit: " + depth);
        System.out.println("State expansions: " + state_expansions);
        System.out.println("Iteration take: " + (System.currentTimeMillis() - start_time));
    }
    public int get_state_expansions_for_iteration(){
        return state_expansions;
    }

    public Action getBestAction() {
        return best_action_at_all;
    }
}
