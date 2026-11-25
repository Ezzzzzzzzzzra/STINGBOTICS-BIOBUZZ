package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/*
 * Moves one servo (or two together) with the D-pad so you can find the positions you want.
 *
 * Set SERVO_NAME to the servo's name in the robot configuration (e.g. "pinion").
 * Optional: set SERVO_NAME_2 to a second servo to move it to the SAME position at the same time.
 *           Leave it as "" to use only one servo.
 *
 * Gamepad 1:
 *   D-pad up / down     -> position + / - step
 *   D-pad right / left  -> bigger / smaller step
 *
 * The servos don't move until you press D-pad up or down,
 * so they won't jump somewhere unexpected when you press START.
 */
@TeleOp(name = "Servo Adjustments")
public class ServoAdjustments extends OpMode {

    // CHANGE THIS to the servo's name in the robot configuration
    public static String SERVO_NAME = "leftHood";

    // OPTIONAL second servo, gets the same position as the first. "" = not used
    public static String SERVO_NAME_2 = "rightHood";

    // Set true if the second servo is mounted mirrored and needs to turn the other way
    public static boolean REVERSE_2 = false;

    // Used only if the servo has no known position yet
    public static double START_POSITION = 0;

    Servo servo;
    Servo servo2; // stays null if SERVO_NAME_2 is ""

    double position;
    boolean hasMoved = false;

    double[] steps = {0.001, 0.005, 0.01, 0.05, 0.1};
    int stepIdx = 2; // starts at 0.01

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, SERVO_NAME);

        if (!SERVO_NAME_2.isEmpty()) {
            servo2 = hardwareMap.get(Servo.class, SERVO_NAME_2);
            servo2.setDirection(REVERSE_2 ? Servo.Direction.REVERSE : Servo.Direction.FORWARD);
        }

        // Continue from wherever the first servo was last told to go, if the hub knows it
        double current = servo.getPosition();
        position = Double.isNaN(current) ? START_POSITION : current;

        telemetry.addData("Servo", SERVO_NAME);
        if (servo2 != null) telemetry.addData("Servo 2", SERVO_NAME_2);
        telemetry.addLine("Press START, then use the D-pad");
    }

    @Override
    public void loop() {
        // Change step size
        if (gamepad1.dpadRightWasPressed() && stepIdx < steps.length - 1) stepIdx++;
        if (gamepad1.dpadLeftWasPressed() && stepIdx > 0) stepIdx--;
        double step = steps[stepIdx];

        // Move the servo(s)
        if (gamepad1.dpadUpWasPressed()) {
            position += step;
            hasMoved = true;
        }
        if (gamepad1.dpadDownWasPressed()) {
            position -= step;
            hasMoved = true;
        }

        // Servo positions only go from 0 to 1
        position = Range.clip(position, 0, 1);

        if (hasMoved) {
            servo.setPosition(position);
            if (servo2 != null) servo2.setPosition(position);
        }

        telemetry.addData("Servo", SERVO_NAME);
        if (servo2 != null) telemetry.addData("Servo 2", SERVO_NAME_2 + (REVERSE_2 ? " (reversed)" : ""));
        telemetry.addData("Position", hasMoved ? String.format("%.3f", position) : "not moved yet");
        telemetry.addData("Step", "%.3f", step);
        telemetry.addLine();
        telemetry.addLine("D-pad up/down: move   D-pad left/right: step size");
    }
}
