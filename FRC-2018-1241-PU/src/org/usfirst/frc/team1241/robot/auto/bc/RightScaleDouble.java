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
public class RightScaleDouble extends CommandGroup {

    public RightScaleDouble(int position) {
    	if (position == 0) {
    		addParallel(new SetIntakeSpeedCommand(true, 0.75, 1));
			addParallel(new IntakePistonCommand(true));
			//Bring elevator to driving position after 100 inches 
			addParallel(new ExecuteAfterDistance(EncoderWaitCommand.DRIVE,
					 100,1.5, new ElevatorSetpoint(NumberConstants.portalPosition,NumberConstants.maxElevatorSpeed,1, 2.5)));
			//Drive to the right scale
			addSequential(new DriveCommand(220, 1, 0, 2.5, 215, 90, 0.8));
			addSequential (new DriveCommand (218, 1, 90, 2.7, 212, -25, 0.8));
			//Bring the elevator to scale high position while drving to the scale
			addParallel(new ElevatorSetpoint(NumberConstants.scaleHighPosition, NumberConstants.maxElevatorSpeed, 0.4, 2));
			addSequential(new DriveCommand(45, 1, -25, 1.5));
			//Outake the cube onto the scale
			addSequential(new SetIntakeSpeedCommand(false, 0.4, 0.5));
			//Bring the elevator to intaking position
			addParallel(new ElevatorSetpoint(NumberConstants.intakingPosition, NumberConstants.slowElevatorSpeed, 1, 2));
			//Drive back from the scale and turn to switch
			addSequential(new rightNested());
			//Intake while drving towards the switch to pick up cube
			addParallel(new SetIntakeSpeedCommand(true, 1, 3));
			addSequential(new DriveCommand(39, 1, -138, 2.5, 20, -138, 0.6));
			//Drive back from switch
			//addParallel(new ElevatorSetpoint(NumberConstants.switchPosition, NumberConstants.slowElevatorSpeed, 1, 2));
			addParallel(new SetIntakeSpeedCommand(true, 0.75, 1, true));
			addSequential(new DriveCommand(-41, 1, -138, 1.2));
			//Drive to towards scale
			addParallel(new SetIntakeSpeedCommand(true, 0.25, 1, true));
			addSequential(new leftScore());

			//Bring elevator to scale high position
			//addSequential(new ElevatorSetpoint(NumberConstants.scaleHighPosition, NumberConstants.maxElevatorSpeed, 0.35, 2.5));
			//Outake Cube onto scale
			addSequential(new SetIntakeSpeedCommand(false, 0.3, 3));
		} else if (position == 1) {

		} else if (position == 2) {
			//Intake Cube while brigning intake down
			addParallel(new SetIntakeSpeedCommand(true, 0.75, 2));
			addParallel(new IntakePistonCommand(true));
			//Bring elevator to scale high position
			addParallel(new ExecuteAfterDistance(EncoderWaitCommand.DRIVE, 60,1.5,
					new ElevatorSetpoint(NumberConstants.scaleHighPosition, NumberConstants.maxElevatorSpeed,1, 2.5)));
			//Drive to scale while turning to 25 degrees after 195 inches
			addSequential(new DriveCommand(270, 1, 0, 3.25, 195, -25, 0.8));
			//Outake the cube 
			addSequential(new SetIntakeSpeedCommand(false, 0.625, 0.5));
			//Bring the elevator to intaking position 
			addParallel(new ElevatorSetpoint(NumberConstants.intakingPosition, NumberConstants.slowElevatorSpeed, 1, 2));
			//Drive back and turn to switch
			addSequential(new rightNested());
			//Intake while drving towards the switch to intake a cube
			addParallel(new SetIntakeSpeedCommand(true, 1, 2.4));
			addSequential(new DriveCommand(39, 1, -132, 2.4, 20, -132, 0.7));
			//Drive back from switch
			//addParallel(new ElevatorSetpoint(NumberConstants.switchPosition, NumberConstants.slowElevatorSpeed, 1, 2));
			addParallel(new SetIntakeSpeedCommand(true, 0.75, 2.5, true));
			addSequential(new DriveCommand(-41, 1, -132, 2.5));
			//Drive to towards scale
			addParallel(new SetIntakeSpeedCommand(true, 0.25, 1, true));
			addSequential(new rightScore());

			//Bring elevator to scale high position
			//addSequential(new ElevatorSetpoint(NumberConstants.scaleHighPosition, NumberConstants.maxElevatorSpeed, 0.35, 2.5));
			//Outake Cube onto scale
			addSequential(new SetIntakeSpeedCommand(false, 0.30, 3));
		}
    }
    private static class rightNested extends CommandGroup {
 		public rightNested() {
 			addSequential(new DriveCommand(-42, 1, -26, 1.5));
			addSequential(new TurnCommand(-132, 1, 1));
 		}
 	}
     private static class leftScore extends CommandGroup {
 		public leftScore() {
 			addParallel(new TurnCommand(-35, 1, 1));
 			addSequential(new ElevatorSetpoint(NumberConstants.scaleMidPosition+3, NumberConstants.maxElevatorSpeed, 0.35, 2.5));
			addSequential(new DriveCommand(45, 1, -35, 1.5));
 		}
 	}
     
     private static class rightScore extends CommandGroup {
  		public rightScore() {
  			addParallel(new TurnCommand(-30, 1, 1));
  			addSequential(new ElevatorSetpoint(NumberConstants.scaleMidPosition+3, NumberConstants.maxElevatorSpeed, 0.35, 2.5));
 			addSequential(new DriveCommand(45, 1, -30, 1.5));
  		}
  	}
}