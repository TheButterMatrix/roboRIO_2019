/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;
import frc.robot.commands.Climber.*;
import frc.robot.commands.Drivetrain.*;
import frc.robot.commands.HatchPanel.*;

public class OI {

  private static class Buttons {
    // BUTTTONS
    private static final int A = 1;
    private static final int B = 2;
    private static final int X = 3;
    private static final int Y = 4;
    private static final int LEFT_BUMPER = 5;
    private static final int RIGHT_BUMPER = 6;
    private static final int BACK = 7;
    private static final int START = 8;
  }

  private static final double XBOX_LEFT_Y_THRESHOLD = 0.05;
  private static final double XBOX_RIGHT_Y_THRESHOLD = 0.05;
  private static final double TRIGGER_THRESHOLD = 0.05;

  private static final int DRIVER_PORT_ONE = 0;
  XboxController driverOne = new XboxController(DRIVER_PORT_ONE);

  private static final int DRIVER_PORT_TWO = 1;
  XboxController driverTwo = new XboxController(DRIVER_PORT_TWO);

  // Driver One

  /* Drivetrain */

  private static final int TOGGLE_GEAR_BUTTON = Buttons.LEFT_BUMPER; // Xbox Left Bumper
  private Button toggleGearButton = new JoystickButton(driverOne, TOGGLE_GEAR_BUTTON);

  private static final int TOGGLE_AUTO_SHIFT_BUTTON = Buttons.RIGHT_BUMPER; //Xbox Right Bumper
  private Button toggleAutoShiftButton = new JoystickButton(driverOne, TOGGLE_AUTO_SHIFT_BUTTON);

  // private static final int CHANGE_ROBOT_DIRECTION_BUTTON = Buttons.B; // Xbox B button
  // private Button changeRobotDirectionButton = new JoystickButton(driverOne, CHANGE_ROBOT_DIRECTION_BUTTON);

  // private static final int DIRECTION_STATE_BUTTON = Buttons.A; // Xbox A button
  // private Button changeCameraDirection = new JoystickButton(driverOne, DIRECTION_STATE_BUTTON);

  private static final int RAISE_CLIMBER_FRONT = Buttons.A;
  private Button climberExtendFrontButton = new JoystickButton(driverOne, RAISE_CLIMBER_FRONT);

  private static final int RAISE_CLIMBER_BACK = Buttons.B;
  private Button climberExtendBackButton = new JoystickButton(driverOne, RAISE_CLIMBER_BACK);

  private static final int CALIBRATE_GYRO = Buttons.A;
  public Button calibrateGyro = new JoystickButton(driverOne, CALIBRATE_GYRO);

  /* Climber */

  private static final int CLIMBER_INITIALIZE_BUTTON = Buttons.START; // Xbox Start button
  private Button initializeClimberButton = new JoystickButton(driverOne, CLIMBER_INITIALIZE_BUTTON);

  private static final int RETRACT_CLIMBER_FRONT = Buttons.X; // Xbox X button
  private Button climberRetractFrontButton = new JoystickButton(driverOne, RETRACT_CLIMBER_FRONT);

  private static final int RETRACT_CLIMBER_BACK = Buttons.Y; // Xbox Y button
  private Button climberRetractBackButton = new JoystickButton(driverOne, RETRACT_CLIMBER_BACK);

  // Driver Two

  /* Hatch */

  private static final int TOGGLE_HATCH_PANEL_BUTTON = Buttons.X; // Xbox X button
  private Button toggleHatchButton = new JoystickButton(driverTwo, TOGGLE_HATCH_PANEL_BUTTON);

  private static final int LAUNCH_HATCH_PANEL_BUTTON = Buttons.Y; // Xbox Y button
  private Button launchHatchButton = new JoystickButton(driverTwo, LAUNCH_HATCH_PANEL_BUTTON);

  private Button raiseHatch = new JoystickButton(driverTwo, Buttons.START);
  private Button lowerHatch = new JoystickButton(driverTwo, Buttons.BACK);

  public OI() {

    // Change Direction of camera
    // changeCameraDirection.whenPressed(new ToggleCameraDirection());

    // Gear Shift (Driver One)
    toggleGearButton.whenPressed(new ToggleGear());
    toggleAutoShiftButton.whenPressed(new ToggleAutoShift());

    // Change which direction is forward (Driver One)
    // changeRobotDirectionButton.whenPressed(new ChangeDirection());

    // Climber (Driver One)
    // climberFrontButton.whenPressed(new ToggleFrontClimberSolenoid());
    // climberBackButton.whenPressed(new ToggleBackClimberSolenoid());

    initializeClimberButton.whileHeld(new InitializeClimber());
    climberRetractFrontButton.whileHeld(new RetractFrontClimb());
    climberRetractBackButton.whileHeld(new RetractRearClimb());
    climberExtendFrontButton.whileHeld(new ExtendFrontClimb());
    climberExtendBackButton.whileHeld(new ExtendRearClimb());

    // Hatch (Driver Two)
    toggleHatchButton.whenPressed(new ToggleHatch());
    launchHatchButton.whenPressed(new LaunchPanel());
    raiseHatch.whileHeld(new RaiseHatch());
    lowerHatch.whileHeld(new LowerHatch());
  }

  public double getDriverLeftTriggerAxis() {
    double triggerLeftAxis = driverOne.getTriggerAxis(Hand.kLeft);
    return deadband(triggerLeftAxis, TRIGGER_THRESHOLD);
  }

  public double getDriverRightTriggerAxis() {
    double triggerRightAxis = driverOne.getTriggerAxis(Hand.kRight);
    return deadband(triggerRightAxis, TRIGGER_THRESHOLD);
  }

  public double getDriverLeftYAxis() {
    double rawLeftYAxis = driverOne.getY(Hand.kLeft);
    return deadband(rawLeftYAxis, XBOX_LEFT_Y_THRESHOLD);
  }

  public double getDriverRightYAxis() {
    double rawRightYAxis = driverOne.getY(Hand.kRight);
    Robot.log("Drivetrain speed", rawRightYAxis);

    return deadband(rawRightYAxis, XBOX_RIGHT_Y_THRESHOLD);
  }

  public double deadband(double input, double threshold) {
    return Math.abs(input) <= Math.abs(threshold) ? 0 : input;
  }

  public void rumble(GenericHID.RumbleType type, double value) {
    driverOne.setRumble(type, value);
  }
}
