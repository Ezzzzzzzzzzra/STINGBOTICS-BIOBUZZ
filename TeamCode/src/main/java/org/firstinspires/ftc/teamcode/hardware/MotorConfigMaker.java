package org.firstinspires.ftc.teamcode.hardware;



import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
@Configurable
public class MotorConfigMaker extends OpMode {

    public static double MotorPower = 0.5;
    public static double Aim_angle;
    public static double turret_pos;

    Shooter shooter = new Shooter();
    Robot robot = new Robot();
    ElapsedTime timer = new ElapsedTime();

    Gamepad previousGamepad1 = new Gamepad();
    Gamepad currentGamepad1 = new Gamepad();

    Gamepad previousGamepad2 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    public static boolean shooterOn = true;

    public void init() {
        shooter.init(hardwareMap);

        robot.init(hardwareMap, timer);
        telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);
    }

    @Override
    public void loop() {
        previousGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);

        robot.hood.setPosition(Aim_angle);
        robot.pinion.setPosition(turret_pos);

        if (currentGamepad1.a && !previousGamepad1.a) {
            shooterOn = !shooterOn;
        }

        shooter.setPower(shooterOn ? MotorPower : 0);

        telemetry.addData("Shooter Power", shooter.getPower());
        telemetry.addData("Hood Angle", robot.hood.getPosition());
        telemetry.addData("Target (MotorPower)", MotorPower);
        telemetry.addData("shooterOn", shooterOn);
        telemetry.addData("Aim" , Aim_angle);
        telemetry.update();

    }


}
