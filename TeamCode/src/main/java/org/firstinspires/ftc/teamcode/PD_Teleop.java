package org.firstinspires.ftc.teamcode;

//Import FTC modules
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
        import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
        import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
        import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
        import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

//Import Gyro


//Define as teleop
@TeleOp(name = "PD_Teleop", group = "Linear Opmode")
@Disabled


public class PD_Teleop extends Team6475Controls {

    //Declare runtime variable
    private ElapsedTime runtime = new ElapsedTime();

    //Instantiate hardware links



    public static final double JEWEL_ROTATION_AMOUNT = 0.005;

    //Inilize Encoder


    // Gyro
    //private DcMotor liftMotor;


    //Define opmode
    @Override public void runOpMode() {

        //This code runs immediately after the "init" button is pressed.
        //Inform the user that the opmode has been initialized.
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Initialize hardware variables by paring them to motors in
        //"hardwareMap"
        //Begin with the chassis



       // liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");


        //liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Initialize servos

        //left_intake = hardwareMap.get(Servo.class, "left_intake");
        //right_intake = hardwareMap.get(Servo.class, "right_intake");
        telemetry.addData("Status", "Initializing motors");

        //Gyro
        BNO055IMU.Parameters parametersG = new BNO055IMU.Parameters();
        parametersG.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parametersG.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parametersG.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parametersG.loggingEnabled      = true;
        parametersG.loggingTag          = "IMU";
        parametersG.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parametersG);


        //Reverse the direction of the right motors so the robot drives
        //forward when all motors are set to 1.0
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor2.setDirection(DcMotor.Direction.REVERSE);
        //Reverse the lifting motor so that it goes up when the power is positive
       // liftMotor.setDirection(DcMotor.Direction.REVERSE);

