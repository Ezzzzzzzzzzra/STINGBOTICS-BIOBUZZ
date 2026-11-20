package org.firstinspires.ftc.teamcode.auto;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.paths.Path;
@Autonomous
public class Auto extends OpMode {
    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(30,10, -90);
    private final Pose pose1 = p.of(30, 58, -90);
    private final Pose pose2 = p.of(.9593291, 16.6222422, 90);
    private final Pose pose3 = p.of(89.80283018, 22.535173, 90);
    private final Pose pose4 = p.of(20.442976, 59.13678, 90);
    private final Pose controlPose1 = p.of(24.1664570, 48.7232704, 45);
    private final Pose controlPose2 = p.of(23.6, 24.6991614, 45);
    private Command autoRoutine() {
        return sequential(
                follow(follower, uno())


        );
    }
    private Path uno() {
        return line(startPose, pose1).linear(-90, -90);

    }
    private Path dos(){
        return line(pose2, pose3).tangent();
    }
    private Path tres(){
        return curve (pose3, controlPose2, pose4).tangent();
    }

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine());

    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();

    }

}
