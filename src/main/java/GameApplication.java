import controller.GameUI;

public class GameApplication{

        public static void main(String[] args) {
//            GameUI.init(0, new int[][]{
//                    // 随机生成失败2048
//                    {2, 4, 8, 16},
//                    {4, 8, 1024, 1024},
//                    {8, 16, 1024, 1024},
//                    {16, 4, 1024, 1024}
//            });
            GameUI.init(4, 0);
            GameUI.start();
        }
}
