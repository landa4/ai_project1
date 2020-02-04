import java.util.HashSet;
import java.util.List;

public class EnvironmentTest {

    Environment env;
    Heuristics heuristics;
    MiniMax miniMax;

    public static void main(String[] arg){
        new EnvironmentTest();
    }
    public EnvironmentTest(){
        int width = 3;
        int height = 5;

        final boolean white_turns = false;
        State.setMax(width-1, height-1);
        Environment.max_X = width - 1;
        Environment.max_Y = height - 1;
//        Environment env = new Environment(width,height); // init like starting a new game

        HashSet<Coordinate> white_pawns = new HashSet<>();
        white_pawns.add(new Coordinate(2,3));

        HashSet<Coordinate> black_pawns = new HashSet<>();
        black_pawns.add(new Coordinate(1,1));
        black_pawns.add(new Coordinate(1,4));


        State s = new State(white_pawns, black_pawns, white_turns);
        env = new Environment(width,height, s);// init like start in a state situation
        heuristics = new SimpleHeuristics();
        heuristics.init(env);
        miniMax = new MiniMax(env, heuristics);

        System.out.println("aktueller Status:");
        System.out.println(env.getCurrentState());
        System.out.println();

        List<Action> actions =  env.get_legal_actions(env.getCurrentState());

        System.out.println("mögliche Aktionen:");
        for(Action a : actions){
            System.out.println(a);
        }
        Action nextAction = null;
        int max_depth = 3;
        for(int i = 0; i < max_depth; i++){
            nextAction = miniMax.miniMaxRoot(i, env.currentState);
        }
        System.out.println();
        if(nextAction != null){
            System.out.println("bester Zug angewand");
            System.out.println(nextAction);
            System.out.println(env.get_next_State(env.getCurrentState(), nextAction));
            System.out.println();

            System.out.println("war letzter Zug:" + env.is_terminal_action(env.getCurrentState(), actions.get(0)));
            System.out.println("neuer Spielstatus:" + env.is_winning_move(env.getCurrentState(), actions.get(0)));
        }
    }
}
