package org.usfirst.frc.team1241.robot.auto.drive;

import org.usfirst.frc.team1241.robot.NumberConstants;
import org.usfirst.frc.team1241.robot.Robot;
import org.usfirst.frc.team1241.robot.utilities.BezierCurve;
import org.usfirst.frc.team1241.robot.utilities.Point;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command used to allow robot to travel a path generated using Quintic Bezier curves
 * 
 */
public class QuinticBezierDrivePath extends Command {

	// Create a Bezier curve object
	private BezierCurve curve;

	// Variables to store parameter information
	private int counter;
	private double distance;
	private double timeOut;
	private double speed;
	private boolean reverse;

	/**
	 * Instantiates a new drive path.
	 *
	 * @param startPoint
	 *            The start point
	 * @param controlPoint1
	 *            The control point 1
	 * @param controlPoint2
	 *            The control point 2
	 *  @param controlPoint1
	 *            The control point 3
	 * @param controlPoint2
	 *            The control point 4
	 * @param endPoint
	 *            The end point
	 * @param size
	 *            The total number of points/segments
	 * @param timeOut
	 *            The time out in seconds
	 * @param speed
	 *            The speed the robot will travel at (0.0 - 1.0)
	 */
	public QuinticBezierDrivePath(Point startPoint, Point controlPoint1, Point controlPoint2,Point controlPoint3,Point controlPoint4, Point endPoint, int size, double segmentStep, double timeOut,
			double speed) {

		this(startPoint, controlPoint1, controlPoint2, controlPoint3, controlPoint4, endPoint, size, segmentStep, timeOut, speed, false);
	}

	/**
	 * Instantiates a new drive path.
	 *
	 * @param startPoint
	 *            The start point
	 * @param controlPoint1
	 *            The control point 1
	 * @param controlPoint2
	 *            The control point 2
	 * @param controlPoint1
	 *            The control point 3
	 * @param controlPoint2
	 *            The control point 4
	 * @param endPoint
	 *            The end point
	 * @param size
	 *            The total number of points/segments
	 * @param timeOut
	 *            The time out in seconds
	 * @param speed
	 *            The speed the robot will travel at (0.0 - 1.0)
	 * @param reverse
	 *            True if robot will traverse path in reverse, otherwise false
	 */
	public QuinticBezierDrivePath(Point startPoint, Point controlPoint1, Point controlPoint2,Point controlPoint3,Point controlPoint4, Point endPoint, int size, double segmentStep, double timeOut,
			double speed, boolean reverse) {

		curve = new BezierCurve(startPoint, controlPoint1, controlPoint2,controlPoint3, controlPoint4, endPoint, size, segmentStep);
		distance = curve.findArcLength();
		this.timeOut = timeOut;
		this.speed = speed;
		this.reverse = reverse;
		requires(Robot.drive);
	}

	// Initialize the command by reseting encoders and setting time out
	protected void initialize() {
		counter = 0;
		setTimeout(timeOut);
		Robot.drive.changeDriveGains(NumberConstants.pDrive, NumberConstants.iDrive, NumberConstants.dDrive);
    	Robot.drive.changeGyroGains(NumberConstants.pDriveGyro, NumberConstants.iDriveGyro, NumberConstants.dDriveGyro);
		Robot.drive.resetEncoders();
	}

	// Give set distance for robot to travel, at each point change angle to
	// point towards next coordinate
	protected void execute() {
		if (reverse) {
			if (-Robot.drive.getAverageDistance() > curve.findHypotenuse(counter) && counter <= curve.size())
				counter++;

			Robot.drive.driveSetpoint(-distance, speed, curve.findAngle(counter), 1);
		} else {
			if (Robot.drive.getAverageDistance() > curve.findHypotenuse(counter) && counter < curve.size())
				counter++;

			Robot.drive.driveSetpoint(distance, speed, curve.findAngle(counter), 1);
		}
	
	}

	// Command is finished when average distance = total distance or command
	// times out
	protected boolean isFinished() {
		return Robot.drive.getAverageDistance() >= distance||isTimedOut();
	}

	// At the end, stop drive motors
	protected void end() {
		Robot.drive.runLeftDrive(0);
		Robot.drive.runRightDrive(0);
	}

	protected void interrupted() {
	}
}