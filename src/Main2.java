
public class Main2 {

    /**
     * starts the game player and waits for messages from the game master <br>
     * Command line options: [port]
     */
    public static void main(String[] args){
        try{
            // TODO: put in your agent here
            Agent agent = new Bob(new SimpleHeuristics());

            int port=4002;
            if(args.length>=1){
                port=Integer.parseInt(args[0]);
            }
            GamePlayer gp = new GamePlayer(port, agent);
            gp.waitForExit();
        }catch(Exception ex){
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
