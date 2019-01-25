package RetroArch_Unofficial;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuBar;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class BatchCreator extends Application {

	private File file;
	private ArrayList<String> romList;
	private ArrayList<String> coreList;
	private ArrayList<ContentID> contentList;
	private int column = 0;
	private int val = 0;

	public BatchCreator() {
		file = null;
		romList = new ArrayList<String>();
		coreList = new ArrayList<String>();
		contentList = new ArrayList<ContentID>();
	}

	public void setFile(File tempFile) {
		file = tempFile;
	}

	public File getFile() {
		return file;
	}

	public ArrayList<String> getRomList() {
		return romList;
	}

	public ArrayList<String> getCoreList() {
		return coreList;
	}

	public ArrayList<ContentID> getContentList() {
		return contentList;
	}

	public void createBat(String outputDir, String fileName, String core, String romDir) {
		romDir = file.getAbsolutePath();
		outputDir.replace("\\", "\\\\");

		try {
			FileWriter writer = new FileWriter(outputDir + fileName.substring(0, fileName.length() - 4) + ".bat");
			writer.write("K:\\RetroArch\\retroarch.exe -L \"K:\\RetroArch\\cores\\" + core + "\" \"" + romDir + "\\"
					+ fileName + "\"");
			writer.close();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

	public void directoryButtonAction(String directoryTitle, TextField directory, Stage tempStage) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle(directoryTitle);

		try {
			directory.appendText(chooser.showDialog(tempStage).getAbsolutePath());
		} catch (NullPointerException ex) {
			directory.appendText("");
		}
	}

	@Override
	public void start(Stage primaryStage) {
		VBox vbox = new VBox();

		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem changeDirectoryItem = new MenuItem("Change Directory");
		MenuItem createBatchItem = new MenuItem("Create Batch");
		MenuItem exit = new MenuItem("Exit");

		fileMenu.getItems().addAll(changeDirectoryItem, createBatchItem, exit);
		menuBar.getMenus().addAll(fileMenu);

		GridPane contentDisplay = new GridPane();
		TextField gameName = new TextField();
		TextField gameLocation = new TextField();
		TextField core = new TextField();
		TextField extension = new TextField();

		contentDisplay.add(new Label("Content Title"), 0, 0);
		contentDisplay.add(new Label("Content Directory"), 1, 0);
		contentDisplay.add(new Label("Content Core"), 2, 0);
		contentDisplay.add(new Label("Content Extension"), 3, 0);

		contentDisplay.getColumnConstraints().forEach(e6 -> {
			e6.setMinWidth(200);
			contentDisplay.getColumnConstraints().set(column, e6);
			column++;
		});

		contentDisplay.setHgap(200);
		contentDisplay.borderProperty();
		contentDisplay.setPadding(new Insets(10, 10, 10, 10));

		StringBuilder outputPath = new StringBuilder();

		////////////////////////////////
		// CHANGE DIRECTORY MENU ITEM //
		////////////////////////////////

		changeDirectoryItem.setOnAction(e -> {

			Stage stage = new Stage();

			Label romLabel = new Label("Rom Directory");
			Label coreLabel = new Label("Core Directory");
			Label outputLabel = new Label("Output Directory");

			TextField romDir = new TextField();

			romDir.setEditable(false);
			romDir.setMouseTransparent(true);
			romDir.setFocusTraversable(false);

			TextField coreDir = new TextField();

			coreDir.setEditable(false);
			coreDir.setMouseTransparent(true);
			coreDir.setFocusTraversable(false);

			TextField outputDir = new TextField();

			outputDir.setEditable(false);
			outputDir.setMouseTransparent(true);
			outputDir.setFocusTraversable(false);

			// RETROARCH CORE DIRECTORY SELECTION CODE

			Button coreDirButton = new Button("..");
			coreDirButton.setOnAction(e2 -> {
				this.directoryButtonAction("Open RetroArch Core directory:", coreDir, stage);
			});

			// ROM DIRECTORY SELECTION CODE

			Button romDirButton = new Button("..");
			romDirButton.setOnAction(e3 -> {
				this.directoryButtonAction("Open Rom directory:", romDir, stage);
			});

			// OUTPUT DIRECTORY SELECTION CODE

			Button outputDirButton = new Button("..");
			outputDirButton.setOnAction(e4 -> {
				this.directoryButtonAction("Open Output directory:", outputDir, stage);
			});

			// PARSES ROM AND CORE DIRECTORIES AND ADDS ALLOWED CONTENTS TO APPROPRIATE
			// ARRAYLISTS

			Button ok = new Button("OK");
			ok.setOnAction(e5 -> {
				
				final int COL_ONE = 0, COL_TWO = 1, COL_THREE = 2, COL_FOUR = 3;
				

				file = new File(romDir.getText());

				if (file != null && file.list().length > 0) {
					romList.addAll(Arrays.asList(file.list((file, name) ->
					// name.toLowerCase().endsWith(".cue")
					name.toLowerCase().endsWith(".chd") || name.toLowerCase().endsWith(".m3u")
							|| name.toLowerCase().endsWith(".nes") || name.toLowerCase().endsWith(".gba")
							|| name.toLowerCase().endsWith(".sfc") || name.toLowerCase().endsWith(".md")
							|| name.toLowerCase().endsWith(".gen"))));

					for (int i = 0; i < romList.size(); i++) {
						contentList.add(new ContentID(romList.get(i), file));
						TextField temp = new TextField("");
						temp.setPrefWidth(450);
						
						TextField temp2 = new TextField("");
						temp2.setPrefWidth(300);
						
						TextField temp3 = new TextField("");
						temp3.setPrefWidth(200);
						
						TextField temp4 = new TextField("");
						temp4.setPrefWidth(100);
						
						//System.out.println(contentList.get(i).getContentName());
						
//						contentDisplay.add(new TextField(contentList.get(i).getContentName()), COL_ONE, i+1);
//						contentDisplay.add(new TextField(contentList.get(i).getLocation()), COL_TWO, i+1);
//						contentDisplay.add(new TextField(contentList.get(i).getCorePluglin()), COL_THREE, i+1);
//						contentDisplay.add(new TextField(contentList.get(i).getFileType()), COL_FOUR, i+1);
						
						temp.setText(contentList.get(i).getContentName());
						contentDisplay.add(temp, COL_ONE, i+1);
						
						temp2.setText(contentList.get(i).getLocation());
						contentDisplay.add(temp2, COL_TWO, i+1);
						
						temp3.setText(contentList.get(i).getCorePluglin());
						//contentDisplay.add(temp3, COL_THREE, i+1);
						
						temp4.setText(contentList.get(i).getFileType());
						contentDisplay.add(temp4, COL_FOUR, i+1);
					}
					
//					contentDisplay.getChildren().forEach(e7 ->{
//						e7.minWidth(200);
//						contentDisplay.getChildren().set(val, e7);
//						val++;
//					});
					//
					// System.out.println(file.list().length);
					//
					// for(int i = 0; i < file.list().length; i++) {
					// contentList.add(new ContentID(file.list()[i], file));
					// System.out.println(contentList.get(i).getContentName());
					// }
					
					
					
					

				} else {
				}

				File tempFile = new File(coreDir.getText());

				if (tempFile != null && tempFile.length() > 0) {
					coreList.addAll(Arrays.asList(tempFile.list((file, name) -> name.endsWith(".dll"))));
					for (int i = 0; i < coreList.size(); i++) {
						// System.out.println(coreList.get(i));
					}
				} else {
				}

				outputPath.append(outputDir.getText() + "\\");
				// System.out.println(outputPath.toString());
				// System.out.println(outputPath.toString().replace("\\", "\\\\"));
				stage.close();
			});

			HBox dirBox = new HBox();
			dirBox.setSpacing(10);
			dirBox.setAlignment(Pos.CENTER);

			dirBox.getChildren().addAll(coreLabel, coreDir, coreDirButton);
			dirBox.getChildren().addAll(romLabel, romDir, romDirButton);
			dirBox.getChildren().addAll(outputLabel, outputDir, outputDirButton);
			dirBox.getChildren().add(ok);

			Scene directoryScene = new Scene(dirBox);
			stage.setScene(directoryScene);
			stage.show();

			// DirectoryChooser dirChooser = new DirectoryChooser();
			// dirChooser.setTitle("Open ROM directory:");
			// // dirChooser.getExtensionFilters().addAll(new ExtensionFilter("CUE
			// // Files", "*.cue"),
			// // new ExtensionFilter("ISO Files", ".iso"), new
			// // ExtensionFilter("GBA Files", ".gba"),
			// // new ExtensionFilter("SFC Files", ".sfc"), new
			// // ExtensionFilter("NES Files", ".nes"));
			// file = dirChooser.showDialog(primaryStage);
			//
			// if (file != null) {
			// fileList.addAll(Arrays.asList(file.list((file, name) ->
			// // name.toLowerCase().endsWith(".cue")
			// name.toLowerCase().endsWith(".chd") || name.toLowerCase().endsWith(".m3u")
			// || name.toLowerCase().endsWith(".nes") || name.toLowerCase().endsWith(".gba")
			// || name.toLowerCase().endsWith(".sfc") || name.toLowerCase().endsWith(".md")
			// || name.toLowerCase().endsWith(".gen"))));
			// }
		});

		createBatchItem.setOnAction(e -> {
			romList.forEach(fileName -> {
				String fileVal = fileName.substring(fileName.length() - 4, fileName.length());
				String coreVal = "";
				String romDir = file.getAbsolutePath();

				switch (fileVal.toLowerCase()) {
				case ".chd":
				case ".m3u":
					// case ".cue":
					coreVal = "mednafen_psx_hw_libretro.dll";
					createBat(outputPath.toString(), fileName, coreVal, romDir);
					// System.out.println(x);
					break;
				case ".nes":
					coreVal = "mesen_libretro.dll";
					createBat(outputPath.toString(), fileName, coreVal, romDir);
					break;
				case ".sfc":
					coreVal = "higan_sfc_libretro.dll";
					createBat(outputPath.toString(), fileName, coreVal, romDir);
					break;
				case ".md":
				case ".gen":
					coreVal = "genesis_plus_gx_libretro.dll";
					createBat(outputPath.toString(), fileName, coreVal, romDir);
					break;
				case ".gba":
					coreVal = "mgba_libretro.dll";
					createBat(outputPath.toString(), fileName, coreVal, romDir);
					break;
				default:
					break;
				}
			});
		});

		exit.setOnAction(actionEvent -> Platform.exit());

		vbox.getChildren().addAll(menuBar, contentDisplay);
		Scene myScene = new Scene(vbox);
		primaryStage.setTitle("RetroArch BatchFile Creator");
		primaryStage.setScene(myScene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);

		// try {
		// FileWriter writer = new FileWriter("K:\\RetroArch\\Shortcuts\\Test\\"
		// + (fileType.substring(0, fileType.length() - 4)) + ".bat");
		// writer.write(
		// "K:\\RetroArch\\retroarch.exe -L
		// \"K:\\RetroArch\\cores\\mednafen_psx_hw_libretro.dll\" \"K:\\ISOS &
		// ROMS\\PSX\\"
		// + fileType + "\"");
		// writer.close();
		// } catch (IOException ex) {
		// // TODO Auto-generated catch block
		// ex.printStackTrace();
		// }
	}
}
