package net.prosavage.bedfilevisualizer.Util;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class Util {

	public static void setupContextMenu(ListView listView) {

			listView.setCellFactory(lv -> {

				ListCell<String> cell = new ListCell<>();

				ContextMenu contextMenu = new ContextMenu();


				MenuItem deleteItem = new MenuItem();
				deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
				deleteItem.setOnAction(event -> listView.getItems().remove(cell.getItem()));
				contextMenu.getItems().addAll(deleteItem);

				cell.textProperty().bind(cell.itemProperty());

				cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
					if (isNowEmpty) {
						cell.setContextMenu(null);
					} else {
						cell.setContextMenu(contextMenu);
					}
				});
				return cell ;
			});

	}


}
