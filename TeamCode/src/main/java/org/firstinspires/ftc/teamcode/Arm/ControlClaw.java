package org.firstinspires.ftc.teamcode.Arm;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="Control Claw", group="RC")
public class ControlClaw extends LinearOpMode {

    public static double SHOULDER_POSITION = 0;
    public static double timeToPosition = 5; //seconds

    @Override
    public void runOpMode() {

        Servo claw = hardwareMap.get(Servo.class, "clawServo");

        SlowRunning shoulder = new SlowRunning(claw, 0);

        telemetry = new MultipleTelemetry(
                telemetry,
                FtcDashboard.getInstance().getTelemetry()
        );

        waitForStart();

        while (opModeIsActive()) {

            shoulder.runOverTime(SHOULDER_POSITION, timeToPosition);

            telemetry.addData("Shoulder Servo Position: ", shoulder.getPosition());

            telemetry.update();

            idle();
        }
    }
}