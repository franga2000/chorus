package org.chorusmc.chorus.menus.variables;

import org.chorusmc.chorus.Chorus;
import org.chorusmc.chorus.theme.Themes;
import org.chorusmc.chorus.util.Utils;
import org.chorusmc.chorus.variable.Variable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Gio
 */
public class VariablesMenu {

    private Stage stage = new Stage();
    private TableView<Variable> table = new TableView<>();

    private static VariablesMenu instance;

    private VariablesMenu() {
        instance = this;

        VBox root = new VBox();
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.prefWidthProperty().bind(root.widthProperty());
        scrollPane.prefHeightProperty().bind(root.prefHeightProperty());
        Scene scene = new Scene(scrollPane, 500.0, 400.0);
        scrollPane.getStylesheets().addAll("/assets/styles/global.css", Themes.byConfig().getPath()[0]);
        scrollPane.getStyleClass().add("variables-menu");
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
        stage.setTitle(Utils.translate("variables.title"));
        stage.setScene(scene);
        stage.getIcons().add(new Image(Chorus.class.getResourceAsStream("/assets/images/icon.png")));

        VariablesControlBar controlBar = new VariablesControlBar(this);
        controlBar.prefWidthProperty().bind(scrollPane.widthProperty());

        TableColumn<Variable, String> nameColumn = new TableColumn<>(Utils.translate("variables.name"));
        TableColumn<Variable, String> valueColumn = new TableColumn<>(Utils.translate("variables.value"));
        nameColumn.prefWidthProperty().bind(table.prefWidthProperty().divide(2).subtract(1));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));
        valueColumn.prefWidthProperty().bind(table.prefWidthProperty().divide(2).subtract(1));
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueColumn.setOnEditCommit(e -> e.getRowValue().setValue(e.getNewValue()));
        table.getColumns().addAll(nameColumn, valueColumn);
        table.prefWidthProperty().bind(scrollPane.widthProperty());
        table.prefHeightProperty().bind(scrollPane.heightProperty().subtract(84));

        table.setPlaceholder(new VariablesPlaceholder());

        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root.getChildren().addAll(controlBar, table);
    }

    public void show() {
        stage.show();
    }

    public static VariablesMenu getInstance() {
        return instance == null ? new VariablesMenu() : instance;
    }

    public TableView<Variable> getTable() {
        return table;
    }
}