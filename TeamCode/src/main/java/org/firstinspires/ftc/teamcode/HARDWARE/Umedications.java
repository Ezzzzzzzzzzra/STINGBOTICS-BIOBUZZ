package org.firstinspires.ftc.teamcode.HARDWARE;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class Umedications extends OpMode {
    public DcMotorEx shooter;
    public double highVelocity = 1600;
    public double lowVelocity = 900;

    double currentTargetVelocity = highVelocity;

    //feedforward
    double F = 0;

    //proportional
    double P = 0;

    //value at which F and P change
    double[] stepSizes = {10.0, 1.0, 0.1, 0.001, 0.0001};
    int stepIndex = 1;


    @Override
    public void init(){
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        //I and D are 0 for a flywheel shooter
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        telemetry.addLine("init Complete!");
    }
    public void loop(){
        //set all gamepad commands
        //set target velocity
        //update telemetry

        /*pressing Y allows you to test the amount of time it takes to switch between
        high and low velocities*/
        if (gamepad1.yWasPressed()){
            if (currentTargetVelocity == highVelocity){
                currentTargetVelocity = lowVelocity;
            }else{
                currentTargetVelocity = highVelocity;
            }

            //pressing B changes the value by which P and F change when pressing the d pad
        }
        if (gamepad1.bWasPressed()){
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if (gamepad1.dpadLeftWasPressed()){
            F -= stepSizes[stepIndex];
        }
        if(gamepad1.dpadRightWasPressed()){
            F += stepSizes[stepIndex];
        }
        if(gamepad1.dpadUpWasPressed()){
            P += stepSizes[stepIndex];

        }
        if(gamepad1.dpadDownWasPressed()){
            P -= stepSizes[stepIndex];
        }
        //set new PIDF coefficients
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        //set velocity
        shooter.setVelocity(currentTargetVelocity);

        double currentVelocity = shooter.getVelocity();
        double error = currentTargetVelocity - currentVelocity;

        telemetry.addData("target Velocity: ", currentTargetVelocity);
        telemetry.addData("Current Velocity: ", "%.2f", currentVelocity);
        telemetry.addData("Error ","&.2f", error);
        telemetry.addLine("-------------------------------");
        telemetry.addData("Tuning P", "%.4f (D-Pad U/D)", P);
        telemetry.addData("Tuning F", "%.4f (D-Pad L/R)", F);
        telemetry.addData("StepSize", "%.4f (B Button)", stepSizes[stepIndex]);


    }
}
