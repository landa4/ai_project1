import java.nio.charset.CoderResult;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class EnvironmentTest {

    static Environment env;

    public static void main(String arg[]){
        int width = 3;
        int height = 5;

        final boolean white_turns = false;
        State.setMax(width-1, height-1);
        Environment.max_X = width - 1;
        Environment.max_Y = height - 1;
//        Environment env = new Environment(width,height); // init like starting a new game

        HashSet<Coordinate> white_pawns = new HashSet<>();
        white_pawns.add(new Coordinate(1,3));


        HashSet<Coordinate> black_pawns = new HashSet<>();
        black_pawns.add(new Coordinate(1,2));
        black_pawns.add(new Coordinate(1,4));


        State s = new State(white_pawns, black_pawns, white_turns);
        env = new Environment(width,height, s);// init like start in a state situation

        System.out.println("aktueller Status:");
        System.out.println(env.getCurrentState());
        System.out.println();

        List<Action> actions =  env.get_legal_actions(env.getCurrentState());

        System.out.println("mögliche Aktionen:");
        for(Action a : actions){
            System.out.println(a);
        }
        Action nextAction = null;
        int max_depth = 10;
        for(int i = 0; i < max_depth; i++){
            nextAction = miniMaxRoot(i, env.currentState);
        }
        System.out.println();

        if(nextAction != null){
            System.out.println("bester Zug angewand");
            System.out.println(env.get_next_State(env.getCurrentState(), nextAction));
            System.out.println();

            System.out.println("war letzter Zug:" + env.is_terminal_action(env.getCurrentState(), actions.get(0)));
            System.out.println("neuer Spielstatus:" + env.is_winning_move(env.getCurrentState(), actions.get(0)));
        }
    }

    public static Action miniMaxRoot(int depth, State s){
        float bestValue = Float.NEGATIVE_INFINITY;
        Action bestAction  = null;

        for(Action a : env.get_legal_actions(s)){
            State successor = env.get_next_State(s,a);
            float value = -miniMax(depth-1,successor);
            if(value > bestValue){
                bestValue = value;
                bestAction = a;
            }
        }
        return bestAction;
    }

    public static float miniMax (int depth, State s){
        if(env.is_terminal_state(s) || depth <= 0){
            return env.evaluate(s);
        }
        float bestValue = Float.NEGATIVE_INFINITY;

        for(Action a : env.get_legal_actions(s)){
            State successor = env.get_next_State(s,a);
            float value = -miniMax(depth-1, successor);
            bestValue = Math.max(value, bestValue);
        }
        return bestValue;
    }
}
