package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

//combines two motors into one, for things like two motors driving a shooter or arm
public class DcMotorCombined{
    double power = 0;
    double velocity;
    DcMotorEx.RunMode mode;
    DcMotorEx motor1;
    DcMotorEx motor2;
    PIDFCoefficients coeffs;
    public DcMotorCombined(DcMotorEx motor1, DcMotorEx motor2) {
        this.motor1 = motor1;
        this.motor2 = motor2;

    }

    public double getPower() {
        return power;
    }
    public void setPower(double power) {
        this.power = power;
        motor1.setPower(power);
        motor2.setPower(power);
    }
    public void setMode(DcMotorEx.RunMode mode){
        this.mode = mode;
        motor1.setMode(mode);
        motor2.setMode(mode);
    }
    public void setVelocity(double velocity){
        this.velocity = velocity;
        motor1.setVelocity(velocity);
        motor2.setVelocity(velocity);
    }
    public void setPIDFCoefficients(DcMotorEx.RunMode mode, PIDFCoefficients coeffs) {
        this.mode = mode;
        this.coeffs = coeffs;
        motor1.setPIDFCoefficients(mode, coeffs);
        motor2.setPIDFCoefficients(mode, coeffs);
    }
    //gets whichever velocity is larger, in case one of them has no encoder
    public double getVelocity() {
        if (Math.abs(motor1.getVelocity()) > Math.abs(motor2.getVelocity())) {
            velocity = motor1.getVelocity();
        } else {
            velocity = motor2.getVelocity();
        }
        return velocity;
    }
}
