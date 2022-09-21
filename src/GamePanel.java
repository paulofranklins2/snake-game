import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WITH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WITH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int bonusAppleEaten;
    int appleX;
    int appleY;

    int bonusAppleX;
    int bonusAppleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WITH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WITH, i * UNIT_SIZE);
            }
            graphics.setColor(Color.red);
            graphics.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            if (applesEaten % 10 == 0 && applesEaten != 0) {
                graphics.setColor(Color.pink);
                graphics.fillOval(bonusAppleX, bonusAppleY, UNIT_SIZE, UNIT_SIZE);
            }

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    graphics.setColor(Color.green);
                    graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    graphics.setColor(new Color(45, 180, 0));
                    //random color snake
//                    graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: " + applesEaten, (SCREEN_WITH - fontMetrics.stringWidth("Score" + applesEaten)) / 2, graphics.getFont().getSize());
        } else {
            gameOver(graphics);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WITH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void bonusApple() {
        bonusAppleX = random.nextInt((int) (SCREEN_WITH / UNIT_SIZE)) * UNIT_SIZE;
        bonusAppleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            applesEaten++;
            if (applesEaten % 10 == 0 && applesEaten != 0) {
                bodyParts = bodyParts + 5;
                bonusApple();
            }
            bodyParts++;
            newApple();
        }

        if (x[0] == bonusAppleX && y[0] == bonusAppleY) {
            bonusAppleEaten++;
            bodyParts = bodyParts - 2;
            applesEaten++;
        }
    }

    public void checkCollision() {
        for (int i = bodyParts; i > 0; i--) {
            //head collides with body
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
            //collides at left boarder
            if (x[0] < 0) {
                running = false;
            }
            //collides at right border
            if (x[0] > SCREEN_WITH) {
                running = false;
            }
            //collides at top border
            if (y[0] < 0) {
                running = false;
            }
            //collides at lower border
            if (y[0] > SCREEN_HEIGHT) {
                running = false;
            }

            if (running == false) {
                timer.stop();
            }
        }
    }

    public void gameOver(Graphics graphics) {
        //Game Over Text
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics fontMetricsGameOver = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (SCREEN_WITH - fontMetricsGameOver.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        //Score Text
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics fontMetricsScore = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: " + applesEaten, (SCREEN_WITH - fontMetricsScore.stringWidth("Score: " + applesEaten)) / 2, fontMetricsScore.getFont().getSize());

        //Score Text
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics fontMetricsBonusApple = getFontMetrics(graphics.getFont());
        graphics.drawString("Apple Bonus: " + bonusAppleEaten, (SCREEN_WITH - fontMetricsScore.stringWidth("Apple Bonus: " + bonusAppleEaten)) / 2, 50 + fontMetricsScore.getFont().getSize());

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
