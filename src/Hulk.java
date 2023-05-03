import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Hulk extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/hulkPortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/hulkStage.jpg"));
	String name = "HULK";
	Media theme = new Media(new String(Main.class.getResource("themes/sanctuaryGuardian.mp3").toString()));
	double volume = 1;
	int hp = 100;
	int maxhp = 100;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Nose Pick";
	String move2 = "Smash";
	String move3 = "Pants Rip";
	String move1desc = "Boogers don't grow on trees, you know.\nHULK either eats or flings it.\nHeals 10 - 25 hp or does 15 - 20 damage.";
	String move2desc = "For when enemies resemble the like button.\nBase 10 damage. +5 each use.\nHULK will use this move until an enemy is defeated.";
	String move3desc = "He's not wearing underwear.\n20% chance of making the enemy's eyes melt and die painfully.\nOnly works if HULK has more hp than the "
			+ "enemy.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(1160);
		b.setLayoutY(70);
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
		Timeline atk = new Timeline();
		int r = (int) (Math.random()*2);
		if (r == 0)
		{
			int hp = (int) (Math.random()*16 + 10);
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " picked their nose and ate a booger! They recover " + hp + " hp."));
			atk.getKeyFrames().add(first);
			user.changeHP(hp);
		}
		else
		{
			int dmg = user.modifydmg((int) (Math.random()*6 + 15));
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " picked their nose and flung a booger! " + enemy.getName() +
					" takes " + dmg + " damage!"));
			atk.getKeyFrames().add(first);
			enemy.changeHP(-dmg);
		}
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		if (!charging)
		{
			misc = 0;
		}
		int dmg = modifydmg(10 + 5*misc);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " goes crazy! " + enemy.getName() + " takes " + dmg + " damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		misc += 1;
		charging = true;
		if (dmg >= enemy.getHP())
		{
			charging = false;
			misc = 0;
		}
		enemy.changeHP(-dmg);
		return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		if (enemy.getHP() < user.getHP())
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " shredded their pants!"));
			atk.getKeyFrames().add(first);
			int rng = (int) (Math.random()*5);
			if (rng == 0)
			{
				KeyFrame second = new KeyFrame(Duration.seconds(1), ae -> log.setText(enemy.getName() + "'s eyes melted and they died painfully."));
				atk.getKeyFrames().add(second);
				enemy.changeHP(-enemy.getHP());
			}
			else
			{
				KeyFrame second = new KeyFrame(Duration.seconds(1), ae -> log.setText(enemy.getName() + " threw up in their mouth a little."));
				atk.getKeyFrames().add(second);
			}
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(enemy.getName() + " isn't scared of any green, glorious nudity for now."));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			atk.getKeyFrames().addAll(first, second);
		}
        return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (charging || player.getHP() <= 10)
		{
			i = 2;
		}
		else if (player.getHP() >= enemy.getHP())
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
		return "AAAAAAAAAAAAAAAAAAAA";
	}
}
