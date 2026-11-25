package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@TeleOp
public class ShooterEncoderTest extends OpMode {

    DcMotorEx left;
    DcMotorEx right;


    private void showEncoders() {
        telemetry.addLine("left shooter ");
        telemetry.addData("Position", left.getCurrentPosition());
        telemetry.addData("Velocity", left.getVelocity());
        telemetry.addData("Left port", left.getConnectionInfo());

        telemetry.addLine();

        telemetry.addLine("shootRight");
        telemetry.addData("Position", right.getCurrentPosition());
        telemetry.addData("Velocity", right.getVelocity());
        telemetry.addData("Right port", right.getConnectionInfo());
    }

    @Override
    public void init() {
        left  = hardwareMap.get(DcMotorEx.class, "shootLeft");
        right = hardwareMap.get(DcMotorEx.class, "shootRight");

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void init_loop() {
        showEncoders();
    }

    @Override
    public void loop() {
        double power = gamepad1.right_trigger * 1;
        left.setPower(power);
        right.setPower(power);

        telemetry.addData("Power", power);
        showEncoders();


    }
}
