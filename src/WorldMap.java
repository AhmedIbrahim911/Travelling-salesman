import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

public class WorldMap extends JPanel {

      Population population;
      AtomicInteger generation;

    static final int WIDTH = 1280;
    static final int HEIGHT = 720;
    static final long startTime = System.currentTimeMillis();

    private WorldMap() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        this.population = new Population(GeneticUtils.cities, 9000);
        this.generation = new AtomicInteger(0);
        Timer timer = new Timer(5, (ActionEvent e) -> {
            this.population.update();
            repaint();
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.RED);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString("gen : " +this.generation.incrementAndGet(), 350, 15);
        g.drawString("population size : " +this.population.getPopulation().size(), 150, 15);
        g.drawString("shortest path : "
                +String.format("%.2f", this.population.getBestSolution().getDistance()), 500, 15);
        g.drawString("time : " + (System.currentTimeMillis() - startTime)/ 1000 + " S", 1, 15);
        drawBestRoute(g);
    }

    private void drawBestRoute(Graphics2D g) {
        List<City> route = this.population.getBestSolution().getRoute();
        g.setColor(Color.BLACK);
        for(int i = 0; i < route.size() - 1; i++) {
            City city = route.get(i);
            City neighbor = route.get(i + 1);
            g.drawLine(city.getX(), city.getY(), neighbor.getX(), neighbor.getY());
        }
        g.setColor(Color.GREEN);
        for(City city : route) {
            g.fillOval(city.getX(), city.getY(), 5, 5);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("Travelling salesman");
            frame.setResizable(false);
            frame.add(new WorldMap(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }


}
