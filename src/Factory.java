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


public class Factory extends Map{

	String name = "Old Factory";
	ImageView map = new ImageView(new Image("maps/factory.png"));
	ArrayList<Character> residents;
	
	Media theme = new Media(new String(Main.class.getResource("themes/emptyTown.mp3").toString()));
	MediaPlayer m = new MediaPlayer(theme);
	
	
	boolean eventUsed = false;
	boolean event2Used = false;
	boolean event3Used = false;
	Button event = new Button("Event");
	Button event2 = new Button("Event");
	Button event3 = new Button("Event");
	Button exit = new Button("Exit Old Factory");
	Button party = new Button("View Party");
	
	public void createMap(ArrayList<Character> c, Stage ps)
	{
		residents = c;
		
		m.setCycleCount(MediaPlayer.INDEFINITE);
		
		event.setLayoutX(120); event.setLayoutY(350);
		event.setOnAction(ea -> displayEvent(ps));
		
		event2.setLayoutX(920); event2.setLayoutY(330);
		event2.setOnAction(ea -> displayEvent2(ps));
		
		event3.setLayoutX(1110); event3.setLayoutY(360);
		event3.setOnAction(ea -> displayEvent3(ps));
		
		party.setLayoutX(580); party.setLayoutY(660);
		party.setPrefSize(120, 40);
		party.setOnAction(ea -> displayParty(ps));
		
		eventUsed = false;
		event2Used = false;
		event3Used = false;
		
		
	}
	
	public void displayMap(Stage s)
	{
		m.play();
		
		Pane pane = new Pane();
		pane.getChildren().add(map);
		
		if (!eventUsed)
		{
			pane.getChildren().add(event);
		}
		pane.getChildren().add(event2);
		if (!event3Used)
		{
			pane.getChildren().add(event3);
		}
		pane.getChildren().add(party);
		
		Scene scene = new Scene(pane);
		s.setScene(scene);
		s.show();
	}
	
	private void displayEvent(Stage ps)
	{
		Font sixt = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 16);
		
		ImageView special = new ImageView(new Image("maps/factoryMachine.png"));
		
		Label specialText = new Label("This machine allows the many to become one! Of course you wouldn't all want to be merged together, but you could try it "
				+ "and reverse the process after. (Will result in everyone's hp being collected and split evenly, then adding 10 more hp (not max hp) to each "
				+ "character.)");
		specialText.setWrapText(true);
		specialText.setMaxWidth(280);
		specialText.setFont(sixt);
		ImageView specialBox = new ImageView(new Image("others/textLog1-2.jpg"));
		specialBox.setFitWidth(300); specialBox.setFitHeight(520);
		StackPane specialTextBox = new StackPane(specialBox, specialText);
		specialTextBox.setLayoutX(980);
		
		ImageView buttonBox = new ImageView(new Image("others/textLog1-2.jpg"));
		buttonBox.setFitWidth(300); buttonBox.setFitHeight(100);
		ImageView buttonBox2 = new ImageView(new Image("others/textLog1-2.jpg"));
		buttonBox2.setFitWidth(300); buttonBox2.setFitHeight(100);
		Button action = new Button("Fun!");
		action.setPrefSize(200, 70);
		
		int totalhp = 0;
		for (int i = 0; i < Main.party.size(); i++)
		{
			totalhp += Main.party.get(i).getHP();
		}
		final int thp = totalhp;
		
		action.setOnAction(e -> {
			for (int j = 0; j < Main.party.size(); j++)
			{
				Main.party.get(j).changeHP(-Main.party.get(j).getHP());
				Main.party.get(j).changeHP(thp/Main.party.size() + 10);
			}
			eventUsed = true;
			displayMap(ps);
		});
		
		Button back = new Button("Disturbing!");
		back.setPrefSize(200, 70);
		back.setOnAction(e -> displayMap(ps));
		
		StackPane aButton = new StackPane(buttonBox, action);
		StackPane bButton = new StackPane(buttonBox2, back);
		
		VBox buttons = new VBox(aButton, bButton);
		buttons.setLayoutX(980); buttons.setLayoutY(520);
		
