//package org.firstinspires.ftc.teamcode.Arm;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//@Config
//@TeleOp(name="RC Coordinate Control", group="RC")
//public class ThreeDOFCoordinateControl extends LinearOpMode {
//
//    public static double X_POSITION = 0.0;
//    public static double Y_POSITION = 0.0;
//
//    public double armLengthBase = 105; //in mm
//    public double armLengthMid = 140;
//    public double timeToPosition = 5; //seconds
//
//    public static boolean readAngle = true;
//
//    public double dualShaftRange = 180;
//    public double gobildaRange = 300;
//    public double baseX = 0;
//    public double baseY = 0;
//
//    @Override
//    public void runOpMode() {
//
//        InverseKinematicsAlgorithm IK_Alg = new InverseKinematicsAlgorithm();
//
//        Servo shoulderServo = hardwareMap.get(Servo.class, "shoulderServo");
//        Servo elbowServo = hardwareMap.get(Servo.class, "elbowHinge");
//        Servo wristServo = hardwareMap.get(Servo.class, "wristHinge");
//        //Servo clawServo = hardwareMap.get(Servo.class, "clawServo");
//        //DcMotor slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");
//
//        SlowRunning shoulder = new SlowRunning(shoulderServo);
//        SlowRunning elbow    = new SlowRunning(elbowServo);
//        SlowRunning wrist    = new SlowRunning(wristServo);
//
//        double shoulderPosition;
//        double elbowPosition;
//        double wristPosition;
//
//        telemetry = new MultipleTelemetry(
//                telemetry,
//                FtcDashboard.getInstance().getTelemetry()
//        );
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//
//            shoulderPosition = IK_Alg.returnBasePosition_byLOC(X_POSITION - baseX, Y_POSITION - baseY, armLengthBase, armLengthMid, gobildaRange);
//
//            elbowPosition = IK_Alg.returnElbowPosition_byLOC(X_POSITION - baseX, Y_POSITION - baseY, armLengthBase, armLengthMid, dualShaftRange);
//
//            wristPosition = IK_Alg.returnWristPosition_byLOC(X_POSITION - baseX, Y_POSITION - baseY, armLengthBase, armLengthMid, dualShaftRange);
//
//            //wristPosition = IK_Alg.returnWristPosition_byGlobal(shoulderPosition, elbowPosition, 90, 0);
//
//            shoulder.runOverTime(shoulderPosition, timeToPosition);
//            elbow.runOverTime(elbowPosition, timeToPosition);
//            wrist.runOverTime(wristPosition, timeToPosition);
//
//            if (readAngle) {
//                telemetry.addData("Shoulder Servo Angle: ", IK_Alg.returnServoAngle(shoulder.getPosition()));
//                telemetry.addData("Elbow Servo Angle: ", IK_Alg.returnServoAngle(elbow.getPosition()));
//                telemetry.addData("Wrist Servo Angle: ", IK_Alg.returnServoAngle(wrist.getPosition()));
//            } else {
//                telemetry.addData("Shoulder Servo Position: ", shoulder.getPosition());
//                telemetry.addData("Elbow Servo Position: ", elbow.getPosition());
//                telemetry.addData("Wrist Servo Position: ", wrist.getPosition());
//            }
//            telemetry.update();
//
//            idle();
//        }
//    }
//}

package org.firstinspires.ftc.teamcode.Arm;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="RC Coordinate Control", group="RC")
public class ThreeDOFCoordinateControl extends LinearOpMode {

    public static double X_POSITION = 60.0;
    public static double Y_POSITION = 60.0;

    public double armLengthBase = 110; // in mm
    public double armLengthMid = 145;
    public double timeToPosition = 10; // seconds

    public static boolean readAngle = true;

    public double dualShaftRange = 180;
    public double gobildaRange = 300;
    public double baseX = 0;
    public double baseY = 0;

    public boolean initialize = true;

    @Override
    public void runOpMode() {

        InverseKinematicsAlgorithm IK_Alg = new InverseKinematicsAlgorithm();

        Servo shoulderServo = hardwareMap.get(Servo.class, "shoulderServo");
        Servo elbowServo = hardwareMap.get(Servo.class, "elbowHinge");
        Servo wristServo = hardwareMap.get(Servo.class, "wristHinge");

        // Reverse direction here if physical servo rotates opposite to calculation
        // shoulderServo.setDirection(Servo.Direction.REVERSE);
        // elbowServo.setDirection(Servo.Direction.REVERSE);
        // wristServo.setDirection(Servo.Direction.REVERSE);

        SlowRunning shoulder = new SlowRunning(shoulderServo, 0.44); //start at 0.44
        SlowRunning elbow    = new SlowRunning(elbowServo, 0.44); //start at 0.44
        SlowRunning wrist    = new SlowRunning(wristServo, 0);

        double shoulderPosition;
        double elbowPosition;
        double wristPosition;

        telemetry = new MultipleTelemetry(
                telemetry,
                FtcDashboard.getInstance().getTelemetry()
        );

        waitForStart();

        while (opModeIsActive()) {

//            if (initialize) {
//                elbowPosition = IK_Alg.returnServoPosition(50, 1, dualShaftRange);
//                if (elbow.runOverTime(elbowPosition, timeToPosition)) {
//                    initialize = false;
//                }
//            }

            shoulderPosition = IK_Alg.returnBasePosition_byLOC(X_POSITION - baseX, Y_POSITION - baseY, armLengthBase, armLengthMid, gobildaRange);
            elbowPosition = IK_Alg.returnElbowPosition_byLOC(X_POSITION - baseX, Y_POSITION - baseY, armLengthBase, armLengthMid, dualShaftRange);
            wristPosition = IK_Alg.returnWristPosition_byLOC(X_POSITION - baseX, Y_POSITION - baseY, armLengthBase, armLengthMid, dualShaftRange);

            shoulder.runOverTime(shoulderPosition, timeToPosition);
            elbow.runOverTime(elbowPosition, timeToPosition);
            wrist.runOverTime(wristPosition, timeToPosition);

            if (readAngle) {
                telemetry.addData("Shoulder Servo Angle: ", IK_Alg.returnServoAngle(shoulder.getPosition(), gobildaRange));
                telemetry.addData("Elbow Servo Angle: ", IK_Alg.returnServoAngle(elbow.getPosition(), dualShaftRange));
                telemetry.addData("Wrist Servo Angle: ", IK_Alg.returnServoAngle(wrist.getPosition(), dualShaftRange));
            } else {
                telemetry.addData("Shoulder Servo Position: ", shoulder.getPosition());
                telemetry.addData("Elbow Servo Position: ", elbow.getPosition());
                telemetry.addData("Wrist Servo Position: ", wrist.getPosition());
            }
            telemetry.update();

            idle();
        }
    }
}