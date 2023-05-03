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

public class Shrek extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/shrekPortrait.png"));
	ImageView stage = new ImageView(new Image ("stages/shrekStage.png"));
	String name = "Shrek";
	Media theme = new Media(new String(Main.class.getResource("themes/bossBattle1.mp3").toString()));
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
	
	String move1 = "Shrek Superslam";
	String move2 = "Onion Onslaught";
	String move3 = "Donkey Delusions";
	String move1desc = "True Wombo Combo.\nBase 2 damage. +2 damage for every other party member.";
	String move2desc = "Onions are like Shrek.\nThrows 5 onions, 6 damage each.\nEach has a 1/2 chance of hitting.";
	String move3desc = "Donkey is himself in sub-5 minute intervals.\nSacrifice 20 - 25 hp for 2 rage statuses.\nRage makes your next attack do 50% more "
			+ "damage.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(1060);
		b.setLayoutY(360);
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
		int members = 0;
		if (teammate)
		{
			members = Main.party.size();
		}
		else
		{
			members = (int) (Math.random()*14 + 2);
		}
		int allies = members-1;
		int dmg = modifydmg(members*2);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " calls upon " + allies + " allies and combos " 
				+ enemy.getName() + " for " + dmg + " damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		enemy.changeHP(-dmg);
		atk.getKeyFrames().addAll(first, second);
		misc = 0;
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int shots = 0;
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " gets the onions!"));
		atk.getKeyFrames().add(first);
		for (int i = 0; i < 5; i++)
		{
			int g = i;
			int r = (int) (Math.random()*2);
			if (r == 0)
			{
				shots++;
				int f = shots;
				KeyFrame hit = new KeyFrame(Duration.millis(1000 + 250*i), ae -> log.setText(user.getName() + " hits " + f + "/" + (g+1) + " onions!"));
				atk.getKeyFrames().add(hit);
			}
			else
			{
				int f = shots;
				KeyFrame miss = new KeyFrame(Duration.millis(1000 + 250*i), ae -> log.setText(user.getName() + " hits " + f + "/" + (g+1) + " onions!"));
				atk.getKeyFrames().add(miss);
			}
		}
		int dmg = user.modifydmg(6*shots);
		enemy.changeHP(-dmg);
		misc = 0;
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = (int) (Math.random()*6 + 20);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText("Donkey makes " + user.getName() + " very angry! they take " + dmg + " damage!"));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		user.changeHP(-dmg);
		user.addStat("rage");
		user.addStat("rage");
		misc += 1;
        return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (hp >= 40 && misc < 2 && player.getHP() > 18)
		{
			i = (int) (Math.random()*3 + 1);
		}
		else
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
			return move1 + "\n(" + 2*Main.party.size() + ")";}
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
		return "Fiona dumped me.";
	}
}
