package com.ha1ey.CandleDragon.controller;

import com.ha1ey.CandleDragon.common.CommonUtils;
import com.ha1ey.CandleDragon.common.ComponentUtil;
import com.ha1ey.CandleDragon.common.JarLoader;
import com.ha1ey.CandleDragon.common.ProxyHandler;
import com.ha1ey.CandleDragon.core.*;
import com.ha1ey.CandleDragon.plugin.ArgsInfo;
import com.ha1ey.CandleDragon.plugin.Exploit;
import com.ha1ey.CandleDragon.plugin.Poc;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.Paragraph;
import org.reactfx.collection.LiveList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ha1ey.CandleDragon.common.CommonUtils.pluginList;


public class HomeController {
    @FXML
    private JFXListView<PluginImpl> pluginListView;
    @FXML
    private JFXTextField pluginKeywordsText;
    @FXML
    private JFXTextField exploitTargetAddressText;
    @FXML
    private JFXTabPane exploitTabPane;
    @FXML
    private JFXListView<PluginImpl> scanPocList;
    @FXML
    private SplitPane targetInfoSplitPane;
    CodeArea pocTargetAddressTextArea;
    @FXML
    private TableView<ResultImpl> pocResultTable;
    @FXML
    private TableColumn<ResultImpl, String> pocResPluginNameCol;
    @FXML
    private TableColumn<ResultImpl, String> pocResTargetCol;
    @FXML
    private TableColumn<ResultImpl, String> isvulCol;
    @FXML
    private TableColumn<ResultImpl, String> pocMsgCol;
    @FXML
    private TableColumn<ResultImpl, String> pocTimeCol;
    @FXML
    private JFXTabPane pocexpTabPane;
    @FXML
    private JFXTextArea pocResultText;


    private JFXTextArea exploitResutlText;
    private final HashMap<String, Exploit> expMap = new HashMap<>();
    Font defaultFont = Font.getDefault();


    //init
    @FXML
    private void initialize() {
        initPlugin();
        initPocPane();
        initSearchKeywords();
    }


