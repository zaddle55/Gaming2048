package com.AL1S.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @description: 游戏模式类，用于实现不同的游戏模式， 可自定义模式
 * @Author: zaddle
 * @Date: 2024/4/16 20:00
 * @Version: 1.0
 * @Implementation: 1.用于实现不同的游戏模式，例如经典模式、无尽模式、挑战模式等, 用HashMap存储游戏模式
 *                  2.实现addGameMode方法，用于添加自定义游戏模式
 * @History:
 */
public class GameModeFactory {

    // define the game mode constants
    public static final int CLASSIC = 0;
    public static final int ENDLESS = 1;
    public static final int CHALLENGE = 2;

    public static final Map<Integer, Consumer<Board>> gameModes = new HashMap<>();

    static {
        gameModes.put(CLASSIC, (Board board) -> {
            // do something
        });
        gameModes.put(ENDLESS, (Board board) -> {
            // do something
        });
        gameModes.put(CHALLENGE, (Board board) -> {
            // do something
        });
    }
}
