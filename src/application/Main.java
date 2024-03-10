package application;
	
import java.io.IOException;

import engine.Game;
import javafx.application.Application;
//import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;

public class Main extends Application {
	static Hero selected = null;
	public static Stage primaryStage;
	public static Scene s;
	@Override
	public void init() throws IOException {
		Game.loadHeroes("Heroes.csv");
	}
	public void start(Stage primaryStage) {
		VBox root = new VBox(); //layout of first page
		
		root.setBackground(new Background(new BackgroundImage( new Image("bg.jpg"),
				BackgroundRepeat.REPEAT, 
				BackgroundRepeat.NO_REPEAT, 
				new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                )));
		root.setAlignment(Pos.CENTER);
		
		ImageView heading = new ImageView(new Image("title1.jpg"));
		heading.setTranslateY(-20);
		
		HBox selectHeroes = new HBox(); //The horizontal list of heroes
		selectHeroes.setPadding(new Insets(20,20,20,20));
		selectHeroes.setSpacing(10);
		selectHeroes.setMinHeight(150);
		selectHeroes.setAlignment(Pos.BASELINE_CENTER);
		selectHeroes.setBackground(new Background(new BackgroundImage( new Image("woodbg.png"),
				BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.REPEAT, 
				new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                )));
		
		Label noSelectMsg = new Label("");;
		for(int i = 0; i < Game.availableHeroes.size(); i++) {
			int index = i;
			VBox eachHero = new VBox();
			eachHero.setSpacing(10);
			/*eachHero.setBackground(new Background(new BackgroundImage( new Image("phone.jpg"),
					BackgroundRepeat.NO_REPEAT, 
					BackgroundRepeat.NO_REPEAT, 
					new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
					new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
	                )));*/
			eachHero.setStyle("-fx-background-color: #D3D3D3; ");
			eachHero.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			eachHero.setAlignment(Pos.CENTER);
			eachHero.setPadding(new Insets(10,10,10,10));
			eachHero.setMinWidth(100);
			Label eachDetail = new Label("");
			noSelectMsg = new Label("");
			eachDetail.setFont(Font.font(16));
			Button b1 = new Button(Game.availableHeroes.get(index).getName());
			b1.setWrapText(true);
			b1.setPadding(new Insets(2,2,2,2));
			b1.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
			b1.setAlignment(Pos.BASELINE_CENTER);
			b1.setOnMouseClicked(new EventHandler <Event>(){
				@Override
				public void handle(Event event) {
					selected = Game.availableHeroes.get(index);
				}
				});
			String details = "Damage: " + Game.availableHeroes.get(index).getAttackDmg() + "\n";
			details += "HP: " + Game.availableHeroes.get(index).getMaxHp() + "\n" 
					+ "Actions: " + Game.availableHeroes.get(index).getMaxActions() + "\n";
			if(Game.availableHeroes.get(index) instanceof Fighter) {
				details += "Fighter";
			} else if(Game.availableHeroes.get(index) instanceof Medic) {
				details += "Medic";
			} else {
				details += "Explorer";
			}
			eachDetail.setTextFill(Color.WHITE);
			Image remainImg;
			if(Game.availableHeroes.get(i) instanceof Fighter)
				remainImg = new Image("fighter cell.png");
			else if(Game.availableHeroes.get(i) instanceof Medic)
				remainImg = new Image("medic.jpg");
			else 
				remainImg = new Image("explorer.png");
			ImageView remainImgView = new ImageView(remainImg);
			remainImgView.setFitHeight(70);
			remainImgView.setFitWidth(70);
			eachDetail = new Label(details);
			eachDetail.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
			eachDetail.setWrapText(true);
			eachDetail.setTextAlignment(TextAlignment.CENTER);
			eachHero.getChildren().addAll(b1, remainImgView, eachDetail);
			selectHeroes.getChildren().addAll(eachHero);
		}
		
		Label finalNoSelectMsg = noSelectMsg;
		ImageView startImgView = new ImageView(new Image("start.png"));
		startImgView.setFitHeight(100);
		startImgView.setFitWidth(120);
		Button startGame = new Button("",startImgView);
		startGame.setBackground(null);
		// Set the fullScreenExitHint to an empty string
        primaryStage.setFullScreenExitHint("");
     // Get the primary screen
        Screen screen = Screen.getPrimary();

        // Get the visual bounds of the screen
        Rectangle2D bounds = screen.getVisualBounds();

        // Retrieve the screen width and height
        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();
        primaryStage.setX(0);
        primaryStage.setY(0);

        // Add an event handler to prevent the stage from closing on fullscreen exit;
		startGame.setOnMouseClicked(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				if(selected != null) {
					System.out.println(selected);
					Game.startGame(selected);
					Play.start(selected);
					primaryStage.setScene(Play.play);
					//primaryStage.setFullScreen(true);
				}
				else {
					finalNoSelectMsg.setText("No Hero Selected");
					finalNoSelectMsg.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
					finalNoSelectMsg.setPadding(new Insets(5,5,5,5));
					finalNoSelectMsg.setFont(Font.font("Courier New", FontWeight.BOLD,30));
					finalNoSelectMsg.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

				}
			}
		});
		
		root.getChildren().addAll(heading,selectHeroes,startGame,noSelectMsg);
		
		s = new Scene(root,screenWidth,screenHeight);
		//primaryStage.setFullScreen(true);
		primaryStage.setTitle("The Last Of Us - Legacy");
		primaryStage.getIcons().add(new Image("1.png"));
		primaryStage.setScene(s);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void stop() {
		
	}
	
}

