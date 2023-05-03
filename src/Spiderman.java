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

public class Spiderman extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/spidermanPortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/spidermanStage.jpg"));
	String name = "Spiderman";
	Media theme = new Media(new String(Main.class.getResource("themes/mrBattyTwist.mp3").toString()));
	double volume = 1;
	int hp = 70;
	int maxhp = 70;
	int misc = 0;
	boolean explode = false;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Toby Maguire (0)";
	String move2 = "Andrew Garfield";
	String move3 = "Tom Holland";
	String move1desc = "It's that time.\nPower increases with each pizza slice eaten.\n1-10 damage per slice.";
	String move2desc = "This be the end of a man's career.\nDoes 50 - 75 damage.\nSpiderman will instantly die.";
	String move3desc = "Refreshing at first, like Pixar.\nDoes 30 damage to full hp enemies.\nDoes 10 damage otherwise.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(1080);
		b.setLayoutY(400);
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
		
		if (teammate && hp == 0 && move2 == "Andrew Garfield\n(Auto)" && Main.currentEnemy != null && !explode)
		{
			Main.currentEnemy.changeHP(-modifydmg((int)(Math.random()*26) + 50));
		}
		else if (!teammate && hp == 0 && move2 == "Andrew Garfield\n(Auto)" && !explode)
		{
			Main.currentFighter.changeHP(-modifydmg((int)(Math.random()*26) + 50));
		}
	}
	
	public void refresh()
	{
		hp = maxhp;
		status.clear();
		teammate = true;
		charging = false;
		misc = 0;
		explode = false;
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
		if (teammate)
		{
			if (misc == 0)
			{
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " laments, for he has no pizza."));
				atk.getKeyFrames().add(first);
			}
			else
			{
				int x = 0;
				for (int i = 0; i < misc; i++)
				{
					x += (int)(Math.random()*10 + 1);
				}
				int dmg = modifydmg(x);
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " deploys pizza time upon " + enemy.getName() + " for "
						+ dmg + " damage!"));
				enemy.changeHP(-dmg);
				atk.getKeyFrames().add(first);
			}
		}
		else
		{
			int x = 0;
			for (int i = 0; i < (int)(Math.random()*3 + 1); i++)
			{
				x += (int)(Math.random()*10 + 1);
			}
			int dmg = user.modifydmg(x);
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " deploys pizza time upon " + enemy.getName() + " for "
					+ dmg + " damage!"));
			enemy.changeHP(-dmg);
			atk.getKeyFrames().add(first);
		}
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		explode = true;
		Timeline atk = new Timeline();
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " ends his career with a bang."));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		int dmg = modifydmg((int)(Math.random()*26) + 50);
		enemy.changeHP(-dmg);
		user.changeHP(-user.getHP());
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		if (enemy.getHP() == enemy.getMaxHP())
		{
			int dmg = modifydmg(30);
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " put on quite a show! " + enemy.getName() + " takes " + dmg + 
					" damage!"));
			atk.getKeyFrames().add(first);
			enemy.changeHP(-dmg);
		}
		else
		{
			int dmg = modifydmg(10);
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " was perfectly mediocre. " + enemy.getName() + " takes " + dmg + 
					" damage."));
			atk.getKeyFrames().add(first);
			enemy.changeHP(-dmg);
		}
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
        return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (player.getHP() <= 10)
		{
			i = 3;
		}
		else if (hp <= 15)
		{
			i = 2;
		}
		else if (player.getHP() == player.getMaxHP())
		{
			i = 3;
		}
		else
		{
			i = 1;
			if ((int)(Math.random()*3) == 0)
			{
				i = 3;
			}
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
		if (stat.equals("Venom"))
		{
			move2 = "Andrew Garfield\n(Auto)";
			move2desc = "This be the end of a man's career.\nDoes 50 - 75 damage.\nSpiderman will instantly die.\nNow automatically triggers upon death.";
		}
		
		else if (stat.equals("rage") || stat.equals("weak") || stat.equals("BLD") || (stat.equals("burn") && brn < 3) || stat.equals("heal") || 
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
	
	public void addMisc(int i)
	{
		misc += i;
		move1 = "Toby Maguire (" + misc + ")";
	}
	
	public String twitter()
	{
		return "You can call me Spiderman.";
	}
}
