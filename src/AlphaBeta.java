import java.util.List;

public class AlphaBeta {

    private Environment env;
    private Heuristics heuristics;

    public AlphaBeta(Environment e, Heuristics h){
        env = e;
        heuristics = h;
    }

    public Action alphaBetaRoot(int depth, State s, float alpha, float beta){
        float bestValue = Float.NEGATIVE_INFINITY;
        Action bestAction  = null;

        List<Action> actions = env.get_legal_actions(s);
        for(Action a : actions){
            s.doAction(a, true); // do action
            float value = -alphaBeta(depth-1,s, -beta, -alpha);
            s.doAction(a,false); // undo action
            if(value > bestValue){
                bestValue = value;
                bestAction = a;
            }
        }
        return bestAction;
    }

    public float alphaBeta (int depth, State s, float alpha, float beta){
        System.out.println("depth" + depth);
        if(env.is_terminal_state(s) || depth <= 0){
            return heuristics.eval(s);
        }
        float bestValue = Float.NEGATIVE_INFINITY;

        List<Action> actions = env.get_legal_actions(s);
        for(Action a : actions){
            s.doAction(a, true); // do action
            System.out.println("depth " + depth + " Alpha "+ alpha +" beta " + beta + " state "+ s);
            float value = -alphaBeta(depth-1, s, -beta, -alpha);
            s.doAction(a,false); // undo action
            bestValue = Math.max(value, bestValue);
            if(bestValue > alpha){
                alpha = bestValue;
                if(alpha >=beta){
                    System.out.println("did pruning");
                    break;
                }

            }
        }
        return bestValue;
    }
}
