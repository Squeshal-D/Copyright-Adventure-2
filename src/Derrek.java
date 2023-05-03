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

public class Derrek extends Character{
	
	ImageView portrait = new ImageView(new Image ("portraits/derrekPortrait.jpg"));
	ImageView stage = new ImageView(new Image("stages/derrekStage.jpg"));
	String name = "Derrek";
	Media theme = new Media(new String(Main.class.getResource("themes/666KillChopDeluxe.mp3").toString()));
	double volume = 1;
	int hp = 60;
	int maxhp = 60;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Baseball Smash";
	String move2 = "Airplane Smash";
	String move3 = "Rage";
	String move1desc = "Derrek's superhero alias.\nDoes 15 damage and finishes an enemy at 20 hp or below.";
	String move2desc = "It's a simple maneuver.\nDoes 25 damage, but Derrek might crash the plane (20%).";
	String move3desc = "Derrek m a d.\nGives Derrek +1 rage (+50% dmg on next attack).\nDerrek only loses rage after a fight.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(600);
		b.setLayoutY(200);
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
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> {
		if (enemy.getHP() <= 20)
		{
			log.setText(user.getName() + " smashes " + enemy.getName() + "'s head clean off!");
			enemy.changeHP(-enemy.getHP());
		}
		else
		{
			int dmg = user.modifydmg(15);
			log.setText(user.getName() + " bonks " + enemy.getName() + " with a bat for " + dmg + " damage.");
			enemy.changeHP(-dmg);
		}
		});
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> {
		int dmg = modifydmg(25);
		log.setText(user.getName() + " clips " + enemy.getName() + " with a freaking airplane for " + dmg + " damage!");
		enemy.changeHP(-dmg);
		});
		KeyFrame second = new KeyFrame(Duration.seconds(2), aae -> {
		int rng = (int) (Math.random()*5);
		if (rng == 0)
		{
			log.setText(user.getName() + " crashes the plane in a blaze of glory!");
			hp = 0;
		}
		else log.setText(user.getName() + " lands the plane successfully.");
		});
        atk.getKeyFrames().addAll(first, second);
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> {
		log.setText(user.getName() + " is very angry right now!");
		status.add("rage");
		});
		KeyFrame second = new KeyFrame(Duration.millis(1));
		if (!teammate && user.getMoveName(1).equals("Baseball Smash"))
		{
			misc++;
		}
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (player.getHP() <= 20)
		{
			i = 1;
		}
		else if (misc > 0)
		{
			if (hp < 20 || misc == 2)
			{
				i = 2;
			}
			else
			{
				i = (int) (Math.random()*2 + 2);
			}
		}
		if (i < 3)
		{
			misc = 0;
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
		return "Airplanes. Baseball. Ava.";
	}
}
