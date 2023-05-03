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

public class Gaben extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/gabenPortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/gabenStage.jpg"));
	String name = "Gabe Newell";
	Media theme = new Media(new String(Main.class.getResource("themes/palkiasOnslaught.mp3").toString()));
	double volume = 1.3;
	int hp = 150;
	int maxhp = 150;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = true;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Steam Sale";
	String move2 = "Pow";
	String move3 = "Bribe";
	String move1desc = "Takes percentage off enemy's health";
	String move2desc = "Does 10 damage";
	String move3desc = "Kills an enemy if they were out too long";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(330);
		b.setLayoutY(60);
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
		int percentAdd = (int) (Math.random()*21);
		if (enemy.getMoveName(1).equals("Gravity Toss"))
		{
			percentAdd = 3;
		}
		int percent = 30 + percentAdd;
		int dmg = (int) ((double) enemy.getHP() * (double)percent/100);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " made " + enemy.getName() + " " + (int) percent + "% off!"));
		atk.getKeyFrames().add(first);
		atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
		enemy.changeHP(-dmg);
		enemy.addStat("greed");
		
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = modifydmg(10);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " makes a finger gun!\n" + 
		"Pow! " + enemy.getName() + " takes " + dmg + " damage!"));
		enemy.changeHP(-dmg);
		enemy.addStat("greed");
		atk.getKeyFrames().add(first);
		
		atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
		
		return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		
		int dollars = (int) (Math.random()*50);
		int cents = (int) (Math.random()*100);
		String centsPrint = "" + cents;
		
		if (cents < 10)
		{
			centsPrint = "0" + cents;
		}
		final String finalCent = centsPrint;
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " bribed " + enemy.getName() + " to die for $" + dollars + "." + 
				finalCent + "."));
		atk.getKeyFrames().add(first);
		enemy.changeHP(-enemy.getHP());
		
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
		
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = 1;
		int grd = 0;
		for (int x = player.getStatus().size() - 1; x >= 0; x--)
		{
			if (player.getStatus().get(x).equals("greed"))
			{
				grd ++;
			}
		}
		if (grd >= 3)
		{
			i = 3;
		}
		else if (player.getHP() <= 10)
		{
			i = 2;
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
	
	public void addMaxhp(int i)
	{
		maxhp += i;
		changeHP(i);
	}
	
	public Scene bossCutscene(Stage ps)
	{
		Font sixt = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 16);
		
		for (int i = 0; i < Main.party.size(); i++)
		{
			for (int j = Main.party.get(i).getStatus().size() - 1; j >= 0; j--)
			{
				if (Main.party.get(i).getStatus().get(j).equals("greed"))
				{
					Main.party.get(i).getStatus().remove(j);
				}
			}
		}
		
		ImageView special = new ImageView(new Image("others/gabenCutscene.jpg"));
		
		Label specialText = new Label("Your party has saved the city from Gaben's evil plans that he probably had and put thousands out of a job. With all "
				+ "employees fleeing for their lives, Valve HQ is left empty. With the whole place to yourself, the party decides to party right then and there. "
				+ "Are you a health-minded party that eats fruit from the snack bar (+15 hp for all) or a rowdy bunch that drinks all the coffee from the coffee "
				+ "machines? (+1 rage for all)");
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
		Button action = new Button("Fruit!");
		action.setPrefSize(200, 70);
		action.setOnAction(e -> {
			for (int i = 0; i < Main.party.size(); i++)
			{
				Main.party.get(i).changeHP(15);
			}
			Main.currentMap.displayMap(ps);
		});
		
		Button action2 = new Button("Coffee!");
		action2.setPrefSize(200, 70);
		action2.setOnAction(e -> {
			for (int i = 0; i < Main.party.size(); i++)
			{
				Main.party.get(i).addStat("rage");
			}
			Main.currentMap.displayMap(ps);
		});
		
		StackPane aButton = new StackPane(buttonBox, action);
		StackPane bButton = new StackPane(buttonBox2, action2);
		
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
		return s;
	}
}