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

public class DJ extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/drakePortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/djStage.jpg"));
	String name = "Drake";
	Media theme = new Media(new String(Main.class.getResource("themes/pepperSteak.mp3").toString()));
	double volume = 1.5;
	int hp = 60;
	int maxhp = 60;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = false;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "Tag Team";
	String move2 = "Guitar Shred";
	String move3 = "Attract";
	String move1desc = "Drake says, \"See ya!\" and slaps the opponent before ditching.\nDoes 10 damage.\nSwitches to Josh.";
	String move2desc = "Through the Fire and Flames on expert.\n25 damage on first use per opponent.\n15 damage on other uses.";
	String move3desc = "Drake takes off his shirt or something.\nGives the enemy 2 weakness statuses.";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(480);
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
		name = "Drake";
		portrait = new ImageView(new Image ("portraits/drakePortrait.jpg"));
		move1 = "Tag Team";
		move2 = "Guitar Shred";
		move3 = "Attract";
		move1desc = "Drake says, \"See ya!\" and slaps the opponent before ditching.\nDoes 10 damage.\nSwitches to Josh.";
		move2desc = "Through the Fire and Flames on expert.\n25 damage on first use per opponent.\n15 damage on other uses.";
		move3desc = "Drake takes off his shirt or something.\nGives the enemy 2 weakness statuses.";
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
		String cname = user.getName();
		if (user.getName().equals("Drake"))
		{
			int dmg = modifydmg(10);
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(cname + " says, \"See ya!\" and slaps " + enemy.getName() + " for "
					+ dmg + " damage!"));
			atk.getKeyFrames().add(first);
			enemy.changeHP(-dmg);
			name = "Josh";
			portrait = new ImageView(new Image("portraits/joshPortrait.jpg"));
			move2 = "Car Slam";
			move3 = "Megan...";
			move1desc = "Josh takes a well deserved break.\nHeals 15 hp.\nSwitches to Drake.";
			move2desc = "RIP Oprah.\nDoes 20 damage.\nOnly hits damaged enemies.";
			move3desc = "Josh makes Megan do something.\nYou never know what she's up to.";
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(cname + " is gonna take a break. They healed 15 hp."));
			atk.getKeyFrames().add(first);
			user.changeHP(15);
			name = "Drake";
			portrait = new ImageView(new Image ("portraits/drakePortrait.jpg"));
			move2 = "Guitar Shred";
			move3 = "Attract";
			move1desc = "Drake says, \"See ya!\" and slaps the opponent before ditching.\nDoes 10 damage.\nSwitches to Josh.";
			move2desc = "Through the Fire and Flames on expert.\n25 damage on first use per opponent.\n15 damage on other uses.";
			move3desc = "Drake takes off his shirt or something.\nGives the enemy 2 weakness statuses.";
		}
		
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
		return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		int dmg;
		if (user.getName().equals("Drake"))
		{
			if (!enemy.heardSong())
			{
				dmg = user.modifydmg(25);
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " shreds on their guitar. " + enemy.getName() + " is amazed "
						+ "and takes " + dmg + " damage!"));
				atk.getKeyFrames().add(first);
				enemy.changeHP(-dmg);
				enemy.hearSong();
			}
			else
			{
				dmg = user.modifydmg(15);
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " shreds on their guitar. " + enemy.getName() + " has heard "
						+ "this before.\nThey only take " + dmg + " damage."));
				atk.getKeyFrames().add(first);
				enemy.changeHP(-dmg);
			}
		}
		else
		{
			if(enemy.getHP() < enemy.getMaxHP())
			{
				dmg = modifydmg(20);
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " runs over " + enemy.getName() + " with a car for " + dmg
						+ " damage!"));
				atk.getKeyFrames().add(first);
				enemy.changeHP(-dmg);
			}
			else
			{
				KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(enemy.getName() + " dodges " + user.getName() + ", who tried to hit them with "
						+ "a car."));
				atk.getKeyFrames().add(first);
			}
		}
		KeyFrame second = new KeyFrame(Duration.millis(1));
		atk.getKeyFrames().add(second);
		
        return atk;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		Timeline atk = new Timeline();
		if (name.equals("Drake"))
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " became hot! " + enemy.getName() + " was flustered!"));
			atk.getKeyFrames().add(first);
			enemy.addStat("weak");
			enemy.addStat("weak");
			
			KeyFrame second = new KeyFrame(Duration.millis(1));
			atk.getKeyFrames().add(second);
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " calls for Megan's aid!"));
			atk.getKeyFrames().add(first);
			
			int r = (int) (Math.random()*5);
			if (r == 0)
			{
				KeyFrame second = new KeyFrame(Duration.seconds(2), ae -> log.setText("Megan called " + user.getName() + " a boob and left. They are mad."));
				atk.getKeyFrames().add(second);
				user.addStat("rage");
			}
			else if (r == 1)
			{
				if (enemy.getHP() <= 35)
				{
					KeyFrame second = new KeyFrame(Duration.seconds(2), ae -> log.setText("Megan killed " + enemy.getName() + "."));
					atk.getKeyFrames().add(second);
					enemy.changeHP(-enemy.getHP());
				}
				else
				{
					KeyFrame second = new KeyFrame(Duration.seconds(2), ae -> log.setText("Megan saw " + enemy.getName() + ", screamed, and ran."));
					atk.getKeyFrames().add(second);
				}
			}
			else if (r == 2)
			{
				KeyFrame second = new KeyFrame(Duration.seconds(2), ae -> log.setText("Megan got " + user.getName() + " some lemonade. "
						+ "They gained 30 hp."));
				atk.getKeyFrames().add(second);
				user.changeHP(30);
				user.getStatus().clear();
				int r2 = (int) (Math.random()*2);
				if (r2 == 0)
				{
					KeyFrame third = new KeyFrame(Duration.seconds(4), ae -> log.setText("She also gave " + enemy.getName() + " some spiked lemonade! They "
							+ "feel weak and sick."));
					atk.getKeyFrames().add(third);
					enemy.changeHP(15);
					enemy.addStat("PSN");
					enemy.addStat("weak");
				}
				
			}
			else if (r == 3)
			{
				int dmg = user.modifydmg(30);
				KeyFrame second = new KeyFrame(Duration.seconds(2), ae -> log.setText("Megan throws " + user.getName() + "(10) at " + enemy.getName()
						+ "(" + dmg + "). Megan!!!"));
				atk.getKeyFrames().add(second);
				enemy.changeHP(-dmg);
				user.changeHP(-10);
			}
			else
			{
				KeyFrame second = new KeyFrame(Duration.seconds(2), ae -> log.setText("Megan got " + enemy.getName() + " and " + user.getName() + " in trouble!"
						+ " They shared the pain."));
				atk.getKeyFrames().add(second);
				float ae = (float) enemy.getHP()/(float) enemy.getMaxHP();
				float au = (float) user.getHP()/(float) user.getMaxHP();
				float average = (ae + au)/2;
				int newe = (int) (average*enemy.getMaxHP()) - enemy.getHP();
				int newu = (int) (average*user.getMaxHP()) - user.getHP();
				enemy.changeHP(newe);
				user.changeHP(newu);
			}
		}
		return atk;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = (int) (Math.random()*3 + 1);
		if (name.equals("Drake"))
		{
			if ((!player.heardSong() && player.getHP() <= 25) || (player.heardSong() && player.getHP() <= 15))
			{
				i = 2;
			}
			else if (player.isCharging() && hp > 15 && !player.getMoveName(2).equals("Smash") && !player.getMoveName(1).equals("Headbutt"))
			{
				i = 3;
			}
			else if (!player.heardSong())
			{
				i = 2;
			}
			else if (player.getHP() < player.getMaxHP())
			{
				i = (int) (Math.random()*2 + 1);
			}
			else i = (int) (Math.random()*3 + 1);
		}
		else
		{
			if (player.getHP() <= 20)
			{
				i = 2;
			}
			else if (player.heardSong() && player.getHP() < player.getMaxHP())
			{
				i = (int) (Math.random()*2 + 2);
			}
			else if (hp > 45 && player.getHP() < player.getMaxHP())
			{
				i = (int) (Math.random()*2 + 2);
			}
			else i = (int) (Math.random()*3 + 1);
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
		if (name.equals("Drake"))
		{
			return "I'm going to jail.";
		}
		else
		{
			return "Where's the door hole?";
		}
	}
}
