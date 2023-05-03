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

public class Washington extends Character{
	
	ImageView portrait = new ImageView(new Image ("portraits/washingtonPortrait.png"));
	ImageView stage = new ImageView(new Image ("stages/washingtonStage.png"));
	String name = "George Washington";
	Media theme = new Media(new String(Main.class.getResource("themes/bossBattle2.mp3").toString()));
	double volume = 0.5;
	int hp = 50;
	int maxhp = 50;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Cherry Chop";
	String move2 = "Wood Tooth Chomp";
	String move3 = "Musket Blast";
	String move1desc = "\"People are like trees.\" - George Washington\nDamage increases based on opponent's max hp.\nMax 30 damage at 100+ hp. "
			+ "Min 0 damage at 50- hp.";
	String move2desc = "Some things are better left unbitten.\nDoes 1 - 20 damage.";
	String move3desc = "Young LaFlame, he in minuteman mode.\nDoes 40 damage.\nNeeds to reload (R) before it is used again.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(760);
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
	}
	
	public void refresh()
	{
		hp = maxhp;
		status.clear();
		teammate = true;
		charging = false;
		misc = 0;
		move3 = "Musket Blast";
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
		int calc = (int) ((enemy.getMaxHP() - 50)*.6);
		if (calc > 30)
		{
			calc = 30;
		}
		int dmg = modifydmg(calc);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " hits " + enemy.getName() + " with an axe for " + dmg + " damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		enemy.changeHP(-dmg);
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int rand = (int) (Math.random()*20 + 1);
		int dmg = user.modifydmg(rand);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " bites " + enemy.getName() + " for " + dmg + " damage!"));
		enemy.changeHP(-dmg);
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = modifydmg(40);
		if (misc == 0)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " shoots " + enemy.getName() + " for " + dmg + " damage!"));
			atk.getKeyFrames().add(first);
			enemy.changeHP(-dmg);
			misc = 1;
			move3 = "Musket Blast (R)";
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " reloads their musket."));
			atk.getKeyFrames().add(first);
			misc = 0;
			move3 = "Musket Blast";
		}
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
        return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int calc = (int) ((player.getMaxHP() - 50)*.6);
		if (calc > 30)
		{
			calc = 30;
		}
		
		int i = (int) (Math.random()*3 + 1);
		if (player.getHP() <= calc)
		{
			i = 1;
		}
		else if (player.getHP() <= 40 && misc == 0)
		{
			i = 3;
		}
		else if (misc == 1)
		{
			if (hp <= 20)
			{
				if (calc < 10)
				{
					i = 2;
				}
				else
				{
					i = (int) (Math.random()*2 + 1);
				}
			}
			else
			{
				if (calc < 10)
				{
					i = (int) (Math.random()*2 + 2);
				}
				else i = (int) (Math.random()*3 + 1);
			}
		}
		else if (calc < 10)
		{
			i = (int) (Math.random()*2 + 2);
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
			if (Main.currentEnemy == null)
			{
				return move1;
			}
			else
			{
				int calc = (int) ((Main.currentEnemy.getMaxHP() - 50)*.6);
				if (calc > 30)
				{
					calc = 30;
				}
				return move1 + " (" + calc + ")";
			}
		}
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
		return "Back in my day *rants*";
	}
}
