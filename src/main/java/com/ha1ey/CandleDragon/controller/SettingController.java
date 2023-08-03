package com.ha1ey.CandleDragon.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.ha1ey.CandleDragon.common.CommonUtils;
import com.ha1ey.CandleDragon.core.DNSLogResultEntity;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.Paragraph;
import org.reactfx.collection.LiveList;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SettingController {
    @FXML
    private JFXComboBox<String> proxyTypeComboBox;
    @FXML
    private JFXTextField proxyIPText;
    @FXML
    private JFXTextField proxyPortText;
    @FXML
    private JFXTextField proxyUsernameText;
    @FXML
    private JFXTextField proxyPasswordText;
    Boolean isProxy;
    @FXML
    private JFXCheckBox proxySwitch;
    @FXML
    private JFXTextField timeoutText;
    @FXML
    private TitledPane uaTitlePane;


    CodeArea codeArea;


    @FXML
    private JFXRadioButton gbkRadioButton;
    @FXML
    private JFXRadioButton usasciiRadioButton;
    @FXML
    private JFXRadioButton isoRadioButton;

    @FXML
    private JFXRadioButton utf8RadioButton;

    @FXML
    private JFXTextField dnslogApiText;
    @FXML
    private JFXTextField dnslogDomainText;
    @FXML
    private JFXTextField dnslogTokenText;
    @FXML
    private JFXTextField dnslogRefreshTimeText;
    @FXML
    private JFXComboBox<String> dnslogComboxBox;


    private final List<String> ualist = new ArrayList<>();
    private long lastTimestamp = 0;


    @FXML
    private void initialize() {
        initProxyParams();
        initHttp();
        initCharset();
        initDNSLog();

    }


    //Proxy
    private void initProxyParams() {
        proxyTypeComboBox.getItems().add(0, "HTTP");
        proxyTypeComboBox.getItems().add(1, "SOCKS");
        proxyTypeComboBox.getSelectionModel().selectFirst();
        isProxy = false;
        proxySwitch.setSelected(false);
        proxySwitch.setOnAction(event -> {
            Controller controller = (Controller) Controller.controllers.get("control");
            String proxyType = proxyTypeComboBox.getSelectionModel().getSelectedItem();
            if (proxySwitch.isSelected()) {
                if (!proxyIPText.getText().isEmpty() && !proxyPortText.getText().isEmpty()) {
                    isProxy = true;
                    proxyTypeComboBox.setDisable(true);
                    proxyIPText.setDisable(true);
                    proxyPortText.setDisable(true);
                    proxyUsernameText.setDisable(true);
                    proxyPasswordText.setDisable(true);
                    controller.setProxyStatusLabel(proxyIPText.getText(), proxyPortText.getText(), proxyType, isProxy);
                } else {
                    CommonUtils.alert("Please input proxyip and proxyport");
                    proxySwitch.setSelected(false);
                }
            } else {
                isProxy = false;
                controller.setProxyStatusLabel(proxyIPText.getText(), proxyPortText.getText(), proxyType, isProxy);
                proxyTypeComboBox.setDisable(false);
                proxyIPText.setDisable(false);
                proxyPortText.setDisable(false);
                proxyUsernameText.setDisable(false);
                proxyPasswordText.setDisable(false);
            }
        });
    }


    //charset
    private void initCharset() {
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(gbkRadioButton);
        toggleGroup.getToggles().add(usasciiRadioButton);
        toggleGroup.getToggles().add(isoRadioButton);
        toggleGroup.getToggles().add(utf8RadioButton);
        utf8RadioButton.setSelected(true);
    }

    private void initHttp() {

        ualist.add("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        ualist.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36");
        ualist.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36");
        ualist.add("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2226.0 Safari/537.36");
        ualist.add("Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko");
        ualist.add("Mozilla/5.0 (compatible, MSIE 11, Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");
        ualist.add("Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0");
        ualist.add("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 7.0; InfoPath.3; .NET CLR 3.1.40767; Trident/6.0; en-IN)");
        ualist.add("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
        ualist.add("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)");
        ualist.add("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/5.0)");
        ualist.add("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/4.0; InfoPath.2; SV1; .NET CLR 2.0.50727; WOW64)");
        ualist.add("Mozilla/5.0 (compatible; MSIE 10.0; Macintosh; Intel Mac OS X 10_7_3; Trident/6.0)");
        ualist.add("Mozilla/4.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/5.0)");
        ualist.add("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/532.2 (KHTML, like Gecko) ChromePlus/4.0.222.3 Chrome/4.0.222.3 Safari/532.2");
        ualist.add("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.28.3 (KHTML, like Gecko) Version/3.2.3 ChromePlus/4.0.222.3 Chrome/4.0.222.3 Safari/525.28.3");
        ualist.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");
        ualist.add("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0");
        ualist.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10; rv:33.0) Gecko/20100101 Firefox/33.0");
        ualist.add("Mozilla/5.0 (X11; Linux i586; rv:31.0) Gecko/20100101 Firefox/31.0");
        ualist.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20130401 Firefox/31.0");
        ualist.add("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");


        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setWrapText(true);
        for (int i = 0; i < ualist.size(); i++) {
            String ua = ualist.get(i);
            codeArea.appendText(ua);
            if (i < ualist.size() - 1) {
                codeArea.appendText(System.getProperty("line.separator"));
            }
        }
        codeArea.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/main.css")).toExternalForm());
        uaTitlePane.setContent(codeArea);


    }


    //DNSLog
    private void initDNSLog() {
        dnslogComboxBox.getItems().add(0, "RACP");
        dnslogComboxBox.getItems().add("Custom DNSLogPlatform");
        dnslogComboxBox.getSelectionModel().selectFirst();
        dnslogApiText.setDisable(true);
        dnslogComboxBox.setOnAction(event -> {
            if (dnslogComboxBox.getSelectionModel().getSelectedItem().equals("Custom DNSLogPlatform")){
                dnslogApiText.setDisable(false);
            }else {
                dnslogApiText.setDisable(true);
            }
        });

    }

    @FXML
    private void autoRefresh(ActionEvent actionEvent) {
        if (dnslogDomainText.getText().isEmpty() || dnslogTokenText.getText().isEmpty()){
            CommonUtils.alert("Please input DNSLog Domain or DNSLogToken !");
            return;
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                DebugController debugController = (DebugController) Controller.controllers.get("debug");
                HttpResponse r = HttpRequest.get("http://107.182.20.190/v1/records?token=" + dnslogTokenText.getText() + "&search=" + dnslogDomainText.getText()).execute();
                if (r.getStatus() == 200 && r.body() != null) {
                    JSONObject jsonObject = new JSONObject(r.body());
                    JSONArray jsonArray = jsonObject.getJSONArray("records");
                    List<JSONObject> sortedRecords = new ArrayList<>();
                    for (Object obj : jsonArray) {
                        sortedRecords.add((JSONObject) obj);
                    }

                    Collections.sort(sortedRecords,((o1, o2) -> {
                        long timestamp1 = Long.parseLong((String) o1.get("createTime"));
                        long timestamp2 = Long.parseLong((String) o2.get("createTime"));
                        return Long.compare(timestamp1,timestamp2);
                    }));

                    for (JSONObject jsonObj : sortedRecords){
                        String createTime = (String) jsonObj.get("createTime");
                        long timestamp = Long.parseLong(createTime);
                        if (timestamp > lastTimestamp){
                            String domain = (String) jsonObj.get("queryDomain");
                            String reqip = (String) jsonObj.get("ip");

                            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of("+8"));
                            ZoneId zoneId = ZoneId.of("Asia/Shanghai");
                            ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zoneId);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String time = zonedDateTime.format(formatter);
                            DNSLogResultEntity dnsLogResult = new DNSLogResultEntity();
                            dnsLogResult.setDomain(domain);
                            dnsLogResult.setTime(time);
                            dnsLogResult.setReqip(reqip);
                            debugController.getDnslogResultTable().getItems().add(dnsLogResult);
                        }
                    }
                    if (!sortedRecords.isEmpty()){
                        JSONObject lastRecord = sortedRecords.get(sortedRecords.size() - 1);
                        String lastCreateTime = (String) lastRecord.get("createTime");
                        lastTimestamp = Long.parseLong(lastCreateTime);
                    }

                }

            }
        };
        timer.scheduleAtFixedRate(task, 0, Integer.parseInt(dnslogRefreshTimeText.getText()));

    }

    @FXML
    private void testDNSLog() {
        if (dnslogDomainText.getText().isEmpty()){
            CommonUtils.alert("Please input DNSLog Domain!");
            return;
        }
        try {
            InetAddress.getByName(dnslogDomainText.getText());
        } catch (Exception ignored) {
        }
    }

    @FXML
    private void getDNSLogResult() {
        if (dnslogDomainText.getText().isEmpty() || dnslogTokenText.getText().isEmpty()){
            CommonUtils.alert("Please input DNSLog Domain or DNSLogToken !");
            return;
        }
        DebugController debugController = (DebugController) Controller.controllers.get("debug");
        HttpResponse r = HttpRequest.get("http://107.182.20.190/v1/records?token=" + dnslogTokenText.getText() + "&search=" + dnslogDomainText.getText()).execute();
        if (r.getStatus() == 200 && r.body() != null) {
            JSONObject jsonObject = new JSONObject(r.body());
            JSONArray jsonArray = jsonObject.getJSONArray("records");
            List<JSONObject> sortedRecords = new ArrayList<>();
            for (Object obj : jsonArray) {
                sortedRecords.add((JSONObject) obj);
            }

            Collections.sort(sortedRecords,((o1, o2) -> {
                long timestamp1 = Long.parseLong((String) o1.get("createTime"));
                long timestamp2 = Long.parseLong((String) o2.get("createTime"));
                return Long.compare(timestamp1,timestamp2);
            }));

            for (JSONObject jsonObj : sortedRecords){
                String createTime = (String) jsonObj.get("createTime");
                long timestamp = Long.parseLong(createTime);
                if (timestamp > lastTimestamp){
                    String domain = (String) jsonObj.get("queryDomain");
                    String reqip = (String) jsonObj.get("ip");

                    LocalDateTime dateTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of("+8"));
                    ZoneId zoneId = ZoneId.of("Asia/Shanghai");
                    ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zoneId);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String time = zonedDateTime.format(formatter);
                    DNSLogResultEntity dnsLogResult = new DNSLogResultEntity();
                    dnsLogResult.setDomain(domain);
                    dnsLogResult.setTime(time);
                    dnsLogResult.setReqip(reqip);
                    debugController.getDnslogResultTable().getItems().add(dnsLogResult);
                }
            }
            if (!sortedRecords.isEmpty()){
                JSONObject lastRecord = sortedRecords.get(sortedRecords.size() - 1);
                String lastCreateTime = (String) lastRecord.get("createTime");
                lastTimestamp = Long.parseLong(lastCreateTime);
            }

        }
    }


    public String getCharset() {
        String charset = null;
        if (gbkRadioButton.isSelected()) {
            charset = "GBK";
        }
        if (usasciiRadioButton.isSelected()) {
            charset = "US-ASCII";
        }
        if (isoRadioButton.isSelected()) {
            charset = "ISO-8859-1";
        }
        if (utf8RadioButton.isSelected()) {
            charset = "UTF-8";
        }
        return charset;
    }

    public String getUserAgent() {
        LiveList<Paragraph<Collection<String>, String, Collection<String>>> lines = codeArea.getParagraphs();
        String ua;
        if (lines.size() > 1) {
            Random random = new Random();
            int randomIndex = random.nextInt(lines.size());
            Paragraph<Collection<String>, String, Collection<String>> randomLine = lines.get(randomIndex);
            ua = randomLine.getText();
        } else {
            ua = codeArea.getParagraph(0).getText();
        }

        return ua;
    }

    public int getTimeout() {
        return Integer.parseInt(timeoutText.getText());
    }

    public String getDNSLog() {
        return dnslogDomainText.getText();
    }

    public String getProxyIP() {
        return proxyIPText.getText();
    }

    public String getProxyPort() {
        return proxyPortText.getText();
    }

    public String getProxyUsername() {
        return proxyUsernameText.getText();
    }

    public String getProxyPassword() {
        return proxyPasswordText.getText();
    }

    public String getProxyType() {
        return proxyTypeComboBox.getSelectionModel().getSelectedItem();
    }

    public Boolean getProxyStatus() {
        return isProxy;
    }


}
