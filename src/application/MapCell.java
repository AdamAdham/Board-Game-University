package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapCell extends ImageButton{
	private int cellSize = 45;
	public MapCell (String src){
		super();
        Image backgroundImage = new Image(src);
        ImageView imageView = new ImageView(backgroundImage);
        imageView.setPreserveRatio(false);
        imageView.setFitWidth(cellSize);
        imageView.setFitHeight(cellSize);
        setGraphic(imageView);
        setStyle("-fx-padding: 0;-fx-border-color: black; -fx-border-width: 2px;");
        setPrefSize(cellSize, cellSize);
	}
}
