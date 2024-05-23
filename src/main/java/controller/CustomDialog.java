/*
 * @Author: zaddle55 chenyifan20050115@126.com
 * @Date: 2024-05-21 10:04:20
 * @LastEditors: zaddle55 chenyifan20050115@126.com
 * @LastEditTime: 2024-05-23 12:03:34
 * @FilePath: \Gaming2048\src\main\java\controller\CustomDialog.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package controller;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CustomDialog extends Application {

    private AnchorPane root;

    private List<Button> buttonGroup; 

    public CustomDialog(){
        super();
    };

    public AnchorPane getRoot(){
        return this.root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{}

    public void showAndWait(){
        
    }

}
