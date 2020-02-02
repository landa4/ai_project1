import java.nio.charset.CoderResult;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class EnvironmentTest {

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
        Environment env = new Environment(width,height, s);// init like start in a state situation

        System.out.println("aktueller Status:");
        System.out.println(env.getCurrentState());
        System.out.println();

        List<Action> actions =  env.get_legal_actions(env.getCurrentState(), white_turns);

        System.out.println("mögliche Aktionen:");
        for(Action a : actions){
            System.out.println(a);
        }
        System.out.println();

        if(actions.size() > 0){
            System.out.println("erster Zug angewand");
            System.out.println(env.get_next_State(env.getCurrentState(), actions.get(0)));
            System.out.println();

            System.out.println("war letzter Zug:" + env.is_terminal_action(env.getCurrentState(), actions.get(0)));
            System.out.println("neuer Spielstatus:" + env.is_winning_move(env.getCurrentState(), actions.get(0)));
        }


    }
}
