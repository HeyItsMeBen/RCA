package org.firstinspires.ftc.teamcode.Arm;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class SlowRunning {
    private final Servo servo;
    private double currentPosition;
    private long lastTimeNanos;
    private boolean isFirstRun = true;

    public SlowRunning(Servo servo, double startingPosition) {
        this.servo = servo;
        this.currentPosition = startingPosition;
        this.lastTimeNanos = System.nanoTime();

//        this.servo = servo;
//        this.currentPosition = startingPosition;
//        this.lastTimeNanos = System.nanoTime();
    }

    public boolean runOverTime(double targetPosition, double secondsForFullMove) {

        if (secondsForFullMove <= 0) {
            secondsForFullMove = 0.001;
        }

        long currentTimeNanos = System.nanoTime();

        if (isFirstRun) {
            lastTimeNanos = currentTimeNanos;
            isFirstRun = false;
        }

        double deltaTime = (currentTimeNanos - lastTimeNanos) / 1e9;
        lastTimeNanos = currentTimeNanos;

        if (deltaTime > 0.1) {
            deltaTime = 0.1;
        }

        double maxStep = (1.0 / secondsForFullMove) * deltaTime;

        if (Math.abs(targetPosition - currentPosition) <= maxStep) {
            currentPosition = targetPosition;
        } else if (targetPosition > currentPosition) {
            currentPosition += maxStep;
        } else {
            currentPosition -= maxStep;
        }

        servo.setPosition(currentPosition);

        return Math.abs(currentPosition - targetPosition) <= 0.01; // range of tolerance
    }

    public void resetTimer() {
        this.isFirstRun = true;
    }

    public double getPosition() {
        return currentPosition;
    }
}