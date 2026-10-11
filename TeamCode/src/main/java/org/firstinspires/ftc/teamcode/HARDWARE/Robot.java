package org.firstinspires.ftc.teamcode.HARDWARE;


import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.acmerobotics.dashboard.config.Config;
@Config
public class Robot {
    //fields
    public DcMotor frontRight;
    public DcMotor frontLeft;
    public DcMotor backRight;
    public DcMotor backLeft;

    public Servo toiletpaper1;

    public Servo toiletpaper2;

    public Servo cheekyBoy;
    public ColorSensor achintaSensor;

    //public Servo angle1;
    //public Servo angle2;

    public DcMotor shooter;
    public DcMotor intake;

    public static final double TURRET_RADIUS = 6.49;

    public enum localizationType {ROADRUNNER, PINPOINT, ROADRUNNERxPINPOINT}

    public localizationType type = localizationType.PINPOINT;

    public DcMotor[] driveMotors;
    ElapsedTime timer;

    IMU imu;

    public static final double[] TURRET_LIMITS = {-162, 150};

    public final RevHubOrientationOnRobot.LogoFacingDirection logoFacingDirection = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
    public final RevHubOrientationOnRobot.UsbFacingDirection usbFacingDirection = RevHubOrientationOnRobot.UsbFacingDirection.UP;


    //order of constants will be P, I, D, F
    public static double[] headingConstants = {0.91, 0, 0, 0};
    public static double[] driveConstants = {0.067, 0, -0.00016, 0};
    public static double[] strafeConstants = {-0.085, 0, 0, 0};

    public static double[] shooterConstants = {0.00076, 0.000003, 0.000085, 0.000445};
    public static double[][] lookup = {
            {10, -1450, 0.95},
            {20, -1380, 0.6},
            {30, -1420, 0.3},
            {40, -1450, 0.3},
            {50, -1540, 0.25},
            {60, -1560, 0.26},
            {70, -1560, 0.26},
            {80, -1650, 0.2},
            {90, -1800, 0.25},
            {120, -2080, 0.05},
            {130, -2100, 0.2}

    };

    public static double speedXConstant = 0;
    public static double speedYConstant = 0;
    public static double distanceConstantMultiplierX = 0;
    public static double distanceConstantMultiplierY = 0;

    //    public AprilTagProcessor aprilTagProcessor;
//
//    public VisionPortal visionPortal;
    public Robot() {
    }

    ;

    public void init(HardwareMap hardwareMap, ElapsedTime timer) {
        this.timer = timer;

        //Map variables to motors
        toiletpaper1 = hardwareMap.get(Servo.class, "tp1");
        toiletpaper2 = hardwareMap.get(Servo.class, "tp2");
        cheekyBoy = hardwareMap.get(Servo.class, "tp3");
        frontLeft = hardwareMap.get(DcMotor.class, "motor_fl");
        frontRight = hardwareMap.get(DcMotor.class, "motor_fr");
        backLeft = hardwareMap.get(DcMotor.class, "motor_bl");
        backRight = hardwareMap.get(DcMotor.class, "motor_br");
        achintaSensor = hardwareMap.get(ColorSensor.class, "achintaSensor");
        //angle1 = hardwareMap.get(Servo.class, "intakeangle1");
        //angle2 = hardwareMap.get(Servo.class, "intakeangle2");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        intake = hardwareMap.get(DcMotor.class, "intake");
        driveMotors = new DcMotor[]{frontLeft, frontRight, backLeft, backRight};

        for (DcMotor i : driveMotors) {
            i.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            i.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        //set motor directions
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        toiletpaper1.setDirection(Servo.Direction.REVERSE);
        toiletpaper2.setDirection(Servo.Direction.FORWARD);
        cheekyBoy.setDirection(Servo.Direction.FORWARD);


    }

    public void setMotorPowers(double[] motorPowers) {
        frontLeft.setPower(motorPowers[0]);
        frontRight.setPower(motorPowers[1]);
        backLeft.setPower(motorPowers[2]);
        backRight.setPower(motorPowers[3]);
    }
}