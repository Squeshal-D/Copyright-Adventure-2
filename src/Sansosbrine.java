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

public class Sansosbrine extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/sansosbrinePortrait.png"));
	ImageView stage = new ImageView(new Image ("stages/ssbStage.png"));
	String name = "Sansosbrine";
	Media theme = new Media(new String(Main.class.getResource("themes/iAmSatan.mp3").toString()));
	double volume = 1;
	int hp = 300;
	int maxhp = 300;
	int misc = 1;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = true;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Snap/Thanos Car";
	String move2 = "Gravity/Censored";
	String move3 = "TNT/Creeper";
	String move1desc = "Halves the enemy's hp./Does 20 damage";
	String move2desc = "Does 15 damage to enemy and random party member/To all members chance of: +weak, -5 to 5 dmg, +burn, stat clear, nothing (weighted x2)";
	String move3desc = "Charges TNT, which does 5 dmg to party/Does 10 to 30 damage";
	
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
		int r = (int)(Math.random()*2);
		Timeline atk = new Timeline();
		
		if (r == 0)
		{
			if (enemy.getHP() <= 10 || enemy.getMoveName(2).equals("Andrew Garfield"))
			{
				int dmg = enemy.getHP();
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " snapped! " + enemy.getName() + " doesn't feel so good..."));
				atk.getKeyFrames().add(first);
				atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
				enemy.changeHP(-dmg);
			}
			else
			{
				int dmg = enemy.getHP()/2;
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " snapped! " + enemy.getName() + " lost half of their hp!"));
				atk.getKeyFrames().add(first);
				atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
				enemy.changeHP(-dmg);
			}
		}
		
		else
		{
			int dmg = modifydmg(20);
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " hit " + enemy.getName() + " with the THANOS CAR for " + dmg + " dmg!"));
			atk.getKeyFrames().add(first);
			atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
			enemy.changeHP(-dmg);
		}
		
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		int r = (int)(Math.random()*2);
		Timeline atk = new Timeline();
		
		if (r == 0)
		{
			Character victim = Main.party.get((int) (Math.random()*Main.party.size()));
			
			if (victim == enemy)
			{
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " rapidly changes the direction of gravity!"));
				KeyFrame second = new KeyFrame(Duration.millis(2000), ae -> log.setText(enemy.getName() + " didn't collide with anyone!"));
				atk.getKeyFrames().addAll(first, second);
			}
			else
			{
				int dmg = modifydmg(15);
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " rapidly changes the direction of gravity!"));
				KeyFrame second = new KeyFrame(Duration.millis(2000), ae -> log.setText(enemy.getName() + " collided with " + victim.getName() + "!\nBoth take " +
						dmg + " dmg!"));
				enemy.changeHP(-dmg);
				victim.changeHP(-dmg);
				atk.getKeyFrames().addAll(first, second);
				
				if (victim.getHP() < 1)
				{
					KeyFrame third = new KeyFrame(Duration.millis(4000), ae -> {
						log.setText(victim.getName() + " sadly did not survive the impact.");
						MediaPlayer gm = new MediaPlayer(new Media(new String(Main.class.getResource("others/earthboundDeath.mp3").toString())));
						gm.setVolume(.16*volume);
						gm.play();
					});
					Main.party.remove(victim);
					atk.getKeyFrames().add(third);
				}
			}
		}
		
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " censored this move."));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			
			for (int i = Main.party.size() - 1; i >= 0; i--)
			{
				int rand = (int)(Math.random()*6);
				
				if (rand == 0)
				{
					Main.party.get(i).addStat("weak");
				}
				else if (rand == 1)
				{
					Main.party.get(i).changeHP((int)(Math.random()*11 - 5));
					
					if (Main.party.get(i).getHP() < 1 && enemy != Main.party.get(i))
					{
						Main.party.remove(i);
					}
				}
				else if (rand == 2)
				{
					Main.party.get(i).addStat("burn");
				}
				else if (rand == 3)
				{
					Main.party.get(i).getStatus().clear();
				}
			}
			
			atk.getKeyFrames().addAll(first, second);
		}
		
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		int r = (int)(Math.random()*2);
		Timeline atk = new Timeline();
		
		if (charging)
		{
			int dmg = modifydmg(5);
			
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText("The TNT exploded! Everyone took " + dmg + " dmg!"));
			atk.getKeyFrames().add(first);
			atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
			
			boolean x = false;
			int newl = 0;
			String dudes = "";
			for (int i = Main.party.size() - 1; i >= 0; i--)
			{
				Main.party.get(i).changeHP(-dmg);
				if (Main.party.get(i).getHP() < 1 && Main.party.get(i) != enemy)
				{
					newl ++;
					if (x)
					{
						dudes += "+ ";
					}
					if (newl%4 == 0)
					{
						dudes += "\n";
					}
					dudes += Main.party.get(i).getName() + " ";
					x = true;
					
					Main.party.remove(i);
				}
			}
			
			if (!dudes.equals(""))
			{
				final String bruh = dudes + "will not survive the blast.";
				KeyFrame third = new KeyFrame(Duration.millis(2000), ae -> {
					log.setText(bruh);
					MediaPlayer gm = new MediaPlayer(new Media(new String(Main.class.getResource("others/earthboundDeath.mp3").toString())));
					gm.setVolume(.16*volume);
					gm.play();
				});
				atk.getKeyFrames().add(third);
			}
			
			charging = false;
		}
		
		else if (r == 0)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " lights the TNT!"));
			atk.getKeyFrames().add(first);
			atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
			charging = true;
		}
		
		else
		{
			int dmg = modifydmg((int)(Math.random()*21 + 10));
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " spawns a creeper behind " + enemy.getName() + "! They took " 
					+ dmg + " dmg!"));
			atk.getKeyFrames().add(first);
			atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
			enemy.changeHP(-dmg);
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
		else
		{
			i = misc;
			misc = misc%3 + 1;
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
		
		TheSystem theSystem = new TheSystem();
		
		Pane p = new Pane();
		
		Image firsti = new Image("others/ssbCutscene.png");
		String firstl = "The monster is defeated and the party barely escapes the explosion from the self-destruct sequence! Now you can continue to the coordinates "
				+ "where you've discovered that large disturbance. That must be where the final boss is!";
		
		Image secondi = new Image("others/kaiser.PNG");
		String secondl = "The coordinates lead to no other place but Kaiser Permanente. You should have known all along. One of the party member fakes a broken finger "
				+ "so that the party is quickly granted access to the hospital. After punching the nurses in the face in the surgery room, the party quickly heads to "
				+ "the basement.";
		
		Image thirdi = new Image("others/finalCutscene.png");
		String thirdl = "The basement is quite massive, and only illuminated by the large, dystopian looking monitor that bears the image of a stern man. The monitor "
				+ "speaks. \"So you have finally come. After everything that you know, you still choose to fight me. You know I cannot let myself be defeated for the "
				+ "good of everyone, even you. But, so be it. Have at thee!\"";
		
		ImageView showing = new ImageView(firsti);
		showing.setFitWidth(1280); showing.setFitHeight(570);
		Label reading = new Label(firstl);
		reading.setFont(sixt);
		reading.setMaxWidth(1000);
		reading.setWrapText(true);
		
		ImageView text = new ImageView(new Image("others/textLog1.jpg"));
		text.setFitWidth(1080); text.setFitHeight(150);
		ImageView button = new ImageView(new Image("others/textLog1-2.jpg"));
		button.setFitWidth(200); button.setFitHeight(150);
		
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(secondi);
		images.add(thirdi);
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(secondl);
		strings.add(thirdl);
		
		Button next = new Button("Continue");
		next.setPrefSize(160, 120);
		next.setOnAction(ae -> {
			if (images.size() == 0 && strings.size() == 0)
			{
				Main.m = new MediaPlayer(theSystem.getTheme());
				Main.m.setVolume(.32*Main.volume*theSystem.getVolume());
				Main.m.setCycleCount(1);
				Main.m.setOnEndOfMedia(new Runnable() {
					@Override
					public void run() {
						Main.m = new MediaPlayer(new Media(new String(Main.class.getResource("themes/ootGanon2.mp3").toString())));
						Main.m.setVolume(.32*Main.volume*theSystem.getVolume());
						Main.m.setCycleCount(MediaPlayer.INDEFINITE);
						Main.m.play();
					}
				});
				Main.m.play();
				Main.chooseFighter(null, theSystem, Main.party, ps);
			}
			else
			{
				showing.setImage(images.get(0));
				images.remove(0);
				reading.setText(strings.get(0));
				strings.remove(0);
			}
		});
		
		StackPane textBox = new StackPane(text, reading);
		StackPane buttonBox = new StackPane(button, next);
		HBox boxes = new HBox(textBox, buttonBox);
		boxes.setLayoutY(570);
		
		p.getChildren().addAll(showing, boxes);
		
		Scene s = new Scene(p);
		return s;
	}
}