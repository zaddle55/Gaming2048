package main.java;

import com.AL1S.ui.CustomGraphics.CustomButton;
import com.AL1S.ui.CustomGraphics.CustomDialog;
import com.AL1S.ui.MainInterface;
import com.AL1S.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @description: 主程序入口
 **/

public class MainApplication extends MainInterface {

    private Board board;
    private boolean gameStartFlag;
    private boolean gameWinFlag;
    private boolean gameOverFlag;

    public MainApplication () {
        super();

        /****************************************** 游戏逻辑 ******************************************/

        int size = 4; // 游戏板大小,后删
        drawBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                board = new Board(0, size, 0);
                board.init();
                draw(board.getBoard(), size);
            }
            // 隐藏下拉列表和绘制按钮
            public void mouseReleased(MouseEvent e) {
                gameStartFlag = true;
                startGameDisplay();
            }
        });

        // 添加上下左右按钮的监听器
        upButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameBoard.repaint();
            }

            public void mouseReleased(MouseEvent e) {

                setScore(board.getScore(), size);
                setGameState(board.isWin(), board.isOver());

                if (board.isWin()) {
                    gameWinFlag = true;
                    // gameWinDisplay();
                }

                if (board.isOver()) {
                    gameOverFlag = true;
                    // gameOverDisplay();
                } else {
                    board.addToHistory();
                    board.slip(0);

                }
                draw(board.getBoard(), size);
            }
        });
        downButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameBoard.repaint();
            }
            public void mouseReleased(MouseEvent e) {

                setScore(board.getScore(), size);
                setGameState(board.isWin(), board.isOver());

                if (board.isWin()) {
                    gameWinFlag = true;
                    // gameWinDisplay();
                }

                if (board.isOver()) {
                    gameOverFlag = true;
                    // gameOverDisplay();
                } else {
                    board.addToHistory();
                    board.slip(1);

                }
                draw(board.getBoard(), size);
            }
        });
        leftButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameBoard.repaint();
            }
            public void mouseReleased(MouseEvent e) {

                setScore(board.getScore(), size);
                setGameState(board.isWin(), board.isOver());

                if (board.isWin()) {
                    gameWinFlag = true;
                    // gameWinDisplay();
                }

                if (board.isOver()) {
                    gameOverFlag = true;
                    // gameOverDisplay();
                } else {
                    board.addToHistory();
                    board.slip(2);

                }
                draw(board.getBoard(), size);
            }
        });
        rightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameBoard.repaint();
            }
            public void mouseReleased(MouseEvent e) {

                setScore(board.getScore(), size);
                setGameState(board.isWin(), board.isOver());

                if (board.isWin()) {
                    gameWinFlag = true;
                    // gameWinDisplay();
                }

                if (board.isOver()) {
                    gameOverFlag = true;
                    // gameOverDisplay();
                } else {
                    board.addToHistory();
                    board.slip(3);

                }
                draw(board.getBoard(), size);
            }
        });

        // 添加撤销按钮的监听器
        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                board.undo();
                board.undo();
                draw(board.getBoard(), size);
                setScore(board.getScore(), size);
                setGameState(board.isOver(), board.isWin());
            }
        });

        // 添加Return按钮的监听器
        returnToMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gameStartFlag) {
                     returnToMenuAction();
                } else {
                    cardLayout.show(getContentPane(), "Start");
                }
            }
        });

    }

    // 定义返回到菜单界面行为
    public void returnToMenuAction() {

        if (gameStartFlag) {
            ReturnRequestDialog RRD = new ReturnRequestDialog(this, cardLayout);
            RRD.setVisible(true);
        } else {
            cardLayout.show(getContentPane(), "Start");
            // 重置游戏状态
            resetGame();
        }

    }

    // 定义returnToMenu对话框
    class ReturnRequestDialog extends CustomDialog {
        public ReturnRequestDialog(JFrame frame, CardLayout curCardLayout) {
            super(frame, "Return To Menu", "Are you sure to return to the menu?");

            // 添加保存并退出按钮
            CustomButton saveAndExitButton = new CustomButton("Save And Exit(S)");
            saveAndExitButton.setBounds(50, 100, 100, 30);
            saveAndExitButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // 保存游戏状态
                    Save.saveBoard(board);
                    curCardLayout.show(frame.getContentPane(), "Start");
                    resetGame();
                }
                public void mouseReleased(MouseEvent e) {
                    dispose();
                }
            });
            saveAndExitButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) {
                        // 保存游戏状态
                        Save.saveBoard(board);
                        curCardLayout.show(frame.getContentPane(), "Start");
                        resetGame();
                        dispose();
                    }
                }
            });

            // 添加不保存退出按钮
            CustomButton exitButton = new CustomButton("Exit Without Save(E)");
            exitButton.setBounds(200, 100, 100, 30);
            exitButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    curCardLayout.show(frame.getContentPane(), "Start");
                    resetGame();
                }
                public void mouseReleased(MouseEvent e) {
                    dispose();
                }
            });
            exitButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_E && e.isControlDown()) {
                        curCardLayout.show(getContentPane(), "Start");
                        resetGame();
                        dispose();
                    }
                }
            });

            // 添加取消按钮
            CustomButton cancelButton = new CustomButton("Cancel(C)");
            cancelButton.setBounds(350, 100, 100, 30);
            cancelButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    dispose();
                }
            });
            cancelButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
                        dispose();
                    }
                }
            });

            // 添加按钮到水平Box
            Box verBox = Box.createHorizontalBox();
            verBox.add(saveAndExitButton);
            verBox.add(Box.createHorizontalStrut(10));
            verBox.add(exitButton);
            verBox.add(Box.createHorizontalStrut(10));
            verBox.add(cancelButton);
            verBox.setBounds(0, 150, 500, 200);

            mainBox.add(Box.createVerticalStrut(50));
            mainBox.add(verBox);
        }
    }

    // 重置游戏状态
    public void resetGame() {

        gameStartFlag = false;
        gameWinFlag = false;
        gameOverFlag = false;
        gameBoard.repaint();
        board = null;
        initGameDisplay();
    }

    public static void main(String[] args) {
        new MainApplication();
    }
}
