package application;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;

public class MapCellBar extends VBox{
	private int cellSize = 50;
	ProgressBar bar = new ProgressBar();
	public MapCellBar(float maxHealth,float health,String src){
		super();
		bar.setStyle("-fx-accent: #8a1529;");
		this.setAlignment(Pos.CENTER);
		this.setMinHeight(cellSize);
		this.setMaxHeight(cellSize);
		this.setMinWidth(cellSize);
		this.setMaxWidth(cellSize);
		 Image backgroundImage = new Image("light grass.png");
	        BackgroundImage background = new BackgroundImage(backgroundImage,
	                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null,
	                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
	        setBackground(new Background(background));
		setStyle("-fx-padding: 0;-fx-border-color: black; -fx-border-width: 2px;");
		MapCell character = new MapCell(src);
		bar.setProgress(health/maxHealth);
		getChildren().addAll(bar,character);
	}
}
