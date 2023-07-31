package com.ha1ey.CandleDragon.controller;


import cn.hutool.json.JSONObject;
import com.ha1ey.CandleDragon.core.AvIdentifyEntity;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ha1ey
 * @descrition  Utils module
 * @date 2023-07-31 9:18
 **/
public class UtilsController {

    @FXML
    private JFXTextArea tasklistText;
    @FXML
    private TableView<AvIdentifyEntity> avIdentifyTable;
    @FXML
    private TableColumn<AvIdentifyEntity,String> avIDCol;
    @FXML
    private TableColumn<AvIdentifyEntity,String> avProcessIdCol;
    @FXML
    private TableColumn<AvIdentifyEntity,String> avNameCol;





    @FXML
    private void initialize(){
        initAvIdentify();
    }





    private void initAvIdentify(){
        avIDCol.setCellFactory((tableColumn) -> new TableCell<AvIdentifyEntity,String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);
                if (!empty) {
                    this.setText(String.valueOf(this.getIndex() + 1));
                }
            }
        });
        avProcessIdCol.setCellValueFactory(new PropertyValueFactory<>("ProcessName"));
        avNameCol.setCellValueFactory(new PropertyValueFactory<>("AvName"));


        try{
            Reader reader = new FileReader(Objects.requireNonNull(getClass().getClassLoader().getResource("json/av.json")).getFile());
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line=bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            Set<String> keys = jsonObject.keySet();

            tasklistText.textProperty().addListener((observable, oldValue, newValue) -> {
                String tasklist = tasklistText.getText();
                Pattern regex = Pattern.compile("^(.*?)(\\d+)",Pattern.MULTILINE);
                Matcher matcher = regex.matcher(tasklist);
                List<String> processes = new LinkedList<>();
                while (matcher.find()){
                    processes.add(matcher.group(1).trim());
                }
                for (String process : processes){
                    for (String key : keys){
                        if (key.equalsIgnoreCase(process.toLowerCase()) || key.contains(process)){
                            System.out.println(process);
                            AvIdentifyEntity avIdentifyEntity = new AvIdentifyEntity();
                            avIdentifyEntity.setProcessName(process);
                            avIdentifyEntity.setAvName((String) jsonObject.get(key));
                            avIdentifyTable.getItems().add(avIdentifyEntity);
                        }
                    }
                }
            });


        }catch (Exception ignored){
        }


    }

}
