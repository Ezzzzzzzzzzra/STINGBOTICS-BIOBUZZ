
@TeleOp
public class ShooterEncoderTest extends Op {
    DcMotorEx left;
    DcMotorEx right;



    @Override
    public void init() {
        left  = hardwareMap.get(DcMotorEx.class, "shootLeft");
        right = hardwareMap.get(DcMotorEx.class, "shootRight");

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void init_loop() {
        showEncoders();
    }

    @Override
    public void loop() {
        double power = gamepad1.right_trigger * 0.5;
        left.setPower(power);
        right.setPower(power);

        telemetry.addData("Power", "%.2f", power);
        showEncoders();
    }

    private void showEncoders() {
        telemetry.addLine("--- shootLeft ---");
        telemetry.addData("Port", left.getConnectionInfo());
        telemetry.addData("Position (ticks)", left.getCurrentPosition());
        telemetry.addData("Velocity (ticks/s)", "%.1f", left.getVelocity());

        telemetry.addLine("--- shootRight ---");
        telemetry.addData("Port", right.getConnectionInfo());
        telemetry.addData("Position (ticks)", right.getCurrentPosition());
        telemetry.addData("Velocity (ticks/s)", "%.1f", right.getVelocity());
    }
}