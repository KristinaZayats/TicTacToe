import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {

    static ImageIcon cross = new ImageIcon("cross.png"); // картинка крестика
    static ImageIcon circle = new ImageIcon("circle.png"); // картинка нолика

    static int gameType = 0; // переменная, отвечающая за тип игры (консоль = 1 или окно = 2)

    static int[][] winSituations = {
            {1, 2, 3}, {4, 5, 6}, {7, 8, 9}, // массив выигрышных ситуаций
            {1, 4, 7}, {2, 5, 8}, {3, 6 ,9},
            {1, 5, 9}, {3, 5, 7}
    };

    static boolean inProgress = false; // логическая переменная, показывающая, идет ли в данный момент игра

    static char[] gameBoard; // массив клеток игрового поля

    static char AIChar = ' ', humanChar = ' '; // переменные, хранящие символы, которыми ходят компьютер и игрок

    static int curPlayer = 0; // переменная, показывающая, кто делает ход в данный момент

    static Scanner in = new Scanner(System.in);

    static final ArrayList<Integer> AIPositions = new ArrayList<>(); // список, хранящий индексы позиций, занятых компьютером
    static final ArrayList<Integer> humanPositions = new ArrayList<>(); // список, хранящий индексы позиций, занятых человеком

    static void startGame() { // функция, запускающая игру и запрашивающая желаемый вид игры (консоль или окно)
        JFrame jFrame = new JFrame("Tic Tac Toe"); // создание экземпляра класса JFrame, создание тем самым окна, присвоение ему названия
        jFrame.setSize(400, 200); // установка размеров окна
        jFrame.setResizable(false); // отключение возможности изменения размеров окна
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // установка действия при нажатии на крестик в верхней панели окна (завершение работы)

        JPanel text = new JPanel(); // создание области для текста
        JLabel label = new JLabel("Where would you like to play?"); // создание текста
        label.setHorizontalAlignment(JLabel.CENTER); // установка горизонтального выравнивания области по центру
        label.setVerticalAlignment(JLabel.TOP); // установка вертикального выравнивания области по верхнему краю
        label.setForeground(new Color(0x42373D)); // установка цвета текста
        text.setBorder(new EmptyBorder(30, 10, 0 ,10)); // установка отступов области
        text.add(label); // расположение текста в области

        JPanel panel = new JPanel(); // установка области, в которой будет храниться область с текстом и область с кнопками
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // установка для главной области разметки, в которой выравнивание будет производиться по вертикали

        JPanel buttons = new JPanel(); // создание области для кнопок
        buttons.setLayout(new FlowLayout()); // установка для области для кнопок разметки, которая выровняет кнопки по ширине
        buttons.setBorder(new EmptyBorder(0, 0, 20, 0)); // установка границ области с кнопками

        JButton console = new JButton(); // создание кнопки "консоль"
        console.setText("Console"); // установка текста на кнопке
        console.setBackground(new Color(0xFDE2B1)); // установка цвета фона кнопки
        console.setForeground(new Color(0x42373D)); // установка цвета текста на кнопке
        buttons.add(console); // добавление кнопки в область для кнопок

        JButton window = new JButton(); // создание кнопки "окно"
        window.setText("Window"); //установка текста на кнопке
        window.setBackground(new Color(0xFDE2B1)); // установка цвета фона кнопки
        window.setForeground(new Color(0x42373D)); // установка цвета текста на кнопке
        buttons.add(window); // добавление кнопки в область для кнопок

        panel.add(text); // добавление области с текстом в главную область
        panel.add(buttons); // добавление области с кнопками в главную область
        panel.setBackground(new Color(0x9A7EA6)); // установка цвета фона главной области
        buttons.setBackground(new Color(0x9A7EA6)); // установка цвета фона области с кнопками
        text.setBackground(new Color(0x9A7EA6)); // установка цвета фона с текстом
        jFrame.add(panel); // добавление в окно главной области

        console.addActionListener(e -> { // установка обработчика событий на кнопку "консоль"
            gameType = 1; // определение типа игры как консольный
            jFrame.dispose(); // закрытие окна с выбором
            runGameInConsole(); // вызов функции, запускающей игру в консоли
        });

        window.addActionListener(e -> { // установка обработчика событий на кнопку "окно"
            gameType = 2; // определение типа игры как оконный
            jFrame.dispose(); // закрытие окна с выбором
            askForSideWindow(); // вызов функции, запускающей игру в окне
        });

        jFrame.setVisible(true); // установка окна видимым

        if (gameType == 1) runGameInConsole(); // при консольном типе игры вызывается функция, запускающая игру в консоли
    }

    static void runGameInConsole() { // функция, запускающая игру в консоли
        inProgress = true; // игра в процессе

        gameBoard = new char[9]; // инициализация массива, хранящего игровое поле
        Arrays.fill(gameBoard, ' '); // заполнение поля пробелами

        System.out.println("Choose which side will you play for (type in x to play as cross and o to play as circle)."); // запрос на выбор стороны игроком

        String sideInput = in.nextLine(); // считывание ответа игрока
        if (sideInput.equals("x")) { // если игрок выбрал крестик
            curPlayer = 1; // игрок ходит первым
            humanChar = 'X'; // игрок ходит крестиком
            AIChar = 'O'; // компьютер ходит ноликом
        }
        else if (sideInput.equals("o")) { // если игрок выбрал нолик
            curPlayer = 2; // компьютер ходит первым
            humanChar = 'O'; // игрок ходит ноликом
            AIChar = 'X'; // игрок ходит крестиком
        }
        else {
            System.out.println("Wrong input."); // если введено что-то кроме нолика или крестика, выводится сообщение о неправильности выбора
            return;
        }

        showConsoleBoard(); // вывод игрового поля в консоль

        do {
            makeConsoleMove(); // один ход
            check(); // проверка игры на завершение игры
        } while (inProgress); // пока игра в процессе
    }

    static void makeConsoleMove() { // функция, делающая ход
        if (curPlayer == 1) { // если ходит игрок
            System.out.println("Select a position for your move (1-9)."); // запрос позиции хода
            int pos = in.nextInt(); // считывание ответа
            if (pos > 9 || pos < 1) { // если ответ за границами поля
                System.out.println("No such position."); // вывод сообщения об ошибке
            }
            if (pos > 0 && pos < 10 && gameBoard[pos - 1] == ' ') { // если ответ в пределах поля и позиция свободна
                gameBoard[pos - 1] = humanChar; // размещение символа на поле
                humanPositions.add(pos); // добавление позиции в занятые игроком
                curPlayer = 2; // смена текущего игрока
                showConsoleBoard(); // вывод игрового поля в консоль
            }
            else if (pos > 0 && pos < 10 && gameBoard[pos - 1] != ' ') // если ответ в пределах поля, но позиция занята
                System.out.println("This position has already been used."); // вывод сообщения об ошибке
        }
        else if (curPlayer == 2) { // если ходит компьютер
            int pos = 1 + (int) (Math.random() * 9); // случайная генерация позиции для хода компьютера
            if (gameBoard[pos - 1] == ' ') { // если позиция свободна
                gameBoard[pos - 1] = AIChar; // размещение символа на поле
                AIPositions.add(pos); // добавление позиции в занятые компьютером
                curPlayer = 1; // смена текущего игрока
                showConsoleBoard(); // вывод игрового поля в консоль
            }
        }
    }

    static void showConsoleBoard() { // вывод игрового поля в консоль
        System.out.println(); // отступ
        System.out.println(); // отступ
        System.out.println(gameBoard[0] + "|" + gameBoard[1] + "|" + gameBoard[2]); // первая строка
        System.out.println("-+-+-"); // разделитель
        System.out.println(gameBoard[3] + "|" + gameBoard[4] + "|" + gameBoard[5]); // вторая строка
        System.out.println("-+-+-"); // разделитель
        System.out.println(gameBoard[6] + "|" + gameBoard[7] + "|" + gameBoard[8]); // третья строка
        System.out.println(); // отступ
    }

    static void check() { // проверка на завершение игры
        for (int[] winSituation : winSituations) { // цикл по выигрышным ситуациям
            if (humanPositions.contains(winSituation[0]) && humanPositions.contains(winSituation[1]) && humanPositions.contains(winSituation[2])) { // если среди ходов человека содержится выигрышная комбинация
                if (gameType == 1) // если игра в консоли
                    System.out.println("Congrats! You've won!"); // вывод сообщения о победе
                else { // если игра в окне
                    JFrame jFrame = new JFrame("Tic Tac Toe"); // создание окна оповещения
                    jFrame.setSize(400, 200); // установка размеров окна
                    jFrame.setResizable(false); // отключение возможности изменения размеров окна
                    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //установка действия при нажатии на крестик в верхней панели окна (завершение работы)
                    JPanel text = new JPanel(); // создание области для текста
                    text.setBackground(new Color(0x9A7EA6)); // установка цвета фона текстовой области
                    JLabel label = new JLabel("Congrats! You've won!"); // создание сообщения о победе
                    label.setForeground(new Color(0x42373D)); // установка цвета текста
                    label.setHorizontalAlignment(JLabel.CENTER); // установка горизонтального выравнивания текста по центру
                    text.setBorder(new EmptyBorder(50, 10, 0 ,10)); // установка границ текстовой области
                    text.add(label); // добавление сообщения в текстовую область
                    jFrame.add(text); // добавление текстовой области в окно
                    jFrame.setVisible(true); // установка окна видимым
                }
                inProgress = false; // игра завершается
                break; // прерывание цикла проверки
            } else if (AIPositions.contains(winSituation[0]) && AIPositions.contains(winSituation[1]) && AIPositions.contains(winSituation[2])) { // если среди ходов человека содержится выигрышная комбинация
                if (gameType == 1) // если игра в консоли
                    System.out.println("You've lost!"); // вывод сообщения о поражении
                else { // если игра в окне
                    JFrame jFrame = new JFrame("Tic Tac Toe"); // создание окна оповещения
                    jFrame.setSize(400, 200); // установка размеров окна
                    jFrame.setResizable(false); // отключение возможности изменения размеров окна
                    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //установка действия при нажатии на крестик в верхней панели окна (завершение работы)
                    JPanel text = new JPanel(); // создание области для текста
                    text.setBackground(new Color(0x9A7EA6)); // установка цвета фона текстовой области
                    JLabel label = new JLabel("You've lost!"); // создание сообщения о поражении
                    label.setForeground(new Color(0x42373D)); // установка цвета текста
                    label.setHorizontalAlignment(JLabel.CENTER); // установка горизонтального выравнивания текста по центру
                    text.setBorder(new EmptyBorder(50, 10, 0 ,10)); // установка границ текстовой области
                    text.add(label); // добавление сообщения в текстовую область
                    jFrame.add(text); // добавление текстовой области в окно
                    jFrame.setVisible(true); // установка окна видимым
                }
                inProgress = false; // игра завершается
                break; // прерывание цикла проверки
            }
        }
        if (humanPositions.size() + AIPositions.size() == 9 && inProgress) { // если сделано 9 ходов, но игра еще идет
            if (gameType == 1) // если игра в консоли
                System.out.println("Draw!"); // вывод сообщения о ничьей
            else { // если игра в окне
                JFrame jFrame = new JFrame("Tic Tac Toe"); // создание окна оповещения
                jFrame.setSize(400, 200); // установка размеров окна
                jFrame.setResizable(false); // отключение возможности изменения размеров окна
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //установка действия при нажатии на крестик в верхней панели окна (завершение работы)
                JPanel text = new JPanel(); // создание области для текста
                text.setBackground(new Color(0x9A7EA6)); // установка цвета фона текстовой области
                JLabel label = new JLabel("Draw!"); // создание сообщения о ничьей
                label.setForeground(new Color(0x42373D)); // установка цвета текста
                label.setHorizontalAlignment(JLabel.CENTER); // установка горизонтального выравнивания текста по центру
                text.setBorder(new EmptyBorder(50, 10, 0 ,10)); // установка границ текстовой области
                text.add(label); // добавление сообщения в текстовую область
                jFrame.add(text); // добавление текстовой области в окно
                jFrame.setVisible(true); // установка окна видимым
            }
            inProgress = false; // игра завершается
        }
    }

    static void askForSideWindow() { // функция, запрашивающая, за какую сторону будет играть игрок в окне
        JFrame jFrame = new JFrame("Tic Tac Toe"); // создание окна
        jFrame.setSize(400, 200); // установка размеров окна
        jFrame.setResizable(false); // отключение возможности изменять размер окна
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // установка действия при нажатии на крестик в верхней панели окна (завершение работы)

        JPanel text = new JPanel(); // создание области для текста
        JLabel label = new JLabel("Choose which side will you play for"); // создание текста с запросом
        label.setHorizontalAlignment(JLabel.CENTER); // установка горизонтального выравнивания по центру
        label.setVerticalAlignment(JLabel.TOP);  // установка вертикального выравнивания по верхней границе
        label.setForeground(new Color(0x42373D)); // установка цвета текста
        text.setBorder(new EmptyBorder(30, 10, 0 ,10)); // установка границ текстовой области
        text.add(label); // добавление текста в текстовую область

        JPanel panel = new JPanel(); // создание главной области
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // установка для главной области разметки, в которой выравнивание будет производиться по вертикали

        JPanel buttons = new JPanel(); // создание области для кнопок
        buttons.setLayout(new FlowLayout()); // установка для области для кнопок выравнивания по ширине
        buttons.setBorder(new EmptyBorder(0, 0, 20, 0)); // установка границ области для кнопок

        JButton x = new JButton(); // создание кнопки "х"
        x.setText("X"); // установка текста на кнопку
        x.setBackground(new Color(0xFDE2B1)); // установка цвета фона кнопки
        x.setForeground(new Color(0x42373D)); // установка цвета текста на кнопке
        buttons.add(x); // добавление кнопки "х" в область для кнопок

        JButton o = new JButton(); // создание кнопки "о"
        o.setText("O"); // установка текста на кнопку
        o.setBackground(new Color(0xFDE2B1)); // установка цвета фона кнопки
        o.setForeground(new Color(0x42373D)); // установка цвета текста на кнопке
        buttons.add(o); // добавление кнопки "о" в область для кнопок

        panel.add(text); // добавление области для текста в главную
        panel.add(buttons); // добавление области для текста в главную
        panel.setBackground(new Color(0x9A7EA6)); // установка цвета фона главной области
        buttons.setBackground(new Color(0x9A7EA6)); // установка цвета фона области с кнопками
        text.setBackground(new Color(0x9A7EA6)); // установка цвета фона области с текстом
        jFrame.add(panel); // добавление основной области в окно

        x.addActionListener(e -> { // создание обработчика событий для кнопки "х"
            curPlayer = 1; // игрок ходит первым
            AIChar = 'O'; // компьютер ходит ноликом
            humanChar = 'X'; // игрок ходит крестиком
            jFrame.dispose(); // окно закрывается
            runGameInWindow(); // вызов функции, запускающей игру в окне
        });

        o.addActionListener(e -> { // создание обработчика событий для кнопки "о"
            curPlayer = 2; //компьютер ходит первым
            AIChar = 'X'; // компьютер ходит крестиком
            humanChar = 'O'; // игрок ходит ноликом
            jFrame.dispose(); // окно закрывается
            runGameInWindow(); // вызов функции, запускающей игру в окне
        });
        jFrame.setVisible(true); // установка окна видимым
    }

    static void runGameInWindow() { // функция, запускающая игру в окне
        inProgress = true; // игра в процессе
        JFrame jFrame = new JFrame("Tic Tac Toe"); // создание экземпляра класса JFrame, создание тем самым окна, присвоение ему названия
        jFrame.setSize(480, 480); // установка размеров окна
        jFrame.setResizable(false); // отключение возможности изменять размеры окна
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // установка действия при нажатии на крестик в верхней панели окна (завершение работы)
        jFrame.setLayout(new GridLayout(3, 3)); // создание в окне сеточного макета с тремя рядами и строками


        JButton[] buttons = new JButton[9]; // массив кнопок (игровое поле)

        for (int i = 0; i < 9; i++) { // цикл, итерирующийся по кнопкам
            buttons[i] = new JButton(); // инициализация кнопки
            buttons[i].setBackground(new Color(0x9A7EA6)); // установка цвета фона кнопки
            buttons[i].setBorder(new LineBorder(new Color(0x42373D))); // установка границы кнопки
            int buttonNum = i; // сохранение номера кнопки
            buttons[i].addActionListener(e -> { // создание обработчика событий для кнопки i
                if (curPlayer == 1) { // если ходит игрок
                    if (humanChar == 'X') // если игрок ходит крестиком
                        buttons[buttonNum].setIcon(cross); // размещение иконки крестика на кнопке
                    else buttons[buttonNum].setIcon(circle); // в противном случае - иконки нолика
                    humanPositions.add(buttonNum + 1); // добавление позиции в ходы игрока
                    buttons[buttonNum].setEnabled(false); // отключение кнопки
                    check(); // проверка на завершение игры
                    if (!inProgress) { // если игра завершилась
                        for (int j = 0; j < 9; j++) { // в цикле по кнопкам
                            buttons[j].setEnabled(false); // отключение i-ой кнопки
                        }
                        return; // выход из функции
                    }
                    curPlayer = 2; // смена игрока
                    int pos = 1 + (int) (Math.random() * 9); // выбор случайного числа - позиции для хода компьютера
                    while (humanPositions.contains(pos) || AIPositions.contains(pos)) { // пока выбранная клетка занята
                        pos = 1 + (int) (Math.random() * 9); // выбор случайного числа - позиции
                    }
                    buttons[pos - 1].doClick(); // нажатие на случайную кнопку компьютером
                }
                else if (curPlayer == 2) { // если ходит компьютер
                    if (AIChar == 'O') // если компьютер ходит ноликом
                        buttons[buttonNum].setIcon(circle); // размещение иконки нолика на кнопке
                    else buttons[buttonNum].setIcon(cross); // в противном случае - крестика
                    buttons[buttonNum].setEnabled(false); // отключение кнопки
                    AIPositions.add(buttonNum + 1); // добавление позиции в ходы компьютера
                    check(); // проверка на завершение игры
                    if (!inProgress) { // если игра завершилась
                        for (int j = 0; j < 9; j++) { // в цикле по кнопкам
                            buttons[j].setEnabled(false); // отключение i-ой кнопки
                        }
                        return; // выход из функции
                    }
                    curPlayer = 1; // смена игрока
                }
            });
            jFrame.add(buttons[i]); // добавление i-ой кнопки в окно
        }
        jFrame.setVisible(true); // установка окна видимым
        if (curPlayer == 2) // если игру начинает компьютер
            buttons[(int) (Math.random() * 9)].doClick(); // нажатие на случайную кнопку компьютером
    }

    public static void main(String[] args) { // драйвер-функция
        startGame(); // начало игры
        check(); // финальная проверка на завершение игры
    }
}