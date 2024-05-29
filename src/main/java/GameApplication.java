import controller.GameUI;
import util.GameModeFactory;
import util.Time;

public class GameApplication{

        public static void main(String[] args) {
            GameUI.init(0, new int[][]{
                    // 在这里初始化游戏板
                    {0, 2, 8, 0},
                    {0, 4, 16, 2},
                    {0, 2048, 64, 16},
                    {2, 128, 2, 0}
            }, Time.ZERO);
//            GameUI.init(4, GameModeFactory.CHALLENGE);
            GameUI.start();
        }
}
