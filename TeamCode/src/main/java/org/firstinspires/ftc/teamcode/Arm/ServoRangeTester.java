package org.firstinspires.ftc.teamcode.Arm;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name="Servo Range Tester", group = "Files")
public class ServoRangeTester extends LinearOpMode {

    public GamepadEx gamepad;

    @Override
    public void runOpMode() {

        Servo servo = hardwareMap.get(Servo.class, "ElbowHinge");

        gamepad = new GamepadEx(gamepad1);

        double position = 0;

        double interval = 0.01;

        telemetry.addData("Intervals: ", interval);
        telemetry.addData("To increase, ", "press dpad_up");
        telemetry.addData("To decrease, ", "press dpad_down");
        telemetry.addData("To reset, ", "press X");
        telemetry.update();

        waitForStart();

        servo.setPosition(position);

        while (opModeIsActive()) {
            if (gamepad.getButton(GamepadKeys.Button.DPAD_UP)) {
                position += interval;
                servo.setPosition(position);
            }
            else if (gamepad.getButton(GamepadKeys.Button.DPAD_DOWN)) {
                position -= interval;
                servo.setPosition(position);
            }
            else if (gamepad.getButton(GamepadKeys.Button.X)) {
                position = 0;
                servo.setPosition(position);
            }
            telemetry.addData("Current Servo Position: ", servo.getPosition());
            telemetry.update();
            sleep(100);

            idle();
        }
    }
}
