import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {

    private List<Route> population;
    private final int initialSize;

    Population( City[] points,
                int initialSize) {
        this.population = init(points, initialSize);
        this.initialSize = initialSize;
    }

    List<Route> getPopulation() {
        return this.population;
    }

    Route getBestSolution() {
        return this.population.get(0);
    }

    private List<Route> init( City[] points,  int initialSize) {
        List<Route> ground = new ArrayList<>();
        for(int i = 0; i < initialSize; i++) {
            Route route = Route.create(points);
            ground.add(route);
        }
        return ground;
    }

    void update() {
        doCrossOver();
        doMutation();
        doSpawn();
        doSelection();
    }

    private void doSelection() {
        this.population.sort(Comparator.comparingDouble(Route::getDistance));
        this.population = this.population.stream().limit(this.initialSize).collect(Collectors.toList());
    }

    private void doSpawn() {
        IntStream.range(0, 1000).forEach(e -> this.population.add(Route.create(GeneticUtils.cities)));
    }

    private void doMutation() {
        final List<Route> newPopulation = new ArrayList<>();
        for(int i = 0; i < this.population.size()/10; i++) {
            final Route mutation = this.population.get(GeneticUtils.randomIndex(this.population.size())).mutate();
            newPopulation.add(mutation);
        }
        this.population.addAll(newPopulation);
    }

    private void doCrossOver() {

        final List<Route> newPopulation = new ArrayList<>();
        for(final Route route : this.population) {
            final Route crossRoute = getCrossOverRoute(route);
            newPopulation.addAll(Arrays.asList(route.crossOver(crossRoute)));
        }
        this.population.addAll(newPopulation);
    }

    private Route getCrossOverRoute(final Route route) {
        Route crossRoute = this.population.get(GeneticUtils.randomIndex(this.population.size()));
        while(route == crossRoute) {
            crossRoute = this.population.get(GeneticUtils.randomIndex(this.population.size()));
        }
        return crossRoute;
    }


}
