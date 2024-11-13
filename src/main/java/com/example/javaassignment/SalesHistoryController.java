package com.example.javaassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SalesHistoryController {
    @FXML
    private TableView<Sale> salesHistoryTable;

    private ObservableList<Sale> salesList;
    private SalesDatabase salesDatabase;

    public SalesHistoryController(SalesDatabase salesDatabase) {
        this.salesDatabase = salesDatabase;
    }

    @FXML
    public void initialize() {
        salesList = FXCollections.observableArrayList(salesDatabase.getSales());
        salesHistoryTable.setItems(salesList);
        setupTableColumns();
    }
    @FXML
    private void setupTableColumns() {
            salesHistoryTable.getColumns().clear();

            TableColumn<Sale, LocalDateTime> dateTimeColumn = new TableColumn<>("Date/Time");

            dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
            dateTimeColumn.setCellFactory(column -> {
                return new TableCell<Sale, LocalDateTime>() {
                    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.format(formatter)); // Apply the formatter
                        }
                    }
                };
            });

            dateTimeColumn.setPrefWidth(180);

            TableColumn<Sale, Integer> numberOfTicketsColumn = new TableColumn<>("Number of Tickets");
            numberOfTicketsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfTickets"));
            numberOfTicketsColumn.setPrefWidth(150);

            TableColumn<Sale, String> customerColumn = new TableColumn<>("Customer");
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerColumn.setPrefWidth(150);

            TableColumn<Sale, String> showingColumn = new TableColumn<>("Showing");
            showingColumn.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
            showingColumn.setPrefWidth(150);

            salesHistoryTable.getColumns().addAll(dateTimeColumn, numberOfTicketsColumn, customerColumn, showingColumn);
    }
}


