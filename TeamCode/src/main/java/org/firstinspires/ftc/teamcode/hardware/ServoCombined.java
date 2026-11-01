package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

//combines two motors into one, for things like two motors driving a shooter or arm
public class ServoCombined {
    double position = 0;

    Servo servo1;
    Servo servo2;


    public ServoCombined(Servo servo1, Servo servo2) {
        this.servo1 = servo1;
        this.servo2 = servo2;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
        servo1.setPosition(position);
        servo2.setPosition(position);
    }

    //gets whichever velocity is larger, in case one of them has no encoder
}