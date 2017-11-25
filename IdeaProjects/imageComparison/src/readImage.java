import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.*;

public class readImage extends JFrame{
    static BufferedImage screen = null;
    static BufferedImage screen2 = null;
    private JLabel label1 = new JLabel("File1:");
    private JLabel label2 = new JLabel("File2:");
    private JLabel label3 = new JLabel("");
    private JButton b1 = new JButton("Add file 1");
    private JButton b2 = new JButton("Add file 2");
    private JButton b3 = new JButton("Сompare files");
    private JButton b4 = new JButton("Save");

    private JFileChooser fileopen1 = new JFileChooser();
    private JFileChooser fileopen2 = new JFileChooser();

    private static String pathFile1;
    private String pathFile2;

    private String filesCompare;
    public readImage(String name) {
        super(name);
        setLayout(new GridLayout(7,1));
        label1.setSize(20,100);
        label2.setSize(20,100);
        label3.setSize(20,100);

        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (arg0.getSource() == b1) {
                    int ret = fileopen1.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file1 = fileopen1.getSelectedFile();
                        pathFile1 = file1.getAbsolutePath();
                        label1.setText("File1:"+pathFile1);
                    }
                }
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (arg0.getSource() == b2) {
                    int ret = fileopen2.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file2 = fileopen2.getSelectedFile();
                        pathFile2 = file2.getAbsolutePath();
                        label2.setText("File2:"+pathFile2);

                    }
                }
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (arg0.getSource() == b3) {

                    compareFiles();
                }
            }
        });

        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (arg0.getSource() == b4) {
                    JFileChooser fc = new JFileChooser();
                    if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        try {
                            File fileStream = new File(String.valueOf(fc.getSelectedFile()));
                            ImageIO.write(screen2, "png", fileStream);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    // output image
//                    File outputfile = new File("/home/krab/result.png");
//                    try {
//                        ImageIO.write(screen2, "png", outputfile);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });
        b1.setPreferredSize(new Dimension(100,30));
        b1.setBackground(Color.decode("#FFD700"));
        b2.setPreferredSize(new Dimension(100,30));
        b2.setBackground(Color.decode("#FFD700"));
        b3.setPreferredSize(new Dimension(150,30));
        b3.setBackground(Color.GREEN);
        b4.setPreferredSize(new Dimension(130,30));
        b4.setBackground(Color.CYAN);
        add(b1);
        add(label1);
        add(b2);
        add(label2);
        add(b3);
        add(label3);
        add(b4);
        setVisible(true);
    }

    public static void main(String[] args) {
//        Color myWhite = new Color(100, 255, 100); // Color white
//        int rgb = myWhite.getRGB();

        new readImage("Compare images");
    }

    public void compareFiles() {
        if (pathFile1 == null || pathFile2 == null) {
            label3.setForeground(Color.RED);
            label3.setText("You don't select the file");
        } else {
            try {
                screen = ImageIO.read(new File(pathFile1));
            } catch (IOException e) {

            }

            int w = screen.getWidth();
            int h = screen.getHeight();

            int[][] screenArray = new int[w][h];

            for (int i = 0; i < w; i++)
                for (int j = 0; j < h; j++)
                    screenArray[i][j] = screen.getRGB(i, j);


            try {
                screen2 = ImageIO.read(new File(pathFile2));
            } catch (IOException e) {
            }

            int w2 = screen2.getWidth();
            int h2 = screen2.getHeight();

            int[][] screenArray2 = new int[w2][h2];

            for (int i = 0; i < w2; i++)
                for (int j = 0; j < h2; j++)
                    screenArray2[i][j] = screen2.getRGB(i, j);

            if (w == w2 && h == h2) {
                label3.setForeground(Color.GREEN);
                if (Arrays.deepEquals(screenArray, screenArray2)) {
                    filesCompare = "Files equals";
                } else filesCompare = "Files differents";

                screen.getGraphics().dispose();
                screen2.getGraphics().dispose();
//-----------------------------------------------------
                if (!Arrays.deepEquals(screenArray, screenArray2)) {
                    int count = 0;
                    for (int i = 0; i < w; i++) {
                        for (int j = 0; j < h; j++) {
                            if (screenArray[i][j] != screenArray2[i][j]) {
                                count++;
//                    screen.getGraphics().setColor(Color.red);
//                    screen.getGraphics().drawOval(i, j, 5, 5);
//                    screen.setRGB(i, j, rgb);
                            }
                        }
                    }

                    int[] listPoligX = new int[count];
                    int[] listPoligY = new int[count];
                    int countX = 0;
                    int countY = 0;
                    for (int i = 0; i < w; i++) {
                        for (int j = 0; j < h; j++) {
                            if (screenArray[i][j] != screenArray2[i][j]) {
                                listPoligX[countX++] = i;
                                listPoligY[countY++] = j;
//                    screen.getGraphics().setColor(Color.red);
//                    screen.getGraphics().drawOval(i, j, 5, 5);
//                    screen.setRGB(i, j, rgb);
                            }
                        }
                    }
                    Graphics g = screen2.getGraphics();
//        Rectangle rectangle = new Rectangle(listPoligX[0],listPoligY[0],20,40);
//        if (rectangle.contains(listPoligX[3],listPoligY[3])) {
//            g.setColor(Color.RED);
//            g.drawRect(listPoligX[0],listPoligY[0],20,40);
//        }

                    ArrayList<Integer> listNearX = new ArrayList<>();
                    ArrayList<Integer> listNearY = new ArrayList<>();
                    ArrayList<Integer> listNearBigX = new ArrayList<>();
                    ArrayList<Integer> listNearBigY = new ArrayList<>();


                    for (int i = 0; i < listPoligX.length - 1; i++) {
                        if ((Math.sqrt(Math.pow(listPoligX[i + 1] - listPoligX[i], 2) + Math.pow(listPoligY[i + 1] - listPoligY[i], 2))) < 50) {
                            listNearX.add(listPoligX[i]);
                            listNearY.add(listPoligY[i]);
                        } else break;
                    }


                    g.setColor(Color.RED);
//        g.drawLine(listNearX.get(0),listNearY.get(0),listNearX.get(217),listNearY.get(217));

                    int tmpMaxX = 0;
                    for (int i = 0; i < listNearX.size(); i++) {

                        if (tmpMaxX < listNearX.get(i)) {
                            tmpMaxX = listNearX.get(i);
                        }
                    }
                    int tmpMaxY = 0;
                    for (int i = 0; i < listNearY.size(); i++) {

                        if (tmpMaxY < listNearY.get(i)) {
                            tmpMaxY = listNearY.get(i);
                        }
                    }

                    int tmpMinX = listNearX.get(0);
                    int tmpIndexMinX = 0;
                    for (int i = 0; i < listNearX.size(); i++) {

                        if (tmpMinX > listNearX.get(i)) {
                            tmpMinX = listNearX.get(i);
                            tmpIndexMinX = i;
                        }
                    }
                    int tmpMinY = listNearY.get(0);
                    int tmpIndexMinY = 0;
                    for (int i = 0; i < listNearY.size(); i++) {

                        if (tmpMinY > listNearY.get(i)) {
                            tmpMinY = listNearY.get(i);
                            tmpIndexMinY = i;
                        }
                    }


                    g.drawRect(listNearX.get(tmpIndexMinX), listNearY.get(tmpIndexMinY),
                            (int) Math.sqrt(Math.pow(tmpMaxX - listPoligX[tmpIndexMinX], 2) +
                                    Math.pow(listPoligY[tmpIndexMinY] - listPoligY[tmpIndexMinY], 2)),
                            (int) Math.sqrt(Math.pow(listPoligX[tmpIndexMinX] - listPoligX[tmpIndexMinX], 2) +
                                    Math.pow(tmpMaxY - listPoligY[tmpIndexMinY], 2)));


//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


                    for (int i = listNearX.size() + 1; i < listPoligX.length; i++) {
                        listNearBigX.add(listPoligX[i]);
                    }
                    for (int i = listNearX.size() + 1; i < listPoligY.length; i++) {
                        listNearBigY.add(listPoligY[i]);
                    }

                    g.setColor(Color.RED);
//        g.drawLine(listNearBigX.get(0),listNearBigY.get(0),listNearBigX.get(452),listNearBigY.get(452));


                    int tmpBigMaxX = 0;
                    for (int i = 0; i < listNearBigX.size(); i++) {

                        if (tmpBigMaxX < listNearBigX.get(i)) {
                            tmpBigMaxX = listNearBigX.get(i);
                        }
                    }
//        System.out.println("tmpBigMaxX "+tmpBigMaxX+" "+listNearBigX.get(452));
                    int tmpBigMaxY = 0;
                    for (int i = 0; i < listNearBigY.size(); i++) {

                        if (tmpBigMaxY < listNearBigY.get(i)) {
                            tmpBigMaxY = listNearBigY.get(i);
                        }
                    }
//        System.out.println("tmpBigMaxY "+tmpBigMaxY+" "+listNearBigY.get(452));

                    int tmpBigMinX = listNearBigX.get(0);
                    int tmpIndexBigMinX = 0;
                    for (int i = 0; i < listNearBigX.size(); i++) {

                        if (tmpBigMinX > listNearBigX.get(i)) {
                            tmpBigMinX = listNearBigX.get(i);
                            tmpIndexBigMinX = i;
                        }
                    }
                    int tmpBigMinY = listNearBigY.get(0);
                    int tmpIndexBigMinY = 0;
                    for (int i = 0; i < listNearBigY.size(); i++) {

                        if (tmpBigMinY > listNearBigY.get(i)) {
                            tmpBigMinY = listNearBigY.get(i);
                            tmpIndexBigMinY = i;
                        }
                    }

//        System.out.println("tmpBigMinX "+tmpBigMinX+" "+listNearBigX.get(0)+" "+ tmpIndexBigMinX);
//        System.out.println("tmpBigMinY "+tmpBigMinY+" "+listNearBigY.get(0)+" "+ tmpIndexBigMinY);
//        System.out.println("tmpBigMinX "+tmpBigMinX+" "+listNearBigX.get(0)+" "+ tmpIndexMinX);
//        System.out.println("tmpBigMinY "+tmpBigMinY+" "+listNearBigY.get(0)+" "+ tmpIndexMinY);


                    g.drawRect(listNearBigX.get(tmpIndexBigMinX), listNearBigY.get(tmpIndexBigMinY),
                            (int) Math.sqrt(Math.pow(tmpBigMaxX - listNearBigX.get(tmpIndexBigMinX), 2) +
                                    Math.pow(listPoligY[tmpIndexBigMinY] - listPoligY[tmpIndexBigMinY], 2)),
                            (int) Math.sqrt(Math.pow(listPoligX[tmpIndexBigMinX] - listPoligX[tmpIndexBigMinX], 2) +
                                    Math.pow(tmpBigMaxY - listNearBigY.get(tmpIndexBigMinY), 2)));


//        System.out.println(Math.sqrt(Math.pow(listPoligX[217]-listPoligX[0],2)+Math.pow(listPoligY[217]-listPoligY[0],2)));
//        System.out.println(listNearX.size());
//        System.out.println(listNearY.size());
//        System.out.println(listNearBigX.size());
//        System.out.println(listNearBigY.size());
//        System.out.println(Math.sqrt(Math.pow(listPoligX[600]-listPoligX[0],2)+Math.pow(listPoligY[600]-listPoligY[0],2)));
//        System.out.println(count);

//        for( int i = 0; i < w; i++ ) {
//            for (int j = 0; j < h; j++) {
//                if (screenArray[i][j] != screenArray2[i][j]) {


//                    g.drawRect(listPoligX[0],listPoligY[0],100, 20);

//                    screen.getGraphics().setColor(Color.red);
//                    screen.getGraphics().drawOval(i, j, 5, 5);
//                    screen.setRGB(i, j, rgb);
//                }
//            }
//        }
                }
                label3.setText(filesCompare);
            } else {
                label3.setForeground(Color.red);
                label3.setText("Error size file");
            }
        }
    }
}
