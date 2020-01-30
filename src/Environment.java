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
        this.max_X = width - 1;
        this.max_Y = height - 1;

        currentState = new State(max_X, max_Y);
    }
    public Environment(int width, int height, State state){
        this.max_X = width - 1;
        this.max_Y = height - 1;

        State.setMax(max_X, max_Y);
        currentState = state;
    }
    /**
     * calculates the next state for a given state s and action a
     * @param s the state to evaluate
     * @param a the action which is performend on state s
     * @return the result of action a performend on state s
     */
    public State get_next_State(State s, Action a){
        //Todo optimize: use state s and don´t create a new one
        if(a.is_w_action()){
            HashSet pawns =  s.get_W_pawns();
            pawns.remove(a.get_From());
            pawns.add(a.get_to());
            return new State(pawns, s.get_B_pawns(), false);
        }else{
            HashSet pawns =  s.get_B_pawns();
            pawns.remove(a.get_From());
            pawns.add(a.get_to());
            return new State(s.get_W_pawns(), pawns, true);
        }
    }

    /**
     * get all legal action for a state s when white or black is nex
     * @param state the state
     * @param w_action if true => white's turn, if false => black's turn
     * @return null, if no move is possible
     */
    public List<Action> get_legal_actions(State state, boolean w_action) {
        LinkedList<Action> actions = new LinkedList<>();
        int direction;
        if(w_action){
            direction = 1;
        }else{
            direction = -1;
        }
        for(Coordinate c : state.get_W_pawns()) {
            //action going forward
            Action current_action = new Action(c, Moves.STEP, w_action);
            if (is_legal_action_for_pawn(state, current_action, true)) {
                actions.add(current_action);
            }
            //acton take right
            current_action = new Action(c, Moves.TAKE_RIGTH, w_action);
            if (is_legal_action_for_pawn(state, current_action, true)) {
                actions.add(current_action);
            }
            //action take left
            current_action = new Action(c,Moves.TAKE_LEFT, w_action);
            if (is_legal_action_for_pawn(state, current_action, true)) {
                actions.add(current_action);
            }
        }
        if(!actions.isEmpty())
            return actions;
        else
            return null;
    }

    /**
     * calculates if action a is possible in State s
     * @param s the state
     * @param a the action
     * @param white true => white, false => black
     * @return
     */
    public boolean is_legal_action_for_pawn(State s, Action a, boolean white){ // todo change to private only public for testing
        return is_legal_action_for_pawn(a.get_From(), s, a.get_to(), white);
    }
    /**
     * calculates if pawn can move to destination
     * @param pawn the position of the white pawn
     * @param s the state (where the other pawns are), State s has to contain pawn otherwise false
     * @param destination of the move of the pawn
     * @param white color of the figure pawn
     * @return
     */
    private boolean is_legal_action_for_pawn(Coordinate pawn, State s, Coordinate destination, boolean white){
        int direction; // 1 for white, -1 for black
        HashSet friendly_pawns; // have the same color;
        HashSet enemy_pawns; // have the other color
        if(white){
            direction = 1;
            friendly_pawns = s.get_W_pawns();
            enemy_pawns = s.get_B_pawns();
        }else{
            direction = -1;
            friendly_pawns = s.get_B_pawns();
            enemy_pawns = s.get_W_pawns();
        }
        if(friendly_pawns.contains(pawn)){
            if(s.coordinate_is_free(destination)){
                if(new Action(pawn, Moves.STEP,white).get_to() == destination){
                    return true; // just go there
                }
            }else{
                if(enemy_pawns.contains(destination)){
                    if(new Action(pawn, Moves.TAKE_LEFT,white).get_to() == destination ||new Action(pawn, Moves.TAKE_RIGTH,white).get_to() == destination){
                        return true; // can take other pawn
                    }
                }
            }
        }else{
            System.err.println("Pawn position is not in State");
        }
        return false;
    }

    public boolean is_terminal_action(State s, Action a){
        StateStatus status = is_winning_move(s,a);
        return status == StateStatus.WHITE_WINS || status == StateStatus.BLACK_WINS || status == StateStatus.DRAW;
    }

    public StateStatus is_winning_move(State s, Action a){
        State state = get_next_State(s,a);

        if(a.is_w_action()){
            if(get_legal_actions(s,!a.is_w_action()) == null){
                return StateStatus.WHITE_WINS; // Todo Ask if black can not make a move => draw or win?
            }else{
                for (Coordinate c: state.get_W_pawns()){
                    if(c.getY() == max_Y){ // white wins
                        return StateStatus.WHITE_WINS;
                    }
                }
            }

        }else{
            if(get_legal_actions(s,!a.is_w_action()) == null){ // Todo ask if white can not make a move => draw or win?
                return StateStatus.BLACK_WINS;
            }else {
                for (Coordinate c : state.get_B_pawns()) {
                    if (c.getY() == 0) { // black win wins
                        return StateStatus.BLACK_WINS;
                    }
                }
            }
        }
        return StateStatus.PLAY;
    }

    public int evaluate(){
        //Todo heuristic stuff
        return 0;
    }

    public void doAction(Action a) {
        currentState = get_next_State(currentState, a);
    }

    public State getCurrentState() {
        return currentState;
    }
}
