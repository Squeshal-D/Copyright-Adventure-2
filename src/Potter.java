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

public class Potter extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/potterPortrait.png"));
	ImageView stage = new ImageView(new Image ("stages/potterStage.png"));
	String name = "Harry Potter";
	Media theme = new Media(new String(Main.class.getResource("themes/decisiveBattle.mp3").toString()));
	double volume = 1;
	int hp = 50;
	int maxhp = 50;
	int misc = 50;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Expecto Patronum";
	String move2 = "Expelliarmus";
	String move3 = "MTN";
	String move1desc = "AAAAAAAAAAAAAAAAAAAAA!!!!!!\nDoes more damage as your hp gets low.\n0 damage at 50 hp, 40 damage at 0 hp.";
	String move2desc = "Avada Kadavra would've been too easy.\nInterrupts an ongoing move.\nDoes 10 damage.";
	String move3desc = "What could it be?";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(70);
		b.setLayoutY(340);
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
		misc = 50;
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
		int dmg = user.modifydmg((int)(.8*(user.getMaxHP()-user.getHP())));
		if ((int)(.8*(user.getMaxHP()-user.getHP())) < 20)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " said, \"Expecto Patronum.\" " + enemy.getName() + " took " + dmg
					+ " damage."));
			atk.getKeyFrames().add(first);
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " said, \"EXPECTO. PATRONUUUUUUUM!!!\" " + enemy.getName() + " took " 
					+ dmg + " damage!"));
			atk.getKeyFrames().add(first);
		}
		KeyFrame second = new KeyFrame(Duration.millis(1));
		enemy.changeHP(-dmg);
		atk.getKeyFrames().add(second);
		if (user.getName().equals("Harry Potter"))
		{
			misc = hp;
		}
		
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = modifydmg(10);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " said, \"Expelliarmus.\" " + enemy.getName() + " takes " + dmg +
				" damage!"));
		enemy.changeHP(-dmg);
		enemy.unCharge();
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		misc = hp;
		return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int move = (int) (Math.random()*4);
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText("What's that mysterious ticking noise?"));
		atk.getKeyFrames().add(first);
		if (move == 0)
		{
			KeyFrame second = new KeyFrame(Duration.seconds(1), ae -> log.setText("It's a clock! " + user.getName() + " goes back in time."));
			atk.getKeyFrames().add(second);
			hp = misc;
		}
		else if (move == 1)
		{
			KeyFrame second = new KeyFrame(Duration.seconds(1), ae -> log.setText("It's a revved up minigun!"));
			atk.getKeyFrames().add(second);
			int dmg = 0;
			int shots = 0;
			for (int i = 0; i<30; i++)
			{
				shots ++;
				int shoots = shots;
				int rng = (int)(Math.random()*3);
				if (rng == 0)
				{
					dmg +=1;
				}
				int damage = dmg;
				KeyFrame shot = new KeyFrame(Duration.millis(2000 + (40*i)), ae -> log.setText(user.getName() + " hits " + damage + "/" + shoots + " shots!"));
				atk.getKeyFrames().add(shot);
			}
			int finaldmg = modifydmg(dmg);
			enemy.changeHP(-finaldmg);
			misc = hp;
		}
		else if (move == 2)
		{
			KeyFrame second = new KeyFrame(Duration.seconds(1), ae -> log.setText("It's a metronome! " + user.getName() + " is calibrated. " + enemy.getName() +
					" is confused."));
			atk.getKeyFrames().add(second);
			user.addStat("rage");
			enemy.addStat("weak");
			enemy.addStat("weak");
			misc = hp;
		}
		else
		{
			KeyFrame second = new KeyFrame(Duration.seconds(1), ae -> log.setText("It's a pipe bomb!"));
			atk.getKeyFrames().add(second);
			int dmg = modifydmg(40);
			enemy.changeHP(-dmg);
			user.changeHP(-10);
			misc = hp;
		}
        return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (player.isCharging())
		{
			i = 2;
		}
		else if (enemy.getHP() <= 25)
		{
			int r = (int) (Math.random()*2);
			if (r == 0)
			{
				i = 1;
			}
			else r = 3;
		}
		else
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
				int dmg = (int)(.8*(maxhp - hp));
				return move1 + " (" + dmg + ")";
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
		return "I'm a what!?!";
	}
}
