import controller.GameUI;
import util.GameModeFactory;

public class GameApplication{

        public static void main(String[] args) {
            GameUI.init(0, new int[][]{
                    // 随机生成失败2048
                    {2, 4, 8, 16},
                    {0, 0, 0, 16},
                    {8, 8, 8, 8},
                    {0, 4, 1024, 0}
            });
//            GameUI.init(4, GameModeFactory.CHALLENGE);
            GameUI.start();
        }
}
