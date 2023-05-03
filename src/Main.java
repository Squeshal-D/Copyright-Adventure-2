import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
//import javafx.application.Application;
import javafx.concurrent.Task;
//import javafx.concurrent.WorkerStateEvent;
//import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main{
	
	//all of the variable accessible by the whole program
	static Map normalTown = new Map();
	static NewBongus newBongus = new NewBongus();
	static NoMansLand noMansLand = new NoMansLand();
	static Heck heck = new Heck();
	static Factory factory = new Factory();
	static Club club = new Club();
	static TestMap testMap = new TestMap();
	static Map currentMap;
	
	static ArrayList<Character> pool = new ArrayList<Character>();
	static ArrayList<Character> party = new ArrayList<Character>();
	static ArrayList<Character> dead = new ArrayList<Character>();
	
	static ArrayList<Character> ntCharacters = new ArrayList<Character>();
	static ArrayList<Character> nbcCharacters = new ArrayList<Character>();
	static ArrayList<Character> nmlCharacters = new ArrayList<Character>();
	static ArrayList<Character> clubCharacters = new ArrayList<Character>();
	static ArrayList<Character> heckCharacters = new ArrayList<Character>();
	static ArrayList<Character> factoryCharacters = new ArrayList<Character>();
	static ArrayList<Character> testCharacters = new ArrayList<Character>();
	//make one for each map
	
	static ArrayList<Map> maps = new ArrayList<Map>();
	
	static Character flake = null;
	static boolean sleep = true;
	static MediaPlayer m;
	static MediaPlayer hit1; //hit sounds
	static MediaPlayer hit2; //hit sounds
	static MediaPlayer jingle; //victory jingles
	static MediaPlayer sfx; //deaths
	static MediaPlayer sfx2; //game overs + specials
	static Scene fightScreen;
	static Character currentEnemy;
	static Character currentFighter;
	
	static double volume = 1.25;
	
	static String name = "";
	static String verb = "";
	
	public static void load(Stage ps)
	{
		currentEnemy = null;
		currentFighter = null;
		
		Derrek derrek = new Derrek();
		Caleb caleb = new Caleb();
		Tonio tonio = new Tonio();
		Scott scott = new Scott();
		Ramsay ramsay = new Ramsay();
		
		Blart blart = new Blart();
		DJ dj = new DJ();
		Spiderman spiderman = new Spiderman();
		Hulk hulk = new Hulk();
		Gaben gaben = new Gaben();
		
		Washington washington = new Washington();
		Potter potter = new Potter();
		Spongebob spongebob = new Spongebob();
		Shrek shrek = new Shrek();
		Walmart walmart = new Walmart();
		
		Chief chief = new Chief();
		Wick wick = new Wick();
		Freeman freeman = new Freeman();
		Gandalf gandalf = new Gandalf();
		Barry barry = new Barry();
		
		Greed greed = new Greed();
		Wrath wrath = new Wrath();
		Gluttony gluto = new Gluttony();
		Envy envy = new Envy();
		Pride pride = new Pride();
		Sloth sloth = new Sloth();
		Lust lust = new Lust();
		
		Sansosbrine sansosbrine = new Sansosbrine();
		
		party.clear();
		
		//add specific party members for testing
		//party.add(derrek); derrek.refresh();
		//party.add(caleb); caleb.refresh();
		//party.add(tonio); tonio.refresh();
		//party.add(scott); scott.refresh();
		//party.add(ramsay); ramsay.refresh();
		//party.add(blart); blart.refresh();
		//party.add(washington); washington.refresh();
		//party.add(dj); dj.refresh();
		//party.add(chief); chief.refresh();
		//party.add(wick); wick.refresh();
		//party.add(hulk); hulk.refresh();
		//party.add(shrek); shrek.refresh();
		//party.add(freeman); freeman.refresh();
		//party.add(spiderman); spiderman.refresh();
		//party.add(spongebob); spongebob.refresh();
		//party.add(gandalf); gandalf.refresh();
		//party.add(potter); potter.refresh();
		
		//add all party members for testing
		/*
		party.add(derrek); derrek.refresh();
		party.add(caleb); caleb.refresh();party.add(tonio); tonio.refresh();party.add(scott); scott.refresh();party.add(ramsay); ramsay.refresh();
		party.add(blart); blart.refresh();party.add(washington); washington.refresh();party.add(dj); dj.refresh();party.add(chief); chief.refresh();
		party.add(wick); wick.refresh();party.add(hulk); hulk.refresh();party.add(shrek); shrek.refresh();party.add(freeman); freeman.refresh();
		party.add(spiderman); spiderman.refresh();party.add(spongebob); spongebob.refresh();party.add(gandalf); gandalf.refresh();party.add(potter); potter.refresh();
		*/
		//spiderman.addStat("Venom");
		//spiderman.changeHP(-65);
		
		dead.clear();
		
		pool.clear();
		pool.add(derrek);
		pool.add(caleb);
		pool.add(tonio);
		pool.add(scott);
		pool.add(ramsay);
		
		pool.add(blart);
		pool.add(dj);
		pool.add(hulk);
		pool.add(spiderman);
		pool.add(gaben);
		
		pool.add(chief);
		pool.add(wick);
		pool.add(freeman);
		pool.add(gandalf);
		pool.add(barry);
		
		pool.add(washington);
		pool.add(shrek);
		pool.add(spongebob);
		pool.add(potter);
		pool.add(walmart);
		
		for (int i = 0; i < pool.size(); i++)
		{
			Character c = pool.get(i);
			String label;
			if (c.isTesting())
			{
				label = c.getName();
			}
			else if (c.isBoss())
			{
				label = "BOSS";
			}
			else
			{
				label = "Fight";
			}
			Button b = new Button(label);
			b.setPrefSize(50, 25);
			b.setOnAction(e -> {
				currentEnemy = c;
				currentMap.getTheme().stop();
				m = new MediaPlayer(c.getTheme());
				m.setVolume(.16*Main.volume*c.getVolume());
				m.setCycleCount(MediaPlayer.INDEFINITE);
				m.play();
				chooseFighter(null, c, party, ps);
			});
			pool.get(i).createButton(b);
		}
		
		//do this for all maps
		maps.clear();
		
		ntCharacters.clear();
		ntCharacters.add(derrek);
		ntCharacters.add(caleb);
		ntCharacters.add(tonio);
		ntCharacters.add(scott);
		ntCharacters.add(ramsay);
		normalTown.createMap(ntCharacters, ps);
		maps.add(normalTown);
		
		nbcCharacters.clear();
		nbcCharacters.add(blart);
		nbcCharacters.add(dj);
		nbcCharacters.add(hulk);
		nbcCharacters.add(spiderman);
		nbcCharacters.add(gaben);
		newBongus.createMap(nbcCharacters, ps);
		maps.add(newBongus);
		
		nmlCharacters.clear();
		nmlCharacters.add(wick);
		nmlCharacters.add(freeman);
		nmlCharacters.add(chief);
		nmlCharacters.add(gandalf);
		nmlCharacters.add(barry);
		noMansLand.createMap(nmlCharacters, ps);
		maps.add(noMansLand);
		
		clubCharacters.clear();
		clubCharacters.add(potter);
		clubCharacters.add(spongebob);
		clubCharacters.add(shrek);
		clubCharacters.add(washington);
		clubCharacters.add(walmart);
		club.createMap(clubCharacters, ps);
		maps.add(club);
		
		heckCharacters.clear();
		heckCharacters.add(wrath);
		heckCharacters.add(greed);
		heckCharacters.add(gluto);
		heckCharacters.add(envy);
		heckCharacters.add(pride);
		heckCharacters.add(sloth);
		heckCharacters.add(lust);
		heck.createMap(heckCharacters, ps);
		maps.add(heck);
		
		factoryCharacters.clear();
		factoryCharacters.add(sansosbrine);
		factory.createMap(factoryCharacters, ps);
		maps.add(factory);
		
		testCharacters.clear();
		testMap.createMap(testCharacters, ps);
		//maps.add(testMap);
	}
	
	public static void titleScreen(Stage ps)
	{
		Image titleScreen = new Image("others/title.png");
		ImageView i = new ImageView(titleScreen);
		
		ImageView box = new ImageView(new Image("others/textLog1-2.jpg"));
		box.setFitWidth(400); box.setFitHeight(250);
		
		TextField nameInput = new TextField(name);
		nameInput.setPrefSize(200, 20);
		StackPane ni = new StackPane(nameInput);
		ni.setPrefSize(200, 50);
		Label nameLabel = new Label("Name:");
		StackPane nl = new StackPane(nameLabel);
		nl.setPrefSize(150,  50);
		
		TextField moveInput = new TextField(verb);
		moveInput.setPrefSize(200, 20);
		StackPane mi = new StackPane(moveInput);
		mi.setPrefSize(200, 50);
		Label moveLabel = new Label("Special Move:");
		StackPane ml = new StackPane(moveLabel);
		ml.setPrefSize(150, 50);
		
		Button start = new Button("Start");
		start.setPrefSize(300, 20);
		start.setOnAction(e ->
		{
			if (m != null)
			{
				m.stop();
			}
			Character player = new Character();
			if (!nameInput.getText().trim().isEmpty() && !moveInput.getText().trim().isEmpty())
			{
				player.create(nameInput.getText().trim(), moveInput.getText().trim());
				name = nameInput.getText().trim();
				verb = moveInput.getText().trim();
				party.add(player);

				areaChoiceScreen(ps);
			}
		});
		StackPane startBox = new StackPane(start);
		startBox.setPrefSize(400, 50);
		
		Button readme = new Button("Read First!");
		readme.setPrefSize(300, 20);
		readme.setOnAction(e -> {
			Pane rm = new Pane();
			
			ImageView tips = new ImageView(new Image("others/tips.png"));
			Button back = new Button("Back");
			back.setPrefSize(200, 100);
			back.setLayoutX(1060); back.setLayoutY(600);
			back.setOnAction(ae -> {
				titleScreen(ps);
			});
			
			rm.getChildren().addAll(tips, back);
			Scene s = new Scene(rm);
			ps.setScene(s);
		});
		StackPane readBox = new StackPane(readme);
		readBox.setPrefSize(400, 50);
		
		Slider volume = new Slider(0, 2.5, Main.volume);
		volume.setPrefWidth(100);
		volume.setOnMouseReleased(e -> Main.volume = volume.getValue());
		StackPane volumeHolder = new StackPane(volume);
		volumeHolder.setPrefSize(150, 50);
		Label volumeLabel = new Label("Volume:");
		StackPane vl = new StackPane(volumeLabel);
		vl.setPrefSize(150, 50);
		
		Button test = new Button("Test");
		test.setPrefSize(50, 20);
		test.setOnAction(e -> {
			if (m != null)
			{
				m.stop();
			}
			m = new MediaPlayer(new Media(new String(Main.class.getResource("others/caasp.mp3").toString())));
			m.setVolume(.16*Main.volume);
			m.play();
		});
		StackPane t = new StackPane(test);
		t.setPrefSize(50, 50);
		
		HBox volTest = new HBox(volumeHolder, t);
		VBox labels = new VBox(nl, ml, vl);
		VBox options = new VBox(ni, mi, volTest);
		VBox buttons = new VBox(startBox, readBox);
		buttons.setLayoutX(440); buttons.setLayoutY(620);
		
		HBox all = new HBox(labels, options);
		StackPane bg = new StackPane(box, all);
		bg.setLayoutX(440); bg.setLayoutY(470);
		
		Pane title = new Pane(i, bg, buttons);
		
		Scene scene = new Scene(title);
		ps.setScene(scene);
		
		ps.show();
	}
	
	public static void areaChoiceScreen(Stage ps)
	{
		Pane whole = new Pane();
		
		m = new MediaPlayer(new Media(new String(Main.class.getResource("others/isAnyoneThere.mp3").toString())));
		m.setVolume(.2*volume);
		m.setCycleCount(MediaPlayer.INDEFINITE);
		m.play();
		
		Button party = new Button("View Party");
		party.setLayoutX(1120); party.setLayoutY(350);
		party.setPrefSize(120, 40);
		party.setOnAction(ea -> {
			m.stop();
			displayParty(ps);
		});
		
		ImageView worldMap = new ImageView(new Image("others/recruit.png"));
		
		VBox allMaps = new VBox();
		allMaps.setPrefSize(200, 300);
		allMaps.setLayoutX(1080); allMaps.setLayoutY(420);
		ImageView background = new ImageView(new Image("others/textLog1-2.jpg"));
		background.setFitWidth(200); background.setFitHeight(300);
		background.setLayoutX(1080); background.setLayoutY(420);
		
		int amount = 0;
		int limit = 6;
		for (int i = 0; i < maps.size(); i++)
		{
			if (maps.get(i).getResidents().size() > 0)
			{
				amount++;
			}
		}
		
		if (amount > 2)
		{
			limit = 4;
		}
		else
		{
			worldMap.setImage(new Image("others/path.png"));
		}
		
		//for dev mode
		//limit = 6;
		
		for (int i = 0; i < limit; i++)
		{
			StackPane sp = new StackPane();
			sp.setPrefSize(200, 300);
			Map map;
			map = maps.get(i);
			
			if (maps.get(i).getResidents().size() > 0)
			{
				amount++;
				Button b = new Button(maps.get(i).getName());
				b.setPrefSize(150, 30);
				b.setOnAction(e -> {
					m.stop();
					map.setVolume(.16*Main.volume);
					currentMap = map;
					map.displayMap(ps);
				});
				sp.getChildren().add(b);
				allMaps.getChildren().add(sp);
			}
		}
		
		/*
		if (amount == 0)
		{
			Label end = new Label("End of demo. Purchase the full version for $99.99 when it releases in 2044");
			end.setFont(new Font("OCR A Extended", 16));
			end.setWrapText(true);
			end.setMaxWidth(180);
			StackPane sp = new StackPane(end);
			sp.setPrefSize(200, 300);
			allMaps.getChildren().add(sp);
		}
		*/
		whole.getChildren().addAll(worldMap, background, allMaps, party);
		Scene scene = new Scene(whole);
		ps.setScene(scene);
	}
	
	public static void fight(Character player, Character enemy, ArrayList<Character> party, boolean enemyFirst, Stage ps)
	{
		Font fift = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 15);
		Font sixt = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 16);
		Font twfr = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 24);
		
		Font im64 = Font.loadFont(Main.class.getResourceAsStream("impact.ttf"), 64);
		
		currentEnemy = enemy;
		currentFighter = player;
		
		int originalhp = player.getHP();
		int enemyOriginalhp = enemy.getHP();
		
		Pane windows = new Pane();
		//Background is created here
		ImageView stage = enemy.getStage();
		stage.setLayoutY(150);
		
		//Top text log is created here
		ImageView logWindow = new ImageView(new Image("others/textLog1.jpg"));
		logWindow.setFitWidth(1280);
		logWindow.setFitHeight(150);
		Label log1 = new Label("");
		log1.setFont(twfr);
		Label y = new Label(enemyInfo(player));
		y.setFont(twfr);
		StackPane youHolder = new StackPane(y);
		youHolder.setPrefSize(640, 150);
		Label e = new Label (enemyInfo(enemy));
		e.setFont(twfr);
		StackPane enemyHolder = new StackPane(e);
		enemyHolder.setPrefSize(640, 150);
		HBox infos = new HBox(youHolder, enemyHolder);
		StackPane log = new StackPane(logWindow, log1, infos);
		log.setPrefSize(1280,  150);
		
		//Bottom right text log is created here
		ImageView descsWindow = new ImageView(new Image("others/textLog1.jpg"));
		descsWindow.setFitWidth(640); descsWindow.setFitHeight(150);
		Label descsText = new Label();
		descsText.setFont(fift);
		descsText.setText("Select an action.");
		StackPane descs = new StackPane(descsWindow, descsText);
		descs.setLayoutX(640); descs.setLayoutY(570);
		
		//Bottom left text log is created here
		ImageView buttonPadWindow = new ImageView(new Image("others/textLog1.jpg"));
		buttonPadWindow.setLayoutY(570);
		buttonPadWindow.setFitWidth(640); buttonPadWindow.setFitHeight(150);
		TilePane buttons = new TilePane();
		buttons.setLayoutY(570);
		buttons.setPrefSize(640, 150);
		
		Label enemydmg = new Label("");
		enemydmg.setFont(im64);
		StackPane edmg = new StackPane(enemydmg);
		edmg.setPrefSize(210,210);
		edmg.setLayoutX(870); edmg.setLayoutY(250);
		
		Label youdmg = new Label("");
		youdmg.setFont(im64);
		StackPane pdmg = new StackPane(youdmg);
		pdmg.setPrefSize(210,210);
		pdmg.setLayoutX(200); pdmg.setLayoutY(250);
		
		ImageView you = player.getPortrait();
		you.setFitWidth(210); you.setFitHeight(210);
		you.setLayoutX(200); you.setLayoutY(250);
		
		ImageView notYou = enemy.getPortrait();
		notYou.setFitWidth(210); notYou.setFitHeight(210);
		notYou.setLayoutX(870); notYou.setLayoutY(250);
		
		ProgressBar yourhpBar = new ProgressBar();
		yourhpBar.setPrefSize(200, 40);
		double progress = (double) player.getHP()/player.getMaxHP();
		yourhpBar.setProgress(progress);
		if (progress <= 0.34)
		{
			yourhpBar.setStyle("-fx-accent: red;");
			if (progress > 0 && progress <.04)
			{
				yourhpBar.setProgress(.04);
			}
		}
		else if (progress <= 0.67)
		{
			yourhpBar.setStyle("-fx-accent: orange;");
		}
		else yourhpBar.setStyle("-fx-accent: lime green;");
		
		Label hp = new Label(player.getHP() + "/" + player.getMaxHP());
		hp.setFont(sixt);
		StackPane playerHP = new StackPane(yourhpBar, hp);
		playerHP.setLayoutX(205); playerHP.setLayoutY(500);
		
		ProgressBar enemyhpBar = new ProgressBar();
		enemyhpBar.setPrefSize(200, 40);
		double enemyprogress = (double) enemy.getHP()/enemy.getMaxHP();
		enemyhpBar.setProgress(enemyprogress);
		if (enemyprogress <= 0.34)
		{
			enemyhpBar.setStyle("-fx-accent: red;");
			if (enemyprogress > 0 && enemyprogress <.04)
			{
				enemyhpBar.setProgress(.04);
			}
		}
		else if (enemyprogress <= 0.67)
		{
			enemyhpBar.setStyle("-fx-accent: orange;");
		}
		else enemyhpBar.setStyle("-fx-accent: lime green;");
		
		Label enemyhp = new Label(enemy.getHP() + "/" + enemy.getMaxHP());
		enemyhp.setFont(sixt);
		StackPane enemyHP = new StackPane(enemyhpBar, enemyhp);
		enemyHP.setLayoutX(875); enemyHP.setLayoutY(500);
		
		ImageView charging = new ImageView(new Image("others/charging.png"));
		charging.setLayoutX(1030); charging.setLayoutY(200);
		
		
		
		
		
		
		windows.getChildren().addAll(stage, log, descs, buttonPadWindow, buttons, you, notYou, playerHP, enemyHP, pdmg, edmg);
		if (enemy.isCharging())
		{
			windows.getChildren().add(charging);
		}
		else
		{
			windows.getChildren().remove(charging);
		}
		fightScreen = new Scene(windows);
		ps.setScene(fightScreen);
		
		if (enemyFirst)//this will happen if the player switches characters in the middle of a battle
		{
			
			if (enemy.getHP() != 0 && party.size() > 0 && player.getHP() != 0)//if you're still fighting the enemy and didn't get a game over
			{
				descsText.setText("");
				log.getChildren().remove(infos);
				int original = player.getHP();
				int enemyOriginal = enemy.getHP();
				Timeline ea = enemy.enemyAttack(player, enemy, log1, ps);
				ea.setOnFinished(aaae -> {
				Task<Void> s2 = sleep(2000, Main.sleep);
				s2.setOnSucceeded(aae -> {//waits 2 seconds here
					if (!enemy.isCharging())
					{
						windows.getChildren().remove(charging);
					}
				Timeline shake2 = shake(you, notYou, original, enemyOriginal, player, enemy, enemydmg, youdmg, yourhpBar, enemyhpBar, hp, enemyhp);
				shake2.setOnFinished(lkjf -> {
					youdmg.setText("");
					enemydmg.setText("");
				Timeline stat2 = statEffect(enemy, player, ps, enemyOriginal, original, log1, enemyhpBar, yourhpBar, enemyhp, hp);
				stat2.setOnFinished(fff -> {
				Timeline ed = checkDeath(false, player, enemy, log1, m, ps);
				ed.setOnFinished(aaaaaae -> {
					
				if (enemy.getHP() != 0 && party.size() > 0 && player.getHP() != 0)
				{
					if (flake != null)
					{
						fight(flake, enemy, party, true, ps);
						flake = null;
					}
					else
					{
						fight(player, enemy, party, false, ps);
					}
				}
				else if (enemy.getHP() < 1 && party.size() > 0)
				{
					enemy.refresh();
					if (!enemy.isBoss() || enemy.getName().equals("Papa John"))
					{
						currentMap.displayMap(ps);
					}
					else if (currentMap.getName().equals("Heck") && currentMap.getResidents().size() != 0)
					{
						chooseFighter(null, currentMap.getResidents().get((int)(Math.random()*currentMap.getResidents().size())), Main.party, ps);
					}
					else
					{
						ps.setScene(enemy.bossCutscene(ps));
					}
				}
				});
				ed.play();
				});
				stat2.play();
				});
				shake2.play();
				});
				new Thread(s2).start();
				});
				ea.play();
			}
		}
		
		else if (player.isCharging())
		{
			log.getChildren().remove(infos);
			windows.getChildren().remove(buttons);//buttons are removed
			descsText.setText("");
			Timeline pa = player.enemyAttack(enemy, player, log1, ps);//executes player attack
			pa.setOnFinished(aaaae -> {
			if (flake == null || flake.getMoveName(1) != "Flake") {
			Task<Void> s1 = sleep(2000, Main.sleep);
			s1.setOnSucceeded(ae -> {//waits 2 seconds here
				if (!enemy.isCharging())
				{
					windows.getChildren().remove(charging);
				}
			Timeline shake = shake(you, notYou, originalhp, enemyOriginalhp, player, enemy, enemydmg, youdmg, yourhpBar, enemyhpBar, hp, enemyhp);
			shake.setOnFinished(dasf -> {
			youdmg.setText("");
			enemydmg.setText("");
			Timeline stat = statEffect(player, enemy, ps, originalhp, enemyOriginalhp, log1, yourhpBar, enemyhpBar, hp, enemyhp);
			stat.setOnFinished(eea -> {
			Timeline cd = checkDeath(true, player, enemy, log1, m, ps);
			cd.setOnFinished(aaaaae -> {
			
			if (enemy.getHP() != 0 && party.size() > 0 && player.getHP() != 0)//if you're still fighting the enemy and didn't get a game over
			{
				int original = player.getHP();
				int enemyOriginal = enemy.getHP();
				Timeline ea = enemy.enemyAttack(player, enemy, log1, ps);
				ea.setOnFinished(aaae -> {
				Task<Void> s2 = sleep(2000, Main.sleep);
				s2.setOnSucceeded(aae -> {//waits 2 seconds here
					if (!enemy.isCharging())
					{
						windows.getChildren().remove(charging);
					}
				Timeline shake2 = shake(you, notYou, original, enemyOriginal, player, enemy, enemydmg, youdmg, yourhpBar, enemyhpBar, hp, enemyhp);
				shake2.setOnFinished(lkjf -> {
					youdmg.setText("");
					enemydmg.setText("");
				Timeline stat2 = statEffect(enemy, player, ps, enemyOriginal, original, log1, enemyhpBar, yourhpBar, enemyhp, hp);
				stat2.setOnFinished(fff -> {
				Timeline ed = checkDeath(false, player, enemy, log1, m, ps);
				ed.setOnFinished(aaaaaae -> {
					
				if (enemy.getHP() != 0 && party.size() > 0 && player.getHP() != 0)
				{
					if (flake != null)
					{
						fight(flake, enemy, party, true, ps);
						flake = null;
					}
					else
					{
						fight(player, enemy, party, false, ps);
					}
				}
				else if (enemy.getHP() < 1 && party.size() > 0)
				{
					enemy.refresh();
					if (!enemy.isBoss() || enemy.getName().equals("Papa John"))
					{
						currentMap.displayMap(ps);
					}
					else if (currentMap.getName().equals("Heck") && currentMap.getResidents().size() != 0)
					{
						chooseFighter(null, currentMap.getResidents().get((int)(Math.random()*currentMap.getResidents().size())), Main.party, ps);
					}
					else
					{
						ps.setScene(enemy.bossCutscene(ps));
					}
				}
				});
				ed.play();
				});
				stat2.play();
				});
				shake2.play();
				});
				new Thread(s2).start();
				});
				ea.play();
			}
			else if (enemy.getHP() < 1 && party.size() > 0)
			{
				enemy.refresh();
				if (!enemy.isBoss() || enemy.getName().equals("Papa John"))
				{
					currentMap.displayMap(ps);
				}
				else if (currentMap.getName().equals("Heck") && currentMap.getResidents().size() != 0)
				{
					chooseFighter(null, currentMap.getResidents().get((int)(Math.random()*currentMap.getResidents().size())), Main.party, ps);
				}
				else
				{
					ps.setScene(enemy.bossCutscene(ps));
				}
			}
			});
			cd.play();
			});
			stat.play();
			});
			shake.play();
			});
			new Thread(s1).start();
			}
			else
			{
				chooseFighter(null, enemy, Main.party, ps);
			}
			});
			pa.play();
		}
		else {
		//loop creates all of the buttons
		for (int i = 1; i < 5; i++)
		{
			if (i < 4)
			{
				int num = i;
				Button atk = new Button(player.getMoveName(i));
				atk.setFont(sixt);
				atk.setPrefSize(250, 50);
				atk.setOnMouseEntered(fe -> descsText.setText(player.getMoveDesc(num)));
				atk.setOnMouseExited(fe -> descsText.setText("Select an action."));
				atk.setOnAction(fe ->                                                             //The battle function happens here
				{
					log.getChildren().remove(infos);
					windows.getChildren().remove(buttons);//buttons are removed
					descsText.setText("");
					Timeline pa = player.whichAtk(num, player, enemy, log1, ps); //executes player attack
					pa.setOnFinished(aaaae -> {
						ps.setScene(fightScreen);
					if ((flake == null || flake.getMoveName(1) != "Flake")) {//&& !stop) {
					Task<Void> s1 = sleep(2000, Main.sleep);
					s1.setOnSucceeded(ae -> {//waits 2 seconds here
						if (!enemy.isCharging())
						{
							windows.getChildren().remove(charging);
						}
					Timeline shake = shake(you, notYou, originalhp, enemyOriginalhp, player, enemy, enemydmg, youdmg, yourhpBar, enemyhpBar, hp, enemyhp);
					shake.setOnFinished(dasf -> {
					youdmg.setText("");
					enemydmg.setText("");
					Timeline stat = statEffect(player, enemy, ps, originalhp, enemyOriginalhp, log1, yourhpBar, enemyhpBar, hp, enemyhp);
					stat.setOnFinished(eea -> {
					Timeline cd = checkDeath(true, player, enemy, log1, m, ps);
					cd.setOnFinished(aaaaae -> {
					
					if (enemy.getHP() != 0 && party.size() > 0 && player.getHP() != 0)//if you're still fighting the enemy and didn't get a game over
					{
						int original = player.getHP();
						int enemyOriginal = enemy.getHP();
						Timeline ea = enemy.enemyAttack(player, enemy, log1, ps);
						ea.setOnFinished(aaae -> {
						Task<Void> s2 = sleep(2000, Main.sleep);
						s2.setOnSucceeded(aae -> {//waits 2 seconds here
							if (!enemy.isCharging())
							{
								windows.getChildren().remove(charging);
							}
						Timeline shake2 = shake(you, notYou, original, enemyOriginal, player, enemy, enemydmg, youdmg, yourhpBar, enemyhpBar, hp, enemyhp);
						shake2.setOnFinished(lkjf -> {
							youdmg.setText("");
							enemydmg.setText("");
						Timeline stat2 = statEffect(enemy, player, ps, enemyOriginal, original, log1, enemyhpBar, yourhpBar, enemyhp, hp);
						stat2.setOnFinished(fff -> {
						Timeline ed = checkDeath(false, player, enemy, log1, m, ps);
						ed.setOnFinished(aaaaaae -> {
							
						if (enemy.getHP() != 0 && party.size() > 0 && player.getHP() != 0)
						{
							if (flake != null)
							{
								fight(flake, enemy, party, true, ps);
								flake = null;
							}
							else
							{
								fight(player, enemy, party, false, ps);
							}
						}
						else if (enemy.getHP() < 1 && party.size() > 0)
						{
							enemy.refresh();
							if (!enemy.isBoss() || enemy.getName().equals("Papa John"))
							{
								currentMap.displayMap(ps);
							}
							else if (currentMap.getName().equals("Heck") && currentMap.getResidents().size() != 0)
							{
								chooseFighter(null, currentMap.getResidents().get((int)(Math.random()*currentMap.getResidents().size())), Main.party, ps);
							}
							else
							{
								ps.setScene(enemy.bossCutscene(ps));
							}
						}
						});
						ed.play();
						});
						stat2.play();
						});
						shake2.play();
						});
						new Thread(s2).start();
						});
						ea.play();
					}
					else if (enemy.getHP() < 1 && party.size() > 0)
					{
						enemy.refresh();
						if (!enemy.isBoss() || enemy.getName().equals("Papa John"))
						{
							currentMap.displayMap(ps);
						}
						else if (currentMap.getName().equals("Heck") && currentMap.getResidents().size() != 0)
						{
							chooseFighter(null, currentMap.getResidents().get((int)(Math.random()*currentMap.getResidents().size())), Main.party, ps);
						}
						else
						{
							ps.setScene(enemy.bossCutscene(ps));
						}
					}
					});
					cd.play();
					});
					stat.play();
					});
					shake.play();
					});
					new Thread(s1).start();
					}
					/*else if (stop)
					{
						stop = false;
					}*/
					else
					{
						chooseFighter(null, enemy, Main.party, ps);
					}
					});
					pa.play();
				});
				StackPane buttonHolder = new StackPane(atk);
				buttonHolder.setPrefSize(320, 75);
				buttons.getChildren().add(buttonHolder);
			}
			else if (party.size() > 1)
			{
				Button swap = new Button("Switch");
				swap.setFont(sixt);
				swap.setPrefSize(160, 40);
				swap.setOnMouseEntered(re -> descsText.setText("Send a different party member to fight."));
				swap.setOnMouseExited(re -> descsText.setText("Select an action."));
				swap.setOnAction(re -> chooseFighter(player, enemy, party, ps));
				StackPane buttonHolder = new StackPane(swap);
				buttonHolder.setPrefSize(320, 75);
				buttons.getChildren().add(buttonHolder);
			}
		}}
		
		
		//All of the components of the fight screen ARE COMPILED HERE!!!!!!!!!!!!!!!!!!!
		//windows.getChildren().addAll(stage, log, descs, buttonPadWindow, buttons, you, notYou, playerHP, enemyHP, pdmg, edmg);
		//Scene scene = new Scene(windows);
		//ps.setScene(scene);
	}
	
	public static void chooseFighter(Character fighter, Character enemy, ArrayList<Character> party, Stage ps)
	{
		Font thir = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 30);
		
		Pane chooseFighter = new Pane();
		Scene scene = new Scene(chooseFighter);
		
		VBox characters = new VBox();

		Label l = new Label("");
		l.setFont(thir);
		StackPane yourInfo = new StackPane(l);
		yourInfo.setPrefSize(490, 75);
		
		boolean enemyFirst = true;
		if (fighter == null)
		{
			enemyFirst = false;
		}
		boolean turn = enemyFirst;
		//lists all of the party members and an hp bar
		for (int i = 0; i < party.size(); i++)
		{
			Character player = party.get(i);
			ImageView yourPortrait = player.getPortrait();
			yourPortrait.setFitWidth(420); yourPortrait.setFitHeight(420);
			yourPortrait.setLayoutX(300); yourPortrait.setLayoutY(150);
			
			if (player != fighter) //prevents a character that was already battling from showing up
			{
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
				smallHPBar.setPrefSize(150, 720/party.size());
				
				//creates the big health bar for when you hover over a button
				ProgressBar bigBar = new ProgressBar();
				double bigProgress = (double) player.getHP()/player.getMaxHP();
				bigBar.setProgress(bigProgress);
				if (bigProgress <= 0.34)
				{
					bigBar.setStyle("-fx-accent: red;");
					if (bigProgress > 0 && progress <.03)
					{
						bigBar.setProgress(.03);
					}
				}
				else if (bigProgress <= 0.67)
				{
					bigBar.setStyle("-fx-accent: orange;");
				}
				else bigBar.setStyle("-fx-accent: lime green;");
				bigBar.setPrefSize(300, 40);
				
				Button b = new Button(player.getName());
				b.setPrefSize(110, 20);
				b.setOnAction(e -> {
				flake = null;
				fight(player, enemy, party, turn, ps);
				});
				
				Label bighp = new Label(player.getHP() + "/" + player.getMaxHP());
				bighp.setFont(thir);
				StackPane hoverHPBar = new StackPane(bigBar, bighp);
				hoverHPBar.setLayoutX(300); hoverHPBar.setLayoutY(570);
				hoverHPBar.setPrefSize(420, 150);
				
				
				
				
				//sets the portraits to show up when the mouse is hovered over their button
				b.setOnMouseEntered(e -> {
					chooseFighter.getChildren().addAll(yourPortrait, hoverHPBar);
					l.setText(enemyInfo(player));
				});
				b.setOnMouseExited(e -> {
					chooseFighter.getChildren().removeAll(yourPortrait, hoverHPBar);
					l.setText("");
				});
				StackPane button = new StackPane(b);
				button.setPrefSize(150, 720/party.size());
				
				ImageView selectionWindow = new ImageView(new Image("others/textLog1-2.jpg"));
				selectionWindow.setFitWidth(300); selectionWindow.setFitHeight(720/party.size());
				if (selectionWindow.getFitHeight() > 360)
				{
					selectionWindow.setFitHeight(360);
				}
				HBox eachCharacter = new HBox(button, smallHPBar);
				StackPane options = new StackPane(selectionWindow, eachCharacter);
				characters.getChildren().add(options);
			}
		}
		//puts a back button if switch was selected
		if (fighter != null)
		{
			Button b = new Button("<- Back");
			b.setPrefSize(100, 20);
			b.setOnAction(e -> fight(fighter, enemy, party, false, ps));
			ImageView selectionWindow = new ImageView(new Image("others/textLog1-2.jpg"));
			selectionWindow.setFitWidth(300); selectionWindow.setFitHeight(720/party.size());
			StackPane backButton = new StackPane(selectionWindow, b);
			backButton.setPrefSize(150, 720/party.size());
			characters.getChildren().add(backButton);
		}
		
		//displays the enemy
		ImageView enemyPortrait = enemy.getPortrait();
		enemyPortrait.setFitWidth(420); enemyPortrait.setFitHeight(420);
		enemyPortrait.setLayoutX(860); enemyPortrait.setLayoutY(150);
		ProgressBar enemyBar = new ProgressBar();
		double enemyProgress = (double) enemy.getHP()/enemy.getMaxHP();
		enemyBar.setProgress(enemyProgress);
		if (enemyProgress <= 0.34)
		{
			enemyBar.setStyle("-fx-accent: red;");
			if (enemyProgress > 0 && enemyProgress <.03)
			{
				enemyBar.setProgress(.03);
			}
		}
		else if (enemyProgress <= 0.67)
		{
			enemyBar.setStyle("-fx-accent: orange;");
		}
		else enemyBar.setStyle("-fx-accent: lime green;");
		enemyBar.setPrefSize(300, 40);
		
		Label enemyhp = new Label(enemy.getHP() + "/" + enemy.getMaxHP());
		enemyhp.setFont(thir);
		StackPane enemyHPBar = new StackPane(enemyBar, enemyhp);
		enemyHPBar.setLayoutX(860); enemyHPBar.setLayoutY(570);
		enemyHPBar.setPrefSize(420, 150);
		
		//creates the "who will fight" text log
		ImageView who = new ImageView(new Image("others/textLog1.jpg"));
		who.setFitWidth(980);
		who.setLayoutX(300);
		Label whoLabel = new Label("Who will fight?");
		whoLabel.setFont(thir);
		StackPane whoWillFight = new StackPane(whoLabel);
		whoWillFight.setLayoutX(300);
		whoWillFight.setPrefHeight(75); whoWillFight.setPrefWidth(980);
		Label enemyStats = new Label(enemyInfo(enemy));
		enemyStats.setFont(thir);
		StackPane enemyInfo = new StackPane(enemyStats);
		enemyInfo.setPrefSize(490, 75);
		HBox infos = new HBox(yourInfo, enemyInfo);
		infos.setLayoutX(300); infos.setLayoutY(75);
		
		//creates the background
		ImageView explosion = new ImageView(new Image("others/vsExplosion.jpg"));
		explosion.setFitWidth(980); explosion.setFitHeight(570);
		if (enemy.getName().equals("The System"))
		{
			explosion.setImage(new Image("others/vsFinal.png"));
		}
		ImageView vs = new ImageView(new Image("others/vs.png"));
		StackPane background = new StackPane(explosion, vs);
		background.setLayoutX(300); background.setLayoutY(150);
		
		chooseFighter.getChildren().addAll(background, who, whoWillFight, infos, characters, enemyHPBar, enemyPortrait);
		ps.setScene(scene);
	}
	
	public static Timeline shake (ImageView you, ImageView enemy, int originalhp, int enemyOriginalhp, Character player, Character opponent,
	Label elabel, Label plabel, ProgressBar yp, ProgressBar ep, Label yplabel, Label eplabel)
	{
		int pdiff = originalhp - player.getHP();
		int ediff = enemyOriginalhp - opponent.getHP();
		int origx = (int) you.getLayoutX();
		int enemyOrigx = (int) enemy.getLayoutX();
		double yprog = (double) player.getHP()/(double) player.getMaxHP();
		double eprog = (double) opponent.getHP()/(double) opponent.getMaxHP();
		if (ediff <= 0)
		{}
		else if (ediff < 10)
		{
			int rand = (int) (Math.random()*2);
			if (rand == 0)
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/hitMarker.mp3").toString())));
				hit1.setVolume(.16*Main.volume);
				hit1.play();
			}
			else
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/bruh.mp3").toString())));
				hit1.setVolume(.4*Main.volume);
				hit1.play();
			}
			
		}
		else if (ediff < 20)
		{
			int rand = (int) (Math.random()*2);
			if (rand == 0)
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/earthboundHit.mp3").toString())));
				hit1.setVolume(.16*Main.volume);
				hit1.play();
			}
			else
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/fartReverb.mp3").toString())));
				hit1.setVolume(.4*Main.volume);
				hit1.play();
			}
		}
		else if (ediff < 30)
		{
			int rand = (int) (Math.random()*2);
			if (ediff == 21)
			{
				sfx = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/21.mp3").toString())));
				sfx.setVolume(.16*Main.volume);
				sfx.play();
			}
			if (rand == 0)
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/bonk.mp3").toString())));
				hit1.setVolume(.16*Main.volume);
				hit1.play();
			}
			else
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/tf2Crit.mp3").toString())));
				hit1.setVolume(.16*Main.volume);
				hit1.play();
			}
		}
		else if (ediff < 40)
		{
			int rand = (int) (Math.random()*2);
			if (rand == 0)
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/creeperExplosion.mp3").toString())));
				hit1.setVolume(.16*Main.volume);
				hit1.play();
			}
			else
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/smashBat.mp3").toString())));
				hit1.setVolume(.08*Main.volume);
				hit1.play();
			}
		}
		else if (ediff < 50)
		{
			int rand = (int) (Math.random()*2);
			if (rand == 0)
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/slowOof.mp3").toString())));
				hit1.setVolume(.4*Main.volume);
				hit1.play();
			}
			else
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/intervention.mp3").toString())));
				hit1.setVolume(.16*Main.volume);
				hit1.play();
			}
		}
		else if (ediff >= 50)
		{
			int rand = (int) (Math.random()*2);
			if (rand == 0)
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/tomScream.mp3").toString())));
				hit1.setVolume(.16*Main.volume);
				hit1.play();
			}
			else
			{
				hit1 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/windows.mp3").toString())));
				hit1.setVolume(.16*Main.volume);
				hit1.play();
			}
		}
		if (pdiff <= 0)
		{}
		else if (pdiff < 10)
		{
			int rand = (int) (Math.random()*2);
			if (rand == 0)
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/hitMarker.mp3").toString())));
				hit2.setVolume(.16*Main.volume);
				hit2.play();
			}
			else
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/bruh.mp3").toString())));
				hit2.setVolume(.4*Main.volume);
				hit2.play();
			}
			
		}
		else if (pdiff < 20)
		{
			int rand = (int) (Math.random()*2);
			if (rand == 0)
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/earthboundHit.mp3").toString())));
				hit2.setVolume(.16*Main.volume);
				hit2.play();
			}
			else
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/fartReverb.mp3").toString())));
				hit2.setVolume(.4*Main.volume);
				hit2.play();
			}
		}
		else if (pdiff < 30)
		{
			int rand = (int) (Math.random()*2);
			if (pdiff == 21)
			{
				sfx2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/21.mp3").toString())));
				sfx2.setVolume(.16*Main.volume);
				sfx2.play();
			}
			if (rand == 0)
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/bonk.mp3").toString())));
				hit2.setVolume(.4*Main.volume);
				hit2.play();
			}
			else
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/tf2Crit.mp3").toString())));
				hit2.setVolume(.16*Main.volume);
				hit2.play();
			}
		}
		else if (pdiff < 40)
		{
			int rand = (int) (Math.random()*2);
			if (rand == 0)
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/creeperExplosion.mp3").toString())));
				hit2.setVolume(.16*Main.volume);
				hit2.play();
			}
			else
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/smashBat.mp3").toString())));
				hit2.setVolume(.08*Main.volume);
				hit2.play();
			}
		}
		else if (pdiff < 50)
		{
			int rand = (int) (Math.random()*2);
			if (rand == 0)
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/slowOof.mp3").toString())));
				hit2.setVolume(.4*Main.volume);
				hit2.play();
			}
			else
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/intervention.mp3").toString())));
				hit2.setVolume(.16*Main.volume);
				hit2.play();
			}
		}
		else if (pdiff >= 50)
		{
			int rand = (int) (Math.random()*2);
			if (rand == 0)
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/tomScream.mp3").toString())));
				hit2.setVolume(.16*Main.volume);
				hit2.play();
			}
			else
			{
				hit2 = new MediaPlayer(new Media(new String(Main.class.getResource("hitSounds/windows.mp3").toString())));
				hit2.setVolume(.16*Main.volume);
				hit2.play();
			}
		}
		
		you.setImage(player.getPortrait().getImage());
		enemy.setImage(opponent.getPortrait().getImage());
		
		yp.setProgress(yprog);
		if (yprog <= 0.34)
		{
			yp.setStyle("-fx-accent: red;");
			if (yprog > 0 && yprog <.04)
			{
				yp.setProgress(.04);
			}
		}
		else if (yprog <= 0.67)
		{
			yp.setStyle("-fx-accent: orange;");
		}
		else yp.setStyle("-fx-accent: lime green;");
		
		yplabel.setText(player.getHP() + "/" + player.getMaxHP());
		
		ep.setProgress(eprog);
		if (eprog <= 0.34)
		{
			ep.setStyle("-fx-accent: red;");
			if (eprog > 0 && eprog <.04)
			{
				ep.setProgress(.04);
			}
		}
		else if (eprog <= 0.67)
		{
			ep.setStyle("-fx-accent: orange;");
		}
		else ep.setStyle("-fx-accent: lime green;");
		
		eplabel.setText(opponent.getHP() + "/" + opponent.getMaxHP());
		
		Timeline s = new Timeline();
		if (ediff > 0 || pdiff > 0)
		{
			if (ediff != 0)
			{
				elabel.setText("" + (-ediff));
				if (ediff > 0)
				{
					elabel.setTextFill(Color.RED);
				}
				else elabel.setTextFill(Color.LIMEGREEN);
			}
			
			if (pdiff != 0)
			{
				plabel.setText("" + (-pdiff));
				if (pdiff > 0)
				{
					plabel.setTextFill(Color.RED);
				}
				else plabel.setTextFill(Color.LIMEGREEN);
			}
			
			for (int i = 0, e = ediff, p = pdiff; e >= -1 || p >= -1; i++, p -= 2, e -= 2)
			{
				if (e > 100)
				{
					e = 100;
				}
				if (p > 100)
				{
					p = 100;
				}
				int direction = (int) Math.pow(-1, i);
				int ed = e;
				int pd = p;
				KeyFrame move = new KeyFrame(Duration.millis(100*i), ae -> {
					if (ed > 0)
					{
						enemy.setLayoutX(enemyOrigx + (direction*ed));
					}
					else enemy.setLayoutX(enemyOrigx);
					
					if (pd > 0)
					{
						you.setLayoutX(origx + (direction*pd));
					}
					else you.setLayoutX(origx);
				});
				s.getKeyFrames().add(move);
			}
		}
		return s;
	}
	
	public static Timeline checkDeath(boolean yourTurn, Character player, Character enemy, Label log1, MediaPlayer m, Stage ps)
	{
		ArrayList<Media> jingles = new ArrayList<Media>();
		jingles.add(new Media(new String(Main.class.getResource("victory/andStop.mp3").toString())));
		jingles.add(new Media(new String(Main.class.getResource("victory/earthboundWin.mp3").toString())));
		jingles.add(new Media(new String(Main.class.getResource("victory/FF7Win.mp3").toString())));
		jingles.add(new Media(new String(Main.class.getResource("victory/lisaRecruit.mp3").toString())));
		jingles.add(new Media(new String(Main.class.getResource("victory/pmdJingle.mp3").toString())));
		
		int dur = 0;
		
		jingle = new MediaPlayer(jingles.get((int)(Math.random()*jingles.size())));
		jingle.setVolume(.16*volume);
		if (enemy.getName().equals("The System"))
		{
			jingle = new MediaPlayer(new Media(new String(Main.class.getResource("victory/ootFanfare.mp3").toString())));
			jingle.setVolume(.24*volume);
			dur += 6;
		}
		sfx = new MediaPlayer(new Media(new String(Main.class.getResource("others/earthboundDeath.mp3").toString())));
		sfx.setVolume(.16*volume);
		
		Timeline death = new Timeline();
		KeyFrame second = new KeyFrame(Duration.millis(1));
		death.getKeyFrames().add(second);
		if (yourTurn && enemy.getHP() == 0)
		{
			currentEnemy = null;
			currentFighter = null;
			
			for (int i = 0; i < party.size(); i++) //resets gordon r. gordonmet + removes rage from derrek
			{
				if (party.get(i).getMoveName(1).equals("Pan Slam"))
				{
					party.get(i).addMisc(0);
				}
				if (party.get(i).getMoveName(1).equals("Baseball Smash"))
				{
					for (int j = party.get(i).getStatus().size() - 1; j >= 0; j--)
					{
						if (party.get(i).getStatus().get(j).equals("rage"))
						{
							party.get(i).getStatus().remove(j);
						}
					}
				}
			}
			
			if (!currentMap.getName().equals("Heck") || currentMap.getResidents().size() == 1)
			{
				m.stop();
			}
			removeResident(enemy);
			player.unCharge();
			
			if (!enemy.isBoss())
			{
				party.add(enemy);
				KeyFrame win = new KeyFrame(Duration.ZERO, ae -> log1.setText(enemy.getName() + " was defeated and joined your party!"));
				jingle.play();
				death.getKeyFrames().add(win);
			}
			else
			{
				KeyFrame win = new KeyFrame(Duration.ZERO, ae -> log1.setText(enemy.getName() + " was defeated!"));
				if (!currentMap.getName().equals("Heck") || currentMap.getResidents().size() == 0)
				{
					jingle.play();
				}
				death.getKeyFrames().add(win);
			}
			
			if (player.getHP() == 0)
			{
				party.remove(player);
				dead.add(player);
				KeyFrame hollowWin = new KeyFrame(Duration.seconds(5 + dur), aae -> {
					log1.setText("But " + player.getName() + " died tho lol.");
					sfx.play();
				});
				
				if (party.size() < 1)
				{
					sfx2 = new MediaPlayer(new Media(new String(Main.class.getResource("others/sm64GameOver.mp3").toString())));
					sfx2.setVolume(.24*volume);
					KeyFrame lose = new KeyFrame(Duration.seconds(6 + dur), ae -> {
						m.stop();
						log1.setText("You are out of warriors");
						sfx2.play();
					});
					KeyFrame gameOver = new KeyFrame(Duration.seconds(9 + dur), aae -> gameOver(ps, sfx2));
					death.getKeyFrames().addAll(lose, gameOver);
				}
				else
				{
					KeyFrame next = new KeyFrame(Duration.seconds(6 + dur));
					death.getKeyFrames().add(next);
				}
				death.getKeyFrames().add(hollowWin);
			}
			else
			{
				KeyFrame next = new KeyFrame(Duration.seconds(5 + dur));
				death.getKeyFrames().add(next);
			}
		}
		
		else if (yourTurn && player.getHP() == 0)
		{
			party.remove(player);
			dead.add(player);
			KeyFrame fail = new KeyFrame(Duration.ZERO, ae -> {
				log1.setText("Bruh! " + player.getName() + " died from that!");
				sfx.play();
			});
			death.getKeyFrames().add(fail);
			
			if (enemy.getMoveName(2).equals("Smash"))
			{
				enemy.unCharge();
			}
			
			if (party.size() < 1)
			{
				sfx2 = new MediaPlayer(new Media(new String(Main.class.getResource("others/sm64GameOver.mp3").toString())));
				sfx2.setVolume(.24*volume);
				KeyFrame lose = new KeyFrame(Duration.seconds(2), ae -> {
					m.stop();
					log1.setText("You are out of warriors");
					sfx2.play();
				});
				KeyFrame gameOver = new KeyFrame(Duration.seconds(5), aae -> gameOver(ps, sfx2));
				death.getKeyFrames().addAll(lose, gameOver);
			}
			
			else
			{
				KeyFrame choose = new KeyFrame(Duration.seconds(2), ae -> chooseFighter(null, enemy, party, ps));
				death.getKeyFrames().add(choose);
			}
		}
		
		else if (!yourTurn && enemy.getHP() == 0)
		{
			currentEnemy = null;
			currentFighter = null;
			
			for (int i = 0; i < party.size(); i++) //resets gordon r. gordonmet + removes rage from derrek
			{
				if (party.get(i).getMoveName(1).equals("Pan Slam"))
				{
					party.get(i).addMisc(0);
				}
				if (party.get(i).getMoveName(1).equals("Baseball Smash"))
				{
					for (int j = party.get(i).getStatus().size() - 1; j >= 0; j--)
					{
						if (party.get(i).getStatus().get(j).equals("rage"))
						{
							party.get(i).getStatus().remove(j);
						}
					}
				}
			}
			
			if (!currentMap.getName().equals("Heck") || currentMap.getResidents().size() == 1)
			{
				m.stop();
			}
			removeResident(enemy);
			player.unCharge();
			
			if (!enemy.isBoss())
			{
				party.add(enemy);
				KeyFrame wow = new KeyFrame(Duration.ZERO, ae -> log1.setText(enemy.getName() + " died during their own attack and joined the party!"));
				jingle.play();
				death.getKeyFrames().add(wow);
			}
			else
			{
				KeyFrame wow = new KeyFrame(Duration.ZERO, ae -> log1.setText(enemy.getName() + " died during their own attack!"));
				if (!currentMap.getName().equals("Heck") || currentMap.getResidents().size() == 0)
				{
					jingle.play();
				}
				death.getKeyFrames().add(wow);
			}
			
			if (player.getHP() == 0)
			{
				party.remove(player);
				dead.add(player);
				KeyFrame awman = new KeyFrame(Duration.seconds(5 + dur), aae -> {
					log1.setText(player.getName() + " also died from the attack! Bruh!");
					sfx.play();
				});
				
				if (party.size() < 1)
				{
					sfx2 = new MediaPlayer(new Media(new String(Main.class.getResource("others/sm64GameOver.mp3").toString())));
					sfx2.setVolume(.24*volume);
					KeyFrame lose = new KeyFrame(Duration.seconds(7 + dur), ae -> {
						m.stop();
						log1.setText("You are out of warriors");
						sfx2.play();
					});
					KeyFrame gameOver = new KeyFrame(Duration.seconds(10 + dur), aae -> gameOver(ps, sfx2));
					death.getKeyFrames().addAll(lose, gameOver);
				}
				else
				{
					KeyFrame next = new KeyFrame(Duration.seconds(7 + dur));
					death.getKeyFrames().add(next);
				}
				death.getKeyFrames().add(awman);
			}
			else
			{
				KeyFrame next = new KeyFrame(Duration.seconds(5 + dur));
				death.getKeyFrames().add(next);
			}
		}
		
		else if (!yourTurn && player.getHP() == 0)
		{
			party.remove(player);
			dead.add(player);
			KeyFrame ohno = new KeyFrame(Duration.ZERO, ae -> {
				log1.setText("Oh no! " + player.getName() + " has passed away...");
				sfx.play();
			});
			death.getKeyFrames().add(ohno);
			
			if (enemy.getMoveName(2).equals("Smash"))
			{
				enemy.unCharge();
			}
			
			if (party.size() < 1)
			{
				sfx2 = new MediaPlayer(new Media(new String(Main.class.getResource("others/sm64GameOver.mp3").toString())));
				sfx2.setVolume(.24*volume);
				KeyFrame lose = new KeyFrame(Duration.seconds(2), ae -> {
					m.stop();
					log1.setText("You are out of warriors");
					sfx2.play();
				});
				KeyFrame gameOver = new KeyFrame(Duration.seconds(5), aae -> gameOver(ps, sfx2));
				death.getKeyFrames().addAll(lose, gameOver);
			}
			else
			{
				KeyFrame choose = new KeyFrame(Duration.seconds(2), ae -> chooseFighter(null, enemy, party, ps));
				death.getKeyFrames().add(choose);
			}
		}
		return death;
	}
	
	public static void gameOver(Stage ps, MediaPlayer g)
	{
		ImageView background = new ImageView(new Image("others/gameOver2.png"));
		
		Button t = new Button("Try Again");
		t.setPrefSize(200, 40);
		t.setOnAction(e -> 
		{
			g.stop();
			load(ps);
			titleScreen(ps);
		});
		
		StackPane tBox = new StackPane(t);
		tBox.setPrefSize(300, 80);
		
		Button q = new Button("Quit");
		q.setPrefSize(150, 30);
		q.setOnAction(e -> System.exit(0));
		
		StackPane qBox = new StackPane(q);
		qBox.setPrefSize(300, 70);
		
		VBox buttons = new VBox(tBox, qBox);
		
		ImageView box = new ImageView(new Image("others/textLog1-2.jpg"));
		box.setFitWidth(300); box.setFitHeight(150);
		
		StackPane all = new StackPane(box, buttons);
		all.setLayoutX(980); all.setLayoutY(570);
		
		ps.setScene(new Scene(new Pane(background, all)));
	}
	
	public static void removeResident(Character r)
	{
		for (int i = 0; i < maps.size(); i++)
		{
			for (int j = 0; j < maps.get(i).getResidents().size(); j++)
			{
				maps.get(i).getResidents().remove(r);
			}
		}
	}
	
	public boolean inParty(Character c)
	{
		for (int i = 0; i < party.size(); i++)
		{
			if (party.get(i) == c)
			{
				return true;
			}
		}
		return false;
	}
	
	public static String enemyInfo(Character enemy)
	{
		String info = enemy.getName() + "(" + enemy.getHP() + "/" + enemy.getMaxHP() + ")\n";
		int rage = 0;
		int weak = 0;
		int bld = 0;
		int brn = 0;
		int heal = 0;
		int greed = 0;
		for (int i = 0; i < enemy.getStatus().size(); i++)
		{
			if (enemy.getStatus().get(i).equals("rage"))
			{
				rage++;
			}
			else if (enemy.getStatus().get(i).equals("weak"))
			{
				weak++;
			}
			else if (enemy.getStatus().get(i).equals("BLD"))
			{
				bld++;
			}
			else if (enemy.getStatus().get(i).equals("burn"))
			{
				brn++;
			}
			else if (enemy.getStatus().get(i).equals("heal"))
			{
				heal++;
			}
			else if (enemy.getStatus().get(i).equals("greed"))
			{
				greed++;
			}
			else info += "(" + enemy.getStatus().get(i) + ")";
		}
		if (rage > 0)
		{
			info += "(RAGE x" + rage + ")";
		}
		if (weak > 0)
		{
			info += "(WEAK x" + weak + ")";
		}
		if (bld > 0)
		{
			info += "(BLD x" + bld + ")";
		}
		if (brn > 0)
		{
			info += "(BRN x" + brn + ")";
		}
		if (heal > 0)
		{
			info += "(HEAL x" + heal + ")";
		}
		if (greed > 0)
		{
			info += "(GRD x" + greed + ")";
		}
		return info;
	}
	
	public static Timeline statEffect(Character user, Character enemy, Stage ps, int userhp, int enemyhp, Label log, 
			ProgressBar yp, ProgressBar ep, Label yplabel, Label eplabel)
	{
		Timeline t = new Timeline();
		boolean fire = false;
		boolean heal = false;
		int dur = 0;
		int bld = 0;
		int grd = 0;
		for (int i = user.getStatus().size()-1; i>=0; i--) //healing should always go first
		{
			if (user.getStatus().get(i).equals("heal") && !heal)
			{
				KeyFrame p = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " heals 10 hp."));
				t.getKeyFrames().add(p);
				dur += 1;
				heal = true;
				user.getStatus().remove("heal");
				user.changeHP(10);
			}
		}
		for (int i = user.getStatus().size()-1; i>=0; i--)
		{
			if (user.getStatus().get(i).equals("PSN"))
			{
				KeyFrame p = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " takes 5 poison damage!"));
				t.getKeyFrames().add(p);
				dur += 1;
				user.changeHP(-5);
			}
			else if (user.getStatus().get(i).equals("burn") && !fire)
			{
				KeyFrame p = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " takes 7 burn damage!"));
				t.getKeyFrames().add(p);
				dur += 1;
				fire = true;
				user.getStatus().remove("burn");
				user.changeHP(-7);
			}
			else if (user.getStatus().get(i).equals("BLD"))
			{
				bld += 1;
			}
			else if (user.getStatus().get(i).equals("greed"))
			{
				grd += 1;
			}
			else if (enemyhp > enemy.getHP())
			{
				if (user.getStatus().get(i).equals("rage") && 
						!user.getMoveDesc(1).equals("Does 20 damage and adds permanent rage") && //Wrath doesn't lose rage
						!user.getMoveName(1).equals("Baseball Smash") && //Derrek doesn't lose rage
						!user.getMoveName(1).equals("Virus/Cache")) //The System doesn't lose rage
				{
					user.getStatus().remove(i);
				}
				else if (user.getStatus().get(i).equals("weak"))
				{
					user.getStatus().remove(i);
				}
			}
		}
		if (bld > 0)
		{
			int dmg = bld + 2;
			KeyFrame p = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " takes " + dmg + " bleed damage!"));
			t.getKeyFrames().add(p);
			dur += 1;
			user.addStat("BLD");
			user.changeHP(-dmg);
		}
		if ((!user.isBoss() && enemy.getName().equals("Gabe Newell") && grd == 2 && user.getHP() > 0 && enemy.getHP() > 0) || 
				(!user.isBoss() && enemy.getName().equals("Morshu") && grd == 0 && user.getHP() > 0) && enemy.getHP() > 0)
		{
			KeyFrame p = new KeyFrame(Duration.seconds(dur), ae -> log.setText("Careful! " + user.getName() + "'s greed is maxed out!"));
			t.getKeyFrames().add(p);
			dur += 2;
		}
		t.getKeyFrames().add(new KeyFrame(Duration.seconds(dur)));
		
		double yprog = (double) user.getHP()/(double) user.getMaxHP();
		double eprog = (double) enemy.getHP()/(double) enemy.getMaxHP();
		
		yp.setProgress(yprog);
		if (yprog <= 0.34)
		{
			yp.setStyle("-fx-accent: red;");
			if (yprog > 0 && yprog <.04)
			{
				yp.setProgress(.04);
			}
		}
		else if (yprog <= 0.67)
		{
			yp.setStyle("-fx-accent: orange;");
		}
		else yp.setStyle("-fx-accent: lime green;");
		
		yplabel.setText(user.getHP() + "/" + user.getMaxHP());
		
		ep.setProgress(eprog);
		if (eprog <= 0.34)
		{
			ep.setStyle("-fx-accent: red;");
			if (eprog > 0 && eprog <.04)
			{
				ep.setProgress(.04);
			}
		}
		else if (eprog <= 0.67)
		{
			ep.setStyle("-fx-accent: orange;");
		}
		else ep.setStyle("-fx-accent: lime green;");
		
		eplabel.setText(enemy.getHP() + "/" + enemy.getMaxHP());
		
		return t;
	}
	
	private static void displayParty(Stage ps)
	{
		Font thir = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 30);
		Font fift = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 15);
		
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
			areaChoiceScreen(ps);
			menu.stop();
		});
		ImageView buttonBorder = new ImageView(new Image("others/textLog1-2.jpg"));
		buttonBorder.setFitWidth(300); buttonBorder.setFitHeight(720/(Main.party.size()+1));
		StackPane backHolder = new StackPane(buttonBorder, back);
		characters.getChildren().add(backHolder);
		
		
		displayParty.getChildren().addAll(characters, pictureHere, yourInfo, moves);
		ps.setScene(scene);
	}
	
	public static Task<Void> sleep (int t, boolean s)
	{
		Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            	if (s)
            	{
                try {
                    Thread.sleep(t);
                } catch (InterruptedException e) {
                }
            	}
                return null;
            }
        };
        Main.sleep = true;
        return sleeper;
	}
}