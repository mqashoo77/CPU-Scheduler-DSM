package sample;

import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import sample.Scheduler;
//import schedulers.algorithms.*;

import static sample.Main.*;

public class MainScreenHandeler {

    private static boolean isPreemptive;
    static double TimeQuantum =0 ;
    static double AgeFactor = 0 ;

    public static void handelMainScreenActions(Pane mainScreen) {



        Button shortestRemainingTimeFirst = (Button) getElementById(mainScreen, "shortest_remaining_time_first");
        Button roundRobin = (Button) getElementById(mainScreen, "round_robin");
        TextField timeQuantum = (TextField) getElementById(mainScreen, "RRQ");

        Button exit = (Button) getElementById(mainScreen, "exit");
        Region returnButton = (Region) getElementById(mainScreen, "return_button");
        exit.setOnAction(actionEvent -> {
            System.exit(0);
        });


        shortestRemainingTimeFirst.setOnAction(
                event -> {
                    Scheduler shortestRemainingTimeFirstScheduler = new ShortestRemainingTimeFirstScheduler(processes);

                    Result result = new Result(shortestRemainingTimeFirstScheduler);
                    try {
                        result.show();
                    } catch (Exception e) {
                        (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
                    }
                }
        );


        roundRobin.setOnAction(
                event -> {
                    Scheduler roundRobinScheduler = new RoundRobinScheduler(processes, Double.parseDouble(timeQuantum.getText()));
                    Result result = new Result(roundRobinScheduler);
                    try {
                        result.show();
                    } catch (Exception e) {
                        (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
                    }
                }
        );

        /*
        multiprogrammingWithUniformIoPercentage.setOnAction(
                event -> {
                    IO.setDisable(false);
                }
        );
        IO.getEditor().textProperty().addListener((observableValue, s, t1) -> {
            try {
                Double.parseDouble(t1);
            } catch (Exception e) {
                if (t1.compareTo("") == 0)
                    IO.getEditor().setText("0");
                else
                    IO.getEditor().setText(s);
            }
        });
        IO.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().compareTo(KeyCode.ENTER) == 0) {
                try {
                    double IOPercent = Double.parseDouble(IO.getEditor().getText());
                    Scheduler multiprogrammedWithUniformIOPercentageScheduler = new MultiprogrammedWithUniformIOPercentage(processes, IOPercent);
                    Result result = new Result(multiprogrammedWithUniformIOPercentageScheduler);
                    result.show();
                    IO.setDisable(true);
                } catch (Exception e) {
                    (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
                }
            }
        });


    }

    public static void setAutoOrUserEntryForRoundRobinScheduler(CheckBox auto, TextField userEnrty) {
        auto.setDisable(false);
        userEnrty.setDisable(false);

        auto.setOnAction(
                event1 -> {
                    userEnrty.setDisable(true);
                    TimeQuantum = findTimeQuantum(processes);
                    Scheduler roundRobinScheduler = new RoundRobinScheduler(processes, TimeQuantum);
                    Result result = new Result(roundRobinScheduler);
                    try {
                        result.show();
                    } catch (Exception e) {
                        (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
                    } finally {
                        auto.setSelected(false);
                        auto.setDisable(true);
                    }
                }
        );

        userEnrty.setOnAction( // enter
                event2 -> {
                    auto.setDisable(true);
                    TimeQuantum = Double.parseDouble(userEnrty.getText());
                    Scheduler roundRobinScheduler = new RoundRobinScheduler(processes, Double.parseDouble(userEnrty.getText()));
                    Result result = new Result(roundRobinScheduler);
                    try {
                        result.show();
                    } catch (Exception e) {
                        (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
                    } finally {
                        userEnrty.clear();
                        userEnrty.setDisable(true);
                    }
                }
        );
    }

    public static void setAutoOrUserEntryForPriorityScheduler(CheckBox auto, TextField userEnrty) {
        auto.setDisable(false);
        userEnrty.setDisable(false);
        auto.setOnAction(
                event1 -> {
                    userEnrty.setDisable(true);
                    Scheduler scheduler;
                    if (MainScreenHandeler.isPreemptive) {
                        AgeFactor = findAgeFactor(processes);
                        scheduler = new PreemptiveExplicitPriorityScheduler(processes, findAgeFactor(processes));
                    }
                    else {
                        AgeFactor = findAgeFactor(processes);
                        scheduler = new NonPreemptiveExplicitPriorityScheduler(processes, findAgeFactor(processes));
                    }
                    Result result = new Result(scheduler);
                    try {
                        result.show();
                    } catch (Exception e) {
                        (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
                    } finally {
                        auto.setSelected(false);
                        auto.setDisable(true);
                    }
                }
        );

        userEnrty.setOnAction( // enter
                event2 -> {
                    auto.setDisable(true);
                    Scheduler scheduler;
                    if (MainScreenHandeler.isPreemptive) {
                        AgeFactor = Long.parseLong(userEnrty.getText());
                        scheduler = new PreemptiveExplicitPriorityScheduler(processes, Long.parseLong(userEnrty.getText()));
                    }
                    else {
                        AgeFactor = Long.parseLong(userEnrty.getText());
                        scheduler = new NonPreemptiveExplicitPriorityScheduler(processes, Long.parseLong(userEnrty.getText()));
                    }
                    Result result = new Result(scheduler);
                    try {
                        result.show();
                    } catch (Exception e) {
                        (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
                    } finally {
                        userEnrty.clear();
                        userEnrty.setDisable(true);
                    }
                }
        );

         */
    }

}
