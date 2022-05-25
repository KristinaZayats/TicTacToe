import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {

    static ImageIcon cross = new ImageIcon("cross.png");
    static ImageIcon circle = new ImageIcon("circle.png");

    static int gameType = 0;

    static int[][] winSituations = {
            {1, 2, 3}, {4, 5, 6}, {7, 8, 9},
            {1, 4, 7}, {2, 5, 8}, {3, 6 ,9},
            {1, 5, 9}, {3, 5, 7}
    };

    static int movesCnt = 0;

    static boolean inProgress = false;

    static char[] gameBoard;

    static char AIChar = ' ', humanChar = ' ';

    static int curPlayer = 0;

    static Scanner in = new Scanner(System.in);

    static final ArrayList<Integer> AIPositions = new ArrayList<>();
    static final ArrayList<Integer> humanPositions = new ArrayList<>();

    static void runGameInConsole() {
        inProgress = true;

        gameBoard = new char[9];
        Arrays.fill(gameBoard, ' ');

        System.out.println("Choose which side will you play for (type in x to play as cross and o to play as circle).");

        String sideInput = in.nextLine();
        if (sideInput.equals("x")) {
            curPlayer = 1;
            humanChar = 'X';
            AIChar = 'O';
        }
        else if (sideInput.equals("o")) {
            curPlayer = 2;
            humanChar = 'O';
            AIChar = 'X';
        }
        else {
            System.out.println("Wrong input.");
            return;
        }

        showConsoleBoard();

        do {
            makeConsoleMove();
            check();
        } while (inProgress);
    }

    static void makeConsoleMove() {
        if (curPlayer == 1) {
            System.out.println("Select a position for your move (1-9).");
            int pos = in.nextInt();
            if (pos > 9 || pos < 1) {
                System.out.println("No such position.");
            }
            if (pos > 0 && pos < 10 && gameBoard[pos - 1] == ' ') {
                gameBoard[pos - 1] = humanChar;
                humanPositions.add(pos);
                curPlayer = 2;
                movesCnt++;
                showConsoleBoard();
            }
            else if (pos > 0 && pos < 10 && gameBoard[pos - 1] != ' ') System.out.println("This position has already been used.");
        }
        else if (curPlayer == 2) {
            int pos = 1 + (int) (Math.random() * 9);
            if (gameBoard[pos - 1] == ' ') {
                gameBoard[pos - 1] = AIChar;
                AIPositions.add(pos);
                curPlayer = 1;
                movesCnt++;
                showConsoleBoard();
            }
        }
    }

    static void showConsoleBoard() {
        System.out.println();
        System.out.println();
        System.out.println(gameBoard[0] + "|" + gameBoard[1] + "|" + gameBoard[2]);
        System.out.println("-+-+-");
        System.out.println(gameBoard[3] + "|" + gameBoard[4] + "|" + gameBoard[5]);
        System.out.println("-+-+-");
        System.out.println(gameBoard[6] + "|" + gameBoard[7] + "|" + gameBoard[8]);
        System.out.println();
    }

    static void check() {
        for (int[] winSituation : winSituations) {
            if (humanPositions.contains(winSituation[0]) && humanPositions.contains(winSituation[1]) && humanPositions.contains(winSituation[2])) {
                if (gameType == 1) System.out.println("Congrats! You've won!");
                else {
                    JFrame jFrame = new JFrame("Tic Tac Toe");
                    jFrame.setSize(400, 200);
                    jFrame.setResizable(false);
                    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    JPanel text = new JPanel();
                    text.setBackground(new Color(0x9A7EA6));
                    JLabel label = new JLabel("Congrats! You've won!");
                    label.setForeground(new Color(0x42373D));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    text.setBorder(new EmptyBorder(50, 10, 0 ,10));
                    text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
                    text.add(label);
                    jFrame.add(text);
                    jFrame.setVisible(true);
                }
                inProgress = false;
                break;
            } else if (AIPositions.contains(winSituation[0]) && AIPositions.contains(winSituation[1]) && AIPositions.contains(winSituation[2])) {
                if (gameType == 1) System.out.println("You've lost!");
                else {
                    JFrame jFrame = new JFrame("Tic Tac Toe");
                    jFrame.setSize(400, 200);
                    jFrame.setResizable(false);
                    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    JPanel text = new JPanel();
                    text.setBackground(new Color(0x9A7EA6));
                    JLabel label = new JLabel("You've lost!");
                    label.setForeground(new Color(0x42373D));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    text.setBorder(new EmptyBorder(50, 10, 0 ,10));
                    text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
                    text.add(label);
                    jFrame.add(text);
                    jFrame.setVisible(true);
                }
                inProgress = false;
                break;
            }
        }
        if (humanPositions.size() + AIPositions.size() == 9 && inProgress) {
            if (gameType == 1) System.out.println("Draw!");
            else {
                JFrame jFrame = new JFrame("Tic Tac Toe");
                jFrame.setSize(400, 200);
                jFrame.setResizable(false);
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                JPanel text = new JPanel();
                text.setBackground(new Color(0x9A7EA6));
                JLabel label = new JLabel("Draw!");
                label.setForeground(new Color(0x42373D));
                label.setHorizontalAlignment(JLabel.CENTER);
                text.setBorder(new EmptyBorder(50, 10, 0 ,10));
                text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
                text.add(label);
                jFrame.add(text);
                jFrame.setVisible(true);
            }
            inProgress = false;
        }
    }

    static void runGame() {
        JFrame jFrame = new JFrame("Tic Tac Toe");
        jFrame.setSize(400, 200);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel text = new JPanel();
        JLabel label = new JLabel("Where would you like to play?");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setForeground(new Color(0x42373D));
        text.setBorder(new EmptyBorder(30, 10, 0 ,10));
        text.add(label);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton console = new JButton();
        console.setText("Console");
        console.setBackground(new Color(0xFDE2B1));
        console.setForeground(new Color(0x42373D));
        buttons.add(console);

        JButton window = new JButton();
        window.setText("Window");
        window.setBackground(new Color(0xFDE2B1));
        window.setForeground(new Color(0x42373D));
        buttons.add(window);

        panel.add(text);
        panel.add(buttons);
        panel.setBackground(new Color(0x9A7EA6));
        buttons.setBackground(new Color(0x9A7EA6));
        text.setBackground(new Color(0x9A7EA6));
        jFrame.add(panel);

        console.addActionListener(e -> {
            gameType = 1;
            jFrame.dispose();
            runGameInConsole();
        });

        window.addActionListener(e -> {
            gameType = 2;
            jFrame.dispose();
            askForSideWindow();
        });
        jFrame.setVisible(true);

        if (gameType == 1) runGameInConsole();
    }

    static void askForSideWindow() {
        JFrame jFrame = new JFrame("Tic Tac Toe");
        jFrame.setSize(400, 200);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setVisible(true);

        JPanel text = new JPanel();
        JLabel label = new JLabel("Choose which side will you play for");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setForeground(new Color(0x42373D));
        text.setBorder(new EmptyBorder(30, 10, 0 ,10));
        text.add(label);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton x = new JButton();
        x.setText("X");
        x.setBackground(new Color(0xFDE2B1));
        x.setForeground(new Color(0x42373D));

        buttons.add(x);

        JButton o = new JButton();
        o.setText("O");
        o.setBackground(new Color(0xFDE2B1));
        o.setForeground(new Color(0x42373D));
        buttons.add(o);

        panel.add(text);
        panel.add(buttons);
        panel.setBackground(new Color(0x9A7EA6));
        buttons.setBackground(new Color(0x9A7EA6));
        text.setBackground(new Color(0x9A7EA6));
        jFrame.add(panel);

        x.addActionListener(e -> {
            curPlayer = 1;
            AIChar = 'O';
            humanChar = 'X';
            jFrame.dispose();
            runGameInWindow();
        });

        o.addActionListener(e -> {
            curPlayer = 2;
            AIChar = 'X';
            humanChar = 'O';
            jFrame.dispose();
            runGameInWindow();
        });
    }

    static void runGameInWindow() {
        inProgress = true;
        JFrame jFrame = new JFrame("Tic Tac Toe");
        jFrame.setSize(480, 480);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridLayout(3, 3));


        JButton[] buttons = new JButton[9];

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setBackground(new Color(0x9A7EA6));
            buttons[i].setBorder(new LineBorder(new Color(0x42373D)));
            int buttonNum = i;
            buttons[i].addActionListener(e -> {
                if (curPlayer == 1) {
                    if (humanChar == 'X') buttons[buttonNum].setIcon(cross);
                    else buttons[buttonNum].setIcon(circle);
                    humanPositions.add(buttonNum + 1);
                    buttons[buttonNum].setEnabled(false);
                    check();
                    if (!inProgress) {
                        for (int j = 0; j < 9; j++) {
                            buttons[j].setEnabled(false);
                        }
                        return;
                    }
                    curPlayer = 2;
                    int pos = 1 + (int) (Math.random() * 9);
                    while (humanPositions.contains(pos) || AIPositions.contains(pos)) {
                        pos = 1 + (int) (Math.random() * 9);
                    }
                    buttons[pos - 1].doClick();
                }
                else if (curPlayer == 2) {
                    if (AIChar == 'O') buttons[buttonNum].setIcon(circle);
                    else buttons[buttonNum].setIcon(cross);
                    buttons[buttonNum].setEnabled(false);
                    AIPositions.add(buttonNum + 1);
                    check();
                    if (!inProgress) {
                        for (int j = 0; j < 9; j++) {
                            buttons[j].setEnabled(false);
                        }
                        return;
                    }
                    curPlayer = 1;
                }
            });
            jFrame.add(buttons[i]);
        }
        jFrame.setVisible(true);
        if (curPlayer == 2) buttons[(int) (Math.random() * 9)].doClick();
    }

    public static void main(String[] args) {
        runGame();
        check();
    }
}