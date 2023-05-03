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

public class Spongebob extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/spongebobPortrait.png"));
	ImageView stage = new ImageView(new Image ("stages/spongebobStage.png"));
	String name = "Spongebob";
	Media theme = new Media(new String(Main.class.getResource("themes/emeraldWild.mp3").toString()));
	double volume = .75;
	int hp = 50;
	int maxhp = 50;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Bubble Blow (30)";
	String move2 = "Jellyfish Net";
	String move3 = "Rebound (0)";
	String move1desc = "Play AS a bubble blowing double baby.\nStarts with 30 damage lose 5 damage per use.\nRefill when empty.";
	String move2desc = "Ol' Reliable.\nDoes more damage to enemies with low max hp.\n0 dmg at 100 max hp, 30 dmg at 50 max hp.";
	String move3desc = "Return the butt kicking.\nInflicts the most recent damage taken.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(670);
		b.setLayoutY(300);
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
		if (Main.currentEnemy != null && i < 0)
		{
			misc -= i;
			move3 = "Rebound (" + -i + ")";
		}
	}
	
	public void refresh()
	{
		hp = maxhp;
		status.clear();
		teammate = true;
		charging = false;
		move1 = "Bubble Blow (30)";
		move3 = "Rebound (0)";
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
		int dmg = modifydmg(30 - 5*(int)(misc/100));
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " blows bubbles at " + enemy.getName() + " for " + dmg + " damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		enemy.changeHP(-dmg);
		if (misc < 600)
		{
			misc += 100;
		}
		else
		{
			first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " refills their bubbles."));
			misc -= 600;
		}
		misc -= misc%100;
		move3 = "Rebound (0)";
		move1 = "Bubble Blow (" + (30-(5*(misc/100))) + ")";
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int calc = (int) ((100 - enemy.getMaxHP())*.6);
		if (calc < 0)
		{
			calc = 0;
		}
		int dmg = user.modifydmg(calc);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " catches " + enemy.getName() + " in a jellyfishing net! They take " +
				dmg + " damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		enemy.changeHP(-dmg);
		
		if (user.getName().equals("Spongebob"))
		{
			misc -= misc%100;
			move3 = "Rebound (0)";
		}
		
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = modifydmg(misc%100);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " rebounds an attack on " + enemy.getName() + " for " + dmg + 
				" damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		enemy.changeHP(-dmg);
		misc -= misc%100;
		move3 = "Rebound (0)";
        return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		int move1calc = 30-(5*(int)(misc/100));
		int move2calc = (int) ((100 - player.getMaxHP())*.6);
		int move3calc = misc%100;
		if (move3calc < 20 && move2calc < 20)
		{
			i = 1;
		}
		else if (move3calc >= player.getHP())
		{
			i = 3;
		}
		else if (move2calc >= player.getHP())
		{
			i = 2;
		}
		else if (move1calc >= player.getHP())
		{
			i = 1;
		}
		else if (move3calc >= move2calc && move3calc >= move1calc)
		{
			i = 3;
		}
		else if (move2calc >= move1calc)
		{
			i = 2;
		}
		else i = 1;
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
			if (Main.currentEnemy == null)
			{
				return move2;
			}
			else
			{
				int calc = (int) ((100 - Main.currentEnemy.getMaxHP())*.6);
				if (calc < 0)
				{
					calc = 0;
				}
				return move2 + " (" + calc + ")";
			}
		}
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
		return "I'm ready.";
	}
}
