package com.nextbreakpoint.fuzzylogic.playground;

import com.nextbreakpoint.fuzzylogic.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FuzzyPlayground extends Application {
    private static final int FRAME_LENGTH_IN_MILLIS = 50;
    private static final int FRAMES = 100;
    private FuzzyGraphSource inputSource1;
    private FuzzyGraphSource outputSource1;
    private FuzzySystem system;
    private Stage primaryStage;
    private FuzzyRange inRange;
    private FuzzyRange outRange;
    private int time;
    private GraphPane inputGraphPane;
    private GraphPane outputGraphPane;
    private Map<String, Double> inputs;
    private AnimationTimer timer;

    @Override
    public void start(Stage primaryStage) {
        initSystem();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("FuzzyPlayground");

        VBox main = new VBox(10);

        primaryStage.setWidth(800);
        primaryStage.setHeight(800);

        List<GraphSource> inputSources = new ArrayList<>();
        List<GraphSource> outputSources = new ArrayList<>();

        inputSources.add(inputSource1);
        outputSources.add(outputSource1);

        inputGraphPane = new GraphPane(inputSources, FRAMES, FRAME_LENGTH_IN_MILLIS, TimeUnit.MILLISECONDS);
        outputGraphPane = new GraphPane(outputSources, FRAMES, FRAME_LENGTH_IN_MILLIS, TimeUnit.MILLISECONDS);

        inputGraphPane.setWidth(primaryStage.getWidth());
        inputGraphPane.setHeight(primaryStage.getHeight() / 2);

        outputGraphPane.setWidth(primaryStage.getWidth());
        outputGraphPane.setHeight(primaryStage.getHeight() / 2);

        main.getChildren().add(inputGraphPane);
        main.getChildren().add(outputGraphPane);

        BorderPane root = new BorderPane();

        root.setCenter(main);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        inputGraphPane.widthProperty().bind(main.widthProperty());
        outputGraphPane.widthProperty().bind(main.widthProperty());

        inputs.put("input0", inRange.min() + 0.5 * (inRange.max() - inRange.min()));

        inputGraphPane.getValueProperty().addListener((observable, oldValue, newValue) -> {
            inputs.put("input0", inRange.min() + newValue * (inRange.max() - inRange.min()));
        });

        primaryStage.setOnCloseRequest(e -> timer.stop());

        timer = new AnimationTimer() {
            private long last;

            @Override
            public void handle(long now) {
                long time = now / 1000000;
                if ((time - last) > FRAME_LENGTH_IN_MILLIS) {
                    update();
                    last = time;
                }
            }
        };

        timer.start();
    }

    private void update() {
        Map<String, Double> outputs = system.evaluate(inputs);
        double input0 = inputs.get("input0");
        double output0 = outputs.get("output0");
        inputSource1.update(time, input0);
        outputSource1.update(time, output0);
        inputGraphPane.redraw();
        outputGraphPane.redraw();
        time += 1;
    }

    private void initSystem() {
        FuzzyRange inBaseRange = FuzzyRange.of(0, 1).scale(4);
        FuzzyRange outBaseRange = FuzzyRange.of(0, 1).scale(4);
        FuzzyVariable inVar1 = FuzzyVariable.of("input0", inBaseRange.translate(-1.0));
        FuzzyVariable inVar2 = FuzzyVariable.of("input0", inBaseRange.translate(+1.0));
        FuzzyVariable outVar1 = FuzzyVariable.of("output0", outBaseRange.translate(-2));
        FuzzyVariable outVar2 = FuzzyVariable.of("output0", outBaseRange.translate(+2));
        FuzzyRule rule1 = FuzzyRule.of(FuzzyPredicate.of(inVar1), FuzzyInference.of(outVar1));
        FuzzyRule rule2 = FuzzyRule.of(FuzzyPredicate.of(inVar2), FuzzyInference.of(outVar2));
        system = FuzzySystem.empty().addRule(rule1).addRule(rule2);
        inRange = FuzzyRange.of(0, 10);
        outRange = FuzzyRange.of(0, 5);
        inputSource1 = new FuzzyGraphSource("input0", inRange, FRAMES);
        outputSource1 = new FuzzyGraphSource("output0", outRange, FRAMES);
        inputs = new HashMap<>();
    }

    public static void main(String[] args) {
        launch(args);
    }
}