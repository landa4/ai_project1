
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class AlphaBetaTables {

    private Environment env;
    private Heuristics heuristics;
    private Hashtable<Integer,TableNode> nodes;
    Action best_action_at_all  = null;

    private final float time_threshold = -25f; // in ms
    private double start;
    private double time_to_play;
    private int state_expansions;
    private int depth;
    private double start_time; // just for this iteration

    public AlphaBetaTables(Environment e, Heuristics h){
        env = e;
        heuristics = h;
    }

    public Action alphaBetaRoot(int depth, State s, float alpha, float beta,double start, double play_clock) throws OutOfTimeException{
        time_to_play = play_clock;
        start_time = System.currentTimeMillis();
        this.depth = depth;
        this.start = start;

        //if(depth == 0)
        nodes = new Hashtable<>();

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
        // System.out.println(bestValue);
        best_action_at_all = bestAction;
        return bestAction;
    }

    public float alphaBeta (int depth, State s, float alpha, float beta)throws OutOfTimeException{
        if(System.currentTimeMillis() - start > time_to_play + time_threshold){
            throw new OutOfTimeException();
        }

        float alphaOrig = alpha;
        TableNode ttEntry = nodes.get(s.hashCode());
        if(ttEntry != null && ttEntry.getDepth() >= depth){
            if(ttEntry.getFlag() == NodeFlag.EXACT)
                return ttEntry.getValue();
            else  if(ttEntry.getFlag() == NodeFlag.LOWERBOUND)
                alpha = Math.max(alpha, ttEntry.getValue());
            else if(ttEntry.getFlag() == NodeFlag.UPPERBOUND)
                beta = Math.min(beta, ttEntry.getValue());

            if(alpha >=beta)
                return ttEntry.getValue();
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
        NodeFlag flag;
        if(bestValue <=alphaOrig)
            flag = NodeFlag.UPPERBOUND;
        else if(bestValue >= beta)
            flag = NodeFlag.LOWERBOUND;
        else
            flag = NodeFlag.EXACT;
        nodes.put(s.hashCode(), new TableNode(bestValue, flag, depth));

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
