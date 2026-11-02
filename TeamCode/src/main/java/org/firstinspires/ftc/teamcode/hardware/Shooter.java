package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

//combines two motors into one, for things like two motors driving a shooter or arm
public class Shooter{
    double power = 0;
    double velocity;
    DcMotorEx.RunMode mode;
    DcMotorEx m1, m2;

    private double encoderPPR = 28;
    private double gearRatio = 1;

    public double kS = 0, kV = 0, kP = 0; //SET WITH TUNED VALUES

    public void init(HardwareMap hwMap) {
        m1 = hwMap.get(DcMotorEx.class, "shootLeft");
        m2 = hwMap.get(DcMotorEx.class, "shootRight");
        m1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m1.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public double getPower() {
        return power;
    }
    public void setPower(double power) {
        this.power = power;
        m1.setPower(power);
        m2.setPower(power);
    }
    public void setVelocity(double velocity){
        this.velocity = velocity;
        m1.setVelocity(velocity);
        m2.setVelocity(velocity);
    }
    //gets whichever velocity is larger, in case one of them has no encoder
    public double getVelocity() {
        if (Math.abs(m1.getVelocity()) > Math.abs(m2.getVelocity())) {
            velocity = m1.getVelocity();
        } else {
            velocity = m2.getVelocity();
        }
        return velocity;
    }
    public double getRPM(){
        return ((getVelocity()/encoderPPR) * 60)/gearRatio;

    }
}
