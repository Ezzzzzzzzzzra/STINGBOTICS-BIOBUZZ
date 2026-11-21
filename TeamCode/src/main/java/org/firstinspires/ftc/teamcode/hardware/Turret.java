package org.firstinspires.ftc.teamcode.hardware;



import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Turret {
    public DcMotorEx turret;
    ElapsedTime timer;
    public enum Alliance {BLUE, RED};

    Pose targetGoal;
    public final Pose farGoalRed = new Pose(57.9, 90.2);
    public final Pose closeGoalRed = new Pose(57.9,	51.3);
    public final Pose farGoalBlue = new Pose(83.9,	90.2);
    public final Pose closeGoalBlue = new Pose(83.9, 51.3);

    public void init(HardwareMap hardwareMap, ElapsedTime timer) {
        this.timer = timer;
        turret = hardwareMap.get(DcMotorEx.class, "turret");


        }
        //Map variables to motors
    public void changeTarget(Turret.Alliance color, Follower follower){
        Pose robotPose = follower.pose();
        if (color == Alliance.BLUE){
            if(robotPose.y() > 70.75){
                targetGoal = farGoalBlue;
            }else if(robotPose.y() < 70.75){
                targetGoal = closeGoalBlue;
            }
        }else if(color == Alliance.RED) {
            if (robotPose.y() > 70.75) {
                targetGoal = farGoalRed;
            } else if (robotPose.y() < 70.75) {
                targetGoal = closeGoalRed;
            }
    }

    }
}
