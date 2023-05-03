
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

public class Blart extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/blartPortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/blartStage.jpg"));
	String name = "Paul Blart";
	Media theme = new Media(new String(Main.class.getResource("themes/smashingSongOfPraise.mp3").toString()));
	double volume = 1;
	int hp = 80;
	int maxhp = 80;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Headbutt";
	String move2 = "Segway Slam";
	String move3 = "Faint";
	String move1desc = "\"Nobody wins with a headbutt\"\nDeals 30 damage.\nRecoils 10 damage.";
	String move2desc = "Dean Kamen's accidental super weapon.\n50% chance to hit (40).\n50% chance to recoil (20).";
	String move3desc = "To fall asleep instantly... It's what everyone wants...\nFall asleep for a turn.\nHeal 30 hp and clear statuses when you wake up.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(790);
		b.setLayoutY(260);
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
		int dmg = user.modifydmg(30);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " headbutts " + enemy.getName() + " for " + dmg + " damage and "
				+ "recieves 10 recoil damage."));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		user.changeHP(-10);
		enemy.changeHP(-dmg);
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = modifydmg(40);
		int hit = (int) (Math.random()*2);
		int recoil = (int) (Math.random()*2);
		if (hit == 0)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " slams into " + enemy.getName() + " on their segway "
					+ "for " + dmg + " damage..."));
			atk.getKeyFrames().add(first);
			enemy.changeHP(-dmg);
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " zooms passed " + enemy.getName() + " on their segway..."));
			atk.getKeyFrames().add(first);
		}
		if (recoil == 0)
		{
			KeyFrame second = new KeyFrame(Duration.seconds(2), ae -> log.setText("and falls off, taking 20 damage!"));
			atk.getKeyFrames().add(second);
			user.changeHP(-20);
		}
		else
		{
			KeyFrame second = new KeyFrame(Duration.seconds(2), ae -> log.setText("and stays on!"));
			atk.getKeyFrames().add(second);
		}
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		if (!charging)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " passed out!"));
			atk.getKeyFrames().add(first);
			charging = true;
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " woke up feeling refreshed. They gained 30 hp."));
			atk.getKeyFrames().add(first);
			user.changeHP(30);
			for (int i = user.getStatus().size() - 1; i >= 0; i--)
			{
				if (!user.getStatus().get(i).equals("greed"))
				{
					user.getStatus().remove(i);
				}
			}
			charging = false;
		}
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*2 + 1);
		if (charging)
		{
			i = 3;
		}
		else if (player.getHP() <= 30)
		{
			i = 1;
		}
		else if (hp >= 30 && hp <= 50)
		{
			i = (int) (Math.random()*3 + 1);
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
		return "Scooba-dooby-doo.";
	}
}
