package me.madcuzdev.paddleball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Random;

public class PaddleBall extends JComponent implements ActionListener, MouseMotionListener, KeyListener {
    private int randomStartx() {
        Random rand = new Random();
        int num = rand.nextInt(3 + 3) - 3;
        while (num == 0)
            num = rand.nextInt(3 + 3) - 3;
        return num;
    }
    private static JFrame wind = new JFrame("Paddle Ball");

    private double ballx = 400;
    private double bally = 30;
    private double hardBallx = 400;
    private double hardBally = 30;
    private static int paddlex = 0;
    private double ballySpeed = 1;
    private double ballxSpeed = randomStartx();
    private double hardBallySpeed = 1;
    private double hardBallxSpeed = randomStartx();
    private double score = 0;
    private double highscore = 0;
    private double multi = 1;
    private boolean paused = false;
    private double difficulty = 1;
    private static PaddleBall g = new PaddleBall();
    private boolean deathText = false;

    public static int getPaddleX() {
        return paddlex;
    }
    public static void setPaddleX(int i) {
        paddlex = i;
    }

    private static void start() {
        JLabel sun = new JLabel(new ImageIcon(new ImageIcon(PaddleBall.class.getResource("sun.png")).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
        sun.setBounds(10, 30, 150, 150);
        g.add(sun);
        wind.add(g);
        wind.pack();
        wind.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        wind.setLocationRelativeTo(null);
        wind.setVisible(true);
        wind.setResizable(false);
        wind.addMouseMotionListener(g);
        wind.addKeyListener(g);

        Timer timer = new Timer(2, g);
        timer.start();
    }

    public static void main(String[] args) {
    start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    protected void paintComponent(Graphics g) {

        // Sky (blue)
        g.setColor(new Color(76, 177, 255));
        g.fillRect(0, 0, 850, 650);

        // Clouds
        g.setColor(Color.WHITE);
        g.fillOval(380, 50, 40, 30);
        g.fillOval(350, 40, 45, 31);
        g.fillOval(650, 45, 40, 38);
        g.fillOval(680, 40, 49, 31);
        g.fillOval(580, 50, 60, 30);
        g.fillOval(550, 40, 45, 31);
        g.fillOval(480, 40, 43, 26);
        g.fillOval(450, 45, 49, 39);
        g.fillOval(180, 40, 48, 33);
        g.fillOval(150, 45, 35, 31);
        g.fillOval(390, 50, 40, 35);
        g.fillOval(360, 40, 45, 31);
        g.fillOval(660, 45, 40, 30);
        g.fillOval(690, 40, 49, 40);
        g.fillOval(590, 50, 60, 30);
        g.fillOval(560, 40, 45, 40);
        g.fillOval(490, 40, 43, 30);
        g.fillOval(460, 45, 49, 39);
        g.fillOval(190, 40, 48, 41);
        g.fillOval(160, 45, 35, 31);

        // Ground (green)
        g.setColor(new Color(31, 155, 49));
        g.fillRect(0, 530, 850, 650);

        //draw the paddle
        g.setColor(Color.black);
        g.fillRect(paddlex, 500, 100, 20);

        //draw the ball
        g.setColor(Color.yellow);
        g.fillOval((int)ballx, (int)bally, 30, 30);

        //draw the hard ball
        if (score >= 10) {
            g.setColor(Color.RED);
            g.fillOval((int) hardBallx, (int) hardBally, 30, 30);
        }

        //score
        g.setColor(Color.black);
        g.setFont(new Font("Impact", Font.BOLD, 20));
        g.drawString("SCORE: " + (int)score + " |  HIGHSCORE: " + (int)highscore + " | DIFFICULTY: " + new DecimalFormat("##.#").format(difficulty), 10, 30);

        if (deathText) {
            g.setFont(new Font("Impact", Font.BOLD, 30));
            g.setColor(Color.RED);
            drawCenteredString("You Died!", 0, 0, g);
        }


        // Pause menu
        if (paused) {
            g.setFont(new Font("Impact", Font.BOLD, 70));
            drawCenteredString("PAUSED", 0, -100, g);
            g.setFont(new Font("Impact", Font.BOLD, 40));
            drawCenteredString("CONTROLS", 0, 100, g);
            g.setFont(new Font("Impact", Font.BOLD, 20));
            drawCenteredString("Left and Right or A and D keys for movement", 0, 160, g);
            drawCenteredString("ESC and P keys for pause/menu", 0, 200, g);
            drawCenteredString("UP and DOWN keys for difficulty control", 0, 240, g);
        }
    }

    private void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        Dimension dim = this.getSize();
        int x = (dim.width + w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (dim.height + h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    private void checkHardBall() {
        if (paused) return;
        // hard ball paddle hit
        if (hardBallx + 45 >= paddlex && hardBallx <= paddlex + 100 && hardBally > 470 && hardBally < 480) {
            hardBallySpeed = -1;
            score+=5;
        }

        // hard ball Window top
        if (hardBally <= 0)
            hardBallySpeed = 1;

        // hard ball Window right
        if (hardBallx >= 770)
            hardBallxSpeed = -1;

        // hard ball Window left
        if (hardBallx <= 0)
            hardBallxSpeed = 1;

        // hard ball Window bottom
        if (hardBally >= 570)
            hardBallySpeed = -1;
    }

    private void checkBall() {
        if (paused) return;
        // ball paddle hit
        if (ballx + 45 >= paddlex && ballx <= paddlex + 100 && bally > 470 && bally < 480) {
            ballySpeed = -1;
            score++;
        }

        // ball Window top
        if (bally <= 0)
            ballySpeed = 1;

        // ball Window right
        if (ballx >= 770)
            ballxSpeed = -1;

        // ball Window left
        if (ballx <= 0)
            ballxSpeed = 1;
    }

    public void actionPerformed(ActionEvent e) {
        if (paused) return;

        // ball speeds
        ballx = ballx + (int)ballxSpeed * multi * difficulty;
        bally = bally + (int)ballySpeed * multi * difficulty;
        if (score >= 10) {
            hardBallx = hardBallx + (int)hardBallxSpeed * multi * 2 * difficulty;
            hardBally = hardBally + (int)hardBallySpeed * multi * 2 * difficulty;
        }

        // Death
        if (bally >= 720 ) {
            bally = 30;
            ballx = 400;
            ballySpeed = 1;
            ballxSpeed = randomStartx();

            hardBally = 30;
            hardBallx = 400;
            hardBallySpeed = 1;
            hardBallxSpeed = randomStartx();

            if (score > highscore)
                highscore = score;
            score = 0;

            if (deathText) return;
            deathText = true;

            java.util.Timer timer =  new java.util.Timer();
            for (int i = 500; i < 2501; i+=500) {
                timer.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                deathText = !deathText;
                            }
                        }, i
                );
            }

        }

        checkHardBall();
        checkBall();

        // Difficulty multiplier
        multi = 1 + score/20;

        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        if (paused) return;
        paddlex = e.getX() - 50;
        repaint();
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }


    private static boolean isKeyPressedValue = false;
    public static boolean isKeyPressed() {
        return isKeyPressedValue;
    }

    public void keyPressed(KeyEvent e) {

        // Pause
        if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            paused = !paused;
            repaint();
        }
        // Difficulty up
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            difficulty = difficulty + .2;
            repaint();
        }
        // Difficulty down
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            difficulty = difficulty - .2;
            if (difficulty < 1)
                difficulty = 1;
            repaint();
        }
        if (paused) return;
        // Left
        if ((e.getKeyCode() == KeyEvent.VK_A) && !isKeyPressedValue || e.getKeyCode() == KeyEvent.VK_LEFT && !isKeyPressedValue) {
            isKeyPressedValue = true;
            KeyMovement.goLeftKey();
        }
        // Right
        if ((e.getKeyCode() == KeyEvent.VK_D) && !isKeyPressedValue || e.getKeyCode() == KeyEvent.VK_RIGHT && !isKeyPressedValue) {
            isKeyPressedValue = true;
            KeyMovement.goRightKey();
        }

        repaint();
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                break;
            case KeyEvent.VK_RIGHT:
                break;
            case KeyEvent.VK_A:
                break;
            case KeyEvent.VK_D:
                break;
            default:
                return;
        }
        isKeyPressedValue = false;
    }

}
