/*
 * The MIT License
 *
 * Copyright 2014 tarchan.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mac.tarchan;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * SumController
 * 
 * @author tarchan
 */
public class SumController implements Initializable {
    
    private static final Logger log = Logger.getLogger(SumController.class.getName());
    private Stage resultDialog;
    private ResultController result;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;
    @FXML
    private TextField restTime;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("Result.fxml"));
            fxml.load();
            Parent root = fxml.getRoot();
            result = fxml.getController();
            resultDialog = new Stage();
            resultDialog.initModality(Modality.WINDOW_MODAL);
            resultDialog.setTitle("結果");
            resultDialog.setScene(new Scene(root));
        } catch (IOException ex) {
            log.log(Level.SEVERE, "FXMLを読み込めません。", ex);
        }
    }

    @FXML
    private void onSum(ActionEvent event) {
        LocalTime a = LocalTime.parse(startTime.getText());
        LocalTime b = LocalTime.parse(endTime.getText());
//        Duration c = Duration.ofSeconds(LocalTime.parse(restTime.getText()).toSecondOfDay());
        Duration c = Duration.between(LocalTime.MIN, LocalTime.parse(restTime.getText()));
        Duration d = Duration.between(a, b).minus(c);
        log.info("開始時刻: " + a);
        log.info("終了時刻: " + b);
        log.info("休憩時間: " + c);
        log.info("勤務時間: " + d.toMinutes() / 60.0);
        result.textProperty().set("" + d.toMinutes() / 60.0);
        resultDialog.showAndWait();
        log.info("閉じた？");
    }
}
