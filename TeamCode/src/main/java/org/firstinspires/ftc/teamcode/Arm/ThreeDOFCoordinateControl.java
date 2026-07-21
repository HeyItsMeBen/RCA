package org.firstinspires.ftc.teamcode.Arm;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp(name="RC Coordinate Control", group="Debug")
public class ThreeDOFCoordinateControl extends LinearOpMode {

    public static double X_POSITION = 0.0;
    public static double Y_POSITION = 0.0;

    public static double armLengthBase = 0;
    public static double armLengthMid = 0;
    public static double armLengthConnector = 0;

    public double baseX = 0;
    public double baseY = 0;

    @Override
    public void runOpMode() {

        InverseKinematicsAlgorithm IK_Alg = new InverseKinematicsAlgorithm();

        Servo shoulderServo = hardwareMap.get(Servo.class, "shoulderServo");
        Servo elbowServo = hardwareMap.get(Servo.class, "elbowHinge");
        Servo wristServo = hardwareMap.get(Servo.class, "wristHinge");
        //Servo clawServo = hardwareMap.get(Servo.class, "clawServo");
        //DcMotor slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");

        SlowRunning shoulder = new SlowRunning(shoulderServo);
        SlowRunning elbow    = new SlowRunning(elbowServo);
        SlowRunning wrist    = new SlowRunning(wristServo);

        double shoulderPosition = 0;
        double elbowPosition = 0;
        double wristPosition = 0;

        telemetry = new MultipleTelemetry(
                telemetry,
                FtcDashboard.getInstance().getTelemetry()
        );

        ElapsedTime timer = new ElapsedTime();
        waitForStart();

        while (opModeIsActive()) {

            shoulderPosition = IK_Alg.returnBasePosition_byLOC(X_POSITION - baseX, Y_POSITION - baseY, armLengthBase, armLengthMid);

            elbowPosition = IK_Alg.returnElbowPosition_byLOC(X_POSITION - baseX, Y_POSITION - baseY, armLengthBase, armLengthMid);

            wristPosition = IK_Alg.returnWristPosition_byLOC(X_POSITION - baseX, Y_POSITION - baseY, armLengthBase, armLengthMid);

            //wristPosition = IK_Alg.returnWristPosition_byGlobal(shoulderPosition, elbowPosition, 90, 0);

            shoulder.runOverTime(shoulderPosition, 3.0);
            elbow.runOverTime(elbowPosition, 2.5);
            wrist.runOverTime(wristPosition, 2.5);

            telemetry.addData("Shoulder Servo Position: ", shoulder.getPosition());

            telemetry.addData("Elbow Servo Position: ", elbow.getPosition());

            telemetry.addData("Wrist Servo Position: ", wrist.getPosition());

            telemetry.update();

            idle();
        }
    }
}