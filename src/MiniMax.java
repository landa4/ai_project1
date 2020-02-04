import java.util.List;

public class MiniMax {

    private Environment env;
    private Heuristics heuristics;

    public MiniMax(Environment e, Heuristics h){
        env = e;
        heuristics = h;
    }

    public Action miniMaxRoot(int depth, State s){
        float bestValue = Float.NEGATIVE_INFINITY;
        Action bestAction  = null;

        List<Action> actions = env.get_legal_actions(s);
        for(Action a : actions){
            s.doAction(a, true); // do action
            float value = -miniMax(depth-1,s);
            s.doAction(a,false); // undo action
            if(value > bestValue){
                bestValue = value;
                bestAction = a;
            }
        }
        return bestAction;
    }

    public float miniMax (int depth, State s){
        if(env.is_terminal_state(s) || depth <= 0){
            return heuristics.eval(s);
        }
        float bestValue = Float.NEGATIVE_INFINITY;

        List<Action> actions = env.get_legal_actions(s);
        for(Action a : actions){
            s.doAction(a, true); // do action
            float value = -miniMax(depth-1, s);
            s.doAction(a,false); // undo action
            bestValue = Math.max(value, bestValue);
        }
        return bestValue;
    }
}
