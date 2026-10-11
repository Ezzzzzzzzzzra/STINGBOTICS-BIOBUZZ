// Import FTC package
package org.firstinspires.ftc.teamcode.TELEOP;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import org.firstinspires.ftc.teamcode.HARDWARE.Robot;

@TeleOp
@Config
public class ServoAdjustments extends OpMode{
    Robot robot = new Robot();
    ElapsedTime timer = new ElapsedTime();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    double screwdoodoo;
    @Override
    // Set starting values for variable
    public void init() {

        robot.init(hardwareMap, timer);
        screwdoodoo = 0;
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

        robot.cheekyBoy.setPosition(screwdoodoo);
    // telemetryA.addData("x-encoder", robot.odometry.getEncoderX());
        // telemetryA.addData("y-encoder", robot.odometry.getEncoderY());
        telemetry.addData("side servo1", robot.toiletpaper1.getPosition());
        telemetry.addData("side servo2", robot.toiletpaper2.getPosition());
        telemetry.addData("pinion", robot.cheekyBoy.getPosition());
        robot.toiletpaper1.setPosition(screwdoodoo);
        robot.toiletpaper2.setPosition(screwdoodoo);

        if (currentGamepad1.b && !previousGamepad1.b) {
           robot.toiletpaper1.setPosition(robot.toiletpaper1.getPosition()+0.2);
        }
        if (currentGamepad1.y && !previousGamepad1.y){
            robot.toiletpaper1.setPosition(robot.toiletpaper1.getPosition()-0.2);
        }

        if (currentGamepad1.left_bumper && !previousGamepad1.left_bumper) {
            robot.toiletpaper2.setPosition(robot.toiletpaper2.getPosition()+0.2);
        }
        if (currentGamepad1.right_bumper && !previousGamepad1.right_bumper){
            robot.toiletpaper2.setPosition(robot.toiletpaper2.getPosition()-0.2);
        }


        //These things MUST be at the end of each loop. DO NOT MOVE
        telemetry.update();

    }

    @Override
    public void stop() {
        super.stop();
    }
}

