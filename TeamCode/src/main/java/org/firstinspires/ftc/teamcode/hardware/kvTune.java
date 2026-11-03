package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class kvTune extends OpMode {
    Shooter shooter = new Shooter();
    public static double kV = 0.001;
    public double kS = 0; //SET WITH TUNED VALUE

    public double goalRPM = 1500; //SHOULD BE GREATEST EXPECTED RPM

    double[] increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrementIdx = 4; // starts at 0.001

    @Override
    public void init() {
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

        if(gamepad1.dpadUpWasPressed()) {kV += currentStep; }
        if(gamepad1.dpadDownWasPressed()) {kV -= currentStep; }

        double power = (kV * goalRPM) + kS;
        shooter.setPower(power);

        telemetry.addData("Step", "%.6f", currentStep);
        telemetry.addData("kV", "%.6f", kV);
        telemetry.addData("RPM", shooter.getRPM());
        telemetry.addData("Velocity (tps)", shooter.getVelocity());

    }
}
