package org.usfirst.frc.team1241.robot.auto.drive;

import org.usfirst.frc.team1241.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command used when robot stopping at the end of the motion is not required.
 */
public class DriveUntilDetected extends Command {

	// Variables to hold parameter values
	private double speed;
	private double setPoint;
	private double timeOut;
	private double angle;
	private boolean stop;

	/**
	 * Instantiates a new continous motion.
	 *
	 * @param speed
	 *            Speed robot will travel at (-1.0 to 1.0)
	 * @param setPoint
	 *            The set point in inches
	 * @param timeOut
	 *            The time out in seconds
	 */
	public DriveUntilDetected(double setPoint, double speed, double angle, double timeOut, boolean stop) {
		this.speed = speed;
		this.angle = angle;
		this.setPoint = setPoint;
		this.timeOut = timeOut;
		this.stop = stop;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drive.resetEncoders();
		setTimeout(timeOut);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drive.driveAngle(angle, speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (setPoint >= 0) {
			return isTimedOut() || Robot.drive.getAverageDistance() > setPoint;
		} else {
			return isTimedOut() || Robot.drive.getAverageDistance() < setPoint;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		if (stop){
			Robot.drive.runLeftDrive(0);
			Robot.drive.runLeftDrive(0);
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}