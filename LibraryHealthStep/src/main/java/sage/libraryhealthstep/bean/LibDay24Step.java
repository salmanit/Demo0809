package sage.libraryhealthstep.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Sage on 2016/10/27.
 * 存储24小时的步数，每小时一条
 */
@Entity
public class LibDay24Step {
    @Id
    public long actionID;
    @Property
    public double distance;
    @Generated(hash = 1456700649)
    public LibDay24Step(long actionID, double distance) {
        this.actionID = actionID;
        this.distance = distance;
    }
    @Generated(hash = 1517320480)
    public LibDay24Step() {
    }
    public long getActionID() {
        return this.actionID;
    }
    public void setActionID(long actionID) {
        this.actionID = actionID;
    }
    public double getDistance() {
        return this.distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }

}
