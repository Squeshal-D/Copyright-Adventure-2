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

public class Gandalf extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/gandalfPortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/gandalfStage.jpg"));
	String name = "Gandalf";
	Media theme = new Media(new String(Main.class.getResource("themes/insaneCultist.mp3").toString()));
	double volume = 1;
	int hp = 60;
	int maxhp = 60;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Elf's Blessing";
	String move2 = "Fireball";
	String move3 = "Hat Stab";
	String move1desc = "Some also call him Gandalf the Compassionate.\nGives a teammate 3 heal statuses.\nEvery heal heals 10 hp after a turn.";
	String move2desc = "Magic words burn more than rap lyrics.\nDoes 10 damage.\nEnemy burns (7 dmg) for 3 turns.";
	String move3desc = "It wasn't an accident this time.\n+5 damage for each stat effect the enemy has (max 30 dmg).\nCounts stacked effects, "
			+ "like rage and burn.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(390);
		b.setLayoutY(200);
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
		status.clear();
		teammate = true;
		charging = false;
		misc = 0;
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
		Font thir = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 30);
		
		Timeline atk = new Timeline();
		if (teammate)
		{
			KeyFrame second = new KeyFrame(Duration.INDEFINITE);
			//Main.sleep = false;
			
			Pane gordonmet = new Pane();
			VBox characters = new VBox();
			
			Label info = new Label("");
			info.setFont(thir);
			StackPane infoHolder = new StackPane(info);
			infoHolder.setPrefSize(980, 75);
			infoHolder.setLayoutX(300); infoHolder.setLayoutY(75);
			
			for (int i = 0; i < Main.party.size(); i++)
			{
				Character player = Main.party.get(i);
				
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
				b.setOnMouseEntered(e -> info.setText(Main.enemyInfo(player)));
				b.setOnMouseExited(e -> info.setText(""));
				b.setOnAction(e -> {
					ps.setScene(Main.fightScreen);
					
					KeyFrame first = new KeyFrame(Duration.millis(1), ae -> {
						log.setText(user.getName() + " casts a healing spell on " + player.getName() + ".");
						player.addStat("heal");
						player.addStat("heal");
						player.addStat("heal");
					});
					atk.getKeyFrames().clear();
					atk.getKeyFrames().add(first);
					atk.playFromStart();
					//Main.fight(user, enemy, Main.party, true, ps);
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
				characters.getChildren().add(options);
			}
			
			ImageView who = new ImageView(new Image("others/textLog1.jpg"));
			who.setFitWidth(980);
			who.setLayoutX(300);
			Label whoLabel = new Label("Cast healing on who?");
			whoLabel.setFont(thir);
			StackPane whoWillFight = new StackPane(whoLabel);
			whoWillFight.setLayoutX(300);
			whoWillFight.setPrefHeight(75); whoWillFight.setPrefWidth(980);
			
			Button back = new Button("<- Back");
			back.setPrefSize(200, 100);
			back.setLayoutX(1030); back.setLayoutY(570);
			back.setOnAction(e -> {
				//Main.sleep = true;
				Main.fight(user, enemy, Main.party, false, ps);
			});
			
			ImageView image = new ImageView(new Image("others/gandalfHeal.png"));
			image.setLayoutX(300); image.setLayoutY(150);
			
			gordonmet.getChildren().addAll(who, image, characters, whoWillFight, back, infoHolder);
			
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> {
				Scene s = new Scene(gordonmet);
				ps.setScene(s);
			});
			
			
			atk.getKeyFrames().addAll(first, second);
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " casts a healing spell on himself."));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			user.addStat("heal");
			user.addStat("heal");
			user.addStat("heal");
			atk.getKeyFrames().addAll(first, second);
		}
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = user.modifydmg(10);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " fireballs " + enemy.getName() + " for " + dmg + " damage!"));
		enemy.changeHP(-dmg);
		enemy.addStat("burn");
		enemy.addStat("burn");
		enemy.addStat("burn");
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int d = 5*enemy.getStatus().size();
		if (d > 30)
		{
			d = 30;
		}
		int dmg = modifydmg(d);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " runs hat first into " + enemy.getName() + " for " + dmg + " damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		enemy.changeHP(-dmg);
		atk.getKeyFrames().addAll(first, second);
        return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		int d = 5*player.getStatus().size();
		int calc = enemy.modifydmg(d);
		if (calc >= player.getHP()) 
		{
			i = 3;
		}
		else if (enemy.hasStat("heal"))
		{
			if (player.hasStat("burn"))
			{
				if (calc > 10)
				{
					i = 3;
				}
				else
				{
					i = 2;
				}
			}
			else if (calc < 15)
			{
				i = 2;
			}
		}
		else if (calc < 15)
		{
			i = (int)(Math.random()*2 + 1);
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
			if (Main.currentEnemy == null)
			{
				return move3;
			}
			else
			{
				int d = 5*Main.currentEnemy.getStatus().size();
				if (d > 30)
				{
					d = 30;
				}
				return move3 + " (" + d + ")";
			}
		}
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
	
	public boolean isTesting()
	{
		return testing;
	}
	
	public boolean heardSong()
	{
		return song;
	}
	
	public void unCharge()
	{
		charging = false;
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
	
	public void addMaxhp(int i)
	{
		maxhp += i;
		changeHP(i);
	}
	
	public String twitter()
	{
		return "You shall not pass!";
	}
}
