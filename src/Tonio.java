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

public class Tonio extends Character{
	
	ImageView portrait = new ImageView(new Image ("portraits/tonio.jpg"));
	ImageView stage = new ImageView(new Image ("stages/tonioStage.jpg"));
	String name = "Tonio";
	Media theme = new Media(new String(Main.class.getResource("themes/fate.mp3").toString()));
	double volume = 1;
	int hp = 50;
	int maxhp = 50;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Water Drink";
	String move2 = "P90 Rush A";
	String move3 = "Carrot Stab";
	String move1desc = "A move you should do in real life, like Tonio.\nHeals 15 health and removes any statuses.";
	String move2desc = "It shouldn't work. It does, though.\n50 shots, 1 damage each.\n50% chance for each shot to hit.";
	String move3desc = "The unconventional way to get some vitamin A.\nDoes more damage as the enemy loses hp.\n0 - 40 damage.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(1070);
		b.setLayoutY(100);
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
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " drinks a nice glass of water and heals 15 hp."));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		user.changeHP(15);
		for (int i = user.getStatus().size() - 1; i >= 0; i--)
		{
			if (!user.getStatus().get(i).equals("greed"))
			{
				user.getStatus().remove(i);
			}
		}
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = 0;
		int shots = 0;
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " pulls out a P90!"));
		atk.getKeyFrames().add(first);
		for (int i = 0; i<50; i++)
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
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		float ratio = (float) enemy.getHP() / (float) enemy.getMaxHP();
		float f = 40 - (40*ratio);
		int dmg = modifydmg((int) f);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " stabs " + enemy.getName() + " with a carrot for " + dmg + " damage!"));
		enemy.changeHP(-dmg);
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i;
		float ratio = (float) player.getHP() / (float) player.getMaxHP();
		if (hp > 35)
		{
			if (ratio <= 0.5)
			{
				i = (int) (Math.random()*2 + 2);
			}
			else i = 2;
		}
		else
		{
			if (ratio <= 0.5)
			{
				i = (int) (Math.random()*3 + 1);
			}
			else i = (int) (Math.random()*2 + 1);
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
				float ratio = (float) Main.currentEnemy.getHP() / (float) Main.currentEnemy.getMaxHP();
				float f = 40 - (40*ratio);
				int dmg = (int) f;
				return move3 + " (" + dmg + ")";
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
	
	public void addMaxhp(int i)
	{
		maxhp += i;
		changeHP(i);
	}
	
	public String twitter()
	{
		return "Aghaghaghaghaghaghaghaga!.";
	}
}
