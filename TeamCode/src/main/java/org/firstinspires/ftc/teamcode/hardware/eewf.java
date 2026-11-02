package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class eewf extends LinearOpMode {


    @Override
    public void runOpMode() {
        double servoPosition = 0;

        Servo myServo= hardwareMap.get(Servo.class, "pinion");
        myServo.setPosition(servoPosition);

        enum Artifact{
            nectar,
            pollen
        }
        Artifact artifact = Artifact.nectar;

        waitForStart();

        while (opModeIsActive()){


            if (artifact == artifact.nectar){
                servoPosition = 0.5;
                myServo.setPosition(servoPosition);

            }else if(artifact == artifact.pollen){
                servoPosition = 0;
                myServo.setPosition(servoPosition);
            }

            if (gamepad1.a && artifact == artifact.nectar){
                artifact = artifact.pollen;
            } else if (gamepad1.a) {
                artifact = artifact.nectar;
            }


            telemetry.addData("Actual Servo Position", myServo.getPosition());
            telemetry.addData("artifact" , artifact);
            telemetry.update();
        }

    }




}
