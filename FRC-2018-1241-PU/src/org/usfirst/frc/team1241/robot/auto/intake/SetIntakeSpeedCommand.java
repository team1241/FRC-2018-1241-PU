package org.usfirst.frc.team1241.robot.auto.intake;

import org.usfirst.frc.team1241.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetIntakeSpeedCommand extends Command {

	private double timeout;
	private double speed;
	private boolean intake;
	
    public SetIntakeSpeedCommand(boolean intake, double speed, double timeout) {
    	this.timeout = timeout;
    	this.speed = speed;
    	this.intake = intake;
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(intake)
    		Robot.intake.intake(speed);
    	else
    		Robot.intake.outtake(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stop();
    }
}