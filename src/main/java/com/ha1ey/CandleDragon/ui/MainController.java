package com.ha1ey.CandleDragon.ui;


import com.ha1ey.CandleDragon.core.PluginPOJOList;
import com.ha1ey.CandleDragon.entity.*;
import com.ha1ey.CandleDragon.plugin.ArgsInfo;
import com.ha1ey.CandleDragon.plugin.Exploit;
import com.ha1ey.CandleDragon.plugin.InfoDetector;
import com.ha1ey.CandleDragon.tools.JarLoader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;


public class MainController {
    public static HashMap<String, Object> setProxy = new HashMap<>();
    public static String charset = "UTF-8";
    JFXRadioButton utf8RadioButton = new JFXRadioButton();
    JFXRadioButton gbkRadioButton = new JFXRadioButton();
    JFXRadioButton isoRadioButton = new JFXRadioButton();
    JFXRadioButton usasciiRadioButton = new JFXRadioButton();
    @FXML
    public Label proxyStatus;
    @FXML
    public TextField infoPluginKeywordSearchTextField;
    @FXML
    public TableView<InfoDetectorPOJO> infoDetectorTableView;
    @FXML
    public TableColumn<InfoDetectorPOJO, String> infoPlugin_ID;
    @FXML
    public TableColumn<InfoDetectorPOJO, String> infoPlugin_PluginName;
    @FXML
    public TableColumn<InfoDetectorPOJO, String> infoPlugin_Author;
    @FXML
    public TableColumn<InfoDetectorPOJO, String> infoPlugin_Version;
    @FXML
    public TableColumn<InfoDetectorPOJO, String> infoPlugin_Descrition;
    @FXML
    public TabPane infoDetectorTabPane;
    @FXML
    public TextField vulPluginKeywordSearchTextField;
    @FXML
    public TableView<VulPOJO> vulTableView;
    @FXML
    public TableColumn<VulPOJO, String> vulPlugin_ID;
    @FXML
    public TableColumn<VulPOJO, String> vulPlugin_Name;
    @FXML
    public TableColumn<VulPOJO, String> vulplugin_VulName;
    @FXML
    public TableColumn<VulPOJO, String> vulPlugin_AuthorName;
    @FXML
    public TableColumn<VulPOJO, String> vulPlugin_Version;
    @FXML
    public TableColumn<VulPOJO, String> vulPlugin_VersionScope;
    @FXML
    public TableColumn<VulPOJO, String> vulPlugin_DisclosureTime;
    @FXML
    public TableColumn<VulPOJO, String> vulPlugin_Description;
    @FXML
    public TabPane mainTabPane;
    @FXML
    public TabPane vulExpTabPane;
    @FXML
    public TabPane vulPocTabPane;

    //以下是信息探测模块定义的属性
    JFXButton infoDetectorStartButton = new JFXButton();          //信息探测开始按钮
    TextField infoDetectorTargetAddressTextField = new TextField();         //信息探测地址栏
    TextArea infoDetectorArgsTextArea = new TextArea();
    TextArea infoDetectorResultTextArea = new TextArea();
    TextArea infoDetectorArgsResultTextArea = new TextArea();

    int infoDetectorIndex;


    //以下是漏洞利用模块定义的属性
    //POC模块
    JFXButton pocScanButton = new JFXButton();
    int vulIndex;
    JFXTextArea pocTargetAddressTextArea = new JFXTextArea();
    JFXTextArea pocResultTextArea = new JFXTextArea();
    TableView<ScanResultPOJO> pocResultTableView;
    ScanResultPOJO scanResultPOJO = new ScanResultPOJO();

    //EXP模块
    JFXButton vulExpStartButton = new JFXButton();
    TextField vulExpTargetAddressTextField = new TextField();
    TextArea vulExpArgsTextArea = new TextArea();
    TextArea vulExpResultTextArea = new TextArea();

    @FXML
    public void initialize() {
        new JarLoader().mainLoader();
        initInfoDetectorPlugin();
        vulPlugin();
        infoKeywordSearch();
        vulKeywordSearch();

    }


