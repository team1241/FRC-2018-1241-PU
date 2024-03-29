package org.usfirst.frc.team1241.robot.auto.bc;

import org.usfirst.frc.team1241.robot.NumberConstants;
import org.usfirst.frc.team1241.robot.auto.drive.DriveCommand;
import org.usfirst.frc.team1241.robot.auto.drive.EncoderWaitCommand;
import org.usfirst.frc.team1241.robot.auto.drive.ExecuteAfterDistance;
import org.usfirst.frc.team1241.robot.auto.drive.TurnCommand;
import org.usfirst.frc.team1241.robot.auto.elevator.ElevatorSetpoint;
import org.usfirst.frc.team1241.robot.auto.intake.IntakePistonCommand;
import org.usfirst.frc.team1241.robot.auto.intake.SetIntakeSpeedCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightSwitchDouble extends CommandGroup {

    public RightSwitchDouble(int position) {
    	if (position == 0) {
    		addParallel(new IntakePistonCommand(false));
			addParallel(new SetIntakeSpeedCommand(true, 0.75, 1));
			//addSequential (new DriveCommand (70, 0.8, 20, 2.75, 60, 90, 0.8));
			addSequential (new DriveCommand (60, 1, 0, 2.75));
			addSequential(new TurnCommand(90, 0.75, 2, 3));
			addSequential (new DriveCommand (175, 1, 90, 2.75));
			addSequential(new TurnCommand(0, 0.75, 2, 3));
			addParallel(new ElevatorSetpoint(NumberConstants.switchPosition, NumberConstants.maxElevatorSpeed, 1, 2));
			addSequential (new DriveCommand (50, 1, 0, 2.75));
			addSequential(new SetIntakeSpeedCommand(false, 0.75, 1));
			
			addSequential (new DriveCommand (-100, 1, 30, 2.75));
			addParallel(new ElevatorSetpoint(NumberConstants.intakingPosition, NumberConstants.maxElevatorSpeed, 1, 2));
			addSequential(new TurnCommand(-12, 1, 2, 1.5));
			addParallel (new DriveCommand (49, 1, -12, 2.75));
			addSequential(new SetIntakeSpeedCommand(true, 1, 2));
			addParallel (new DriveCommand (-20, 0.8, -12, 2.75));



			
		} else if (position == 1) {
			addParallel(new SetIntakeSpeedCommand(true, 0.75, 2));
    		addParallel(new IntakePistonCommand(true));
    		
    		addParallel(new ExecuteAfterDistance(EncoderWaitCommand.DRIVE,
					 30,1.5, new ElevatorSetpoint(NumberConstants.switchPosition,NumberConstants.maxElevatorSpeed,1, 3)));
    		
        	//addParallel(new ElevatorSetpoint(NumberConstants.switchPosition, NumberConstants.maxElevatorSpeed, 1, 3));
        	//Drive in parallel to the switch 
        	addSequential(new DriveCommand(119, 0.8, 22,2));
            addSequential(new SetIntakeSpeedCommand(false, 0.7,1));
            
           /* addSequential (new DriveCommand (-40, 1, -27.5, 3, -20, 60, 0.7));
            addParallel(new ElevatorSetpoint(NumberConstants.intakingPosition, NumberConstants.maxElevatorSpeed, 1, 3));
            addParallel (new DriveCommand(30, 0.7, 60, 1.5));
            addSequential(new SetIntakeSpeedCommand(true, 1, 1.5));

            addSequential (new DriveCommand(-30, 0.7, 60, 1.5));
            addParallel(new ElevatorSetpoint(NumberConstants.switchPosition, NumberConstants.maxElevatorSpeed, 1, 3));
            addSequential (new DriveCommand (40, 1, 60, 3, 20, -27.5, 0.7));
            addSequential(new SetIntakeSpeedCommand(false, 0.7,1));
*/
            
            addSequential (new DriveCommand (-100, 1, 27.5, 2));
            
            addParallel (new TurnCommand(0, 1, 1));
            addSequential(new ElevatorSetpoint(NumberConstants.intakingPosition, NumberConstants.maxElevatorSpeed, 1, 3));
            addParallel(new SetIntakeSpeedCommand(true, 1, 2));
			addSequential(new DriveCommand(53, 1, 0, 2, 20, 0, 0.6));
            
           
            addParallel(new SetIntakeSpeedCommand(true, 0.4, 1.5, true));
            addSequential (new DriveCommand(-50, 0.7, 0, 1.5));
            addParallel (new TurnCommand(22, 1, 0.5));
            addSequential(new ElevatorSetpoint(NumberConstants.switchPosition, NumberConstants.maxElevatorSpeed, 1, 3));
            addSequential (new DriveCommand (90, 1, 22, 2));
            addSequential(new SetIntakeSpeedCommand(false, 0.7,1));
            
            addParallel(new ElevatorSetpoint(NumberConstants.intakingPosition, NumberConstants.maxElevatorSpeed, 1, 3));
            addSequential (new DriveCommand (-50, 1, 22, 2, 3));
            
            addSequential (new TurnCommand(-25, 1, 1));
            addParallel(new SetIntakeSpeedCommand(true, 1, 2, false, true));
			addSequential(new DriveCommand(25, 1, -25, 2, 20, -25, 0.9, 3));
            
            addSequential (new DriveCommand(-50, 1, -25, 1.5));

		} else if (position == 2) {
			
		}
    }
}
