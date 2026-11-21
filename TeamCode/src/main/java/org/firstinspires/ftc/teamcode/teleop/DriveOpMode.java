// Import FTC package
package org.firstinspires.ftc.teamcode.teleop;
// Import FTC classes
import static org.firstinspires.ftc.teamcode.hardware.MathFunctions.toInt;

import org.firstinspires.ftc.teamcode.hardware.MecanumKinematics;

import com.pedropathing.drivetrain.Drivetrain;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
// Import custom-made classes/methods
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
//import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
//import org.firstinspires.ftc.teamcode.hardware.AprilTag;
import org.firstinspires.ftc.teamcode.hardware.MotionProfile1D;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.Shooter;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.pedro.procedures.Tests;

import java.util.function.Function;

@TeleOp
@Config
public class DriveOpMode extends OpMode{
    // Create variables
    double[] motorPowers;
    Robot robot = new Robot();

    Shooter shooter = new Shooter();

    private Follower follower;

    ElapsedTime timer = new ElapsedTime();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad currentGamepad1 = new Gamepad();
    Drivetrain drivetrain;
    Gamepad previousGamepad2 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();

    MotionProfile1D rampFunction = new MotionProfile1D(0.8, 1, 0.4, timer);

    // list of colors and variables
    public enum colors {YELLOW, RED, BLUE, UNKNOWN}

    public enum side {BLUE, RED}

    public side currentSide = side.BLUE;

    public enum state {
        ON,
        OFF,
        REVERSE
    }

    //I am NOT unemployed

    colors detectedColor = colors.UNKNOWN;
    state shooterState = state.OFF;
    state intakeState = state.OFF;
    public double blueVal;
    public double greenVal;
    public double redVal;
    public double position = 0;
    VectorF targetAprilTagPos;
    @Override
    public void init() {
        robot.init(hardwareMap, timer);;
        shooter.init(hardwareMap);


        follower = Constants.create(hardwareMap);

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

        //driving controls
        ManualDrive.driveOrHold(
                follower,
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                -gamepad1.right_stick_x
        );
        follower.update();

        if(shooterState == state.ON){
            shooter.setPower(-.8);
        }else if(shooterState == state.REVERSE){
             shooter.setPower(.8);
        }else{
            shooter.setPower(0);
        }

        if(intakeState == state.ON){
            robot.intake.setPower(1);
        }else if(intakeState == state.REVERSE){
            robot.intake.setPower(-.5);
        }else{
            robot.intake.setPower(0);
        }
        // Robot.odometry.update();
        //  pose = Robot.odometry.getPosition();
        redVal = robot.achintaSensor.red()+0.0000000000000001;
        blueVal = robot.achintaSensor.blue()/redVal;
        greenVal = robot.achintaSensor.green()/redVal;

        if ((Math.abs(currentGamepad1.left_stick_x) > 0.05 || Math.abs(currentGamepad1.left_stick_y) > 0.05) && !(Math.abs(previousGamepad1.left_stick_x) > 0.05 || Math.abs(previousGamepad1.left_stick_y) > 0.05)) {
            rampFunction.reset();
        }
        // Vector2Dim fieldVelocity = new Vector2Dim(robot.odometry.getVelX(DistanceUnit.INCH), robot.odometry.getVelY(DistanceUnit.INCH));
        //Vector2Dim goalVelocity = fieldVelocity.rotateBy((currentSide==side.BLUE ? 1:-1) * (Math.PI - robotToGoalAngle));
        if (blueVal>0.2 && blueVal<0.9 && greenVal > 1.4 && greenVal < 1.6){
            detectedColor = colors.YELLOW;
        } else if (greenVal > 1.5 && greenVal < 2.5 && blueVal > 4 && blueVal < 10) {
            detectedColor = colors.BLUE;

        } else if (greenVal > 0.4 && greenVal < 1.2 && blueVal > 0.2 && blueVal < 0.6)  {
            detectedColor = colors.RED;
        }else{
            detectedColor = colors.UNKNOWN;

        }

        if (currentGamepad1.b && !previousGamepad1.b) {
            if (shooterState != state.REVERSE) {
                shooterState = state.REVERSE;
            } else {
                shooterState = state.OFF;
            }
        }
        if (currentGamepad1.y && !previousGamepad1.y){
            if(intakeState != state.REVERSE){
                intakeState = state.REVERSE;
            }else{
                intakeState = state.OFF;
            }
        }
        if(currentGamepad1.a &&!previousGamepad1.a){
            if (intakeState != state.ON){
                intakeState = state.ON;
            }else{
                intakeState = state.OFF;
            }
        }

        telemetry.addData("gamepadx", currentGamepad1.left_stick_x);
        telemetry.addData("gamepady", currentGamepad1.left_stick_y);
        // Sets power levels
        // Works because each index corresponds with the same wheel in both array

        telemetry.addData("frontLeft", robot.frontLeft.getPower());
        telemetry.addData("backLeft", robot.backLeft.getPower());
        telemetry.addData("frontRight", robot.frontRight.getPower());
        telemetry.addData("backRight", robot.backRight.getPower());
        telemetry.addData("blue#", robot.achintaSensor.blue());
        telemetry.addData("red#", robot.achintaSensor.red());
        telemetry.addData("green#", robot.achintaSensor.green());
        telemetry.addData("Detected Color: ", detectedColor);



        // telemetryA.addData("x-encoder", robot.odometry.getEncoderX());
        // telemetryA.addData("y-encoder", robot.odometry.getEncoderY());


        //These things MUST be at the end of each loop. DO NOT MOVE
        telemetry.update();

    }

    @Override
    public void stop() {
        super.stop();
    }
}

