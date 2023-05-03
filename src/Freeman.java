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

public class Freeman extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/freemanPortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/freemanStage.jpg"));
	String name = "Gordon Freeman";
	Media theme = new Media(new String(Main.class.getResource("themes/battleAgainstAMachine.mp3").toString()));
	double volume = 1;
	int hp = 70;
	int maxhp = 70;
	int misc = 1;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Gravity Toss";
	String move2 = "Mad Crowbar (1)";
	String move3 = "Triple Threat";
	String move1desc = "It's a sacrifice I am willing to make.\nThrow a party member at an enemy.\nBoth people take 40% of each other's current hp as damage.";
	String move2desc = "That's why they're red.\n5 damage per charge.\n+1 charge after every turn. Goes back to 1 charge after use.";
	String move3desc = "The scariest enemies are the ones you can't comprehend.\nDoes 33 damage on enemies with an hp ending in 3.\n"
			+ "Otherwise, brings enemy hp down to the nearest number ending in 3.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(1090);
		b.setLayoutY(400);
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
		misc = 1;
		move2 = "Mad Crowbar (1)";
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
		Font twfr = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 24);
		Font thir = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 30);
		
		Timeline atk = new Timeline();
		if (teammate)
		{
			KeyFrame second = new KeyFrame(Duration.INDEFINITE);
			//Main.sleep = false;
			
			Pane gtoss = new Pane();
			VBox characters = new VBox();
			
			Label yourInfo = new Label("");
			yourInfo.setFont(twfr);
			StackPane infoHolder = new StackPane(yourInfo);
			infoHolder.setPrefSize(490, 100);
			
			Label theirInfo = new Label("(" + (int)((double)enemy.getHP()*0.4) + " dmg)\n" + Main.enemyInfo(enemy));
			theirInfo.setFont(twfr);
			StackPane infoHolder2 = new StackPane(theirInfo);
			infoHolder2.setPrefSize(490, 100);
			
			HBox infos = new HBox(infoHolder, infoHolder2);
			infos.setLayoutX(300); infos.setLayoutY(50);
			
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
				b.setOnMouseEntered(e -> yourInfo.setText("(" + (int)((double)player.getHP()*0.4) + " dmg)\n" + Main.enemyInfo(player)));
				b.setOnMouseExited(e -> yourInfo.setText(""));
				b.setOnAction(e -> {
					ps.setScene(Main.fightScreen);
					int dmg = modifydmg((int) (player.getHP()*.4));
					int enemydmg = (int) (enemy.getHP()*.4);
					enemy.changeHP(-dmg);
					player.changeHP(-enemydmg);
					
					KeyFrame first = new KeyFrame(Duration.millis(1), ae -> {
						log.setText(user.getName() + " throws " + player.getName() + "(-" + enemydmg + ") at " + enemy.getName() + "(-" + dmg + ")!");
						misc++;
						move2 = "Mad Crowbar (" + misc + ")";
					});
					atk.getKeyFrames().clear();
					if (player.getMoveName(1) != "Gravity Toss" && player.getHP() <= 0)
					{
						KeyFrame scond = new KeyFrame(Duration.seconds(2), ae -> {
							log.setText("Nice going. That got " + player.getName() + " killed, you buffoon!");
							Main.party.remove(player);
						});
						atk.getKeyFrames().add(scond);
					}
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
			Label whoLabel = new Label("Who will be thrown?");
			whoLabel.setFont(thir);
			StackPane whoWillFight = new StackPane(whoLabel);
			whoWillFight.setLayoutX(300);
			whoWillFight.setPrefHeight(50); whoWillFight.setPrefWidth(980);
			
			Button back = new Button("<- Back");
			back.setPrefSize(200, 100);
			back.setLayoutX(1030); back.setLayoutY(570);
			back.setOnAction(e -> {
				Main.sleep = true;
				Main.fight(user, enemy, Main.party, false, ps);
			});
			
			ImageView image = new ImageView(new Image("others/gordonToss.png"));
			image.setLayoutX(300); image.setLayoutY(150);
			
			gtoss.getChildren().addAll(who, image, characters, whoWillFight, back, infos);
			
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> {
				Scene s = new Scene(gtoss);
				ps.setScene(s);
			});
			
			
			atk.getKeyFrames().addAll(first, second);
		}
		else
		{
			String[] names = new String[] {"Alyx Vance", "Dr. Breen", "Barney", "Eli Vance", "Dr. Kleiner", "Father Grigory"};
			int rand = (int) (Math.random()*6);
			String name = names[rand];
			int dmg = modifydmg(4*(rand+1));
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " launches " + name + " at " + enemy.getName() + "(-" + dmg + ")!"));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			enemy.changeHP(-dmg);
			atk.getKeyFrames().addAll(first, second);
		}
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = modifydmg(5*misc);
		int m = misc;
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " hits " + enemy.getName() + " with a crowbar " + m + " times!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		enemy.changeHP(-dmg);
		misc = 1;
		move2 = "Mad Crowbar (" + misc + ")";
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
		if (enemy.getHP()%10 == 3)
		{
			
			if (enemy.getMoveName(1).equals("Steam Sale"))
			{
				int dmg = user.modifydmg(66);
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText("33 damage? " + enemy.getName() + " would rather take 66!"));
				atk.getKeyFrames().add(first);
				enemy.changeHP(-dmg);
			}
			else
			{
				int dmg = user.modifydmg(33);
				int hp = enemy.getHP();
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " is triggered by the 3 in " + hp + "! "
					+ enemy.getName() + " takes " + dmg + " damage!"));
				atk.getKeyFrames().add(first);
				enemy.changeHP(-dmg);
			}
			
			if (user.getName().equals("Gordon Freeman"))
			{
				misc++;
				move2 = "Mad Crowbar (" + misc + ")";
			}
			
		}
		else
		{
			int dmg = 0;
			for (int i = 1; i < 10; i++)
			{
				if (Math.abs((enemy.getHP()-i)%10) == 3)
				{
					dmg = i;
				}
			}
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " scientifically reduces " + enemy.getName() + "'s hp to contain" 
					+ " a 3."));
			atk.getKeyFrames().add(first);
			enemy.changeHP(-dmg);
			misc++;
			move2 = "Mad Crowbar (" + misc + ")";
		}
		
        return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (player.getHP()%10 == 3)
		{
			i = 3;
		}
		else if (misc >= 3)
		{
			i = (int) (Math.random()*3 + 1);
		}
		else
		{
			int r = (int) (Math.random()*2);
			if (r == 0)
			{
				i = 1;
			}
			else
			{
				i = 3;
			}
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
		return ".";
	}
}
