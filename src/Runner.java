
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Runner extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		//Main game = new Main();
		
		primaryStage.setTitle("Copyright Adventure 2");
		primaryStage.getIcons().add(new Image("others/ca2Logo.png"));
		//game resolution
		primaryStage.setMaxHeight(759);
		primaryStage.setMaxWidth(1296);
		primaryStage.setMinHeight(759);
		primaryStage.setMinWidth(1296);
		
		//sets up all the arraylists and stuff that the game will use
		Main.load(primaryStage);
		//makes and shows the title screen
		Main.titleScreen(primaryStage);
	}
	
}
