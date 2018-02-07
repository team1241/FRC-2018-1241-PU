package org.usfirst.frc.team1241.robot.commands;

import org.usfirst.frc.team1241.robot.Robot;
import org.usfirst.frc.team1241.robot.subsystems.LEDstrips;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Kaveesha Siribaddana
 * @since 10/01/2018
 */
public class IntakeCommand extends Command {

	public IntakeCommand() {
		requires(Robot.intake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		if (Robot.oi.getToolBackButton()) {
			Robot.intake.spinCube();
		} else if (Robot.oi.getToolLeftButton()) {
			Robot.intake.outtake();
		} else if (Robot.oi.getToolRightButton()) {
			Robot.intake.intake();
		} else {
			Robot.intake.stop();
		}

		if (Robot.intake.getOptic()) {
			LEDstrips.solidBlue();
		} else {
			LEDstrips.solidGreen();
		}

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
