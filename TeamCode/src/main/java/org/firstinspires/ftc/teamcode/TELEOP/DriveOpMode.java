// Import FTC package
package org.firstinspires.ftc.teamcode.TELEOP;
// Import FTC classes
import static org.firstinspires.ftc.teamcode.HARDWARE.MathFunctions.toInt;

import org.firstinspires.ftc.teamcode.HARDWARE.MathFunctions;
import org.firstinspires.ftc.teamcode.HARDWARE.MecanumKinematics;

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
import org.firstinspires.ftc.teamcode.HARDWARE.MotionProfile1D;
import org.firstinspires.ftc.teamcode.HARDWARE.Robot;

@TeleOp
@Config
public class DriveOpMode extends OpMode{
    // Create variables
    double[] motorPowers;
    Robot robot = new Robot();

    ElapsedTime timer = new ElapsedTime();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad currentGamepad1 = new Gamepad();

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
        if(shooterState == state.ON){
            robot.shooter.setPower(.3);
        }else if(shooterState == state.REVERSE){
            robot.shooter.setPower(.3);
        }else{
            robot.shooter.setPower(0);
        }

        if(intakeState == state.ON){
            robot.intake.setPower(.5);
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
        // Gets power levels for each motor, using gamepad inputs as directions
        // The third item in the array dictates which trigger is being pressed (=1 if left, =-1 if right, =0 if none or both).
        motorPowers = MecanumKinematics.getPowerFromDirection(new double[]{                        -gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x),
                        -gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y),
                        -(toInt(gamepad1.right_bumper) - toInt(gamepad1.left_bumper))
                },
                rampFunction.getTargetSpeed()
        );

        telemetry.addData("gamepadx", currentGamepad1.left_stick_x);
        telemetry.addData("gamepady", currentGamepad1.left_stick_y);
        // Sets power levels
        // Works because each index corresponds with the same wheel in both arrays
        for (int i = 0; i < motorPowers.length; i++) {
            robot.driveMotors[i].setPower(motorPowers[i]);
        }

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

