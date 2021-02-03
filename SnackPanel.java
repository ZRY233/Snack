import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnackPanel extends JPanel implements ActionListener {
    static int width = 400;
    static int height = 400;
    static int unit = 25;
    static int delay = 300;
    int snackLength = 6;
    char direction = 'd';
    int[] x = new int[(width * height) / unit];
    int[] y = new int[(width * height) / unit];
    int appleX;
    int appleY;
    Random random;
    Timer timer;

    SnackPanel() {
        random = new Random();
        timer = new Timer(delay, this);
        setPreferredSize(new Dimension(width, height));
        addKeyListener(new MyKey());
        addComponentListener(new MyCom());
        startGame();
    }

    public void startGame() {
        for (int i = 0; i < snackLength; i++) {
            x[i] = 0;
            y[i] = 0;
            direction = 'd';
        }


        timer.start();
    }

    public void move() {
        for (int i = snackLength; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'w' -> y[0] = y[0] - unit;
            case 's' -> y[0] = y[0] + unit;
            case 'a' -> x[0] = x[0] - unit;
            case 'd' -> x[0] = x[0] + unit;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(x[snackLength], y[snackLength], unit, unit);
        g.setColor(Color.black);
        for (int i = 0; i <= height / unit; i++) {
            g.drawLine(0, i * unit, width, i * unit);
        }
        for (int i = 0; i <= width / unit; i++) {
            g.drawLine(i * unit, 0, i * unit, height);
        }
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, unit, unit);

        for (int i = 0; i < snackLength; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], unit, unit);
            } else {
                g.setColor(Color.red);
                g.fillRect(x[i], y[i], unit, unit);
                g.setColor(Color.white);
                g.drawString(String.valueOf(i), x[i], y[i] + unit);
            }
        }
    }

    public void newApple() {
        //生成苹果的代码,我觉得还可以继续改进
        int X = random.nextInt(width / unit) * unit;
        int Y = random.nextInt(height / unit) * unit;
        for (int i = 0; i < snackLength; i++) {
            if (x[i] == X && y[i] == Y) {
                //重合
                X = random.nextInt(width / unit) * unit;
                Y = random.nextInt(width / unit) * unit;
                i = 0;
            }
        }
        appleX = X;
        appleY = Y;
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            snackLength++;
            newApple();
        }
    }

    public void checkCollision() {
        //碰到自己
        for (int i = 1; i < snackLength; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                gameOver();
            }
        }
        //碰到墙
        if (x[0] >= width || y[0] >= height || x[0] < 0 || y[0] < 0) {
            gameOver();
        }

    }

    public void gameOver() {
        timer.stop();
        int op = JOptionPane.showConfirmDialog(this, "You Die ! Replay ?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (op == 0) {
            startGame();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkApple();
        move();
        checkCollision();

        repaint();
    }

    class MyCom extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            width = getWidth();
            height = getHeight();
//            newApple();
        }
    }

    class MyKey extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyChar()) {
                case 'w':
                    if (!(direction == 's')) {
                        direction = 'w';
                    }
                    break;
                case 's':
                    if (!(direction == 'w')) {
                        direction = 's';
                        break;
                    }
                case 'a':
                    if (!(direction == 'd')) {
                        direction = 'a';
                        break;
                    }
                case 'd':
                    if (!(direction == 'a')) {
                        direction = 'd';
                        break;
                    }
            }
//            move();
        }
    }
}
