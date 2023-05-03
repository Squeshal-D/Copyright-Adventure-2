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

public class Ramsay extends Character{
	
	ImageView portrait = new ImageView(new Image ("portraits/ramsay.jpg"));
	ImageView stage = new ImageView(new Image ("stages/ramsayStage.jpg"));
	String name = "Gordon Ramsay";
	Media theme = new Media(new String(Main.class.getResource("themes/deathByGlamour.mp3").toString()));
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
	
	String move1 = "Pan Slam";
	String move2 = "Scold";
	String move3 = "Gordonmet";
	String move1desc = "I could've made so many pan jokes.\nBut I didn't.\nDeals 15 damage.";
	String move2desc = "There is no lamb sauce.\nDoes 10 damage.\nGives enemy +1 weakness (-50% dmg on next attack).";
	String move3desc = "Instead of gourmet, get it???\nHeals 20 hp and clears status to a party member.\nCan only be used 5 times per battle.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(490);
		b.setLayoutY(520);
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
		misc = 0;
		hp = maxhp;
		status.clear();
		teammate = true;
		charging = false;
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
		int dmg = modifydmg(15);
		Timeline atk = new Timeline();
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " hits " + enemy.getName() + " with a pan for " + dmg + " damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		enemy.changeHP(-dmg);
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = user.modifydmg(10);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " says AAAA at " + enemy.getName() + " for " + dmg + " damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		enemy.changeHP(-dmg);
		enemy.addStat("weak");
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Font thir = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 30);
		
		Timeline atk = new Timeline();
		if (misc >= 5)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " is out of ingredients! They must be restocked."));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			atk.getKeyFrames().addAll(first, second);
		}
		
		else if (teammate && !enemy.getName().equals("Envy"))
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
						log.setText(user.getName() + " prepares a gourmet meal for " + player.getName() + ". They recover 20 hp.");
						player.changeHP(20);
						for (int x = player.getStatus().size() - 1; x >= 0; x--)
						{
							if (!player.getStatus().get(x).equals("greed"))
							{
								player.getStatus().remove(x);
							}
						}
					});
					misc++;
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
			Label whoLabel = new Label("Who will recieve this gourmet meal?");
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
			
			ImageView image = new ImageView(new Image("others/gordonmet.jpg"));
			image.setLayoutX(300); image.setLayoutY(150);
			
			gordonmet.getChildren().addAll(who, image, characters, whoWillFight, back, infoHolder);
			
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> {
				Scene s = new Scene(gordonmet);
				ps.setScene(s);
			});
			
			
			atk.getKeyFrames().addAll(first, second);
		}
		else if (enemy.getName().equals("Envy"))
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(enemy.getName() + " said, \"That food is mine.\" And so it was his."));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			misc++;
			enemy.changeHP(20);
			enemy.getStatus().clear();
			atk.getKeyFrames().addAll(first, second);
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " prepares and devours a gourmet meal. They recover 25 hp."));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			misc++;
			user.changeHP(25);
			atk.getKeyFrames().addAll(first, second);
		}
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (player.getHP() <= 15)
		{
			i = 1;
		}
		else if (player.isCharging() && !player.getMoveName(2).equals("Smash") && !player.getMoveName(1).equals("Headbutt"))
		{
			i = 2;
		}
		else if (hp > 35 || misc >= 5)
		{
			i = (int) (Math.random()*2 + 1);
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
				return move3 + " (" + (5 - misc) + ")";
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
	
	public void addMisc(int i)
	{
		misc = i;
	}
	
	public void addMaxhp(int i)
	{
		maxhp += i;
		changeHP(i);
	}
	
	public String twitter()
	{
		return "(Censored)";
	}
}
