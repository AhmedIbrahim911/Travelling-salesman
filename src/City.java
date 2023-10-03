import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
//a gene
public class City {

    private final int x;
    private final int y;

    City( int x,
          int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y+ ")";
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    //distance between any two points calculated by square root of ((x1-x2)^2+(y1-y2)^2)
    double distance(City neighborCity) {
        return sqrt(pow(getX() - neighborCity.getX(), 2) + pow(getY() - neighborCity.getY(), 2));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return this.x == city.x &&
                this.y == city.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
