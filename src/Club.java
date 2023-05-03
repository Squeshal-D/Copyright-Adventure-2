import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BoxBlur;
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
import javafx.util.Duration;


public class Club extends Map{

	String name = "Da Club";
	ImageView map = new ImageView(new Image("maps/daClub.png"));
	ArrayList<Character> residents;
	
	Media theme = new Media(new String(Main.class.getResource("themes/GTASA.mp3").toString()));
	MediaPlayer m = new MediaPlayer(theme);
	
	boolean eventUsed = false;
	boolean event2Used = false;
	Button event = new Button("Event");
	Button exit = new Button("Exit Da Club");
	Button party = new Button("View Party");
	
	MediaPlayer wow;
	MediaPlayer die;
	MediaPlayer evmp; 
	MediaPlayer last;
	
	public void createMap(ArrayList<Character> c, Stage ps)
	{
		residents = c;
		
		m.setCycleCount(MediaPlayer.INDEFINITE);
		
		exit.setPrefSize(200, 100);
		exit.setLayoutX(50); exit.setLayoutY(570);
		exit.setOnAction(e -> {
			m.stop();
			Main.areaChoiceScreen(ps);
		});
		
		event.setLayoutX(1000); event.setLayoutY(480);
		event.setOnAction(ea -> displayEvent(ps));
		
		party.setLayoutX(580); party.setLayoutY(660);
		party.setPrefSize(120, 40);
		party.setOnAction(ea -> displayParty(ps));
		
		eventUsed = false;
		event2Used = false;
	}
	
	public void displayMap(Stage s)
	{
		if (residents.size() < 3)
		{
			map.setImage(new Image("maps/daClub3.png"));
		}
		else if (residents.size() < 4)
		{
			map.setImage(new Image("maps/daClub2.png"));
		}
		else if (residents.size() < 5)
		{
			map.setImage(new Image("maps/daClub1.png"));
		}
		else
		{
			map.setImage(new Image("maps/daClub.png"));
		}
		
		m.play();
		
		Pane pane = new Pane();
		pane.getChildren().add(map);
		
		if (residents.size() == 1)
		{
			pane.getChildren().add(residents.get(0).getButton());
		}
		else
		{
			for (int i = 0; i < residents.size(); i++)
			{
				if (!residents.get(i).isBoss())
				{
					pane.getChildren().add(residents.get(i).getButton());
				}
			}
		}
		if (!eventUsed && residents.size() < 3)
		{
			pane.getChildren().add(event);
		}
		if (residents.size() == 0)
		{
			pane.getChildren().add(exit);
		}
		pane.getChildren().add(party);
		
		Scene scene = new Scene(pane);
		s.setScene(scene);
		s.show();
	}
	
	private void displayEvent(Stage ps)
	{
		Font sixt = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 16);
		
		int spiderman = 0;
		ImageView special = new ImageView(new Image("maps/clubEvent.png"));
		
		Label specialText = new Label("Hi, im the owner of this place ;). My name is Kam Samp but you can call me \n\n" + 
				"SEXY. *flexes*\n\n" + 
				"In order to find out if you’re truly good enough for me to keep talking to, I’m gonna have to ask you a couple of questions.\n(+3 hp to party "
				+ "per correct answer)");
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
		Button action = new Button("Alright bruh.");
		action.setPrefSize(200, 70);
		
		if (!event2Used)
		{
			for (int i = 0; i < Main.party.size(); i++)
			{
				if (Main.party.get(i).getMoveName(2).length() >= 15 && Main.party.get(i).getMoveName(2).substring(0, 15).equals("Andrew Garfield"))
				{
					action.setText("(Spiderman) Pizza Time!");
					spiderman = i;
				}
			}
		}
		
		final int x = spiderman;
		action.setOnAction(e -> {
			
			if (action.getText().equals("(Spiderman) Pizza Time!"))
			{
				specialText.setText("Oh, you want that pizza, little dude? Sure bro, I got a lot more in the back anyways. (Spiderman munches the pizza heartily.)");
				Main.party.get(x).addMisc(1);
				event2Used = true;
				action.setText("Alright, bruh.");
			}
			else
			{
				m.stop();
				eventUsed = true;
				displayEvent2(ps);
			}
			
		});
		
		Button back = new Button("I'll think about it.");
		back.setPrefSize(200, 70);
		back.setOnAction(e -> displayMap(ps));
		
		StackPane aButton = new StackPane(buttonBox, action);
		StackPane bButton = new StackPane(buttonBox2, back);
		
		VBox buttons = new VBox(aButton, bButton);
		buttons.setLayoutX(980); buttons.setLayoutY(520);
		
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
		