    //Load Plugin and Display pluginlist
    public void initPlugin() {
        JarLoader.loadJar();
        pluginListView.setItems(CommonUtils.pluginList);
        pluginListView.setCellFactory(param -> new ListCell<PluginImpl>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(PluginImpl item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    String pluginInfo = "Plugin Name：" + item.getPluginName() + "\n" +
                            "Plugin Version：" + item.getPluginVersion() + "\n" +
                            "Author：" + item.getPluginAuthor() + "\n" +
                            "Plugin Description：" + item.getDescription() + "\n" +
                            "Vul Name：" + item.getVulName() + "\n" +
                            "Vul Id：" + item.getVulId() + "\n" +
                            "Vul Category：" + item.getVulCategory() + "\n" +
                            "Vul Disclosure Time：" + item.getVulDisclosureTime() + "\n" +
                            "Vul Scope：" + item.getVulScope() + "\n" +
                            "Vul Product：" + item.getProduct();
                    setText(item.getPluginName());
                    tooltip.setText(pluginInfo);
                    setOnMouseEntered(event -> {
                        tooltip.show(this, event.getScreenX() + 10, event.getScreenY() + 10);
                    });

                    setOnMouseExited(event -> {
                        tooltip.hide();
                    });


                }
            }
        });

        pluginListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }


    private void initSearchKeywords() {
        ObservableList<PluginImpl> items = FXCollections.observableArrayList();
        FilteredList<PluginImpl> filteredItems = new FilteredList<>(items);
        items.addAll(pluginList);
        pluginListView.setItems(filteredItems);
        pluginKeywordsText.setOnKeyReleased(searchKeywordsEvent -> {
            String filterText = pluginKeywordsText.getText().toLowerCase();
            filteredItems.setPredicate(item ->
                    (filterText.isEmpty() || item.getPluginName() != null && item.getPluginName().toLowerCase().contains(filterText)) ||
                            (filterText.isEmpty() || item.getPluginAuthor() != null && item.getPluginAuthor().toLowerCase().contains(filterText)) ||
                            (filterText.isEmpty() || item.getProduct() != null && item.getProduct().toLowerCase().contains(filterText)) ||
                            (filterText.isEmpty() || item.getVulId() != null && item.getVulId().toLowerCase().contains(filterText)) ||
                            (filterText.isEmpty() || item.getVulName() != null && item.getVulName().toLowerCase().contains(filterText)) ||
                            (filterText.isEmpty() || item.getVulDisclosureTime() != null && item.getVulDisclosureTime().toLowerCase().contains(filterText))
            );
        });
    }


    //monitor pluginlist
    @FXML
    public void monitorPluginList(MouseEvent event) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem toPocScan = new MenuItem("To Poc");
        MenuItem toExploit = new MenuItem("To Exploit");
        contextMenu.getItems().add(toPocScan);
        contextMenu.getItems().add(toExploit);
        if (event.getButton() == MouseButton.SECONDARY && pluginListView.getSelectionModel().getSelectedItems().size() > 1) {
            toExploit.setDisable(true);
            ListCell<PluginImpl> cell = getClickedCell(event);
            if (cell.getText() != null) {
                contextMenu.show(cell, event.getScreenX(), event.getScreenY());
            }
        } else if (event.getButton() == MouseButton.SECONDARY && pluginListView.getSelectionModel().getSelectedItems().size() == 1) {
            if (pluginListView.getSelectionModel().getSelectedItem().getExploits() == null) {
                toExploit.setDisable(true);
            }
            ListCell<PluginImpl> cell = getClickedCell(event);
            if (cell.getText() != null) {
                contextMenu.show(cell, event.getScreenX(), event.getScreenY());
            }
        }

        toPocScan.setOnAction(toPocScanEvent -> {
            pocexpTabPane.getSelectionModel().select(0);
            List<PluginImpl> selectedPlugins = pluginListView.getSelectionModel().getSelectedItems();
            for (PluginImpl plugin : selectedPlugins) {
                if (!scanPocList.getItems().contains(plugin)) {
                    scanPocList.getItems().add(plugin);
                }

            }
        });


        toExploit.setOnAction(toExploitEvent -> {
            pocexpTabPane.getSelectionModel().select(1);
            List<Exploit> exploits = pluginListView.getSelectionModel().getSelectedItem().getExploits();
            for (Exploit exploit : exploits) {
                Tab expTab = new Tab();
                expTab.setClosable(true);
                expTab.setText(exploit.setExploitTitle());
                expTab.setId(String.valueOf(exploit));
                expMap.put(String.valueOf(exploit), exploit);
                exploitTabPane.getTabs().add(expTab);
                exploitTabPane.getSelectionModel().select(expTab);
                HelpPluginImpl helpPlugin = new HelpPluginImpl();
                List<ArgsInfo> argsInfoList = exploit.setArgs(helpPlugin);
                if (argsInfoList == null) {
                    SplitPane splitPane = (SplitPane) ComponentUtil.loadComponent(String.valueOf(exploit), "fxml/Exploit/Exploit.fxml", Controller.components);
                    TitledPane resultTitledPane = (TitledPane) splitPane.getItems().get(0);
                    exploitResutlText = (JFXTextArea) resultTitledPane.getContent();

                    try {
                        Method initInfoMethod = exploit.getClass().getMethod("initInfo");
                        if (initInfoMethod != null) {
                            Object result = initInfoMethod.invoke(exploit);
                            if (result == null) {
                                exploitResutlText.appendText("Default Information is Null\n");
                            } else {
                                exploitResutlText.appendText("【#INFO#】\n" + result + "\n---------------------------------------------------------------------------------------------------------\n\n");
                            }
                        }
                    } catch (InvocationTargetException e) {
                        exploitResutlText.appendText("Method 'initInfo' does not exist.未编写初始化信息\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    expTab.setContent(splitPane);
                } else {
                    SplitPane splitPane = (SplitPane) ComponentUtil.loadComponent(String.valueOf(exploit), "fxml/Exploit/Args_Exploit.fxml", Controller.components);
                    TitledPane argsTilePane = (TitledPane) splitPane.getItems().get(0);
                    TitledPane resultTitledPane = (TitledPane) splitPane.getItems().get(1);
                    exploitResutlText = (JFXTextArea) resultTitledPane.getContent();
                    try {
                        Method initInfoMethod = exploit.getClass().getMethod("initInfo");
                        if (initInfoMethod != null) {
                            Object result = initInfoMethod.invoke(exploit);
                            if (result == null) {
                                exploitResutlText.appendText("Default Information is Null\n");
                            } else {
                                exploitResutlText.appendText("【#INFO#】\n" + result + "\n---------------------------------------------------------------------------------------------------------\n\n");
                            }
                        }
                    } catch (InvocationTargetException e) {
                        exploitResutlText.appendText("Method 'initInfo' does not exist.未编写初始化信息\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    CodeArea argsCodeArea = new CodeArea();
                    argsCodeArea.setStyle("-fx-font-size: " + defaultFont.getSize() + "px; -fx-font-family: " + defaultFont.getFamily() + ";");
                    argsCodeArea.setWrapText(true);
                    argsCodeArea.setId("argsCodeArea");
                    argsCodeArea.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/main.css")).toExternalForm());
                    argsCodeArea.setParagraphGraphicFactory(LineNumberFactory.get(argsCodeArea));

                    Tooltip argsDescTip = new Tooltip();
                    Tooltip.install(argsCodeArea, argsDescTip);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < argsInfoList.size(); i++) {
                        ArgsInfoImpl args = (ArgsInfoImpl) argsInfoList.get(i);
                        String argsName = args.getArgsName();
                        if (!args.getDefaultValue().isEmpty()) {
                            argsCodeArea.appendText(argsName + "=" + args.getDefaultValue());
                        } else {
                            argsCodeArea.appendText(argsName + "=");
                        }
                        if (args.getDescription() != null) {
                            stringBuilder.append(args.getArgsName()).append("\t//").append(args.getDescription()).append(System.getProperty("line.separator"));
                        }

                        if (i < argsInfoList.size() - 1) {
                            argsCodeArea.appendText(System.getProperty("line.separator"));
                        }
                    }
                    argsDescTip.setText(stringBuilder.toString());
                    argsCodeArea.setOnMouseMoved(event1 -> {
                        argsDescTip.show(argsCodeArea, event1.getScreenX() + 10, event1.getScreenY() + 10);
                    });
                    argsCodeArea.setOnMouseExited(event1 -> {
                        argsDescTip.hide();
                    });


                    argsTilePane.setContent(argsCodeArea);
                    expTab.setContent(splitPane);
                }

            }

        });


    }

    private ListCell<PluginImpl> getClickedCell(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        while (node != null && !(node instanceof ListCell)) {
            node = node.getParent();
        }
        return (ListCell<PluginImpl>) node;
    }

    private void initPocPane() {
        scanPocList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        scanPocList.setCellFactory(param -> new ListCell<PluginImpl>() {
            @Override
            protected void updateItem(PluginImpl item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getPluginName());
                }
            }
        });

        pocTargetAddressTextArea = new CodeArea();
        pocTargetAddressTextArea.setStyle("-fx-font-size: " + defaultFont.getSize() + "px; -fx-font-family: " + defaultFont.getFamily() + ";");
        pocTargetAddressTextArea.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/main.css")).toExternalForm());
        pocTargetAddressTextArea.setWrapText(true);
        pocTargetAddressTextArea.setId("pocTargetAddressTextArea");
        pocTargetAddressTextArea.setParagraphGraphicFactory(LineNumberFactory.get(pocTargetAddressTextArea));
        targetInfoSplitPane.getItems().add(0, pocTargetAddressTextArea);

        pocResPluginNameCol.setCellValueFactory(new PropertyValueFactory<>("PluginName"));
        pocResTargetCol.setCellValueFactory(new PropertyValueFactory<>("PocTarget"));
        isvulCol.setCellValueFactory(new PropertyValueFactory<>("PocVul"));
        pocMsgCol.setCellValueFactory(new PropertyValueFactory<>("PocMsg"));
        pocTimeCol.setCellValueFactory(new PropertyValueFactory<>("PocTime"));
        pocResultTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem clearTableview = new MenuItem("delete result");
        contextMenu.getItems().add(clearTableview);
        pocResultTable.setContextMenu(contextMenu);
        clearTableview.setOnAction(clearTableviewEvent -> {
            List<ResultImpl> list = new ArrayList<>(pocResultTable.getSelectionModel().getSelectedItems());
            pocResultTable.getItems().removeAll(list);
        });

    }


    @FXML
    private void monitorPocPlugin(MouseEvent event) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem removePlugin = new MenuItem("remove plugin");
        contextMenu.getItems().add(removePlugin);
        if (event.getButton() == MouseButton.SECONDARY && !scanPocList.getSelectionModel().getSelectedItems().isEmpty()) {
            ListCell<PluginImpl> cell = getClickedCell(event);
            if (cell != null) {
                contextMenu.show(cell, event.getScreenX(), event.getScreenY());
            }
        } else if (event.getClickCount() == 2 && !scanPocList.getSelectionModel().getSelectedItems().isEmpty()) {
            List<PluginImpl> list = new ArrayList<>(scanPocList.getSelectionModel().getSelectedItems());
            scanPocList.getItems().removeAll(list);
        }

        removePlugin.setOnAction(removePluginEvent -> {
            List<PluginImpl> list = new ArrayList<>(scanPocList.getSelectionModel().getSelectedItems());
            scanPocList.getItems().removeAll(list);
        });
    }


    //runPoc
    @FXML
    private void startPoc() {
        if (pocTargetAddressTextArea.getText().isEmpty()) {
            CommonUtils.alert("Please input url");
            return;
        }
        if (scanPocList.getItems().isEmpty()) {
            CommonUtils.alert("Please select plugin");
            return;
        }
        String[] urls = pocTargetAddressTextArea.getText().split("\n");
        ObservableList<PluginImpl> plugins = FXCollections.observableArrayList(scanPocList.getItems());
        //proxy
        SettingController settingController = (SettingController) Controller.controllers.get("setting");
        ExecutorService executorService = Executors.newFixedThreadPool(urls.length);
        Boolean proxyStatus = settingController.getProxyStatus();
        for (PluginImpl plugin : plugins) {
            for (String url : urls) {
                Runnable scanTask = () -> {
                    TargetInfoImpl targetInfo = new TargetInfoImpl();
                    targetInfo.setAddress(url);
                    targetInfo.setUserAgent(settingController.getUserAgent());
                    targetInfo.setTimeout(settingController.getTimeout());
                    targetInfo.setCharset(settingController.getCharset());
                    targetInfo.setDnslog(settingController.getDNSLog());
                    targetInfo.setCookie(settingController.getCustomCookie());
                    ResultImpl result = new ResultImpl();
                    result.setPluginName(plugin.getPluginName());
                    result.setPocTarget(url);
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    result.setPocTime(simpleDateFormat.format(date));
                    if (proxyStatus) {
                        String proxyIP = settingController.getProxyIP();
                        String proxyPort = settingController.getProxyPort();
                        String proxyUsername = settingController.getProxyUsername();
                        String proxyPassword = settingController.getProxyPassword();
                        String proxyType = settingController.getProxyType();
                        Poc proxyObj;
                        if (!proxyUsername.isEmpty() && !proxyPassword.isEmpty()) {
                            proxyObj = (Poc) Proxy.newProxyInstance(plugin.getPoc().getClass().getClassLoader(), new Class[]{Poc.class}, new ProxyHandler(plugin.getPoc(), proxyIP, proxyPort, proxyUsername, proxyPassword, proxyType));
                        }
                        proxyObj = (Poc) Proxy.newProxyInstance(plugin.getPoc().getClass().getClassLoader(), new Class[]{Poc.class}, new ProxyHandler(plugin.getPoc(), proxyIP, proxyPort, proxyType));
                        proxyObj.doPoc(targetInfo, result);

                    } else {
                        plugin.getPoc().doPoc(targetInfo, result);
                    }


                    Platform.runLater(() -> {
                        pocResultTable.getItems().addAll(result);
                        //print poc result
                        pocResultText.appendText("【»»»»】" + plugin.getPluginName() + "\tis Started\n\n");
                        if (!result.getInfo().isEmpty()) {
                            pocResultText.appendText(String.join("\n", result.getInfo()) + "\n");
                        }
                        if (!result.getSuccess().isEmpty()) {
                            pocResultText.appendText(String.join("\n", result.getSuccess()) + "\n");
                        }
                        if (!result.getRaw().isEmpty()) {
                            pocResultText.appendText(String.join("\n", result.getRaw()) + "\n");
                        }
                        if (!result.getFail().isEmpty()) {
                            pocResultText.appendText(String.join("\n", result.getFail()) + "\n");
                        }
                        if (!result.getError().isEmpty()) {
                            pocResultText.appendText(String.join("\n", result.getError()) + "\n");
                        }
                        pocResultText.appendText("\n【««««】" + plugin.getPluginName() + "\tis Stoped\n---------------------------------------------------------------------------------------------------------\n\n");
                    });
                };
                executorService.execute(scanTask);

            }
        }
        executorService.shutdown();

    }

    //runExploit
    @FXML
    private void startExploit() {
        if (exploitTargetAddressText.getText().isEmpty()) {
            CommonUtils.alert("Please input url");
            return;
        }
        if (exploitTabPane.getSelectionModel().isEmpty()) {
            CommonUtils.alert("Please select plugin");
            return;
        }

        Tab selected = exploitTabPane.getSelectionModel().getSelectedItem();
        Exploit exploit = expMap.get(selected.getId());
        TargetInfoImpl targetInfo = new TargetInfoImpl();
        ResultImpl result = new ResultImpl();
        HashMap<String, Object> argsMap = new HashMap<>();
        String exploitTargetAddress = CommonUtils.urlParse(exploitTargetAddressText.getText());
        targetInfo.setAddress(exploitTargetAddress);
        SettingController settingController = (SettingController) Controller.controllers.get("setting");
        targetInfo.setUserAgent(settingController.getUserAgent());
        targetInfo.setTimeout(settingController.getTimeout());
        targetInfo.setCharset(settingController.getCharset());
        targetInfo.setDnslog(settingController.getDNSLog());
        SplitPane splitPane = (SplitPane) Controller.components.get(String.valueOf(exploit));
        List<ArgsInfo> argsInfoList = exploit.setArgs(new HelpPluginImpl());
        if (argsInfoList != null) {
            List<String> argsNames = new ArrayList<>();
            for (ArgsInfo argsInfo : argsInfoList) {
                ArgsInfoImpl args = (ArgsInfoImpl) argsInfo;
                String argsName = args.getArgsName();
                argsNames.add(argsName);
            }
            CodeArea argsCodeArea = (CodeArea) splitPane.lookup("#argsCodeArea");
            LiveList<Paragraph<Collection<String>, String, Collection<String>>> lines = argsCodeArea.getParagraphs();
            String key = null;
            String value = null;
            for (Paragraph<Collection<String>, String, Collection<String>> line : lines) {
                Pattern keyPattern = Pattern.compile("^[^=]*");
                Matcher keyMatcher = keyPattern.matcher(line.getText());
                if (keyMatcher.find()) {
                    key = keyMatcher.group();
                }
                Pattern valuePattern = Pattern.compile("(?<=\\=)(.*)");
                Matcher valueMatcher = valuePattern.matcher(line.getText());
                if (valueMatcher.find()) {
                    value = valueMatcher.group();
                }
                if (argsNames.contains(key)) {
                    argsMap.put(key, value);
                }
            }
        }


        //proxy
        Boolean isProxy = settingController.getProxyStatus();
        if (isProxy) {
            String proxyIP = settingController.getProxyIP();
            String proxyPort = settingController.getProxyPort();
            String proxyUsername = settingController.getProxyUsername();
            String proxyPassword = settingController.getProxyPassword();
            String proxyType = settingController.getProxyType();
            Exploit proxyObj;
            if (!proxyUsername.isEmpty() && !proxyPassword.isEmpty()) {
                proxyObj = (Exploit) Proxy.newProxyInstance(exploit.getClass().getClassLoader(), new Class[]{Exploit.class}, new ProxyHandler(exploit, proxyIP, proxyPort, proxyUsername, proxyPassword, proxyType));
            } else {
                proxyObj = (Exploit) Proxy.newProxyInstance(exploit.getClass().getClassLoader(), new Class[]{Exploit.class}, new ProxyHandler(exploit, proxyIP, proxyPort, proxyType));
                proxyObj.doExploit(targetInfo, argsMap, result);
            }
        } else {
            exploit.doExploit(targetInfo, argsMap, result);
        }

        //result
        exploitResutlText.appendText("【»»»»】" + exploit.setExploitTitle() + "\tis Started\n\n");
        if (!result.getInfo().isEmpty()) {
            exploitResutlText.appendText(String.join("\n", result.getInfo()) + "\n");
        }
        if (!result.getSuccess().isEmpty()) {
            exploitResutlText.appendText(String.join("\n", result.getSuccess()) + "\n");
        }
        if (!result.getRaw().isEmpty()) {
            exploitResutlText.appendText(String.join("\n", result.getRaw()) + "\n");
        }
        if (!result.getFail().isEmpty()) {
            exploitResutlText.appendText(String.join("\n", result.getFail()) + "\n");
        }
        if (!result.getError().isEmpty()) {
            exploitResutlText.appendText(String.join("\n", result.getError()) + "\n");
        }
        exploitResutlText.appendText("\n【««««】" + exploit.setExploitTitle() + "\tis Stoped\n---------------------------------------------------------------------------------------------------------\n\n");


    }


}
