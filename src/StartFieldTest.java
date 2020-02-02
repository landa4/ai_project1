public class StartFieldTest {


    public static void main(String [] args){
        for(int x = 2; x <= 10; x++){
            for(int y = 4; y <= 10; y++){
                System.out.println(new Environment(x,y).getCurrentState());
                System.out.println();
            }
            System.out.println();
        }
    }
}
