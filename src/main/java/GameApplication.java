import controller.GameUI;
import util.GameModeFactory;
import util.Time;

public class GameApplication{

        public static void main(String[] args) {
//            GameUI.init(0, new int[][]{
//                    // 在这里初始化游戏板
//                    {2, 8, 0, 0, 0 ,8, 8},
//                    {8, 8, 0, 0, 0 ,8, 8},
//                    {8, 8, 0, 0, 0 ,0, 0},
//                    {8, 8, 0, 0, 0 ,8, 8},
//                    {8, 8, 0, 0, 1024 ,8, 8},
//                    {8, 8, 0, 0, 0 ,8, 8},
//                    {8, 8, 0, 0, 0 ,8, 8}
//            }, Time.ZERO);
            GameUI.init(4, GameModeFactory.CLASSIC);
            GameUI.start();
        }
}
