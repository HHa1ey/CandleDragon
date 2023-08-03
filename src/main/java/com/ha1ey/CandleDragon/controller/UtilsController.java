package com.ha1ey.CandleDragon.controller;


import cn.hutool.json.JSONObject;
import com.ha1ey.CandleDragon.common.Base64Transform;
import com.ha1ey.CandleDragon.common.WeaverVersionDecrypt;
import com.ha1ey.CandleDragon.core.AvIdentifyEntity;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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
    private JFXTextField weaverDecText;
    @FXML
    private JFXTextArea weaverDecResultText;

    @FXML
    private JFXTextArea str;
    @FXML
    private JFXTextArea base64Str;




    @FXML
    private void initialize(){
        initAvIdentify();
        initWeaverDecrypt();
    }



    private void initWeaverDecrypt(){
        weaverDecText.textProperty().addListener((observable, oldValue, newValue) -> {
            String result = new WeaverVersionDecrypt().decrypt(weaverDecText.getText());
            weaverDecResultText.setWrapText(true);
            weaverDecResultText.setText(result);
        });
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


    @FXML
    public void base64Encode(){
        String str = this.str.getText().trim();
        String base64str = new Base64Transform().base64Encode(str.getBytes());
        this.base64Str.setText(base64str);
    }

    @FXML
    public void base64Decode(){
        String base64Str = this.base64Str.getText().trim();
        byte[] bytes = new Base64Transform().base64Decode(base64Str);
        String str =new String(bytes);
        this.str.setText(str);
    }

    @FXML
    public void classFileToBase64() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择Class文件");
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(new Stage());
        byte[] base64Bytes = new byte[0];
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] bytes = new byte[1024];
            int n;
            while ((n = fis.read(bytes)) != -1) {
                bos.write(bytes, 0, n);
            }
            fis.close();
            bos.flush();
            bos.close();
            base64Bytes = bos.toByteArray();
        }catch (Exception ignored){
        }
        String classBase64Str = new Base64Transform().base64Encode(base64Bytes);
        this.base64Str.setText(classBase64Str);
    }

    @FXML
    public void base64ToClassFile(){
        String base64str = this.base64Str.getText().trim();
        byte[] bytes = Base64.getDecoder().decode(base64str.getBytes());
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("class files (*.class)", "*.class");
        fileChooser.setTitle("选择导出Class文件路径");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file =fileChooser.showSaveDialog(new Stage());
        try{
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            bos.flush();
            bos.close();
        } catch (IOException ignored) {
        }
    }
}
