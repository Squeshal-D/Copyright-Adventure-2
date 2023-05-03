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

public class TheSystem extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/systemPortrait1.png"));
	ImageView stage = new ImageView(new Image ("stages/systemStage.png"));
	String name = "The System";
	Media theme = new Media(new String(Main.class.getResource("themes/ootGanon1.mp3").toString()));
	double volume = 1;
	int hp = 500;
	int maxhp = 500;
	int misc = 0;
	int moveChoice = 0;
	boolean firstLine = false;
	boolean secondLine = false;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = true;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Virus/Cache";
	String move2 = "Electrocute";
	String move3 = "Strobe/Twitter";
	String move1desc = "50% chance to add every negative stat effect/50% chance to remove every status effect (on self) and +4 hp for each status removed";
	String move2desc = "Does 16 damage";
	String move3desc = "Charges for 2 turns, then instakills the opponent/Chance to get ratio'd, maybe badly";
	
	Button button = new Button();
	
	MediaPlayer end;
	
	public void createButton(Button b)
	{
		//button = b;
		//b.setLayoutX(490);
		//b.setLayoutY(480);
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
		
		if ((double) hp/maxhp <= .34)
		{
			portrait = new ImageView(new Image("portraits/systemPortrait3.png"));
			for (int x = status.size() - 1; x >= 0; x--)
			{
				if (status.get(x).equals("rage"))
				{
					status.remove(x);
				}
			}
			status.add("rage");
			status.add("rage");
		}
		else if ((double) hp/maxhp <= .67)
		{
			portrait = new ImageView(new Image("portraits/systemPortrait2.png"));
			for (int x = status.size() - 1; x >= 0; x--)
			{
				if (status.get(x).equals("rage"))
				{
					status.remove(x);
				}
			}
			status.add("rage");
		}
		else
		{
			portrait = new ImageView(new Image("portraits/systemPortrait1.png"));
			for (int x = status.size() - 1; x >= 0; x--)
			{
				if (status.get(x).equals("rage"))
				{
					status.remove(x);
				}
			}
		}
	}
	
	public void refresh()
	{
		portrait = new ImageView(new Image("portraits/systemPortrait1.png"));
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
		int dur = 0;
		if ((double) hp/maxhp <= .67 && firstLine == false)
		{
			firstLine = true;
			KeyFrame k = new KeyFrame(Duration.ZERO, ae -> log.setText("\"None of this will change the past. You have to let them go, " + Main.name + ".\""));
			dur += 3;
			atk.getKeyFrames().add(k);
		}
		else if ((double) hp/maxhp <= .34 && secondLine == false)
		{
			secondLine = true;
			KeyFrame k = new KeyFrame(Duration.ZERO, ae -> log.setText("\"What will you get out of killing me!? You'll doom us all!\""));
			dur += 3;
			atk.getKeyFrames().add(k);
		}
		
		if (moveChoice == 1)
		{
			KeyFrame first = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " injected " + enemy.getName() + " with a virus!"));
			int psn = (int)(Math.random()*2);
			int bld = (int)(Math.random()*2);
			int weak = (int)(Math.random()*2);
			int brn = (int)(Math.random()*2);
			String stats = "";
			
			if (psn == 1)
			{
				enemy.addStat("PSN");
				stats += "poison";
			}
			if (bld == 1)
			{
				enemy.addStat("BLD");
				if (stats.length() > 0)
				{
					stats += " + ";
				}
				stats += "bleed";
			}
			if (weak == 1)
			{
				enemy.addStat("weak");
				if (stats.length() > 0)
				{
					stats += " + ";
				}
				stats += "weak";
			}
			if (brn == 1)
			{
				enemy.addStat("burn");
				if (stats.length() > 0)
				{
					stats += " + ";
				}
				stats += "burn";
			}
			if (stats.length() == 0)
			{
				stats = "nothing";
			}
			
			final String s = stats;
			KeyFrame second = new KeyFrame(Duration.seconds(dur + 2), ae -> log.setText(enemy.getName() + " was inflicted with " + s + "!"));
			
			atk.getKeyFrames().addAll(first, second);
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " cleared their cache!"));
			int removed = 0;
			
			for (int i = status.size() - 1; i >= 0; i--)
			{
				if (!status.get(i).equals("rage") && (int)(Math.random()*2) == 1)
				{
					status.remove(i);
					removed += 1;
					user.changeHP(4);
				}
			}
			
			atk.getKeyFrames().add(first);
			final int r = removed;
			final int heal = removed*4;
			
			KeyFrame second = new KeyFrame(Duration.seconds(dur + 2),ae ->log.setText(r + " statuses were cleared and " + user.getName() + " healed " + heal + " hp!"));
			atk.getKeyFrames().add(second);
		}
		
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dur = 0;
		if ((double) hp/maxhp <= .67 && firstLine == false)
		{
			firstLine = true;
			KeyFrame k = new KeyFrame(Duration.ZERO, ae -> log.setText("\"None of this will change the past. You have to let them go, " + Main.name + ".\""));
			dur += 3;
			atk.getKeyFrames().add(k);
		}
		else if ((double) hp/maxhp <= .34 && secondLine == false)
		{
			secondLine = true;
			KeyFrame k = new KeyFrame(Duration.ZERO, ae -> log.setText("\"What will you get out of killing me!? You'll doom us all!\""));
			dur += 3;
			atk.getKeyFrames().add(k);
		}
		
		int dmg = user.modifydmg(16);
		
		KeyFrame first = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " electrocuted " + enemy.getName() + " for " + dmg + " damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		enemy.changeHP(-dmg);
		
		atk.getKeyFrames().addAll(first, second);
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dur = 0;
		if ((double) hp/maxhp <= .67 && firstLine == false)
		{
			firstLine = true;
			KeyFrame k = new KeyFrame(Duration.ZERO, ae -> log.setText("\"None of this will change the past. You have to let them go, " + Main.name + ".\""));
			dur += 3;
			atk.getKeyFrames().add(k);
		}
		else if ((double) hp/maxhp <= .34 && secondLine == false)
		{
			secondLine = true;
			KeyFrame k = new KeyFrame(Duration.ZERO, ae -> log.setText("\"What will you get out of killing me!? You'll doom us all!\""));
			dur += 3;
			atk.getKeyFrames().add(k);
		}
		
		if (charging || moveChoice == 1)
		{
			if (misc == 0)
			{
				KeyFrame first = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " is charging energy... (33%)"));
				misc += 1;
				charging = true;
				atk.getKeyFrames().add(first);
			}
			else if (misc == 1)
			{
				KeyFrame first = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " is charging energy... (67%)"));
				misc += 1;
				atk.getKeyFrames().add(first);
			}
			else
			{
				KeyFrame first = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " flashed strobe lights rapidly!"));
				misc = 0;
				charging = false;
				atk.getKeyFrames().add(first);
				enemy.changeHP(-enemy.getHP());
			}
			KeyFrame second = new KeyFrame(Duration.millis(1));
			atk.getKeyFrames().add(second);
		}
		else
		{
			int userLikes = 0;
			int enemyLikes = 0;
			int dmg = 0;
			KeyFrame first = new KeyFrame(Duration.seconds(dur), ae -> log.setText(user.getName() + " opened Twitter.com!"));
			atk.getKeyFrames().add(first);
			for (int i = 0; i < 10; i++)
			{
				if (Math.random() > 0.4)
				{
					userLikes++;
				}
				if (Math.random() > 0.5)
				{
					enemyLikes++;
				}
				
				final int ul = userLikes;
				final int el = enemyLikes;
				final int round = i+1;
				KeyFrame likes = new KeyFrame(Duration.millis(dur*1000 + 1000 + 200*i), ae -> {
					log.setText(enemy.getName() + ": " + enemy.twitter() + " (" + el + "/" + round + " Likes)\n "
							+ user.getName() + ": ratio (" + ul + "/" + round + " Likes)");
				});
				atk.getKeyFrames().add(likes);
			}
			if (userLikes > enemyLikes)
			{
				dmg = user.modifydmg(10*(userLikes - enemyLikes));
			}
			
			enemy.changeHP(-dmg);
		}
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		moveChoice = 0;
		int playerStats = 0;
		for (int x = 0; x < player.getStatus().size(); x++)
		{
			if (player.getStatus().get(x).equals("PSN") || player.getStatus().get(x).equals("burn") ||
							player.getStatus().get(x).equals("BLD") || player.getStatus().get(x).equals("weak"))
			{
				playerStats++;
			}
		}
		int yourStats = 0;
		for (int x = 0; x < enemy.getStatus().size(); x++)
		{
			if (enemy.getStatus().get(x).equals("PSN") || enemy.getStatus().get(x).equals("burn") ||
					enemy.getStatus().get(x).equals("BLD") || enemy.getStatus().get(x).equals("weak"))
			{
				yourStats++;
			}
		}
		
		if (charging)
		{
			i = 3;
		}
		else if (player.getHP() <= modifydmg(16))
		{
			i = 2;
		}
		else if (yourStats >= 10)
		{
			i = 1;
			moveChoice = 2;
		}
		else if (player.getHP() >= modifydmg(16)*2)
		{
			if (playerStats < 2)
			{
				i = (int)(Math.random()*3 + 1);
				moveChoice = (int)(Math.random()*2 + 1);
				if (i == 1 && yourStats < 5)
				{
					moveChoice = 1;
				}
			}
			else
			{
				i = (int)(Math.random()*2 + 2);
				moveChoice = (int)(Math.random()*2 + 1);
			}
		}
		else
		{
			i = (int)(Math.random()*2 + 2);
			moveChoice = 2;
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
		Main.currentMap = new Map();
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
		misc = 0;
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
		Font thr2 = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 32);
		
		Image end1 = new Image("others/ending1.png");
		Image end2 = new Image("others/ending2.png");
		ImageView theEnd = new ImageView(end2);
		
		ImageView textLog = new ImageView(new Image("others/textLog1.jpg"));
		textLog.setFitWidth(1080);textLog.setFitHeight(150);
		Label text = new Label("The struggle is over. Our hero has fulfilled his goals, but at what cost? What was the meaning of such cruel, cartoony, and wacky "
				+ "violence? Like many things in life, there was no meaning, unless you're an English teacher or conspiracy theorist. It is about now that the player "
				+ "also may ask, \"What was the meaning of playing this game? Why did I do it?\" The answer may be too philosophical or complex for an adult to "
				+ "understand, yet simple enough for a child to grasp.");
		text.setFont(sixt);
		text.setMaxWidth(1000);
		text.setWrapText(true);
		StackPane textBox = new StackPane(textLog, text);
		
		ImageView buttonLog = new ImageView(new Image("others/textLog1-2.jpg"));
		buttonLog.setFitWidth(200); buttonLog.setFitHeight(150);
		Button button = new Button("Continue");
		button.setPrefSize(160, 120);
		StackPane buttonBox = new StackPane(buttonLog, button);
		
		Timeline picture = new Timeline();
		picture.setCycleCount(Timeline.INDEFINITE);
		KeyFrame first = new KeyFrame(Duration.millis(300), ae -> theEnd.setImage(end2));
		KeyFrame second = new KeyFrame(Duration.millis(600), ae -> theEnd.setImage(end1));
		picture.getKeyFrames().addAll(first, second);
		
		HBox bottom = new HBox(textBox, buttonBox);
		bottom.setLayoutY(570);
		
		Label num = new Label("0");
		num.setLayoutX(1000); num.setLayoutY(300);
		
		Label credits = new Label("ARTISTS:\n\n"
				+ "Tonio: New Bongus City \n Drake & Josh \n Paul Blart \n HULK \n Spiderman \n Papa John \n Gabe Newell \n\n"
				+ "Caleb: Da Club \n George Washington \n Shrek \n Harry Potter \n Spongebob \n Kam Samp \n Walmart \n\n"
				+ "Morgan: No Man's Land \n John Wick \n Master Chief \n Gandalf \n Gordon Freeman \n Barry B. Benson \n\n"
				+ "Justin: Normal Town \n Gordon Ramsay \n Caleb \n Tonio \n Derrek \n DarkScott \n\n"
				+ "Justin: Old Factory \n Sansosbrine \n\n"
				+ "Google Images: Heck \n Big Chungus \n Sloth \n Bling Bling Boy \n Stingy \n Morshu \n Squidward \n League Player \n\n"
				+ "Justin Again: The System \n\n"
				+ "Justin did all the programming.\nWhat a cool guy! \n\n"
				+ "Thanks for playing!");
		credits.setFont(thr2);
		credits.setLayoutY(570);
		
		Timeline creditRoll = new Timeline();
		for (int i = 0; i < 760; i++)
		{
			final int x = i;
			KeyFrame k = new KeyFrame(Duration.millis(i*180), ae -> {
				
				credits.setLayoutY(credits.getLayoutY() - 3);
				num.setText(x + "");
			});
			creditRoll.getKeyFrames().add(k);
		}
		
		button.setOnAction(ae -> {
			if (text.getText().equals(""))
			{
				picture.stop();
				System.exit(0);
			}
			else if (text.getText().equals("Because I made it."))
			{
				text.setText("");
				buttonBox.getChildren().remove(button);
				
				button.setText("Retire");
				
				
				picture.play();
				
				Main.m = new MediaPlayer(new Media(new String(Main.class.getResource("themes/pmdgtiEnd.mp3").toString())));
				Main.m.setVolume(.16*Main.volume);
				Main.m.play();
				
				end = new MediaPlayer(new Media(new String(Main.class.getResource("themes/timeOfReunion.mp3").toString())));
				end.setVolume(.32*Main.volume);
				end.setCycleCount(MediaPlayer.INDEFINITE);
				
				creditRoll.setOnFinished(aae -> {
					
					buttonBox.getChildren().add(button);
					end.play();
					end.seek(Duration.millis(7500));
				});
				creditRoll.play();
			}
			else
			{
				text.setText("Because I made it.");
			}
		});
		
		Pane all = new Pane(theEnd, credits, bottom);
		Scene s = new Scene(all);
		return s;
	}
}
