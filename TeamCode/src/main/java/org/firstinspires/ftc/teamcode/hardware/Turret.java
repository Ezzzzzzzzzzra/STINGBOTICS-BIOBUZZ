package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Turret {
    public DcMotorEx turret;
    ElapsedTime timer;
    public Turret() {
    }

    ;

    public void init(HardwareMap hardwareMap, ElapsedTime timer) {
        this.timer = timer;
        turret = hardwareMap.get(DcMotorEx.class, "turret");

        //Map variables to motors
    }
}
