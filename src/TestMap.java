import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TestMap extends Map{

	//This map is for testing newly developed characters and is not to be in the final game
	
	String name = "Test Map";
	ImageView map = new ImageView(new Image("maps/testMap.jpg"));
	ArrayList<Character> residents;
	
	Media theme = new Media(new String(Main.class.getResource("themes/sorrowfulTazmily.mp3").toString()));
	MediaPlayer m = new MediaPlayer(theme);
	
	
	boolean eventUsed = false;
	Button event = new Button("Event");
	Button exit = new Button("Exit Test Map");
	Button party = new Button("View Party");
	
	public void createMap(ArrayList<Character> c, Stage ps)
	{
		residents = c;
		
		m.setCycleCount(MediaPlayer.INDEFINITE);
		
		exit.setPrefSize(200, 100);
		exit.setLayoutX(1030); exit.setLayoutY(570);
		exit.setOnAction(e -> {
			m.stop();
			Main.areaChoiceScreen(ps);
		});
		
		party.setLayoutX(580); party.setLayoutY(660);
		party.setPrefSize(120, 40);
		party.setOnAction(ea -> displayParty(ps));
		
		eventUsed = false;
	}
	
	public void displayMap(Stage s)
	{
		m.play();
		
		Pane pane = new Pane();
		pane.getChildren().add(map);
		
		HBox fights = new HBox();
		
		for (int i = 0; i < residents.size(); i++)
		{
			fights.getChildren().add(residents.get(i).getButton());
		}
		
		pane.getChildren().add(fights);
		pane.getChildren().add(exit);
		pane.getChildren().add(party);
		
		Scene scene = new Scene(pane);
		s.setScene(scene);
		s.show();
	}
	
	private void displayParty(Stage ps)
	{
		Font thir = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 30);
		Font fift = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 15);
		
		m.pause();
		Media ayw = new Media(new String(Main.class.getResource("others/asYouWish.mp3").toString()));
		MediaPlayer menu = new MediaPlayer(ayw);
		menu.setVolume(.16*Main.volume);
		menu.setCycleCount(MediaPlayer.INDEFINITE);
		menu.play();
		
		Pane displayParty = new Pane();
		Scene scene = new Scene(displayParty);
		
		ImageView move1Name = new ImageView(new Image("others/textLog1-2.jpg"));
		move1Name.setFitWidth(150); move1Name.setFitHeight(100);
		Label n1 = new Label();
		n1.setFont(fift);
		StackPane box1 = new StackPane(move1Name, n1);
		ImageView move1Desc = new ImageView(new Image("others/textLog1.jpg"));
		move1Desc.setFitWidth(830); move1Desc.setFitHeight(100);
		Label d1 = new Label();
		d1.setFont(fift);
		StackPane box11 = new StackPane(move1Desc, d1);
		HBox move1 = new HBox(box1, box11);
		
		ImageView move2Name = new ImageView(new Image("others/textLog1-2.jpg"));
		move2Name.setFitWidth(150); move2Name.setFitHeight(100);
		Label n2 = new Label();
		n2.setFont(fift);
		StackPane box2 = new StackPane(move2Name, n2);
		ImageView move2Desc = new ImageView(new Image("others/textLog1.jpg"));
		move2Desc.setFitWidth(830); move2Desc.setFitHeight(100);
		Label d2 = new Label();
		d2.setFont(fift);
		StackPane box22 = new StackPane(move2Desc, d2);
		HBox move2 = new HBox(box2, box22);
		
		ImageView move3Name = new ImageView(new Image("others/textLog1-2.jpg"));
		move3Name.setFitWidth(150); move3Name.setFitHeight(100);
		Label n3 = new Label();
		n3.setFont(fift);
		StackPane box3 = new StackPane(move3Name, n3);
		ImageView move3Desc = new ImageView(new Image("others/textLog1.jpg"));
		move3Desc.setFitWidth(830); move3Desc.setFitHeight(100);
		Label d3 = new Label();
		d3.setFont(fift);
		StackPane box33 = new StackPane(move3Desc, d3);
		HBox move3 = new HBox(box3, box33);
		
		VBox moves = new VBox(move1, move2, move3);
		moves.setLayoutX(300); moves.setLayoutY(420);
		VBox characters = new VBox();
		
		ImageView pictureHere = new ImageView(new Image("others/textLog1-2.jpg"));
		pictureHere.setFitWidth(420); pictureHere.setFitHeight(420);
		pictureHere.setLayoutX(300);
		
		ImageView descHere = new ImageView(new Image("others/textLog1-2.jpg"));
		descHere.setFitWidth(560); descHere.setFitHeight(420);
		descHere.setLayoutX(720);
		
		Label l = new Label("");
		l.setWrapText(true);
		l.setFont(thir);
		StackPane yourInfo = new StackPane(descHere, l);
		yourInfo.setPrefSize(560, 420);
		yourInfo.setLayoutX(720);
		
		for (int i = 0; i < Main.party.size(); i++)
		{
			Character player = Main.party.get(i);
			ImageView yourPortrait = player.getPortrait();
			yourPortrait.setFitWidth(420); yourPortrait.setFitHeight(420);
			yourPortrait.setLayoutX(300); yourPortrait.setLayoutY(0);
			
			ProgressBar hpBar = new ProgressBar();
			hpBar.setPrefSize(130, 25);
			double progress = (double) player.getHP()/player.getMaxHP();
			hpBar.setProgress(progress);
			if (progress <= 0.34)
			{
				hpBar.setStyle("-fx-accent: red;");
				if (progress > 0 && progress <.05)
				{
					hpBar.setProgress(.05);
				}
			}
			else if (progress <= 0.67)
			{
				hpBar.setStyle("-fx-accent: orange;");
			}
			else hpBar.setStyle("-fx-accent: lime green;");
				
			Label hp = new Label(player.getHP() + "/" + player.getMaxHP());
			StackPane smallHPBar = new StackPane(hpBar, hp);
			smallHPBar.setPrefSize(150, 720/(Main.party.size()+1));
			
			Label b = new Label(player.getName());
			StackPane nameHolder = new StackPane(b);
			nameHolder.setPrefSize(150, 720/(Main.party.size()+1));
				
			ImageView selectionWindow = new ImageView(new Image("others/textLog1-2.jpg"));
			selectionWindow.setFitWidth(300); selectionWindow.setFitHeight(720/(Main.party.size()+1));
			if (selectionWindow.getFitHeight() > 360)
			{
				selectionWindow.setFitHeight(360);
			}
			HBox eachCharacter = new HBox(nameHolder, smallHPBar);
			StackPane options = new StackPane(selectionWindow, eachCharacter);
			
			options.setOnMouseEntered(ea -> {
				l.setText(Main.enemyInfo(player));
				n1.setText(player.getMoveName(1));
				d1.setText(player.getMoveDesc(1));
				n2.setText(player.getMoveName(2));
				d2.setText(player.getMoveDesc(2));
				n3.setText(player.getMoveName(3));
				d3.setText(player.getMoveDesc(3));
				displayParty.getChildren().add(yourPortrait);
			});
			options.setOnMouseExited(ea -> {
				l.setText("");
				n1.setText("");
				d1.setText("");
				n2.setText("");
				d2.setText("");
				n3.setText("");
				d3.setText("");
				displayParty.getChildren().remove(yourPortrait);
			});
			
			characters.getChildren().add(options);
		}
		
		Button back = new Button("Back");
		back.setMaxHeight(100);
		back.setPrefSize(200, 720/(Main.party.size()+1));
		back.setOnAction(ea -> {
			displayMap(ps);
			menu.stop();
		});
		ImageView buttonBorder = new ImageView(new Image("others/textLog1-2.jpg"));
		buttonBorder.setFitWidth(300); buttonBorder.setFitHeight(720/(Main.party.size()+1));
		StackPane backHolder = new StackPane(buttonBorder, back);
		characters.getChildren().add(backHolder);
		
		
		displayParty.getChildren().addAll(characters, pictureHere, yourInfo, moves);
		ps.setScene(scene);
	}
	
	public void setVolume(double vol)
	{
		m.setVolume(vol);
	}
	
	public String getName()
	{
		return name;
	}
	
	public ImageView getImage()
	{
		return map;
	}
	
	public ArrayList<Character> getResidents()
	{
		return residents;
	}
	
	public MediaPlayer getTheme()
	{
		return m;
	}
	
	public boolean eventUsed()
	{
		return eventUsed;
	}
}