        //Inform the user that he needs to press the play button
        telemetry.addData("Status", "Waiting for play button");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Inform the user of the current time
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Left Trigger", gamepad2.left_trigger );
            //Control the chassis
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double angle;
            angle = AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle));
            telemetry.addData("angle", angle);
            telemetry.update();

            if (gamepad1.left_trigger < .5 && gamepad1.right_trigger < .5) {
                if ( 0 <= angle && angle <= 180) {

                    leftMotor.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x));
                    leftMotor2.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x));
                    rightMotor2.setPower((gamepad1.left_stick_y + gamepad1.right_stick_x));
                    rightMotor.setPower((gamepad1.left_stick_y + gamepad1.right_stick_x));

                }
                if ( 0 > angle && angle > -180) {

                    leftMotor.setPower((-gamepad1.left_stick_y - gamepad1.right_stick_x));
                    leftMotor2.setPower((-gamepad1.left_stick_y - gamepad1.right_stick_x));
                    rightMotor2.setPower((-gamepad1.left_stick_y + gamepad1.right_stick_x));
                    rightMotor.setPower((-gamepad1.left_stick_y + gamepad1.right_stick_x));

                }
            }
            if (gamepad1.left_trigger > .5 && gamepad1.right_trigger < .5) {

                leftMotor.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x));
                leftMotor2.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x));
                rightMotor2.setPower((gamepad1.left_stick_y + gamepad1.right_stick_x));
                rightMotor.setPower((gamepad1.left_stick_y + gamepad1.right_stick_x));

            }



            if (gamepad1.left_trigger < .5 && gamepad1.right_trigger > .5 ) {
                if ( 0 <= angle && angle <= 180) {

                    leftMotor.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x)/2);
                    leftMotor2.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x)/2);
                    rightMotor2.setPower((gamepad1.left_stick_y + gamepad1.right_stick_x)/2);
                    rightMotor.setPower((gamepad1.left_stick_y + gamepad1.right_stick_x)/2);

                }
                if ( 0 > angle && angle > -180) {

                    leftMotor.setPower((-gamepad1.left_stick_y - gamepad1.right_stick_x)/2);
                    leftMotor2.setPower((-gamepad1.left_stick_y - gamepad1.right_stick_x)/2);
                    rightMotor2.setPower((-gamepad1.left_stick_y + gamepad1.right_stick_x)/2);
                    rightMotor.setPower((-gamepad1.left_stick_y + gamepad1.right_stick_x)/2);

                }
            }
            if (gamepad1.left_trigger > .5 && gamepad1.right_trigger > .5) {

                leftMotor.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x)/2);
                leftMotor2.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x)/2);
                rightMotor2.setPower((gamepad1.left_stick_y + gamepad1.right_stick_x)/2);
                rightMotor.setPower((gamepad1.left_stick_y + gamepad1.right_stick_x)/2);

            }
            //Add telemetry data
        //    telemetry.addData("Lift motor power", liftMotor.getPower());
            telemetry.addData("Left motor power", leftMotor.getPower());
            telemetry.addData("Right motor power", leftMotor.getPower());

            //Jewel servo mechanism
            if (gamepad1.a) {
                blueColorServo.setPosition(.3);
            }
            if (gamepad1.a) {
                jewelRotationServo.setPosition(.45);
            }
            if (gamepad2.x) {
                jewelRotationServo.setPosition(.45);
            }


            //Lifting mechanism
            //Stop the lifting motor if it isn't busy
            /* if(!liftMotor.isBusy()) {
                liftMotor.setPower(0.0);
            }
            //Set the target position to base position
            if(gamepad2.a){
                liftMotor.setTargetPosition(10);
                if(liftMotor.isBusy()) liftMotor.setPower(0.5);
            }
            //Set the target position to the first block
            if(gamepad2.b){
                liftMotor.setTargetPosition(1570);
                if(liftMotor.isBusy()) liftMotor.setPower(0.5);
            }
            //Set the target position to the second (highest) block
            if(gamepad2.y){
                liftMotor.setTargetPosition(2390);
                if(liftMotor.isBusy()) liftMotor.setPower(0.5);
            }*/

            //TODO Change drop motor to glyph drop code
            /*
            //Glyph dropping mechanism
            if(!dropMotor.isBusy()) dropMotor.setPower(0.0);
            if(gamepad2.dpad_up) {
                dropMotor.setTargetPosition(400);
                dropMotor.setPower(0.25);
            }
            if (gamepad2.dpad_down){
                dropMotor.setTargetPosition(0);
                dropMotor.setPower(-0.25);
            }
            if(gamepad2.dpad_right) {
                int dropPos = dropMotor.getCurrentPosition();
                dropMotor.setTargetPosition(dropPos + 50);
                dropMotor.setPower(0.25);
            }
            if (gamepad2.dpad_left){
                int dropPos = dropMotor.getCurrentPosition();
                dropMotor.setTargetPosition(dropPos - 50);
                dropMotor.setPower(-0.25);
            }
            //Drop motor
            if(gamepad2.y){
                dropMotor.setTargetPosition(400);
                if(dropMotor.isBusy()) dropMotor.setPower(0.1);
            }
            if(gamepad2.a){
                dropMotor.setTargetPosition(0);
                if(dropMotor.isBusy()) dropMotor.setPower(0.1);
            }
            if(gamepad2.b){
                dropMotor.setTargetPosition(400);
                if(dropMotor.isBusy()) dropMotor.setPower(0.1);
            }
            */
            //add some debug data
            telemetry.addData("Buttons",(gamepad1.a?"A":"-")+(gamepad1.b?"B":"-")+(gamepad1.x?"X":"-")+(gamepad1.y?"Y":"-"));
            telemetry.addData("Dpad",(gamepad1.dpad_left?"L":"-")+(gamepad1.dpad_right?"R":"-")+(gamepad1.dpad_down?"D":"-")+(gamepad1.dpad_up?"U":"-"));
            telemetry.addData("Servo Position", jewelRotationServo.getPosition());
            //telemetry.addData("Lift Position", liftMotor.getCurrentPosition());
            //telemetry.addData("Lift Target", liftMotor.getTargetPosition());
            //telemetry.addData("Lift status", liftMotor.isBusy()?"I'm busy":"I got free time.");

            //Update the telemetry
            telemetry.update();
        }

        telemetry.addData("Status", "Done");
        telemetry.update();

    }
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newTarget;
        // Ensure that the opmode is still active liftMotor
        if (opModeIsActive()) {
/*
            // Determine new target position, and pass to motor controller
            newTarget = liftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            liftMotor.setTargetPosition(newTarget);

            // Turn On RUN_TO_POSITION
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            liftMotor.setPower(Math.abs(speed));
*/
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
         /*   while (opModeIsActive() && (runtime.seconds() < timeoutS) && (liftMotor.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1",  "Running to", newTarget);
                telemetry.addData("Path2",  "Running at", liftMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            liftMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sleep(1000);   // optional pause after each move

            telemetry.addData("Done?", "Done" );
            telemetry.update();

        */
        }
        }

    }


