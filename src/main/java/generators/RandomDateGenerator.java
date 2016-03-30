package generators;

import java.util.Date;

public class RandomDateGenerator {

    private static final long beginTime = ConfigReader.getInstance().getBeginTime();
    private static final long endTime = ConfigReader.getInstance().getEndTime();

    private static long getRandomTimeBetweenTwoDates () {
        long diff = endTime - beginTime + 1;
        return beginTime + (long) (Math.random() * diff);
    }

    public static Date generateRandomDate() {
        return new Date(getRandomTimeBetweenTwoDates());
    }

}