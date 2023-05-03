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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Barry extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/barryPortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/barryStage.png"));
	String name = "Barry B. Benson";
	Media theme = new Media(new String(Main.class.getResource("themes/plokBoss.mp3").toString()));
	double volume = 1;
	int hp = 200;
	int maxhp = 200;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = true;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Stinger";
	String move2 = "Lawsuit";
	String move3 = "Pollenate";
	String move1desc = "20-25 dmg. 10 self dmg.";
	String move2desc = "Steal opponent's statuses.";
	String move3desc = "Has a chance to weaken or poison each party member.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(240);
		b.setLayoutY(470);
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
		int rand = (int)(Math.random()*6);
		int dmg = modifydmg(20 + rand);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " impales " + enemy.getName() + " with their stinger " 
		+ "for " + dmg + " damage!"));
		atk.getKeyFrames().add(first);
		atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
		enemy.changeHP(-dmg);
		user.changeHP(-10);
		misc--;
		
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " sues " + enemy.getName() + " for their statuses!"));
		atk.getKeyFrames().add(first);
		atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
		while (enemy.getStatus().size() > 0)
		{
			user.addStat(enemy.getStatus().get(0));
			enemy.getStatus().remove(0);
		}
		misc--;
		
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		if (!charging)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " readies the pollen gun!"));
			atk.getKeyFrames().add(first);
			atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
			charging = true;
		}
		else
		{
			charging = false;
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " spreads toxic pollen!"));
			atk.getKeyFrames().add(first);
			
			int frames = 0;
			for (int i = 0; i < Main.party.size(); i++)
			{
				int rnd = (int)(Math.random()*4);
				if (rnd == 3 && !Main.party.get(i).hasStat("PSN"))
				{
					final int j = i;
					KeyFrame psn = new KeyFrame(Duration.millis(1000 + frames*800), ae -> log.setText(Main.party.get(j).getName() + " was poisoned!"));
					Main.party.get(j).addStat("PSN");
					atk.getKeyFrames().add(psn);
					frames++;
				}
				else if (rnd > 0)
				{
					final int j = i;
					KeyFrame wk = new KeyFrame(Duration.millis(1000 + frames*800), ae -> log.setText(Main.party.get(j).getName() + " was weakened!"));
					Main.party.get(j).addStat("weak");
					atk.getKeyFrames().add(wk);
					frames++;
				}
			}
			
			if (frames == 0)
			{
				KeyFrame k = new KeyFrame(Duration.millis(1000), ae -> log.setText("Nobody was affected!"));
				atk.getKeyFrames().add(k);
			}
			misc = 2;
		}
		
        return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (charging)
		{
			i = 3;
		}
		else if (misc < 1)
		{
			if (player.hasStat("rage") || player.hasStat("heal"))
			{
				i = (int)(Math.random()*2 + 1);
			}
			else
			{
				int rnd = (int)(Math.random()*2);
				if (rnd == 0)
					i = 3;
				else i = rnd;
			}
		}
		else
		{
			if (player.hasStat("rage") || player.hasStat("heal"))
			{
				i = (int)(Math.random()*2 + 1);
			}
			else i = 1;
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
		Font thir = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 30);
		
		ImageView special = new ImageView(new Image("others/barryCutscene.png"));
		
		Label specialText = new Label("Barry B. Benson was beeaten like the bee he has beeen. But beehold, this has riled up the beeligerent bees in the beehive. "
				+ "They threaten to beesiege your party for such beehavior if you don't beeat it. Beefore you leave, you could collect one beesought item that "
				+ "once beelonged to Barry B. Benson. Honey will remove negative statuses from the whole party. His sweater will increase the max hp of a party "
				+ "member of your choosing by 30.");
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
		Button action = new Button("Honey");
		action.setPrefSize(200, 70);
		action.setOnAction(e -> {
			for (int i = 0; i < Main.party.size(); i++)
			{
				for (int j = Main.party.get(i).getStatus().size() - 1; j >= 0; j--)
				{
					if (Main.party.get(i).getStatus().get(j).equals("PSN") || Main.party.get(i).getStatus().get(j).equals("burn") ||
							Main.party.get(i).getStatus().get(j).equals("BLD") || Main.party.get(i).getStatus().get(j).equals("weak"))
					{
						Main.party.get(i).getStatus().remove(j);
					}
				}
			}
			Main.currentMap.displayMap(ps);
		});
		
		Button action2 = new Button("Sweater");
		action2.setPrefSize(200, 70);
		action2.setOnAction(e -> {
			
			Pane sweater = new Pane();
			Scene scene = new Scene(sweater);
			
			VBox characters = new VBox();

			Label l = new Label("");
			l.setFont(thir);
			StackPane yourInfo = new StackPane(l);
			yourInfo.setPrefSize(980, 75);
			
			//lists all of the party members and an hp bar
			for (int i = 0; i < Main.party.size(); i++)
			{
				Character player = Main.party.get(i);
				ImageView yourPortrait = player.getPortrait();
				yourPortrait.setFitWidth(420); yourPortrait.setFitHeight(420);
				yourPortrait.setLayoutX(580); yourPortrait.setLayoutY(150);
				
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
				smallHPBar.setPrefSize(150, 720/(Main.party.size() + 1));
					
				//creates the big health bar for when you hover over a button
				ProgressBar bigBar = new ProgressBar();
				double bigProgress = (double) player.getHP()/player.getMaxHP();
				bigBar.setProgress(bigProgress);
				if (bigProgress <= 0.34)
				{
					bigBar.setStyle("-fx-accent: red;");
					if (bigProgress > 0 && bigProgress <.03)
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
				
				Label bighp = new Label(player.getHP() + "/" + player.getMaxHP());
				bighp.setFont(thir);
				StackPane hoverHPBar = new StackPane(bigBar, bighp);
				//hoverHPBar.setLayoutX(300); hoverHPBar.setLayoutY(570);
				hoverHPBar.setPrefSize(490, 150);
					
				ProgressBar bigBar2 = new ProgressBar();
				double bigProgress2 = (double) (player.getHP() + 30)/(player.getMaxHP() + 30);
				bigBar2.setProgress(bigProgress2);
				if (bigProgress2 <= 0.34)
				{
					bigBar2.setStyle("-fx-accent: red;");
					if (bigProgress2 > 0 && bigProgress2 <.03)
					{
						bigBar2.setProgress(.03);
					}
				}
				else if (bigProgress2 <= 0.67)
				{
					bigBar2.setStyle("-fx-accent: orange;");
				}
				else bigBar2.setStyle("-fx-accent: lime green;");
				bigBar2.setPrefSize(300, 40);
				
				Label bighp2 = new Label((player.getHP() + 30) + "/" + (player.getMaxHP() + 30));
				bighp2.setFont(thir);
				StackPane hoverHPBar2 = new StackPane(bigBar2, bighp2);
				hoverHPBar2.setPrefSize(490, 150);	
				
				HBox hpBars = new HBox(hoverHPBar, hoverHPBar2);
				hpBars.setLayoutX(300); hpBars.setLayoutY(570);
				
				Button b = new Button(player.getName());
				b.setPrefSize(110, 20);
				b.setOnAction(ea -> {
				player.addMaxhp(30);
				Main.currentMap.displayMap(ps);
				});
				
				//sets the portraits to show up when the mouse is hovered over their button
				b.setOnMouseEntered(ea -> {
					sweater.getChildren().addAll(yourPortrait, hpBars);
					l.setText(Main.enemyInfo(player));
				});
				b.setOnMouseExited(ea -> {
					sweater.getChildren().removeAll(yourPortrait, hpBars);
					l.setText("");
				});
				StackPane button = new StackPane(b);
				button.setPrefSize(150, 720/(Main.party.size() + 1));
					
				ImageView selectionWindow = new ImageView(new Image("others/textLog1-2.jpg"));
				selectionWindow.setFitWidth(300); selectionWindow.setFitHeight(720/(Main.party.size() + 1));
				if (selectionWindow.getFitHeight() > 360)
				{
					selectionWindow.setFitHeight(360);
				}
				HBox eachCharacter = new HBox(button, smallHPBar);
				StackPane options = new StackPane(selectionWindow, eachCharacter);
				characters.getChildren().add(options);
			}
			
			Button b = new Button("<- Back");
			b.setPrefSize(100, 20);
			b.setOnAction(ea -> ps.setScene(bossCutscene(ps)));
			ImageView selectionWindow = new ImageView(new Image("others/textLog1-2.jpg"));
			selectionWindow.setFitWidth(300); selectionWindow.setFitHeight(720/(Main.party.size() + 1));
			StackPane backButton = new StackPane(selectionWindow, b);
			backButton.setPrefSize(150, 720/(Main.party.size() + 1));
			characters.getChildren().add(backButton);
			
			ImageView who = new ImageView(new Image("others/textLog1.jpg"));
			who.setFitWidth(980);
			who.setLayoutX(300);
			Label whoLabel = new Label("Who gets the sweater? (+30 Max HP)");
			whoLabel.setFont(thir);
			StackPane whoWillFight = new StackPane(whoLabel);
			whoWillFight.setLayoutX(300);
			whoWillFight.setPrefHeight(75); whoWillFight.setPrefWidth(980);
			StackPane infos = new StackPane(yourInfo);
			infos.setLayoutX(300); infos.setLayoutY(75);
			
			Label arrow = new Label("->");
			arrow.setFont(thir);
			StackPane arrowPane = new StackPane(arrow);
			arrowPane.setPrefSize(380, 150);
			arrowPane.setLayoutX(600); arrowPane.setLayoutY(570);
			//creates the background
			
			sweater.getChildren().addAll(who, whoWillFight, infos, characters, arrowPane);
			ps.setScene(scene);
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
