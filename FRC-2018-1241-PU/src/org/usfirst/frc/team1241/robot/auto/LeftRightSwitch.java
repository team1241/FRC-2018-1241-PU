package org.usfirst.frc.team1241.robot.auto;

import org.usfirst.frc.team1241.robot.NumberConstants;
import org.usfirst.frc.team1241.robot.auto.drive.EncoderWaitCommand;
import org.usfirst.frc.team1241.robot.auto.drive.ExecuteAfterDistance;
import org.usfirst.frc.team1241.robot.auto.drive.QuinticBezierDrivePath;
import org.usfirst.frc.team1241.robot.auto.drive.TurnCommand;
import org.usfirst.frc.team1241.robot.auto.elevator.ElevatorSetpoint;
import org.usfirst.frc.team1241.robot.auto.intake.IntakePistonCommand;
import org.usfirst.frc.team1241.robot.auto.intake.SetIntakeSpeedCommand;
import org.usfirst.frc.team1241.robot.utilities.Point;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftRightSwitch extends CommandGroup {

    public LeftRightSwitch() {
    	addParallel(new SetIntakeSpeedCommand(true, 0.75, 2));
		addParallel(new IntakePistonCommand(true));
    	addParallel(new ExecuteAfterDistance(EncoderWaitCommand.DRIVE, 34,1.5,
				new ElevatorSetpoint(NumberConstants.switchPosition, NumberConstants.maxElevatorSpeed,1, 2.5)));
		addSequential(new QuinticBezierDrivePath (new Point(0,0), new Point (-38,134), new Point (41,132), new Point (-67,242), new Point (97, 201), new Point(222,216), 20, 0.05, 5, 1 ));
		addSequential (new TurnCommand (180, 1, 1));
		addSequential(new SetIntakeSpeedCommand(false, 0.65, 1));
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
