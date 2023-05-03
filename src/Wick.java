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

public class Wick extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/wickPortrait.png"));
	ImageView stage = new ImageView(new Image ("stages/wickStage.jpg"));
	String name = "John Wick";
	Media theme = new Media(new String(Main.class.getResource("themes/rudeBuster.mp3").toString()));
	double volume = 1;
	int hp = 70;
	int maxhp = 70;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Pencil Shank";
	String move2 = "Neck Snap";
	String move3 = "Precise Slice";
	String move1desc = "A school classic.\nContinuous attack, 5 damage per hit.\nStops on miss (25%).";
	String move2desc = "The body's natural off switch.\nMight kill the opponent.\nChances are better on lower hp targets.\nWon't work on characters above "
			+ "100 hp.";
	String move3desc = "Cut something that shouldn't be cut!\nInflicts bleed status.\nBase 3 damage, +1 more damage every turn of bleed.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(1140);
		b.setLayoutY(680);
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
		int dmg = 0;
		int shots = 0;
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " brandishes a pencil!"));
		atk.getKeyFrames().add(first);
		while (dmg < enemy.getHP())
		{
			shots++;
			int i = (int) (Math.random()*4);
			if (i != 3)
			{
				int hits = shots;
				KeyFrame hit = new KeyFrame(Duration.millis(750 + 250*shots), ae -> log.setText(user.getName() + " strikes " + hits + "/" + hits + " times!"));
				atk.getKeyFrames().add(hit);
				dmg = user.modifydmg(5*hits);
			}
			else
			{
				int hits = shots;
				KeyFrame miss = new KeyFrame(Duration.millis(750 + 250*shots), ae -> log.setText(user.getName() + " strikes " + (hits-1) + "/" + hits + 
						" times!"));
				atk.getKeyFrames().add(miss);
				break;
			}
		}
		enemy.changeHP(-dmg);
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		float chance = (float) (10.0/(float)enemy.getHP());
		float roll = (float) (Math.random());
		if (enemy.getHP() > 100)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(enemy.getName() + " is currently too strong to have their neck broken!"));
			atk.getKeyFrames().add(first);
		}
		else if (roll < chance)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " breaks " + enemy.getName() + "'s neck!"));
			atk.getKeyFrames().add(first);
			enemy.changeHP(-enemy.getHP());
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " failed to break " + enemy.getName() + "'s neck."));
			atk.getKeyFrames().add(first);
		}
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
		return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		if (!enemy.hasStat("BLD"))
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " cuts something vital! " + enemy.getName() + " bleeds!"));
			atk.getKeyFrames().add(first);
			enemy.addStat("BLD");
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(enemy.getName() + " is already bleeding!"));
			atk.getKeyFrames().add(first);
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
			i = 2;
		}
		else if (player.getHP() <= 30)
		{
			if (player.hasStat("BLD"))
			{
				i = (int) (Math.random()*2 + 1);
			}
			else i = (int) (Math.random()*3 + 1);
		}
		else
		{
			if (player.hasStat("BLD"))
			{
				i = 1;
			}
			else
			{
				int j = (int) (Math.random()*2);
				if (j == 0)
				{
					i = 1;
				}
				else i = 3;
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
			if (Main.currentEnemy == null)
			{
				return move2;
			}
			else
			{
				float chance = (float) (10.0/(float)Main.currentEnemy.getHP());
				int percent = (int) (chance*100.0);
				if (percent > 100)
				{
					percent = 100;
				}
				if (Main.currentEnemy.getHP() > 100)
				{
					percent = 0;
				}
				return move2 + " (" + percent + "%)";
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
		return "I know Kung Fu.";
	}
}
