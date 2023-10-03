import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//a chromosome
public class Route {

    private  List<City> route;
    private  double distance;

    public double getDistance() {
        return this.distance;
    }

    private Route( List<City> route) {
        this.route = route;
        this.distance = calculateDistance();
    }

    static Route create( City[] points) {
        List<City> route = Arrays.asList(Arrays.copyOf(points, points.length));
        Collections.shuffle(route);
        return new Route(route);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(City city : this.route) {
            builder.append(city.toString()).append((" : "));
        }
        return builder.toString();
    }

    List<City> getRoute() {
        return this.route;
    }

    double calculateDistance() {
        double total = 0.0f;
        for(int i = 0; i < this.route.size() - 1; i++) {
            total += this.route.get(i).distance(this.route.get(i+1));
        }
        return total;
    }

    Route[] crossOver(Route otherRoute) {

         List<City>[] firstDNA = GeneticUtils.split(this.route);
         List<City>[] secondDNA = GeneticUtils.split(otherRoute.getRoute());

         List<City> firstCrossOver = new ArrayList<>(firstDNA[0]);
         List<City> secondCrossOver = new ArrayList<>(secondDNA[1]);

        for (List<City> routes: secondDNA) {
            for (City city: routes) {
                if(!firstCrossOver.contains(city)) {
                    firstCrossOver.add(city);
                }
            }
        }

        for (List<City> routes: firstDNA) {
            for (City city: routes) {
                if(!secondCrossOver.contains(city)) {
                    secondCrossOver.add(city);
                }
            }
        }
/*
        for(City gene : secondDNA[0]) {
            if(!firstCrossOver.contains(gene)) {
                firstCrossOver.add(gene);
            }
        }

        for(City gene : secondDNA[1]) {
            if(!firstCrossOver.contains(gene)) {
                firstCrossOver.add(gene);
            }
        }



        for(City gene : firstDNA[0]) {
            if(!secondCrossOver.contains(gene)) {
                secondCrossOver.add(gene);
            }
        }

        for(City gene : firstDNA[1]) {
            if(!secondCrossOver.contains(gene)) {
                secondCrossOver.add(gene);
            }
        }

 */

        if(firstCrossOver.size() != GeneticUtils.cities.length ||
           secondCrossOver.size() != GeneticUtils.cities.length) {
            throw new RuntimeException("CrossOver over or under flow check size of list!");
        }

        return new Route[] {
                new Route(firstCrossOver),
                new Route(secondCrossOver)
        };
    }

    Route mutate() {
        final List<City> copy = new ArrayList<>(this.route);
        int indexA = GeneticUtils.randomIndex(copy.size());
        int indexB = GeneticUtils.randomIndex(copy.size());
        while(indexA == indexB) {
            indexA = GeneticUtils.randomIndex(copy.size());
            indexB = GeneticUtils.randomIndex(copy.size());
        }
        Collections.swap(copy, indexA, indexB);
        return new Route(copy);
    }


}
