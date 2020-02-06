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
    private AlphaBeta alphaBeta;

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
        alphaBeta = new AlphaBeta(env, heuristics);
        our_role = role.equals("white");

        System.out.println("role " + our_role);
    }

    // lastMove is null the first time nextAction gets called (in the initial state)
    // otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last player did
    public String nextAction(int[] lastMove) {
        double start = System.currentTimeMillis();
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

            State perform_state = new State(env.currentState);
            Action nextAction;
            int total_state_expansion = 0;
            int i = 0;
            try {
                for(;; i++){
                    alphaBeta.alphaBetaRoot(i,perform_state, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, start,playclock * 1000f);
                    //alphaBeta.print_result_of_iteration();
                    total_state_expansion += alphaBeta.get_state_expansions_for_iteration();
                }
            }catch (OutOfTimeException e){
                nextAction = alphaBeta.getBestAction();
                System.out.println("------------------------------------");
                System.out.println("max depth limit: " + i);
                System.out.println("State expansions: " + total_state_expansion);
                System.out.println("Iteration take: " + (System.currentTimeMillis()-start));
                System.out.println("------------------------------------");
            }

            return "(move " + (nextAction.get_From().getX() + 1) + " " + (nextAction.get_From().getY() +1) + " " + (nextAction.get_to().getX() +1) + " " + (nextAction.get_to().getY() +1)+ ")";
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
