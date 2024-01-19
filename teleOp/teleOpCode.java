package org.firstinspires.ftc.teamcode.teleOp;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp (name = "TeleOp")
public class teleOpCode extends LinearOpMode {
    /**
     * ALL THE VARIABLES
     */

    DcMotor AM, HM, CM, SM; // All of the motors
    double ticks = 384.5;
    double ticks2 = 444;
    double newTarget;
    Servo Claw;

    double BRSpeed = 0.75;
    double BLSpeed = 2;
    double speed = 1;
    double halfspeed = 0.5; //This was for the faster moving parts (Chain motor)
    double openClaw = 0.001;
    double closeClaw = 1;

    double ticksHar = 537.7;
    double turn = ticksHar / 2;
    double Actuatorticks = 537;

    @Override
    public void runOpMode() throws InterruptedException {
        // Assigning all of the servos and motors
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        CM = hardwareMap.dcMotor.get("ch");
        HM = hardwareMap.dcMotor.get("hoist");
        AM = hardwareMap.dcMotor.get("actuator");
        SM = hardwareMap.dcMotor.get("Slide");
        Claw = hardwareMap.servo.get("Claw");


        telemetry.addData(">", "Press Play to start op mode"); // Will add stuff to the driver hub screen
        telemetry.update(); // Will update the driver hub screen so that the above will appear

        waitForStart(); // When the start button is pressed

        if (opModeIsActive()) {

            while (opModeIsActive()) {
                /**
                 * ALL UNDER GAMEPAD 1
                 */
                CM.setPower(gamepad2.right_stick_y * halfspeed); //THESE ARE THE OLD GAMEPAD 2 CONTROLS

                SM.setPower(gamepad2.left_stick_y * -speed); //THESE ARE THE  OLD GAMEPAD 2 CONTROLS

                if (gamepad1.y) {
                    SM.setPower(speed);
                } else if (gamepad1.a) {
                    SM.setPower(-speed);
                } else {
                    SM.setPower(0);
                }
                // Drive Train

                if (gamepad1.right_stick_x != 0 || gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    -gamepad1.left_stick_y,
                                    -gamepad1.left_stick_x,
                                    -gamepad1.right_stick_x
                            )
                    );

                    drive.update();

                    Pose2d poseEstimate = drive.getPoseEstimate();
                    telemetry.addData("x", poseEstimate.getX());
                    telemetry.addData("y", poseEstimate.getY());
                    telemetry.addData("heading", poseEstimate.getHeading());
                    telemetry.update();
                } else {
                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    0,
                                    0,
                                    0
                            )
                    );
                }


                if (gamepad1.dpad_up) {
                    HM.setTargetPosition((int) 537.7/4);
                    HM.setPower(0.25);
                    HM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    while(HM.isBusy()) {
                    }
                    HM.setPower(0);

                    AM.setTargetPosition((int) Actuatorticks);
                    AM.setPower(0.5);
                    AM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    while (AM.isBusy()) {

                    }
                    AM.setPower(0);
                } else if (gamepad1.dpad_down) {
                    AM.setTargetPosition(0);
                    AM.setPower(0.3);
                    AM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    while (AM.isBusy()) {
                    }
                    AM.setPower(0);
                } else {
                    AM.setPower(0);
                }
                /*if (gamepad1.dpad_left) {
                    HM.setTargetPosition((int) 537.7/4);
                    HM.setPower(0.25);
                    HM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    while(HM.isBusy()) {
                    }
                    HM.setPower(0);

                    AM.setTargetPosition((int) Actuatorticks);
                    AM.setPower(0.5);
                    AM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    while (AM.isBusy()) {

                    }
                    AM.setPower(0);
                } else if (gamepad1.dpad_right) {
                    HM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    HM.setTargetPosition(0);
                    HM.setPower(0.5);
                    HM.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    while (HM.isBusy()) {
                    }
                    HM.setPower(0);

                    HM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                } else {
                    HM.setPower(0);
                }*/

                /**
                 * ALL UNDER GAMEPAD 2
                 */

                if (gamepad2.left_bumper) {         //gamepad1.left_trigger > 0 OLD GAMEPAD 2 CONTROLS
                    Claw.setPosition(openClaw);
                }
                if (gamepad2.right_bumper) {       //gamepad1.right_bumper > 0  OLD GAMEPAD 2 CONTROLS
                    Claw.setPosition(closeClaw);
                }
            }
        }
    }
}


