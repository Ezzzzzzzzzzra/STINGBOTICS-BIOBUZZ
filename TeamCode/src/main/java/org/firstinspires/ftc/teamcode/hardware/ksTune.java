package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ksTune extends OpMode {
    Shooter shooter = new Shooter();

    public static double kS = 0;

    double[] increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrementIdx = 4;

    @Override
    public void init(){
        shooter.init(hardwareMap);
    }
    @Override
    public void loop(){
        if(gamepad1.dpadRightWasPressed() && incrementIdx < 4){
            incrementIdx++;
        }else if(gamepad1.dpadLeftWasPressed() && incrementIdx > 0){
            incrementIdx--;
        }
        double currentStep = increments[incrementIdx];

        if(gamepad1.dpadUpWasPressed()) {kS += currentStep; }
        if(gamepad1.dpadDownWasPressed()) {kS -= currentStep; }

        shooter.setPower(kS);

        telemetry.addData("Step", "%.6f", currentStep);
        telemetry.addData("kS", "%.6f", kS);
        telemetry.addData("RPM", shooter.getRPM());
        telemetry.addData("Velocity (tps)", shooter.getVelocity());

    }
}
