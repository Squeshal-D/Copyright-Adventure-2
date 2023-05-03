import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

public class Walmart extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/walmartPortrait.png"));
	ImageView stage = new ImageView(new Image ("stages/walmartStage.png"));
	String name = "Walmart";
	Media theme = new Media(new String(Main.class.getResource("themes/moltenGigaplex.mp3").toString()));
	double volume = 1;
	int hp = 200;
	int maxhp = 200;
	int misc = 1;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = true;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Parking Lot";
	String move2 = "Purchase";
	String move3 = "Restock";
	String move1desc = "Player gets shot in the parking lot 1-5 times";
	String move2desc = "Player purchases random stat, spending hp";
	String move3desc = "Walmart heals 5 hp per charge (+1 per turn)";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(490);
		b.setLayoutY(480);
	}
	
	public boolean hasStat(String s)
	{
		for (int i = 0; i<status.size(); i++)
		{
			if (status.get(i).equals(s))
			{
				return true;
			}
		}
		return false;
	}
	
	public void changeHP(int i)
	{
		hp += i;
		if (hp < 0)
		{
			hp = 0;
		}
		else if (hp > maxhp)
		{
			hp = maxhp;
		}
	}
	
	public void refresh()
	{
		hp = maxhp;
		misc = 0;
		teammate = true;
		charging = false;
		status.clear();
	}
	
	public Timeline whichAtk(int i, Character user, Character enemy, Label log, Stage ps)
	{
		if (i == 1) {
			return atk1(user, enemy, log, ps);
		}
		if (i == 2) {
			return atk2(user, enemy, log, ps);
		}
		if (i ==3) {
			return atk3(user, enemy, log, ps);
		}
		return atk1(user, enemy, log, ps);
	}
	
	public Timeline atk1(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		misc++;
		int shots = 0;
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText("Someone pulled a Glock on " + enemy.getName() + " in the parking lot!"));
		atk.getKeyFrames().add(first);
		for (int i = 0; i < 5; i++)
		{
			int g = i;
			int r = (int) (Math.random()*2);
			if (r == 0)
			{
				shots++;
				int f = shots;
				KeyFrame hit = new KeyFrame(Duration.millis(1500 + 250*i), ae -> log.setText("They hit " + f + "/" + (g+1) + " shots!"));
				atk.getKeyFrames().add(hit);
			}
			else
			{
				int f = shots;
				KeyFrame miss = new KeyFrame(Duration.millis(1500 + 250*i), ae -> log.setText("They hit " + f + "/" + (g+1) + " shots!"));
				atk.getKeyFrames().add(miss);
			}
		}
		int dmg = user.modifydmg(6*shots);
		enemy.changeHP(-dmg);
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		int r = (int)(Math.random()*2);
		Timeline atk = new Timeline();
		misc++;
		
		String rep = "";
		String stat = "";
		int cost = 0;
		int num = 0;
		String s = "status";
		
		if (r == 0)
		{
			rep = "poison";
			stat = "PSN";
			cost = 10;
			num = 1;
		}
		else if (r == 1)
		{
			rep = "bleed";
			stat = "BLD";
			cost = 10;
			num = (int)(Math.random()*2 + 1);
			if (num == 2)
			{
				s = "statuses";
			}
		}
		else if (r == 2)
		{
			rep = "heal";
			stat = "heal";
			cost = 25;
			num = (int)(Math.random()*2 + 1);
			if (num == 2)
			{
				s = "statuses";
			}
		}
		else if (r == 3)
		{
			rep = "weak";
			stat = "weak";
			cost = 10;
			num = (int)(Math.random()*2 + 1);
			if (num == 2)
			{
				s = "statuses";
			}
		}
		else if (r == 4)
		{
			rep = "rage";
			stat = "rage";
			cost = 20;
			num = 1;
		}
		else
		{
			rep = "burn";
			stat = "burn";
			cost = 10;
			num = (int)(Math.random()*2 + 1);
			if (num == 2)
			{
				s = "statuses";
			}
		}
		
		final String repf = rep;
		//final String statf = stat;
		final int costf = modifydmg(cost);
		final int numf = num;
		final String sf = s;
		
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(enemy.getName() + " bought " + numf + " " + repf + " " + sf + " for " + costf + " hp!\n"
				+ "They couldn't resist!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		
		for (int i = 0; i < num; i++)
		{
			enemy.addStat(stat);
		}
		enemy.changeHP(-costf);
		
		atk.getKeyFrames().addAll(first, second);
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int heal = misc*5;
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " restocked the shelves. " + heal + " hp was restored."));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		misc = 1;
		user.changeHP(heal);
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*2 + 1);
		if (player.getHP() <= 10)
		{
			i = 2;
		}
		else if (misc >= 4 && hp <= 180)
		{
			i = (int) (Math.random()*3 + 1);
		}
		else if (misc >= 8 && hp <= 160)
		{
			i = 3;
		}
		
		return whichAtk(i, enemy, player, log, ps);
	}
	
	public String getName()
	{
		return name;
	}
	
	public Button getButton()
	{
		return button;
	}
	
	public ImageView getPortrait()
	{
		return portrait;
	}
	
	public ImageView getStage()
	{
		return stage;
	}
	
	public int getHP()
	{
		return hp;
	}
	
	public int getMaxHP()
	{
		return maxhp;
	}
	
	public double getVolume()
	{
		return volume;
	}
	
	public String getMoveName(int i)
	{
		if (i == 1){
			return move1;}
		if (i == 2){
			return move2;}
		if (i == 3){
			return move3;}
		return null;
	}
	
	public String getMoveDesc(int i)
	{
		if (i == 1){
			return move1desc;}
		if (i == 2){
			return move2desc;}
		if (i == 3){
			return move3desc;}
		return null;
	}
	
	public Media getTheme()
	{
		return theme;
	}
	
	public ArrayList<String> getStatus()
	{
		return status;
	}
	
	public boolean isBoss()
	{
		return boss;
	}
	
	public boolean isCharging()
	{
		return charging;
	}
	
	public void unCharge()
	{
		charging = false;
	}
	
	public boolean isTesting()
	{
		return testing;
	}
	
	public boolean heardSong()
	{
		return song;
	}
	
	public void hearSong()
	{
		song = true;
	}
	
	public int modifydmg(int dmg)
	{
		int buff = 0;
		int nerf = 0;
		double mod = (double) dmg;
		for (int i = status.size()-1; i >= 0; i--)
		{
			if (status.get(i).equals("rage"))
			{
				buff += 1;
			}
			if (status.get(i).equals("weak"))
			{
				nerf += 1;
			}
		}
		if (buff > nerf)
		{
			for (int i = buff - nerf; i > 0; i--)
			{
				mod *= 1.5;
			}
		}
		else if (nerf > buff)
		{
			for (int i = nerf - buff; i > 0; i--)
			{
				mod *= .5;
			}
		}
		if ((int) mod == 0 && dmg > 0)
		{
			mod = 1;
		}
		return (int) mod;
	}
	
	public void addStat(String stat)
	{
		int brn = 0;
		for (int i = 0; i < status.size(); i++)
		{
			if (status.get(i).equals("burn"))
			{
				brn++;
			}
		}
		if (stat.equals("rage") || stat.equals("weak") || stat.equals("BLD") || (stat.equals("burn") && brn < 3) || stat.equals("heal") || 
				stat.equals("greed") || !hasStat(stat))
		{
			status.add(stat);
		}
	}
	
	public Scene bossCutscene(Stage ps)
	{
		Font sixt = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 16);
		Font twen = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 20);
		Font sxfr = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 64);
		
		ImageView special = new ImageView(new Image("others/walmartCutscene.jpg"));
		
		Label specialText = new Label("Well, you did it. Walmart is no more. With no Walmart, there are only two other places that you could go to. Would you like "
				+ "to visit the Black Market (Trade a party member to get back a dead party member), or would you like to go through the grocery store? (+15 hp "
				+ "to each party member)");
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
		Button action = new Button("Black Market!");
		action.setPrefSize(200, 70);
		action.setOnAction(e -> {
			
			Pane all = new Pane();
			
			ArrayList <Character> sacrifice = new ArrayList <Character>();
			ArrayList <Character> product = new ArrayList <Character>();
			
			ImageView infoBox = new ImageView(new Image("others/textLog1.jpg"));
			infoBox.setFitWidth(680); infoBox.setFitHeight(150);
			Label yourInfo = new Label("");
			yourInfo.setFont(twen);
			StackPane infoHolder = new StackPane(yourInfo);
			infoHolder.setPrefSize(340, 150);
			Label theirInfo = new Label("");
			theirInfo.setFont(twen);
			StackPane infoHolder2 = new StackPane(theirInfo);
			infoHolder2.setPrefSize(340, 150);
			HBox infos = new HBox(infoHolder, infoHolder2);
			StackPane topBox = new StackPane(infoBox, infos);
			topBox.setLayoutX(300);
			
			ImageView youBox = new ImageView(new Image("others/textLog1-2.jpg"));
			youBox.setFitWidth(210); youBox.setFitHeight(210);
			ImageView theyBox = new ImageView(new Image("others/textLog1-2.jpg"));
			theyBox.setFitWidth(210); theyBox.setFitHeight(210);
			ImageView intermissionBox = new ImageView(new Image("others/textLog1-2.jpg"));
			intermissionBox.setFitWidth(260); intermissionBox.setFitHeight(210);
			Label arrow = new Label("->");
			arrow.setFont(sxfr);
			StackPane intermission = new StackPane(intermissionBox, arrow);
			HBox pictureBoxes = new HBox(youBox, intermission, theyBox);
			pictureBoxes.setLayoutX(300);
			pictureBoxes.setLayoutY(150);
			
			VBox party = new VBox();
			
			for (int i = 0; i < Main.party.size(); i++)
			{
				Character player = Main.party.get(i);
				
				Image playerPortrait = player.getPortrait().getImage();
				
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
				smallHPBar.setPrefSize(150, 720/Main.party.size());
				
				Button b = new Button(player.getName());
				b.setPrefSize(110, 20);
				b.setOnAction(ea -> {
					sacrifice.clear();
					sacrifice.add(player);
					youBox.setImage(playerPortrait);
					yourInfo.setText(Main.enemyInfo(player));
				});
					
				b.setOnMouseEntered(ea -> yourInfo.setText(Main.enemyInfo(player)));
				b.setOnMouseExited(ea -> {
					if (sacrifice.size() == 0)
					{
						yourInfo.setText("");
					}
					else
					{
						yourInfo.setText(Main.enemyInfo(sacrifice.get(0)));
					}
				});
				StackPane button = new StackPane(b);
				button.setPrefSize(150, 720/Main.party.size());
					
				ImageView selectionWindow = new ImageView(new Image("others/textLog1-2.jpg"));
				selectionWindow.setFitWidth(300); selectionWindow.setFitHeight(720/Main.party.size());
				if (selectionWindow.getFitHeight() > 360)
				{
					selectionWindow.setFitHeight(360);
				}
				HBox eachCharacter = new HBox(button, smallHPBar);
				StackPane options = new StackPane(selectionWindow, eachCharacter);
				party.getChildren().add(options);
			}
			
			VBox dead = new VBox();
			dead.setLayoutX(980);
			
			for (int i = 0; i < Main.dead.size(); i++)
			{
				Character player = Main.dead.get(i);
				player.refresh();
				
				Image playerPortrait = player.getPortrait().getImage();
				
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
				smallHPBar.setPrefSize(150, 720/Main.dead.size());
				
				Button b = new Button(player.getName());
				b.setPrefSize(110, 20);
				b.setOnAction(ea -> {
					product.clear();
					product.add(player);
					theyBox.setImage(playerPortrait);
					theirInfo.setText(Main.enemyInfo(player));
				});
					
				b.setOnMouseEntered(ea -> theirInfo.setText(Main.enemyInfo(player)));
				b.setOnMouseExited(ea -> {
					if (product.size() == 0)
					{
						theirInfo.setText("");
					}
					else
					{
						theirInfo.setText(Main.enemyInfo(product.get(0)));
					}
				});
				StackPane button = new StackPane(b);
				button.setPrefSize(150, 720/Main.dead.size());
					
				ImageView selectionWindow = new ImageView(new Image("others/textLog1-2.jpg"));
				selectionWindow.setFitWidth(300); selectionWindow.setFitHeight(720/Main.dead.size());
				if (selectionWindow.getFitHeight() > 360)
				{
					selectionWindow.setFitHeight(360);
				}
				HBox eachCharacter = new HBox(button, smallHPBar);
				StackPane options = new StackPane(selectionWindow, eachCharacter);
				dead.getChildren().add(options);
			}
			
			ImageView textBox = new ImageView(new Image("others/textLog1.jpg"));
			textBox.setFitWidth(680); textBox.setFitHeight(180);
			Label shopkeep = new Label("This here be the black market. Don't tell anyone about it!\nSelect a party member to recruit on the right, and select "
					+ "a party member as payment on the left. Once the two are chosen, hit confirm.");
			if (Main.dead.size() < 1)
			{
				shopkeep.setText("Sorry, we are out of stock for now. Just hit that back button.");
			}
			shopkeep.setFont(sixt);
			shopkeep.setMaxWidth(640);
			shopkeep.setWrapText(true);
			StackPane text = new StackPane(textBox, shopkeep);
			text.setLayoutX(300); text.setLayoutY(360);
			
			ImageView bBox = new ImageView(new Image("others/textLog1.jpg"));
			bBox.setFitWidth(680); bBox.setFitHeight(180);
			VBox buttons = new VBox();
			StackPane bottomBox = new StackPane(bBox, buttons);
			bottomBox.setLayoutX(300); bottomBox.setLayoutY(540);
			
			Button back = new Button("Back");
			back.setPrefSize(300, 80);
			back.setOnAction(ae -> {
				ps.setScene(bossCutscene(ps));
			});
			StackPane bButton = new StackPane(back);
			bButton.setPrefSize(680, 90);
			
			Button confirm = new Button("Confirm");
			confirm.setPrefSize(300, 80);
			confirm.setOnAction(ea -> {
				if (sacrifice.size() > 0 && product.size() > 0)
				{
					bottomBox.getChildren().remove(buttons);
					all.getChildren().removeAll(party, dead);
					
					Timeline t = new Timeline();
					KeyFrame one = new KeyFrame(Duration.ZERO, ae -> {
						shopkeep.setText("I'm sure that " + sacrifice.get(0).getName() + " will have a nice life with whoever you just sold them to.");
					});
					KeyFrame two = new KeyFrame(Duration.seconds(3), ae -> {
						Main.party.remove(sacrifice.get(0));
						sacrifice.get(0).refresh();
						Main.party.add(product.get(0));
						Main.dead.remove(product.get(0));
						Main.currentMap.displayMap(ps);
					});
					
					t.getKeyFrames().addAll(one, two);
					t.play();
				}
				else
				{
					if (Main.dead.size() < 1)
					{
						shopkeep.setText("Hey, that button won't do anything for you right now! Just hit the back button!");
					}
					else
					{
						shopkeep.setText("Bruh! You gotta choose a member to buy on the right and a member to trade in on the left first!");
					}
				}
			});
			StackPane cButton = new StackPane(confirm);
			cButton.setPrefSize(680, 90);
			
			buttons.getChildren().addAll(cButton, bButton);
			
			all.getChildren().addAll(topBox, pictureBoxes, party, dead, text, bottomBox);
			Scene s = new Scene(all);
			ps.setScene(s);
		});
		
		Button action2 = new Button("Grocery Store!");
		action2.setPrefSize(200, 70);
		action2.setOnAction(e -> {
			for (int j = 0; j < Main.party.size(); j++)
			{
				Main.party.get(j).changeHP(15);
			}
			Main.currentMap.displayMap(ps);
		});
		
		StackPane aButton = new StackPane(buttonBox, action);
		StackPane bButton = new StackPane(buttonBox2, action2);
		
		VBox buttons = new VBox(aButton, bButton);
		buttons.setLayoutX(980); buttons.setLayoutY(520);
		
		Pane eventScreen = new Pane();
		eventScreen.getChildren().addAll(special, specialTextBox, buttons);
			
		Scene s = new Scene(eventScreen);
		return s;
	}
}