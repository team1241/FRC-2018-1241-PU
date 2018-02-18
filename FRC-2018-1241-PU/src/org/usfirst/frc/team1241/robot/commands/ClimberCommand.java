package org.usfirst.frc.team1241.robot.commands;

import org.usfirst.frc.team1241.robot.Robot;
import org.usfirst.frc.team1241.robot.utilities.ToggleBoolean;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberCommand extends Command {

	ToggleBoolean toggle = new ToggleBoolean();
	
	public ClimberCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.climber);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		toggle.set(Robot.oi.getToolLeftTrigger());
		
		if(toggle.get()){
			Robot.climber.retractArmPiston();
		} else {
			Robot.climber.extendArmPiston();
		}
		
		Robot.climber.runClimberArm(-Robot.oi.getToolLeftY());
		
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
