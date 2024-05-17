import controller.GameUI;
import util.GameModeFactory;

public class GameApplication{

        public static void main(String[] args) {
//            GameUI.init(0, new int[][]{
//                    // 在这里初始化游戏板
//                    {2, 8, 0, 0, 0 ,8, 8},
//                    {8, 8, 0, 0, 0 ,8, 8},
//                    {8, 8, 0, 0, 0 ,0, 0},
//                    {8, 8, 0, 0, 0 ,8, 8},
//                    {8, 8, 0, 0, 0 ,8, 8},
//                    {8, 8, 0, 0, 0 ,8, 8},
//                    {8, 8, 0, 0, 0 ,8, 8}
//            });
            GameUI.init(4, GameModeFactory.CHALLENGE);
            GameUI.start();
        }
}
