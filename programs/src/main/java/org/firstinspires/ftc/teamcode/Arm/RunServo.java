package org.firstinspires.ftc.teamcode.Arm;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name="Run Servo", group = "Files")
public class RunServo extends LinearOpMode {

    public GamepadEx gamepad;

    @Override
    public void runOpMode() {

        Servo servo = hardwareMap.get(Servo.class, "ElbowHinge");

        waitForStart();

        while (opModeIsActive()) {
            servo.setPosition(0.7);
            sleep(500);
            servo.setPosition(0);

            idle();
        }
    }
}
