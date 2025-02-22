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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.openftc.easyopencv.PipelineRecordingParameters;


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

@TeleOp(name="Tele", group="Linear OpMode")
//@Disabled
public class ArmControl extends LinearOpMode {

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

    private  Servo bucket = null;

    private  Servo wrist = null;
    private Servo wristLeft = null;
    private Servo wristRight = null;
    private  DcMotor bar = null;

    private DcMotor leftBar = null;

    private DcMotor rightBar = null;

    private  DcMotor hang = null;

    private CRServo hook = null;

    private TouchSensor leftBarSensor = null;
    private TouchSensor rightBarSensor = null;



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
        arm = hardwareMap.get(DcMotor.class, "arm");
        bucket = hardwareMap.get(Servo.class, "bucket");
        wrist = hardwareMap.get(Servo.class, "wrist");
        wristLeft = hardwareMap.get(Servo.class, "wristLeft");
        wristRight = hardwareMap.get(Servo.class, "wristRight");

        hook = hardwareMap.get(CRServo.class, "hook");
        hang = hardwareMap.get(DcMotor.class, "hang");


        //rightBar = hardwareMap.get(DcMotor.class, "rightBar");
        //leftBar = hardwareMap.get(DcMotor.class, "leftBar");
        leftBarSensor = hardwareMap.get(TouchSensor.class, "leftBarSensor");
        rightBarSensor = hardwareMap.get(TouchSensor.class, "rightBarSensor");



        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        hang.setDirection(DcMotorSimple.Direction.FORWARD);

        //leftBar.setDirection(DcMotorSimple.Direction.FORWARD);
        //rightBar.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);


        // set other mechanism's direction
        int barTarget = 0;
        int target = 0;
        int hangTarget = 0;
        double rotationTarget = 0;
        boolean rotationOverride = false;
        int scoreMacro = 0;

        hang.setTargetPosition(hangTarget);
        hang.setMode(RunMode.STOP_AND_RESET_ENCODER);
        hang.setMode(RunMode.RUN_TO_POSITION);
        //leftBar.setTargetPosition(barTarget);
        //leftBar.setMode(RunMode.STOP_AND_RESET_ENCODER);
        //leftBar.setMode(RunMode.RUN_TO_POSITION);
        //rightBar.setTargetPosition(barTarget);
        //rightBar.setMode(RunMode.STOP_AND_RESET_ENCODER);
        //rightBar.setMode(RunMode.RUN_TO_POSITION);
        intakeFront.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeBack.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setTargetPosition(target);
        arm.setMode(RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(RunMode.RUN_TO_POSITION);


        //int currentPos = leftBar.getCurrentPosition();
        // set Vars

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();
        boolean score = false;
        int timeTurn = 0;
        boolean turnTime = false;
        wrist.setPosition(0.5);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            arm.setPower(1);
            //leftBar.setMode(RunMode.RUN_TO_POSITION);
            //rightBar.setMode(RunMode.RUN_TO_POSITION);
            double intakePower =  gamepad2.left_trigger - gamepad2.right_trigger;
            double bucketTarget = (-gamepad2.right_stick_x+1);
            double y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = -gamepad1.left_stick_x ; // Counteract imperfect strafing *1.1
            double rx = gamepad1.right_stick_x;
            boolean leftBarDown = leftBarSensor.isPressed();
            boolean rightBarDown = rightBarSensor.isPressed();


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = ((y + x + rx) / denominator);
            double backLeftPower = ((y - x + rx) / denominator);
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;


            if (gamepad2.right_bumper && barTarget < 0){ barTarget += 47;}
            if (gamepad2.left_bumper && barTarget > -5200){ barTarget += -47;}

            if(!rotationOverride){rotationTarget=(gamepad2.right_stick_y*0.87+0.13);}

            if (!rotationOverride){target -= 12*gamepad2.left_stick_y;}
            if (target > 0) {target = 0;}
            if (target < -300) {target = -300;}

            if (gamepad2.x){
                target = -55;
                rotationTarget = 1;
                scoreMacro = 0;
                rotationOverride = true;
            }
            if(rotationOverride){
                arm.setPower(0.3);
                intakePower=-1;
            }

            if (rotationOverride && arm.getCurrentPosition() > -65){
                intakePower = 1;
                rotationTarget = 1;
                scoreMacro++;
                if (scoreMacro==15){
                    target = -220;
                    rotationOverride = false;
                    scoreMacro=0;

                }
            }


            intakeFront.setPower(intakePower);
            intakeBack.setPower(intakePower);
            //leftBar.setPower(1);
            //rightBar.setPower(1);
            hook.setPower(-(gamepad1.right_trigger - gamepad1.left_trigger));
            if (gamepad1.left_bumper){hangTarget+=80;}
            if (gamepad1.right_bumper){hangTarget-=80;}

            hang.setTargetPosition(hangTarget);
            hang.setPower(1);

            if (gamepad2.y){score = true; barTarget = -5200; timeTurn = 0;}

            if (score){
                if (turnTime) { timeTurn+=1;}
                bucketTarget = 0.6;
                if (rightBar.getCurrentPosition() <= -4000 && timeTurn<33){
                    bucketTarget = 0;
                    turnTime = true;
                }
                if (timeTurn > 40){
                    barTarget = 0;
                    bucketTarget = 0.95;
                }
                if (timeTurn > 30 && rightBar.getCurrentPosition()>-15){
                    score = false;
                    turnTime = false;
                }
            }

            //leftBar.setTargetPosition(barTarget);
            //rightBar.setTargetPosition(barTarget);
            if (barTarget == 0){
                //leftBar.setPower(0.5);
                //rightBar.setPower(0.5);

                if(!leftBarDown){
                    //leftBar.setTargetPosition(2000);
                }else{
                    //leftBar.setMode(RunMode.STOP_AND_RESET_ENCODER);
                }
                if(!rightBarDown){
                    //rightBar.setTargetPosition(2000);
                }else{
                    //rightBar.setMode(RunMode.STOP_AND_RESET_ENCODER);
                }
            }

            wrist.setPosition(gamepad2.left_stick_x);
            wristLeft.setPosition((gamepad2.right_stick_y+1)/2);
            wristRight.setPosition(1-(gamepad2.right_stick_y+1)/2);


            bucket.setPosition(bucketTarget);
            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);
            arm.setTargetPosition(target);
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Bar target", barTarget);

            //telemetry.addData("LeftbarPos", leftBar.getCurrentPosition());
            //telemetry.addData("RightbarPos", rightBar.getCurrentPosition());
            telemetry.addData("leftDown", leftBarDown);
            telemetry.addData("rightDown", rightBarDown);
            telemetry.addData("armPos", arm.getCurrentPosition());
            telemetry.addData("Bucket Power", bucketTarget);
            telemetry.addData("target", target);
            telemetry.addData("rotation", rotationTarget);
            telemetry.update();
        }


    }
}
