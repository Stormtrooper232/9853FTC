/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


/*
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="Auto", group="Linear OpMode")
//@Disabled
public class Auto extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    // drive motors
    private DcMotor backRight = null;
    private DcMotor backLeft = null;
    private DcMotor frontRight = null;
    private DcMotor frontLeft = null;

    // arm motors
    private DcMotor arm = null;
    private DcMotor extension = null;

    // servo

    private  CRServo intakeFront = null;
    private  CRServo intakeBack = null;

    private  CRServo bucket = null;

    private  CRServo rotation = null;
    private  DcMotor bar = null;

    private DcMotor leftBar = null;

    private DcMotor rightBar = null;

    private  DcMotor hang = null;

    private CRServo hook = null;



    /*public double runToEncoder(int target){
        if (Math.abs(armEncoder.getCurrentPosition() - target) < 10){
            return 0;
        }

        double speed = 0;
        if (Math.abs(armEncoder.getCurrentPosition() - target) > 300){
            speed = 1;
        } else if (Math.abs(armEncoder.getCurrentPosition() - target) > 200) {
            speed = 0.5;
        } else if (Math.abs(armEncoder.getCurrentPosition() - target) > 100) {
            speed = 0.2;
        }

        if (armEncoder.getCurrentPosition() < target){

            return speed;
        }

        if (armEncoder.getCurrentPosition() > target){
            return -1*speed;
        }

        return 0;
    }*/


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        intakeFront = hardwareMap.get(CRServo.class, "intakeFront");
        intakeBack = hardwareMap.get(CRServo.class, "intakeBack");
        bar = hardwareMap.get(DcMotor.class, "bar");
        bucket = hardwareMap.get(CRServo.class, "bucket");
        rotation = hardwareMap.get(CRServo.class, "rotation");

        hook = hardwareMap.get(CRServo.class, "hook");
        hang = hardwareMap.get(DcMotor.class, "hang");

        rightBar = hardwareMap.get(DcMotor.class, "rightBar");
        leftBar = hardwareMap.get(DcMotor.class, "leftBar");


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        rotation.setDirection(DcMotorSimple.Direction.FORWARD);
        hang.setDirection(DcMotorSimple.Direction.FORWARD);

        leftBar.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBar.setDirection(DcMotorSimple.Direction.FORWARD);


        // set other mechanism's direction
        int barTarget = 0;
        int target = 0;
        leftBar.setTargetPosition(barTarget);
        leftBar.setMode(RunMode.STOP_AND_RESET_ENCODER);
        leftBar.setMode(RunMode.RUN_TO_POSITION);
        rightBar.setTargetPosition(barTarget);
        rightBar.setMode(RunMode.STOP_AND_RESET_ENCODER);
        rightBar.setMode(RunMode.RUN_TO_POSITION);
        intakeFront.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeBack.setDirection(DcMotorSimple.Direction.REVERSE);
        bar.setTargetPosition(target);
        bar.setMode(RunMode.STOP_AND_RESET_ENCODER);
        bar.setMode(RunMode.RUN_TO_POSITION);


        int currentPos = leftBar.getCurrentPosition();
        // set Vars

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();
        int timer = 0;
        boolean turnTime = false;
        int timeTurn = 0;
        boolean score = false;
        boolean raise = false;



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            timer+=1;






            // robot movement set code
            double y = 0; // Remember, Y stick value is reversed
            double x = 0 ; // Counteract imperfect strafing *1.1
            double rx = 0;
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]


            double intakePower =  0;
            double bucketPower = 0;


            intakeFront.setPower(intakePower);
            intakeBack.setPower(intakePower);
            bar.setTargetPosition(target);
            bar.setPower(1);
            leftBar.setPower(1);
            rightBar.setPower(1);
            rotation.setPower(0);
            hook.setPower(0);

            leftBar.setTargetPosition(barTarget);
            rightBar.setTargetPosition(barTarget);

            if (raise){score = true; barTarget = -5200; timeTurn = 0; }
            raise = false;

            if (score){
                if (turnTime) { timeTurn+=1;}
                if (leftBar.getCurrentPosition() <= -5100){
                    bucketPower = -1;
                    turnTime = true;
                }
                if (timeTurn > 31){
                    bucketPower = 1;
                }
                if (timeTurn > 40){
                    barTarget = 0;
                }
                if (timeTurn > 66){
                    score = false;
                    turnTime = false;
                }
            }


            // step by step

            // move left
            if (timer < 30) {
                y = -0.5;
                target = 105;
            } else if (timer < 90) {
                x = 0.5;
            } else if (timer < 120) {
                rx = 0.355;
            } else if (timer < 156) {
                y=0.55;
            } else if (timer<162) {
                x=-0.4;
            } else if (timer == 162) {
                raise = true;
            } else if (timer > 200 && leftBar.getCurrentPosition() > -100) {
                target = 0;
            }


            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = ((y + x + rx) / denominator);
            double backLeftPower = ((y - x + rx) / denominator);
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;
            bucket.setPower(bucketPower);
            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Bar Pos", bar.getCurrentPosition());
            telemetry.addData("Bar target", barTarget);
            telemetry.addData("rightBar", rightBar.getCurrentPosition());
            telemetry.addData("rightBar", rightBar.getTargetPosition());
            telemetry.addData("barPos", target);

            telemetry.addData("speed", currentPos-leftBar.getCurrentPosition());

            telemetry.update();
        }


    }
}
