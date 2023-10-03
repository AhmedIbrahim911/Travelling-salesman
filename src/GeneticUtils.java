import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GeneticUtils {

    private static Random R = new Random(10000);

    static City[] cities = generateData(100);

    private static City[] generateData(int numDataPoints) {
        City[] data = new City[numDataPoints];
        for(int i = 0; i < numDataPoints; i++) {
            data[i] = new City(GeneticUtils.randomIndex(WorldMap.WIDTH),
                                  GeneticUtils.randomIndex(WorldMap.HEIGHT));
        }
        return data;
    }

    static int randomIndex(int limit) {
        return R.nextInt(limit);
    }

    @SuppressWarnings("unchecked")
    static<T> List<T>[] split(List<T> list) {
        List<T> firstPart = new ArrayList<>();
        List<T> secondPart = new ArrayList<>();
        int size = list.size();
        int partitionIndex = 1 + GeneticUtils.randomIndex(list.size());
        IntStream.range(0, size).forEach(i -> {
            if(i < (size+1)/partitionIndex) {
                firstPart.add(list.get(i));
            } else {
                secondPart.add(list.get(i));
            }
        });
        return (List<T>[]) new List[] {firstPart, secondPart};
    }

}
