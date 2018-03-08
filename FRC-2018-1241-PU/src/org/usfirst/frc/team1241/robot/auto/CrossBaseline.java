package org.usfirst.frc.team1241.robot.auto;

import org.usfirst.frc.team1241.robot.auto.drive.DriveCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossBaseline extends CommandGroup {

    public CrossBaseline() {
    	
    	addSequential(new DriveCommand(150, 1.0, 0, 3));
    }
}
