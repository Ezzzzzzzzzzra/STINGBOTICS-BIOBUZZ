package org.firstinspires.ftc.teamcode.hardware;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import java.util.ArrayList;
import java.util.List;

public class AprilTag {
    public AprilTagProcessor aprilTagProcessor;
    public VisionPortal visionPortal;
    public List<AprilTagDetection> detectedTags = new ArrayList<>();
    public Telemetry telemetry;

    /**
     * Variables to store the position and orientation of the camera on the robot. Setting these
     * values requires a definition of the axes of the camera and robot:
     *
     * Camera axes:
     * Origin location: Center of the lens
     * Axes orientation: +x right, +y down, +z forward (from camera's perspective)
     *
     * Robot axes (this is typical, but you can define this however you want):
     * Origin location: Center of the robot at field height
     * Axes orientation: +x right, +y forward, +z upward
     *
     * Position:
     * If all values are zero (no translation), that implies the camera is at the center of the
     * robot. Suppose your camera is positioned 5 inches to the left, 7 inches forward, and 12
     * inches above the ground - you would need to set the position to (-5, 7, 12).
     *
     * Orientation:
     * If all values are zero (no rotation), that implies the camera is pointing straight up. In
     * most cases, you'll need to set the pitch to -90 degrees (rotation about the x-axis), meaning
     * the camera is horizontal. Use a yaw of 0 if the camera is pointing forwards, +90 degrees if
     * it's pointing straight left, -90 degrees for straight right, etc. You can also set the roll
     * to +/-90 degrees if it's vertical, or 180 degrees if it's upside-down.
     */
    private Position cameraPosition = new Position(DistanceUnit.INCH,
            0, 0, 12.99, 0);
    private YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
            0, -70, 0, 0);


    public void init(HardwareMap hardwareMap, Telemetry telemetry){
        this.telemetry = telemetry;

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .setCameraPose(cameraPosition, cameraOrientation)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "cameraOfDoom"));
        builder.setCameraResolution(new Size(640, 480));
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();
    }

    public void update(){
        detectedTags = aprilTagProcessor.getDetections();
    }

    public List<AprilTagDetection> getDetectedTags(){
        return detectedTags;
    }

    public void displayTelemetry(AprilTagDetection detection) {

        if (detection == null) return;

        if (detection instanceof AprilTagSingleDetection) {

            AprilTagSingleDetection single =
                    (AprilTagSingleDetection) detection;

            if (single.metadata != null) {

                telemetry.addLine(String.format(
                        "\n==== (ID %d) %s",
                        single.id,
                        single.metadata.name
                ));

                telemetry.addLine(String.format(
                        "XYZ %6.1f %6.1f %6.1f (inch)",
                        detection.ftcPose.x,
                        detection.ftcPose.y,
                        detection.ftcPose.z
                ));

                telemetry.addLine(String.format(
                        "PRY %6.1f %6.1f %6.1f (deg)",
                        detection.ftcPose.pitch,
                        detection.ftcPose.roll,
                        detection.ftcPose.yaw
                ));

                telemetry.addLine(String.format(
                        "RBE %6.1f %6.1f %6.1f",
                        detection.ftcPose.range,
                        detection.ftcPose.bearing,
                        detection.ftcPose.elevation
                ));

            } else {

                telemetry.addLine(String.format(
                        "\n==== (ID %d) Unknown",
                        single.id
                ));
            }

        } else if (detection instanceof AprilTagClusterDetection) {

            AprilTagClusterDetection cluster =
                    (AprilTagClusterDetection) detection;

            telemetry.addLine("AprilTag Cluster detected");
        }
    }

    // Find an individual AprilTag by ID
    public AprilTagSingleDetection getTagByID(int id) {

        if (detectedTags == null) return null;

        for (AprilTagDetection detection : detectedTags) {

            if (detection instanceof AprilTagSingleDetection) {

                AprilTagSingleDetection single =
                        (AprilTagSingleDetection) detection;

                if (single.id == id) {
                    return single;
                }
            }
        }

        return null;
    }

    // Close the camera
    public void stop() {

        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}