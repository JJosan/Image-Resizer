import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Program {

    private static final String OUTPUT_PATH = "C:\\Users\\jason\\Desktop\\java things\\result.png";

    private static final String INPUT_PATH = "C:\\Users\\jason\\Desktop\\java things\\doorvr.png";

    private static int height;

    private static int width;

    public static void main(String[] args) throws IOException {
        File f = new File(INPUT_PATH);
        Picture pic = new Picture(f);
        SeamCarver seamCarver = new SeamCarver(pic, new DualGradientEnergyFunction(), new DynamicProgrammingSeamFinder());
        Scanner sc = new Scanner(System.in);


        System.out.println("Image WIDTH: " + pic.width());
        System.out.println("Image HEIGHT : " + pic.height());
        System.out.println();
        System.out.println("new WIDTH: ");
        width = sc.nextInt();
        System.out.println("new HEIGHT: ");
        height = sc.nextInt();


        Horizontal(pic, seamCarver);
        Vertical(pic, seamCarver);


        seamCarver.picture().save(new File(OUTPUT_PATH));
    }

    private static void Horizontal(Picture pic, SeamCarver s) {
        if (pic.width() < width) {
            throw new IndexOutOfBoundsException("Given width must be less than image width");
        }
        int finalWidth = pic.width() - width;
        for (int i = 0; i < finalWidth; i++) {
            s.removeVertical();
        }
    }

    private static void Vertical(Picture pic, SeamCarver s) {
        if (pic.height() < height) {
            throw new IndexOutOfBoundsException("Given height must be less than image height");
        }
        int finalWidth = pic.height() - height;
        for (int i = 0; i < finalWidth; i++) {
            s.removeHorizontal();
        }
    }

}