    //信息探测插件加载
    private void initInfoDetectorPlugin() {
        //插件序号递增实现
        infoPlugin_ID.setCellFactory((tableColumn) -> new TableCell<InfoDetectorPOJO, String>() {
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
        infoPlugin_PluginName.setCellValueFactory(new PropertyValueFactory<>("InfoDetectorPluginName"));
        infoPlugin_Author.setCellValueFactory(new PropertyValueFactory<>("InfoDetectorPluginAuthor"));
        infoPlugin_Version.setCellValueFactory(new PropertyValueFactory<>("InfoDetectorPluginVersion"));
        infoPlugin_Descrition.setCellValueFactory(new PropertyValueFactory<>("InfoDetectorPluginDescription"));
        infoDetectorTableView.getItems().addAll(PluginPOJOList.infoDetectorPOJOList);

        //添加双击事件
        infoDetectorTableView.setOnMouseClicked(mouseEvent -> {
            infoDetectorIndex = infoDetectorTableView.getSelectionModel().getSelectedIndex();
            if (mouseEvent.getClickCount() == 2 && !(infoDetectorTableView.getColumns().isEmpty())) {
                try {
                    //跳转探测页面
                    Tab infoDetectorTab = new Tab();
                    infoDetectorTab.setText(infoDetectorTableView.getSelectionModel().getSelectedItems().get(0).getInfoDetectorPluginName());
                    AnchorPane infoDetectorTabAnchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/InfoDetector/InfoDetectorTabAnchorPane.fxml")));
                    infoDetectorTab.setContent(infoDetectorTabAnchorPane);
                    infoDetectorTabPane.getTabs().add(infoDetectorTab);
                    //信息探测开始按钮
                    infoDetectorStartButton = (JFXButton) infoDetectorTabAnchorPane.lookup("#infoDetectorStartButton");
                    //信息探测插件URL地址按钮
                    infoDetectorTargetAddressTextField = (TextField) infoDetectorTabAnchorPane.lookup("#infoDetectorTargetAddressTextField");
                    TabPane infoDetectorResultTabPane = (TabPane) infoDetectorTabAnchorPane.lookup("#infoDetectorResultTabPane");
                    List<InfoDetector> infoDetectorList = PluginPOJOList.infoDetectorPOJOList.get(infoDetectorIndex).getInfoDetector();
                    HashMap<String, InfoDetector> infoDetectorHashMap = new HashMap<>();

                    for (InfoDetector infoDetector : infoDetectorList) {
                        Tab infoDetectorResultTab = new Tab();
                        //设置信息探测插件的TabTitle
                        infoDetectorHashMap.put(infoDetector.getInfoDetectorTabTitle(), infoDetector);
                        infoDetectorResultTab.setText(infoDetector.getInfoDetectorTabTitle());
                        infoDetectorResultTabPane.getTabs().add(infoDetectorResultTab);
                        //取出当前选择表格的参数列表，用于下面设置TextArea的预设Arg值
                        ArgsUsagePOJO infoDetectorArgsUsagePOJO = (ArgsUsagePOJO) infoDetector.getInfoDetectorCustomArgs();
                        AnchorPane infoDetectorTabResultAnchorPane;
                        //根据插件是否设置参数，选择加载信息探测结果页面
                        ArgsInfoPOJO argsInfoPOJO;
                        if (infoDetectorArgsUsagePOJO == null) {
                            infoDetectorTabResultAnchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/InfoDetector/InfoDetectorTabResultAnchorPane.fxml")));
                            infoDetectorResultTextArea = (TextArea) infoDetectorTabResultAnchorPane.lookup("#infoDetectorResultTextArea");
                            infoDetectorResultTab.setContent(infoDetectorTabResultAnchorPane);
                        } else {
                            infoDetectorTabResultAnchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/InfoDetector/InfoDetectorTabArgsAnchorPane.fxml")));
                            infoDetectorArgsTextArea = (TextArea) infoDetectorTabResultAnchorPane.lookup("#infoDetectorArgsTextArea");
                            infoDetectorArgsTextArea.setWrapText(true);

                            infoDetectorResultTextArea = (TextArea) infoDetectorTabResultAnchorPane.lookup("#infoDetectorResultTextArea");
                            infoDetectorResultTab.setContent(infoDetectorTabResultAnchorPane);

                            for (ArgsInfo argsInfo : infoDetectorArgsUsagePOJO.getArgsInfoList()) {
                                argsInfoPOJO = (ArgsInfoPOJO) argsInfo;
                                infoDetectorArgsTextArea.appendText(argsInfoPOJO.getName() + "=\n");
                            }
                        }

                        mainTabPane.getSelectionModel().select(1);


                        infoDetectorStartButton.setOnAction(event -> {
                            //设置每个参数对应的value值
                            Map<String, Object> args = new HashMap<>();
                            ResultOutputPOJO resultOutputPOJO = new ResultOutputPOJO();
                            TargetPOJO targetPOJO = new TargetPOJO();
                            InfoDetector infoDetect = infoDetectorHashMap.get(infoDetectorResultTabPane.getSelectionModel().getSelectedItem().getText());
                            ArgsUsagePOJO argsUsagePOJO = (ArgsUsagePOJO) infoDetect.getInfoDetectorCustomArgs();
                            AnchorPane anchorPane = (AnchorPane) infoDetectorResultTabPane.getSelectionModel().getSelectedItem().getContent();
                            TextArea infoResultTextArea = (TextArea) anchorPane.lookup("#infoDetectorResultTextArea");
                            infoResultTextArea.setWrapText(true);
                            if (argsUsagePOJO != null) {
                                String argsText = infoDetectorArgsTextArea.getText();
                                for (ArgsInfo argsInfo : argsUsagePOJO.getArgsInfoList()) {
                                    ArgsInfoPOJO finalArgsInfoPOJO = (ArgsInfoPOJO) argsInfo;
                                    String[] argsValues = argsText.split(finalArgsInfoPOJO.getName() + "=");
                                    String value = argsValues[1].substring(0, argsValues[1].indexOf("\n"));
                                    args.put(finalArgsInfoPOJO.getName(), value);
                                }
                            }
                            try {
                                targetPOJO.setAddress(infoDetectorTargetAddressTextField.getText());
                                LinkedHashMap<String, String> infos = infoDetect.doDetect(targetPOJO, args, resultOutputPOJO);
                                infoResultTextArea.appendText(
                                        "[>]开始探测>>>>>>>>" + infoDetect.getInfoDetectorTabTitle() + "\n\n" +
                                                String.join("\n", resultOutputPOJO.getSuccessList()) + "\n" +
                                                String.join("\n", resultOutputPOJO.getRawList()) + "\n" +
                                                String.join("\n", resultOutputPOJO.getDebugList()) + "\n" +
                                                String.join("\n", resultOutputPOJO.getErrorList()) + "\n" +
                                                String.join("\n", resultOutputPOJO.getFailList()) + "\n" +
                                                String.join("\n", resultOutputPOJO.getWarningList()) + "\n" +
                                                "——————————————————————————————\n" +
                                                targetPOJO.getAddress() + "\t|\t" + infos.get(targetPOJO.getAddress()) + "\n" +
                                                "——————————————————————————————\n" +
                                                "<<<<<<<<" + infoDetect.getInfoDetectorTabTitle() + "  |  探测结束>>>>>>>>\n\n"
                                );
                            } catch (Throwable ignored) {
                            }


                        });
                    }
                } catch (Throwable ignored) {
                }
            }
        });

    }


    //漏洞插件加载
    private void vulPlugin() {
        //插件序号递增实现
        vulPlugin_ID.setCellFactory((tableColumn) -> new TableCell<VulPOJO, String>() {
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
        vulPlugin_Name.setCellValueFactory(new PropertyValueFactory<>("VulPluginName"));
        vulPlugin_AuthorName.setCellValueFactory(new PropertyValueFactory<>("VulPluginAuthor"));
        vulPlugin_Version.setCellValueFactory(new PropertyValueFactory<>("VulPluginVersion"));
        vulPlugin_Description.setCellValueFactory(new PropertyValueFactory<>("VulDescription"));
        vulTableView.getItems().addAll(PluginPOJOList.vulPOJOList);


        //右击事件
        vulTableView.setOnMouseClicked(event2 -> {
            vulIndex = vulTableView.getSelectionModel().getSelectedIndex();
            if (event2.getButton() == MouseButton.SECONDARY) {
                Node node = event2.getPickResult().getIntersectedNode();
                //判断该插件是否编写了poc
                if (PluginPOJOList.vulPOJOList.get(vulIndex).getPoc() == null) {
                    GlobalMenu.getInstance().getItems().get(0).setDisable(true);
                }
                //判断该插件是否编写了exp
                if (PluginPOJOList.vulPOJOList.get(vulIndex).getExploit() == null) {
                    GlobalMenu.getInstance().getItems().get(1).setDisable(true);
                }
                //将右击的菜单显示出来
                GlobalMenu.getInstance().show(node, event2.getScreenX(), event2.getScreenY());

                //点击右击菜单的poc按钮事件
                GlobalMenu.getInstance().getItems().get(0).setOnAction(event -> {
                    try {
                        //点击发送到POC后跳转显示的页面
                        Tab vulPocTab = new Tab();
                        vulPocTab.setText(vulTableView.getSelectionModel().getSelectedItems().get(0).getVulName());
                        AnchorPane vulPocTabAnchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/POC/VulPocTabAnchorPane.fxml")));
                        pocScanButton = (JFXButton) vulPocTabAnchorPane.lookup("#pocScanButton");
                        SplitPane pocTargetSplitPane = (SplitPane) vulPocTabAnchorPane.lookup("#pocTargetSplitPane");
                        //POC 扫描地址区域
                        pocTargetAddressTextArea = (JFXTextArea) pocTargetSplitPane.getItems().get(0);
                        //扫描结果输出区域
                        SplitPane pocResultSplitPane = (SplitPane) pocTargetSplitPane.getItems().get(1);
                        pocResultTextArea = (JFXTextArea) pocResultSplitPane.getItems().get(1);
                        pocResultTableView = (TableView<ScanResultPOJO>) pocResultSplitPane.getItems().get(0);
                        TableColumn<ScanResultPOJO, String> poc_Target = (TableColumn<ScanResultPOJO, String>) pocResultTableView.getColumns().get(0);
                        TableColumn<ScanResultPOJO, String> poc_isVul = (TableColumn<ScanResultPOJO, String>) pocResultTableView.getColumns().get(1);
                        TableColumn<ScanResultPOJO, String> poc_Msg = (TableColumn<ScanResultPOJO, String>) pocResultTableView.getColumns().get(2);
                        TableColumn<ScanResultPOJO, String> poc_Date = (TableColumn<ScanResultPOJO, String>) pocResultTableView.getColumns().get(3);

                        poc_Target.setCellValueFactory(new PropertyValueFactory<>("Target"));
                        poc_isVul.setCellValueFactory(new PropertyValueFactory<>("Vul"));
                        poc_Msg.setCellValueFactory(new PropertyValueFactory<>("Msg"));
                        poc_Date.setCellValueFactory(new PropertyValueFactory<>("Time"));


                        vulPocTab.setContent(vulPocTabAnchorPane);
                        vulPocTabPane.getTabs().add(vulPocTab);
                        vulExpTabPane.getSelectionModel().selectLast();
                        mainTabPane.getSelectionModel().select(2);

                        //加载poc结果表格
                        pocScanStart();


                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                });


                //点击右击菜单的exp按钮事件
                GlobalMenu.getInstance().getItems().get(1).setOnAction(event1 -> {
                    try {
                        //点击发送到EXP跳转到漏洞利用页面
                        Tab vulExpTab = new Tab();
                        vulExpTab.setText(vulTableView.getSelectionModel().getSelectedItems().get(0).getVulPluginName());
                        AnchorPane vulExpTabAnchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Vul/VulExpTabAnchorPane.fxml")));
                        vulExpTab.setContent(vulExpTabAnchorPane);
                        vulExpTabPane.getTabs().add(vulExpTab);
                        vulExpTabPane.getSelectionModel().selectLast();
                        mainTabPane.getSelectionModel().select(3);

                        //漏洞利用开始按钮
                        vulExpStartButton = (JFXButton) vulExpTabAnchorPane.lookup("#vulExpStartButton");
                        //漏洞利用插件URL地址按钮
                        vulExpTargetAddressTextField = (TextField) vulExpTabAnchorPane.lookup("#vulExpTargetAddressTextField");
                        //加载漏洞利用页面的子TabPane页面
                        TabPane vulResultTabPane = (TabPane) vulExpTabAnchorPane.lookup("#vulExpResultTabPane");
                        List<Exploit> exploitList = PluginPOJOList.vulPOJOList.get(vulIndex).getExploit();


                        HashMap<String, Exploit> exploitHashMap = new HashMap<>();
                        for (Exploit exp : exploitList) {
                            Tab vulExpResultTab = new Tab();
                            exploitHashMap.put(exp.getExploitTabTitle(), exp);
                            vulResultTabPane.getTabs().add(vulExpResultTab);
                            //设置漏洞利用插件的TabTitle
                            vulExpResultTab.setText(exp.getExploitTabTitle());
                            //取出当前选择表格的参数列表，用于下面设置TextArea的预设Arg值
                            ArgsUsagePOJO vulExpArgsUsagePOJO = (ArgsUsagePOJO) exp.getExploitCustomArgs();

                            AnchorPane vulExpTabResultAnchorPane;
                            //根据插件是否设置参数，选择加载漏洞利用结果页面
                            ArgsInfoPOJO argsInfoPOJO;

                            if (vulExpArgsUsagePOJO == null) {
                                vulExpTabResultAnchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/vul/VulExpTabResultAnchorPane.fxml")));
                                vulExpResultTextArea = (TextArea) vulExpTabResultAnchorPane.lookup("#vulExpResultTextArea");
                                vulExpResultTab.setContent(vulExpTabResultAnchorPane);
                            } else {
                                vulExpTabResultAnchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/vul/VulExpTabArgsAnchorPane.fxml")));
                                vulExpArgsTextArea = (TextArea) vulExpTabResultAnchorPane.lookup("#vulExpArgsTextArea");
                                vulExpArgsTextArea.setWrapText(true);

                                vulExpResultTextArea = (TextArea) vulExpTabResultAnchorPane.lookup("#vulExpResultTextArea");
                                vulExpResultTab.setContent(vulExpTabResultAnchorPane);


                                for (ArgsInfo argsInfo : vulExpArgsUsagePOJO.getArgsInfoList()) {
                                    argsInfoPOJO = (ArgsInfoPOJO) argsInfo;
                                    vulExpArgsTextArea.appendText(argsInfoPOJO.getName() + "=\n");
                                }
                            }
                        }

                        vulExpStartButton.setOnAction(event -> {
                            TargetPOJO targetPOJO = new TargetPOJO();
                            //设置每个参数对应的value值
                            Map<String, Object> args = new HashMap<>();
                            ResultOutputPOJO resultOutputPOJO = new ResultOutputPOJO();
                            Exploit exploit = exploitHashMap.get(vulResultTabPane.getSelectionModel().getSelectedItem().getText());
                            ArgsUsagePOJO argsUsagePOJO = (ArgsUsagePOJO) exploit.getExploitCustomArgs();
                            AnchorPane resultAnchorPane = (AnchorPane) vulResultTabPane.getSelectionModel().getSelectedItem().getContent();
                            TextArea resultTextArea = (TextArea) resultAnchorPane.lookup("#vulExpResultTextArea");
                            resultTextArea.setWrapText(true);
                            if (argsUsagePOJO != null) {
                                String argsText = vulExpArgsTextArea.getText();
                                for (ArgsInfo argsInfo : argsUsagePOJO.getArgsInfoList()) {
                                    ArgsInfoPOJO finalArgsInfoPOJO = (ArgsInfoPOJO) argsInfo;
                                    String[] argsValues = argsText.split(finalArgsInfoPOJO.getName() + "=");
                                    String value = argsValues[1].substring(0, argsValues[1].indexOf("\n"));
                                    args.put(finalArgsInfoPOJO.getName(), value);
                                }
                            }
                            try {
                                targetPOJO.setAddress(vulExpTargetAddressTextField.getText());
                                exploit.doExploit(targetPOJO, args, resultOutputPOJO);
                                resultTextArea.appendText(
                                        exploit.getExploitTabTitle() + "\t[>]开始>>>>>>>>\n\n" +
                                                String.join("\n", resultOutputPOJO.getSuccessList()) + "\n" +
                                                String.join("\n", resultOutputPOJO.getRawList()) + "\n" +
                                                String.join("\n", resultOutputPOJO.getDebugList()) + "\n" +
                                                String.join("\n", resultOutputPOJO.getErrorList()) + "\n" +
                                                String.join("\n", resultOutputPOJO.getFailList()) + "\n" +
                                                String.join("\n", resultOutputPOJO.getWarningList()) + "\n" +
                                                "<<<<<<<<" + exploit.getExploitTabTitle() + "  |  结束>>>>>>>>\n\n"
                                );
                            } catch (Throwable ignored) {
                            }
                        });
                    } catch (Throwable ignored) {
                    }
                });
            }
        });

    }


    private void pocScanStart() {
        pocScanButton.setOnAction(event -> {
            String dateFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            String time = simpleDateFormat.format(new Date());
            pocResultTableView.getItems().clear();
            pocResultTextArea.setText("");
            String[] strings = pocTargetAddressTextArea.getText().split("\n");
            for (String targetAddress : strings) {
                try {
                    TargetPOJO targetPOJO = new TargetPOJO();
                    ResultOutputPOJO resultOutputPOJO = new ResultOutputPOJO();
                    targetPOJO.setAddress(targetAddress);
                    scanResultPOJO = (ScanResultPOJO) PluginPOJOList.vulPOJOList.get(vulIndex).getPoc().doCheck(targetPOJO, resultOutputPOJO);
                    scanResultPOJO.setTime(time);
                    pocResultTableView.getItems().addAll(scanResultPOJO);
                    pocResultTextArea.appendText(
                            "[>]开始扫描>>>>>>>>" + targetAddress + "\n\n" +
                                    String.join("\n", resultOutputPOJO.getSuccessList()) + "\n" +
                                    String.join("\n", resultOutputPOJO.getRawList()) + "\n" +
                                    String.join("\n", resultOutputPOJO.getDebugList()) + "\n" +
                                    String.join("\n", resultOutputPOJO.getErrorList()) + "\n" +
                                    String.join("\n", resultOutputPOJO.getFailList()) + "\n" +
                                    String.join("\n", resultOutputPOJO.getWarningList()) + "\n" +
                                    "<<<<<<<<" + targetAddress + "  |  扫描结束>>>>>>>>\n\n"
                    );
                } catch (Throwable ignored) {
                }
            }

        });
    }


    @FXML
    private void getAbout() {
        URI uri = URI.create("https://github.com/HHa1ey");
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void proxy() throws IOException {
        AnchorPane proxyAnchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Proxy.fxml")));
        ToggleGroup toggleGroup = new ToggleGroup();
        TextField field_ip = (TextField) proxyAnchorPane.lookup("#autoproxy_ip");
        TextField field_port = (TextField) proxyAnchorPane.lookup("#autoproxy_port");
        TextField field_user = (TextField) proxyAnchorPane.lookup("#autoproxy_user");
        TextField field_pass = (TextField) proxyAnchorPane.lookup("#autoproxy_pass");
        JFXRadioButton rb_enable = (JFXRadioButton) proxyAnchorPane.lookup("#proxy_enable");
        JFXRadioButton rb_disable = (JFXRadioButton) proxyAnchorPane.lookup("#proxy_disable");
        JFXComboBox<String> proxy_cbb = (JFXComboBox<String>) proxyAnchorPane.lookup("#choose_proxy");
        JFXButton cancel_button = (JFXButton) proxyAnchorPane.lookup("#cancel_button");
        JFXButton save_button = (JFXButton) proxyAnchorPane.lookup("#save_button");
        String proxyIP = field_ip.getText().trim();
        String proxyPort = field_port.getText().trim();
        String proxyUsername = field_user.getText().trim();
        String proxyPassword = field_pass.getText().trim();
        Alert dialog = new Alert(Alert.AlertType.NONE);
        dialog.setResizable(true);
        dialog.setTitle("设置代理");
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest((e) -> {
            window.hide();
        });

        //开启or关闭
        rb_enable.setToggleGroup(toggleGroup);
        rb_disable.setSelected(true);
        rb_disable.setToggleGroup(toggleGroup);


        //代理类型选择
        proxy_cbb.setItems(FXCollections.observableArrayList("HTTP", "SOCKS"));
        proxy_cbb.getSelectionModel().select(0);

        if (setProxy.get("proxy") != null) {
            Proxy curr_proxy = (Proxy) setProxy.get("proxy");
            String proxy_info = curr_proxy.address().toString();
            String[] info = proxy_info.split(":");
            String ipaddr = info[0].replace("/", "");
            String port = info[1];
            field_port.setText(port);
            field_ip.setText(ipaddr);
            rb_enable.setSelected(true);
        } else {
            rb_disable.setSelected(true);
        }


        //保存代理参数
        save_button.setOnAction(event -> {
            if (rb_disable.isSelected()) {
                setProxy.put("proxy", null);
                this.proxyStatus.setText("代理模式：关闭\t");
            } else {
                if (!proxyUsername.equals("")) {
                    Authenticator.setDefault(new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(proxyUsername, proxyPassword.toCharArray());
                        }
                    });
                } else {
                    Authenticator.setDefault(null);
                }
                setProxy.put("username", proxyUsername);
                setProxy.put("password", proxyPassword);
                InetSocketAddress socketAddress = new InetSocketAddress(proxyIP, Integer.parseInt(proxyPort));
                Proxy proxy;
                if (proxy_cbb.getValue().equals("HTTP")) {
                    proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
                    setProxy.put("proxy", proxy);
                } else if (proxy_cbb.getValue().equals("SOCKS")) {
                    proxy = new Proxy(Proxy.Type.SOCKS, socketAddress);
                    setProxy.put("proxy", proxy);
                }
                field_ip.setText(proxyIP);
                field_port.setText(proxyPort);
                this.proxyStatus.setText("代理模式：开启\t | " + proxyIP + ":" + proxyPort);
            }
            dialog.getDialogPane().getScene().getWindow().hide();
        });

        //不保存
        cancel_button.setOnAction(event -> {
            dialog.getDialogPane().getScene().getWindow().hide();
        });
        dialog.getDialogPane().setContent(proxyAnchorPane);
        dialog.showAndWait();
    }

    @FXML
    public void setCharset() throws IOException {
        AnchorPane charsetAnchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Charset.fxml")));
        utf8RadioButton = (JFXRadioButton) charsetAnchorPane.lookup("#utf8RadioButton");
        gbkRadioButton = (JFXRadioButton) charsetAnchorPane.lookup("#gbkRadioButton");
        isoRadioButton = (JFXRadioButton) charsetAnchorPane.lookup("#isoRadioButton");
        usasciiRadioButton = (JFXRadioButton) charsetAnchorPane.lookup("#usasciiRadioButton");
        JFXButton saveCharsetButton = (JFXButton) charsetAnchorPane.lookup("#saveCharsetButton");
        ToggleGroup charsetGroup = new ToggleGroup();
        utf8RadioButton.setToggleGroup(charsetGroup);
        utf8RadioButton.setSelected(true);
        gbkRadioButton.setToggleGroup(charsetGroup);
        isoRadioButton.setToggleGroup(charsetGroup);
        usasciiRadioButton.setToggleGroup(charsetGroup);

        Alert dialog = new Alert(Alert.AlertType.NONE);
        dialog.setResizable(true);
        dialog.setTitle("设置编码方式");
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest((e) -> {
            window.hide();
        });
        saveCharsetButton.setOnAction(event -> {
            if (isoRadioButton.isSelected()) {
                charset = "ISO-8859-1";
            } else if (usasciiRadioButton.isSelected()) {
                charset = "US-ASCII";
            } else if (gbkRadioButton.isSelected()) {
                charset = "GBK";
            } else {
                charset = "UTF-8";
            }
            dialog.getDialogPane().getScene().getWindow().hide();
        });
        dialog.getDialogPane().setContent(charsetAnchorPane);
        dialog.showAndWait();

    }

    private void infoKeywordSearch() {
        FilteredList<InfoDetectorPOJO> filteredList = new FilteredList<>(infoDetectorTableView.getItems(), p -> true);
        infoPluginKeywordSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getInfoDetectorPluginName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                } else if (String.valueOf(myObject.getInfoDetectorPluginDescription()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getInfoDetectorPluginAuthor()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getInfoDetectorPluginVersion()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<InfoDetectorPOJO> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(infoDetectorTableView.comparatorProperty());
        infoDetectorTableView.setItems(sortedList);
    }

    private void vulKeywordSearch() {
        FilteredList<VulPOJO> filteredList = new FilteredList<>(vulTableView.getItems(), p -> true);
        vulPluginKeywordSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getVulPluginName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                } else if (String.valueOf(myObject.getVulDescription()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getVulId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getVulName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getVulProduct()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        SortedList<VulPOJO> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(vulTableView.comparatorProperty());
        vulTableView.setItems(sortedList);
    }

}
