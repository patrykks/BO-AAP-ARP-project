import alg.model.Aco;
import generators.ConfigReader;

/**
 * Created by patrykks on 28/03/16.
 */
public class Main {
    private static final int NUMBER_OF_ANTS = ConfigReader.getInstance().getNumberOfAnts();

    public static void main(String [] args) throws InterruptedException {
        Aco aco = new Aco(NUMBER_OF_ANTS);
        aco.solve();
    }
}
