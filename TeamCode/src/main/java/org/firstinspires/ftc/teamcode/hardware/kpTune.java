package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class kpTune extends OpMode {
    Shooter shooter = new Shooter();
    public static double kV = 0.01;
    public double kS = 0; //SET WITH TUNED VALUE
    public double kP = 0; //SET WITH TUNED VALUE
    public double goalRPM = 1500; /*SHOULD BE GREATEST EXPECTED RPM
    FOR EXAMPLE SHOOTING LARGE BALL FROM FAR*/



    double[] increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrementIdx = 4; // starts at 0.001

    @Override
    public void init() {
        shooter.init(hardwareMap);
    }
    @Override
    public void loop(){
        if(gamepad2.dpadRightWasPressed() && incrementIdx < 4){
            incrementIdx++;
        }else if(gamepad1.dpadLeftWasPressed() && incrementIdx > 0){
            incrementIdx--;
        }
        double currentStep = increments[incrementIdx];

        if (gamepad1.a) {
            goalRPM = 1500;
        }    else if (gamepad1.b) {
            goalRPM = 800; /* SHOULD BE LOWEST REALISTIC VALUE
            FOR EXAMPLE SHOOTING SMALL BALL FROM CLOSE*/
        }

        if(gamepad1.dpadUpWasPressed()) {kP += currentStep; }
        if(gamepad1.dpadDownWasPressed()) {kP += currentStep; }

        double feedForward = (kV * goalRPM) + kS;
        double error = goalRPM - shooter.getRPM();
        double feedBack = error * kP;

        shooter.setPower(feedForward + feedBack);

        telemetry.addData("Step", "%.6f", currentStep);
        telemetry.addData("kP", "%.6f", kP);
        telemetry.addData("Error", error);
        telemetry.addData("RPM", shooter.getRPM());

    }
}



