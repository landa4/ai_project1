import java.nio.charset.CoderResult;
import java.util.HashSet;

public class EnvironmentTest {

    public static void main(String arg[]){
        int width = 3;
        int height = 5;

        //Environment env = new Environment(5,6); // init like starting a new game

        HashSet<Coordinate> white_pawns = new HashSet<>();
        white_pawns.add(new Coordinate(1,2));
        white_pawns.add(new Coordinate(2,2));

        HashSet<Coordinate> black_pawns = new HashSet<>();
        black_pawns.add(new Coordinate(1,3));
        black_pawns.add(new Coordinate(2,3));

        State s = new State(white_pawns, black_pawns, true);
        Environment env = new Environment(5,6, s);// init like start in a state situation

        System.out.println(env.getCurrentState());

        //Action a = new Action(new Coordinate(1,2), new Coordinate(1,1), true);
        //System.out.println(env.is_legal_action_for_pawn(env.getCurrentState(), a, true));
        //System.out.println(env.get_next_State(env.getCurrentState(), a));
    }


}
