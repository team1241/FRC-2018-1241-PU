/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1241.robot;

import org.usfirst.frc.team1241.robot.auto.bc.CrossBaseline;
import org.usfirst.frc.team1241.robot.auto.bc.LeftScale;
import org.usfirst.frc.team1241.robot.auto.bc.LeftScaleDouble;
import org.usfirst.frc.team1241.robot.auto.bc.LeftScaleTriple;
import org.usfirst.frc.team1241.robot.auto.bc.LeftSwitch;
import org.usfirst.frc.team1241.robot.auto.bc.LeftSwitchDouble;
import org.usfirst.frc.team1241.robot.auto.bc.LeftSwitchLeftScale;
import org.usfirst.frc.team1241.robot.auto.bc.LeftSwitchRightScale;
import org.usfirst.frc.team1241.robot.auto.bc.NoAuto;
import org.usfirst.frc.team1241.robot.auto.bc.RightScale;
import org.usfirst.frc.team1241.robot.auto.bc.RightScaleDouble;
import org.usfirst.frc.team1241.robot.auto.bc.RightScaleTriple;
import org.usfirst.frc.team1241.robot.auto.bc.RightSwitch;
import org.usfirst.frc.team1241.robot.auto.bc.RightSwitchDouble;
import org.usfirst.frc.team1241.robot.auto.bc.RightSwitchLeftScale;
import org.usfirst.frc.team1241.robot.auto.bc.RightSwitchRightScale;
import org.usfirst.frc.team1241.robot.auto.drive.DriveCommandV2;
import org.usfirst.frc.team1241.robot.auto.drive.TurnCommand;
import org.usfirst.frc.team1241.robot.commands.ResetElevatorEncoder;
import org.usfirst.frc.team1241.robot.subsystems.Climber;
import org.usfirst.frc.team1241.robot.subsystems.Drivetrain;
import org.usfirst.frc.team1241.robot.subsystems.Elevator;
import org.usfirst.frc.team1241.robot.subsystems.Intake;
import org.usfirst.frc.team1241.robot.subsystems.LEDstrips;
import org.usfirst.frc.team1241.robot.utilities.Logger;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	public static OI oi;
	public static Drivetrain drive;
	public static Intake intake;
	public static Elevator elevator;
	public static Climber climber;
	public static LEDstrips ledstrips;
	public static Logger logger;

	Preferences pref;

	public static double pDrive;
	public static double iDrive;
	public static double dDrive;
	public static double pGyro;
	public static double iGyro;
	public static double dGyro;
	
	public static boolean autoIntake = true;

	public static double fTalonElevator;
	public static double pTalonElevator;
	public static double iTalonElevator;
	public static double dTalonElevator;

	public static double pElevator;
	public static double iElevator;
	public static double dElevator;

	public static double pLockElevator;
	public static double iLockElevator;
	public static double dLockElevator;
	
	Command m_autonomousCommand;
	public static int autoLLNum;
	public static int autoLRNum;
	public static int autoRLNum;
	public static int autoRRNum;
	public static int positionNum;
	
	public static int gameNum;
	
	CameraServer server;

	SendableChooser<Integer> positionChooser;
	SendableChooser<Integer> autoLRChooser;
	SendableChooser<Integer> autoRLChooser;
	SendableChooser<Integer> autoLLChooser;
	SendableChooser<Integer> autoRRChooser;

	String clipboard = "Match Strategy";

	double elevatorCurrentDraw = 0;

	String gameData = "";


	// GET RID*********************************************************
	double maxElevatorSpeed = 0;
	double maxLeftDriveSpeed = 0;
	double maxRightDriveSpeed = 0;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		pref = Preferences.getInstance();

		oi = new OI();
		drive = new Drivetrain();
		elevator = new Elevator();
		intake = new Intake();
		climber = new Climber();
		ledstrips = new LEDstrips();
		server = CameraServer.getInstance();
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(160,120); //320x240		
		
		logger = Logger.getInstance();
		logger.openFile("Robot Init");
		
		positionChooser = new SendableChooser<Integer>();
		autoLRChooser = new SendableChooser<Integer>();
		autoRLChooser = new SendableChooser<Integer>();
		autoLLChooser = new SendableChooser<Integer>();
		autoRRChooser = new SendableChooser<Integer>();
		
		

		positionChooser.addDefault("Left", 0);
		positionChooser.addObject("Center", 1);
		positionChooser.addObject("Right", 2);

		autoLLChooser.addDefault("BaseLine", 0);
		autoLLChooser.addObject("Left Switch", 1);
		autoLLChooser.addObject("Left Scale", 2);
		autoLLChooser.addObject("Left Switch, Left Scale", 3);
		autoLLChooser.addObject("Left Double Scale", 4);
		autoLLChooser.addObject("Left Switch Double", 5);
		autoLLChooser.addObject("Left Scale Triple", 6);
		autoLLChooser.addObject("No Auton", 7);

		autoLRChooser.addDefault("BaseLine", 0);
		autoLRChooser.addObject("Left Switch", 1);
		autoLRChooser.addObject("Right Scale", 2);
		autoLRChooser.addObject("Right Double Scale", 3);
		autoLRChooser.addObject("Left Switch Right Scale", 4);
		autoLRChooser.addObject("Left Switch Double", 5);
		autoLRChooser.addObject("Right Scale Triple", 6);
		autoLRChooser.addObject("No Auton", 7);

		autoRLChooser.addDefault("BaseLine", 0);
		autoRLChooser.addObject("Right Switch", 1);
		autoRLChooser.addObject("Left Scale", 2);
		autoRLChooser.addObject("Left Double Scale", 3);
		autoRLChooser.addObject("Right Switch Double", 4);
		autoRLChooser.addObject("Left Scale Triple", 5);
		autoRLChooser.addObject("Right Switch Left Scale", 6);
		autoRLChooser.addObject("No Auton", 7);

		autoRRChooser.addDefault("BaseLine", 0);
		autoRRChooser.addObject("Right Switch", 1);
		autoRRChooser.addObject("Right Scale", 2);
		autoRRChooser.addObject("Right Switch Right Scale", 3);
		autoRRChooser.addObject("Right Double Scale", 4);
		autoRRChooser.addObject("Right Switch Double", 5);
		autoRRChooser.addObject("Right Scale Triple", 6);
		autoRRChooser.addObject("No Auton", 7);
		
		climber.retractPTOPiston();
		//SmartDashboard.putData("Reset Elevator", new ResetElevatorEncoder());

		updateSmartDashboard();


	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		LEDstrips.disabled();
		logger.close();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();
		
		autoLLNum = (int) autoLLChooser.getSelected();
		autoLRNum = (int) autoLRChooser.getSelected();
		autoRLNum = (int) autoRLChooser.getSelected();
		autoRRNum = (int) autoRRChooser.getSelected();
		
		SmartDashboard.putString("Choosers", autoLLNum + " " + autoLRNum + " " + autoRLNum + " " + autoRRNum);
		
	}


	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		drive.reset();
		elevator.resetEncoders();
		intake.retractIntakePistons();
		logger.openFile("Auton Mode");


		LEDstrips.solidGold();
		
		positionNum = (int) positionChooser.getSelected();

		autoLLNum = (int) autoLLChooser.getSelected();
		autoLRNum = (int) autoLRChooser.getSelected();
		autoRLNum = (int) autoRLChooser.getSelected();
		autoRRNum = (int) autoRRChooser.getSelected();

		
		
		String gameData = "";
		while (gameData.length() < 3) {
			gameData = DriverStation.getInstance().getGameSpecificMessage();

		}

		if (gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L') {

			switch (autoLLNum) {
			case 0:
				m_autonomousCommand = (Command) new CrossBaseline();
				break;
			case 1:
				m_autonomousCommand = (Command) new LeftSwitch(positionNum);
				break;
			case 2:
				m_autonomousCommand = (Command) new LeftScale(positionNum);
				break;
			case 3:
				m_autonomousCommand = (Command) new LeftSwitchLeftScale(positionNum);
				break;
			case 4:
				m_autonomousCommand = (Command) new LeftScaleDouble(positionNum);
				break;
			case 5:
				m_autonomousCommand = (Command) new LeftSwitchDouble(positionNum);
				break;
			case 6:
				m_autonomousCommand = (Command) new LeftScaleTriple(positionNum);
				break;
			case 7:
				m_autonomousCommand = (Command) new NoAuto();
				break;
			}
		} else if (gameData.charAt(0) == 'L' && gameData.charAt(1) == 'R') {

			switch (autoLRNum) {
			case 0:
				m_autonomousCommand = (Command) new CrossBaseline();
				break;
			case 1:
				m_autonomousCommand = (Command) new LeftSwitch(positionNum);
				break;
			case 2:
				m_autonomousCommand = (Command) new RightScale(positionNum);
				break;
			case 3:
				m_autonomousCommand = (Command) new RightScaleDouble(positionNum);
				break;
			case 4:
				m_autonomousCommand = (Command) new LeftSwitchRightScale(positionNum);
				break;
			case 5:
				m_autonomousCommand = (Command) new LeftSwitchDouble(positionNum);
				break;
			case 6:
				m_autonomousCommand = (Command) new RightScaleTriple(positionNum);
				break;
			case 7:
				m_autonomousCommand = (Command) new NoAuto();
				break;
			}
		} else if (gameData.charAt(0) == 'R' && gameData.charAt(1) == 'L') {

			switch (autoRLNum) {
			case 0:
				m_autonomousCommand = (Command) new CrossBaseline();
				break;
			case 1:
				m_autonomousCommand = (Command) new RightSwitch(positionNum);
				break;
			case 2:
				m_autonomousCommand = (Command) new LeftScale(positionNum);
				break;
			case 3:
				m_autonomousCommand = (Command) new LeftScaleDouble(positionNum);
				break;
			case 4:
				m_autonomousCommand = (Command) new RightSwitchDouble(positionNum);
				break;
			case 5:
				m_autonomousCommand = (Command) new LeftScaleTriple(positionNum);
				break;
			case 6:
				m_autonomousCommand = (Command) new RightSwitchLeftScale(positionNum);
				break;
			case 7:
				m_autonomousCommand = (Command) new NoAuto();
				break;
			}
		} else if (gameData.charAt(0) == 'R' && gameData.charAt(1) == 'R') {

			switch (autoRRNum) {
			case 0:
				m_autonomousCommand = (Command) new CrossBaseline();
				break;
			case 1:
				m_autonomousCommand = (Command) new RightSwitch(positionNum);
				break;
			case 2:
				m_autonomousCommand = (Command) new RightScale(positionNum);
				break;
			case 3:
				m_autonomousCommand = (Command) new RightSwitchRightScale(positionNum);
				break;
			case 4:
				m_autonomousCommand = (Command) new RightScaleDouble(positionNum);
				break;
			case 5:
				m_autonomousCommand = (Command) new RightSwitchDouble(positionNum);
				break;
			case 6:
				m_autonomousCommand = (Command) new RightScaleTriple(positionNum);
				break;
			case 7:
				m_autonomousCommand = (Command) new NoAuto();
				break;
			}
		}
		
		/*switch(positionNum){
			case 0:
				m_autonomousCommand = new RightSwitchRightScale(positionNum);
				break;
			case 1:
				if(gameData.charAt(0) == 'L'){
					//m_autonomousCommand = new LeftSwitch(positionNum);
					m_autonomousCommand = new LeftSwitch(positionNum);

				} else if(gameData.charAt(0) == 'R'){
					m_autonomousCommand = new RightSwitch(positionNum);
				}
				break;
			case 2:
				//m_autonomousCommand = new RightScaleDouble(positionNum);
				//m_autonomousCommand = new RightSwitchRightScale(positionNum);
				m_autonomousCommand = new LeftScaleDouble(positionNum);


				break;
		}*/
		//m_autonomousCommand = new TurnCommand(150, 1, 3, 1); 
		
		if (m_autonomousCommand != null) {
			logger.logd("AUTO SELECTED", m_autonomousCommand.getName());
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		updateSmartDashboard();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		logger.openFile("Teleop Mode");
		intake.setContains(false);
		pref = Preferences.getInstance();
		LEDstrips.solidGreen();
		intake.extendIntakePistons();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();
		
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	public void updateSmartDashboard() {
		
		// AUTO
			SmartDashboard.putData("Left Left chooser", autoLLChooser);
			SmartDashboard.putData("Left Right chooser", autoLRChooser);
			SmartDashboard.putData("Right Left chooser", autoRLChooser);
			SmartDashboard.putData("Right Right chooser", autoRRChooser);
			SmartDashboard.putData("Position chooser", positionChooser);
		// INTAKE
			SmartDashboard.putNumber("Left Intake Current", intake.getLeftCurrent());
			SmartDashboard.putNumber("Right Intake Current", intake.getLeftCurrent());
			SmartDashboard.putNumber("Intake Current Difference", intake.getLeftCurrent() - intake.getRightCurrent());
			SmartDashboard.putBoolean("Cube In", intake.getOptic());
			
			/*SmartDashboard.putNumber("Intake Ultrasonic", intake.getUltrasonicRange());

			if(SmartDashboard.getBoolean("Elevator Reset", false)){
				elevator.resetEncoders();
			}*/
			
			intake.intakeSpeed = pref.getDouble("Intake Speed", 0.76); //
	 		intake.lowOuttake = pref.getDouble("lowOuttake", 1.0); //
			intake.regOuttake = pref.getDouble("regOuttake", 0.75); //
			intake.highOuttake = pref.getDouble("highOuttake", 0.5); //
			intake.slowOuttake = pref.getDouble("slowOuttake", 0.2); //
			intake.maxCurrentDraw = pref.getInt("Max Current Draw", 27);
			intake.maxCurrentDuration = pref.getInt("Max Current Duration", 1000);
			intake.continuousCurrentLimit = pref.getInt("Continuous Current Limit", 18);
			autoIntake = pref.getBoolean("autoIntake", true);

			
		// DRIVE
			SmartDashboard.putNumber("Gyro Angle", drive.getYaw());
			SmartDashboard.putNumber("Right Encoder", drive.getRightDriveEncoder());
			SmartDashboard.putNumber("Left Encoder", drive.getLeftDriveEncoder());
			SmartDashboard.putNumber("Left Drive Speed", drive.getLeftSpeed());
			SmartDashboard.putNumber("Right Drive Speed", drive.getRightSpeed());
		
		// Elevator
			SmartDashboard.putNumber("Elevator Encoder", elevator.getElevatorEncoder());
			SmartDashboard.putNumber("Elevator Encoder RAW", elevator.getElevatorRotations());
			SmartDashboard.putNumber("Elevator RPM", elevator.getElevatorSpeed());

		
		// DRIVER STATION
			SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
			SmartDashboard.putNumber("Match Time", DriverStation.getInstance().getMatchTime());
		
		// GAME DATA
			SmartDashboard.putString("Game Message", DriverStation.getInstance().getGameSpecificMessage());
			SmartDashboard.putString("Match Strategy", clipboard);
			clipboard = pref.getString("Match Strategy", "Defeat the Boss!");
			
		if (elevator.getElevatorSpeed() > maxElevatorSpeed) {
			maxElevatorSpeed = elevator.getElevatorSpeed();
			SmartDashboard.putNumber("MAX Elevator RPM", maxElevatorSpeed);
		}

		if (drive.getLeftSpeed() < maxLeftDriveSpeed) {
			maxLeftDriveSpeed = drive.getLeftSpeed();
			SmartDashboard.putNumber("Left Drive Max Speed", maxLeftDriveSpeed);
		}

		if (drive.getRightSpeed() < maxRightDriveSpeed) {
			maxRightDriveSpeed = drive.getRightSpeed();
			SmartDashboard.putNumber("Right Drive Max Speed", maxRightDriveSpeed);
		}
			
		//Preferences
		pDrive = pref.getDouble("Drive pGain", 0.0);
		iDrive = pref.getDouble("Drive iGain", 0.0);
		dDrive = pref.getDouble("Drive dGain", 0.0);

		pGyro = pref.getDouble("pGyro", 0.0);
		iGyro = pref.getDouble("iGyro", 0.0);
		dGyro = pref.getDouble("dGyro", 0.0);

		fTalonElevator = pref.getDouble("Elevator Profile fGain", 0.0);
		pTalonElevator = pref.getDouble("Elevator Profile pGain", 0.0);
		iTalonElevator = pref.getDouble("Elevator Profile iGain", 0.0);
		dTalonElevator = pref.getDouble("Elevator Profile dGain", 0.0);

		pElevator = pref.getDouble("Elevator pGain", 0.0);
		iElevator = pref.getDouble("Elevator iGain", 0.0);
		dElevator = pref.getDouble("Elevator dGain", 0.0);

		pLockElevator = pref.getDouble("Elevator Lock pGain", 0.0);
		iLockElevator = pref.getDouble("Elevator Lock ipGain", 0.0);
		dLockElevator = pref.getDouble("Elevator Lock dGain", 0.0);

	}
}
