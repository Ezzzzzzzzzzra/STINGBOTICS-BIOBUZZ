// Import FTC package
package org.firstinspires.ftc.teamcode.TELEOP;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp
@Config
public class ServoAdjustments extends OpMode{
    Robot robot = new Robot();
    ElapsedTime timer = new ElapsedTime();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    public static double servoPos = 0.4;
    @Override

    // Set starting values for variable
    public void init() {

        robot.init(hardwareMap, timer);

        telemetry = new MultipleTelemetry(
                telemetry,
                FtcDashboard.getInstance().getTelemetry()
        );

        telemetry.addLine("Initialized!");
        telemetry.update();


    }
    @Override
    public void loop(){
        previousGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad2.copy(gamepad2);

        robot.hood.setPosition(servoPos);
    // telemetryA.addData("x-encoder", robot.odometry.getEncoderX());
        // telemetryA.addData("y-encoder", robot.odometry.getEncoderY());
        telemetry.addData("side servo1", robot.leftHood.getPosition());
        telemetry.addData("side servo2", robot.rightHood.getPosition());
        telemetry.addData("pinion", robot.pinion.getPosition());

        telemetry.addData("left", robot.leftHood.getPosition());
        telemetry.addData("right", robot.rightHood.getPosition());
        //These things MUST be at the end of each loop. DO NOT MOVE
        telemetry.update();

    }

    @Override
    public void stop() {
        super.stop();
    }
}

