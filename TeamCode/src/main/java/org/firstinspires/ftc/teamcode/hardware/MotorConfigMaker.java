package org.firstinspires.ftc.teamcode.hardware;





import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
@Configurable
public class MotorConfigMaker extends OpMode {

    public static double MotorVelocity = 0;
    public static double hood_angle = 0.3;
    public static double pinion_pos = 0.3;

    Shooter shooter = new Shooter();
    Robot robot = new Robot();
    ElapsedTime timer = new ElapsedTime();

    Gamepad previousGamepad1 = new Gamepad();
    Gamepad currentGamepad1 = new Gamepad();

    Gamepad previousGamepad2 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();

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

        robot.hood.setPosition(hood_angle);
        robot.pinion.setPosition(pinion_pos);
        shooter.setVelocity(MotorVelocity);


        telemetry.addLine("----------------");
        telemetry.addData("Current Velocity", shooter.getVelocity());
        telemetry.addData("Shooter RPM", shooter.getRPM());
        telemetry.addLine("----------------");
        telemetry.addData("Hood" , hood_angle);
        //telemetry.addData("leftHood pos", robot.leftHood.getPosition());
        telemetry.addData("righthood pos", robot.rightHood.getPosition());
        telemetry.addData("lefthood pos", robot.leftHood.getPosition());
        telemetry.addData("pinion position", robot.pinion.getPosition());
        telemetry.update();

    }


}
