/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.net.Socket;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpiutil.math.MathUtil;

/**
* The VM is configured to automatically run this class, and to call the
* functions corresponding to each mode, as described in the TimedRobot
* documentation. If you change the name of this class or the package after
* creating this project, you must also update the build.gradle file in the
* project.
*/
public class Robot extends TimedRobot {
    
    CANSparkMax motor1 = new CANSparkMax(20, MotorType.kBrushless);
    CANSparkMax motor2 = new CANSparkMax(22, MotorType.kBrushless);

    {
    
        motor1.restoreFactoryDefaults();
        motor2.restoreFactoryDefaults();
        motor2.setIdleMode(IdleMode.kCoast);
        motor1.setIdleMode(IdleMode.kCoast);
        // motor2.setOpenLoopRampRate(5.0);
        // motor2.setOpenLoopRampRate(5.0);
        motor2.follow(motor1);
    }

    CANPIDController pid = motor1.getPIDController();

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("shooter test");
    NetworkTableEntry setpoint = table.getEntry("setpoint");
    NetworkTableEntry pEntry = table.getEntry("kP");
    NetworkTableEntry iEntry = table.getEntry("kI");
    NetworkTableEntry dEntry = table.getEntry("kD");
    NetworkTableEntry vEntry = table.getEntry("velocity");

    {
        setpoint.setDouble(100.0);
        pEntry.setDouble(0.001);
        iEntry.setDouble(0.0);
        dEntry.setDouble(0.0);
        vEntry.setDouble(0.0);
    }
    
    /**
    * This function is run when the robot is first started up and should be used
    * for any initialization code.
    */
    @Override
    public void robotInit() {
    }
    
    @Override
    public void autonomousInit() {
    }
    
    @Override
    public void autonomousPeriodic() {
    }
    
    @Override
    public void teleopInit() {
        pid.setP(pEntry.getDouble(0.0));
        pid.setI(iEntry.getDouble(0.0));
        pid.setD(dEntry.getDouble(0.0));
        pid.setOutputRange(-1.0, 0.0);
    }
    
    @Override
    public void teleopPeriodic() {
        pid.setP(pEntry.getDouble(0.0));
        pid.setI(iEntry.getDouble(0.0));
        pid.setD(dEntry.getDouble(0.0));
        pid.setReference(-1 * MathUtil.clamp(setpoint
                .getDouble(0.0), 0.0, 5000.0), ControlType.kVelocity);
        vEntry.getDouble(motor1.getEncoder().getVelocity());
    }
    
    @Override
    public void testInit() {
    }
    
    @Override
    public void testPeriodic() {
    }
    
}
