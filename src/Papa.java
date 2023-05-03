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

public class Papa extends Character{
	
	ImageView portrait = new ImageView(new Image ("portraits/papaPortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/papaStage.jpg"));
	String name = "Papa John";
	Media theme = new Media(new String(Main.class.getResource("themes/pizza.mp3").toString()));
	double volume = 1;
	int hp = 60;
	int maxhp = 60;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = true;
	boolean testing = true;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Spicy Pepperoni";
	String move2 = "Pizza Rain";
	String move3 = "Pizza Delivery";
	String move1desc = "";
	String move2desc = "";
	String move3desc = "";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		//b.setLayoutX(1070);
		//b.setLayoutY(100);
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
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " launches a spicy pepperoni into " + enemy.getName() + 
				"'s oral cavity! (+50hp +burning)"));
		enemy.changeHP(50);
		enemy.addStat("burn");
		enemy.addStat("burn");
		enemy.addStat("burn");
		user.changeHP(-20);
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " makes pizza rain from the heavens! (+8 hp to party)"));
		for (int i = 0; i < Main.party.size(); i++)
		{
			Main.party.get(i).changeHP(8);
		}
		user.changeHP(-20);
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " delivers " + enemy.getName() + "'s pizza late. (+25 hp +rage)"));
		enemy.changeHP(25);
		enemy.addStat("rage");
		user.changeHP(-20);
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (player.getMaxHP() - player.getHP() >= 40)
		{
			i = 1;
		}
		else if (player.getMaxHP() - player.getHP() >= 20)
		{
			i = (int)(Math.random()*2 + 2);
		}
		else
		{
			i = 2;
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
}