		Pane eventScreen = new Pane();
		eventScreen.getChildren().addAll(special, specialTextBox, buttons);
			
		VBox members = new VBox();
		for (int j = 0; j < Main.party.size(); j++)
		{
			Character player = Main.party.get(j);
			ProgressBar hpBar = new ProgressBar();
			hpBar.setPrefSize(130, 20);
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
			smallHPBar.setPrefSize(150, 25);
				
			Label name = new Label(Main.party.get(j).getName());
			StackPane nameHolder = new StackPane(name);
			nameHolder.setPrefSize(100, 25);
			HBox infos = new HBox(nameHolder, smallHPBar);
			ImageView f = new ImageView(new Image("others/textLog1.jpg"));
			f.setFitWidth(250); f.setFitHeight(25);
			StackPane background = new StackPane(f, infos);
			members.getChildren().add(background);
		}
		eventScreen.getChildren().add(members);
		Scene s = new Scene(eventScreen);
		ps.setScene(s);
	}
	
	private void displayEvent2(Stage ps)
	{
		Font sixt = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 16);
		
		ImageView special = new ImageView(new Image("maps/factoryDoor.png"));
		
		Label specialText = new Label("It looks as if this exit may lead to the final boss. It also looks like that once you open the door, the factory is set to "
				+ "self destruct as well as release the scary monster in the blue fluid! After opening this door, there's no going back.");
		specialText.setWrapText(true);
		specialText.setMaxWidth(280);
		specialText.setFont(sixt);
		ImageView specialBox = new ImageView(new Image("others/textLog1-2.jpg"));
		specialBox.setFitWidth(300); specialBox.setFitHeight(520);
		StackPane specialTextBox = new StackPane(specialBox, specialText);
		specialTextBox.setLayoutX(980);
		
		ImageView buttonBox = new ImageView(new Image("others/textLog1-2.jpg"));
		buttonBox.setFitWidth(300); buttonBox.setFitHeight(100);
		ImageView buttonBox2 = new ImageView(new Image("others/textLog1-2.jpg"));
		buttonBox2.setFitWidth(300); buttonBox2.setFitHeight(100);
		Button action = new Button("Touch the door!");
		
		action.setPrefSize(200, 70);
		action.setOnAction(e -> {
			Main.currentMap.getTheme().stop();
			Main.m = new MediaPlayer(residents.get(0).getTheme());
			Main.m.setVolume(.16*Main.volume*residents.get(0).getVolume());
			Main.m.setCycleCount(MediaPlayer.INDEFINITE);
			Main.m.play();
			Main.chooseFighter(null, residents.get(0), Main.party, ps);
		});
		
		Button back = new Button("Back to Old Factory");
		back.setPrefSize(200, 70);
		back.setOnAction(e -> displayMap(ps));
		
		StackPane aButton = new StackPane(buttonBox, action);
		StackPane bButton = new StackPane(buttonBox2, back);
		
		VBox buttons = new VBox(aButton, bButton);
		buttons.setLayoutX(980); buttons.setLayoutY(520);
		
		Pane eventScreen = new Pane();
		eventScreen.getChildren().addAll(special, specialTextBox, buttons);
			
		Scene s = new Scene(eventScreen);
		ps.setScene(s);
	}
	
	private void displayEvent3(Stage ps)
	{
		Font sixt = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 16);
		
		ImageView special = new ImageView(new Image("maps/drb.png"));
		
		Label specialText = new Label("You are greeted by Dr. B and his trusty assistant Big L. \"Wee hee hee! Finally some test subjects for this very mysterious "
				+ "concoction I've put together! I need you to sign this contract if I'm to legally use it on you! Wee hee hee!\"");
		specialText.setWrapText(true);
		specialText.setMaxWidth(280);
		specialText.setFont(sixt);
		ImageView specialBox = new ImageView(new Image("others/textLog1-2.jpg"));
		specialBox.setFitWidth(300); specialBox.setFitHeight(520);
		StackPane specialTextBox = new StackPane(specialBox, specialText);
		specialTextBox.setLayoutX(980);
		
		ImageView buttonBox = new ImageView(new Image("others/textLog1-2.jpg"));
		buttonBox.setFitWidth(300); buttonBox.setFitHeight(100);
		ImageView buttonBox2 = new ImageView(new Image("others/textLog1-2.jpg"));
		buttonBox2.setFitWidth(300); buttonBox2.setFitHeight(100);
		Button action = new Button("Sign the contract.");
		action.setPrefSize(200, 70);
		
		int spiderman = 0;
		
		if (!event2Used)
		{
			for (int i = 0; i < Main.party.size(); i++)
			{
				if (Main.party.get(i).getMoveName(2).length() >= 15 && Main.party.get(i).getMoveName(2).substring(0, 15).equals("Andrew Garfield"))
				{
					action.setText("(Spiderman) Inject pizza time.");
					spiderman = i;
				}
			}
		}
		
		final int sp = spiderman;
		
		action.setOnAction(e -> {
			if (action.getText().equals("(Spiderman) Inject pizza time."))
			{
				specialText.setText("I may be evil, but even the most hardened criminals wouldn't dream of denying a nice boy his pizza injection. (Spiderman gets "
						+ "a pizza injection.)");
				Main.party.get(sp).addMisc(1);
				event2Used = true;
				action.setText("Sign the contract.");
			}
			
			else
			{
				for (int j = 0; j < Main.party.size(); j++)
				{
					Main.party.get(j).changeHP((int)(Math.random()*25 - 5));
					if (Main.party.get(j).getHP() == 0)
					{	
						Main.party.get(j).changeHP(1);
					}
					
					int psn = (int)(Math.random()*10);
					int heal = (int)(Math.random()*10);
					int weak = (int)(Math.random()*4);
					int rage = (int)(Math.random()*4);
					
					if (psn == 0)
					{
						Main.party.get(j).addStat("PSN");
					}
					if (heal == 0)
					{
						int cycle = (int)(Math.random()*3 + 1);
						for (int x = 0; x < cycle; x++)
						{
							Main.party.get(j).addStat("heal");
						}
					}
					if (weak == 0)
					{
						Main.party.get(j).addStat("weak");
					}
					if (rage == 0)
					{
						Main.party.get(j).addStat("rage");
					}
				}
				event3Used = true;
				displayMap(ps);
			}
			
		});
		
		
		
		Button back = new Button("No way, Dr. B!");
		back.setPrefSize(200, 70);
		back.setOnAction(e -> displayMap(ps));
		
		StackPane aButton = new StackPane(buttonBox, action);
		StackPane bButton = new StackPane(buttonBox2, back);
		
		VBox buttons = new VBox(aButton, bButton);
		buttons.setLayoutX(980); buttons.setLayoutY(520);
		
		Pane eventScreen = new Pane();
		eventScreen.getChildren().addAll(special, specialTextBox, buttons);
			
		VBox members = new VBox();
		for (int j = 0; j < Main.party.size(); j++)
		{
			Character player = Main.party.get(j);
			ProgressBar hpBar = new ProgressBar();
			hpBar.setPrefSize(130, 20);
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
			smallHPBar.setPrefSize(150, 25);
				
			Label name = new Label(Main.party.get(j).getName());
			StackPane nameHolder = new StackPane(name);
			nameHolder.setPrefSize(100, 25);
			HBox infos = new HBox(nameHolder, smallHPBar);
			ImageView f = new ImageView(new Image("others/textLog1.jpg"));
			f.setFitWidth(250); f.setFitHeight(25);
			StackPane background = new StackPane(f, infos);
			members.getChildren().add(background);
		}
		eventScreen.getChildren().add(members);
		Scene s = new Scene(eventScreen);
		ps.setScene(s);
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
	
	public void changeEvent(int i)
	{
		if (i == 1)
		{
			eventUsed = !eventUsed;
		}
		else if (i == 2)
		{
			event2Used = !event2Used;
		}
		else
		{
			event3Used = !event3Used;
		}
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
	
	public boolean eventUsed(int i)
	{
		if (i == 1)
			return eventUsed;
		else if (i == 2)
			return event2Used;
		else
			return event3Used;
	}
}
