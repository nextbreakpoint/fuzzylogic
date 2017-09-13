package com.nextbreakpoint.fuzzylogic.playground;

import com.nextbreakpoint.fuzzylogic.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FuzzyPlayground extends Application {
    private static final int FRAME_LENGTH_IN_MILLIS = 20;
    private static final int FRAMES = 1000 / FRAME_LENGTH_IN_MILLIS;
    public static final String INPUT_0 = "input0";
    public static final String OUTPUT_0 = "output0";
    private GraphSource inputSource1;
    private GraphSource outputSource1;
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
        this.primaryStage.setResizable(true);

        VBox main = new VBox(10);

        primaryStage.setWidth(800);
        primaryStage.setHeight(800);

        List<GraphSource> inputSources = new ArrayList<>();
        List<GraphSource> outputSources = new ArrayList<>();

        inputSources.add(inputSource1);
        outputSources.add(outputSource1);

        inputGraphPane = new GraphPane(inputSources, FRAMES, FRAME_LENGTH_IN_MILLIS, TimeUnit.MILLISECONDS);
        outputGraphPane = new GraphPane(outputSources, FRAMES, FRAME_LENGTH_IN_MILLIS, TimeUnit.MILLISECONDS);

        inputGraphPane.setLineColor(Color.LIGHTGREEN);
        outputGraphPane.setLineColor(Color.LIGHTBLUE);

        inputGraphPane.setWidth(primaryStage.getWidth());
        inputGraphPane.setHeight(primaryStage.getHeight() / 2 - 5);

        outputGraphPane.setWidth(primaryStage.getWidth());
        outputGraphPane.setHeight(primaryStage.getHeight() / 2 - 5);

        main.getChildren().add(inputGraphPane);
        main.getChildren().add(outputGraphPane);

        BorderPane root = new BorderPane();

        root.setCenter(main);

        Scene scene = new Scene(root, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();

        inputGraphPane.widthProperty().bind(main.widthProperty());
        outputGraphPane.widthProperty().bind(main.widthProperty());

        inputs.put(INPUT_0, getValue(0.5));

        inputGraphPane.getValueProperty().addListener((observable, oldValue, newValue) -> inputs.put(INPUT_0, getValue(newValue)));

        primaryStage.setOnCloseRequest(e -> timer.stop());

        timer = createAnimationTimer();
        timer.start();
    }

    private double getValue(Double newValue) {
        return inRange.min() + clampValue(newValue) * (inRange.max() - inRange.min());
    }

    private AnimationTimer createAnimationTimer() {
        return new AnimationTimer() {
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
    }

    private double clampValue(Double value) {
        return value < 0 ? 0 : value > 1 ? 1 : value;
    }

    private void update() {
        Map<String, Double> outputs = system.evaluate(inputs);
        double input0 = inputs.get(INPUT_0);
        double output0 = outputs.get(OUTPUT_0);
        inputSource1.update(time, input0);
        outputSource1.update(time, output0);
        inputGraphPane.setValue(input0);
        outputGraphPane.setValue(output0);
        inputGraphPane.redraw();
        outputGraphPane.redraw();
        time += 1;
    }

    private void initSystem() {
        FuzzyRange inBaseRange = FuzzyRange.of(-0.5, 0.5).scale(6);
        FuzzyRange outBaseRange = FuzzyRange.of(-0.5, 0.5).scale(6);
        FuzzyVariable inVar1 = FuzzyVariable.of(INPUT_0, inBaseRange.translate(-2.5));
        FuzzyVariable inVar2 = FuzzyVariable.of(INPUT_0, inBaseRange.translate(+2.5));
        FuzzyVariable outVar1 = FuzzyVariable.of(OUTPUT_0, outBaseRange.translate(-3.0));
        FuzzyVariable outVar2 = FuzzyVariable.of(OUTPUT_0, outBaseRange.translate(+3.0));
        FuzzyRule rule1 = FuzzyRule.of(FuzzyPredicate.of(inVar1), FuzzyInference.of(outVar1));
        FuzzyRule rule2 = FuzzyRule.of(FuzzyPredicate.of(inVar2), FuzzyInference.of(outVar2));
        system = FuzzySystem.empty().addRule(rule1).addRule(rule2);
        inRange = FuzzyRange.of(-5, 5);
        outRange = FuzzyRange.of(-5, 5);
        inputSource1 = new GraphSource(INPUT_0, FRAMES, inRange);
        outputSource1 = new GraphSource(OUTPUT_0, FRAMES, outRange);
        inputs = new HashMap<>();
    }

    public static void main(String[] args) {
        launch(args);
    }
}