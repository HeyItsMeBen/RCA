package org.firstinspires.ftc.teamcode.Arm;

import static java.lang.Math.*;

public class InverseKinematicsAlgorithm {

    public InverseKinematicsAlgorithm() {}

    public double returnBasePosition_byLOC(double x, double y, double armBase, double armMid, double totalRange) {
        double hypotenuse = hypot(x, y);
        if (hypotenuse == 0) return 0;

        double lowerShoulderAngle = toDegrees(atan2(y, x));

        double cosUpper = (pow(armBase, 2) + pow(hypotenuse, 2) - pow(armMid, 2)) / (2 * armBase * hypotenuse);
        cosUpper = max(-1.0, min(1.0, cosUpper));
        double upperShoulderAngle = toDegrees(acos(cosUpper));

        double totalShoulderAngle = lowerShoulderAngle + upperShoulderAngle;

        return returnServoPosition(totalShoulderAngle, 0.55, totalRange);
    }

    public double returnElbowPosition_byLOC(double x, double y, double armBase, double armMid, double totalRange) {
        double hypotenuse = hypot(x, y);
        if (hypotenuse == 0) return 0;

        double cosElbow = (pow(armBase, 2) + pow(armMid, 2) - pow(hypotenuse, 2)) / (2 * armBase * armMid);
        cosElbow = max(-1.0, min(1.0, cosElbow));

        double elbowAngle = toDegrees(acos(cosElbow));

        return returnServoPosition(elbowAngle, 0.9, totalRange);
    }

    public double returnWristPosition_byLOC(double x, double y, double armBase, double armMid, double totalRange) {
        double hypotenuse = hypot(x, y);
        if (hypotenuse == 0) return 0;

        double cosWrist = (pow(armMid, 2) + pow(hypotenuse, 2) - pow(armBase, 2)) / (2 * armMid * hypotenuse);
        cosWrist = max(-1.0, min(1.0, cosWrist));

        double upperWristAngle = toDegrees(acos(cosWrist));

        return returnServoPosition(upperWristAngle, 0.8, totalRange);
    }

    public double returnServoPosition(double angleDeg, double maxLimit, double totalRange) {
        if (Double.isNaN(angleDeg)) return 0;

        double divisor = totalRange / 180.0;
        double position = (0.0060912 * angleDeg + 0.0163413) / divisor;

        if (position >= maxLimit) {
            position = maxLimit;
        } else if (position <= 0) {
            position = 0;
        }
        return position;
    }

    public double returnServoAngle(double servoPosition, double totalRange) {
        double divisor = totalRange / 180.0;
        return ((servoPosition * divisor) - 0.0163413) / 0.0060912;
    }
}