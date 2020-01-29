/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        motor1.setOpenLoopRampRate(2.0);
        motor2.setOpenLoopRampRate(2.0);

        motor2.setInverted(true);
    }

    XboxController controller = new XboxController(0);


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
        motor1.set(0);
        motor2.set(0);
        speed = 0.0;
    }

    double speed = 0;
    LatchedBoolean leftLatch = new LatchedBoolean();
    LatchedBoolean rightLatch = new LatchedBoolean();
    
    @Override
    public void teleopPeriodic() {
        if (leftLatch.update(controller.getBumper(Hand.kLeft))) {
            speed = MathUtil.clamp(speed - 0.005, -1, 1);
        }
        if (rightLatch.update(controller.getBumper(Hand.kRight))) {
            speed = MathUtil.clamp(speed + 0.005, -1, 1);
        }
        if (controller.getAButton()) {
            speed = 0.0;
        }
        if (controller.getBButton()) {
            speed = 0.3;
        }
        motor1.set(-speed);
        motor2.set(-speed);
        SmartDashboard.putNumber("flywheelSpeed", speed);
    }
    
    @Override
    public void testInit() {
    }
    
    @Override
    public void testPeriodic() {
    }
    
}
