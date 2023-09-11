package com.ha1ey.CandleDragon.controller;

import com.ha1ey.CandleDragon.core.DNSLogResultEntity;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author Ha1ey
 * @descrition
 * @date 2023-07-31 16:28
 **/
public class DebugController {
    @FXML
    private TableView<DNSLogResultEntity> dnslogResultTable;
    @FXML
    private TableColumn<DNSLogResultEntity, String> dnslogReqIPCol;
    @FXML
    private TableColumn<DNSLogResultEntity, String> dnslogCreateTimeCol;
    @FXML
    private TableColumn<DNSLogResultEntity, String> dnslogDomainCol;
    @FXML
    private TableColumn<DNSLogResultEntity, String> dnslogResultIDCol;


    @FXML
    private void initialize() {
        initDNSLogResult();
    }


    private void initDNSLogResult() {
        dnslogResultIDCol.setCellFactory((tableColumn) -> new TableCell<DNSLogResultEntity, String>() {
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

        dnslogReqIPCol.setCellValueFactory(new PropertyValueFactory<>("Reqip"));
        dnslogCreateTimeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));
        dnslogDomainCol.setCellValueFactory(new PropertyValueFactory<>("Domain"));
    }


    public TableView<DNSLogResultEntity> getDnslogResultTable() {
        return this.dnslogResultTable;
    }
}
