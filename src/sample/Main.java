package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Process;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import static sample.MainScreenHandeler.handelMainScreenActions;



public class Main extends Application {
    public static ArrayList<Process> processes = new ArrayList<>();
    private static Stage stage;
    private static Scene fileLoadingScene;

    public static Node getElementById(Pane pane, String id) {
        for (Node node : pane.getChildren()) {
            if (node.getId() != null && node.getId().compareTo(id) == 0)
                return node;
        }

        return null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Pane fileLoadingScreen = FXMLLoader.load(getClass().getResource("screens/file_loading_screen.fxml"));
        Pane mainScreen = FXMLLoader.load(getClass().getResource("screens/main_screen.fxml"));
        fileLoadingScene = new Scene(fileLoadingScreen, 600, 400);
        Scene mainScene = new Scene(mainScreen, 600, 400);
        handelMainScreenActions(mainScreen);

        primaryStage.setTitle("Hello World");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(true);
        Button loadFile = (Button) getElementById(fileLoadingScreen, "load_file");
        Button browse = (Button) getElementById(fileLoadingScreen, "browse");
        Button generateFile = (Button) getElementById(fileLoadingScreen, "generate_file");
        Button exit = (Button) getElementById(fileLoadingScreen, "exit");
        TextField fileName = (TextField) getElementById(fileLoadingScreen, "file_name");
        TextField GF = (TextField) getElementById(fileLoadingScreen, "GFile");
        exit.setOnAction(actionEvent -> {
            System.exit(0);
        });
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));

        File[] file = new File[1];
        double[] pos = new double[4];
        EventHandler<MouseEvent> capturePos = event -> {
            pos[0] = event.getScreenX();
            pos[1] = event.getScreenY();
            pos[2] = primaryStage.getX();
            pos[3] = primaryStage.getY();
        };
        EventHandler<MouseEvent> changePos = event -> {
            primaryStage.setX(pos[2] + event.getScreenX() - pos[0]);
            primaryStage.setY(pos[3] + event.getScreenY() - pos[1]);
        };

        fileLoadingScreen.setOnMousePressed(capturePos);
        fileLoadingScreen.setOnMouseDragged(changePos);
        mainScreen.setOnMousePressed(capturePos);
        mainScreen.setOnMouseDragged(changePos);
        ChangeListener<Number> widthListener = (observable, oldValue, newValue) -> primaryStage.setWidth(600);
        ChangeListener<Number> heightListener = (observable, oldValue, newValue) -> primaryStage.setHeight(400);
        browse.setOnAction(
                event -> {
                    file[0] = fileChooser.showOpenDialog(primaryStage);
                    if (file[0] != null)
                        fileName.setText(file[0].getAbsolutePath());
                }
        );
        generateFile.setOnAction(
                event -> {
                    GenerateFile(GF);
                    try {

                        Alert c = new Alert(AlertType.NONE);

                        c.setAlertType(AlertType.CONFIRMATION);
                        c.setContentText("The File has been generate successfully >> Look at the path");
                        c.show();

                    } catch (Exception e) {
                        processes.clear();
                        // Stop the Operation until a file is entered
                        (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();

                    }
                }
        );
        fileName.setOnAction(
                event -> {
                    try {
                        if (fileName.getText() == "") throw new Exception("Invalid File Name");
                        file[0] = new File(fileName.getText());
                        readFile(file[0]);
                        primaryStage.setScene(
                                mainScene
                        );
                    } catch (Exception e) {
                        processes.clear();
                        // Stop the Operation until a file is entered
                        (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
                    }
                }
        );
        loadFile.setOnAction(
                event -> {
                    if (file[0] == null) {
                        try {
                            if (fileName.getText() == "") throw new Exception("Invalid File Name");
                            file[0] = new File(fileName.getText());
                            readFile(file[0]);
                            primaryStage.setScene(
                                    mainScene
                            );
                        } catch (Exception e) {
                            processes.clear();
                            // Stop the Operation until a file is entered
                            (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
                        }
                    } else {
                        try {
                            readFile(file[0]);
                            primaryStage.setScene(mainScene);
                        } catch (Exception e) {
                            processes.clear();
                            (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
                        }
                    }
                }
        );

        primaryStage.setScene(fileLoadingScene);
        primaryStage.show();
    }

    private void readFile(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        processes.clear();

        //ArrayList<Integer> cputemp = new ArrayList<>();
        //ArrayList<Integer> iotemp = new ArrayList<>();


        while (sc.hasNext()){
            int i;
            ArrayList<Integer> cputemp = new ArrayList<>();
            ArrayList<Integer> iotemp = new ArrayList<>();



            String line = sc.nextLine();
            String [] fields = line.split(",");

            for (i=3;i<=fields.length-2;i+=2) {
                cputemp.add(Integer.parseInt(fields[i+1]));
            }
            for (i=3;i<=fields.length-2;i+=2) {
                iotemp.add(Integer.parseInt(fields[i]));
            }

            Process process = new Process(Double.parseDouble(fields[0]),Long.parseLong(fields[1])
                    ,Long.parseLong(fields[2]), cputemp , iotemp);

            processes.add(process);


        }

        System.out.println(processes);




    }

    public static double findTimeQuantum(ArrayList<Process> processes) {
        double timeQuantum;
        processes = new ArrayList<>(processes);
        // Sort processes based on the task duration parameter
        Collections.sort(processes, Comparator.comparingDouble(Process::getCpuBurst));
        int numberOfProcessesToIncludeInTQ = (int) Math.ceil(0.8 * (double) processes.size());
        if (numberOfProcessesToIncludeInTQ == 0) return 0;
        timeQuantum = processes.get(numberOfProcessesToIncludeInTQ - 1).getCpuBurst();
        return timeQuantum;
    }

    public static double findAgeFactor(ArrayList<Process> processes) {
        double sumOfPIDs = 0.0;
        for (Process process : processes) {
            sumOfPIDs += process.getProcessID();
        }
        double average = sumOfPIDs / processes.size();
        double sumOfSquaredDiff = 0.0;
        for (Process process : processes) {
            sumOfSquaredDiff += ((process.getProcessID() - average) * (process.getProcessID() - average));
        }
        double variance = sumOfSquaredDiff / processes.size();
        return Math.sqrt(variance);
    }

    public static void GenerateFile(TextField f){
        // More than 40 process would be exhaustive, less than 5 would be meaningless
        int numberOfLines = (int) (20 + Math.random() * (100 - 20)); // generate a number n: 20 <= n <= 100



        try {
            FileWriter fw = new FileWriter(f.getText()+".txt");
            Integer[] arr = new Integer[numberOfLines];

            for (int i = 0; i < numberOfLines; i++) arr[i] = i;

            List<Integer> temp = Arrays.asList(arr);
            Collections.shuffle(temp);
            temp.toArray(arr);
            for (int i = 0; i < numberOfLines; i++) {
                int numberOfBurst = (int) (5 + Math.random() * (20 - 5)); // generate a number n: 5 <= n <= 20
                long pID = arr[i]; // unique
                double arrivalTime = Math.floor(0 + Math.random() * 41); // arrivalTime <= 40.0
                double cpuburstt = Math.floor(2 + Math.random() * 120); // cpuburst <= 120.0
                Integer [] cpuburst= new Integer [numberOfBurst];
                Integer [] ioburst  = new Integer [numberOfBurst];
                 for (int j = 0; j < numberOfBurst; j++) {
                     cpuburst[j] =   (int) (2 + Math.random() * 120); // ioburst <= 120.0
                     ioburst[j] =   (int) (2 + Math.random() * 120); // ioburst <= 120.0
                }
                fw.write(pID + "," + (int)arrivalTime + "," +  (int)cpuburstt );

                 for (int j = 0; j < numberOfBurst; j++) {
                    fw.write( "," + cpuburst[j] + "," + ioburst[j]);

                }
                fw.write("\n");


            }
            fw.close();

        } catch (IOException e) {
            processes.clear();
            (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
        }


    }



    public static void main(String[] args) {
        launch(args);
    }
}
