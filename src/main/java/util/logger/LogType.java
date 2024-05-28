package util.logger;


public enum LogType {

    success("/FXView/success.fxml"), error("/FXView/error.fxml"), warn("/FXView/warn.fxml"), info("/FXView/info.fxml");
    private String fxViewPath;

    private LogType(String fxViewPath) {
        this.fxViewPath = fxViewPath;
    }

    public String getFxViewPath() {
        return fxViewPath;
    }

}
