import java.util.*;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    private double[][] pic;

    private Picture picture;

    private EnergyFunction f;

    @Override
    public List<Integer> findSeam(Picture picture, EnergyFunction f) {

        this.picture = picture;
        this.f = f;

        pic = new double[picture.width()][picture.height()];

        List<Integer> result = new ArrayList<>();

        // fill out left-most column
        for (int i = 0; i < picture.height(); i++) {
            pic[0][i] = f.apply(picture, 0, i);
        }

        // fill out the rest of it
        fillPic();

        // find the seam and put it in the result
        createSeam(result);

        // reverse for the correct path
        Collections.reverse(result);


        return result;
    }

    private void createSeam(List<Integer> result) {
        int start = -1;
        double low = Double.POSITIVE_INFINITY;
        for (int y = 0; y < picture.height(); y++) {
            if (pic[picture.width() - 1][y] < low) {
                low = pic[picture.width() - 1][y];
                start = y;
            }
        }
        result.add(start);

        for (int x = picture.width() - 1; x > 0; x--) {
            double left = pic[x - 1][start];
            double leftUp = Double.POSITIVE_INFINITY;
            double leftDown = Double.POSITIVE_INFINITY;

            Map<Double, Integer> m = new HashMap<>();

            m.put(left, start);

            if (start > 0) {
                leftUp = pic[x - 1][start - 1];
                m.put(leftUp, start - 1);
            }

            if (start < picture.height() - 1) {
                leftDown = pic[x - 1][start + 1];
                m.put(leftDown, start + 1);
            }

            start = m.get(findMin(left, leftUp, leftDown));
            result.add(start);

        }


    }

    private void fillPic() {
        for (int x = 1; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {

                double left = pic[x - 1][y];
                double leftUp = Double.POSITIVE_INFINITY;
                double leftDown = Double.POSITIVE_INFINITY;

                if (y > 0) {
                    leftUp = pic[x - 1][y - 1];
                }
                if (y < picture.height() - 1) {
                    leftDown = pic[x - 1][y + 1];
                }

                double add = findMin(left, leftUp, leftDown);
                pic[x][y] = add + f.apply(picture, x, y);

            }
        }
    }

    private double findMin(double one, double two, double three) {
        double result = Double.POSITIVE_INFINITY;
        if (one < result) {
            result = one;
        }
        if (two < result) {
            result = two;
        }
        if (three < result) {
            result = three;
        }
        return result;
    }

}
