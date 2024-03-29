package org.usfirst.frc.team1241.robot.auto;

import org.usfirst.frc.team1241.robot.NumberConstants;
import org.usfirst.frc.team1241.robot.auto.drive.DriveCommand;
import org.usfirst.frc.team1241.robot.auto.drive.DrivePath;
import org.usfirst.frc.team1241.robot.auto.elevator.ElevatorSetpoint;
import org.usfirst.frc.team1241.robot.auto.intake.IntakePistonCommand;
import org.usfirst.frc.team1241.robot.auto.intake.SetIntakeSpeedCommand;
import org.usfirst.frc.team1241.robot.utilities.Point;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSwitch extends CommandGroup {

    public LeftSwitch(int position) {
        if(position == 0){
        	addParallel(new SetIntakeSpeedCommand(true, 0.75, 2));
    		addParallel(new IntakePistonCommand(true));
        	addParallel(new ElevatorSetpoint(NumberConstants.switchPosition, NumberConstants.maxElevatorSpeed, 1, 2));
        	
        	//addSequential(new DriveCommand(113, 1, -25,2));
        	addSequential(new DrivePath(new Point(0,0), new Point(-10,40), new Point(-18,117), new Point(20,134), 2.5, 1));
            addSequential(new SetIntakeSpeedCommand(false, 0.65, 1));
    		addParallel(new IntakePistonCommand(true));       	
        	
        } else if (position == 1){
        	addParallel(new SetIntakeSpeedCommand(true, 0.75, 2));
    		addParallel(new IntakePistonCommand(true));
        	addParallel(new ElevatorSetpoint(NumberConstants.switchPosition, NumberConstants.maxElevatorSpeed, 1, 3));
        	addSequential(new DriveCommand(113, 1, -30,2));
        	//addSequential(new DrivePath(new Point(0,0), new Point(-35,32), new Point(-50,22), new Point(-50,100), 3, 0.6));
                        
            addSequential(new SetIntakeSpeedCommand(false, 0.7,1));
        } else if (position == 2){
        	
        	
        }
    }
}
