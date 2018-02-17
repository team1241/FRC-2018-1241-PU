package org.usfirst.frc.team1241.robot.commands;

import org.usfirst.frc.team1241.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberCommand extends Command {
	
    public ClimberCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.climb);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(Robot.oi.getToolStartButton()){
    		Robot.climb.extendPTOPiston();
    		Robot.climb.setPtoHanger(true);
    	} else if(Robot.oi.getToolBackButton())
    		Robot.climb.retractPTOPiston();
    	
    	if(Robot.oi.getToolRightTrigger()){
    		Robot.climb.extendArmPiston();
    	} else if(Robot.oi.getToolLeftTrigger())
    		Robot.climb.retractArmPiston();
    	
    	/*if(Robot.oi.getToolBackButton() && Robot.climb.ptoPosistionHanger()){
    		Robot.drive.runWinchPTO(1);
    	} else {
    		Robot.drive.runWinchPTO(0);
    	}*/

    	//if(Robot.climb.ptoPosistionHanger()){
    		Robot.climb.runClimberArm(-Robot.oi.getToolLeftY());
    	//}
    	
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
