package com.assessment.hero.util;

import com.assessment.hero.repository.database.model.MissionDAO;

public class MissionUtil {

    public static final String MISSION_NAME = "mission name";

    private MissionUtil(){}

    public static MissionDAO buildMissionDAO(){
        MissionDAO missionDAO = new MissionDAO();
        missionDAO.setMissionName(MISSION_NAME);
        return missionDAO;
    }
}
