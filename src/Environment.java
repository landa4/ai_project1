import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Environment {

    protected static int max_X, max_Y; // zero based
    protected State currentState;

    /**
     * creates the initial environment
     * @param width it is NOT null based
     * @param height is is NOT null based
     */
    public Environment(int width, int height){
        max_X = width - 1;
        max_Y = height - 1;

        currentState = new State(max_X, max_Y);
    }
    public Environment(int width, int height, State state){
        max_X = width - 1;
        max_Y = height - 1;
        currentState = state;
    }

    /**
     * CREATES the next state for a given state s and action a
     * @param s the state to evaluate
     * @param a the action which is performend on state s
     * @return the result of action a performend on state s
     */
    @SuppressWarnings("use insted s.doAction() and s.undoAction() for performance")
    public State get_next_State(State s, Action a){
        State newState = new State(s);
        newState.doAction(a, true);
        return newState;
    }

    /**
     * get all legal action for a state s when white or black is next
     * @param state the state
     * @return empty list, if no move is possible
     */
    public List<Action> get_legal_actions(State state) {
        LinkedList<Action> actions = new LinkedList<>();
        HashSet<Coordinate> current_pawns;

        if(state.isW_turn())
            current_pawns = state.get_W_pawns();
        else
            current_pawns = state.get_B_pawns();

        for(Coordinate c : current_pawns) { // for every pawn
            for (Moves move : Moves.values()) { // check every move
                Action current_action = new Action(c, move, state.is_W_turn());
                if (is_legal_action_for_pawn(state, current_action)) {
                    actions.add(current_action);
                }
            }
        }
        return actions;
    }

    /**
     * calculates if action a is possible in State s
     * @param s the state
     * @param a the action
     * @return if action is possible => true else false
     */
    private boolean is_legal_action_for_pawn(State s, Action a){
        return is_legal_action_for_pawn(a.get_From(), s, a.get_to(), a.is_w_action());
    }
    /**
     * calculates if pawn can move to destination an check is the destination is reachable
     * @param pawn the position of the white pawn
     * @param s the state (where the other pawns are), State s has to contain pawn otherwise false
     * @param destination of the move of the pawn
     * @param white color of the figure pawn
     * @return if action is possible => true else false
     */
    private boolean is_legal_action_for_pawn(Coordinate pawn, State s, Coordinate destination, boolean white){
        if(destination.get_is_legal()){
            HashSet<Coordinate> friendly_pawns; // have the same color;
            HashSet<Coordinate> enemy_pawns; // have the other color
            if(white){
                friendly_pawns = s.get_W_pawns();
                enemy_pawns = s.get_B_pawns();
            }else{
                friendly_pawns = s.get_B_pawns();
                enemy_pawns = s.get_W_pawns();
            }
            if(friendly_pawns.contains(pawn)){
                if(s.coordinate_is_free(destination)){
                    Action step = new Action(pawn, Moves.STEP,white);
                    return step.get_to().equals(destination); // just go there
                }else{
                    if(enemy_pawns.contains(destination)){
                        Action take_left = new Action(pawn, Moves.TAKE_LEFT,white);
                        Action take_right = new Action(pawn, Moves.TAKE_RIGHT,white);
                        return take_left.get_to().equals(destination) || take_right.get_to().equals(destination); // can take other pawn
                    }
                }
            }else{
                System.err.println("Pawn position is not in State");
            }
        }
        return false;
    }

    public boolean is_terminal_action(State s, Action a){
        StateStatus status = is_winning_move(s,a);
        return status == StateStatus.WHITE_WINS || status == StateStatus.BLACK_WINS || status == StateStatus.DRAW;
    }

    /**
     * return if action a is a winning move from status s
     * @param s state
     * @param a action
     * @return the StateStatus
     */
    public StateStatus is_winning_move(State s, Action a){
        State state = get_next_State(s,a);
        return is_winning_state(state);
    }

    public StateStatus is_winning_state(State state){
        for (Coordinate c: state.get_W_pawns()){
            if(c.getY() == max_Y){ // white wins
                return StateStatus.WHITE_WINS;
            }
        }
        for (Coordinate c : state.get_B_pawns()) {
            if (c.getY() == 0) { // black win wins
                return StateStatus.BLACK_WINS;
            }
        }
        if(get_legal_actions(state).isEmpty())
            return StateStatus.DRAW;

        return StateStatus.PLAY;
    }
    public boolean is_terminal_state(State s){
        StateStatus status = is_winning_state(s);
        return status == StateStatus.WHITE_WINS || status == StateStatus.BLACK_WINS || status == StateStatus.DRAW;
    }

    public void doAction(Action a) {
        currentState = get_next_State(currentState, a);
    }

    public State getCurrentState() {
        return currentState;
    }
}
