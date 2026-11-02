package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoPositionFinder extends LinearOpMode {
    @Override
    public void runOpMode() {

        double servoPosition = 0;

        final double increment = 0.01;

        Servo myServo= hardwareMap.get(Servo.class, "pinion");
        myServo.setPosition(servoPosition);

        telemetry.addData("Status", "Initialized. Use D-Pad Up/Down to move.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.dpad_up && servoPosition < 1){
                servoPosition += increment;
                sleep(50);

            } else if (gamepad1.dpad_down && servoPosition > 0){
                servoPosition -= increment;
                sleep(50);
            }

            myServo.setPosition(servoPosition);

            telemetry.addData("Actual Servo Position", myServo.getPosition());
            telemetry.addData("Target Servo Position" , servoPosition);
            telemetry.update();


        }




    }
}
