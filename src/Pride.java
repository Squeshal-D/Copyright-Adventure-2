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
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pride extends Character{

	ImageView portrait = new ImageView(new Image ("portraits/pridePortrait.jpg"));
	ImageView stage = new ImageView(new Image ("stages/scottStage.jpg"));
	String name = "Bling Bling Boy";
	Media theme = new Media(new String(Main.class.getResource("themes/hotFoe.mp3").toString()));
	double volume = 1;
	int hp = 60;
	int maxhp = 60;
	int misc = 0;
	boolean song = false;
	boolean teammate = false;
	boolean charging = false;
	boolean boss = true;
	boolean testing = false;
	ArrayList<String> status = new ArrayList<String>();
	
	String move1 = "";
	String move2 = "";
	String move3 = "";
	String move1desc = "20 + damage from losing hp";
	String move2desc = "";
	String move3desc = "";
	
	Button button = new Button();
	
	public void createButton(Button b)
	{
		button = b;
		b.setLayoutX(330);
		b.setLayoutY(60);
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
		misc = 0;
		teammate = true;
		charging = false;
		status.clear();
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
		int dmg = modifydmg(20 + (60 - user.getHP())/2);
		if (user.getHP() > 25)
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " slapped " + enemy.getName() + " for " + dmg + " dmg!"));
			atk.getKeyFrames().add(first);
		}
		else
		{
			KeyFrame first = new KeyFrame(Duration.ZERO, ae -> log.setText(user.getName() + " doesn't want to lose! " + enemy.getName() + " takes " + dmg + " dmg!"));
			atk.getKeyFrames().add(first);
		}
		atk.getKeyFrames().add(new KeyFrame(Duration.millis(1)));
		enemy.changeHP(-dmg);
		
        return atk;
	}
	
	public Timeline atk2(Character user, Character enemy, Label log, Stage ps)
	{
		return null;
	}
	
	public Timeline atk3(Character user, Character enemy, Label log, Stage ps)
	{
		return null;
	}
	
	public Timeline enemyAttack(Character player, Character enemy, Label log, Stage ps)
	{
		int i = 1;
		
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
		if (Main.heckCharacters.size() == 7)
		{
			return new ImageView(new Image ("stages/7-1Stage.png"));
		}
		else if (Main.heckCharacters.size() == 6)
		{
			return new ImageView(new Image ("stages/7-2Stage.png"));
		}
		else if (Main.heckCharacters.size() == 5)
		{
			return new ImageView(new Image ("stages/7-3Stage.png"));
		}
		else if (Main.heckCharacters.size() == 4)
		{
			return new ImageView(new Image ("stages/7-4Stage.png"));
		}
		else if (Main.heckCharacters.size() == 3)
		{
			return new ImageView(new Image ("stages/7-5Stage.png"));
		}
		else if (Main.heckCharacters.size() == 2)
		{
			return new ImageView(new Image ("stages/7-6Stage.png"));
		}
		else
		{
			return new ImageView(new Image ("stages/7-7Stage.png"));
		}
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
	
	public void unCharge()
	{
		charging = false;
	}
	
	public boolean isTesting()
	{
		return testing;
	}
	
	public boolean heardSong()
	{
		return song;
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
	
	public Scene bossCutscene(Stage ps)
	{
		Font sixt = Font.loadFont(Main.class.getResourceAsStream("OCRAExt.ttf"), 16);
		
		TheSystem theSystem = new TheSystem();
		
		Pane p = new Pane();
		
		Image firsti = new Image("others/7Cutscene.png");
		String firstl = "The seven deadly dudes are now the seven dead dudes! The barely-intact elevator reaches the surface. Now you can continue to the coordinates "
				+ "where you've discovered that large disturbance. That must be where the final boss is!";
		
		Image secondi = new Image("others/kaiser.PNG");
		String secondl = "The coordinates lead to no other place but Kaiser Permanente. You should have known all along. One of the party member fakes a broken finger "
				+ "so that the party is quickly granted access to the hospital. After punching the nurses in the face in the surgery room, the party quickly heads to "
				+ "the basement.";
		
		Image thirdi = new Image("others/finalCutscene.png");
		String thirdl = "The basement is quite massive, and only illuminated by the large, dystopian looking monitor that bears the image of a stern man. The monitor "
				+ "speaks. \"So you have finally come. After everything that you know, you still choose to fight me. You know I cannot let myself be defeated for the "
				+ "good of everyone, even you. But, so be it. Have at thee!\"";
		
		ImageView showing = new ImageView(firsti);
		showing.setFitWidth(1280); showing.setFitHeight(570);
		Label reading = new Label(firstl);
		reading.setFont(sixt);
		reading.setMaxWidth(1000);
		reading.setWrapText(true);
		
		ImageView text = new ImageView(new Image("others/textLog1.jpg"));
		text.setFitWidth(1080); text.setFitHeight(150);
		ImageView button = new ImageView(new Image("others/textLog1-2.jpg"));
		button.setFitWidth(200); button.setFitHeight(150);
		
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(secondi);
		images.add(thirdi);
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(secondl);
		strings.add(thirdl);
		
		Button next = new Button("Continue");
		next.setPrefSize(160, 120);
		next.setOnAction(ae -> {
			if (images.size() == 0 && strings.size() == 0)
			{
				Main.m = new MediaPlayer(theSystem.getTheme());
				Main.m.setVolume(.32*Main.volume*theSystem.getVolume());
				Main.m.setCycleCount(1);
				Main.m.setOnEndOfMedia(new Runnable() {
					@Override
					public void run() {
						Main.m = new MediaPlayer(new Media(new String(Main.class.getResource("themes/ootGanon2.mp3").toString())));
						Main.m.setVolume(.32*Main.volume*theSystem.getVolume());
						Main.m.setCycleCount(MediaPlayer.INDEFINITE);
						Main.m.play();
					}
				});
				Main.m.play();
				Main.chooseFighter(null, theSystem, Main.party, ps);
			}
			else
			{
				showing.setImage(images.get(0));
				images.remove(0);
				reading.setText(strings.get(0));
				strings.remove(0);
			}
		});
		
		StackPane textBox = new StackPane(text, reading);
		StackPane buttonBox = new StackPane(button, next);
		HBox boxes = new HBox(textBox, buttonBox);
		boxes.setLayoutY(570);
		
		p.getChildren().addAll(showing, boxes);
		
		Scene s = new Scene(p);
		return s;
	}
}