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

public class Chief extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/chiefPortrait.png"));
	ImageView stage = new ImageView(new Image ("stages/chiefStage.png"));
	String name = "Master Chief";
	Media theme = new Media(new String(Main.class.getResource("themes/dreamyFoe.mp3").toString()));
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
	
	String move1 = "Assault Rifle";
	String move2 = "Spartan Laser";
	String move3 = "Armor Regen\n(30%)";
	String move1desc = "That's not super bad-A-word.\n32 shots, 1 damage each.\nEvery shot has a 1/2 chance of hitting.";
	String move2desc = "Woah, that's bad-A-word!\nDoes 40 damage.\nTakes a turn to charge.";
	String move3desc = "(Electric humming noises...)\nRegenerates a portion of lost hp.\nLess effective after each use.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(1030);
		b.setLayoutY(150);
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
		move3 = "Armor Regen\n(30%)";
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
		int dmg = 0;
		int shots = 0;
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " pulls out an assault rifle!"));
		atk.getKeyFrames().add(first);
		for (int i = 0; i<32; i++)
		{
			shots ++;
			int shoots = shots;
			int rng = (int)(Math.random()*2);
			if (rng == 1)
			{
				dmg +=1;
			}
			int damage = dmg;
			KeyFrame shot = new KeyFrame(Duration.millis(1500 + (40*i)), ae -> log.setText(user.getName() + " hits " + damage + "/" + shoots + " shots!"));
			atk.getKeyFrames().add(shot);
		}
		int finaldmg = user.modifydmg(dmg);
		enemy.changeHP(-finaldmg);
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		if (!charging)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " charges the spartan laser!"));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			charging = true;
			atk.getKeyFrames().addAll(first, second);
		}
		else
		{
			int dmg = modifydmg(40);
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " fires the spartan laser! " + enemy.getName() + " takes " + dmg
					+ " damage!"));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			enemy.changeHP(-dmg);
			charging = false;
			atk.getKeyFrames().addAll(first, second);
		}
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int regen = (int)((float)(user.getMaxHP() - user.getHP())*(0.3 - (misc*0.03)));
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " regenerated " + regen + " hp."));
		atk.getKeyFrames().add(first);
		user.changeHP(regen);
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
		misc ++;
		move3 = "Armor Regen\n(" + (30 - (3*misc)) + "%)";
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (charging)
		{
			i = 2;
		}
		else if (player.getHP() <= 16)
		{
			i = 1;
		}
		else if (hp <= 20)
		{
			int j = (int) (Math.random()*2 + 1);
			if (j == 1)
			{
				i = 1;
			}
			else i = 3;
		}
		else i = (int) (Math.random()*2 + 1);
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
		return "I need a weapon.";
	}
}
