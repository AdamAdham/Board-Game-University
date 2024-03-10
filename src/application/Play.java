package application;


import java.awt.Point;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.application.Platform;
//import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import model.characters.Character;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;


public class Play {
	//Grid pane is x = 0->14 from left->right and y 14->0 form down->up
	public static Scene play;
	public static Hero selectedHero = Main.selected;
	public static int indexOfSelectedHero = 0;
	public static BorderPane root = new BorderPane();
	public static StackPane cont = new StackPane();
	public static VBox heroes = new VBox();
	public static GridPane map = new GridPane();
	public static Label messages = new Label("");
	public static ImageButton left = new ImageButton();
    public static ImageButton right = new ImageButton();
    public static ImageButton up = new ImageButton();
    public static ImageButton down = new ImageButton();
	public static ImageButton attackButton = new ImageButton();
    public static Button useSpecialButton = new Button("Use Special");
    public static ImageButton cureButton = new ImageButton();
	public static Button endTurnButton = new Button("End Turn");
	public static Button helpButton = new Button("Help ?");
	public static boolean running = true;
	public static Button quitButton = new Button("Quit");
	
	public static void start(Hero selected) {
		map.setVgap(0);
		map.setHgap(0);
		map.setPadding(new Insets(0));
		heroes.setMinWidth(250);
		selectedHero = selected;
//		root.setBackground(new Background(new BackgroundImage( new Image("Bg.png"),
//				BackgroundRepeat.REPEAT, 
//				BackgroundRepeat.NO_REPEAT, 
//				new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
//				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
//                )));
		root.setStyle("-fx-background-color: #202020;");
		refreshHeroStats();
		map.setMaxSize(550, 550);
		map.setMinSize(550,550);
		//Every element in the grid for the elements added to that element it is centrally place in the grid element
		map.setAlignment(Pos.CENTER);
		refreshMap();
		actions();
		root.setBottom(messages);
        GridPane movePane = new GridPane();
        GridPane actionsPane = new GridPane();
    	HBox actionsBox = new HBox(10);
        actionsBox.setPadding(new Insets(20, 40, 30, 40));
        actionsBox.setPrefHeight(300);
        movePane.setPrefSize(500, 500);
        
        //Setting images to buttons
        Image image = new Image("Left arrow.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        left.setGraphic(imageView);
        image = new Image("Right Arrow.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        right.setGraphic(imageView);
        image = new Image("Up arrow.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        up.setGraphic(imageView);
        image = new Image("Down arrow.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        down.setGraphic(imageView);
        image = new Image("Sword Narrow.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        attackButton.setGraphic(imageView);
        
        image = new Image("syringe.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        cureButton.setGraphic(imageView);
        
        // Set preferred button sizes
        attackButton.setPrefSize(80, 40);
        useSpecialButton.setPrefSize(80, 40);
        endTurnButton.setPrefSize(80, 40);
        cureButton.setPrefSize(70, 70);
        left.setPrefSize(70, 70);
        right.setPrefSize(70, 70);
        up.setPrefSize(70, 70);
        down.setPrefSize(70, 70);
        
        //Set pane properties
        movePane.setHgap(10);
        movePane.setVgap(10); 
        actionsPane.setVgap(20);
        actionsPane.setHgap(10);

        //Add move buttons to a movePane
        movePane.add(left,0,1);
        movePane.add(down,1,1);
        movePane.add(right, 2, 1);
        movePane.add(up, 1, 0);
        
        //Add action buttons to actionsPane
        actionsPane.add(attackButton, 0, 0);
        actionsPane.add(useSpecialButton, 1, 0);
        actionsPane.add(endTurnButton,1,1);
        actionsPane.add(cureButton,2,0);
        actionsPane.add(helpButton, 2, 5);
        actionsPane.add(left, 0, 4);
        actionsPane.add(right, 2, 4);
        actionsPane.add(up, 1, 3);
        actionsPane.add(down, 1, 4);

        
        // Create an HBox to hold the buttons
        actionsBox.setAlignment(Pos.CENTER);
        
     // Create a region to fill the space between Move and Attack/Use Special buttons
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        // Add the buttons to the button container
        actionsBox.getChildren().addAll(spacer, actionsPane);
        actionsBox.setTranslateY(-200);
		root.setBottom(actionsBox);

		root.setLeft(heroes);
		cont.getChildren().add(root);
		cont.getChildren().add(map);
		// Get the primary screen
        Screen screen = Screen.getPrimary();

        // Get the visual bounds of the screen
        Rectangle2D bounds = screen.getVisualBounds();

        // Retrieve the screen width and height
        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();
		play = new Scene(cont,screenWidth,screenHeight);
		eventHandler();
	}
	
	public static void eventHandler(){
	play.setOnKeyPressed(new EventHandler<KeyEvent>(){
		
		@Override
		public void handle(KeyEvent event) {
			switch(event.getCode()) {
				case X: try {
						Game.endTurn();
						boolean done = false;
						if(Game.checkWin()) {
							Alert alert = new Alert(Alert.AlertType.WARNING);
							alert.setTitle("Game Win");
							alert.setHeaderText("You have won!");
							ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
					        alert.getButtonTypes().setAll(exitButton);
							alert.showAndWait();
							done = true;
						}
						if(Game.checkGameOver() && !done) {
							Alert alert = new Alert(Alert.AlertType.WARNING);
							ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
					        alert.getButtonTypes().setAll(exitButton);
							alert.setTitle("Game Over");
							alert.setHeaderText("You have lost :(");
							alert.showAndWait();
							done = true;
						}
						if (done) {
							Platform.exit();
						}
						messages.setText("");break;
					} catch (NotEnoughActionsException | InvalidTargetException e) {
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						break;
					}
				case SPACE: try {
						selectedHero.attack();
						refreshMap();
						refreshHeroStats();
						boolean done = false;
						if (Game.checkGameOver() && !done) {
							Alert alert = new Alert(Alert.AlertType.WARNING);
							alert.setTitle("Game Over");
							alert.setHeaderText("You have lost :(");
							ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
					        alert.getButtonTypes().setAll(exitButton);
							alert.showAndWait();
							done = true;
						}
						if (done) {
							Platform.exit();
						}
						messages.setText("");break;
					} catch (NotEnoughActionsException | InvalidTargetException e) {
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						break;
					}
				case ENTER: try {
						selectedHero.cure();
						refreshMap();
						refreshHeroStats();
						boolean done = false;
						if (Game.checkWin()) {
							Alert alert = new Alert(Alert.AlertType.WARNING);
							alert.setTitle("Game Win");
							alert.setHeaderText("You have won!");
							ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
					        alert.getButtonTypes().setAll(exitButton);
							alert.showAndWait();
							done = true;
						}
						if(done)
							Platform.exit();
						messages.setText("");break;
					} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e) {
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						break;
					}
				case S: try {
						selectedHero.move(Direction.LEFT);
						messages.setText("");
						refreshMap();
						refreshHeroStats();
						break;
						
					} catch (MovementException | NotEnoughActionsException e) {
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						break;
					}
				case W: try {
						selectedHero.move(Direction.RIGHT);
						messages.setText("");
						refreshMap();
						refreshHeroStats();
						break;
					} catch (MovementException | NotEnoughActionsException e) {
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						break;
					}
				case A: try {
						selectedHero.move(Direction.DOWN);
						messages.setText("");
						refreshMap();
						refreshHeroStats();
						break;
					} catch (MovementException | NotEnoughActionsException e) {
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						break;
					}
				case D: try {
						selectedHero.move(Direction.UP);
						refreshMap();
						refreshHeroStats();
						messages.setText("");
						break;
					} catch (MovementException | NotEnoughActionsException e) {
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						break;
					}
				case U: try {
						selectedHero.useSpecial();
						refreshMap();
						refreshHeroStats();
						messages.setText("");
						break;
						
					} catch (NoAvailableResourcesException | InvalidTargetException e) {
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						break;
					}
				case P:
					try {
						selectedHero = Game.heroes.get(indexOfSelectedHero + 1);break;
					} catch (Exception e) {
						messages.setText("No heroes on right");
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						break;
					}
				case O:
					try {
						selectedHero = Game.heroes.get(indexOfSelectedHero - 1);break;
					} catch (Exception e) {
						messages.setText("No heroes on left");
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						break;
					}
				default:break;
			}

		}
		
		
	});
	}
	
	public static void actions(){
		helpButton.setOnMouseClicked(new EventHandler <Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Help");
				alert.setHeaderText("Help");
				String content = "Should show help information for player";
				alert.setContentText(content);
				alert.showAndWait();
			}});
			
		endTurnButton.setOnMouseClicked(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
					try {
						Game.endTurn();
						boolean done = false; 
						if (Game.checkWin()) {
							Alert alert = new Alert(Alert.AlertType.WARNING);
							alert.setTitle("Game Win");
							alert.setHeaderText("You have won!");
							ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
					        alert.getButtonTypes().setAll(exitButton);
							alert.showAndWait();
							done = true;
						}
						if (Game.checkGameOver() && !done) {
							Alert alert = new Alert(Alert.AlertType.WARNING);
							alert.setTitle("Game Over");
							alert.setHeaderText("You have lost :(");
							ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
					        alert.getButtonTypes().setAll(exitButton);
							alert.showAndWait();
							done = true;
						}
						if (done) {
							Platform.exit();
						}
					} catch (NotEnoughActionsException | InvalidTargetException e) {
						// TODO Auto-generated catch block
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
					}finally{
						refreshMap();
						refreshHeroStats();
					}
				}
			});
		cureButton.setOnMouseClicked(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				try {
					selectedHero.cure();
					refreshMap();
					refreshHeroStats();
					boolean done = false;
					if (Game.checkWin()) {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Game Win");
						alert.setHeaderText("You have won!");
						ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
				        alert.getButtonTypes().setAll(exitButton);
						alert.showAndWait();
						done = true;
					}
					if(done)
						Platform.exit();
					messages.setText("");
					} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e) {
					messages.setText(e.getMessage());
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Exception found");
					alert.setHeaderText(e.getMessage());
					alert.showAndWait();
					}
				}
			});
		useSpecialButton.setOnMouseClicked(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				try {
					selectedHero.useSpecial();
					refreshMap();
					refreshHeroStats();
					messages.setText("");
					
				} catch (NoAvailableResourcesException | InvalidTargetException e) {
					messages.setText(e.getMessage());
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Exception found");
					alert.setHeaderText(e.getMessage());
					alert.showAndWait();
					}
				}
			});
		attackButton.setOnMouseClicked(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
					try {
						selectedHero.attack();
						refreshMap();
						refreshHeroStats();
						boolean done = false;
						if (Game.checkGameOver() && !done) {
							Alert alert = new Alert(Alert.AlertType.WARNING);
							alert.setTitle("Game Over");
							alert.setHeaderText("You have lost :(");
							ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
					        alert.getButtonTypes().setAll(exitButton);
							alert.showAndWait();
							done = true;
						}
						if (done) {
							Platform.exit();
						}
						messages.setText("");
					} catch (NotEnoughActionsException | InvalidTargetException e) {
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
					}
				}
			});
		attackButton.setOnMousePressed(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				if((selectedHero.getTarget() instanceof Zombie)){
					Image image = new Image("Sword Narrow Clicked.png");
			        ImageView imageView = new ImageView(image);
			        imageView.setFitHeight(70);
			        imageView.setFitWidth(70);
			        attackButton.setGraphic(imageView);
				}
			}});
		
		attackButton.setOnMouseReleased(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				if((selectedHero.getTarget() instanceof Zombie)){
				Image image = new Image("Sword Narrow.png");
		        ImageView imageView = new ImageView(image);
		        imageView.setFitHeight(70);
		        imageView.setFitWidth(70);
		        attackButton.setGraphic(imageView);
				}
			}});
		left.setOnMouseClicked(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
					try {
						selectedHero.move(Direction.DOWN);
						refreshMap();
						refreshHeroStats();
					} catch (MovementException | NotEnoughActionsException e) {
						// TODO Auto-generated catch block
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
					}
				}
			});
		left.setOnMousePressed(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				Image image = new Image("Left arrow Clicked.png");
		        ImageView imageView = new ImageView(image);
		        imageView.setFitHeight(70);
		        imageView.setFitWidth(70);
		        left.setGraphic(imageView);
				}
				});
		
		left.setOnMouseReleased(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				Image image = new Image("Left arrow.png");
		        ImageView imageView = new ImageView(image);
		        imageView.setFitHeight(70);
		        imageView.setFitWidth(70);
		        left.setGraphic(imageView);
				}
			});
		right.setOnMouseClicked(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
					try {
						selectedHero.move(Direction.UP);
						refreshMap();
						refreshHeroStats();
					} catch (MovementException | NotEnoughActionsException e) {
						// TODO Auto-generated catch block
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
					}
				}
			});
		right.setOnMousePressed(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				Image image = new Image("Right arrow Clicked.png");
		        ImageView imageView = new ImageView(image);
		        imageView.setFitHeight(70);
		        imageView.setFitWidth(70);
		        right.setGraphic(imageView);
				}
				});
		
		right.setOnMouseReleased(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				Image image = new Image("Right arrow.png");
		        ImageView imageView = new ImageView(image);
		        imageView.setFitHeight(70);
		        imageView.setFitWidth(70);
		        right.setGraphic(imageView);
				}
			});
		up.setOnMouseClicked(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
					try {
						selectedHero.move(Direction.RIGHT);
						refreshMap();
						refreshHeroStats();
					} catch (MovementException | NotEnoughActionsException e) {
						// TODO Auto-generated catch block
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
					}
				}
			});
		up.setOnMousePressed(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				Image image = new Image("Up arrow Clicked.png");
		        ImageView imageView = new ImageView(image);
		        imageView.setFitHeight(70);
		        imageView.setFitWidth(70);
		        up.setGraphic(imageView);
				}
				});
		
		up.setOnMouseReleased(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				Image image = new Image("Up arrow.png");
		        ImageView imageView = new ImageView(image);
		        imageView.setFitHeight(70);
		        imageView.setFitWidth(70);
		        up.setGraphic(imageView);
				}
			});
		down.setOnMouseClicked(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
					try {
						selectedHero.move(Direction.LEFT);
						refreshMap();
						refreshHeroStats();
					} catch (MovementException | NotEnoughActionsException e) {
						// TODO Auto-generated catch block
						messages.setText(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Exception found");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
					}
				}
			});
		down.setOnMousePressed(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				Image image = new Image("Down arrow Clicked.png");
		        ImageView imageView = new ImageView(image);
		        imageView.setFitHeight(70);
		        imageView.setFitWidth(70);
		        down.setGraphic(imageView);
				}
				});
		
		down.setOnMouseReleased(new EventHandler <Event>(){
			@Override
			public void handle(Event event) {
				Image image = new Image("Down arrow.png");
		        ImageView imageView = new ImageView(image);
		        imageView.setFitHeight(70);
		        imageView.setFitWidth(70);
		        down.setGraphic(imageView);
				}
			});
	}
	
	public static void refreshHeroStats(){
		heroes.getChildren().clear();
		for(int i = 0; i < Game.heroes.size(); i++) {
			HBox cont = new HBox();
			VBox eachHero = new VBox();
			cont.setPadding(new Insets(10,10,10,10));
			Label remainingActions = new Label("");
			cont.setStyle("-fx-background-color: black;");
			if(Game.heroes.get(i) == selectedHero) {
				cont.setStyle(cont.getStyle()+"-fx-border-color: white; -fx-border-width: 3px;");
				remainingActions.setText("Remaining actions: " + selectedHero.getActionsAvailable());
				remainingActions.setTextFill(Paint.valueOf("white"));
			}
			Label name = new Label(Game.heroes.get(i).getName());
			name.setTextFill(Paint.valueOf("white"));
			Label actions = new Label("Max Actions: " + Game.heroes.get(i).getMaxActions());
			actions.setTextFill(Paint.valueOf("white"));
			Label damage = new Label("Damage: " + Game.heroes.get(i).getAttackDmg());
			damage.setTextFill(Paint.valueOf("white"));
			Label health = new Label("Health: " + Game.heroes.get(i).getCurrentHp()+"");
			health.setTextFill(Paint.valueOf("white"));
			Image remainImg;
			if(Game.heroes.get(i) instanceof Fighter)
				remainImg = new Image("fighter cell.png");
			else if(Game.heroes.get(i) instanceof Medic)
				remainImg = new Image("medic.jpg");
			else 
				remainImg = new Image("explorer.png");
			ImageView remainImgView = new ImageView(remainImg);
			remainImgView.setFitHeight(50);
			remainImgView.setFitWidth(50);

			HBox vaccines = new HBox();
			vaccines.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
			HBox supplies = new HBox();
			supplies.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
			vaccines.setSpacing(3);
			supplies.setSpacing(3);
			for(int j = 0; j < Game.heroes.get(i).getVaccineInventory().size(); j++) {
				Image syr = new Image("syringe.png");
				ImageView img = new ImageView(syr);
				img.setFitHeight(20);
				img.setFitWidth(20);

				vaccines.getChildren().addAll(img);
			}
			for(int j = 0; j < Game.heroes.get(i).getSupplyInventory().size(); j++) {
				Image sup = new Image("supply.jpg");
				ImageView img = new ImageView(sup);
				img.setFitHeight(20);
				img.setFitWidth(20);

				supplies.getChildren().addAll(img);
			}
			int j=i;
	         cont.setOnMouseClicked(new EventHandler <Event>(){
	 			@Override
	 			public void handle(Event event) {
	 				selectedHero = Game.heroes.get(j);
	 				refreshHeroStats();
	 				refreshMap();
	 				}
	 			});
	        Region spacer = new Region();
	        HBox.setHgrow(spacer, Priority.ALWAYS);
			eachHero.getChildren().addAll(name,actions,damage,health,vaccines,supplies,remainingActions);
			cont.getChildren().addAll(eachHero,spacer,remainImgView);
			cont.setTranslateY(200);
			heroes.getChildren().addAll(cont);
		}	
	}
	public static boolean isAdj(Point p1,Point p2){
		if(Math.abs(p1.x-p2.x)<=1&&Math.abs(p1.y-p2.y)<=1){
			return true;
		}
		return false;
	}
	
	public static void refreshActionButtons(){
//		if(selectedHero.getTarget() instanceof Hero || selectedHero.getTarget()==null){
//			ImageView view = new ImageView(new Image("Sword Narrow unclickable new.png"));
//			view.setFitHeight(70);
//			view.setFitWidth(70);
//			attackButton.setGraphic(view);
//			cureButton.setBackground(null);
//		}else{
//			ImageView view = new ImageView(new Image("Sword Narrow.png"));
//			view.setFitHeight(70);
//			view.setFitWidth(70);
//			attackButton.setGraphic(view);
//			cureButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
//
//		}
	}
	public static void refreshMap(){
		map.getChildren().clear();
		for(int i = 14; i >= 0; i--) {
			for(int j = 14; j >= 0; j--) {
				if(!Game.map[i][j].isVisible()) {
					MapCell notVisibleCell = new MapCell("nonVisible light grass.png");
					map.add(notVisibleCell, i, 14-j);
				} else {
					if(Game.map[i][j] instanceof CharacterCell) {
						MapCell characterCellButton;
						String charImg = null;
						Character cellCharacter = ((CharacterCell)Game.map[i][j]).getCharacter();
						if(cellCharacter!=null){
							if(cellCharacter instanceof Zombie)
								charImg = "zombie.jpg";
							else if(cellCharacter instanceof Fighter)
								charImg = "fighter cell.png";
							else if(cellCharacter instanceof Medic)
								charImg = "medic.jpg";
							else if(cellCharacter instanceof Explorer)
								charImg = "explorer.png";
							characterCellButton = new MapCell(charImg);
							if(cellCharacter==selectedHero.getTarget()){
								characterCellButton.setStyle("-fx-padding: 0;-fx-border-color: blue; -fx-border-width: 2px;");
							}
							if (cellCharacter == selectedHero) {
								characterCellButton.setPadding(new Insets(0));
								characterCellButton.setStyle("-fx-border-color: red;");
							}
							characterCellButton.setOnMouseClicked(new EventHandler <Event>(){
								@Override
								public void handle(Event event) {
									selectedHero.setTarget(cellCharacter);
									refreshMap();
									refreshActionButtons();
								}
							});
							if(!(cellCharacter instanceof Zombie))
								map.add(characterCellButton, i, 14-j);
							else {
								ProgressBar zombieHealth = new ProgressBar(((float)((CharacterCell)Game.map[i][j]).getCharacter().getCurrentHp())/((float)((CharacterCell)Game.map[i][j]).getCharacter().getMaxHp()));
								zombieHealth.setTranslateY(20);
								zombieHealth.setMaxHeight(10);
								StackPane zombieAll = new StackPane();
								zombieAll.getChildren().addAll(characterCellButton,zombieHealth);
								map.add(zombieAll, i, 14-j);
							}
						}else {
							MapCell emptyCellButton = new MapCell("light grass.png");
							int x = i;
							int y = j;
							emptyCellButton.setOnMouseClicked(new EventHandler <Event>(){
								@Override
								public void handle(Event event){
									if(isAdj(selectedHero.getLocation(),new Point(x,y))){
										System.out.println("make button available to move to " + x + " " + y);
									}
								}
							});
							map.add(emptyCellButton, i, 14-j);
						}
					}else {
						//do it that there is no way it cannot be instatiated
						MapCell collectibleCell = new MapCell("Right Arrow.png");
						if(Game.map[i][j] instanceof TrapCell) {
							collectibleCell = new MapCell("light grass.png");
						} else if(Game.map[i][j] instanceof CollectibleCell) {
							if(((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine) {
								collectibleCell = new MapCell("syringe.png");
								collectibleCell.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
							} else {
								collectibleCell = new MapCell("supply.jpg");
							}
						}
						map.add(collectibleCell, i, 14-j);
					}
				}
			}
		}
//		for(int i = 14; i >= 0; i--) {
//			for(int j = 14; j >= 0; j--) {
//				if(!Game.map[i][j].isVisible()) {
//					MapCell notVisibleCell = new MapCell("nonVisible light grass.png");
//					map.add(notVisibleCell, i, 14-j);
//				} else {
//					if(Game.map[i][j] instanceof CharacterCell) {
//						MapCellBar characterCellButton;
//						String charImg = null;
//						Character cellCharacter = ((CharacterCell)Game.map[i][j]).getCharacter();
//						if(cellCharacter!=null){
//							if(cellCharacter instanceof Zombie)
//								charImg = "zombie.jpg";
//							else if(cellCharacter instanceof Fighter)
//								charImg = "fighter cell.png";
//							else if(cellCharacter instanceof Medic)
//								charImg = "medic.jpg";
//							else if(cellCharacter instanceof Explorer)
//								charImg = "fighter.jpg";
//							characterCellButton = new MapCellBar(cellCharacter.getMaxHp(),cellCharacter.getCurrentHp(),charImg);
//							if(cellCharacter==selectedHero.getTarget()){
//								characterCellButton.setStyle("-fx-padding: 0;-fx-border-color: blue; -fx-border-width: 2px;");
//							}
//							characterCellButton.setOnMouseClicked(new EventHandler <Event>(){
//								@Override
//								public void handle(Event event) {
//									selectedHero.setTarget(cellCharacter);
//									refreshMap();
//									refreshActionButtons();
//								}
//							});
//							map.add(characterCellButton, i, 14-j);
//						}else {
//							MapCell emptyCellButton = new MapCell("light grass.png");
//							int x = i;
//							int y = j;
//							emptyCellButton.setOnMouseClicked(new EventHandler <Event>(){
//								@Override
//								public void handle(Event event){
//									if(isAdj(selectedHero.getLocation(),new Point(x,y))){
//										System.out.println("make button available to move to " + x + " " + y);
//									}
//								}
//							});
//							map.add(emptyCellButton, i, 14-j);
//						}
//					}else {
//						//do it that there is no way it cannot be instatiated
//						MapCell collectibleCell = new MapCell("Right Arrow.png");
//						if(Game.map[i][j] instanceof TrapCell) {
//							collectibleCell = new MapCell("trap cell.png");
//						} else if(Game.map[i][j] instanceof CollectibleCell) {
//							if(((CollectibleCell)Game.map[i][j]).getCollectible() instanceof Vaccine) {
//								collectibleCell = new MapCell("syringe.png");
//							} else {
//								collectibleCell = new MapCell("supply.jpg");
//							}
//						}
//						map.add(collectibleCell, i, 14-j);
//					}
//				}
//			}
//		}
	}
}
