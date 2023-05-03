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

public class Scott extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/darkScott.jpg"));
	ImageView stage = new ImageView(new Image ("stages/scottStage.jpg"));
	String name = "Dark Scott";
	Media theme = new Media(new String(Main.class.getResource("themes/absoluteTerror.mp3").toString()));
	double volume = 1;
	int hp = 200;
	int maxhp = 200;
	int misc = 0;
	boolean hv = false;;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = true;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Hyper Voice (" + (5 + misc*5) + ")";
	String move2 = "Kolibri";
	String move3 = "Venom";
	String move1desc = "Say something a little too loud for comfort.\nGets louder (+5) on every use.\nCaps at 45 damage";
	String move2desc = "A Kolibri is a small pistol.\nDoes 25 damage.\nOnly hits enemies with status effects.";
	String move3desc = "Venommmmmmmm.\nPoisons the enemy.\nPoisoned enemies take 5 damage after their moves.";
	
	Button button = new Button();
	
	MediaPlayer upgrade;
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(980);
		b.setLayoutY(560);
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
		name = "Scott";
		maxhp = 50;
		hp = maxhp;
		misc = 0;
		teammate = true;
		charging = false;
		move1 = "Hyper Voice (" + (misc*5 + 5) + ")";
		portrait = new ImageView(new Image ("portraits/scottPortrait.jpg"));
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
		hv = true;
		if (user.getMoveName(2).equals("Kolibri"))
		{
			misc += 1;
			if (misc > 9)
			{
				misc = 9;
			}
			move1 = "Hyper Voice (" + (misc*5 + 5) + ")";
		}
		if (user.getName().equals("Stingy"))
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " tried being obnoxious, but " + enemy.getName() + " loved it "
					+ "and got all riled up!"));
			atk.getKeyFrames().add(first);
			atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
			user.changeHP(-user.getHP());
			
			misc += 4;
			if (misc > 9)
			{
				misc = 9;
			}
			move1 = "Hyper Voice (" + (misc*5 + 5) + ")";
		}
		else
		{
			int dmg = modifydmg(5*misc);
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " says something too loud!\n" + 
					enemy.getName() + " nervously takes " + dmg + " damage!"));
			atk.getKeyFrames().add(first);
			atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
			enemy.changeHP(-dmg);
		}
		
		
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		hv = false;
		if (enemy.getStatus().size()<1)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(enemy.getName() + " dodges " + user.getName() + "'s Kolibri shot!"));
			atk.getKeyFrames().add(first);
		}
		else
		{
			int dmg = modifydmg(25);
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " shoots " + enemy.getName() + 
					" with a Kolibri for " + dmg + " damage!"));
			atk.getKeyFrames().add(first);
			enemy.changeHP(-dmg);
		}
		atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
		
		return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		hv = false;
		if (enemy.getMoveName(2).equals("Andrew Garfield"))
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " poisons " + enemy.getName() + " by singing Eminem's 'Venom.'"));
			KeyFrame second = new KeyFrame(Duration.seconds(2), ae -> {
				
				log.setText("The song awakened the power of Venom within " + enemy.getName() + ".\nThey will now automatically use Andrew Garfield upon death!");
				upgrade = new MediaPlayer(new Media(new String(Main.class.getResource("others/pmdEvolve.mp3").toString())));
				upgrade.setVolume(.32*Main.volume);
				upgrade.play();
			});
			KeyFrame third = new KeyFrame(Duration.seconds(6));
			atk.getKeyFrames().addAll(first, second, third);
			enemy.addStat("PSN");
			enemy.addStat("Venom");
		}
		else if (!enemy.hasStat("PSN"))
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " poisons " + enemy.getName() + " by singing Eminem's 'Venom.'"));
			atk.getKeyFrames().add(first);
			enemy.addStat("PSN");
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(enemy.getName() + " is already poisoned!"));
			atk.getKeyFrames().add(first);
		}
		
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
		
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*2 + 1);
		if (i == 1 && hv)
		{
			i = 2;
		}
		if (!player.hasStat("PSN") && i == 2)
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
	
	public void addMaxhp(int i)
	{
		maxhp += i;
		changeHP(i);
	}
	
	public String twitter()
	{
		return "Just hit up the M&M concert.";
	}
	
	public Scene bossCutscene(Stage ps)
	{
		Font sixt = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 16);
		
		ImageView special = new ImageView(new Image("others/scottCutscene.jpg"));
		
		Label specialText = new Label("Scott got completely rekt and the darkness was beat out of him. He now lays helplessly on the ground. You can "
				+ "recruit him in his new, weaker state. Alternatively, you can let him go back to his family, become the town hero, and feel better "
				+ "about drinking from the fountain one more time.");
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
		Button action = new Button("Recruit");
		action.setPrefSize(200, 70);
		action.setOnAction(e -> {
			boss = false;
			Main.party.add(this);
			Main.currentMap.displayMap(ps);
		});
		
		Button action2 = new Button("Liberate");
		action2.setPrefSize(200, 70);
		action2.setOnAction(e -> {
			Main.currentMap.changeEvent(3);
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
