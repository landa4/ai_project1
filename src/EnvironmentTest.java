import java.nio.charset.CoderResult;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class EnvironmentTest {

    public static void main(String arg[]){
        int width = 3;
        int height = 5;
    //lalal flos comment
        final boolean white_turns = true;
        State.setMax(width-1, height-1);
        Environment.max_X = width - 1;
        Environment.max_Y = height - 1;
        //Environment env = new Environment(5,6); // init like starting a new game

        HashSet<Coordinate> white_pawns = new HashSet<>();
        white_pawns.add(new Coordinate(1,2));
        white_pawns.add(new Coordinate(2,2));

        HashSet<Coordinate> black_pawns = new HashSet<>();
        black_pawns.add(new Coordinate(1,3));
        black_pawns.add(new Coordinate(2,3));

        State s = new State(white_pawns, black_pawns, white_turns);
        Environment env = new Environment(5,6, s);// init like start in a state situation

        System.out.println("aktueller Status:");
        System.out.println(env.getCurrentState());
        System.out.println();

        List<Action> actions =  env.get_legal_actions(env.getCurrentState(),white_turns);

        System.out.println("mögliche Aktionen:");
        for(Action a : actions){
            System.out.println(a);
        }
        System.out.println();

        System.out.println("erster Zug angewand");
        System.out.println(env.get_next_State(env.getCurrentState(), actions.get(0)));
        System.out.println();

        System.out.println("war letzter Zug:" + env.is_terminal_action(env.getCurrentState(), actions.get(0)));
        System.out.println("gewinnender Zug:" + env.is_winning_move(env.getCurrentState(), actions.get(0)));

        //Action a = new Action(new Coordinate(1,2), new Coordinate(1,1), true);
        //System.out.println(env.is_legal_action_for_pawn(env.getCurrentState(), a, true));
        //System.out.println(env.get_next_State(env.getCurrentState(), a));
    }


}
