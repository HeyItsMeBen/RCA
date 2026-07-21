package org.firstinspires.ftc.teamcode.Arm;

import static java.lang.Math.*;

public class InverseKinematicsAlgorithm {

    public InverseKinematicsAlgorithm() {
        //Equation: y=0.0060912x+0.0163413 with R^2 of 0.99
    }

    public double returnBasePosition_byLOC(double x, double y, double armBase, double armMid) {
        double hypotenuse = hypot(x, y);
        if (hypotenuse == 0) return 0;

        double cosAngleAbove = (pow(armBase, 2) + pow(hypotenuse, 2) - pow(armMid, 2)) / (2 * armBase * hypotenuse);
        cosAngleAbove = max(-1.0, min(1.0, cosAngleAbove));

        double angleAboveDeg = toDegrees(acos(cosAngleAbove));
        double angleBelowDeg = toDegrees(atan2(y, x));

        return returnServoPosition(angleAboveDeg + angleBelowDeg, 1.0);
    }

    public double returnElbowPosition_byLOC(double x, double y, double armBase, double armMid) {

        double cosElbow = (pow(armBase, 2) + pow(armMid, 2) - pow(hypot(x, y), 2)) / (2 * armBase * armMid);
        cosElbow = max(-1.0, min(1.0, cosElbow));

        double angleDeg = toDegrees(acos(cosElbow));
        return returnServoPosition(angleDeg, 1.0);
    }

    public double returnWristPosition_byLOC(double x, double y, double armBase, double armMid) {
        double hypotenuse = hypot(x, y);
        if (hypotenuse == 0) return 0;

        double cosAngleAbove = (pow(armMid, 2) + pow(hypotenuse, 2) - pow(armBase, 2)) / (2 * armBase * hypotenuse);
        cosAngleAbove = max(-1.0, min(1.0, cosAngleAbove));

        double angleAboveDeg = toDegrees(acos(cosAngleAbove));
        double angleBelowDeg = toDegrees(atan2(x, y));

        return returnServoPosition(180 - (angleAboveDeg + angleBelowDeg), 0.7);
    }

    public double returnWristPosition_byGlobal(double baseJointPosition, double midJointPosition, double targetGlobalDeg, double wristOffsetDegree) {

        double shoulderAngleDeg = returnServoAngle(baseJointPosition);
        double elbowAngleDeg = returnServoAngle(midJointPosition);

        double forearmGlobalAngleDeg = shoulderAngleDeg + elbowAngleDeg;

        double wristRelativeAngleDeg = targetGlobalDeg - forearmGlobalAngleDeg;

        return returnServoPosition(wristRelativeAngleDeg + wristOffsetDegree, 0.7);
    }

    private double returnServoPosition(double angleDeg, double maxLimit) {
        if (Double.isNaN(angleDeg)) return 0;

        double position = (0.0060912 * angleDeg + 0.0163413);

        if (position >= maxLimit) {
            position = maxLimit;
        } else if (position <= 0) {
            position = 0;
        }
        return position;
    }

    private double returnServoAngle(double servoPosition) {
        return (servoPosition - 0.0163413 ) / 0.0060912;
    }
}