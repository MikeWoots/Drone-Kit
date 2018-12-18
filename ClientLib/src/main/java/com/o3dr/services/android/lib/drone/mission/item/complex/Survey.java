package com.o3dr.services.android.lib.drone.mission.item.complex;

import android.os.Parcel;

import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.util.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class Survey extends MissionItem implements MissionItem.ComplexItem<Survey>, android.os.Parcelable {

    private SurveyDetail surveyDetail = new SurveyDetail();
    {
        surveyDetail.setAltitude(100);
        surveyDetail.setAngle(180);
        surveyDetail.setOverlap(70);
        surveyDetail.setSpeed(12);
        surveyDetail.setSidelap(75);
        surveyDetail.setLockOrientation(false);
        surveyDetail.setSaveable(true);
        surveyDetail.setSunny(false);
    }

    private double polygonArea;
    private List<LatLong> polygonPoints = new ArrayList<LatLong>();
    private List<Double> polygonPointAltitudes = new ArrayList<Double>();
    private List<LatLong> gridPoints = new ArrayList<LatLong>();
    private List<LatLong> cameraLocations = new ArrayList<LatLong>();
    private boolean isValid;
    private boolean startCameraBeforeFirstWaypoint;
    private boolean isMergedConvexSurvey = false;

    private String id;

    public Survey(){
        this(MissionItemType.SURVEY);
    }

    protected Survey(MissionItemType type){
        super(type);
    }

    public Survey(Survey copy){
        this();
        copy(copy);
    }

    public void copy(Survey source){
        this.surveyDetail = new SurveyDetail(source.surveyDetail);
        this.polygonArea = source.polygonArea;
        this.polygonPoints = copy2DPointsList(source.polygonPoints);
        this.polygonPointAltitudes = copyDoubleList(source.polygonPointAltitudes);
        this.gridPoints = copy2DPointsList(source.gridPoints);
        this.cameraLocations = copy2DPointsList(source.cameraLocations);
        this.isValid = source.isValid;
        this.startCameraBeforeFirstWaypoint = source.startCameraBeforeFirstWaypoint;
        this.isMergedConvexSurvey = source.isMergedConvexSurvey;
    }

    private List<LatLong> copy2DPointsList(List<LatLong> copy){
        final List<LatLong> dest = new ArrayList<>();
        for(LatLong itemCopy : copy){
            dest.add(new LatLong(itemCopy));
        }

        return dest;
    }

    private List<LatLongAlt> copy3DPointsList(List<LatLongAlt> copy){
        final List<LatLongAlt> dest = new ArrayList<>();
        for(LatLongAlt itemCopy : copy){
            dest.add(new LatLongAlt(itemCopy));
        }

        return dest;
    }

    private List<Double> copyDoubleList(List<Double> copy){
        final List<Double> dest = new ArrayList<>();
        for(Double itemCopy : copy){
            dest.add(new Double(itemCopy));
        }

        return dest;
    }

    public SurveyDetail getSurveyDetail() {
        return surveyDetail;
    }

    public void setSurveyDetail(SurveyDetail surveyDetail) {
        this.surveyDetail = surveyDetail;
    }

    public double getPolygonArea() {
        return polygonArea;
    }

    public void setPolygonArea(double polygonArea) {
        this.polygonArea = polygonArea;
    }

    public List<LatLong> getPolygonPoints() {
        return polygonPoints;
    }

    public void setPolygonPoints(List<LatLong> polygonPoints) {
        this.polygonPoints = polygonPoints;
    }

    public boolean getIsMergedConvexSurvey () {
        return isMergedConvexSurvey;
    }

    public void setIsMergedConvexSurvey(boolean in) {
        this.isMergedConvexSurvey = in;
    }

    public List<Double> getPolygonPointAltitudes() { return this.polygonPointAltitudes; }

    public void setPolygonPointAltitudes(List<Double> polygonPointAltitudes) {
        this.polygonPointAltitudes = polygonPointAltitudes;
    }

    public synchronized List<LatLong> getGridPoints() {
        return this.gridPoints;
    }

    public synchronized void setGridPoints(List<LatLong> gridPoints) {
        this.gridPoints = gridPoints;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public double getGridLength() {
        return MathUtils.getPolylineLength(gridPoints);
    }

    public int getNumberOfLines() {
        return gridPoints.size() / 2;
    }

    public List<LatLong> getCameraLocations() {
        return cameraLocations;
    }

    public void setCameraLocations(List<LatLong> cameraLocations) {
        this.cameraLocations = cameraLocations;
    }

    /**
     * @since 2.8.1
     * @return true if the camera trigger should be started before reaching the first survey waypoint.
     */
    public boolean isStartCameraBeforeFirstWaypoint() {
        return startCameraBeforeFirstWaypoint;
    }

    /**
     * Enable to start the camera trigger before reaching the first survey waypoint.
     * @since 2.8.1
     * @param startCameraBeforeFirstWaypoint
     */
    public void setStartCameraBeforeFirstWaypoint(boolean startCameraBeforeFirstWaypoint) {
        this.startCameraBeforeFirstWaypoint = startCameraBeforeFirstWaypoint;
    }

    public int getCameraCount() {
        return getCameraLocations().size();
    }

    @Override
    public String toString() {
        return "Survey{" +
                "cameraLocations=" + cameraLocations +
                ", surveyDetail=" + surveyDetail +
                ", polygonArea=" + polygonArea +
                ", polygonPoints=" + polygonPoints +
                ", polygonPointAltitudes=" + polygonPointAltitudes +
                ", gridPoints=" + gridPoints +
                ", isValid=" + isValid +
                ", startCameraBeforeFirstWaypoint=" + startCameraBeforeFirstWaypoint +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Survey)) return false;
        if (!super.equals(o)) return false;

        Survey survey = (Survey) o;

        if (Double.compare(survey.polygonArea, polygonArea) != 0) return false;
        if (isValid != survey.isValid) return false;
        if (startCameraBeforeFirstWaypoint != survey.startCameraBeforeFirstWaypoint) return false;
        if (surveyDetail != null ? !surveyDetail.equals(survey.surveyDetail) : survey.surveyDetail != null)
            return false;
        if (polygonPoints != null ? !polygonPoints.equals(survey.polygonPoints) : survey.polygonPoints != null)
            return false;
        if (polygonPointAltitudes != null ? !polygonPointAltitudes.equals(survey.polygonPointAltitudes) : survey.polygonPointAltitudes != null)
            return false;
        if (gridPoints != null ? !gridPoints.equals(survey.gridPoints) : survey.gridPoints != null)
            return false;
        return !(cameraLocations != null ? !cameraLocations.equals(survey.cameraLocations) : survey.cameraLocations != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (surveyDetail != null ? surveyDetail.hashCode() : 0);
        temp = Double.doubleToLongBits(polygonArea);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (polygonPoints != null ? polygonPoints.hashCode() : 0);
        result = 31 * result + (polygonPointAltitudes != null ? polygonPointAltitudes.hashCode() : 0);
        result = 31 * result + (gridPoints != null ? gridPoints.hashCode() : 0);
        result = 31 * result + (cameraLocations != null ? cameraLocations.hashCode() : 0);
        result = 31 * result + (isValid ? 1 : 0);
        result = 31 * result + (startCameraBeforeFirstWaypoint ? 1 : 0);
        result = 31 * result + (isMergedConvexSurvey ? 1 : 0);
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.surveyDetail, 0);
        dest.writeDouble(this.polygonArea);
        dest.writeTypedList(polygonPoints);
        dest.writeList(polygonPointAltitudes);
        dest.writeTypedList(gridPoints);
        dest.writeTypedList(cameraLocations);
        dest.writeByte(isValid ? (byte) 1 : (byte) 0);
        dest.writeByte(startCameraBeforeFirstWaypoint ? (byte) 1: (byte) 0);
        dest.writeByte(isMergedConvexSurvey? (byte) 1 : (byte) 0);
    }

    protected Survey(Parcel in) {
        super(in);
        this.surveyDetail = in.readParcelable(SurveyDetail.class.getClassLoader());
        this.polygonArea = in.readDouble();
        in.readTypedList(polygonPoints, LatLong.CREATOR);
        in.readList(polygonPointAltitudes, Double.class.getClassLoader());
        in.readTypedList(gridPoints, LatLong.CREATOR);
        in.readTypedList(cameraLocations, LatLong.CREATOR);
        this.isValid = in.readByte() != 0;
        this.startCameraBeforeFirstWaypoint = in.readByte() != 0;
        this.isMergedConvexSurvey = in.readByte() != 0;
    }

    @Override
    public MissionItem clone() {
        return new Survey(this);
    }

    public static final Creator<Survey> CREATOR = new Creator<Survey>() {
        public Survey createFromParcel(Parcel source) {
            return new Survey(source);
        }

        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
}
