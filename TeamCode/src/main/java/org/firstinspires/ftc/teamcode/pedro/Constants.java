package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.OptionalDouble;

public class Constants {
    public static MecanumConfig driveConfig = new MecanumConfig(
            config ->{
                config.frontLeftName.set("motor_fl");
                config.backLeftName.set("motor_bl");
                config.frontRightName.set("motor_fr");
                config.backRightName.set("motor_br");
                config.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                config.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                config.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
                config.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
            }
    );
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.ticksPerUnit.set(OptionalDouble.of(924));
        c.xPodOffset.set(3.59251969);
        c.yPodOffset.set(6.21377953);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.23918631710251337);
                Controller secondaryTranslationalForward = Controller.proportional(0.08837295299327773);
                Controller primaryTranslationalLateral = Controller.proportional(0.36959270468584626);
                Controller secondaryTranslationalLateral = Controller.proportional(0.13655462868246762);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.013555014976905545));
                c.brake.set(Controller.proportionalFeedforward(0.011521762730369712));

                c.headingFeedback.set(Controller.proportional(3.4616881053159694));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.0490441083183722, 0.006488371574679769));

                c.linearBrakeCoefficients.set(Matrix.diag(0.06849023400949317, 0.04883058942239601));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0016152948268170428, 0.0019578166368198644));

                c.maxAchievableForwardVelocity.set(77.42725973366235);
                c.maxAchievableStrafeVelocity.set(65.69908412067915);
                c.naturalForwardDeceleration.set(42.796843853621574);
                c.naturalStrafeDeceleration.set(62.420509569543896);
            }
    );
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, driveConfig),
                new Foresight(foresightConfig)
        );
    }
}