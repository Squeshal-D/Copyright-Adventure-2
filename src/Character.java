import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Character {

	ImageView portrait = new ImageView(new Image ("portraits/playerPortrait.jpg"));
	ImageView stage;
	String name;
	Media theme;
	double volume = 1;
	int hp = 50;
	int maxhp = 50;
	int misc = 3;
	boolean song = false;
	boolean teammate = true;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Punch";
	String move2 = "Neck";
	String move3;
	String move1desc = "Very normal, like you.\nDoes 15 to 25 damage.";
	String move2desc = "Connect your pointer finger and thumb under the waist.\nDoes 30 damage - if the opponent sees it (50%)!";
	String move3desc = "It's what you do best!\nDoes 40 damage, but has limited uses!";
	
	Button button = new Button();
	
	public void create(String n, String s)
	{
		name = n;
		move3 = s + " (" + misc + ")";
	}
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(200);
		b.setLayoutY(500);
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
		misc = 3;
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
		int dmg = user.modifydmg(15 + (int)(Math.random()*11));
        enemy.changeHP(-dmg);
        log.setText(user.getName() + " punches " + enemy.getName() + " for " + dmg + " damage.");
		});
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " makes a loop under their waist..."));
		KeyFrame second = new KeyFrame(Duration.seconds(2), ae -> {
		int rng = (int)(Math.random()*2);
        if (rng == 1)
        {
        	int dmg = modifydmg(30);
        	log.setText("That's a neck! " + enemy.getName() + " takes " + dmg + " damage!");
            enemy.changeHP(-dmg);
        }
        else log.setText(enemy.getName() + " didn't see it.");
		});
        atk.getKeyFrames().addAll(first, second);
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		KeyFrame first = new KeyFrame(Duration.ZERO, ae -> {
		if (user.getStage() != null)
		{
			log.setText("Only " + enemy.getName() + " truly knows how to use " + move3 + ".");
		}
		else if (misc > 0)
		{
			int dmg = modifydmg(40);
			String moveName = move3.substring(0, move3.length() - 4);
			log.setText(user.getName() + " uses the power of " + moveName + "! " + enemy.getName() + " takes " + dmg + " damage!");
			enemy.changeHP(-dmg);
			misc -= 1;
			move3 = move3.substring(0, move3.length() - 2) + misc + ")";
		}
		else log.setText(user.getName() + " couldn't gather enough energy to use " + move3 + ".");
		});
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().addAll(first, second);
		return atk;
	}
	
	public Timeline enemyAttack(Character user, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		return whichAtk(i, enemy, user, log, ps);
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
	
	public Scene bossCutscene(Stage ps)
	{
		return null;
	}
	
	public void addMisc(int i)
	{
		misc += i;
		move3 = move3.substring(0, move3.length() - 2) + misc + ")";
	}
	
	public void addMaxhp(int i)
	{
		maxhp += i;
		changeHP(i);
	}
	
	public String twitter()
	{
		return "I like to " + move3 + ".";
	}
}
