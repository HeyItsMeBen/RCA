package org.firstinspires.ftc.teamcode.Arm;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp(name="Move By Angle", group="RC")
public class AngleMove extends LinearOpMode {

    public static double ANGLE = 0.0;
    public static double TOTAL_RANGE = 180;
    public static double timeToPosition = 5; //seconds

    @Override
    public void runOpMode() {

        InverseKinematicsAlgorithm IK_Alg = new InverseKinematicsAlgorithm();

        Servo shoulderServo = hardwareMap.get(Servo.class, "shoulderServo");
        //Servo clawServo = hardwareMap.get(Servo.class, "clawServo");
        //DcMotor slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");

        SlowRunning shoulder = new SlowRunning(shoulderServo, 0);

        double shoulderPosition;

        telemetry = new MultipleTelemetry(
                telemetry,
                FtcDashboard.getInstance().getTelemetry()
        );

        waitForStart();

        while (opModeIsActive()) {

            shoulderPosition = IK_Alg.returnServoPosition(ANGLE, 1, TOTAL_RANGE);

            shoulder.runOverTime(shoulderPosition, timeToPosition);

            telemetry.addData("Shoulder Servo Position: ", shoulder.getPosition());
            telemetry.addData("Shoulder Servo Angle: ", IK_Alg.returnServoAngle(shoulder.getPosition(), TOTAL_RANGE));

            telemetry.update();

            idle();
        }
    }
}