
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

public class Caleb extends Character{
	
	ImageView portrait = new ImageView(new Image ("portraits/calebPortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/calebStage.jpg"));
	String name = "Caleb";
	Media theme = new Media(new String(Main.class.getResource("themes/youBlowMyMind.mp3").toString()));
	double volume = 1;
	int hp = 50;
	int maxhp = 50;
	int misc = 0;
	boolean league = false;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Flake";
	String move2 = "Falcon Punch";
	String move3 = "Kiss";
	String move1desc = "He does it on people's hindquarters, G.\nSwap characters without losing a turn.";
	String move2desc = "No, it's not hitting birds.\nDoes 50 damage, but takes a turn to charge."
			+ "\nThe charge is interrupted if you take 20+ damage in a single attack.";
	String move3desc = "It's not consensual.\nHeals 10 hp and gives 10 damage\nGive any statuses Caleb has to the enemy.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(150);
		b.setLayoutY(510);
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
		if (Main.currentEnemy != null && Main.currentEnemy.getMoveDesc(2).equals("done"))
		{
			league = true;
			move2desc = "No, it's not hitting birds.\nDoes 60 damage, but takes a turn to charge."
					+ "\nThe charge is no longer interrupted upon taking damage.";
		}
		
		hp += i;
		if (hp < 0)
		{
			hp = 0;
		}
		else if (hp > maxhp)
		{
			hp = maxhp;
		}
		if (i <= -20 && !league)
		{
			charging = false;
		}
	}
	
	public void refresh()
	{
		hp = maxhp;
		status.clear();
		misc = 0;
		charging = false;
		teammate = true;
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
		Character e = enemy;
		if (teammate)
		{
			Main.flake = user;
			/*KeyFrame first = new KeyFrame(Duration.ZERO, ae -> Main.chooseFighter(null, e, Main.party, ps));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			atk.getKeyFrames().addAll(first, second);*/
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " caught " + e.getName() + " flakin."));
			
			enemy.unCharge();
			Character replacement = Main.party.get((int)(Math.random()*Main.party.size()));
			while(replacement == enemy)
			{
				replacement = Main.party.get((int)(Math.random()*Main.party.size()));
			}
			Character r = replacement;
			Main.flake = replacement;
			
			KeyFrame second = new KeyFrame(Duration.seconds(2), aee -> log.setText(r.getName() + " was sent out as a replacement."));
			if (user.getMoveName(1).equals("Flake"))
			{
				misc = 1;
			}
			atk.getKeyFrames().addAll(first, second);
		}
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		if (!charging)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " charges a falcon punch!"));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			charging = true;
			misc = 0;
			atk.getKeyFrames().addAll(first, second);
		}
		else
		{
			int dmg = modifydmg(50);
			if (league)
			{
				dmg = modifydmg(60);
			}
			final int finald = dmg;
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> 
			log.setText(user.getName() + " falcon puches " + enemy.getName() + "!!! They take " + finald + " damage!"));
			KeyFrame second = new KeyFrame(Duration.millis(1));
			enemy.changeHP(-dmg);
			charging = false;
			misc = 0;
			atk.getKeyFrames().addAll(first, second);
		}
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg = user.modifydmg(10);
		int orig = enemy.getHP();
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " violently kisses " + enemy.getName() 
		+ " and steals " + dmg + " health."));
		KeyFrame second = new KeyFrame(Duration.millis(1));
		enemy.changeHP(-dmg);
		int current = enemy.getHP();
		user.changeHP(orig - current);
		for (int i = 0; i < user.getStatus().size(); i++)
		{
			enemy.addStat(user.getStatus().get(i));
		}
		misc = 0;
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i;
		if (charging)
		{
			i = 2;
		}
		else if (Main.party.size() == 1 || misc > 0)
		{
			i = (int) (Math.random()*2 + 2);
		}
		else if (player.getHP() <= 10)
		{
			i = 3;
		}
		else if (hp < 20)
		{
			if (misc > 0)
			{
				i = 3;
			}
			else
			{
				int x = (int)(Math.random()*2);
				if (x == 0)
				{
					i = 1;
				}
				else
				{
					i = 3;
				}
			}
		}
		else i = (int) (Math.random()*3 + 1);
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
		return "OMG Rawr XD!!!";
	}
}