		Pane eventScreen = new Pane();
		eventScreen.getChildren().addAll(special, specialTextBox, buttons, members);
			
		Scene s = new Scene(eventScreen);
		ps.setScene(s);
	}
		
	private void displayEvent2(Stage ps)
	{
		Font nine = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 9);
		Font fort = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 14);
		Font twfr = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 24);
		
		evmp = new MediaPlayer(new Media(new String(Main.class.getResource("themes/refreshingToilets.mp3").toString())));
		evmp.setVolume(.24*Main.volume);
		evmp.setCycleCount(MediaPlayer.INDEFINITE);
		evmp.play();
		
		last = new MediaPlayer(new Media(new String(Main.class.getResource("others/ootItemGet.mp3").toString())));
		last.setVolume(.32*Main.volume);
		
		ImageView bg = new ImageView(new Image("maps/daClub.png"));
		BoxBlur blur = new BoxBlur();
		blur.setWidth(5) ;blur.setHeight(5);
		bg.setEffect(blur);
		
		Image neutral = new Image("others/sampNeutral.png");
		Image angry = new Image("others/sampAngry.png");
		Image happy = new Image("others/sampHappy.png");
		
		ImageView samp = new ImageView(neutral);
		
		Timeline sampShake = new Timeline();
		
		ImageView samBox = new ImageView(new Image("others/textLog1.jpg"));
		samBox.setFitWidth(1200); samBox.setFitHeight(40);
		
		Label samText = new Label("");
		samText.setFont(twfr);
		samText.setWrapText(true);
		samText.setMaxWidth(1120);
		samText.setAlignment(Pos.CENTER);
		
		StackPane topBox = new StackPane(samBox, samText);
		topBox.setLayoutX(40); topBox.setLayoutY(590);
		
		//insert pictures of sam
		
		Button ba = new Button();
		ba.setPrefSize(1200, 20);
		Button bb = new Button();
		bb.setPrefSize(1200, 20);
		Button bc = new Button();
		bc.setPrefSize(1200, 20);
		
		Label la = new Label("");
		la.setMouseTransparent(true);
		la.setFont(fort);
		la.setWrapText(true);
		la.setMaxWidth(1280);
		la.setAlignment(Pos.CENTER);
		Label lb = new Label("");
		lb.setMouseTransparent(true);
		lb.setFont(fort);
		lb.setWrapText(true);
		lb.setMaxWidth(1280);
		lb.setAlignment(Pos.CENTER);
		Label lc = new Label("");
		lc.setMouseTransparent(true);
		lc.setFont(fort);
		lc.setWrapText(true);
		lc.setMaxWidth(1280);
		lc.setAlignment(Pos.CENTER);
		
		StackPane sa = new StackPane(ba, la);
		sa.setPrefSize(1280, 30);
		StackPane sb = new StackPane(bb, lb);
		sb.setPrefSize(1280, 30);
		StackPane sc = new StackPane(bc, lc);
		sc.setPrefSize(1280, 30);
		
		VBox buttons = new VBox(sa, sb, sc);
		buttons.setLayoutY(630);
		
		Pane eventScreen = new Pane(bg, samp, topBox, buttons);
		Scene s = new Scene(eventScreen);
		ps.setScene(s);
		
		String[] questions = {"What is my favorite soda?", "What is better: Xbox or Playstation?", "What is the best Call of Duty game no cap?",
				"Am I too sexy for you?", "Would you let me into your house at 1:00AM to use your bathroom?"};
		
		String[][] answers = {{"black cherry soda", "grape flavored fanta", "A&W vanilla root beer"},
				{"Xbox forever, bro!", "Playstation life, cuh.", "Xbox and Playstation are basically the same. Missed me with that."},
				{"Black Ops 2", "Ghosts", "Newest installment is always the best one."},
				{"Yes 11/10 and I'm looking like PUBG.", "I believe that personality wins over looks, and I'm looking like PUBG.", "There is no such thing as too sexy."},
				{"Of course.", "No.", "Why?"}};
		
		String[] finalA = {"Aquire the power up zombie blood and while under its effects you are able to see a red plane, shoot it down, "
				+ "then kill an invisible zombie which is only visible while under the effects of zombie blood. "
				+ "Zombie blood is able to be found after extinguishing 7 carts that are on fire around the excavation site. "
				+ "After killing the zombie pick up the maxis drone.",
				"All four players must step on the four golden rings on top of four elevators. One spot is on the elevator that leads to the Bowie Knife "
				+ "and is accessible from the power room. Another spot is on the elevator near the Remington 870 MCS and the MP5. "
				+ "The other spots are on top of the Quick Revive elevator and the other elevator next to Quick Revive, "
				+ "both elevators are adjacent to where the player builds the Trample Steam. All four players must step on them simultaneously.",
				"The players, preferably all of them, must go underneath the Transmission Tower, located on the opposite side of the road of Nacht der Untoten "
				+ "after a small maze in the cornfield. The player with the Thrustodyne Aeronautics Model 23 should shoot it under the tower until it breaks, "
				+ "which is when Richtofen speaks to Samuel. The players now need to kill zombies under the tower. The players must kill zombies with explosives "
				+ "(the Ray Gun counts) until Richtofen speaks to Samuel about the power being too high."};
	
		String[] correct = {"Holy F! That's what I wanted to hear!", "If only everyone could answer like you...", "I'd take no other answer.",
				"So dreamy...", "Wrong. Just kidding! sksksksksks!", "Were you right? Well, is Naruto's jumpsuit orange?"};
		
		//Timeline plays when player successfully answers all questions
		Timeline win = new Timeline();
		//Timeline plays when player fails to correctly answer a question
		Timeline lose = new Timeline();
		//Plays after a successfully answered question
		Timeline intermission = new Timeline();
		//The last question is timed, so it's a timeline instead of a task
		Timeline finalQ = new Timeline();
		//Holds all the tasks that change the questions
		ArrayList <Task<Void>> frames = new ArrayList <Task<Void>>();
		
		//start of WIN
		KeyFrame win1 = new KeyFrame(Duration.ZERO, ae -> {
			last.play();
			samp.setImage(happy);
			samText.setText("Woah, you are so knowledgable. We should go to the club sometime!");
			eventScreen.getChildren().remove(buttons);
			evmp.stop();
		});
		KeyFrame win2 = new KeyFrame(Duration.seconds(5), ae -> {
			Main.currentMap.displayMap(ps);
		});
		win.getKeyFrames().addAll(win1, win2);
	
		//start of LOSE
		KeyFrame lose1 = new KeyFrame(Duration.ZERO, ae -> {
			samp.setImage(angry);
			samText.setText("Sorry, stupid head. I don't think this is going to work out.");
			evmp.stop();
			
			die = new MediaPlayer(new Media(new String(Main.class.getResource("others/earthboundDeath.mp3").toString())));
			die.setVolume(.16*Main.volume);
			die.play();
			
			finalQ.stop();
			
			eventScreen.getChildren().remove(buttons);
		});
		KeyFrame lose2 = new KeyFrame(Duration.seconds(3), ae -> {
			Main.currentMap.displayMap(ps);
		});
		lose.getKeyFrames().addAll(lose1, lose2);
	
		//start of INTERMISSION
		KeyFrame i1 = new KeyFrame(Duration.ZERO, ae -> {
			samp.setImage(happy);
			sampShake.getKeyFrames().clear();
			
			for (int i = 0; i < 16; i++)
			{
				KeyFrame k = new KeyFrame(Duration.millis(i * 125), aae -> {
					samp.setLayoutX(-300 + (int)(601*Math.random()));
					samp.setLayoutY(-100 + (int)(201*Math.random()));
				});
				sampShake.getKeyFrames().add(k);
			}
			KeyFrame l = new KeyFrame(Duration.millis(1980), aae -> {
				samp.setImage(neutral);
				samp.setLayoutX(0);
				samp.setLayoutY(0);
			});
			sampShake.getKeyFrames().add(l);
			sampShake.play();
			
			samText.setText(correct[(int)(Math.random()*6)]);
			wow = new MediaPlayer(new Media(new String(Main.class.getResource("others/animeWow.mp3").toString())));
			wow.setVolume(.32*Main.volume);
			wow.play();
			eventScreen.getChildren().remove(buttons);
		});
		KeyFrame i2 = new KeyFrame(Duration.seconds(2), ae -> {
			eventScreen.getChildren().add(buttons);
		});
		intermission.getKeyFrames().addAll(i1, i2);
		
		//start of FINALQ
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> {
			samp.setImage(happy);
			sampShake.getKeyFrames().clear();
			
			for (int i = 0; i < 16; i++)
			{
				KeyFrame k = new KeyFrame(Duration.millis(i * 125), aae -> {
					samp.setLayoutX(-300 + (int)(601*Math.random()));
					samp.setLayoutY(-100 + (int)(201*Math.random()));
				});
				sampShake.getKeyFrames().add(k);
			}
			KeyFrame l = new KeyFrame(Duration.millis(1980), aae -> {
				samp.setImage(neutral);
				samp.setLayoutX(0);
				samp.setLayoutY(0);
			});
			sampShake.getKeyFrames().add(l);
			sampShake.play();
			
			samText.setText("Cool! This is going great, only one more question...");
			wow = new MediaPlayer(new Media(new String(Main.class.getResource("others/animeWow.mp3").toString())));
			wow.setVolume(.32*Main.volume);
			wow.play();
			eventScreen.getChildren().remove(buttons);
		});
		KeyFrame second = new KeyFrame(Duration.seconds(3), ae -> {
			samText.setText("What is step 5 of the Black Ops 2 Origins easter egg?");
			
			int rand = (int)(Math.random()*3);
			
			la.setText(finalA[rand]);
			lb.setText(finalA[(rand+1)%3]);
			lc.setText(finalA[(rand+2)%3]);
			
			la.setFont(nine);
			lb.setFont(nine);
			lc.setFont(nine);
			
			ba.setOnAction(aae -> {
				if (la.getText().equals(finalA[0]))
				{
					for (int x = 0; x < Main.party.size(); x++)
					{
						Main.party.get(x).changeHP(5);
					}
					finalQ.stop();
					win.play();
				}
				else
				{
					lose.play();
				}
			});
			bb.setOnAction(aae -> {
				if (lb.getText().equals(finalA[0]))
				{
					for (int x = 0; x < Main.party.size(); x++)
					{
						Main.party.get(x).changeHP(5);
					}
					finalQ.stop();
					win.play();
				}
				else
				{
					lose.play();
				}
			});
			bc.setOnAction(aae -> {
				if (lc.getText().equals(finalA[0]))
				{
					for (int x = 0; x < Main.party.size(); x++)
					{
						Main.party.get(x).changeHP(5);
					}
					finalQ.stop();
					win.play();
				}
				else
				{
					lose.play();
				}
			});
			eventScreen.getChildren().add(buttons);
		});
		KeyFrame third = new KeyFrame(Duration.seconds(13), ae -> {
			samText.setText("What's the matter? Can't recall it immediately from memory or something?");
			eventScreen.getChildren().remove(buttons);
		});
		KeyFrame fourth = new KeyFrame(Duration.seconds(16), ae -> {
			lose.play();
		});
		finalQ.getKeyFrames().addAll(first, second, third, fourth);
		
		for (int i = 0; i < 5; i++)
		{
			int rand = (int)(Math.random()*3);
			int row = i;
			
			Task<Void> k = new Task<Void>()
			{
				@Override
	            protected Void call() throws Exception{
					la.setText(answers[row][rand]);
					lb.setText(answers[row][(rand+1)%3]);
					lc.setText(answers[row][(rand+2)%3]);
				
					samText.setText(questions[row]);
					return null;
				}
			};
			
			frames.add(k);
		}
		
		
		
		ba.setOnAction(ae -> {
			for (int i = 0; i < 5; i++)
			{
				if (la.getText().equals(answers[i][0]))
				{
					for (int x = 0; x < Main.party.size(); x++)
					{
						Main.party.get(x).changeHP(3);
					}
					frames.remove(0);
					if (frames.size() == 0)
					{
						finalQ.play();
					}
					else
					{
						intermission.setOnFinished(aae -> {
							frames.get(0).run();
						});
						intermission.play();
					}
					break;
				}
				else if (i == 4)
				{
					lose.play();
				}
			}
		});
		bb.setOnAction(ae -> {
			for (int i = 0; i < 5; i++)
			{
				if (lb.getText().equals(answers[i][0]))
				{
					for (int x = 0; x < Main.party.size(); x++)
					{
						Main.party.get(x).changeHP(3);
					}
					frames.remove(0);
					if (frames.size() == 0)
					{
						finalQ.play();
					}
					else
					{
						intermission.setOnFinished(aae -> {
							frames.get(0).run();
						});
						intermission.play();
						
					}
					break;
				}
				else if (i == 4)
				{
					lose.play();
				}
			}
		});
		bc.setOnAction(ae -> {
			for (int i = 0; i < 5; i++)
			{
				if (lc.getText().equals(answers[i][0]))
				{
					for (int x = 0; x < Main.party.size(); x++)
					{
						Main.party.get(x).changeHP(3);
					}
					frames.remove(0);
					if (frames.size() == 0)
					{
						finalQ.play();
					}
					else
					{
						intermission.setOnFinished(aae -> {
							frames.get(0).run();
						});
						intermission.play();
					}
					break;
				}
				else if (i == 4)
				{
					lose.play();
				}
			}
		});
		
		frames.get(0).run();
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
		m.setVolume(1.5*vol);
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
