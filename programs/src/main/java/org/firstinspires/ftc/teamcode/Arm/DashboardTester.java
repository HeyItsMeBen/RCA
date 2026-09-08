package org.firstinspires.ftc.teamcode.Arm;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="Dashboard Servo Test", group="RC")
public class DashboardTester extends LinearOpMode {

    public static double ELBOW_SERVO_POSITION = 0.0;

    @Override
    public void runOpMode() {

        Servo servo = hardwareMap.get(Servo.class, "shoulderServo");

        telemetry = new MultipleTelemetry(
                telemetry,
                FtcDashboard.getInstance().getTelemetry()
        );

        waitForStart();

        while (opModeIsActive()) {

            servo.setPosition(ELBOW_SERVO_POSITION);

            telemetry.addData("Servo Position", ELBOW_SERVO_POSITION);
            telemetry.update();

            idle();
        }
    }
}