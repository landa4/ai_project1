import java.util.List;
import java.util.Random;

public class Bob implements Agent {

    private Random random = new Random();

    private String role; // the name of this agent's role (white or black)
    private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
    private boolean myTurn; // whether it is this agent's turn or not
    private int width, height; // dimensions of the board
    private boolean our_role; // true if bob is white false

    private Environment env;
    private Heuristics heuristics;

    public Bob(Heuristics h){
        heuristics = h;
    }
    /*
        init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "white" or "black" and playclock is the number of seconds after which nextAction must return.
    */
    public void init(String role, int width, int height, int playclock) {
        this.role = role;
        this.playclock = playclock;
        myTurn = !role.equals("white");
        this.width = width;
        this.height = height;
        // TODO: add your own initialization code here
        env = new Environment(width,height);
        heuristics.init(env);
        our_role = role.equals("white");

        System.out.println("role " + our_role);
    }

    // lastMove is null the first time nextAction gets called (in the initial state)
    // otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last player did
    public String nextAction(int[] lastMove) {
        boolean was_turn;
        if (lastMove != null) {
            int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
            String roleOfLastPlayer;
            if (myTurn && role.equals("white") || !myTurn && role.equals("black")) {
                roleOfLastPlayer = "white";
                was_turn = true;
            } else {
                roleOfLastPlayer = "black";
                was_turn = false;
            }
            System.out.println(roleOfLastPlayer + " moved from " + x1 + "," + y1 + " to " + x2 + "," + y2);
            // TODO: 1. update your internal world model according to the action that was just executed

            env.doAction(new Action(new Coordinate(x1 - 1,y1 - 1), new Coordinate(x2 - 1, y2 - 1), was_turn));
            System.out.println(env.getCurrentState());

        }

        // update turn (above that line it myTurn is still for the previous state)
        myTurn = !myTurn;
        if (myTurn) {
            // TODO: 2. run alpha-beta search to determine the best move

            List<Action> possible_moves = env.get_legal_actions(env.currentState);

            if(possible_moves.isEmpty())
                System.err.println("No more moves possible");

            Action a = possible_moves.get(random.nextInt(possible_moves.size()));

            return "(move " + (a.get_From().getX() + 1) + " " + (a.get_From().getY() +1) + " " + (a.get_to().getX() +1) + " " + (a.get_to().getY() +1)+ ")";
        } else {
            return "noop";
        }
    }

    // is called when the game is over or the match is aborted
    @Override
    public void cleanup() {
        // TODO: cleanup so that the agent is ready for the next match
        env = null;
    }
}
